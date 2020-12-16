package my.awesome.tooros;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CitySelectionActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    String HttpURL = "https://www.cakiweb.com/tooros/api/api.php";
    String finalResult ;
    ProgressDialog progressDialog;
    JsonHttpParse jsonhttpParse = new JsonHttpParse();

    TextView username;
//    Spinner select_city;String citySelected;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    androidx.appcompat.widget.Toolbar toolbar;
//
    Spinner spinner;
    String city;
    Calendar myEndCalendar;
    Calendar myStartCalendar;
    TextView startdate,enddate;String startdateSelected="",enddateSelected="";
    DatePickerDialog.OnDateSetListener startdatelistener;
    DatePickerDialog.OnDateSetListener enddatelistener;
    //
   RecyclerView senitization_recycler;
    ArrayList<Guidlines_model> guidlines_models = new ArrayList<Guidlines_model>();
Guidlines_adapter guidlines_adapter;
//
RecyclerView offer_recycler;
    ArrayList<Guidlines_model> offer_model_arraylist = new ArrayList<Guidlines_model>();
    Offer_adapter offer_adapter;
 // String[] Cityname=getResources().getStringArray(R.array.City);
//List<String> City=Arrays.asList(Cityname);
 //
 int hour=0,min,hour1=0,min1,daydif=0;
    TextView startime,endtime;
    String st="",et="";
    List<String> list=new ArrayList<String>() ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_selection);
        startime=findViewById(R.id.startt);
        endtime=findViewById(R.id.endt);
        spinner=findViewById(R.id.select_city);

       // list.add("Bhubaneshwar");
       // list.add("Kota");

                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        city=parent.getItemAtPosition(position).toString();
                       // Toast.makeText(CitySelectionActivity.this, ""+city, Toast.LENGTH_SHORT).show();
                      //  city=spinner.getSelectedItem().toString();
                        SharedPreferences sharedPreferences = CitySelectionActivity.this.getSharedPreferences("Date", MODE_PRIVATE);
                        SharedPreferences.Editor myEdit = sharedPreferences.edit();
                        myEdit.putString("city",""+city);
                        myEdit.apply();
                       // Toast.makeText(CitySelectionActivity.this, ""+city, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });





//         sharedpreference to store info if user is logged in or not
//        SharedPreferences sharedPreferences = this.getSharedPreferences("loginOrNot", MODE_PRIVATE);
//        final SharedPreferences.Editor myEdit = sharedPreferences.edit();
//        myEdit.putString("info","no");
//        myEdit.putString("username","");
//        myEdit.apply();



        LayoutInflater inflater = (LayoutInflater)this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.navheader, null); //log.xml is your file.
         username = view.findViewById(R.id.user_name);


        offer_recycler=findViewById(R.id.offer_recycler);

        drawerLayout=findViewById(R.id.drawer_layout);
        navigationView=findViewById(R.id.nav_view);
        toolbar=findViewById(R.id.toolbar);

        //select_city=findViewById(R.id.select_city);



        startdate=findViewById(R.id.startDate);
        enddate=findViewById(R.id.endDate);

        //spinner.setOnItemSelectedListener(onContextItemSelected());


//        SharedPreferences sharedPreferences = CitySelectionActivity.this.getSharedPreferences("Date", MODE_PRIVATE);
//        SharedPreferences.Editor myEdit = sharedPreferences.edit();
//        myEdit.putString("city",""+city);
//        myEdit.apply();

        setSupportActionBar(toolbar);

        //hide or show items
        SharedPreferences shared = getSharedPreferences("loginOrNot", MODE_PRIVATE);
        String info = (shared.getString("info", ""));
        String name = (shared.getString("username", ""));
        Menu menu = navigationView.getMenu();
        if(info.equals("yes")){
            username.setText(name);
            menu.findItem(R.id.nav_login).setVisible(false);
            menu.findItem(R.id.nav_SignUp).setVisible(false);
            menu.findItem(R.id.nav_logout).setVisible(true);
            menu.findItem(R.id.nav_profile).setVisible(true);
        }else{
            username.setText(name);
            menu.findItem(R.id.nav_logout).setVisible(false);
            menu.findItem(R.id.nav_profile).setVisible(false);
        }





        navigationView.setItemIconTintList(null);

        getSupportActionBar().setDisplayShowTitleEnabled(false);

         navigationView.bringToFront();

        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.app_name,R.string.app_name);
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

                myEndCalendar.set(Calendar.YEAR, year);
                myEndCalendar.set(Calendar.MONTH, monthOfYear);
                myEndCalendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);


                String myFormat = "yyyy-MM-dd"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);


                enddateSelected=sdf.format(myEndCalendar.getTime());
                String getenddate=enddateSelected;
                String getfrom2[]=getenddate.split("-");
                int dayOfMonth2=Integer.parseInt(getfrom2[2]);

            daydif=dayOfMonth2-dayOfMonth1;

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





//        guidlines_adapter=new Guidlines_adapter(guidlines_models,CitySelectionActivity.this);
//        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(CitySelectionActivity.this,RecyclerView.HORIZONTAL,false);
//        senitization_recycler.setLayoutManager(linearLayoutManager);
//        Guidlines_model senitizationmodel=new Guidlines_model(R.drawable.senitization);
//        guidlines_models.add(senitizationmodel);
//        ServicesFunction("getAllService");//add service name
//      all for offer recycler
        offer_adapter=new Offer_adapter(offer_model_arraylist,CitySelectionActivity.this);
        offer_recycler.setAdapter(offer_adapter);
        LinearLayoutManager linearLayoutManager1=new LinearLayoutManager(CitySelectionActivity.this,RecyclerView.HORIZONTAL,false);
        offer_recycler.setLayoutManager(linearLayoutManager1);
        Guidlines_model offer=new Guidlines_model(R.drawable.offertooros);
        offer_model_arraylist.add(offer);
        Guidlines_model offer2=new Guidlines_model(R.drawable.offertooros);
        offer_model_arraylist.add(offer2);
        ServicesFunction("getAlllocation");//add service name

    }
//add service name accordingly
       public void ServicesFunction(String getAlllocation) {
//we have to fetch here
        class UserLoginClass extends AsyncTask<String,Void,String> {


            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                progressDialog = ProgressDialog.show(CitySelectionActivity.this,"Loading Services",null,true,true);
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
                            list.add(ob.getString("location"));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    //senitization_recycler.setAdapter(guidlines_adapter);
                   // guidlines_adapter.notifyDataSetChanged();
                    ArrayAdapter<String>dataadapter=new ArrayAdapter<String>(CitySelectionActivity.this,android.R.layout.simple_spinner_item,list);
                    dataadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner.setAdapter(dataadapter);


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
  /*  public void   gettingOffersFunction(String getAllOffers) {
//we have to fetch here
        class OfferClass  extends AsyncTask<String,Void,String> {


            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                progressDialog = ProgressDialog.show(CitySelectionActivity.this,"Loading Services",null,true,true);
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
                            //   ob.getString("service_name"),ob.getString("sch_servie_id"));

                            // androidFlavors.add(history);
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


                String jsonInputString="{\"method\":\"getAllService\"}";

                finalResult = jsonhttpParse.postRequest(jsonInputString, HttpURL);

                return finalResult;
            }
        }

        OfferClass offerClass = new OfferClass();

        offerClass.execute(getAllOffers);

    }
*/


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
    public void onClickFindCarButton(View view) {
       // citySelected=select_city.getSelectedItem().toString();

        if(city.equals("Select City") || startdateSelected=="" || enddateSelected=="" || st=="" || et=="")

        {
            Toast.makeText(this, "Select required input !!", Toast.LENGTH_SHORT).show();

        }else if(daydif<=0) {
            Toast.makeText(this, "Select valid  date to drop !!", Toast.LENGTH_SHORT).show();
        }
        else{

            int dif=Math.abs(hour1-hour);
          //  Toast.makeText(CitySelectionActivity.this, ""+dif, Toast.LENGTH_SHORT).show();
            SharedPreferences sharedPreferences2 = CitySelectionActivity.this.getSharedPreferences("Date", MODE_PRIVATE);
            final SharedPreferences.Editor myEdit2 = sharedPreferences2.edit();
            myEdit2.putInt("dif",dif);
            myEdit2.apply();
            Intent intent=new Intent(CitySelectionActivity.this,CarBooking.class);
//            intent.putExtra("city",citySelected);
//            intent.putExtra("startdate",startdateSelected);
//            intent.putExtra("enddate",enddateSelected);
            startActivity(intent);
        }


//
//        Toast.makeText(this, "No Car Found !!", Toast.LENGTH_SHORT).show();

    }



    @Override
    public void onBackPressed(){

        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch(item.getItemId()){

            case R.id.nav_home:
                break;

            case R.id.nav_policy:
                startActivity(new Intent(this,PolicyActivity.class));
                break;
            case R.id.nav_profile:
                Toast.makeText(this, "Not created till now", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_login:
                startActivity(new Intent(this,Login.class));
                break;
            case R.id.nav_SignUp:
                startActivity(new Intent(this,Signup.class));
                break;
            case R.id.nav_logout:
                Toast.makeText(this, "Logged Out Successfully", Toast.LENGTH_SHORT).show();
                SharedPreferences sharedPreferences = this.getSharedPreferences("loginOrNot", MODE_PRIVATE);
                final SharedPreferences.Editor myEdit = sharedPreferences.edit();
                myEdit.putString("info","no");
                myEdit.putString("username","Guest_User");
                myEdit.apply();
                startActivity(new Intent(this,CitySelectionActivity.class));
        }

        drawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }
    public void endTime(View view) {
        TimePickerDialog timePickerDialog=new TimePickerDialog(CitySelectionActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, new TimePickerDialog.OnTimeSetListener() {
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
                     et=endtime.getText().toString();
                    SharedPreferences sharedPreferences1 = CitySelectionActivity.this.getSharedPreferences("Date", MODE_PRIVATE);
                    final SharedPreferences.Editor myEdit = sharedPreferences1.edit();
                    myEdit.putString("endtime",""+et);
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
        TimePickerDialog timePickerDialog=new TimePickerDialog(CitySelectionActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, new TimePickerDialog.OnTimeSetListener() {
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
                     st=startime.getText().toString();
                    SharedPreferences sharedPreferences1 = CitySelectionActivity.this.getSharedPreferences("Date", MODE_PRIVATE);
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
