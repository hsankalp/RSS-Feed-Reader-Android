package com.example.sankalpheranjal.feed4me;

import android.content.Intent;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import java.util.Locale;

public class DescPage extends AppCompatActivity {

    WebView webview;
    TextToSpeech tts;
    int t;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webpage);

        Intent intent = getIntent();
        String descPageUrl = intent.getStringExtra("descPageUrl");

        webview = (WebView) findViewById(R.id.webView);
        webview.loadUrl(descPageUrl);
        webview.setWebViewClient(new DisPlayWebView());


        tts = new TextToSpeech(DescPage.this, new TextToSpeech.OnInitListener() {

            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    t = tts.setLanguage(Locale.ENGLISH);
                } else {
                    Toast.makeText(getApplicationContext(), "Not Supported", Toast.LENGTH_LONG).show();
                }
            }

        });

    }

    public void buttonClicked(View view) {
        switch (view.getId()) {
            case R.id.button:
                if (t == TextToSpeech.LANG_NOT_SUPPORTED || t == TextToSpeech.LANG_MISSING_DATA) {
                    Toast.makeText(getApplicationContext(), "Not Supported", Toast.LENGTH_LONG).show();
                } else {
                    Intent in = getIntent();
                    String desc = in.getStringExtra("desc");
                    tts.speak(desc, TextToSpeech.QUEUE_FLUSH, null);
                }
                break;

            case R.id.button2:
                if (tts != null) {
                    tts.stop();
                }

                break;
        }
    }

    private class DisPlayWebView extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
    }
}