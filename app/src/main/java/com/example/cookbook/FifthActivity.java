package com.example.cookbook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

public class FifthActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fifth);
        Intent callfourth = getIntent();
        int pos = callfourth.getIntExtra("position",0);
        String x = callfourth.getStringExtra("item");
        TextView textView = (TextView) findViewById(R.id.textView5);
        textView.setText(x);
        final ImageView imageView = (ImageView) findViewById(R.id.imageview_photo);
        WebView webView = (WebView)findViewById(R.id.webview);
        webView.setWebViewClient(new WebViewClient());
        if(pos==0){
            webView.loadUrl("file:///android_asset/cholebhature.html");
            imageView.setBackgroundResource(R.drawable.chanabhatura);
        }
        else if(pos==1){
            webView.loadUrl("file:///android_asset/alooparantha.html");
            imageView.setBackgroundResource(R.drawable.alooparantha);
        }
        else if(pos==2){
            webView.loadUrl("file:///android_asset/ravadosa.html");
            imageView.setBackgroundResource(R.drawable.ravadosa);
        }
        else if(pos==3){
            webView.loadUrl("file:///android_asset/poha.html");
            imageView.setBackgroundResource(R.drawable.poha);
        }
        else if(pos==4){
            webView.loadUrl("file:///android_asset/vegsandwich.html");
            imageView.setBackgroundResource(R.drawable.vegsandwich);
        }
    }
}
