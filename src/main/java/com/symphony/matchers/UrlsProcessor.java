package com.symphony.matchers;


import com.symphony.matchers.core.SimpleMatcher;
import com.symphony.matchers.core.exporter.FileResultWriter;
import com.symphony.matchers.core.html.HtmlAttributeExtractor;
import com.symphony.matchers.core.html.HtmlParser;
import com.symphony.matchers.core.html.HtmlTextExtractor;
import com.symphony.matchers.core.regexp.RegExpMatcher;
import com.symphony.matchers.core.exporter.ResultWriter;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class UrlsProcessor {

    private final Logger logger = LoggerFactory.getLogger(UrlsProcessor.class);

    private List<String> textFilters = new ArrayList<>();
    private List<String> htmlTextFilters = new ArrayList<>();
    private List<String> htmlAttributeFilters = new ArrayList<>();
    private ResultWriter writer = null;
    private HtmlParser htmlParser = HtmlParser.fromUrl();

    public void start(String path) {
        Path location = Paths.get(path);
        try (Stream<String> stream = Files.lines(location)) {

            execute(location, stream);


        } catch (IOException | InterruptedException | ExecutionException e) {
            logger.error("error executing ", e);
        }
    }

    protected void execute(Path location, Stream<String> stream) throws InterruptedException, ExecutionException {
        final SimpleMatcher<String, String> text = textFilters.stream().map(RegExpMatcher::build).reduce((s) -> Stream.empty(), SimpleMatcher::aggregate);
        final SimpleMatcher<Document, String> html = Stream.concat(htmlTextFilters.stream().map(HtmlTextExtractor::build),
                htmlAttributeFilters.stream().map(HtmlAttributeExtractor::build)).reduce((s) -> Stream.empty(), SimpleMatcher::aggregate);
        ForkJoinPool customThreadPool = new ForkJoinPool();


        customThreadPool.submit(
                () -> {

                    final ResultWriter w = writer == null ? new FileResultWriter(location.getParent().toString()) : writer;
                    stream.parallel().distinct()
                            .map(htmlParser::parse).forEach(s -> {
                                Stream<String> evaluate = html.evaluate(s);
                                String content = evaluate.flatMap(text::evaluate).collect(Collectors.joining(System.lineSeparator()));
                                w.write(s.location(), content);
                            }

                    );

                }
        ).get();
    }

    public List<String> getTextFilters() {
        return textFilters;
    }

    public void setTextFilters(List<String> textFilters) {
        this.textFilters = textFilters;
    }

    public List<String> getHtmlTextFilters() {
        return htmlTextFilters;
    }

    public void setHtmlTextFilters(List<String> htmlTextFilters) {
        this.htmlTextFilters = htmlTextFilters;
    }

    public List<String> getHtmlAttributeFilters() {
        return htmlAttributeFilters;
    }

    public void setHtmlAttributeFilters(List<String> htmlAttributeFilters) {
        this.htmlAttributeFilters = htmlAttributeFilters;
    }

    public void setWriter(ResultWriter writer) {
        this.writer = writer;
    }

    public ResultWriter getWriter() {
        return writer;
    }

    public HtmlParser getHtmlParser() {
        return htmlParser;
    }

    public void setHtmlParser(HtmlParser htmlParser) {
        this.htmlParser = htmlParser;
    }
}
