package com.example.sankalpheranjal.feed4me;

import android.app.ListActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SecondActivity extends ListActivity {

    ArrayList<HashMap<String, String>> arrayList = new ArrayList<HashMap<String, String>>();

    ParseRssFeeds parseRssFeeds = new ParseRssFeeds();

    List<Tags> tags = new ArrayList<Tags>();

    private static String TITLE = "title";
    private static String LINK = "link";
    private static String DESC = "description";
    private static String PDATE = "pubDate";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        Intent intent = getIntent();
        int clicked = intent.getIntExtra("itemClicked", 0);
        String rssUrl = "";

        if (clicked == 0) {
            rssUrl = "http://www.cnet.com/rss/news/";
        } else if (clicked == 1) {
            rssUrl = "https://www.sciencemag.org/rss/news_current.xml";
        } else if (clicked == 2) {
            rssUrl = "http://www.rollingstone.com/music/rss";
        } else if (clicked == 3) {
            rssUrl = "http://movieweb.com/rss/all-news/";
        } else if (clicked == 4) {
            rssUrl = "http://api.foxsports.com/v1/rss?partnerKey=zBaFxRyGKCfxBagJG9b8pqLyndmvo7UU";
        } else if (clicked == 5) {
            rssUrl = "http://feeds.bbci.co.uk/news/world/rss.xml";
        } else if (clicked == 6) {
            rssUrl = "http://www.cnbc.com/id/10001147/device/rss/rss.html";
        }

        new loadFeeds().execute(rssUrl);

        ListView listView = getListView();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                Intent intent = new Intent(getApplicationContext(), DescPage.class);
                String descPageUrl = ((TextView) view.findViewById(R.id.url)).getText().toString();
                String desc = ((TextView) view.findViewById(R.id.desc)).getText().toString();
                intent.putExtra("descPageUrl", descPageUrl);
                intent.putExtra("desc", desc);
                startActivity(intent);

            }
        });
    }

    public class loadFeeds extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... args) {

            String rssUrl = args[0];
            tags = parseRssFeeds.getRSSFeedItems(rssUrl);

            for (Tags tag : tags) {

                HashMap<String, String> hm = new HashMap<>();

                hm.put(TITLE, tag.getTitle());
                hm.put(LINK, tag.getLink());
                hm.put(PDATE, tag.getPubDate());
                hm.put(DESC, tag.getDescription());

                arrayList.add(hm);
            }

            runOnUiThread(new Runnable() {
                public void run() {

                    ListAdapter listAdapter = new SimpleAdapter(SecondActivity.this, arrayList, R.layout.custom_row,
                            new String[]{LINK, TITLE, PDATE, DESC}, new int[]{R.id.url, R.id.title, R.id.pubDate, R.id.desc});
                    setListAdapter(listAdapter);
                }
            });
            return null;
        }
    }
}