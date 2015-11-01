package com.maximiliano.Filters;

import com.maximiliano.TweetProcessor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Maximiliano on 10/31/2015.
 * This {@link com.maximiliano.TweetProcessor.TextFilter} filters all Unicode characters using regex.
 * It also holds a counter that counts the processed unicode characters.
 */
public class UnicodeFilter implements TweetProcessor.TextFilter {
    Pattern unicodePattern = Pattern.compile("[^\u0000-\u007F]");
    private int counter;

    @Override
    public String filter(String s) {
        Matcher matcher = unicodePattern.matcher(s);
        if (matcher.find()) {
            counter++;
            matcher.reset();
            s = matcher.replaceAll("");
        }
        return s;
    }

    public int getCounter() {
        return counter;
    }
}
