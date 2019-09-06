package com.symphony.matchers;

import com.symphony.matchers.core.exporter.FileResultWriter;
import com.symphony.matchers.core.exporter.ResultWriter;
import com.symphony.matchers.core.html.CssSelectorTypes;
import com.symphony.matchers.core.html.HtmlParser;
import com.symphony.matchers.core.regexp.RegExpTypes;
import org.hamcrest.CoreMatchers;
import org.junit.Test;
import org.mockito.Mockito;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Stream;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;


public class SimpleMatcherApplicationTests {


    @Test
    public void SimpleHashTag() throws URISyntaxException, ExecutionException, InterruptedException {

        String htmlContent = "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<body>\n" +
                "\n" +
                "<h2>An Unordered HTML List</h2>\n" +
                "\n" +
                "<ul>\n" +
                "  <li>#one</li>\n" +
                "  <li>other#two</li>\n" +
                "  <li>##tree#four#five</li>\n" +
                "</ul>  \n" +
                "\n" +
                "<h2>An Ordered HTML List</h2>\n" +
                "\n" +
                "<ol>\n" +
                "  <li>Coffee</li>\n" +
                "  <li>Tea</li>\n" +
                "  <li>Milk</li>\n" +
                "</ol> \n" +
                "\n" +
                "</body>\n" +
                "</html>";

        UrlsProcessor processor = new UrlsProcessor();
        FileResultWriter writer = Mockito.mock(FileResultWriter.class);
        processor.setWriter(writer);
        processor.setHtmlParser(HtmlParser.fromContent());

        List<String> filters = new ArrayList<>();
        filters.add(CssSelectorTypes.BODY);
        processor.setHtmlTextFilters(filters);

        filters = new ArrayList<>();
        filters.add(RegExpTypes.HASH_TAG_MATCHER);
        processor.setTextFilters(filters);


        URI uri = getClass().getResource("/simpleTagFind.html").toURI();
        processor.execute(Paths.get(uri), Stream.of(htmlContent));
        verify(writer, times(1)).write("", "#one" + System.lineSeparator() +
                "#two" + System.lineSeparator() +
                "#tree" + System.lineSeparator() +
                "#four" + System.lineSeparator() +
                "#five");


    }


    @Test
    public void hashTagWithNumbers() throws URISyntaxException, ExecutionException, InterruptedException {
        String htmlContent = "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<body>\n" +
                "\n" +
                "<h2>An Unordered HTML List</h2>\n" +
                "\n" +
                "<ul>\n" +
                "  <li>#1one</li>\n" +
                "  <li>other#t2wo</li>\n" +
                "</ul>  \n" +
                "\n" +
                "<h2>An Ordered HTML List</h2>\n" +
                "\n" +
                "<ol>\n" +
                "  <li>##tre3e#four4#five5</li>\n" +
                "  <li>Coffee</li>\n" +
                "  <li>Tea</li>\n" +
                "  <li>Milk</li>\n" +
                "</ol> \n" +
                "\n" +
                "</body>\n" +
                "</html>";

        UrlsProcessor processor = new UrlsProcessor();
        FileResultWriter writer = Mockito.mock(FileResultWriter.class);
        processor.setWriter(writer);
        processor.setHtmlParser(HtmlParser.fromContent());

        List<String> filters = new ArrayList<>();
        filters.add(CssSelectorTypes.BODY);
        processor.setHtmlTextFilters(filters);

        filters = new ArrayList<>();
        filters.add(RegExpTypes.HASH_TAG_MATCHER);
        processor.setTextFilters(filters);


        URI uri = getClass().getResource("/simpleTagFind.html").toURI();
        processor.execute(Paths.get(uri), Stream.of(htmlContent));
        verify(writer, times(1)).write("", "#1one" + System.lineSeparator() +
                "#t2wo" + System.lineSeparator() +
                "#tre3e" + System.lineSeparator() +
                "#four4" + System.lineSeparator() +
                "#five5");
    }

    @Test
    public void towLineHashTag() throws URISyntaxException, ExecutionException, InterruptedException {
        String htmlContent = "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<body>\n" +
                "\n" +
                "<s>#</s>" +
                "<b>Java</b>\n" +
                "\n" +
                "<ul>\n" +
                "  <li>#1one</li>\n" +
                "  <li>other#t2wo</li>\n" +
                "</ul>  \n" +
                "\n" +
                "<h2>An Ordered HTML List</h2>\n" +
                "\n" +
                "<ol>\n" +
                "  <li>##tre3e#four4#five5</li>\n" +
                "  <li>Coffee</li>\n" +
                "  <li>Tea</li>\n" +
                "  <li>Milk</li>\n" +
                "</ol> \n" +
                "\n" +
                "</body>\n" +
                "</html>";

        UrlsProcessor processor = new UrlsProcessor();
        FileResultWriter writer = Mockito.mock(FileResultWriter.class);
        processor.setWriter(writer);
        processor.setHtmlParser(HtmlParser.fromContent());

        List<String> filters = new ArrayList<>();
        filters.add(CssSelectorTypes.BODY);
        processor.setHtmlTextFilters(filters);

        filters = new ArrayList<>();
        filters.add(RegExpTypes.HASH_TAG_MATCHER);
        processor.setTextFilters(filters);


        URI uri = getClass().getResource("/simpleTagFind.html").toURI();
        processor.execute(Paths.get(uri), Stream.of(htmlContent));
        verify(writer, times(1)).write("",
                "#Java" + System.lineSeparator() +
                        "#1one" + System.lineSeparator() +
                        "#t2wo" + System.lineSeparator() +
                        "#tre3e" + System.lineSeparator() +
                        "#four4" + System.lineSeparator() +
                        "#five5");
    }

    @Test
    public void attributeFilterSimpleTest() throws URISyntaxException, ExecutionException, InterruptedException {

        String htmlContent = "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<body>\n" +
                "\n" +
                "<h2 href='/home'>An Unordered HTML List</h2>\n" +
                "\n" +
                "<ul>\n" +
                "  <li>#one</li>\n" +
                "  <li>other#two</li>\n" +
                "  <li>##tree#four#five</li>\n" +
                "</ul>  \n" +
                "\n" +
                "<h2>An Ordered HTML List</h2>\n" +
                "\n" +
                "<ol>\n" +
                "  <li>Coffee</li>\n" +
                "  <li>Tea</li>\n" +
                "  <li>Milk</li>\n" +
                "</ol> \n" +
                "\n" +
                "</body>\n" +
                "</html>";

        UrlsProcessor processor = new UrlsProcessor();
        FileResultWriter writer = Mockito.mock(FileResultWriter.class);
        processor.setWriter(writer);
        processor.setHtmlParser(HtmlParser.fromContent());

        List<String> filters = new ArrayList<>();
        filters.add("href");
        processor.setHtmlAttributeFilters(filters);

        filters = new ArrayList<>();
        filters.add(RegExpTypes.ALL);
        processor.setTextFilters(filters);


        URI uri = getClass().getResource("/simpleTagFind.html").toURI();
        processor.execute(Paths.get(uri), Stream.of(htmlContent));
        verify(writer, times(1)).write("", "/home" + System.lineSeparator() );

    }


    @Test
    public void withNoMatchesWriteEmptyFile() throws URISyntaxException, MalformedURLException, ExecutionException, InterruptedException {

        String htmlContent = "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<body>\n" +
                "\n" +
                "<h2>An Unordered HTML List</h2>\n" +
                "\n" +
                "<ul>\n" +
                "  <li>#1one</li>\n" +
                "  <li>other#t2wo</li>\n" +
                "</ul>  \n" +
                "\n" +
                "<h2>An Ordered HTML List</h2>\n" +
                "\n" +
                "<ol>\n" +
                "  <li>##tre3e#four4#five5</li>\n" +
                "  <li>Coffee</li>\n" +
                "  <li>Tea</li>\n" +
                "  <li>Milk</li>\n" +
                "</ol> \n" +
                "\n" +
                "</body>\n" +
                "</html>";
        UrlsProcessor processor = new UrlsProcessor();
        ResultWriter writer = Mockito.mock(ResultWriter.class);
        processor.setWriter(writer);

        processor.setHtmlParser(HtmlParser.fromContent());
        URI uri = getClass().getResource("/simpleTagFind.html").toURI();
        processor.execute(Paths.get(uri), Stream.of(htmlContent));

        verify(writer, times(1)).write(any(), eq(""));

    }


    @Test
    public void multipleLinesHashTag() throws URISyntaxException, ExecutionException, InterruptedException {
        String htmlContent = "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<body>\n" +
                "\n" +
                "<s>#</s>" +
                "<b>Java</b>\n" +
                "\n" +
                "<ul>\n" +
                "  <li>#1one</li>\n" +
                "  <li>other#t2wo</li>\n" +
                "</ul>  \n" +
                "\n" +
                "<h2 href=/home>An Ordered HTML List</h2>\n" +
                "\n" +
                "<ol>\n" +
                "  <li>##tre3e#four4#five5</li>\n" +
                "  <li>Coffee</li>\n" +
                "  <li>Tea</li>\n" +
                "  <li>Milk</li>\n" +
                "</ol> \n" +
                "\n" +
                "</body>\n" +
                "</html>";

        UrlsProcessor processor = new UrlsProcessor();
        FileResultWriter writer = Mockito.mock(FileResultWriter.class);
        processor.setWriter(writer);
        processor.setHtmlParser(HtmlParser.fromContent());

        List<String> filters = new ArrayList<>();
        filters.add(CssSelectorTypes.BODY);
        processor.setHtmlTextFilters(filters);

        filters = new ArrayList<>();
        filters.add(RegExpTypes.HASH_TAG_MATCHER);
        processor.setTextFilters(filters);


        URI uri = getClass().getResource("/simpleTagFind.html").toURI();
        processor.execute(Paths.get(uri), Stream.of(htmlContent));
        verify(writer, times(1)).write("",
                "#Java" + System.lineSeparator() +
                        "#1one" + System.lineSeparator() +
                        "#t2wo" + System.lineSeparator() +
                        "#tre3e" + System.lineSeparator() +
                        "#four4" + System.lineSeparator() +
                        "#five5");
    }


}
