package com.symphony.matchers.core.html;

import com.symphony.matchers.core.SimpleMatcher;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;


@Component
public  abstract class CssExtractor implements SimpleMatcher<Document,String> {

    private final String query;

    protected CssExtractor(String query) {
        this.query = query;
    }

    @Override
    public Stream<String> evaluate(Document document) {
        Elements elements = document.select(query);
        return find(elements);
    }

    protected abstract Stream<String> find(Elements elements);


}
