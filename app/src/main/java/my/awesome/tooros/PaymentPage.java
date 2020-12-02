package my.awesome.tooros;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class PaymentPage extends AppCompatActivity {
TextView gst,total,basefair,coupondiscount,picupcharges,weekdaychages,totalcharges,startdate,enddate,timeduration,carname;
EditText Couponcode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_page);
        startdate=findViewById(R.id.startdate);
        enddate=findViewById(R.id.enddate);
        weekdaychages=findViewById(R.id.textView21);
        totalcharges=findViewById(R.id.textView22);
        timeduration=findViewById(R.id.textView12);
        basefair=findViewById(R.id.basefare);
        picupcharges=findViewById(R.id.delivery);
        coupondiscount=findViewById(R.id.discount);
        gst=findViewById(R.id.gst);
        total=findViewById(R.id.totalamount);
        Couponcode=findViewById(R.id.couponcode);
        carname=findViewById(R.id.textView2);
        SharedPreferences sharedPreferences = PaymentPage.this.getSharedPreferences("Date", MODE_PRIVATE);
        String stdate= sharedPreferences.getString("startdate",null);
        String end= sharedPreferences.getString("Enddate",null);

        if(stdate!=" "&& end !=" "){
            startdate.setText(stdate);
            enddate.setText(end);

        }
    }
}
