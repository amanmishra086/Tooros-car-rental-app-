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

public class CitySelectionActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener  {

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
 int hour=0,min,hour1=0,min1,daydif=0,monthdif=0,yeardif=0;
    TextView startime,endtime;
    String st="",et="";
    //this is city adapter
    CityAdapter cityAdapter;
 ArrayList<ModelCity> modelcityss=new ArrayList<>();
    ModelCity modelCity;
   // List<String> list=new ArrayList<String>() ;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_selection);
        startime=findViewById(R.id.startt);
        endtime=findViewById(R.id.endt);
        spinner=findViewById(R.id.select_city);
        //


     //  cityAdapter=new CityAdapter(CitySelectionActivity.this,modelcityss);

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




//        SharedPreferences sharedPreferences = CitySelectionActivity.this.getSharedPreferences("Date", MODE_PRIVATE);
//        SharedPreferences.Editor myEdit = sharedPreferences.edit();
//        myEdit.putString("city",""+city);
//        myEdit.apply();

        setSupportActionBar(toolbar);

        //hide or show items
        SharedPreferences shared = getSharedPreferences("loginOrNot", MODE_PRIVATE);
        String info = (shared.getString("info", ""));
       // String name = (shared.getString("username", ""));
        Menu menu = navigationView.getMenu();
        if(info.equals("yes")){
            //username.setText(name);
            menu.findItem(R.id.nav_login).setVisible(false);
            menu.findItem(R.id.nav_SignUp).setVisible(false);
            menu.findItem(R.id.nav_logout).setVisible(true);
            menu.findItem(R.id.nav_profile).setVisible(true);

        }else{
           // username.setText(name);
            menu.findItem(R.id.nav_logout).setVisible(false);
            menu.findItem(R.id.nav_profile).setVisible(false);
            menu.findItem(R.id.nav_booking).setVisible(false);
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


                progressDialog = ProgressDialog.show(CitySelectionActivity.this,"Loading...",null,true,true);

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

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void onClickFindCarButton(View view) throws IOException {

//
//        String encodedUrl = "JVBERi0xLjMKMSAwIG9iago8PCAvVHlwZSAvQ2F0YWxvZwovT3V0bGluZXMgMiAwIFIKL1BhZ2VzIDMgMCBSID4+CmVuZG9iagoyIDAgb2JqCjw8IC9UeXBlIC9PdXRsaW5lcyAvQ291bnQgMCA+PgplbmRvYmoKMyAwIG9iago8PCAvVHlwZSAvUGFnZXMKL0tpZHMgWzYgMCBSCl0KL0NvdW50IDEKL1Jlc291cmNlcyA8PAovUHJvY1NldCA0IDAgUgovRm9udCA8PCAKL0YxIDggMCBSCi9GMiA5IDAgUgovRjMgMTAgMCBSCi9GNCAxMSAwIFIKPj4KL1hPYmplY3QgPDwgCi9JMSAxMiAwIFIKL0kyIDEzIDAgUgo+Pgo+PgovTWVkaWFCb3ggWzAuMDAwIDAuMDAwIDU5NS4yODAgODQxLjg5MF0KID4+CmVuZG9iago0IDAgb2JqClsvUERGIC9UZXh0IC9JbWFnZUMgXQplbmRvYmoKNSAwIG9iago8PAovUHJvZHVjZXIgKP7/AGQAbwBtAHAAZABmACAAMAAuADgALgA1ACAAKwAgAEMAUABEAEYpCi9DcmVhdGlvbkRhdGUgKEQ6MjAyMDEyMzAxNjQxMDErMDUnMzAnKQovTW9kRGF0ZSAoRDoyMDIwMTIzMDE2NDEwMSswNSczMCcpCi9UaXRsZSAo/v8AVABvAG8AcgBvAHMAIABJAG4AdgBvAGkAYwBlKQo+PgplbmRvYmoKNiAwIG9iago8PCAvVHlwZSAvUGFnZQovTWVkaWFCb3ggWzAuMDAwIDAuMDAwIDU5NS4yODAgODQxLjg5MF0KL1BhcmVudCAzIDAgUgovQ29udGVudHMgNyAwIFIKPj4KZW5kb2JqCjcgMCBvYmoKPDwgL0ZpbHRlciAvRmxhdGVEZWNvZGUKL0xlbmd0aCAxNjE0ID4+CnN0cmVhbQp4nI1YXVPbOBR951fcmZ3utB2q6tOyeKOw7dIpNFuyuzPb9kEkJtGQ2NR2YPj3exU7im3s4IFxQJHuOece6UryESOUUmg+88WRkISyCOoPxTXh0oDWgsTKQJ7A7RHd9m0+cRznREcRaKlJrCUoaYjGgVHdwY/7dcQoI1zhKPwRmij8RjKiEEhHfDtutob3FxzOs6O/EIdvEfZPxPkwBRZHJNIUNI8J1xymc3j/kQMyUDC9Bfj+erq06R08ZRu4zXK4ybI7ly7e/ITpZ/hjinE1Nxgx1srH1bGPKxXhyF/Fnr8BReWWP8MEGCN2wuNIQ/P57ZOPpuARBX2G7wA/8Y/5LphmCoNxWAPDtGjOQssKrnsHdvutAdNFqJAvjuz2W6OFMWFMvziy228NysdiXcx+M7hmRBsFkeGEKlmZIQBH0tqMi/Qhc7Okmf6hhEUxIxFme5+wXYungGiKVf0iQUxMKzAGhsQ11tmmKLN1ksOVXSdwchCzC7HPdQeUKVF1HEK1xXJzEKobeG9OB4qrqOo4BFUnE64y8oK8LsLe1g6mULLqOIT5G6eMC1zG0SgPlSJMRk0P65aOh1gApBDP4S6zG7cao7Abv2FgGzEYOAQZa2piw6jS42wMgCHJbcBg4xDgh6okwbktX5qmXYiGj23Q4OMQqKDwDs6TGT455XSUmVhilTFNM+uWjpkM62Ec9QtN5nBm87FmBsSQ7TZiMHMIcmpLC1NnF9k4MwNgyHQbMJg5BDhxszv4Hc7z7B5xX6w7XZiGoW3gYOgQMBNo5WebVoYyoPIE6/LpJUwz4HH4LrBpxFQmIpr1TJIqkPaBJpcNHT1b/m7LlJIojkcGhg5GDJGJUPWO2XvE2E8b5b00Omwa2FrTOE+KWe7uS5elcJiFwLJFuQ40/OnAxHokDUFEZA7wmOS+2gYGeFgZWClKYgi9rQmBUNVS7Z49zBt54Fh6pdy50cD/N0nu5vapgLOlzReHmXSB9xOrSQVRpcYJhXDDsN8Kgok8lrHwE3hMpVBM4qFMNvXXLe1KIQ32Z9GA1CSdP1Pau4Y6GA2pbVSpNaG4pIZhvdToWHNvyyilMlaYtabTu5aO0igiMjY9k+p6c1NmpV2N07jH22ns4Hk7uW8eBPQauTrmdLSdUuHBn8VNkXVLR6SoFv3zvH66nsKP1+oVuHS22sxxE3Ap7JT/eDNSe6ARtLdpBH8HeWyn8jHHiwgz46Rj5ZCGNaXXLR3p1OC+IHogz10xyzZp+eM1fTVaaUANStuo0sR4E+EHYL3S0bNYGLygRaKhctfSViliSgTtWzjXyWyTu/JpnL493k5fB08iEPcdBwG3TmLt7EocvMwJyTGU7t2ZDqUGrzRUqGZq6pYRpVzgp0IWzxfhdLvkJ9bNX2AfUGv6fTvawVQH/iHVI/jvNoWWgGf559GzKuLH1onzt75qG2WE+Tn6iDEkmhZu5m/hIi3KfDPb7uwnMFkltkhQ02wJtoRymUB9ZFxlM7vtdJPgJT4BPL2uXVpUN/qkLPGSZx+sW0F2C0WSP+A+TeDtVeYP1D5ssdtCKpFIpcWV4+yLMSVbrtr0cb3J/RH9a+4WLkXnzrPZZp2kZUXBbpBrWrqZXwEPSe5uXc33cem8GKSc3ZcoBP/H60z9EoLARxx8kc6dTU+wPlo7x+3uMNMYDzbVaw6O9V33MJ3ZfA52lSHdR1cu/ZsPZFiFXrnUp3Nd3arSzfomybEo+fOqe/ACv2DmUsyWZ7y26dyWWf5U8UxRzo7rQYqRIYLVV8cWs4kt7rO8fP+PK+wxxkLb0m2aMKHzmsB9kq9diUT7eDSnGcdNmuEE5wJP0Fr1rrEszwqY4nRKs1W2cEkBk4cSvpTzViAqSYwrizPemut8/yLh4urk74jirffrN7z+msn0jArGYt4Kg2cNytV2gSqpesLgJnhxhfORs9PTP8+mwrDoE/vvSzMI43gbj3EUrl7GTQiyz+Hp5Tumj2Gy9CuFHcOHpVvjHSddOPgz2xQ+hWcoNn3CbzY3Nk2KR5sff527YmlBKyyZvFlwtEAXlfCvsKgJ79R4tC0uTGL1jaIeJ6dLVwD+4pUiWSWzMs9SnPGr1RMskjTJbbnd4au3E+gezDPMfZqVuLZ/bRx6WrgFWr/Jk+Dp/3PstGIKZW5kc3RyZWFtCmVuZG9iago4IDAgb2JqCjw8IC9UeXBlIC9Gb250Ci9TdWJ0eXBlIC9UeXBlMQovTmFtZSAvRjEKL0Jhc2VGb250IC9IZWx2ZXRpY2EKL0VuY29kaW5nIC9XaW5BbnNpRW5jb2RpbmcKPj4KZW5kb2JqCjkgMCBvYmoKPDwgL1R5cGUgL0ZvbnQKL1N1YnR5cGUgL1R5cGUxCi9OYW1lIC9GMgovQmFzZUZvbnQgL1RpbWVzLUJvbGQKL0VuY29kaW5nIC9XaW5BbnNpRW5jb2RpbmcKPj4KZW5kb2JqCjEwIDAgb2JqCjw8IC9UeXBlIC9Gb250Ci9TdWJ0eXBlIC9UeXBlMQovTmFtZSAvRjMKL0Jhc2VGb250IC9IZWx2ZXRpY2EtQm9sZAovRW5jb2RpbmcgL1dpbkFuc2lFbmNvZGluZwo+PgplbmRvYmoKMTEgMCBvYmoKPDwgL1R5cGUgL0ZvbnQKL1N1YnR5cGUgL1R5cGUxCi9OYW1lIC9GNAovQmFzZUZvbnQgL0hlbHZldGljYS1PYmxpcXVlCi9FbmNvZGluZyAvV2luQW5zaUVuY29kaW5nCj4+CmVuZG9iagoxMiAwIG9iago8PAovVHlwZSAvWE9iamVjdAovU3VidHlwZSAvSW1hZ2UKL1dpZHRoIDQ0OAovSGVpZ2h0IDE2NwovRmlsdGVyIC9GbGF0ZURlY29kZQovRGVjb2RlUGFybXMgPDwgL1ByZWRpY3RvciAxNSAvQ29sb3JzIDEgL0NvbHVtbnMgNDQ4IC9CaXRzUGVyQ29tcG9uZW50IDg+PgovQ29sb3JTcGFjZSAvRGV2aWNlR3JheQovQml0c1BlckNvbXBvbmVudCA4Ci9MZW5ndGggODg0ND4+CnN0cmVhbQp4nO1df0Rk3RsfI0mSZCTJGEmykiTJSpKVJEmSJEmSlSRrJUmSZGUlGUmS5JUka60kGSMjGVlJkiQjGUmSJMnI+N7nnHtn7r3znHPP/Gjb9jufP963vXPPmWfOj+c8v87zmEx/HpkF9YML2ydXV97Ly0vv1c3dw5Pv5cX3dO893Jztq8pNeQOa4hBCVkXPvPP0zs+Fz7u/8rUq+61pjUOHjOpxp5c/dWpcOUYrU9+a5jgU5HSsXehm6P7y5GDP5ZSw49rdc7v39/d/HxwenZx7H5RXTufr09+a8jikvdf640azufaWBho/5melJGjfM5sTEpNTLdn5FZ0zrmv67rm9wvw2VMcho3Tao5q8m62hSqHjzVo3fUSb7PVmvjaNcbCQ0LDhU3HNH+22MBonVchzfzGe81oExsFDUvueavOdj+SF3YOlc5du3O/xKfzjMLfsq6bP0x+ZPGKu36ZTOJEVY/ri4KPKoZq+58mMyHtqoPvYO5AWO+riMIB1/kU1f8dVUXWW1EsVyKPWGFEXhxE6NSr7WhTbj8K6RHv6VRQL6uIwQMayRmWfjIUm13pJBdnh5Bh09u8hPSt2yKw81s5fbEi0/aLduUpj098/BYsbHAMxwsWzZv5+xorIhFF6rD70xarHfwdV/lfDXX7syGy6pX0ux8VRHb693gRux5LO0lPaqftDLHt9/0jY509CNNiNKaU22bRz8Smm3b535D/wJyEa+PpjSmqGbB54GkqMab/vG52vN38SNtvyk2JHa+qW3K2zPHadvnesv+oESsrb4fbK3NTEyEBfd3tzfXVFWXFBfo4105KWHME2StuRe32ajZ+EFOl6Z/mr4+Xp/vbq8vz06MC969xcX56f/jY62Nfd0dJQU1leSqc3PSURNwAkKzPov1+sijNS06sqERHBB9N7cX5yuL/r3Pq5ujQ/Mzk+/DWwe/NqVf79w+mGAkuC8W/8pzHxdnMVAV6erp80Dx7Pf7u2f66tLC3MTo1/7agutsbwyH0PMLvfaCpeCb6ro+25/poP/zdG09dUIt4QD0c/BiujdoO8B7yuEvG28PzoDj+O471h9a1H+XVxv9HxbwdjpP1xJeKPwztf8daj/Ir480rE7fHO6vRIX1dbS0tbV/+ofW3n+DrKLu/P3ZuLk0O9Xe1t7Z29g5OLW7+1YfyO5n9WY/yjSsSde767Iifk+lGyraxteuc2oi6vXfauyvyQkLeErNI2u1sln+01/Znx/OPYY49NjHEy35TLoySnZSXcnXg8U2fldfmheyM4hz//yYCajD+kRFzYPwnc+7MNnIbR5Xy1QJcfxjxKg/sv/+A9i4FXmKxQ7HaJamRpPcfG3QEcraJdZny9VBqt/Xu3nTrHRtmYecKG7vHnOsHayrIYFhrCWflptbU1xigL50dmTik3NNy28IbnnaPoBZvAnbcmKwLUKKz5kHtm/mvoQ5nX9FuTFQmyNxVu/v90BX8FncCWtyYrIiQpP8b+1pT8OSQdYvP3VBCLvs0plqzs7OxMS1rMnD9Klxl4lwnKDFazu0hISUtPT7dIyJCQmZkJkcrZ2cIOquT0TImCrIy0KFwhCZZsq3gYNberXDSfxGl0PMhSVNc/tebcPzm/hEQjntPDva3F4aaiKOYxvaiu7/uqc/9Y6fJob2tppLlIN4pJckDULuoATizpnncenJydnUvwSLi4uIA4ZS/ASINMK6jpmVxxuCUKvEDB2ZF7e3m8rSTMAFZrVd/c9u9zmoBFCEfc/mpQDvorPKLUyG+ecnp8aK/PRwuNkTh/8hq/O86f0S59x4vNmhWa7WFuwaKJA1Rgo7jhLXRb3fjm6SPe8HRV+E5yUsWo84pNAgOH3D6H0TbDghTpkFg+sXfPp+Ziviqs6IiEj2Mugy4vF6tVRtBa+vCHvqOPa6i+FICLSULxoOOG29R/vdogwLKsgwf8bhhY5Pb6E21TY0xOKPKHxQh0fxbmOrmDYvHIv/ssgTaVDQDdbcWMWXwHB8GQu7P7XDg70eF40MChZZuM1Jrfy+s25QRrcse1Z+IoXzbYKCqcfhY6DUsXxW3f5/08meKTsfUOlbsLpsU5nneYYwJK/Bq5M4brJ/uAMvbDsKWNsnXO8YJg19h7V7JitGm02GeH4nfxuScAk7vz58IzIp80swgo2A6rIw2uuBlYGtE2q4ajq4XVkEGF4GmYb3rLmmbIDGz4xhmnq4gt+CTkEEsfM0j4hmAWPwrrDc5QLna444TfWQrzrkN3GPnRgljlnfsdEcUQ/EAPV9zWpMO6vlW9oMFdix3MitcR9vJW4zt37PGtHVZ0gnUtQsq2mInuMv+LsMsdS2hnzULM/Yu2UepMhBQchYoPLeGdLnpwndTp51iT63CMwdVoF0L4xQiBqAzDYaiDI0SUKRAThLRrtjDyONoD/ZFVEfZZoMEj9/ZsMbq53WF4hnqiYQ+4ybIjml+sV5oSXULNvJphj+rM2tSexBlnUfTlNxIo29E2c+LzNx4ddVhSmKHouuzW9tYv1mpH3aYrqjPLP6ohYCGqvvz+/7jjb0fbdAjPX6QnhQJvqPYb7VXwG03GtWxB9UudZkNwzpl4LlF1VhHdAej39/DG37yLNfGV8NqoMR0lcQgTjT6Abknd3aRgo4Zgk96oKdhWHUEbUfb1wg1LyELlfw8iyqEYjZI4f+gRHYPwnefiYHdWQVPOQzA4vy3aLSOhNtBbqZAdjoML7mSUo8Q6BOePf+Hi7sS949o/NRpBrQmyxahLl9SlkYSxEOxvzOBVBUFJoZJrtHk43Xe53CdGfHkrsAXnBAlggj8ZPWibb2Lz95EjLLrHqmllgdScip7VS/aLfq96hRVzzKn7EzV5qbTL8s8rPD3/NnCwCl8pWFFa2DgtjqbrPxBLQYq1rHORJ1w+FyoEeAQJYGKcOwfLaJtGofmzMJW1x8VyrR6S1fWbTaHKfpiKRgcAnv+r1AnnHZxg5U7lLf6GVkFJDJXAtln+rNWqmGlNHPvmqPzSJ1ECmKjjzUEi6v55FMsrsMT6yh/FoS8n9zPtiioxGZeJJWwiJ3liD5M7BzJ/CUsQihrPVGF2sRiNVg/rdbe83EZECWDh0cabgxx0VEPNuhiaGN94142/X4L6rSScBgxqeHCAdPD04oaFQpb7UTn4baLW6BvZ9FTMOAB9I7g2bXPg7weEItzbKnW43tfUKIJPXO93Ndp5iC8bg4VhQDtmqiC5DJb7rMSipDKm+JyZLMZ6hLdQRO82xvBRPAXw7KLDZHbib97UsyhIZbSQ1ZJkhkH8KUZBfzjDGBRpylC33RwrahlD6FF+DB7c4T/kXL8tZGyxLvoxxyR+PVVVWKCg0Ebf78DfveQkv8xmCD1j5FMrwx0stEcEgG9wkRRlebin85BbLoIh09PfylLZTrmp7L/iXU6RD/FoA4JVrNc0nKtcc+0arfgX0CnKZ0jVYdgqecB/4K1NoOk8StcVX/yx4G5DWYqZQj+8RSQiFVLxMadBdQVMlQ53sX1B333mhJiamPk/9smxXcQwqp6Jmkr4KEB52m+BoDF8A74wjwoZuJTpJJ9ZcW5olAIdt7y5yWdMJWIZ7Yqhs301oOAz2uqUKIxlLAqcYd3bYQEXJPlBbBT4bpkxaoaLmXTF4PzVkBjcVnxEVDaWpfYMDz7qQl/eMnKtfUAXs5ccJswJ9Pv2liZGRse/yZiY+D4zOzPRW20z+sFqfEe7ZqgBamSgZ7PH8F4errYcg9qCL3/EWaFDNkoKZVE72Ed+lrMFT6j6YKgUp6CS5i1xzJeGbVe93uwUj/R2Yj3wrd8UuBG007BdOqpJnMLE4+zOODYnGdUkLsA9m8EQEM9xPRe3C/MDUgjQdXJPJp4lxHBx2mX8nQQZqIny0riMmBlVX4+N73rgF2nIdvmFfXJuXB0SlyEugYEVMxwBDFPvLPburUBwCUr6A4lSZGnLBlgVk3HKIr3ZiYsw3PhhimTmBOIijIBGakYZ3yWw3nZ8dBgsBtchZo0pwO11dAJNqL/VGHtC1ftw8WnKuCHqw+DeDZGRjlrwQWBDbSZ3AtXMUlAW6gE2gm4paVvjZwyaSscnUr8CDbq5p37OSCMWdkUq0+KuCGZwcRAozxCJBc5BdXXwxaG3TDcEusxGdUtYE2aGu4LRK2paElGp8FP4mi6+OpwGYwio+rgr4qnQsKEFFQ5EalaVo7TuSvo4Kt18FuiyBD3oYOCtDL8vzpfx6Z4QoABflef0Gl3kiZL55gPyxfjNTuO9+xE7Ox9Ean/gIdI/GNGNTyI5e3DzJRSyYPk28CJrNnS6RayKuMvvQA55FY3JCYHLMLQTd0UIsC30CDwSuQ7zA/3KKYa8cSaiEOHnAARVMAIer3H5AD0Cb2wCFODfsyV/KuzRCkGl0RdHfLMTDXQUudJrwUMrehiWHZHQHIYtFNgkQ43fxVc2GkoockmLYQsNiK8Mc7sx5o2+OeKbnajUJZKWpB6nVOL2W9jzBeMeWSELjSZTFsOTw5CyUeu8SEkhhsU8YIIwb6KfG8PIrx7xzU7c7zok8FvxrLOPBaZEVD8UkSBwrwgEtrIybDbgHaG2CX5QNMUI/jXBjWCL8O6IkTiJ3+w8MM64iTspBS6kMY6D83RThgf7QICbZ+LxfaDHj+DDco+vUFwkN+RikhKB+xwfVddFi9FfZwyD2LIGtJHAmsOt70ZOFxNzSKWjLgcVAUeNu8Q9eP49M4MrSwoGvkJxK62AY4YRGqQR5vMiy/Y5wP9m3JEmYA/DTYzG/I4hwkBLfE0YZ1tKZcRmSi0tHvwjxqbKQokzlszwuwn6ipipU5HcljGoioqu0BeBYlOl6LetGbYbYdApyTCFqCCwadglYwMC88ENvUx7A34uGBtimhkU6AuQfkStV3zwjTFp6OI19r+xJtBQD8QNFjQyuwidwDMjmwLuC5TUt2zmtepHRgoxfAJvjHwRrKib51ABpHKRF5+OgX+c4WPGUJI0wI1XhmYTVvU0CGovwCM0Php0yQotBs6H2wxkV30ocBZqGKPOukq1j23drAa72xvGXRe+dRl3oIok+cNH2yCGnxGv4KdifS5utjQI0WDEg/n9bcwAM+aqtuCnqUH4XyVrOph+sOziyuqa6urqYFLbT22s29z8CcSjWkQs0jaccXm5SdBKWa5p4rPNxE2+fA5WyLqkBEyZFcvAcnbjaoT/iRsTl83S8B7CqiSTwegGD71SgBqankXMx6wAat5+yWFe5RmFj3FPPV+Mz2ZEZVNbC37tSnN1UAtGjPwW630JKayYGwGJTgOG05DLgPBwCoavUwcG3S9sT6KNmUPtjm4ylrmpg9llFlO3In4RRqkhtlzEusfHtjClMG/OvIRZQ4ZhLuUaMnAp21hwBzA83f4HlguriJ0yRzahsu7FPzEMX6Z89n01MKGyFET2mcbYsv4XVpBeFvtymWEkog4MYayN16YbbTIm9H3tLMIfO9D3W9g3apUocDzhl4Qn3KnbyE4NRc4fVjwT2+CHOjkJ8JRgleyw/XA3YA0jIRtXJ8cnnXuZMAB2xLp/wRbytg23N1MoDCqXHX73X6hAkMW8R+iXTUL4+uQpJhzX+UbowZk+zrmITSXdwopyAXwsr59h3Pm54UUEJaIc6F5MeMIbU1xPaDXl3FFeusYjxWPCskgBbr9rRaucYV5mNhp1jbt5uQkDOPnCHua0gWxZ/bzr1dQLzDIcioPrV8ATZYsmmeQmp3jcHqCVrxIyirvWuSkOfJWBLrluzyfHUCXp0mwp6ljlpzgg+SFYUi3vjGcZxQhedseqyQV9c3pByxI/tR/VVHB7VThY4k1BLdpENMkkh4dS3By7Xa7fjMTZQYwGu8wxCmG+PRHqkgZS47d2+E7LdKMtc3cKKTLOjH67nC6BabgQBleGweM4hJNMsi4Oh4cNtb0p0vSEWjgp22lnfIzHM8mINkMUxZHMpRej7YjhuJSBq63C0lNltNQBjjUx/LiFNUycWbmjd8U11UcefqTCrXxe8+QEMTh5ukgmysWvhOK5CRim4nDg1RnsGXJHOLiR46hZR+AO/0fFoErms5KiCRcywgE3KLYCbbInrn/iHthwcKPXcnCvfDi4Vzgkq+a6gdMZz50aDnyB3AW4kBEGrrim5UG0TTgXtyMOllPoC2XX+FUNcdwGDEEsgbI25Du1YETNCeM5mHtCNMUXE3znDm56bA9jAs3RyTFnWN6A6OSYi+CWZtg1jS//RxxGTXCnumHOiMgRBv+4xi83+PjpBHTIiKwICYUTHcq0aCr97gVD+1kChPERkRBB4EMAJyptnxWRIwxuklBGCkTjG9Ia5EWe19rOcIuz8vYIYEHlZshj6JSGV/ilNcT0EBliXb1nopWpDczhuJIpEoasRn6Ew33J9hrniNXZCcFVh7oXVhIwkRLl6RHyPl3p5SjV+EuDi5F4FmeRQGgNsiP6sYs8ZSUjoqP1P+3vZfhHxdzkyYuRULCh84RH1EkAj1yDAysdg98oyUsokr6FzSlcBncvEkaMi+To4NYRzrhtwgrpDUFv2CrcsV78i06Nf2Q5QRXgV+GELvjp8Sk8uWO/3XgQy8M7hg679BZ4G8OALqwlFYYnypx9CYljYIVQCuHK8H4RfqNHIMkEgqTPwgVqXrZbhLwdiZ3iAu5OWyjVLB06jASBTeK5CfY/I7JfNGled4yzteJ5xUNqBwkitX1bJG7cM2MU5RlEcsumCCO9mEONt4yLnYJpbCkS6tZFMrxcL9egLCXyyhc3gwKLHHddCtxOYaF4ZI9fa+V8uSnMyquFgy6+tc6z0sJIaOPEG7BCelnI63fwD0Pvj05W2GOkpQauvgvk5TCl4O5kA8nHAAUdC/voVa/ns82Rqohy831om3ej7nzf+dZYNXNBZDPOnxVWAzZym2d2cefthfNbHVuaZmRlNMCdo1esZFXu+U0obk/CqXeFw1bePra0vX9ycXVzc+31HO9tzA40FIVZ41kL68e20cWt/RNPoMu5gcZirsWh/Bb5edIPDMdQqEJWWcvwwqb72OOVOrnyHLu3FoabS/j8pMh7LQZK2vXlyd76eKNw0dSkHAziriQjpGRk23Js2ZYoqqxH02VqLvr7cgSVCBYFFkJBhlA68eQcmyCAMJs1UySrTxxxxBFHHHHEEUccccQRRxxxxBFHHH8Eaz9Z+CFjHbAmYVXCysrKfxKWl5eXAIuLCwsL8/Pzc3Nzs7OzdsDMzMy0hKmpqe+ASUCwhgXF+Pj42Njo6OjIyPDw8NDQoISBgYGvX7986e/v7+vr7e3t6fn8+XN3d1dXV2dnR0d7e3tba2trS0tLc3NTU2NjQ0N9fV1dbW0NZHX4VFVVWVlZUQGX7j5+/FhWVlZaWlpSUlxcXFRUCFWsPnzIz8/LywU7ms1mtWZnZWVmZmRYLOnpaWmpKSnJyUlJiQkJYV6d/WsQiXH8PeDl5cXne35+fnp6fHx4uL+/u7u9vb25vr6+uvJeXl5ceDzn52enpycnx8dHh4cHB7/3993uvd1d186O07G9tbnx6+eP9bXV/5aXFhfm5+wz01Pfv02Mj44MDw187e/r/dzd2dHW0tzYUFdTXVVZUf6xtKS4qLDgQ35ujk1aIhmWdGltSOvi9ZcF31sXhzhefM+PD/d30irxXl54zk5Pjo+kZeHec+04tzc3fq6v/re0MGefltbB2MjQwJe+nu7O9tbmJmkJfKqSVkAZLAFpBeTl5lizMzPSpfkXmv6obzLEETP4np/IEpBWgOfs5PiQzP+ey7n9a/2/Rfv38eEvPR3NdVVlhTmZqeb4BL5jvNx7T387Vu2D+fEJfEd4ebq/8XpODn/vOTdW5ieHelqqM02mSEowxREjvADPlE5NSa66kEQqSZw6IIemY2vj59rK0vzs9OT4yOCXnq7WhurKsuKCPFuWJUXnfo5PYHiQRBV6UoG0AgN/4TknAgs9sXZdLkmIBZllbUUSWuZnZySpZRyklq/9vVRsaQyILSC4gtRC5VZJbE2OQGyNxeXhsAbghQj4gOdnIuZLcj7gAXAPuCMAqZ/GhVwT4V+CF3B5CVrABSgCHtAFFJxRnIJqAMrB8ZGkHoB+QBUE0BB0KsIK1RFmiY4wOTE+JikJgwNfv/T39Xzu7pLUhNaW5qbGhvraGknXlFQFIilSbYEIi0SnzKQaA9El30SVVAK1HvubmtVoUiAXLW8IoB5QVweKNKjSRJmu/kRQVQU6NVWrqWYNqjVRrol6DQo2gCjZgEIA0bUB+YA8QC6ARIUArIBsgixAJijiAAsgXUYaQaqElBRQz2FMJQU9wfxeVXQxKLH03qhCxOJ4MygT6AkzvDaOvwRKyO15fALfJ5QA5wvj8qpx/I1QQr29sQvfjeNPQkkDdiscvh3HXwWPPIHGRdHj+CuhJJR4YpS9iOMvR+AiNF6BO46/HYEa0SJlheP4+xAoEiWWlv5NkbroUGFzrpXevUqYCj7cXp9sku+MDjscP63kvxoB2yy9vfPZlLvh0GJGtrilLDscS+qrYgnTDodzMdFkKhpY3Vbe/jEup+/KWHM41LXE0pYcjrkkkylf1f/m8mAg2VdWh/2XTOrasJxCsVtHiqPRZGqT/qe9eZ44o3uL1h4oUSbwCctR9nehTG8Z/01ysOTpssqfkwub6ZKG+5xLOIwmwS1JnFcbWgHrQJ7ARLeuxTR8OmJKmdVdzl8kVxKh1qJT9TaUUz9NDE35t0kzffRobuj67OAbSghJgdlDjzZtTooC/Vu0hkhy4Kqy4683+oYmLTwGC25dyGPIOgHlBfYTTG1+bWFAkrpyEkmfF8i2BXUOdszaL50xJYVmASTVpCBvgroMR6/8fSHlRU4h31bIshmVHlpDLqCXkuor19qb0SFZpuSkacEESAIJw94WUBDh6V4G3Q+QmwvyNz7Thw80KwakvG/3k0IfkCNbXesPKqUdp9KCSI/3ATzcBnICJR9LWyMg0lXB9/xKkJNEPqi//BFUZ0hJpd4pkJy202RKOgr2/xCYK1pCwUcfE77hSaNJjl/uVbTsp5MCT25tnbNJ9a+X3rqWU0CpMmj+F/1l+NdEwm8o4pOXT1HQ6pUXHeyNbvlxUT1UTYEiwVBEpotOh6p8M5Q1e6miaXM9JflB5Ab33Fd/cNPmgqHjdzpNumwvU94u6IB/19FMsOrq0LAwfKU0q57S/4fSHmBzLprT39tZRB8XA2+GooUghsznBUn5kEmrW+gqijqlR+2q12zyc3Wp3qupKltqgtlMvGgyXmMqIgPkWbpQ3Rkf8ZMaWFCv8t4WeEpqjJeZzHt0JMkhGNhdpBYg1PSp8bPFtiyJy97QBIMpcBR58+kga+pWQWZpiVMXPWurOEAiposMytZV/XfCMjBDpq+XYN1Bi/Q9vkLKbfXVCBfpRlYB0lTeoAbrNg1jvTk5+E2wr8BNsLcHTu1dlwv82js7ToAkSW1vb0nY3Nzc2Nj49esXiecOBHKTMG4SxL2owoIO8wRzczS6m8Z3k/BuiO2eVEmQkORILS/A6TRFx1BV1iLlmOxAmEcYSfJWQEyEcTmFc3PYz6kmBFlhv5C/gCE+VZpoKnhN/SfI9/KJDp668KBC45hfUzqhiuxAOOwug37XtIPHx6MMwm31ZhTIWecr1Twq8TGKR5rMrAKafwVU9Q4gjdR3FeFwKHRTrqgqa5EDzNFCpEEy25AB1SV/BvvihZxYcPIz09/AWJElAdNMshUTmUKtjUCB1bs8kwkq/aiL4g7KNAJbV6VxhvpZ86Qe8LUqobu1oCCbcttjHQXWm5BaMrCJGUU4MxmZ3P8KHAb5Eyw0dWJP6bR7KaMpJFVjCHL9NpUGyWzDKSUn3ScFxWfJ+Et/vdiHRwCjocVU4RyVhr8J5IxReEBkCvVpArIRzDFkTlPnaAfO2iyzddXRCKS3m6wQv3K5NjXc21rzsVBhh5DAzTNESBkZltvAjlUzGwlQZ2pLplifPTIv8nS4r49AhmEQR9SsBg6FS2kUXH7NGAJvG6RqQnPgAd1sIDWcWZTxDyC0kiNI7D9MRSCoLJEHkKV1Qf/GinRcerUlGRQaC7VHI4hMjx9M5hXVtz5dOnsI49dUK5LTSYIcpS01rsmUGVIqLys29VZeBbeKmwQqBpyqTCQllDdmStztIvA4FYbjuYBIg3JZauBqRFOrhA1VFxj/AEKr7gCDfKgFxuSkCYQgD66mRCuw7z5amU6db5vQmErZenDC8yBNJqiWmbpslZtAt1rBfJYzz0LOOm0WYU1Z7tA6Oua+iHJ4/REcyLwGeONPFc1wKEzTMbyG22wSZtaISL1G1/wplVhBzQKBMBkmZCE4/g9yoi0vYsaHSQf17YTuLpApXjQyBQg1FfRsUycbheoEGybK1ne/U6LmNknkH5kPc8204/D8JmDT+Uq57YscQXm7SYWx5KOQqugV0ts+hWKsHG7WSOSZyl8ZezZCIVRPUW8WOBRaMfPMVR5VE37Jv8xLpBoiGCqhW07p72ZOJjE5Ld6tnK2fiI/qoCFg3yDUgJTaoXoO3zFiomxdi9XAPk202ArLa9snoD6AdFiDZHOUR0nJlZUk2Mgn2tRd/dLbszLFjLoDqfXzR2EnNf4juGkG8RkkC5UkD2o9KOwh9SrPIVUoyI+KhctJhJ0S+G2yvJIhDd4VN08iKU3pU4QF2MQamQLsdJJQA/mTNcWTgR3WULauxZo0NXm1tZ9UKd2Aje/Qou/6is6wkX9qH4GyaJRv2WRKKmwcmt/YPTy9uL69fyDx0vcQuS9fd4ObkEqgs1opJGoh2Ne3iUq4CSohueJLNMJVIaysaC7/Kvoi0RCX7EVUmrxV5cwseiKphPU5qE/GCMf95Q9auGDrfiabYkl+AqrFLtdEUSr17gscNbDLNYbGXvog26uNBku7pDVEdGWIn7fbzHIlS5XHoJnOHEhW+mK8YGDVnsxQ80k8L21iWqY1N+8DREyTyP2sTBK4n0wind/KNlMsCXZ7wX+aQWCfplm+L+toPDgIeVR2AzUhYOECOX0ZhvxC4T1wfH7jfhtYLU8DYiQciWohNO2IcoO8O20lI7DXwHELE75Oaaq7VDYpSEZqFgIcpYNwW58+J/kWVZB09JwI5UP8a9Eu/V5HeYWMVmBWviJq+lD0ABDeb8iRpqhpBGDeugORJJAaGxjk98pPQYSkGdVKTKBFPAxVK293gUh/kESPV/9aQxV9XD+lcGlg60oJDNhNxM6QCLN+2CH30Q7azb2VcNvblqogKaUJprRzqBqtepZGFuFPPZHvC0iFc5D/BtWbCQaF1LiQ1TSKBFmFCjwIrT4QUvjjm4aJFSA1FOBAMoeWx9hMoMdzoFoR7B26BUMLQM0gRd8v04lxUIM6oizyioz+/TCHVjFwgVkROGnAlAKmsmvgk9/9ausMrWwdjHwNccD5QpzZOttnaG3sEfK8XD/Uh+DQgS3vCchIMG/EXFqiz0fgtiBCtHQ2t+keSbLapl9ni313sOqvEt/ZYf5SQd4LHO7ECwf7EeqXBK0zLaRBMLFySOr/Kz0LzZCm4NEW/HfipPYa3kmH/EG9prTC/QKRaIDfuQJtK+ATsgXrNBavu3k4PUPSzM+Els+5SEmV1Pgnkaznfy/SxmkaGoqZ8XZqs0gatNv7gzb6ypkZO0xUr90+HJTYLd/sM/beYF95M3YNZjv035Y6Yrf3aKS1vI4JpdX0YE3QqZVaNzQt0zTRIQsj+dMz9k+BN8x90rfTNZZSMzAl9zHSSn9A26yWlmlJf2/SPpttMyUP6emJI4444ogjjjjieNf4H5xVA40KZW5kc3RyZWFtCmVuZG9iagoxMyAwIG9iago8PAovVHlwZSAvWE9iamVjdAovU3VidHlwZSAvSW1hZ2UKL1dpZHRoIDQ0OAovSGVpZ2h0IDE2NwovU01hc2sgMTIgMCBSCi9GaWx0ZXIgL0ZsYXRlRGVjb2RlCi9EZWNvZGVQYXJtcyA8PCAvUHJlZGljdG9yIDE1IC9Db2xvcnMgMyAvQ29sdW1ucyA0NDggL0JpdHNQZXJDb21wb25lbnQgOD4+Ci9Db2xvclNwYWNlIC9EZXZpY2VSR0IKL0JpdHNQZXJDb21wb25lbnQgOAovTGVuZ3RoIDExNDE0Pj4Kc3RyZWFtCnic7Z1drCTHdd///9PdM3N3l2QokdylrEgwIDhBGPghJuDAlkjRtmDAD3kTX4IYMOJoSX1YDow8S3w2DEOwvCRlGUIe8mDm3YDgGEsuxcgJ7DdRSAJBgD9EcUWJlEjee2emu+vkoXr69nT3zPT3dN+tH8Dl3JnqqlNfp06dqq4iHD2gij/93w/754wIAYUEYFSFNAYkyCSkUYgARgGAVCgVSgIgoFAAVNoPoAihIfVkHcT6ux//yXGy53A4AAA8HMRRgef/5wdMNPOoMACt6qN48AMaA1WoqiqsGixFseNXwmpTEiRFQGK9MlAoQZKAqonV/9yTb/SUO4fDUYpToM35s28/Eq+VIkYpUPElmCcFGscwMUysutGJJMQjBUJQLtTiBbr5Z4eSTX9SBQnKxoxVhGuEoQIQqgHk5OrNx7/ffYYdDsc2ToHW5tarj4gSCgq8QIIAAKIQUaRqkjAU+D49HySsDl0v1cSbKTmxmanTasuL2ToAUAkqVNX+sJnPE4BSaT8AwYz+zP6a/KyK9VKNUQXVmGjtf+FTzix1OPrCKdCq3HrtOiMBlKKLE6EgChGtt2xMz2cwA7AxCRWgCgBlhNj3/fXp+uQK/jH8yXNPVUtV8aUv47HH8PrD+MD5xxbR6dJfLU7mhJCxEZA0iWuAszm9APYvK164VhKRwcNvvvn00/2Ui8NxD+MU6GGef+W650GVsznFQ7hGFGl2ou0HDGYwBqulUSVBiIljb0in5NdfuxGFCo+qEMHiCk0EAOJjeaYwMKoLY37nqbcGE8nhuPQ4BbqTF/8WOHtEQZBXrtIadJp1UBKzGb0Ay1NjDKD0qJ958u7RJAYA/NFffPjao5ESBGYLkgjXsHbxcmmo9GH+4yd+dFwhHY7LgVOgJXzj9keX/ooKf8YgwHqpcZwPY63R81MVD/FKPvvro3M1/vmdG2sloV6AYM71Uj2ffoDzM+tHjZ75uLNGHY5WOAW6xadfwqduPGLA2UJEsFperAuleD5mc56fqgLRXL7wy6NTnVleegk/fuQRzxcR+DOuz9XzOZtzeRYDGkv07K+8fWwZHY6p4hToBS/ceQSUYEbPqs6y7UTzE4YrNVBvqf/pN6Y0EX7+1UeoMj+hMQhX6gcM5li+bxQ4j5f/+amfHVtAh2N6OAUKAF/96xuzGZSYn3B1XmJ1AiCxuMLlueraPPNrU1KdKd+4/dEVlvB4co2rMzUGwZyej9WZamye+eQkM+VwHBGnQHHr5esivHJNlufGFHydFgpmM4ZrPVua35+U4Vnkq6/dCFRFxPOwXimI+YJxjDhSQj7z8VF7JByOUXFPK9Bbr133DbxAQISrnW9ZUuAHDFfmh39997nnhhSwR269/KjnmcUVWZ6pKihYnMjyzCjAd3jz3/3w2AI6HBOgRIH+yV99yOcyMpdZt157QFbhDCY+ucblWfmcPWU252qlzz7x5lDSDcTXXrmuQuvVtdsM/ID+DOulxpE+e+z9WA7H+MlryRdvP2pEPdF4r06ZOvYNyfkJ18vdx3sAAGZzrlfmmU/cvZTG+pdu40P+dQo9j+E6ec00cQSrPvOE06EOxz7yWuGFO4/MFl7pAvQlw0QluztziECBVcjfe+oyT2lvvXzD8zBbcHWeVLwfUDyEK8Q4++wn3AK9w1FOQYG+cmO24Hq3Q/CeIpgxXOkzT162yXuRF+5cp8f5nMuzpOrtroPVucYGl8994XB0gmT/uH0bCjWXevJei3vBErc888RdRrpa6uJqMqaq4vxUZwt6ghdf/dBxxXM4xsmWBfria4+qURL7F1XuIQihxkaffWLaW5cqcuub170rnJ9c2KEAZnOGkTGhrs/u/+Jvfe+I4jkcY2NLgT7/yqPzBdz8PYsIKDSxiSN6M75P7w9+5Z+OLVSPfOVvPjBfB3Z7U/ql5yMIuFopQt681O5gh6MWWwr0hTvXg5nY1VhHCgXzBVWxOlcQsOY5NbnGCABUqQJRAxFVelGsnq8Ijed5sRdz5amJaHydhTTBGWbRevnRn/vg0499d4SL+398+4ETf7E42dKhyer8SmHUKJ1X1OFAVoH+yV99yJ/FfsA4OqI848XeyeEHEO/i1g3d+t/mo714wyCK1ERQe8C8KmEPmieoFyWvCtjfgfREegGMKkBRgRijauB5olCIUkXVQAUaUxPVHN4f6bvBycnq9JEH3/5v32u54f8Pv3n9gasQT6JwazT1fMwWsjwzRqFGw9P73KTecS9zoUBvvXLd97nrXUbHYZj+s/mCyWVwAJObODM3xNkA9suE3XoZmxUtVZhY48j+qQAvrv4AwY26NmTyC2lvAFEYpYgahYCAGoV4orHCU4lV6cFXhMZ4HkhGRnwlWFxUTA4iOVONAaoaUTUeBP/vhzdvdlSYDscUuOjvL9y5MZu7DUzjo6CXt7VwRjVnv8/cnAwcUs3YsprjCIQqCVUR7tqKIIJgThGYODmq2d7WZKzS3ljW9hYnsXc2GagHjUUACt73LrlD2XHpSTrZp1/Cr12/EcyYm7I5LjPM/R/IWM0k46j8TL98NIR49DyIV25QX/y1UdOrZWo/A1BCYwoV75/rf/lN9/qTYzIkjf3F1x7VWCluA5OjZwgCFHgexYMIYP0SBuultXrVEBLDBPjsrzpl6hg1iQJ9/pXr84W4+bvjWFys0QkUCNcw9ppooSg/84Q7ZM8xRnz7P2Knq8vRJcVNS/dOsefyXlgiiyNNd4BQEMzo+VDF6sy88OoNqKofugtIHKOCAG69+hGaldvA1CF2imrdgpbUA1gW+kK3GIM4honU6CR165Y/VOziP4BDeSeMgYkQx2rMdmDC95Nbo8OVGlUY3gunEzgmAQHceuX6bJbf8eeoBQl75yUJVYRrxJFBsumTEKgChspYKAZKqEIIAkpVJe1OUSgJ0MN8QRIKRGtUXMw5CiT8gF4AAqqIIkRrtXfTq1GQMAoRwEBVRVSRbKsyAARUu45kSwLA4go3ZajZEd0m5M+wPFVVQPUDdz/49NPfPUquHQ4LAbxw58bJNZ6/P9Y+OmLsVR/iwRiszo0qVVU8gVF4cRTi80/Vuzr4D795/doMnq+qonYLEDA/ERGEIaLRvCRGwWxOEUSRPcxfoVAKaERkfcYvfKqe1/L2bfyf+YcRhkLC7oUSLE5oLwvIalLxMFswjhCt1UR49ilnjTqOBgE8f+fGyZWt8yMc+yHhz+gHWC8RxwYGVBp4z37yB52n9cK3HjYmoEYiMr9CEyNcHc0gJRHM6NmMh0ZBNWoMP//r3Wux5+/cgIJUkourEoeaXeS0r5ZGoZoIoLvKyXEc+Gff/nkTnYvHOHIK9DAX78UvjQAKz+Pqdz/+kwGSfv7b1yWEMVxcpSrClWrZolQfKCDEbEFjEC6NglBGJqprXzfj+Ts3KArDxVXG0dbtVfYqp9W5MYof/o83L82NVY6pwOdfvv7AQ17nG5jUID3evBkimJ+M7KQNIlxqFAFKL4yPci/8rVc/wnhN6vyKcLDiIdbnqjFiNQh4lO2Zt755XRYAeXKN6/Ot2wT8gJ6P9VLXs9Mv/PJ7w8vmuGfhiy9fVx/o9BV4AgYE9r0IeCAGwto5xadJwNA63IZEDcRTGDz45o+efnrQpIv88e0HZrLwfJhwCCXqzaGrUVwc/8IrH6G3IujPtu6zIrG4ytWZLmP9PXcdnmMo+up+X/vWhwDTeC9OMGO4Ms+4nuAo44U7N0CcXOH56VbzWlzl6lyhvPkJd2ipYwj6suLiSP2ArXYyDjdBdUyMZ554cxV5q3Oz2PZjLE91fiJqzNe/9cHjSee4h+hLgdJrt1JMuzPQ4Sjni0/94L0fBMtzMzvhtg41J9ckiv3jiea4h+hFSX3j9kfPZdl4c35ykKXRMTjdukJV0/3yuZ+YHkt3GUnzW5pxVRVpPoq/+OIvmX/5A+v9zEQLEiaOn3myy00CxRpMTlrdka89UbXJcjOs8KU/TbH52bzs6lBD0ktFns7PCJi4YcbEIxS+/2C3Ug2G2UZV7b8o62zYNO7cI/abwWVvRZrZYl52ZRyb4so9WDHFmzf/bmHmy1PNbthQhR9Q6b34YqvsFCsF2zW4P1/HolgFqfC7wtd9ZHhKOwiOXdRIDxPpmJUXLJofLSqCCPzdj//fboXqlWJfyg7pDYb3VLnYP4e3WSqSU3bWKGgWVdagq57x33nq7792++dWyziYMVyrTTxcq+8jfuxRoMlqkk3dStKtadZTh8/WQidiZ4dwkkexT7N9qk276g9V7aVb+oJWe4zY7vGhyA2J3KZl5LmoRmUUZM0BFmgZeWnG99ukn3nqB7FRY3BylfMTzk84O+HV+wSmdnGlaXWVnSIdxplWBLbLrdtayFZ3F1IfprRPDZN0XfpRVGx1kpCaVo8PQG5w7ruCc035iGq02LJ7TS5bvPsz/rkn78ZrEy7j1Vm8OovXZ/HZu7q5QLUSOU3UgfQ7aB956bDdiWylFMez/tI6utlbl+5F/Op3HvbfFs+XZu+GiiCOFRo/+8kfdy5be9KVhOPKgMFbWNptjpj3VIF27tBIO2230RZpuWh2iWshnbBPQm9a+pnCvyMATf3Zk0U8UrHQq90K1Z7smH9cSbLW6ADJDWaaHSRrB3UY7ZDas82zl7gWstZ0V3H2ja3N7heR/JjBgmHTg9dIqPB3nvj7bqVqwxiG/SKpDu2v2d0LGbfxdCJYT6QrWqOSM9Wh7WthsAGsW2zGu7dA1TscZg8kvDEV5VHmyxVJTdE+vKL3QsaH1551p70jsTp30d4Unaj2RH8WaB3HfQnRaE5fn4pTJrXIuvJJjdPwLMLNtqdmcg62ptyMqdSCpVnzm672xEbsji3Q//q/PsTkEJEmiIc4wvnq+AU6EndnRVJV0j6qSYwZWZr5gofvurVqZ+SGZ45mzW/S2jO1rjpWoOexQmCaHo4nQgCvvXPko3SmWLXs4p22aQ0blgbSjnyQmG7zq94Cp5jHHL1YoLrW2az5DckkhPjvRz1ts9dlmV4h2dIbNd2M1xo8jjJIVCzb6WqWurUwxTxm6UWBtjyEiby4B3d40rXdYwnQCc106OQMzxzVzZ9juT6rlPB0x7CUKsU7cu9zLbpUoF/6EqBsrP9IrNcKOeYO4Um3XTQd1S9Bg66V8aPUckXtOYwwPVHFGTpy/0kVshnsUoHe+MSHodriECZAwcVxSvYSaM+UWgrxEvRbS5XVpGPt8DhoIF+CMcxycCI/lk027Ui7TJfbmGJvNfO8NitIhnrz8SOsIPXXfHc1l/50Vi1X1L2W8XEOk/35PYevhTTdPUmMsArq0osC9X3xA7a54PMoY1PnSqTYatMtcukhhtkwfbSnKvvy+rA9c3nPZjztVGmYgTPe0xsHFdklVR/aM9e6spGn7zVlA3SYdFGA3PeXwwJN6VKBqpHJFU63zTerGkrjLH7Zx86hirZYh025WcbRtUm4P+MjND+7PZvmYC2goMfTEX2wWrhkdOYDfek7/wo0zTcwCcK1Rk3foG9Dh82XpIiISPU4bXgMPjh33m8bZDw1SC93fxtGpzerBatqRaTzWiiNbYRjWF1y+erMAn377lvwpPkKkjA2BjKoK72rFpOqzsYxpO8Ud9jC9kxmu/JatH9f3ko4QMaPq6ZLs9ZtLaDd+XKpDu3cHO6DrK09cLXmyqczBWroB3PEba7xUH7+qS5vAdtPV82lw20ZItKhKtkVSVdeC+3u/LHOe2+R/iJv1oG7bX6dHIOQvk3UXqpSvdbtgDGSS246E8KfwWsRGZm8xzkko2q+lnQ63zKe/SPzqIYNS5UthNXjGQDdcDBkdb9wAxnQafPrqhb2RN4JI9Ge6NICNaravIBMjD6Ox9+ZXBeGXk8jobVDu40zpZOYJ5fxnhx8jUugE0NvcrWAS7GBKUc3pf/1b/0LqDYudhGEoWHjHaSNGGfzzcXfB2POeE8urc61Z0uX92i1p6WrWshF0uEwNp5Vx24qYB3+FGxzih0J/JurdzsR5iDtB9i+tWdPMXdlWfSX8f5WMDqMudYyd2kMnUgy/lroKWYOeJ/NQbqZwns+/aDVNR4UPv54J7JUTLFtRY7HC7ML285yOW1v+AyQ8a4MtM7pyi5rn7sB5sKjrQVsX+uSuuN3CbxfjJYLod0oUGO0jQdzSMdIV+bnALRswZ1PoMYzbzpIttw6FLv9roOuaqFvBdqfL6UrydN4ih92hdwlUpvjqzuwJrRddZII1xq3vAmkXoqX3/ws0knbnUrGs52/K9dbJ/F0IskAtdDVBrVitJ3H2RJuaOYW6KAmvv7tRxQtDmESqKppdIl8XTrpACNsBLvoUNTBMt55KmPbPdNyVjHkPq1uIxz5K55Wjdr9OdWf6sICjSBk8yV4jyA+98khttB3Un9T1CPtHRc9mSQD0OF+9TFEMtFamAp1TdEOFKhRCVpc4wGAA1p1Y1g/PUpaU8l4J4xz+nnv+KBRVlxTUf3cvFddJXAXFmhL1xAn41nDdJyAlm7XT7qKaj997HjvhF7fr6347GC10Mla61Q0ZpHqkrdVB1/5y4+RaHOLXLQyMFFLMaowko1jQ5Ie1XFsQY7GeKy2afnfO0moNMvjqZH9sNoVjW0V6Pz+n0HR4hoPGsVidn9LMQZg+BF1JAP4FDOejWEkxTgteqr0aU3gUEHdt82PiX1/Jm1e4gT5H/7t91qKUZHRbgzuG6dE2jCG/fNTbHt7rgYYWJL+aKtABco2cXDQXfQtGWz8HNuke1oO0FTaPrZtH5Fp1cIuJmSEVtl31TozRJubjNRg2CNEJsMYeuxESRt9h6dXjKE6LocCxeg3hObYb820UqBf/c7DQLtDmNbGzIbQoGOz6fombaDTynjn71+PQfFNkV7LLX3zp78kBqOdBXpXALY8hCl+6IFWMlRjQh3pcjSsBowz4+PZQj8MHZ4Yuwf2eXJzhxzUG60UqDeTYEZtWuAkQH7xF4ZYQRp/VaV05bmbkLPJ0lXGR/gy6ISa32C7Tbu6fOG4tOpjrV4/sqfYcaDim4oFOvX21JjOd7yPbQv9JOjQa1xl/D7KfbTd0kqBtsx0FCmigTx07WtomF406fc32tB5xqfbJ0sZxmk4fPM71rXeXdFcgd569SNAm1PoEUfqBYvGAgzMABXc+T236Ejv970S1UfGcbmm3hNtfhVJr7OfnBptrkBNFAm18SYk8UjwH//qHxsLcCnpavxPJ1BTaY6dZ/ySMa0zwJrFw81NU7pNJyL1RPMT6T1oMJP1quk1HoASzz3XOP2h6XsOZe8n6CSqbjvbhDKeMrAv7yDj37XT7SSjTYVmCzyVqlbpMXPPR2MxKtJcgYqnbe4xJoEBL4LvqgUbY/qwcTpsvjk3VlcZ78k71lPGR66tGtNrLfR3gEhj2vS1Tq4uP0jji61blRSJcG3W0XAKdMxn2XbYfC197OPpQyX1mvFOHKCjmhP05CK0M4CxndvfnmHEaJjG1//mkXaHMMEYBl43V9pVSrG70ux2stOtEin2rg47xsgzfi/sXuh8JOu2Ti+r4b+HhmoljECvxTUeQgg/+4l/aPj88ah1WvVBOjfB0NvA2+3myj4y3sf8fZxKeRK1cI/QsLN5aH+Nx5Te0c7SlSrpvPn2bYXVvS5mF31kvKuoeqUrP5KthZax9VQL41Ggw7yT2nQSra1WkFQHvMV4K90uXxa0NdTA4rOerCHv7RlJxtM23Xnes8J0ktkxK+V0CG+2pDlMLaAfC3c/turTuhvgzeAmFuhX/vJjBs2v8RBBuFYfXsPnm9LTK9LGmOpjnQ3ch/bcE2d/Ga+uZVLTtfNXrces6bLYjeIdRphtfhULIW2rA9TC8NozTY4bOolzf9k2sUCvnPwsUmlzjUcc63v+0Et1rHbJSd04kdEOyFSh/bO4bNpHk7Lac48CHSDjluwG/mzGe7K4bRKlOwfb07k3ufNCSGthVwscpvlZRrL43i0HFXETBRqLBDOJwqZb6AkQf/Ar/9Ts8Zb00ZOzEdr2WtwA3PdQfDD+gTNeXbCWTKvfDlAL2YoYpvmpamktjMcf2phefKAUtrnGg4R3pJId4IWQ4RtNlT4pIn2/z16Uoe+i2FWVo3WAisilbH4DpzgYVbLWRBEabXUQUxQe7f1Wa5BfpiqvtfR5+TI+LfPTci/UwrTuQdjDYdOkbozfuP0wVeNWW+gR6fTa/QippUSmqGt2sSvjXemmztdYsjH3Ee1RmO4YVp3uFeiZeuKx+SFMQih+9MQbDZ9vTbcbwo+InbnXbb6XIOOWXvtt3ztqL0EtHNSeUx8qKtZR7VboB/SDdmeIUJ87atlegjGz2ably5HxXUsWmMgLnZejFnApMrKfSofq143UGNPqBSTa/47MpD2hbZrv1DPewOhukEqv8WOQ1aT+uBe0Z/XaqVcKf37nhrY4Q4TEaq0zbxQKFNOcSbVsvtNdRtu/1xVTewV+urWAQ81vivkqUnVpoVaka1XPb3GGiAfEOD09vgLFNC8F7GTwn+LgUcVlMTkFOn5vQ46KzW9aTatILUdQva5ISBA0P0NEhBR84VNHW0HKMSEdmvr+Opk6pffPtI+qb2plfFoqiZO6AqiW/2RaFZGl7upCzd7YutON7Qym8evQtI9163WahB06vLtt4AIZf/NDphamqxYr0qC91XgT6cW/hTnVNocwAfD80dVB6tEf4RpurxrEZrzZgn7f1M14h6bcwEVh83iUozcOkraN6oJNyKbOcdDJXkoNBcrT62CLFSRBuNKmT/dL9uSFkTTiBm23AemJPv0lUZe0+x1rnfcoDcC+a3uvNb/x0NiMqKFAVTzf0zhq+g6SMI71vqa3eA6APbXouBZZ9vSHwWQYjyna2OIej+ppzEhM0fYD2EQroll+6xwmorHnSRw1SAUARBBRf/s37zZ8fhDSRjzMMTZZjmt5pabovZbxUkmOyLEGs65G7mmNZO3bXh0LVJsfogyABI/fPiuRluYACiXbVY6uQYbPOLqwtafrAN0vQ9a10nctYATNb0i6GrarKtCv3n4YpOej4SISAR7nGo82ZBUKOjpdMdfVx+lmKtWk6DTj6K7HTmsHaHWmVQuYwgpS5y6yqgp0DkTAemniRlpQiNBgvZ7qEJc9aB2te+w4lWYpubuGUnu5cQn0YeZMa0NlA3JH7rfPad9HsfQXeXu6zXtVBerjSixnkRKNOr5S12b2xd/4we83eXpElCq+XO/NbrGckK48SGlGitPM4X2anXgMR97tU0pLNdsCuX2r2sDN74irr9mk71nXhMPhcDgcDofD4XA4HA6Hw+FwOBwOh8PhcDgcDofD4XA4HA6Hw+FwOBwOh8PhGCO89coN6fDlqx3vw5nty4xpoJu/ieSQka3jmtLAJgmYxqDbMW69vmUuniWTkGkA1eQ4E918SSBJMiO1bj2ydYKUCksPlKKU5tsYgJo8IYQCVKQv2dnEDSAb0YW0f8ab8ACgGzEFhFHmS1MVBFRJgYkhAgoUFIVRK549CWUTLxArKPCFcQxVG95clJXaNzIhAo0pAqMQpXqxjVOV4kEVRplIq2AQC2hCAFAPoowAT2E8IoLnQRQRYJSeB9EoiiBecr+WKNWHUYpBCHhK30esoa+MN98YH57SNzDKOKBvEAdrGyBQrgD6xBLiC5aIZpRgGRhGMaMF54ah4TxmFHN9hYtY/BPvpz/FKnr3oRNZhnL/Ws4/KO+vvXdX3mMPX31n+d033sNjb+HTnx7DJdyO8cI/+5sbs9lo2sg4XkceTopx5DdFt/7XIh5NIlEAySmICoWmI6wmP10EJlQ3A6dataVQKJNfsfkOTL5IfkzGSRDExfiEiwFI09EqGcAIhSQybNK50JPKRJY0plQwqibxKmgHUxupHfvtSRE2jFqBkNyBo0zGSYV4MEpqmmMqkYzMSiigBkJVgsamroZCKgEYNaRH0KihR6ox9BiHFD9WkoY0jIQeYxrGHhGTcagLYUyJaRYhY0osKsJIVsFy4XkSytlKrvnBuyfveefeaj73f7r4xZPvvfwynnuubWO43PD5O9cbHhDiODpd1NsQdc/Sj/uEYP77rRDlViEL4XYlzUKQogC5b4qRVym4Q0NRye/VRq90+Nn6UqEKNWqM/bDR9ak+V6jVwwrdjAncDFgXR21t4rMzmyRBBYRUqKidP2oyW1PCzow0idGOJDQKAiQM7SADNQoPNCCTcc0oKSBhIkN6VGM8MjakwGMc29EiphiaeSQxGTP0hdF65omKGM8zq5VZesF9gTk9DX3/Kq6s73vowe//3dOfHqJx84U7N3pPxOFwNKNMBRxQC8wPP6m3bMuNxu3w+cCFALsSLtP4uvfXrZBa+FOhCmNUDYxCDRJtTav1qUxuB97kR1NVz830ZHMaE4nMjwAEapS0Lq/E6UdJfFbJFEZgDCCqCk8Usa7m5tF/ePvpp0vkdwrU4XAMwq55xo7AWa3N7H8F1Z8OAMz8u/UsNk6ehMR5AmYeSWMjVBGHul4hDDVjkCuUQihVDcSXm7/6Q1/VflenIBwOhyNL0StSFqZMJyJrGicftgNchGRWGxZSKvo00n81dcJfEMUaR2rijaN6Y94mqnIjlwpoYKBi/RGJ1SyzdYR6l8o5HI4ie2fUB6fbW2GY+X/m26JDNvfIDsVkH9734AV13bWVTa7EM6tQs5mYx5sorKyq9gzoxLzcrB1iM1uHAGaziqeA0O4vIRJnLDeritjoOLvKZ4y96h2kqhJUGFV4EKgBjWGgHr3YGPV8enEYB7Mw8oLw/n/2k6f/daXc7VWgHblgp7dEVWuuUS1Eye9VyyUdpiuG35lQjQh2BN0ZQ1PZdvbn7UB7Sq9SDKi9tWBn8HZztYw1tFnnUXuePFShcar+NpsYLlZrNpsGFCCTvWvcOBHJdCVoo0o2G/QSrZq4CzfLSpstAsmzCkLsbJWJz9BAaZW3AtRkPyGVhgpQVJUUQo0aEqRnDAiTrBqBjGEEVGOUtEtDHuNY6PkSG/qMQ8OACA0DemsjMwmVZCyrcLkQz3hy3weD9+++Fz7qB9/3l/7Vt0++h5FtDPB5obu3IDFbTOY2Ct35R/7Lontbi7+mf1582AqxtQVnO+SWXzvTqXU74YutpkiCpUlvLsbY7HtNr2e4eH7T7O3328KTmag235Npp0w3CZUURWrIZHtWVto0SGnSOchCsaR9Fhfls1EOTL9PDQpNtwsnK7uA3UqU5FEvtMpGs9Bs7BQmKwiplrmoEKYbpEg1BqTYRG25EDB2X60q7FZeJa2QVAPCKIU0NGKTUBCkGGMgpFGCsQ1DgsrYxPZ7IVVjL2AYwhdSqAqYWJUQik9VIKZ4caygT4kRK8VnvBR6pLeWWMRDbMhIVoFcC5fnMwljyFq8he+t3vNOZHbNn5/53115eOvv8TDGpnQuE+UWKAljsDxXDzDV7orhnu34ZffQ5cLnEjEay+abGPAzgcPN93Lh3oWI3UgBAAJ4AgBRHKuXBEhjsDu9AaQ/2S3fALxNdGlURuF5Nsxm1uERSPaEw+5tz5B0AJvBMIlKPJvBJKTvhwDWITwfAHzl2qZuAMAPaJLt71ivASBW+n7yEwCsYQIBoCv4QRIgKShdA/ADrFcAEMzomc1PgQIIDAEslwhmBDAzPAcARJtgV4Pk9pjzszMAwZwLQwDRIgkQbqoyjAlgsXkwjEpqf3FVrsRJhD/bfOldeQ8A3sHqPl6NCOAnwNXNVna8gdOHvA+svHfPg0c/Mntn+d1f+CW8/DIeewuvv44vf9lta3eMCz5/57qQOf0lHkyE+x6c/ftf/IcjCeZwOBxjR8rfTQQgODtdDi2Ow+FwTAcpnxQRMAjfdfMlh8Ph2ImUOzgVCjWzMuelw+FwOADsmsInS68SDCyNw+FwTAgpX2NXADoLwrLfHA6HwwEAQin51lqgGpf95nA4HA4AgAC7XuFQE7s35B0Oh2MnsnklZAs1EM8twTscDsc+BGTpLD4IiNIfHA6HwwEA8BHDmzGOCoetFE5/clxWjGmyX01ka3xVeyRGZWjPydktiQ2Qky2XaClZSUiqqog0y2MVIdN/K4qUi7l6uR0UBhXKJ5dcAzF2SZLKY8t8z7P216KodZuQxcbToJ0Un6qVosWPQ8TzrXMfLFGo4vHF127c/NU3G6ThmBZ1j41RVWNMtiWlF0I0jsG25mJ/TuOs2LtUtVSMBnks/d6qgFpS5USyf+qGioLZEsspr1y0tSTJhq8uhsVWVk5Ppfpof1Q277m81CqKrNjFZ+sO5LVSzCH3GQ1D4xfulVOF71NjfOlLbeJ3XE5SNVf8snoMRQOzcWwpOZGs+dkgnj0y5L7cb3BhY5TlRDLG2D5fPZs2cDGDFR/fFWebB7PVV1Tu+x/v8Ki33EjQVbQH0wIgv/2bdwUIZiVBw7XO5rzxa+7Oj0tO0YrRHeSe2tVY90SyK3BRkqJBcVBVYYeFlVPTu3J3UNRSsfcHLiadipfTINUlaTbx3EPF8sk9lZW/KFKVSPY3icZ11GborZuiDwCanhGdJ1zrfMEXX72BE958/IcNxHKMHC2M3rpjJpU2oCoNtBiJbuatqD/xrBgya+vpbkcbDmXhYJisU3WPWs86JbLRZrui7hg/csKk5ZlNLqu2qhRjlfC78p6VueiOKGZzf3aKgbXMy7EnL5qx34tzgj0PHoyzSoopPoArD+D0ZyaYS7QuWUoK1zo/4fJcX3j1OlTXSp2HC//nH/z+3+HTeP3lJORjb+XTe/315IM7xnHM5Br0wblVqfmT0wj7I0lnr8iog106paKqKhUsqz2z8rSZ1GfJ9recPxc7tKcNk+uoVeTpxOrcU4DV5dllbKaf98ewx3LMUquODmrbUorDSYOGkaT6p3duLOaM1juzFszozxCuEK6TZqH2iOA0RVO2ofQimbQZbSWs6R/2rmhq7qfNkePJaeJF8dKzTJn8CVss9o6DJAYiPYbdFhUz1+jlrj5Iv0k/UaGSHAudRruH9DD5st9QurXh4uj19Jvti/6I7QfT0PayKyQXbwOwZ6zbP3yf60g+9+Qbe6XNryQcVKBZpZBqhLQRV+xCucC5FaRsDKUpHhQsm5GKMdRlf7S7zOFaZVUaGzIuyFr52lVEteIpBs7laH8TKirQ/bV/kGblicKiZTO3bHIi/Y/++s0bT12/cp+cn5Zr0HCt4RoUBDMRD2Tb1asE5v7aEefeNb1WSZaSmVTVjLwxHSa0adzAbMb47RpOQ7TwH3Vr4pVO/0vTshStiQ7XKPawS7CcvkOmTKq7LFLqTtV30dVUN0dOpP0ScnvlsLSp1BKsqwJpVhoXz3z19sO+7y1OuDxz2z8vCX7AaG1Wp/d98be+tytMqWmzhzR8drSvZcLkbN6DMRw0T4qT5f2PF+Fmu+j+vBczUmr75OTJfW5g+JQ+0sDyOjh7QIXqK0ZS6qzYRWnkRWu9+uONpxcHE63SKi5++/xTb4nK8lyvXBtm8Hb0ThRqMJfZtfd3BciZNgcH85z/q6iVqnij6poMVbrEfvs0G6yUWvIUE81+LmqTYvwNDJ/iI3WLcZcZu+tz9UiKhu2eQjbGWCf4riR2Pd6tVsoJ0Di5rab5mSfeOF35q3OdLTibOyV6GTBGhfjaq9f7S6L6DM52nmLIg1PUtDUXm3Wx/+9XWN3C7WWZKtqzP0k6eXbPcJXb6Zl+th8OKt+sblLVustibabq/cVWUugvvYQfP3JdhCfXGEcIV91K7hia2ZzhWsMo/vxT+a0SpbPpPZROl6rMkZEuPJZpt4OT9Nx0Nbu+hG2FdXCKtyd3zVy3uwzwKhmpmGh7t8me8NXLB4UNTLlX0fYPfjlKfQgH/ae52LpaQdoTcn+EO0etr3/rn8cmNKqzhfgBTIwoVBOXrIM7xs/iClfnSqOfefJu9vtOOmFxwaQKtXTBLr9hlY2fOe2zy1ZqvPBV9HjmhMx9X7fD7/G0ZuM/WAVVHKDV2e+9zb4RnyvwKgNwaR11VZ5ZCbN5adYqDhTci3/7oDkNSCoJ1ZMTSa5CJ6AwBqpQBTRRrHZLXzb+NMvJB24ex8WHnEQlMrH0Y93c1OHSjRPzK/LTHxuB0ZO7Nx9Pvqy7+FOlJ1ch19YPilGadEXzE3tt204ovqy5K6E9e612kct7OgUuflldyFLlVZ1c7dsv0+n8wQdTSkfBWhXUyQrSQbH34O//+ebj79gPf/QXH77y0GqlVLvPUChUNQSQ7NSEWikyZUTYfY8ENdGgajdJKpL9lAqQVusSF3syNdGwTPZU2oOhqMnvSWrJv2mCuimLnE8uEYXJv0h9L9kSTEXTzC5RAxKUZK+p7nDatD32T1Ec/HKZ2Bob0s+5Ha+Zokg6BrgpSZy+Zzxf1DBcPgy8hUzTRzWXUGmYuv6dNHytfsLM3pdUfWidteP02VrSVqeZHVRFnpyFWGXWWRpJ+kgzkzObaKqvi7VwMIbqgfujq21haGmzfeUvP3b/SQicnRoP4i8CExqjsWIGAL5BGJO+BCIahipiPC9erWLPe4iz95fveScyu+afR/L+2vMfWjy6nr2z/O4b7+Gxt/D663juywPuwrwnyVln1c3PbPjqDtDcg7vEQOWdLgcn78Wn+jA/UcfOrStP9WKvtfdojw+klF2mbq1hrOguKJrSBzOyK19t5u91E83iVNS9S63VA+xQWNkHd/Wf/b294r7IYk+r0m/3qN3SJNh0Q2j288GF7NxTu/Kb+7U4cS568Uq9hMV4Snf176m+XUVdcddBaU617C2mPQWSjWqXH2OPcZ3+ZGczXQ2rB6bwjnuEunO6Wi+QZD30RW997qkqkhT7YWmw7Ly1Ssz9TfCzZHtyLWGyQwvLVmZKI8x9uWsSvX9en3sq/SzbJ1XvaQDFCIsDcMWqT6u1YglkAxwcROvSy4zGMX4au35yPS3XCUsfKbbXBk6o/abNLlGrxNzykVpPNe66xclvrXTTSFAYfko/Z8mdyVIM1kCMlgs42YGkVrq5D+1xCvQepZlyyU2xq++FzhmhbcTIcWCbXv9GZa3BILv2UjF+G7iYzaIFdzASkrmdm9nyqb7wlU20gRhoqgGzMjR4KpUh+7llC3FT+HuU7FSoOqV9rIrryjqediVXywgt9t491M3gLg/sQaoUgiV1YlbRtnucwjaqKpXIDXvEriLJrl9lc8B+laiKbxZUeaqBVLtStwNeh0aow+FwOBwOh8PhcDgcDofD4XA4HA6Hw7HN/weMKV6yCmVuZHN0cmVhbQplbmRvYmoKeHJlZgowIDE0CjAwMDAwMDAwMDAgNjU1MzUgZiAKMDAwMDAwMDAwOSAwMDAwMCBuIAowMDAwMDAwMDc0IDAwMDAwIG4gCjAwMDAwMDAxMjAgMDAwMDAgbiAKMDAwMDAwMDM0NCAwMDAwMCBuIAowMDAwMDAwMzgxIDAwMDAwIG4gCjAwMDAwMDA1NzAgMDAwMDAgbiAKMDAwMDAwMDY3MyAwMDAwMCBuIAowMDAwMDAyMzYwIDAwMDAwIG4gCjAwMDAwMDI0NjcgMDAwMDAgbiAKMDAwMDAwMjU3NSAwMDAwMCBuIAowMDAwMDAyNjg4IDAwMDAwIG4gCjAwMDAwMDI4MDQgMDAwMDAgbiAKMDAwMDAxMTg5NCAwMDAwMCBuIAp0cmFpbGVyCjw8Ci9TaXplIDE0Ci9Sb290IDEgMCBSCi9JbmZvIDUgMCBSCi9JRFs8M2Q2OTJmNzRmODI1YmE1MWZjMjA5YzY4MzVhNjY3NTY+PDNkNjkyZjc0ZjgyNWJhNTFmYzIwOWM2ODM1YTY2NzU2Pl0KPj4Kc3RhcnR4cmVmCjIzNTY4CiUlRU9GCg==";
////        byte[] decodedBytes = Base64.getDecoder().decode(encodedUrl);
////        String result = new String(decodedBytes);
////        System.out.println(result);
//
//        File file = new File("./test.pdf");
//
//        try ( FileOutputStream fos = new FileOutputStream(file); ) {
//            // To be short I use a corrupted PDF string, so make sure to use a valid one if you want to preview the PDF file
//            //String b64 = "JVBERi0xLjUKJYCBgoMKMSAwIG9iago8PC9GaWx0ZXIvRmxhdGVEZWNvZGUvRmlyc3QgMTQxL04gMjAvTGVuZ3==";
//            byte[] decoder = Base64.getDecoder().decode(encodedUrl);
//
//            Toast.makeText(this, "mmmmmmmm", Toast.LENGTH_SHORT).show();
//            fos.write(decoder);
//            System.out.println("PDF File Saved");
//        } catch (Exception e) {
//            Toast.makeText(this, "ex", Toast.LENGTH_SHORT).show();
//            e.printStackTrace();
//        }

//
       // String encodedUrl = "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";


//        final File dwldsPath = new File(getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS) + "/Tooros_Booking1.pdf");
//        byte[] pdfAsBytes = Base64.getDecoder().decode(encodedUrl);
//        FileOutputStream os;
//        os = new FileOutputStream(dwldsPath, false);
//        os.write(pdfAsBytes);
//        os.flush();
//        os.close();
/////storage/emulated/0/Android/data/my.awesome.tooros/files/Download/Tooros_Booking1.pdf
//       Toast.makeText(this, "done", Toast.LENGTH_SHORT).show();


//        File file = new File(getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS).getPath() + "/Tooros_Booking1.pdf");
//        Uri path = Uri.fromFile(file);
//      //  Log.i("Fragment2", String.valueOf(path));
//        Intent pdfOpenintent = new Intent(Intent.ACTION_VIEW);
//        pdfOpenintent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        pdfOpenintent.setDataAndType(path, "application/pdf");
//        try {
//          this.startActivityForResult(pdfOpenintent,0);
//        } catch (ActivityNotFoundException e) {
//
//        }



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
            super.onBackPressed();
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
                Toast.makeText(this, "Logged Out Successfully", Toast.LENGTH_SHORT).show();
                SharedPreferences sharedPreferences = this.getSharedPreferences("loginOrNot", MODE_PRIVATE);
                final SharedPreferences.Editor myEdit = sharedPreferences.edit();
                myEdit.putString("info","no");
               // myEdit.putString("username","Guest_User");
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
