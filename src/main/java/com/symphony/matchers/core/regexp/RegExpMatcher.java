package com.symphony.matchers.core.regexp;

import com.symphony.matchers.core.SimpleMatcher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class RegExpMatcher implements SimpleMatcher <String,String>{

    private final Logger logger = LoggerFactory.getLogger(RegExpMatcher.class);
    private final Pattern pattern;

    private RegExpMatcher(String exp) {
        pattern = Pattern.compile(exp);
    }

    @Override
    public Stream<String> evaluate(String tag) {
        logger.debug("doing {}",tag.hashCode());
        Matcher matcher = pattern.matcher(tag);
        Stream.Builder<String> builder = Stream.builder();
        while (matcher.find()) {
            builder.add(matcher.group());
        }
        return builder.build();
    }

    public static SimpleMatcher <String,String> build(String regExp) {
        return new RegExpMatcher(regExp);
    }
}
