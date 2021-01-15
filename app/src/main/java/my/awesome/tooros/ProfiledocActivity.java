package my.awesome.tooros;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class ProfiledocActivity extends AppCompatActivity {

    WebView web;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profiledoc);

        Intent intent=getIntent();
        String url=intent.getExtras().getString("url");

        Toast.makeText(this, "Loading...", Toast.LENGTH_SHORT).show();

        web = findViewById(R.id.web);
        web.getSettings().setLoadWithOverviewMode(true);
        web.getSettings().setUseWideViewPort(true);
        web.loadUrl(url);
        WebSettings webSettings=web.getSettings();
        webSettings.setAllowContentAccess(true);

        webSettings.setJavaScriptEnabled(true);
        web.setWebViewClient(new WebViewClient());

    }
}
