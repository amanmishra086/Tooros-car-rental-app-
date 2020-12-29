package my.awesome.tooros;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class TermsAndCondition extends AppCompatActivity {

    WebView web;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_and_condition);

        Toast.makeText(this, "Loading...", Toast.LENGTH_SHORT).show();


        web = findViewById(R.id.web);
        web.loadUrl("https://tooros.in/terms-condition.php");
        WebSettings webSettings=web.getSettings();
        webSettings.setAllowContentAccess(true);

        webSettings.setJavaScriptEnabled(true);
        web.setWebViewClient(new WebViewClient());
    }
}
