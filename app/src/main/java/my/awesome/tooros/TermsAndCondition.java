package my.awesome.tooros;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

public class TermsAndCondition extends AppCompatActivity {

    WebView web;
    TextView tv1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.terms_app);
       tv1=findViewById(R.id.cr);
       String str="1.1.The Services are available only to persons who can enter legally binding contracts under Indian Contract Act, 1872 and are above the age of 21 years, holding a valid drivers license, without previous criminal records and without any restrictions on driving due to any medical conditions or any other restrictions imposed under any law including but not limiting to the Motor Vehicles Act, 1988, as amended from time to time.\n" +
               "\n" +
               "1.2. ORIGINAL Aadhaaris mandatory at the time of pick-up.\n" +
               "\n" +
               "1.3.Local IDs, Students Must carry their Job/Collage/institution ID Card for the Address Proof. Without absence of Institution/Job/College ID, Local IDs are not eligible to rent a car under Tooros Platform.\n" +
               "\n" +
               "1.4. Upload your Driving license & ,Adhar Card or Other ID & Live Photo for the Pre-verification of your documents.\n" +
               "\n" +
               "1.5.In the absence of any Valid Documents the booking will be treated as Cancelled and Rs.500/- will be deducted from Total amount Paid.\n" +
               "\n" +
               "1.6.Minimum 24 years age is required for Premium Range vehicles.\n" +
               "\n" +
               "1.7. Persons who are not-competent to contract within the meaning of the Indian Contract Act, 1872 including minors, un-discharged insolvents, and person of unsound mind are not eligible to use the Platform or avail the Services.\n" +
               "\n" +
               "1.8. Any person under the age of 21 shall not register as a User of the Platform and shall not transact on or use the Platform.\n" +
               "\n" +
               "1.9. Toorosreserves the right to terminate any Users membership and/or refuse to provide such User with access to the Platform and/or initiate appropriate action against any User if it is brought to Toorosnotice or if it is discovered that such User is not eligible to use the Platform and/or avail the Services.";
       tv1.setText(str);




    }

    public void adview(View view) {
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
