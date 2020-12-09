package my.awesome.tooros;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class PaymentPage extends AppCompatActivity {
TextView gst,total,basefair,coupondiscount,picupcharges,weekdaychages,totalcharges,startdate,enddate,timeduration,carname,startt,endt;
EditText Couponcode;
Button book,apply;
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
        startt=findViewById(R.id.starttime);
        endt=findViewById(R.id.endtime);
        picupcharges=findViewById(R.id.delivery);
        coupondiscount=findViewById(R.id.discount);
        gst=findViewById(R.id.gst);
        total=findViewById(R.id.totalamount);
        Couponcode=findViewById(R.id.couponcode);
        carname=findViewById(R.id.carname);
        book=findViewById(R.id.book);
        apply=findViewById(R.id.apply);
       // Intent intent=getIntent();
        SharedPreferences sharedPreferences = PaymentPage.this.getSharedPreferences("Date", MODE_PRIVATE);
        String stdate= sharedPreferences.getString("startdate",null);
        String end= sharedPreferences.getString("Enddate",null);
        String startime=sharedPreferences.getString("starttime",null);
        String endtime=sharedPreferences.getString("endtime",null);
        int duration=sharedPreferences.getInt("dif",0);
        String dur=String.valueOf(duration);
      //  timeduration.append(" hrs");
        if(stdate!=" "&& end !=" "&&startime!=null&&endtime!=null&&dur!=null){
            startdate.setText(stdate);
            enddate.setText(end);
            startt.setText(startime);
            endt.setText(endtime);
           timeduration.setText(""+dur);
           timeduration.append(" hrs");
        }else{

        }
        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //take coupon code and recive amount from api
            }
        });
        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //take all input send to api and proceed for payment
            }
        });

    }
}
