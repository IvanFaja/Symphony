package com.symphony;

import com.symphony.matchers.UrlsProcessor;
import com.symphony.matchers.core.exporter.FileResultWriter;
import com.symphony.matchers.core.html.CssSelectorTypes;
import com.symphony.matchers.core.html.HtmlParser;
import com.symphony.matchers.core.regexp.RegExpTypes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CommaLineRunner implements ApplicationRunner {

    private final Logger logger = LoggerFactory.getLogger(CommaLineRunner.class);

    @Autowired
    private UrlsProcessor processor;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        List<String> path = args.getOptionValues("path");
        if (path ==null) {
            logger.info("path argument is mandatory");
            System.exit(1);
        }
        List<String> regexp = args.getOptionValues("regexp");
        List<String> textSelector = args.getOptionValues("text");
        List<String> attributesSelector = args.getOptionValues("attributes");
//
        if (regexp == null) {
            regexp = new ArrayList<>();
            regexp.add(RegExpTypes.HASH_TAG_MATCHER);
        }
        if (textSelector == null) {
            textSelector = new ArrayList<>();
            textSelector.add(CssSelectorTypes.BODY);
            textSelector.add(CssSelectorTypes.HEAD);
        }
        if (attributesSelector == null) {
            attributesSelector = new ArrayList<>();

        }
        processor.setHtmlAttributeFilters(attributesSelector);
        processor.setTextFilters(regexp);
        processor.setHtmlTextFilters(textSelector);

        path.forEach(processor::start);
        logger.info("done!!");
        System.exit(0);
    }
}
