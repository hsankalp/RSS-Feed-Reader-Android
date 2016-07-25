package com.example.sankalpheranjal.feed4me;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


public class ParseRssFeeds {

    private static String CHANNEL = "channel";
    private static String TITLE = "title";
    private static String LINK = "link";
    private static String DESC = "description";
    private static String ITEM = "item";
    private static String PDATE = "pubDate";


    public ParseRssFeeds() {

    }

    public ArrayList<Tags> getRSSFeedItems(String rssUrl) {
        ArrayList<Tags> arrayList = new ArrayList<>();

        try {
            Document doc = this.getDomElement(rssUrl);
            NodeList channel = doc.getElementsByTagName(CHANNEL);
            Element element = (Element) channel.item(0);
            NodeList itemList = element.getElementsByTagName(ITEM);

            for (int i = 0; i < itemList.getLength(); i++) {

                Element element1 = (Element)itemList.item(i);

                String title = this.getValue(element1, TITLE);
                String link = this.getValue(element1, LINK);
                String description = this.getValue(element1, DESC);
                String pubDate = this.getValue(element1, PDATE);

                Tags rssTags = new Tags(title, link, description, pubDate);

                arrayList.add(rssTags);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return arrayList;
    }

    public Document getDomElement(String rssUrl) throws Exception {

        URL url = new URL(rssUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        InputStream inputStream = connection.getInputStream();
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        return documentBuilder.parse(inputStream);

    }

    public final String getElementValue(Node element) {

        Node child;
        if (element != null) {
            if (element.hasChildNodes()) {
                for (child = element.getFirstChild(); child != null; child = child.getNextSibling()) {
                    if (child.getNodeType() == Node.TEXT_NODE || (child.getNodeType() == Node.CDATA_SECTION_NODE)) {
                        return child.getNodeValue();
                    }
                }
            }
        }
        return "";
    }

    public String getValue(Element item, String string) {
        NodeList nodeList = item.getElementsByTagName(string);
        return this.getElementValue(nodeList.item(0));
    }
}