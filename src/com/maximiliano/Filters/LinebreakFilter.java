package com.maximiliano.Filters;

import com.maximiliano.TweetProcessor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Maximiliano on 10/31/2015.
 * This {@link com.maximiliano.TweetProcessor.TextFilter} filters escaping characters using regex.
 */
public class LinebreakFilter implements TweetProcessor.TextFilter {
    Pattern escaping = Pattern.compile("\\r?\\n|\\v|\\r");
    @Override
    public String filter(String s) {
        Matcher matcherEscaping = escaping.matcher(s);
        if(matcherEscaping.find()){
            matcherEscaping.reset();
            s = matcherEscaping.replaceAll("");
        }
        return s;
    }
}
