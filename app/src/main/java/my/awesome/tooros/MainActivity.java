package my.awesome.tooros;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

//import com.example.tooros.R;

public class MainActivity extends AppCompatActivity {

    WebView web;
    AlertDialog.Builder alert;

    @SuppressLint("JavascriptInterface")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        alert = new AlertDialog.Builder(this);



        web = findViewById(R.id.web);
          web.loadUrl("https://www.tooros.in");
        WebSettings webSettings=web.getSettings();
        webSettings.setAllowContentAccess(true);

        webSettings.setJavaScriptEnabled(true);
        web.setWebViewClient(new WebViewClient());

        web.addJavascriptInterface("PaymentInterface", String.valueOf(new PaymentInterface() {
            @Override
            public void success(String data) {
                Toast.makeText(MainActivity.this, "sssssssssssssssssssssssssssssssssssssssssss", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void error(String data) {
                Toast.makeText(MainActivity.this, "eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee", Toast.LENGTH_SHORT).show();
            }
        }));

        //demo1TrySpecificURL();
        alert.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();

            }
        });
        alert.setNegativeButton("no", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

    }

//    private void demo1TrySpecificURL(){
//        // webview=(WebView)findViewById(R.id.webview1);
//        web.getSettings().setJavaScriptEnabled(true);
//        //webview.setWebViewClient(new MyWebClient(txtMsg, "https://my.gediz.edu.tr/"));
//        web.loadUrl("http://yppschool.com/student/index.php/login");
//
//        web.setWebViewClient(new WebViewClient(){
//
//            public void onPageFinished(WebView view, String url){
//
//
//
//                view.loadUrl("javascript:document.getElementsByName('student_id').value = '5'");
//                //view.loadUrl("javascript:document.getElementsByName('password')[0].value = 'password'");
//
//
//                view.loadUrl("javascript:document.forms['submitBtn'].submit()");
//            }
//
//        });
//    }
    public void onBackPressed() {
        //  super.onBackPressed();
        if(web.canGoBack()){
            web.goBack();
        }
        else{
            AlertDialog dialog=alert.create();
            dialog.setMessage("are you sure want to exit");
            dialog.show();
            //  finish();
        }
    }
}

