package com.example;

import com.vladsch.flexmark.ast.Text;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.ast.NodeVisitor;
import com.vladsch.flexmark.util.ast.VisitHandler;
import com.vladsch.flexmark.util.data.MutableDataSet;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FlexMarkJavaTest {
    int topicCount = 0;
    private Set<Character> uniqueStartingCharactersSet = new HashSet<>();
    private List<String> gitHubRepositories = new ArrayList<>();

    NodeVisitor visitor = new NodeVisitor(
            new VisitHandler<>(Text.class, this::visit)
    );

    public void visit(Text text) {
        String line = text.getChars().toString();
        if (line.contains("https://github.com/")) {
            gitHubRepositories.add(line);
        }

        if (text.getParent().getChars().startsWith("##")) {
            char firstCharacter = text.getChars().toLowerCase().charAt(0);
            if (Character.isLetter(firstCharacter)) {
                uniqueStartingCharactersSet.add(firstCharacter);
            }
            topicCount++;
        }
        visitor.visitChildren(text);
    }

    @Test
    void testNumberOfTopics() throws IOException {
        String fileName = "../../README.md";
        List<String> lines = Files.readAllLines(Paths.get(fileName), StandardCharsets.UTF_8);
        String markdown = String.join("\n", lines);

        MutableDataSet options = new MutableDataSet();
        Parser parser = Parser.builder(options).build();
        Node document = parser.parse(markdown);
        visitor.visit(document);
        assertEquals(69, topicCount);
        assertEquals(19, uniqueStartingCharactersSet.size());

        String alphabetString = uniqueStartingCharactersSet.stream()
                .map(String::valueOf)
                .collect(Collectors.joining());
        assertEquals("abcefghijlmoprstuvw", alphabetString);

        testGitHubLinks();
    }

    void testGitHubLinks() {
        for(String repositoryURL: gitHubRepositories) {
            String repositoryName = repositoryURL.replace("https://github.com/", "");
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<GitHubRepository> repositoryInfo = restTemplate.exchange(
                    "https://api.github.com/repos/" + repositoryName,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<GitHubRepository>() {
                    });
            System.out.println(repositoryInfo.getBody());
        }

    }
}
