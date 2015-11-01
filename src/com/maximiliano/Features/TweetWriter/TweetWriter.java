package com.maximiliano.Features.TweetWriter;

import com.maximiliano.Tweet;
import com.maximiliano.TweetProcessor;

import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;

/**
 * Created by Maximiliano on 10/31/2015.
 * This method writes the stream of tweets as mentioned in the challenge documentation.
 */
public class TweetWriter implements TweetProcessor.TweetFeature {
    private FileWriter fileWriter;
    final String TWITTER_DATE_FORMAT = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
    final SimpleDateFormat twitterDateFormat = new SimpleDateFormat(TWITTER_DATE_FORMAT);

    public TweetWriter(FileWriter fileWriter) {
        this.fileWriter = fileWriter;
    }

    @Override
    public void process(Tweet tweet) throws IOException {
        StringBuffer message = new StringBuffer();
        message.append(tweet.text);
        message.append('(');
        message.append(twitterDateFormat.format(tweet.created_at));
        message.append(")\n");
        fileWriter.write(message.toString());
    }

}
