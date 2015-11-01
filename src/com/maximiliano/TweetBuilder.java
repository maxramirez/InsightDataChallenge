package com.maximiliano;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

public class TweetBuilder {
    private String text = null;
    private Date created_at = null;
    private Collection<String> hashtags = null;

    public TweetBuilder setText(String text) {
        this.text = text;
        return this;
    }

    public TweetBuilder setCreated_at(Date created_at) {
        this.created_at = created_at;
        return this;
    }

    public TweetBuilder setHashtags(Collection<String> hashtags) {
        this.hashtags = hashtags;
        return this;
    }

    public Tweet createTweet() {
        Tweet tweet = new Tweet();
        if (hashtags!=null) {
            tweet.hashtags= new HashSet<String>(hashtags);
        }
        tweet.created_at=created_at;
        tweet.text=text;
        return tweet;
    }
}