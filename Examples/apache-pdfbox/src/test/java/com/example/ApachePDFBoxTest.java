package com.example;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

public class ApachePDFBoxTest {

    @Test
    public void testPDF() throws IOException {
        PDDocument document = new PDDocument();

        PDPage page = new PDPage();
        document.addPage( page );

        PDFont font = PDType1Font.COURIER_BOLD;

        PDPageContentStream contentStream = new PDPageContentStream(document, page);

        contentStream.beginText();
        contentStream.setFont( font, 14 );
        contentStream.moveTextPositionByAmount( 100, 500 );
        contentStream.drawString( "Hello conference" );
        contentStream.endText();

        contentStream.close();

        document.save(new File("target", "Example.pdf"));

        document.close();
    }
}
