package com.cts.digital.download;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;

import static java.util.Objects.nonNull;

public class ProcessDownload {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProcessDownload.class);

    private final DownLoadFiles downLoadFiles;

    public ProcessDownload(){
        downLoadFiles = null;
    }

    public ProcessDownload(final DownLoadFiles downLoadFiles){
        this.downLoadFiles = downLoadFiles;
    }

    public static String getHostName(final String mainUrl) throws URISyntaxException {
        URI uri = new URI(mainUrl);
        return uri.getHost();
    }

    public static String getProtocol(final String mainUrl) throws MalformedURLException {
        URL uri = new URL(mainUrl);
        return uri.getProtocol();
    }

    public void processLinks(final List<String> pdfLinks, final String localPath, final String webPageUrl)
            throws URISyntaxException, IOException, ExecutionException, InterruptedException {
        final String rootUrl = buildUrlPrefix(webPageUrl);

        final String currentPath = new java.io.File( localPath ).getCanonicalPath();
        LOGGER.debug("Total PDF links on the webpage {}", pdfLinks.size());

        if(pdfLinks.size() > 100){
            ForkJoinPool threadPool = new ForkJoinPool(10);
            threadPool.submit(
                    () -> pdfLinks.parallelStream()
                            .map(pdfLink -> downLoadFiles.buildUri(pdfLink, rootUrl))
                            .forEach(pdfUrl -> this.downloadFilesToLocalMachine(pdfUrl, currentPath))
            ).get();
        }else{
            pdfLinks.stream()
                    .map(pdfLink -> downLoadFiles.buildUri(pdfLink, rootUrl))
                    .forEach(pdfUrl -> this.downloadFilesToLocalMachine(pdfUrl, currentPath));
        }
    }

    public String buildUrlPrefix(final String webPageUrl) throws URISyntaxException, MalformedURLException {
        final String hostName = getHostName(webPageUrl);
        LOGGER.debug("hostName = {}", hostName);

        final String protocol = getProtocol(webPageUrl);
        LOGGER.debug("protocol = {}", protocol);

        return protocol + ":" + "//" + hostName;
    }

    public void downloadFilesToLocalMachine(final String pdfUrl, final String currentPath){
        try {
            if(nonNull(downLoadFiles)){
                downLoadFiles.downloadFile(pdfUrl, currentPath);
            }
        } catch (IOException e) {
            LOGGER.error("Failed to download the pdf file {} to local path {}", pdfUrl, currentPath);
            e.printStackTrace();
        }
    }
}
