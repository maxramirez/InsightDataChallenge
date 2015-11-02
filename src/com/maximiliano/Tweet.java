package com.maximiliano;

import java.util.Date;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Maximiliano on 10/30/2015.
 */
public class Tweet{
    public String text;
    public Date created_at;
    public HashSet<String> hashtags;

    private static Pattern hashsetPattern = Pattern.compile("#([a-zA-Z0-9_]+)");

    private void computeHashtagsFromText() {
        hashtags = new HashSet<String>();
        Matcher matcher = hashsetPattern.matcher(text);
        while (matcher.find()) {
            String upperCaseHashtag = matcher.group(1).toUpperCase();
            hashtags.add(upperCaseHashtag);
        }
    }

    // Lazy loading for getting the hashtags
    public HashSet<String> getHashtags() {
        if (hashtags == null) {
            computeHashtagsFromText();
        }
        return hashtags;
    }
}
