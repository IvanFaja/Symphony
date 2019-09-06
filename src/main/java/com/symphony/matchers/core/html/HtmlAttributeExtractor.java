package com.symphony.matchers.core.html;

import com.symphony.matchers.core.SimpleMatcher;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;


import java.util.stream.Stream;

public class HtmlAttributeExtractor extends CssExtractor {
    private String attributeKey;

    protected HtmlAttributeExtractor(String query,String attributeKey) {
        super(query);
        this.attributeKey=attributeKey;
    }

    @Override
    protected Stream<String> find(Elements elements) {
        return elements.stream().map(s->s.attributes().get(attributeKey));
    }

    public static SimpleMatcher<Document,String> build(String s) {

        return new HtmlAttributeExtractor(String.format("[%1$s]:not([%1$s=\"\"])",s),s);
    }
}
