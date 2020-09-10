package com.example.cookbook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

public class SixthActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sixth);
        Intent callfourth = getIntent();
        int pos = callfourth.getIntExtra("position",0);
        String x = callfourth.getStringExtra("item");
        TextView textView = (TextView) findViewById(R.id.textView6);
        textView.setText(x);
        final ImageView imageView = (ImageView) findViewById(R.id.imageview_photo);
        WebView webView = (WebView)findViewById(R.id.webview);
        webView.setWebViewClient(new WebViewClient());
        if(pos==0){
            textView.setTextSize(22);
            webView.loadUrl("file:///android_asset/paneertamatar.html");
            imageView.setBackgroundResource(R.drawable.paneertamatar);
        }
        else if(pos==1){
            textView.setTextSize(23);
            webView.loadUrl("file:///android_asset/kadhi.html");
            imageView.setBackgroundResource(R.drawable.kadhi);
        }
        else if(pos==2){
            webView.loadUrl("file:///android_asset/dal.html");
            imageView.setBackgroundResource(R.drawable.dal);
        }
        else if(pos==3){
            webView.loadUrl("file:///android_asset/dumaloo.html");
            imageView.setBackgroundResource(R.drawable.dumaloo);
        }
        else if(pos==4){
            webView.loadUrl("file:///android_asset/curdrice.html");
            imageView.setBackgroundResource(R.drawable.curdrice);
        }
    }
}
