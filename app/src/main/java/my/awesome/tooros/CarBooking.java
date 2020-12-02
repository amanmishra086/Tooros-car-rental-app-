package my.awesome.tooros;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class CarBooking extends AppCompatActivity {
RecyclerView recyclerView;
TextView startdate,enddate,city;
    ArrayList<CarBookingModel> carBookingModels = new ArrayList<CarBookingModel>();
    CarBookingAdapter carBookingAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_booking);
        recyclerView=findViewById(R.id.bookingrecycler);
startdate=findViewById(R.id.textView4);
enddate=findViewById(R.id.textView7);
city=findViewById(R.id.cityname);
        carBookingAdapter=new CarBookingAdapter(carBookingModels,CarBooking.this);
      CarBookingModel carBookingModel=new CarBookingModel(R.drawable.hundaiimage,"Renault Kwid","Petrol","₹1500","5 seat","Automatic","5 baggage","BOOKED");
       carBookingModels.add(carBookingModel);
        CarBookingModel carBookingModel1=new CarBookingModel(R.drawable.hundaiimage,"Renault Kwid","Petrol","₹1500","5 seat","Automatic","5 baggage","BOOKED");
        carBookingModels.add(carBookingModel1);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(CarBooking.this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(carBookingAdapter);
        SharedPreferences sharedPreferences = CarBooking.this.getSharedPreferences("Date", MODE_PRIVATE);
       String stdate= sharedPreferences.getString("startdate",null);
       String end= sharedPreferences.getString("Enddate",null);
        String City= sharedPreferences.getString("city",null);
        if(stdate!=" "&& end !=" "&& City!=" "){
            startdate.setText(stdate);
            enddate.setText(end);
            city.setText(City);
        }
       // Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
    }




}
