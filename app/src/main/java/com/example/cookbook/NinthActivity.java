package com.example.cookbook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

public class NinthActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ninth);
        Intent calleighth = getIntent();
        int pos = calleighth.getIntExtra("position",0);
        String x = calleighth.getStringExtra("item");
        TextView textView = (TextView) findViewById(R.id.textView9);
        textView.setText(x);
        final ImageView imageView = (ImageView) findViewById(R.id.imageview_photo);
        WebView webView = (WebView)findViewById(R.id.webview);
        webView.setWebViewClient(new WebViewClient());
        if(pos==0){
            webView.loadUrl("file:///android_asset/omlette.html");
            imageView.setBackgroundResource(R.drawable.omlette);
        }
        else if(pos==1){
            webView.loadUrl("file:///android_asset/chickensandwich.html");
            imageView.setBackgroundResource(R.drawable.chickensandwich);
        }
        else if(pos==2){
            webView.loadUrl("file:///android_asset/eggbhurji.html");
            imageView.setBackgroundResource(R.drawable.eggbhurji);
        }
        else if(pos==3){
            webView.loadUrl("file:///android_asset/chickensalad.html");
            imageView.setBackgroundResource(R.drawable.chickensalad);
        }
        else if(pos==4){
            webView.loadUrl("file:///android_asset/eggparantha.html");
            imageView.setBackgroundResource(R.drawable.eggparantha);
        }
    }
}
