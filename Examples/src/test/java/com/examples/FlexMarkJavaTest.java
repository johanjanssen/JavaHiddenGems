package com.examples;

import com.vladsch.flexmark.ast.Text;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.ast.NodeVisitor;
import com.vladsch.flexmark.util.ast.VisitHandler;
import com.vladsch.flexmark.util.data.MutableDataSet;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FlexMarkJavaTest {
    int topicCount = 0;

    NodeVisitor visitor = new NodeVisitor(
            new VisitHandler<>(Text.class, this::visit)
    );

    public void visit(Text text) {
        // This is called for all Text nodes. Override other visit methods for other node types.
        if (text.getParent().getChars().startsWith("##")) {
            topicCount++;
        }
        visitor.visitChildren(text);
    }

    @Test
    void testNumberOfTopics() throws IOException {
        String fileName = "../README.md";
        List<String> lines = Files.readAllLines(Paths.get(fileName), StandardCharsets.UTF_8);
        String markdown = String.join("\n", lines);

        MutableDataSet options = new MutableDataSet();
        Parser parser = Parser.builder(options).build();
        Node document = parser.parse(markdown);
        visitor.visit(document);
        assertEquals(64, topicCount);
    }
}
