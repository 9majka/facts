package com.facts;


import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

public class HTMLParser {
    private InputStream mInputStream;
    public HTMLParser(InputStream inputStream) {
        mInputStream = inputStream;
    }

    private static String getStringFromInputStream(InputStream is) {

        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();

        String line;
        try {
            br = new BufferedReader(new InputStreamReader(is));
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return sb.toString();

    }

    FactItems parse() throws IOException {
        FactItems result = new FactItems();
        Document document = Jsoup.parse(getStringFromInputStream(mInputStream));
        Elements elements = document.getElementsByClass("fact");
        for (Element element: elements) {
            Elements contents = element.getElementsByClass("content");
            if(contents != null && !contents.isEmpty()) {
                Element cont = contents.get(0);

                List<TextNode> textNodes = cont.textNodes();
                if(textNodes != null && !textNodes.isEmpty()) {
                    result.add(new FactItem(textNodes.get(0).toString()));
                }
            }
        }
        return result;
    }

}
