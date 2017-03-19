package com.facts.model;


import android.util.Log;

import com.facts.FactItem;
import com.facts.FactItems;

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
    private static final String TAG = "HTMLParser";
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

    private int parseId(Element fact) {
        int result = Integer.parseInt(fact.attributes().get("tagid"));
        return result;
    }

    private String parseImageUrl(Element fact) {
        String result = null;
        Elements metas = fact.getElementsByClass("meta1");
        if (metas != null && !metas.isEmpty()) {
            Element meta = metas.get(0);
            Element img = meta.child(0);
            result = img.attributes().get("src");
        }
        return result;
    }

    private String parseContent(Element fact) {
        String result = null;
        Elements contents = fact.getElementsByClass("content");
        if(contents != null && !contents.isEmpty()) {
            Element cont = contents.get(0);
            List<TextNode> textNodes = cont.textNodes();
            if(textNodes != null && !textNodes.isEmpty()) {
                result = textNodes.get(0).toString();
            }
        }
        return result;
    }

    FactItems parse() throws IOException {
        Log.e(TAG, "parse IN");
        FactItems result = new FactItems();
        Document document = Jsoup.parse(getStringFromInputStream(mInputStream));
        Elements elements = document.getElementsByClass("fact");
        for (Element element: elements) {
            String content = parseContent(element);
            if(content != null) {
                String imgUrl = parseImageUrl(element);
                int id = parseId(element);
                result.add(new FactItem(id, content, imgUrl));
            }
        }
        Log.e(TAG, "parse OUT");
        return result;
    }

}
