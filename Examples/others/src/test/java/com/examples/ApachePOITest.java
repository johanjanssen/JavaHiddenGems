package com.examples;

import com.itextpdf.text.pdf.PdfWriter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ApachePOITest {
    @BeforeAll
    static void init() throws IOException {
        try (XWPFDocument doc = new XWPFDocument()) {

            XWPFParagraph paragraph1 = doc.createParagraph();
            paragraph1.setAlignment(ParagraphAlignment.LEFT);
            XWPFRun run1 = paragraph1.createRun();
            run1.setBold(true);
            run1.setFontSize(22);
            String firstParagraph = """
                Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin in fringilla arcu. Nam ullamcorper ut nisi in semper. Fusce dictum pellentesque nulla in congue. Maecenas finibus felis nec efficitur ornare. Mauris velit magna, dapibus in ex et, fermentum ultrices eros. Mauris ut sodales lectus. Mauris lobortis eleifend turpis, et viverra tellus hendrerit porta. Curabitur ut magna sagittis, sollicitudin ex ac, molestie sem. Aliquam lobortis nisl sit amet ipsum malesuada, vel lacinia nisl feugiat. Donec iaculis, nisl sit amet aliquam luctus, sapien nisl dapibus nibh, et molestie nunc lorem ut risus.""";
            run1.setText(firstParagraph);
            run1.setFontFamily("Elephant");

            XWPFParagraph paragraph2 = doc.createParagraph();
            XWPFRun run2 = paragraph2.createRun();
            run2.setItalic(true);
            run2.setFontSize(22);
            String secondParagraph = """
                Nam pellentesque id massa quis rhoncus. Praesent faucibus lorem metus, vel volutpat ex interdum eget. In tempus finibus felis, ut dapibus velit ultricies eget. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia curae; Ut maximus nibh id quam scelerisque, vel cursus dui commodo. Ut viverra sapien enim, vitae mollis dui malesuada mollis. Praesent eget lorem augue.""";
            run2.setText(secondParagraph);
            run2.setFontFamily("Courier");

            XWPFParagraph paragraph3 = doc.createParagraph();
            XWPFRun run3 = paragraph3.createRun();
            run3.setBold(true);
            run3.setItalic(true);
            run3.setFontSize(22);
            String thirdParagraph = """
                Phasellus at nisi id nibh condimentum efficitur in in leo. Donec mi nisl, elementum eget lorem id, placerat convallis lorem. Fusce lobortis vehicula lectus id malesuada. Mauris ut dui eu diam sollicitudin porta. Integer at lacus elit. Fusce nisi ligula, fringilla ut venenatis non, posuere vel magna. In fringilla quam et mi semper scelerisque. Pellentesque accumsan purus ex, in bibendum lectus consectetur vel. Morbi feugiat, purus nec consequat aliquam, tortor lorem varius enim, et viverra lorem neque eget ante. Nullam non lectus ante. Aenean ultrices convallis arcu, eu suscipit metus auctor nec.""";
            run3.setText(thirdParagraph);
            run3.setFontFamily("Consolas");

            Path resourceDirectory = Paths.get("target", "WordOutput.docx");

            try(FileOutputStream fileOutputStream = new FileOutputStream(resourceDirectory.toFile())) {
                doc.write(fileOutputStream);
            }
        }
    }

    @Test
    void testPOI() throws IOException {
        // Read Word file
        List<String> paragraphs = new ArrayList<>();

        Path wordFilePath = Paths.get("target", "WordOutput.docx");

        try (XWPFDocument wordDocument = new XWPFDocument(
                Files.newInputStream(wordFilePath))) {
            wordDocument.getParagraphs().forEach(paragraph -> paragraphs.add(paragraph.getText()));
        }

        // Store in Excel file
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Apacha POI Example");

        int rowNumber = 0;
        for (String paragraph: paragraphs) {
            Row row = sheet.createRow(rowNumber++);

            int cellNumber = 0;
            row.createCell(cellNumber++).setCellValue((Integer) rowNumber);
            row.createCell(cellNumber++).setCellValue((String) paragraph);
            long numberOfWords = Arrays.stream(paragraph.split("\\s+")).count();
            row.createCell(cellNumber++).setCellValue((Long) numberOfWords);
        }

        Path excelFilePath = Paths.get("target", "ExcelOutput.xlsx");

        try(FileOutputStream fileOutputStream = new FileOutputStream(excelFilePath.toFile())) {
            workbook.write(fileOutputStream);
        }
    }
}
