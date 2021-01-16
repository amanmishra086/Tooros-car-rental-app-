package my.awesome.tooros;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class HelpAndSupport extends AppCompatActivity {

    WebView web;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_and_support);

       // Toast.makeText(this, "Loading...", Toast.LENGTH_SHORT).show();

      //  web = findViewById(R.id.web);
      //  web.loadUrl("https://tooros.in/car-rental-bhubaneswar.php");
      //  WebSettings webSettings=web.getSettings();
      //  webSettings.setAllowContentAccess(true);

      //  webSettings.setJavaScriptEnabled(true);
       // web.setWebViewClient(new WebViewClient());

    }
}
