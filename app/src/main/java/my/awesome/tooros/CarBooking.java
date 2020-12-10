package my.awesome.tooros;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Build;

import android.content.SharedPreferences;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class CarBooking extends AppCompatActivity {


    RecyclerView recyclerView;


    TextView startdate,enddate,city,startime,endtime;

    ArrayList<CarBookingModel> carBookingModels = new ArrayList<CarBookingModel>();
    CarBookingAdapter carBookingAdapter;

    ProgressDialog progressDialog1;
    String HttpURL = "https://www.cakiweb.com/tooros/api/api.php";
    String finalResult1 ;
    JsonHttpParse jsonhttpParse = new JsonHttpParse();

    TextView cityname,start,end;
  int hour,min,hour1,min1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_booking);
        recyclerView=findViewById(R.id.bookingrecycler);

        recyclerView.setHasFixedSize(true);
       startime=findViewById(R.id.startt);
       endtime=findViewById(R.id.endt);
        startdate=findViewById(R.id.textView4);
        enddate=findViewById(R.id.textView7);
        city=findViewById(R.id.cityname);
//        recyclerView.setAdapter(carBookingAdapter);
        SharedPreferences sharedPreferences = CarBooking.this.getSharedPreferences("Date", MODE_PRIVATE);
        String stdate= sharedPreferences.getString("startdate",null);
        String end= sharedPreferences.getString("Enddate",null);
        String City= sharedPreferences.getString("city",null);
        if(stdate!=" "&& end !=" "&& City!=" "){
            startdate.setText(stdate);
            enddate.setText(end);
            city.setText(City);
        }


//        Intent intent=getIntent();
//       String city= intent.getStringExtra("city");
//       String startdate= intent.getStringExtra("startdate");
//       String enddate= intent.getStringExtra("enddate");


//       cityname=findViewById(R.id.cityname);cityname.setText(city);
//       start=findViewById(R.id.textView4);start.setText(startdate);
//       end=findViewById(R.id.textView7);end.setText(enddate);




        carBookingAdapter=new CarBookingAdapter(carBookingModels,CarBooking.this);
        recyclerView.setAdapter(carBookingAdapter);

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(CarBooking.this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);


        getSubSerivices("getAllavailCabs",City,stdate,end);


//        CarBookingModel carBookingModel=new CarBookingModel(R.drawable.hundaiimage,"Renault Kwid","Petrol","₹1500","5 seat","Automatic","5 baggage","BOOKED");
//        carBookingModels.add(carBookingModel);


//        CarBookingModel carBookingModel1=new CarBookingModel(R.drawable.hundaiimage,"Renault Kwid","Petrol","₹1500","5 seat","Automatic","5 baggage","BOOKED");
//       carBookingModels.add(carBookingModel1);

    }

    private void getSubSerivices(final String method, String city,  String startdate,  String enddate) {

        class OfferClass extends AsyncTask<String,Void,String> {


            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                progressDialog1 = ProgressDialog.show(CarBooking.this,"Loading Data",null,true,true);
            }

            @Override
            protected void onPostExecute(String httpResponseMsg) {

                super.onPostExecute(httpResponseMsg);

                progressDialog1.dismiss();

                // Toast.makeText(getContext(), httpResponseMsg, Toast.LENGTH_SHORT).show();

                if(httpResponseMsg.contains("200")){
                    //Toast.makeText(getContext(), httpResponseMsg, Toast.LENGTH_SHORT).show();

                    try {
                        JSONObject jsonObject = new JSONObject(httpResponseMsg);
                        JSONArray result = jsonObject.getJSONArray("result");
                        for (int i=0; i<result.length(); i++ ){
                            JSONObject ob=result.getJSONObject(i);

                            // Toast.makeText(FirstActivity.this, ob.getString("name"), Toast.LENGTH_SHORT).show();
//                            CarBookingModel history1=new CarBookingModel(R.drawable.hundaii,ob.getString("sub_service"),
//                                    ob.getString("sch_servie_id"),ob.getString("service_id"),ob.getString("service_price"),
//                                    ob.getString("img"));
//                            CarBookingModel carBookingModel=new CarBookingModel(R.drawable.hundaiimage,"Renault Kwid","Petrol",ob.getString("id"),"5 seat","Automatic","5 baggage","BOOKED");
//                            carBookingModels.add(carBookingModel);

                            CarBookingModel carBookingModel=new CarBookingModel(R.drawable.hundaiimage,ob.getString("car_nme")
                                    ,ob.getString("fuelType"),ob.getString("cost"),ob.getString("no_of_seat"),
                                    ob.getString("gearType"),ob.getString("no_of_baggage"),ob.getString("status"),ob.getString("weekendcost"),ob.getString("security"));
                            carBookingModels.add(carBookingModel);
                        }


//
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    recyclerView.setAdapter(carBookingAdapter);
                    carBookingAdapter.notifyDataSetChanged();



                }else{
                    JSONObject jsonObject = null;
                    try {
                        jsonObject = new JSONObject(httpResponseMsg);
                        String messege = jsonObject.getString("msg");
                        Toast.makeText(CarBooking.this, messege, Toast.LENGTH_SHORT).show();


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }

            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            protected String doInBackground(String... params) {


                //String jsonInputString="{\"method\":\"getAllavailCabs\",\"concatPdate\":\""+startdate+" 09:00"+"\",\"concatDdate\":\""+enddate+" 21:00"+"\",\"city\":1}";
                String jsonInputString="{\"method\":\"getAllavailCabs\",\"concatPdate\":\"2021-01-15 09:00\",\"concatDdate\":\"2021-01-16 21:00\",\"city\":1}";
               // String jsonInputString={"method":"getAllavailCabs","concatPdate":"2021-01-15 09:00","concatDdate":"2021-01-16 21:00","city":1};
                //String jsonInputString1="{\"method\":\"getAllsubService\",\"service_id\":\""+id+"\"}";

                finalResult1 = jsonhttpParse.postRequest(jsonInputString, HttpURL);

                return finalResult1;
            }
        }

        OfferClass offerClass = new OfferClass();

        offerClass.execute(method);




       // Toast.makeText(this, "", Toast.LENGTH_SHORT).show();

    }


    public void endTime(View view) {
        TimePickerDialog timePickerDialog=new TimePickerDialog(CarBooking.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                hour1=hourOfDay;
                min1=minute;
                String time1=hour1+":"+min1;
                SimpleDateFormat f24hours1=new SimpleDateFormat("HH:mm");
                try {
                    Date date1=f24hours1.parse(time1);
                    SimpleDateFormat f12hour1=new SimpleDateFormat("hh:mm aa");
                    endtime.setText(f12hour1.format(date1));
                    String et=endtime.getText().toString();
                    SharedPreferences sharedPreferences1 = CarBooking.this.getSharedPreferences("Date", MODE_PRIVATE);
                    final SharedPreferences.Editor myEdit = sharedPreferences1.edit();
                    myEdit.putString("endtime",""+et);
                    myEdit.apply();
                    // Toast.makeText(CarBooking.this, ""+sd, Toast.LENGTH_SHORT).show();
                    int dif=hour1-hour;
                    Toast.makeText(CarBooking.this, ""+dif, Toast.LENGTH_SHORT).show();
                    SharedPreferences sharedPreferences2 = CarBooking.this.getSharedPreferences("Date", MODE_PRIVATE);
                    final SharedPreferences.Editor myEdit2 = sharedPreferences2.edit();
                    myEdit2.putInt("dif",dif);
                    myEdit2.apply();
                } catch (ParseException e) {
                    // Toast.makeText(CarBooking.this, "hi", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        },12,0,false
        );
        timePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        timePickerDialog.updateTime(hour1,min1);
        timePickerDialog.show();

    }


    public void starttime(View view) {
        TimePickerDialog timePickerDialog=new TimePickerDialog(CarBooking.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                hour=hourOfDay;
                min=minute;

                String time1=hour+":"+min;
                SimpleDateFormat f24hours=new SimpleDateFormat("HH:mm");
                try {
                    Date date=f24hours.parse(time1);
                    SimpleDateFormat f12hour=new SimpleDateFormat("hh:mm aa");
                    startime.setText(f12hour.format(date));
                    String st=startime.getText().toString();
                    SharedPreferences sharedPreferences1 = CarBooking.this.getSharedPreferences("Date", MODE_PRIVATE);
                    final SharedPreferences.Editor myEdit = sharedPreferences1.edit();
                    myEdit.putString("starttime",""+st);
                    myEdit.apply();
                    //Toast.makeText(CarBooking.this, ""+startime, Toast.LENGTH_SHORT).show();
                } catch (ParseException e) {
                   // Toast.makeText(CarBooking.this, "hi", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        },12,0,false
        );
        timePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        timePickerDialog.updateTime(hour,min);
        timePickerDialog.show();

    }



}
