package com.symphony.matchers.core.html;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.UnknownHostException;

public class HtmlParser implements HtmlParserExecute {
    private final Logger logger = LoggerFactory.getLogger(HtmlParser.class);
    private HtmlParserExecute executor;
    private HtmlParser(){
        executor = this;
    }

    private HtmlParser( HtmlParserExecute executor){
        this.executor = executor;
    }
    public static HtmlParser fromUrl(){
        return new HtmlParser();
    }

    public static HtmlParser fromContent(){
        return new HtmlParser(
                Jsoup::parse
        );
    }

    public Document parse(String url)  {
      return executor.execute(url);
    }


    @Override
    public Document execute(String url) {
        try {
            return Jsoup.connect(url).get();
        } catch (IOException e) {
            logger.error("fail to connect to {}",url,e);
        }
        return new Document(url);
    }
}
