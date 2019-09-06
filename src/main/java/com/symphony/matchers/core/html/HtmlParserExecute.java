package com.symphony.matchers.core.html;

import org.jsoup.nodes.Document;

public interface HtmlParserExecute {
    Document execute(String url);
}
