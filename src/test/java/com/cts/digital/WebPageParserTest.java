package com.cts.digital;

import org.jsoup.nodes.Document;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class WebPageParserTest {

    private WebPageParser webPageParser;

    final static String PDF_URL = "https://www.mayoclinic.org/patient-visitor-guide/billing-insurance/financial-assistance/arizona-documents";

    @BeforeEach
    void setUp(){
        webPageParser = new WebPageParser();
    }

    @Test
    void getParserDocument() throws IOException {
        final Document parserDocument = webPageParser.getParserDocument(PDF_URL);
        assertNotNull(parserDocument, "webpage parser document object can not be null");
    }

    @Test
    void getPdfLinks() throws IOException {
        final Document parserDocument = webPageParser.getParserDocument(PDF_URL);
        final List<String> pdfLinks = webPageParser.getPdfLinks(parserDocument);
        assertTrue(pdfLinks.size() > 0, "There should be at least one pdf");
    }

    @Test
    void isLinkPdf() {
    }
}