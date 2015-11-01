package com.maximiliano.Test;

import com.maximiliano.Features.AverageDegree.HashtagGraph;
import com.maximiliano.Main;
import com.maximiliano.Tweet;
import com.maximiliano.TweetBuilder;
import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.HashSet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by Maximiliano on 10/31/2015.
 * Test class.
 *
 */
public class TweetTest {
    public Tweet tweet;
    private Tweet tweet2;
    private Tweet tweet3;
    private Tweet tweet4;

    @Before
    public void setUp() {
        tweet = new TweetBuilder().setText("#HOLA #BLA BLA #test #").setCreated_at(new Date()).createTweet();
        tweet2 = new TweetBuilder().setText("#HOLA").setCreated_at(new Date()).createTweet();
        tweet3 = new TweetBuilder().setText("#HOLA #HOLA BLA #HOLA #").setCreated_at(new Date()).createTweet();
        tweet4 = new TweetBuilder().setText("#1 #2 #3 #4").setCreated_at(new Date()).createTweet();

    }

    @Test
    public void testHashtags() {

        HashSet<String> hashtags = tweet.getHashtags();
        assertTrue(hashtags.contains("HOLA"));
        assertTrue(hashtags.contains("BLA"));
        assertTrue(hashtags.contains("TEST"));
        assertTrue(hashtags.size() == 3);

        hashtags = tweet2.getHashtags();
        assertTrue(hashtags.contains("HOLA"));
        assertTrue(hashtags.size() == 1);

        hashtags = tweet3.getHashtags();
        assertTrue(hashtags.contains("HOLA"));
        assertTrue(hashtags.size() == 1);

        hashtags = tweet4.getHashtags();
        assertTrue(hashtags.size() == 4);


    }

    @Test
    public void testHashtagEdges(){
        HashtagGraph hashtagGraph = new HashtagGraph();
        assertEquals(hashtagGraph.calculateEdges(tweet).size(),3);
        assertEquals(hashtagGraph.calculateEdges(tweet2).size(),0);
        assertEquals(hashtagGraph.calculateEdges(tweet3).size(),0);
        assertEquals(hashtagGraph.calculateEdges(tweet4).size(),6);
    }
    @Test
    public void testFile() throws IOException, JSONException, ParseException {
        Main.runFeature2("tweet_input/tweets.txt","tweet_output/out.txt");
    }

}