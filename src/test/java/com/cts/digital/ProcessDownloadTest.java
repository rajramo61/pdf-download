package com.cts.digital;

import com.cts.digital.download.ProcessDownload;
import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;
import java.net.URISyntaxException;

import static com.cts.digital.WebPageParserTest.PDF_URL;
import static org.junit.jupiter.api.Assertions.*;

class ProcessDownloadTest {

    @Test
    void getHostName() throws URISyntaxException {
        final String hostName = ProcessDownload.getHostName(PDF_URL);
        assertEquals(hostName, "healthy.kaiserpermanente.org", "Hostname did not match");
    }

    @Test
    void getProtocol() throws MalformedURLException {
        final String protocol = ProcessDownload.getProtocol(PDF_URL);
        assertEquals(protocol, "https", "Protocol did not match");
    }
}