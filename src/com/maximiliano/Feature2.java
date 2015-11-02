package com.maximiliano;

import com.maximiliano.Features.AverageDegree.AverageDegreeCalculator;
import com.maximiliano.Features.AverageDegree.AverageDegreeWriter;
import org.json.JSONException;

import java.io.*;
import java.text.ParseException;

/**
 * Created by Maximiliano on 11/1/2015.
 */
public class Feature2 {
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
        runFeature2(inputPath, outputPath);
    }

    public static void runFeature2(String inputPath, String outputPath) throws IOException {
        FileWriter fileWriter = null;
        BufferedReader br = null;
        try {
            File input = new File(inputPath);
            br = new BufferedReader(new FileReader(input));
            TweetProcessor tweetProcessor = new TweetProcessor(TweetProcessor.TweetFields.created_at, TweetProcessor.TweetFields.hashtags);
            File output = new File(outputPath);
            fileWriter = new FileWriter(output);

            AverageDegreeCalculator.AverageDegreeListener averageDegreeListener = new AverageDegreeWriter(fileWriter);
            AverageDegreeCalculator averageDegreeCalculator = new AverageDegreeCalculator(averageDegreeListener);
            tweetProcessor.addFeature(averageDegreeCalculator);

            tweetProcessor.processJsonStream(br);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }finally {
            if(fileWriter!=null) fileWriter.close();
            if(br!=null)br.close();
        }
    }
}
