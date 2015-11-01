package com.maximiliano;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by Maximiliano on 10/30/2015.
 */
public class TweetProcessor {
    final String TWITTER_DATE_FORMAT = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
    final SimpleDateFormat twitterDateFormat = new SimpleDateFormat(TWITTER_DATE_FORMAT);
    List<TweetFeature> featureList = new ArrayList<TweetFeature>();
    List<TextFilter> filterList = new ArrayList<TextFilter>();
    enum TweetFields {text,created_at,hashtags};
    private boolean extract_created_at;
    private boolean extract_text;
    private boolean extract_hashtags;

    public TweetProcessor(TweetFields... extractedFields) {
        List<TweetFields> tweetFieldses = Arrays.asList(extractedFields);
        if(tweetFieldses.contains(TweetFields.created_at)){
            extract_created_at=true;
        }
        if(tweetFieldses.contains(TweetFields.text)){
            extract_text=true;
        }
        if(tweetFieldses.contains(TweetFields.hashtags)){
            extract_hashtags=true;
        }
    }

    public void addTextFilter(TextFilter... filters) {
        for(TextFilter filter:filters) {
            filterList.add(filter);
        }
    }

    public interface TweetFeature {
        void process(Tweet message) throws IOException;
    }

    public interface TextFilter {
        String filter(String s);
    }

    public Tweet processJsonString(String s) throws JSONException, ParseException, IOException {
        JSONObject json = new JSONObject(s);
        if (!json.has("text") && !json.has("created_at")) {
            return null;
        }
        TweetBuilder tweetBuilder = new TweetBuilder();
        if (extract_text) {
            String message = json.getString("text");
            message = cleanMessage(message);
            tweetBuilder.setText(message);
        }
        if (extract_created_at) {
            String createdAt = json.getString("created_at");
            Date date = twitterDateFormat.parse(createdAt);
            tweetBuilder.setCreated_at(date);
        }
        if (extract_hashtags) {
            JSONArray hashtags = json.getJSONArray("hashtags");
            List<String> list = new ArrayList<String>(hashtags.length());
            for (int i = 0; i < hashtags.length(); i++) {
                list.add(hashtags.getString(i));
            }
            tweetBuilder.setHashtags(list);
        }
        Tweet tweet = tweetBuilder.createTweet();
        processTweet(tweet);
        return tweet;
    }

    public void processJsonStream(BufferedReader br) throws IOException, JSONException, ParseException {
        for (String line; (line = br.readLine()) != null; ) {
            processJsonString(line);
        }
    }
    public String cleanMessage(String s) {
        for (TextFilter textFilter : filterList) {
            s = textFilter.filter(s);
        }
        return s;
    }

    public void addFeature(TweetFeature... features)
    {
        for(TweetFeature feature:features) {
            featureList.add(feature);
        }
    }

    private void processTweet(Tweet tweet) throws IOException {
        for (TweetFeature feature : featureList) {
            feature.process(tweet);
        }
    }
}
