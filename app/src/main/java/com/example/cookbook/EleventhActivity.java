package com.example.cookbook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

public class EleventhActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eleventh);
        Intent calleighth = getIntent();
        int pos = calleighth.getIntExtra("position",0);
        String x = calleighth.getStringExtra("item");
        TextView textView = (TextView) findViewById(R.id.textView11);
        textView.setText(x);
        final ImageView imageView = (ImageView) findViewById(R.id.imageview_photo);
        WebView webView = (WebView)findViewById(R.id.webview);
        webView.setWebViewClient(new WebViewClient());
        if(pos==0){
            webView.loadUrl("file:///android_asset/chickenmasala.html");
            imageView.setBackgroundResource(R.drawable.chickenmasala);
        }
        else if(pos==1){
            webView.loadUrl("file:///android_asset/roganjosh.html");
            imageView.setBackgroundResource(R.drawable.roganjosh);
        }
        else if(pos==2){
            webView.loadUrl("file:///android_asset/butterchicken.html");
            imageView.setBackgroundResource(R.drawable.butterchicken);
        }
        else if(pos==3){
            webView.loadUrl("file:///android_asset/fishcurry.html");
            imageView.setBackgroundResource(R.drawable.fishcurry);
        }
        else if(pos==4){
            webView.loadUrl("file:///android_asset/murghbiryani.html");
            imageView.setBackgroundResource(R.drawable.murghbiryani);
        }
    }
}


