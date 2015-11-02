package com.maximiliano.Features.AverageDegree;

import com.maximiliano.Tweet;
import com.maximiliano.TweetProcessor;

import java.io.IOException;
import java.util.Comparator;
import java.util.Date;
import java.util.PriorityQueue;

/**
 * Created by Maximiliano on 10/31/2015.
 *  This class process a stream of tweets, every time a new tweet is processed it recalculates the graph, and hence the
 *  average degree, which can be accessed through a averageDegreeListener.
 */
public class AverageDegreeCalculator implements TweetProcessor.TweetFeature {
    /**
     * The time window of the graph, at every point, elements older than this time are removed from the graph.
     */
    public static final int TIME_WINDOW = 60000;
    /**
     * A comparator for mantaining the tweet heap.
     */
    private Comparator<Tweet> tweetComparator = new Comparator<Tweet>() {
        @Override
        public int compare(Tweet o1, Tweet o2) {
            return o1.created_at.compareTo(o2.created_at);
        }
    };
    /**
     * This is a priority queue that mantains the oldest tweet at the top of the queue.
     */
    PriorityQueue<Tweet> latestTweets;

    Date currentDate;
    private AverageDegreeListener averageDegreeListener;
    private HashtagGraph graph = new HashtagGraph();

    public AverageDegreeCalculator(AverageDegreeListener averageDegreeListener) {
        this.averageDegreeListener = averageDegreeListener;
        latestTweets=new PriorityQueue<Tweet>(10,tweetComparator);
    }

    @Override
    public void process(Tweet tweet) throws IOException {
        currentDate = tweet.created_at;
        latestTweets.add(tweet);
        removeOldTweets();
        graph.addToGraph(tweet);
        double degree = graph.getAverageDegree();

        if (averageDegreeListener != null) {
            averageDegreeListener.update(degree);
        }
    }

    /**
     * Removes every tweet in the latestTweet heap until the heap contains all elements in the {@link #TIME_WINDOW}
     * time frame.
     */
    private void removeOldTweets() {
        while (latestTweets.peek() != null && currentDate.getTime()-latestTweets.peek().created_at.getTime() > TIME_WINDOW) {
            Tweet tweetToRemove = latestTweets.poll();
            graph.removeFromGraph(tweetToRemove);
        }
    }

    /**
     * Updates when the average degree is recalculated.
     */
    public interface AverageDegreeListener {
        public void update(double avgDegree);
    }

}
