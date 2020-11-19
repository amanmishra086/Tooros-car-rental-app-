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
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class CitySelectionActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    String HttpURL = "https://www.cakiweb.com/tooros/api/api.php";
    String finalResult ;
    ProgressDialog progressDialog;
    JsonHttpParse jsonhttpParse = new JsonHttpParse();
//
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    androidx.appcompat.widget.Toolbar toolbar;
//
    Calendar myEndCalendar;
    Calendar myStartCalendar;
    TextView startdate,enddate;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_selection);

//        senitization_recycler=findViewById(R.id.senitization_recycler);
        offer_recycler=findViewById(R.id.offer_recycler);

        drawerLayout=findViewById(R.id.drawer_layout);
        navigationView=findViewById(R.id.nav_view);
        toolbar=findViewById(R.id.toolbar);

        startdate=findViewById(R.id.startDate);
        enddate=findViewById(R.id.endDate);

        setSupportActionBar(toolbar);

        //hide or show items
      Menu menu = navigationView.getMenu();
      menu.findItem(R.id.nav_logout).setVisible(false);


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

                String myFormat = "dd/MM/yy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                String dateSelected=sdf.format(myStartCalendar.getTime());

                startdate.setText(dateSelected);


               // Toast.makeText(CitySelectionActivity.this,sdf.format(myStartCalendar.getTime()) , Toast.LENGTH_SHORT).show();
            }

        };
        enddatelistener = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myEndCalendar.set(Calendar.YEAR, year);
                myEndCalendar.set(Calendar.MONTH, monthOfYear);
                myEndCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                String myFormat = "dd/MM/yy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                String dateSelected=sdf.format(myStartCalendar.getTime());

                enddate.setText(dateSelected);

                //Toast.makeText(CitySelectionActivity.this,sdf.format(myEndCalendar.getTime()) , Toast.LENGTH_SHORT).show();
            }

        };
        //we have to fetch image here and set on guidline models



//
//        guidlines_adapter=new Guidlines_adapter(guidlines_models,CitySelectionActivity.this);
//        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(CitySelectionActivity.this,RecyclerView.HORIZONTAL,false);
//        senitization_recycler.setLayoutManager(linearLayoutManager);
//        Guidlines_model senitizationmodel=new Guidlines_model(R.drawable.senitization);
//        guidlines_models.add(senitizationmodel);
//        //  ServicesFunction("getAllService");//add service name
////all for offer recycler
        offer_adapter=new Offer_adapter(offer_model_arraylist,CitySelectionActivity.this);
        offer_recycler.setAdapter(offer_adapter);

        LinearLayoutManager linearLayoutManager1=new LinearLayoutManager(CitySelectionActivity.this,RecyclerView.HORIZONTAL,false);
        offer_recycler.setLayoutManager(linearLayoutManager1);
        Guidlines_model offer=new Guidlines_model(R.drawable.offertooros);
        offer_model_arraylist.add(offer);
        Guidlines_model offer2=new Guidlines_model(R.drawable.offertooros);
        offer_model_arraylist.add(offer2);



     //   gettingOffersFunction("getAllOffers");//add service name

    }
//add service name accordingly
   /* public void ServicesFunction(String getAllService) {
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
                                 //   ob.getString("service_name"),ob.getString("sch_servie_id"));

                           // androidFlavors.add(history);
                        }


//
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    senitization_recycler.setAdapter(guidlines_adapter);
                    guidlines_adapter.notifyDataSetChanged();



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

        UserLoginClass userLoginClass = new UserLoginClass();

        userLoginClass.execute(getAllService);
    }
    public void   gettingOffersFunction(String getAllOffers) {
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

        new DatePickerDialog(CitySelectionActivity.this, startdatelistener, myStartCalendar
                .get(Calendar.YEAR), myStartCalendar.get(Calendar.MONTH),
                myStartCalendar.get(Calendar.DAY_OF_MONTH)).show();


    }

    public void onClickEndDate(View view) {
        new DatePickerDialog(CitySelectionActivity.this, enddatelistener, myEndCalendar
                .get(Calendar.YEAR), myEndCalendar.get(Calendar.MONTH),
                myEndCalendar.get(Calendar.DAY_OF_MONTH)).show();

    }
    public void onClickFindCarButton(View view) {
        Toast.makeText(this, "No Car Found !!", Toast.LENGTH_SHORT).show();

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
        }

        drawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }



}
