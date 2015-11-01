package com.maximiliano.Features.AverageDegree;

import com.maximiliano.Tweet;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

/**
 * Created by Maximiliano on 10/31/2015.
 * Mantains a Graph connecting Strings( Hashtags) and where the value of an edge {String,String} is the count of
 * Tweets that maintain that relationship.
 */
public class HashtagGraph {
    /**
     * The edges of the graph.
     */
    HashMap<HashtagEdge, Integer> edges = new HashMap<HashtagEdge, Integer>();
    /**
     * The nodes of the map
     */
    HashMap<String,HashSet<HashtagEdge>> nodes=new HashMap<String, HashSet<HashtagEdge>>();

    /**
     * This function removes a tweet from the map. first it calculates the corresponding edges, and then descreases them
     * @param tweet to be removed.
     */
    void removeFromGraph(Tweet tweet) {
        HashSet<HashtagEdge> hashtagEdges = calculateEdges(tweet);
        for (HashtagEdge hashtagEdge : hashtagEdges) {
                decreaseEdge(hashtagEdge);

        }
    }

    /**
     * @param hashtagEdge decrease the count of tweets that mantain a relationship {Hashtag<-->Hashtag}
     * If the tweet count is 0, then the edge is removed.
     */
    private void decreaseEdge(HashtagEdge hashtagEdge) {
        if (edges.containsKey(hashtagEdge)) {
            Integer edgeCount = edges.get(hashtagEdge);
            edgeCount--;
            if(edgeCount<=0){
                removeEdge(hashtagEdge);
            }
            else{
                edges.put(hashtagEdge,edgeCount);
            }
        }
    }

    /**
     * Removes an edge, then updates the edge count for the connecting nodes, if the edge count is 0 for any node,
     * then is removed from the graph.
     * @param hashtagEdge
     */
    private void removeEdge(HashtagEdge hashtagEdge) {
        edges.remove(hashtagEdge);
        HashSet<HashtagEdge> node1Edges=nodes.get(hashtagEdge.hashtag1);
        HashSet<HashtagEdge> node2Edges=nodes.get(hashtagEdge.hashtag2);
        node1Edges.remove(hashtagEdge);
        node2Edges.remove(hashtagEdge);
        if(node1Edges.size()==0){
            nodes.remove(hashtagEdge.hashtag1);
        }
        if(node2Edges.size()==0){
            nodes.remove(hashtagEdge.hashtag2);
        }
    }

    /**
     * This function increases the value of an edge by one. If the edge not exists, it initializes it...
     * if the nodes dont exist. it also initializes them.
     * @param hashtagEdge The node to be increased.
     */
    private void increaseEdge(HashtagEdge hashtagEdge) {
        Integer edgeCount;
        if (!edges.containsKey(hashtagEdge)) {
            edgeCount=1;
            HashSet<HashtagEdge> node1,node2;
            if(!nodes.containsKey(hashtagEdge.hashtag1)){
                node1 = initializeNode(hashtagEdge.hashtag1);
            }
            else{
                node1=nodes.get(hashtagEdge.hashtag1);
            }
            if(!nodes.containsKey(hashtagEdge.hashtag2) )
            {
                node2 = initializeNode(hashtagEdge.hashtag2);
            }
            else{
                node2=nodes.get(hashtagEdge.hashtag2);
            }
            node1.add(hashtagEdge);
            node2.add(hashtagEdge);
        }
        else{
            edgeCount=edges.get(hashtagEdge);
            edgeCount++;
        }
        edges.put(hashtagEdge, edgeCount);
    }

    /**
     * Initializes a node.
     * @param hashtag1
     * @return
     */
    private HashSet<HashtagEdge> initializeNode(String hashtag1) {
        HashSet<HashtagEdge> node1;
        node1=new HashSet<HashtagEdge>();
        nodes.put(hashtag1, node1);
        return node1;
    }

    /**
     * It calculates the edges of a tweet.
     * @param tweet
     * @return all pairs of different tweets.
     */
    public HashSet<HashtagEdge> calculateEdges(Tweet tweet) {
        HashSet<String> hashtags = tweet.getHashtags();
        LinkedList<String> auxList = new LinkedList<String>(hashtags);
        HashSet<HashtagEdge> hashtagEdges = new HashSet<HashtagEdge>();
        for (String hashtag1 : hashtags) {
            for (String hashtag2 : auxList) {
                if (hashtag1.compareTo(hashtag2) > 0) {
                    HashtagEdge hashtagEdge = new HashtagEdge(hashtag1, hashtag2);
                    hashtagEdges.add(hashtagEdge);
                }
            }
        }
        return hashtagEdges;
    }

    /**
     * Calculates the edges in a tweet and increases their tweet count.
     * @param tweet tweet to be added to the graph.
     */
    public void addToGraph(Tweet tweet) {
        HashSet<HashtagEdge> hashtagEdges = calculateEdges(tweet);
        for (HashtagEdge hashtagEdge : hashtagEdges) {
            increaseEdge(hashtagEdge);
        }
    }

    /**
     * The average degree is calculated using the following formula
     * AvgDegree=2*Count(Edges)/Count(Nodes), ignoring nodes that are not connected to the edges.
     */
    public double getAverageDegree() {
        if (nodes.size() == 0) {
            return 0;
        }
        return ((double) edges.size() * 2) / nodes.size();
    }

    /**
     * This class represents an edge in the map, it consists in a pair of hashtags.
     */
    class HashtagEdge {
        public String hashtag1;
        public String hashtag2;

        public HashtagEdge(String hashtag2, String hashtag1) {
            this.hashtag2 = hashtag2;
            this.hashtag1 = hashtag1;
        }

        @Override
        public int hashCode() {
            return hashtag1.hashCode() + hashtag2.hashCode();
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof HashtagEdge) {
                HashtagEdge hashtagEdge = (HashtagEdge) obj;
                if (hashtag1.equals(hashtagEdge.hashtag1) && hashtag2.equals(hashtagEdge.hashtag2)) {
                    return true;
                }
                if (hashtag1.equals(hashtagEdge.hashtag2) && hashtag2.equals(hashtagEdge.hashtag1)) {
                    return true;
                }
            }
            return super.equals(obj);
        }
    }
}
