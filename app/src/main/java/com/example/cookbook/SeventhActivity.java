package com.example.cookbook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

public class SeventhActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seventh);
        Intent callfourth = getIntent();
        int pos = callfourth.getIntExtra("position",0);
        String x = callfourth.getStringExtra("item");
        TextView textView = (TextView) findViewById(R.id.textView7);
        textView.setText(x);
        final ImageView imageView = (ImageView) findViewById(R.id.imageview_photo);
        WebView webView = (WebView)findViewById(R.id.webview);
        webView.setWebViewClient(new WebViewClient());
        if(pos==0){
            textView.setTextSize(22);
            webView.loadUrl("file:///android_asset/handipaneer.html");
            imageView.setBackgroundResource(R.drawable.handipaneer);
        }
        else if(pos==1){
            textView.setTextSize(23);
            webView.loadUrl("file:///android_asset/dalmakhani.html");
            imageView.setBackgroundResource(R.drawable.dalmakhani);
        }
        else if(pos==2){
            webView.loadUrl("file:///android_asset/vegpulao.html");
            imageView.setBackgroundResource(R.drawable.vegpulao);
        }
        else if(pos==3){
            webView.loadUrl("file:///android_asset/malaikofta.html");
            imageView.setBackgroundResource(R.drawable.malaikofta);
        }
        else if(pos==4){
            webView.loadUrl("file:///android_asset/paneermakhani.html");
            imageView.setBackgroundResource(R.drawable.paneermakhani);
        }
    }
}
