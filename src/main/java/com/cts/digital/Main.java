package com.cts.digital;

import com.cts.digital.download.DownLoadFiles;
import com.cts.digital.download.ProcessDownload;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Scanner;

import static java.util.Objects.nonNull;

public class Main {

    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) throws IOException, URISyntaxException {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the url to download files");
        String webPageUrl = scanner.next();
        LOGGER.debug("Input URL {}", webPageUrl);
        if(nonNull(webPageUrl) && webPageUrl.isEmpty()) {
            throw new RuntimeException("No weburl is provided to download PDFs");
        }

        System.out.println("Enter the location to download the files on this machine");
        String localPath = scanner.next();
        LOGGER.debug("localPath to download the files {}", localPath);
        if(nonNull(localPath) && localPath.isEmpty()) {
            throw new RuntimeException("No location provided to download the PDFs");
        }

        WebPageParser webPageParser = new WebPageParser();
        final Document parserDocument = webPageParser.getParserDocument(webPageUrl);
        final List<String> pdfLinks = webPageParser.getPdfLinks(parserDocument);

        if(nonNull(pdfLinks) && !pdfLinks.isEmpty()){
            final ProcessDownload processDownload = new ProcessDownload(new DownLoadFiles());
            processDownload.processLinks(pdfLinks, localPath, webPageUrl);
        }
    }

}
