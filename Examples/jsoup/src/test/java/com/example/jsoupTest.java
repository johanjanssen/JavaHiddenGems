package com.example;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class jsoupTest {

    @Test
    void testChocolatey() throws IOException {
        Document doc = Jsoup.connect("https://community.chocolatey.org/profiles/JohanJanssen").get();
        Elements elements = doc.select(".card-header").select("div.col-md.py-3.py-md-0");

        for (Element element : elements) {
            if (element.text().contains("Downloads of Packages")) {
                String result = element.select("h4").get(0).text();
                System.out.println("Number of downloads " + result);
                String plainResult = result.replaceAll(",", "");
                Integer integerResult = Integer.valueOf(plainResult);
                assertTrue(Integer.valueOf(plainResult) > 3_000_000);
            }
        }

    }
}
