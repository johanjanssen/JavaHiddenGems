package com.example;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;

public class iTextTest {
    @Test
    void testiText() throws FileNotFoundException, DocumentException {

        Document document = new Document();

        Path resourceDirectory = Paths.get("target", "POI.pdf");

        PdfWriter.getInstance(document, new FileOutputStream(resourceDirectory.toFile()));
        document.open();

        Font larger = new Font();
        larger.setSize(14);

        Paragraph paragraph1 = new Paragraph();
        String firstParagraph = """
            Java is a high-level, class-based, object-oriented programming language that is designed to have as few implementation dependencies as possible. It is a general-purpose programming language intended to let programmers write once, run anywhere (WORA),[17] meaning that compiled Java code can run on all platforms that support Java without the need to recompile.[18] Java applications are typically compiled to bytecode that can run on any Java virtual machine (JVM) regardless of the underlying computer architecture. The syntax of Java is similar to C and C++, but has fewer low-level facilities than either of them. The Java runtime provides dynamic capabilities (such as reflection and runtime code modification) that are typically not available in traditional compiled languages. As of 2019, Java was one of the most popular programming languages in use according to GitHub,[19][20] particularly for client–server web applications, with a reported 9 million developers.[21]""";
        document.add(new Paragraph(firstParagraph, larger));

        Paragraph paragraph2 = new Paragraph();
        String secondParagraph = """
                Java was originally developed by James Gosling at Sun Microsystems and released in May 1995 as a core component of Sun Microsystems' Java platform. The original and reference implementation Java compilers, virtual machines, and class libraries were originally released by Sun under proprietary licenses. As of May 2007, in compliance with the specifications of the Java Community Process, Sun had relicensed most of its Java technologies under the GPL-2.0-only license. Oracle offers its own HotSpot Java Virtual Machine, however the official reference implementation is the OpenJDK JVM which is free open-source software and used by most developers and is the default JVM for almost all Linux distributions.""";
        document.add(new Paragraph(secondParagraph, larger));

        Font smallBold = new Font();
        smallBold.setStyle(Font.BOLD);
        smallBold.setSize(8);

        document.add(new Paragraph("From: https://en.wikipedia.org/wiki/Java_(programming_language)", smallBold));

        document.close();
    }
}
