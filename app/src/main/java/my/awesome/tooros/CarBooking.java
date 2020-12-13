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


    TextView startdate,enddate,city,startime1,endtime1;

    ArrayList<CarBookingModel> carBookingModels = new ArrayList<CarBookingModel>();
    CarBookingAdapter carBookingAdapter;

    ProgressDialog progressDialog1;
    String HttpURL = "https://www.cakiweb.com/tooros/api/api.php";
    String finalResult1 ;
    JsonHttpParse jsonhttpParse = new JsonHttpParse();

    TextView cityname,start,end;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_booking);
        recyclerView=findViewById(R.id.bookingrecycler);

        recyclerView.setHasFixedSize(true);
       startime1=findViewById(R.id.startt);
       endtime1=findViewById(R.id.endt);
        startdate=findViewById(R.id.textView4);
        enddate=findViewById(R.id.textView7);
        city=findViewById(R.id.cityname);
//        recyclerView.setAdapter(carBookingAdapter);
        SharedPreferences sharedPreferences = CarBooking.this.getSharedPreferences("Date", MODE_PRIVATE);
        String stdate= sharedPreferences.getString("startdate",null);
        String end= sharedPreferences.getString("Enddate",null);
        String City= sharedPreferences.getString("city",null);
        final String startime=sharedPreferences.getString("starttime",null);
        final String endtime=sharedPreferences.getString("endtime",null);
        if(stdate!=" "&& end !=" "&& City!=" "&& startime!=" " && endtime!=" "){
            startdate.setText(stdate);
            enddate.setText(end);
            city.setText(City);
            startime1.setText(startime);
            endtime1.setText(endtime);
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

    private void getSubSerivices(final String method, String city, final String startdate, final String enddate) {

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
                                    ob.getString("gearType"),ob.getString("no_of_baggage"),ob.getString("status"),
                                    ob.getString("weekendcost"),ob.getString("security"),ob.getString("id"));
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

                String concatPdate=""+startdate+" 09:00";
                String concatDdate=""+enddate+" 21:00";

                //String jsonInputString="{\"method\":\"getAllavailCabs\",\"concatPdate\":\""+startdate+" 09:00"+"\",\"concatDdate\":\""+enddate+" 21:00"+"\",\"city\":1}";
                String jsonInputString="{\"method\":\"getAllavailCabs\",\"concatPdate\":\""+concatPdate+"\",\"concatDdate\":\""+concatDdate+"\",\"city\":1}";
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






}
