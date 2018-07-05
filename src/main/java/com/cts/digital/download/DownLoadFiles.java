package com.cts.digital.download;

import com.cts.digital.Main;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import static java.util.Objects.requireNonNull;

public class DownLoadFiles {

    private static final Logger LOGGER = LoggerFactory.getLogger(DownLoadFiles.class);

    private static final int CONNECT_TIMEOUT = 10000;
    private static final int READ_TIMEOUT = 10000;

    public String buildUri(final String uri, final String rootUrl){
        requireNonNull(uri, "PDF URI can not be null");
        if(uri.startsWith("http") || uri.startsWith("https")){
            return uri;
        } else {
            if(uri.startsWith("/")){
                return rootUrl + uri;
            }else{
                return rootUrl + "/" + uri;
            }
        }
    }

    public void downloadFile(final String fileUrl, final String currentPath) throws IOException {
        final String FILE_NAME = fileUrl.substring(fileUrl.lastIndexOf("/"));
        LOGGER.debug("FILE_NAME {}", FILE_NAME);
        FileUtils.copyURLToFile(
                new URL(fileUrl),
                new File(currentPath + FILE_NAME),
                CONNECT_TIMEOUT,
                READ_TIMEOUT);
    }
}
