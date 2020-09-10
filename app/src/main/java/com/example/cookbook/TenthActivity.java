package com.example.cookbook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

public class TenthActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tenth);
        Intent calleighth = getIntent();
        int pos = calleighth.getIntExtra("position",0);
        String x = calleighth.getStringExtra("item");
        TextView textView = (TextView) findViewById(R.id.textView10);
        textView.setText(x);
        final ImageView imageView = (ImageView) findViewById(R.id.imageview_photo);
        WebView webView = (WebView)findViewById(R.id.webview);
        webView.setWebViewClient(new WebViewClient());
        if(pos==0){
            webView.loadUrl("file:///android_asset/chickendopyaza.html");
            imageView.setBackgroundResource(R.drawable.chickendopyaza);
        }
        else if(pos==1){
            webView.loadUrl("file:///android_asset/eggfriedrice.html");
            imageView.setBackgroundResource(R.drawable.eggfriedrice);
        }
        else if(pos==2){
            webView.loadUrl("file:///android_asset/muttonfry.html");
            imageView.setBackgroundResource(R.drawable.muttonfry);
        }
        else if(pos==3){
            webView.loadUrl("file:///android_asset/fishbiryani.html");
            imageView.setBackgroundResource(R.drawable.fishbiryani);
        }
        else if(pos==4){
            webView.loadUrl("file:///android_asset/keemapulav.html");
            imageView.setBackgroundResource(R.drawable.keemapulav);
        }
    }
}

