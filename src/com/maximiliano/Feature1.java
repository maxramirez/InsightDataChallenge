package com.maximiliano;

import com.maximiliano.Features.TweetWriter.TweetWriter;
import com.maximiliano.Filters.LinebreakFilter;
import com.maximiliano.Filters.UnicodeFilter;
import org.json.JSONException;

import java.io.*;
import java.text.ParseException;

public class Feature1 {

    /**
     * This method runs the example
     * @param args
     * @throws IOException
     * @throws JSONException
     * @throws ParseException
     */
    public static void main(String[] args) throws IOException, JSONException, ParseException {
        String inputPath = args.length==0?"tweet_input/tweets.txt":args[0];
        String outputPath = args.length<3?"tweet_output/ft1.txt":args[1];
        runFeature1(inputPath, outputPath);
    }

    public static void runFeature1(String inputPath, String outputPath) throws IOException {
        File input = new File(inputPath);
        BufferedReader br = null;
        FileWriter fileWriter = null;
        try {
            br = new BufferedReader(new FileReader(input));
            File output = new File(outputPath);
            fileWriter = new FileWriter(output);

            TweetProcessor tweetProcessor = new TweetProcessor(TweetProcessor.TweetFields.created_at, TweetProcessor.TweetFields.text);
            TweetWriter tweetWriter = new TweetWriter(fileWriter);

            UnicodeFilter unicodeFilter = new UnicodeFilter();
            LinebreakFilter linebreakFilter = new LinebreakFilter();
            tweetProcessor.addTextFilter(unicodeFilter, linebreakFilter);

            tweetProcessor.addFeature(tweetWriter);

            tweetProcessor.processJsonStream(br);

            fileWriter.write(String.format("\n%d tweets contained unicode.\n", unicodeFilter.getCounter()));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } finally {
            if(fileWriter!=null) fileWriter.close();
            if(br!=null)br.close();
        }

    }


}
