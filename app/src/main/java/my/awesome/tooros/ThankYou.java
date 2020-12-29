package my.awesome.tooros;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class ThankYou extends AppCompatActivity {

    TextView invoiceText,startdate,starttime,enddate,endtime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thank_you);

        invoiceText=findViewById(R.id.invoice);
        startdate=findViewById(R.id.startdate);
        enddate=findViewById(R.id.enddate);
        starttime=findViewById(R.id.starttime);
        endtime=findViewById(R.id.endtime);

        Intent intent=getIntent();
        String invoice=intent.getExtras().getString("invoice");


        invoiceText.append(""+invoice);
        startdate.setText(""+intent.getExtras().getString("startdate"));
        enddate.setText(""+intent.getExtras().getString("enddate"));
        starttime.setText(""+intent.getExtras().getString("starttime"));
        endtime.setText(""+intent.getExtras().getString("endtime"));



    }
    @Override
    public void onBackPressed() {
       startActivity(new Intent(this,CitySelectionActivity.class));
    }


}
