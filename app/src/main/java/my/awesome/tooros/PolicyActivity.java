package my.awesome.tooros;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class PolicyActivity extends AppCompatActivity {




    WebView web;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_policy);


        Toast.makeText(this, "Loading...", Toast.LENGTH_SHORT).show();


        web = findViewById(R.id.web);
        web.loadUrl("https://tooros.in/page.php?page=4");
        WebSettings webSettings=web.getSettings();
        webSettings.setAllowContentAccess(true);

        webSettings.setJavaScriptEnabled(true);
        web.setWebViewClient(new WebViewClient());




    }


}
