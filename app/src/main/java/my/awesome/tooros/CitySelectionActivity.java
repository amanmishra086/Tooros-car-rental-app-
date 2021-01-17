package my.awesome.tooros;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Downloader;
import com.squareup.picasso.Picasso;
//import com.squareup.picasso.Request;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class CitySelectionActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener  {

    String HttpURL = "https://www.cakiweb.com/tooros/api/api.php";
    String finalResult ;
    ProgressDialog progressDialog;
    ProgressDialog progressDialog2;
    ProgressDialog progressDialog3;
    JsonHttpParse jsonhttpParse = new JsonHttpParse();

    TextView username;

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    androidx.appcompat.widget.Toolbar toolbar;

    Spinner spinner;
    String city;
    Calendar myEndCalendar;
    Calendar myStartCalendar;
    TextView startdate,enddate;String startdateSelected="",enddateSelected="";
    DatePickerDialog.OnDateSetListener startdatelistener;
    DatePickerDialog.OnDateSetListener enddatelistener;

   RecyclerView senitization_recycler;
    ArrayList<Guidlines_model> guidlines_models = new ArrayList<Guidlines_model>();
Guidlines_adapter guidlines_adapter;
//
RecyclerView offer_recycler;
    ArrayList<Guidlines_model> offer_model_arraylist = new ArrayList<Guidlines_model>();
    Offer_adapter offer_adapter;

 //
 int hour=0,min,hour1=0,min1,daydif=0,monthdif=0,yeardif=0;
    TextView startime,endtime;
    String st="",et="";
    //this is city adapter
    CityAdapter cityAdapter;
 ArrayList<ModelCity> modelcityss=new ArrayList<>();
    ModelCity modelCity;
   // List<String> list=new ArrayList<String>() ;

    TextView carname,fuel,gear,baggage,totalseat,startdatebooking,enddatebooking;
    ImageView carimagebooking;LinearLayout bookinglinearLayout;String userid;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_selection);
        startime=findViewById(R.id.startt);
        endtime=findViewById(R.id.endt);
        spinner=findViewById(R.id.select_city);

        carname=findViewById(R.id.carname);
        fuel=findViewById(R.id.fueltype);
        gear=findViewById(R.id.geartype);
        baggage=findViewById(R.id.baggage);
        totalseat=findViewById(R.id.totalseat);
        startdatebooking=findViewById(R.id.startdate);
        enddatebooking=findViewById(R.id.enddate);
        carimagebooking=findViewById(R.id.carimage);
        bookinglinearLayout=findViewById(R.id.bookingstatus);



        offer_recycler=findViewById(R.id.offer_recycler);

        drawerLayout=findViewById(R.id.drawer_layout);
        navigationView=findViewById(R.id.nav_view);
        toolbar=findViewById(R.id.toolbar);



        startdate=findViewById(R.id.startDate);
        enddate=findViewById(R.id.endDate);


        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }

        //



        boolean online=isOnline();
        if(!online){
            AlertDialog.Builder builder =new AlertDialog.Builder(this);
            builder.setCancelable(false);
            builder.setTitle("No internet Connection");
            builder.setMessage("Please turn on internet connection and reopen the application");
//            builder.setNegativeButton("Refresh", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    onCreate(savedInstanceState);
//                }
//            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }





        cityAdapter=new CityAdapter(CitySelectionActivity.this,R.layout.city,modelcityss);
        spinner.setAdapter(cityAdapter);







        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        username= (TextView) headerView.findViewById((R.id.user_name));
        SharedPreferences sharedPreferences2 = CitySelectionActivity.this.getSharedPreferences("MySharedPref2", MODE_PRIVATE);
        if (sharedPreferences2 != null) {
            username.setText(sharedPreferences2.getString("Name", null));
            userid=sharedPreferences2.getString("userid",null);


        }

        ServicesFunction("getAlllocation");//add service name
        gettingOffersFunction("getAlloffer");
        if(userid!=null){
            final int res=0;
            upCommingBookingInfo("upCommingBookingInfo",Integer.parseInt(userid),res);
        }else{
            bookinglinearLayout.setVisibility(View.GONE);
        }



        setSupportActionBar(toolbar);

        //hide or show items
        SharedPreferences shared = getSharedPreferences("loginOrNot", MODE_PRIVATE);
        String info = (shared.getString("info", ""));
       // String name = (shared.getString("username", ""));
        Menu menu2 = navigationView.getMenu();
        if(info.equals("yes")){
            //username.setText(name);
            menu2.findItem(R.id.nav_login).setVisible(false);
            menu2.findItem(R.id.nav_SignUp).setVisible(false);
            menu2.findItem(R.id.nav_logout).setVisible(true);
            menu2.findItem(R.id.nav_profile).setVisible(true);

        }else{
           // username.setText(name);
            menu2.findItem(R.id.nav_logout).setVisible(false);
            menu2.findItem(R.id.nav_profile).setVisible(false);
            menu2.findItem(R.id.nav_booking).setVisible(false);
        }


        navigationView.setItemIconTintList(null);

        getSupportActionBar().setDisplayShowTitleEnabled(false);

         navigationView.bringToFront();

        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.app_name,R.string.app_name);
        toggle.getDrawerArrowDrawable().setColor(getColor(R.color.white));
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        navigationView.setCheckedItem(R.id.nav_home);



         myStartCalendar = Calendar.getInstance();
         myEndCalendar = Calendar.getInstance();



         startdatelistener = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {

                // TODO Auto-generated method stub
                myStartCalendar.set(Calendar.YEAR, year);
                myStartCalendar.set(Calendar.MONTH, monthOfYear);
                myStartCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "yyyy-MM-dd";//In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                 startdateSelected=sdf.format(myStartCalendar.getTime());

                startdate.setText(startdateSelected);

               //  Toast.makeText(CitySelectionActivity.this, ""+dateSelected, Toast.LENGTH_SHORT).show();
                SharedPreferences sharedPreferences = CitySelectionActivity.this.getSharedPreferences("Date", MODE_PRIVATE);
                SharedPreferences.Editor myEdit = sharedPreferences.edit();
                myEdit.putString("startdate",""+startdateSelected);

                myEdit.apply();

               // Toast.makeText(CitySelectionActivity.this,sdf.format(myStartCalendar.getTime()) , Toast.LENGTH_SHORT).show();
            }
        };

         enddatelistener = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                String getstartdate=startdateSelected;
                String getfrom[]=getstartdate.split("-");
                int dayOfMonth1=Integer.parseInt(getfrom[2]);
                int month1=Integer.parseInt(getfrom[1]);
                int year1=Integer.parseInt(getfrom[0]);
                myEndCalendar.set(Calendar.YEAR, year);
                myEndCalendar.set(Calendar.MONTH, monthOfYear);
                myEndCalendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);


                String myFormat = "yyyy-MM-dd"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);


                enddateSelected=sdf.format(myEndCalendar.getTime());
                String getenddate=enddateSelected;
                String getfrom2[]=getenddate.split("-");
                int dayOfMonth2=Integer.parseInt(getfrom2[2]);
                int mont2=Integer.parseInt(getfrom2[1]);
                int year2=Integer.parseInt(getfrom2[0]);
            daydif=dayOfMonth2-dayOfMonth1;
            monthdif=mont2-month1;
            yeardif=year2-year1;
              //  Toast.makeText(CitySelectionActivity.this, ""+daydif+""+monthdif+""+""+yeardif, Toast.LENGTH_SHORT).show();
                    enddate.setText(enddateSelected);

                //String dateSelected=sdf.format(myEndCalendar.getTime());

              //  enddate.setText(enddateSelected);
                SharedPreferences sharedPreferences = CitySelectionActivity.this.getSharedPreferences("Date", MODE_PRIVATE);
                SharedPreferences.Editor myEdit = sharedPreferences.edit();
                myEdit.putString("Enddate",""+enddateSelected);
                myEdit.apply();


                //Toast.makeText(CitySelectionActivity.this,sdf.format(myEndCalendar.getTime()) , Toast.LENGTH_SHORT).show();
            }


        };

        //we have to fetch image here and set on guidline models




        offer_adapter=new Offer_adapter(offer_model_arraylist,CitySelectionActivity.this);
        offer_recycler.setAdapter(offer_adapter);
        LinearLayoutManager linearLayoutManager1=new LinearLayoutManager(CitySelectionActivity.this,RecyclerView.HORIZONTAL,false);
        offer_recycler.setLayoutManager(linearLayoutManager1);

//        Guidlines_model offer2=new Guidlines_model(R.drawable.offertooros);
//        offer_model_arraylist.add(offer2);







    }

    private void upCommingBookingInfo(final String method, final int user_id,  final int res) {

        class UserLoginClass2 extends AsyncTask<String,Void,String> {


            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                progressDialog2 = ProgressDialog.show(CitySelectionActivity.this,"Loading...",null,true,true);
                progressDialog2.setCancelable(false);
            }

            @Override
            protected void onPostExecute(String httpResponseMsg) {

                super.onPostExecute(httpResponseMsg);

                progressDialog2.dismiss();

                JSONObject jsonObject2 = null;
                try {
                    jsonObject2 = new JSONObject(httpResponseMsg);
                    String status = jsonObject2.getString("status");

                    if(status.equals("200")){

                        JSONObject jsonObject =new JSONObject(jsonObject2.getString("result"));
                        carname.setText(jsonObject.getString("booked_car"));
                        fuel.setText(jsonObject.getString("fuel_type"));
                        gear.setText(jsonObject.getString("gear_type"));
                        baggage.setText(jsonObject.getString("no_of_baggage")+" Baggage");
                        totalseat.setText(jsonObject.getString("no_of_seat")+" Seat");
                        startdatebooking.setText(jsonObject.getString("bookFrom"));
                        enddatebooking.setText(jsonObject.getString("bookTo"));

                        String carurl=jsonObject.getString("car_img");
                        Picasso.with(CitySelectionActivity.this).load(carurl.replace("http","https")).fit().centerInside().into(carimagebooking);
                       // Toast.makeText(CitySelectionActivity.this, "booking status", Toast.LENGTH_SHORT).show();



                    }else{

                        bookinglinearLayout.setVisibility(View.GONE);

//                        String messege = jsonObject2.getString("msg");
//                        Toast.makeText(CitySelectionActivity.this, messege, Toast.LENGTH_SHORT).show();

                    }



                } catch (JSONException e) {
                    e.printStackTrace();
                }

                // Toast.makeText(Signup.this, httpResponseMsg, Toast.LENGTH_SHORT).show();



            }

            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            protected String doInBackground(String... params) {


                String jsonInputString="{\"method\":\"upCommingBookingInfo\",\"user_id\":\""+user_id+"\"}";

//                finalResult = jsonhttpParse.postRequest(method,Email,Password, HttpURL);
                finalResult = jsonhttpParse.postRequest(jsonInputString, HttpURL);

                return finalResult;
            }
        }

        UserLoginClass2 userLoginClass2 = new UserLoginClass2();

        userLoginClass2.execute(method);




    }

    public boolean isOnline() {
        ConnectivityManager conMgr = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = conMgr.getActiveNetworkInfo();

        if(netInfo == null || !netInfo.isConnected() || !netInfo.isAvailable()){
            Toast.makeText(CitySelectionActivity.this, "No Internet connection!", Toast.LENGTH_LONG).show();

            return false;
        }
        return true;
    }

//add service name accordingly
       public void ServicesFunction(String getAlllocation) {
//we have to fetch here
        class UserLoginClass extends AsyncTask<String,Void,String> {


            @Override
            protected void onPreExecute() {
                super.onPreExecute();


                progressDialog = ProgressDialog.show(CitySelectionActivity.this,"Loading...",null,true,false);
                progressDialog.setCancelable(false);
                //progressDialog = ProgressDialog.show(CitySelectionActivity.this,"Loading Services",null,true,true);


            }

            @Override
            protected void onPostExecute(String httpResponseMsg) {

                super.onPostExecute(httpResponseMsg);

                progressDialog.dismiss();

                // Toast.makeText(getContext(), httpResponseMsg, Toast.LENGTH_SHORT).show();

                if(httpResponseMsg.contains("200")){
                    //Toast.makeText(getContext(), httpResponseMsg, Toast.LENGTH_SHORT).show();

                    try {
                        JSONObject jsonObject = new JSONObject(httpResponseMsg);
                        JSONArray result = jsonObject.getJSONArray("result");
                        for (int i=0; i<result.length(); i++ ){
                            JSONObject ob=result.getJSONObject(i);
                            //fetch image here and set to adapter
                            // Toast.makeText(FirstActivity.this, ob.getString("name"), Toast.LENGTH_SHORT).show();
                           // homemodel history=new homemodel(R.drawable.promocodecar2,ob.getString("img"),
                            //ob.getString("service_name"),ob.getString("sch_servie_id"));
                           // androidFlavors.add(history);
                         modelCity=new ModelCity(ob.getString("location"),ob.getString("id"));
//                            list.add(ob.getString("location"));
                            modelcityss.add(modelCity);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    cityAdapter.notifyDataSetChanged();


                    //senitization_recycler.setAdapter(guidlines_adapter);
                   // guidlines_adapter.notifyDataSetChanged();



                }else{
                    JSONObject jsonObject = null;
                    try {
                        jsonObject = new JSONObject(httpResponseMsg);
                        String messege = jsonObject.getString("msg");
                        Toast.makeText(CitySelectionActivity.this, messege, Toast.LENGTH_SHORT).show();


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }

            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            protected String doInBackground(String... params) {


                String jsonInputString="{\"method\":\"getAlllocation\"}";

                finalResult = jsonhttpParse.postRequest(jsonInputString, HttpURL);

                return finalResult;
            }
        }

        UserLoginClass userLoginClass = new UserLoginClass();

        userLoginClass.execute(getAlllocation);
    }

    public void   gettingOffersFunction(String getAllOffers) {
//we have to fetch here
        class OfferClass  extends AsyncTask<String,Void,String> {


            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                progressDialog3 = ProgressDialog.show(CitySelectionActivity.this,"Loading..",null,true,false);
            }

            @Override
            protected void onPostExecute(String httpResponseMsg) {

                super.onPostExecute(httpResponseMsg);

                progressDialog3.dismiss();

                // Toast.makeText(getContext(), httpResponseMsg, Toast.LENGTH_SHORT).show();

                if(httpResponseMsg.contains("200")){
                    //Toast.makeText(getContext(), httpResponseMsg, Toast.LENGTH_SHORT).show();

                    try {
                        JSONObject jsonObject = new JSONObject(httpResponseMsg);
                        JSONArray result = jsonObject.getJSONArray("result");
                        for (int i=0; i<result.length(); i++ ){
                            JSONObject ob=result.getJSONObject(i);

                            String offerimage=ob.getString("promo_image");
                            Guidlines_model offer=new Guidlines_model(offerimage);
                            offer_model_arraylist.add(offer);

                        }


//
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    offer_recycler.setAdapter(offer_adapter);
                    offer_adapter.notifyDataSetChanged();



                }else{
                    JSONObject jsonObject = null;
                    try {
                        jsonObject = new JSONObject(httpResponseMsg);
                        String messege = jsonObject.getString("msg");
                        Toast.makeText(CitySelectionActivity.this, messege, Toast.LENGTH_SHORT).show();


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }

            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            protected String doInBackground(String... params) {


                String jsonInputString="{\"method\":\"getAlloffer\"}";

                finalResult = jsonhttpParse.postRequest(jsonInputString, HttpURL);

                return finalResult;
            }
        }

        OfferClass offerClass = new OfferClass();

        offerClass.execute(getAllOffers);

    }



    private void updateLabel() {
        String myFormat = "dd/MM/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        //Toast.makeText(this,sdf.format(myCalendar.getTime()) , Toast.LENGTH_SHORT).show();

    }
    public void onClickStartDate(View view) {

       DatePickerDialog datePickerDialog= new DatePickerDialog(CitySelectionActivity.this, startdatelistener, myStartCalendar
                .get(Calendar.YEAR), myStartCalendar.get(Calendar.MONTH),
                myStartCalendar.get(Calendar.DAY_OF_MONTH));
       datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis()-1000);
       datePickerDialog.show();




    }

    public void onClickEndDate(View view) {
        DatePickerDialog datePickerDialog= new DatePickerDialog(CitySelectionActivity.this, enddatelistener, myEndCalendar
                .get(Calendar.YEAR), myEndCalendar.get(Calendar.MONTH),
                myEndCalendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis()-1000);
        datePickerDialog.show();


    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void onClickFindCarButton(View view) throws IOException {



        if( startdateSelected=="" || enddateSelected=="" || st=="" || et=="")

        {
            Toast.makeText(this, "Select required input !!", Toast.LENGTH_SHORT).show();

        }else if(daydif<=0) {
            if (monthdif <= 0) {
            if(yeardif<=0) {

                Snackbar.make(view, "Select valid  date to drop !!", Snackbar.LENGTH_LONG)
                        .setAction("OK", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                            }
                        })
                        .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                        .show();
                // Toast.makeText(this, "Select valid  date to drop !!", Toast.LENGTH_SHORT).show();
            }
            else {

                    if (yeardif >0) {

                        int dif = Math.abs(hour1 - hour);
                        //  Toast.makeText(CitySelectionActivity.this, ""+dif, Toast.LENGTH_SHORT).show();
                        SharedPreferences sharedPreferences2 = CitySelectionActivity.this.getSharedPreferences("Date", MODE_PRIVATE);
                        final SharedPreferences.Editor myEdit2 = sharedPreferences2.edit();
                        myEdit2.putInt("dif", dif);
                        myEdit2.apply();
                        SharedPreferences sharedPreferencesForLoginOrNot = CitySelectionActivity.this.getSharedPreferences("loginOrNot", MODE_PRIVATE);
                        String login = sharedPreferencesForLoginOrNot.getString("info", null);
                        //   Toast.makeText(this, "", Toast.LENGTH_SHORT).show()
                        Intent intent = new Intent(CitySelectionActivity.this, CarBooking.class);
                        startActivity(intent);

                    }

            }
            }
            else {

                    if (yeardif >= 0) {

                        int dif = Math.abs(hour1 - hour);
                        //  Toast.makeText(CitySelectionActivity.this, ""+dif, Toast.LENGTH_SHORT).show();
                        SharedPreferences sharedPreferences2 = CitySelectionActivity.this.getSharedPreferences("Date", MODE_PRIVATE);
                        final SharedPreferences.Editor myEdit2 = sharedPreferences2.edit();
                        myEdit2.putInt("dif", dif);
                        myEdit2.apply();
                        SharedPreferences sharedPreferencesForLoginOrNot = CitySelectionActivity.this.getSharedPreferences("loginOrNot", MODE_PRIVATE);
                        String login = sharedPreferencesForLoginOrNot.getString("info", null);
                        //   Toast.makeText(this, "", Toast.LENGTH_SHORT).show()
                        Intent intent = new Intent(CitySelectionActivity.this, CarBooking.class);
                        startActivity(intent);


                }
            }
        }
        else {


                    int dif = Math.abs(hour1 - hour);
                    //  Toast.makeText(CitySelectionActivity.this, ""+dif, Toast.LENGTH_SHORT).show();
                    SharedPreferences sharedPreferences2 = CitySelectionActivity.this.getSharedPreferences("Date", MODE_PRIVATE);
                    final SharedPreferences.Editor myEdit2 = sharedPreferences2.edit();
                    myEdit2.putInt("dif", dif);
                    myEdit2.apply();
                    SharedPreferences sharedPreferencesForLoginOrNot = CitySelectionActivity.this.getSharedPreferences("loginOrNot", MODE_PRIVATE);
                    String login = sharedPreferencesForLoginOrNot.getString("info", null);
                    //   Toast.makeText(this, "", Toast.LENGTH_SHORT).show()
                    Intent intent = new Intent(CitySelectionActivity.this, CarBooking.class);
                    startActivity(intent);

                }



     }



    @Override
    public void onBackPressed(){

        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else{
           // super.onBackPressed();
            AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
            builder1.setMessage("Are you sure you want to exit?");
            builder1.setCancelable(true);

            builder1.setPositiveButton(
                    "Yes",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            finish();
                        }
                    });

            builder1.setNegativeButton(
                    "No",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });

            AlertDialog alert11 = builder1.create();
            alert11.show();
        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch(item.getItemId()){

            case R.id.nav_home:
                break;
            case R.id.nav_help:
                startActivity(new Intent(this,HelpAndSupport.class));
                break;
            case R.id.nav_about_us:
                startActivity(new Intent(this,AboutUs.class));
                break;
            case R.id.nav_booking:
                startActivity(new Intent(this,BookingHistory.class));
                break;
            case R.id.nav_policy:
                startActivity(new Intent(this,PolicyActivity.class));
                break;
            case R.id.nav_profile:
                startActivity(new Intent(this,Profile.class));
                break;
            case R.id.nav_login:
                startActivity(new Intent(this,Login.class));
                break;
            case R.id.nav_SignUp:
                startActivity(new Intent(this,Signup.class));
                break;
            case R.id.nav_logout:
                AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
                builder1.setMessage("Are you sure you want to logout?");
                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        "Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Toast.makeText(CitySelectionActivity.this, "Logged Out Successfully", Toast.LENGTH_SHORT).show();
                                SharedPreferences sharedPreferences = CitySelectionActivity.this.getSharedPreferences("loginOrNot", MODE_PRIVATE);
                                final SharedPreferences.Editor myEdit = sharedPreferences.edit();
                                myEdit.putString("info","no");
                                // myEdit.putString("username","Guest_User");
                                myEdit.apply();
                                SharedPreferences preferences =getSharedPreferences("MySharedPref2",Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = preferences.edit();
                                editor.clear();
                                editor.apply();
                                finish();
                                startActivity(new Intent(CitySelectionActivity.this,CitySelectionActivity.class));
                            }
                        });

                builder1.setNegativeButton(
                        "No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alert11 = builder1.create();
                alert11.show();

        }

        drawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }

    public void endTime(View view) {

        TimePickerDialog timePickerDialog=new TimePickerDialog(CitySelectionActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, new TimePickerDialog.OnTimeSetListener() {

            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                hour1=hourOfDay;

               // min1=00;
                String time1=hour1+":"+00;
                SimpleDateFormat f24hours1=new SimpleDateFormat("HH:mm");
                try {
                    Date date1=f24hours1.parse(time1);
                   SimpleDateFormat f12hour1=new SimpleDateFormat("hh:mm aa");
                    endtime.setText(f12hour1.format(date1));
                     et=endtime.getText().toString();
                     String endtimee=f24hours1.format(date1);
                    SharedPreferences sharedPreferences1 = CitySelectionActivity.this.getSharedPreferences("Date", MODE_PRIVATE);
                    final SharedPreferences.Editor myEdit = sharedPreferences1.edit();
                    myEdit.putString("endtime",""+et);
                    myEdit.putString("endtimee",""+endtimee);
                    myEdit.apply();
                    // Toast.makeText(CarBooking.this, ""+sd, Toast.LENGTH_SHORT).show();

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
     final   TimePickerDialog timePickerDialog=new TimePickerDialog(CitySelectionActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, new TimePickerDialog.OnTimeSetListener() {


            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                hour=hourOfDay;

               min=minute;
                String time1=hour+":"+00;
                SimpleDateFormat f24hours=new SimpleDateFormat("HH:mm");
                try {
                    Date date=f24hours.parse(time1);
                    SimpleDateFormat f12hour=new SimpleDateFormat("hh:mm aa");

                    startime.setText(f12hour.format(date));
                    String timee=f24hours.format(date);
                     st=startime.getText().toString();
                    SharedPreferences sharedPreferences1 = CitySelectionActivity.this.getSharedPreferences("Date", MODE_PRIVATE);
                    final SharedPreferences.Editor myEdit = sharedPreferences1.edit();
                    myEdit.putString("starttime",""+st);
                    myEdit.putString("startimee",""+timee);
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
