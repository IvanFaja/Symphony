package com.symphony.matchers.core.html;

import com.symphony.matchers.core.SimpleMatcher;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.stream.Stream;

public class HtmlTextExtractor extends CssExtractor {

    private HtmlTextExtractor(String query) {
        super(query);
    }

    @Override
    protected Stream<String> find(Elements elements) {
        return elements.stream().map(Element::text);
    }

    public static SimpleMatcher<Document,String> build(String query) {
        return new HtmlTextExtractor(query);
    }
}
