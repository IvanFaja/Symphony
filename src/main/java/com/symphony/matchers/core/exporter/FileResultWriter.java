package com.symphony.matchers.core.exporter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.atomic.AtomicInteger;

public class FileResultWriter implements ResultWriter {
    private final Logger logger = LoggerFactory.getLogger(FileResultWriter.class);
    private String base;
    private AtomicInteger name = new AtomicInteger(0);
    public FileResultWriter(String path) {
        base = path;
    }

    @Override
    public void write(String tag, String content) {
        String file = "";
        try {
            URL url = new URL(tag);
            file = url.getHost();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        logger.debug("writing file {} with content {}",tag,content.hashCode());
        Path path = Paths.get(getBase(),file+Integer.toString(name.addAndGet(1))+".txt");
        try {
            Files.write(path, content.getBytes());
        } catch (IOException e) {
            logger.error("Error writing file {}",path.toString(),e);
        }
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }
}
