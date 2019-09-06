package com.symphony.matchers;

import org.junit.Test;

import java.net.URISyntaxException;
import java.nio.file.Paths;

public class TextFileReaderTests {
    @Test
    public void testNotCollisionForFilesNames() throws URISyntaxException {
        UrlsProcessor reader = new UrlsProcessor();
        reader.start(Paths.get(getClass().getResource("/5urls.txt").toURI()).toString());
    }


}
