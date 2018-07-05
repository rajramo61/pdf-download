package com.cts.digital;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

import static java.util.stream.Collectors.toList;

public class WebPageParser {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebPageParser.class);

    public Document getParserDocument(final String uri) throws IOException {
        LOGGER.debug("URI passed for parsing {}", uri);
        try {
            return Jsoup.connect(uri).get();
        }catch (UnknownHostException uhe){
            LOGGER.error("It seems internet connection is down {}", uhe);
            throw uhe;
        }catch (IllegalArgumentException iae){
            LOGGER.error("It seems weburl is not correct {}", iae);
            throw iae;
        }
    }

    public List<String> getPdfLinks(final Document document){
        return document.select("a")
                .stream()
                .filter(Objects::nonNull)
                .filter(element -> element.hasAttr("href"))
                .filter(isLinkPdf())
                .map(element -> element.attr("href"))
                .collect(toList());
    }

    public Predicate<Element> isLinkPdf() {
        return element -> element.attr("href").endsWith(".pdf") || element.attr("href").endsWith(".PDF");
    }
}
