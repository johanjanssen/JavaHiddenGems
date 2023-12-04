package com.example;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

public class ApachePDFBoxTest {

    @Test
    public void testPDF() throws IOException {
        PDDocument document = new PDDocument();

        PDPage page = new PDPage();
        document.addPage( page );

        PDPageContentStream contentStream = new PDPageContentStream(document, page);

        contentStream.beginText();
        contentStream.newLineAtOffset(100, 500);
        contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.COURIER_BOLD), 14 );
        contentStream.showText("Hello conference");
        contentStream.endText();

        contentStream.close();

        document.save(new File("target", "Example.pdf"));

        document.close();
    }
}
