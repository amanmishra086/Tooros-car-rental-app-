package my.awesome.tooros;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class CarBooking extends AppCompatActivity {
RecyclerView recyclerView;
    ArrayList<CarBookingModel> carBookingModels = new ArrayList<CarBookingModel>();
    CarBookingAdapter carBookingAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_booking);
        recyclerView=findViewById(R.id.bookingrecycler);

        carBookingAdapter=new CarBookingAdapter(carBookingModels,CarBooking.this);
      CarBookingModel carBookingModel=new CarBookingModel(R.drawable.hundaiimage,"Renault Kwid","Petrol","₹1500","5 seat","Automatic","5 baggage","BOOKED");
       carBookingModels.add(carBookingModel);
        CarBookingModel carBookingModel1=new CarBookingModel(R.drawable.hundaiimage,"Renault Kwid","Petrol","₹1500","5 seat","Automatic","5 baggage","BOOKED");
        carBookingModels.add(carBookingModel1);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(CarBooking.this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(carBookingAdapter);
    }




}
