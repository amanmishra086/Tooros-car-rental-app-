package my.awesome.tooros;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class BookingHistory extends AppCompatActivity {

    ProgressDialog progressDialog;

    JsonHttpParse jsonhttpParse = new JsonHttpParse();
    String finalResult ;
    String HttpURL = "https://www.cakiweb.com/tooros/api/api.php";

    RecyclerView recyclerView;
    ArrayList<Model_bookinghistory> arrayList = new ArrayList<Model_bookinghistory>();
    Adapter_bookinghistory adapter_bookinghistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_history);

        SharedPreferences sharedPreferences2 = BookingHistory.this.getSharedPreferences("MySharedPref2", MODE_PRIVATE);
        String mobile="";
        if (sharedPreferences2 != null) {
         mobile = sharedPreferences2.getString("Mobile", null);
        }


        recyclerView=findViewById(R.id.bookingrecycler);

        adapter_bookinghistory=new Adapter_bookinghistory(arrayList,BookingHistory.this);
        recyclerView.setAdapter(adapter_bookinghistory);

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(BookingHistory.this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);

        bookingHistory("bookingHistory",mobile);


    }

    public void bookingHistory(final String method,final String mobile_number){

        class UserLoginClass extends AsyncTask<String,Void,String> {


            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                progressDialog = ProgressDialog.show(BookingHistory.this,"Loading...",null,true,true);
            }

            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            protected void onPostExecute(String httpResponseMsg) {

                super.onPostExecute(httpResponseMsg);

                progressDialog.dismiss();

                JSONObject jsonObject2 = null;
                try {
                    jsonObject2 = new JSONObject(httpResponseMsg);
                    String status = jsonObject2.getString("status");

                    if(status.equals("200")){

                        try {

                            JSONArray result = jsonObject2.getJSONArray("result");
                            for (int i=0; i<result.length(); i++ ){
                                JSONObject ob=result.getJSONObject(i);


                                Model_bookinghistory model_bookinghistory=new Model_bookinghistory(ob.getString("pickupTime"),ob.getString("dropupTime")
                                        ,ob.getString("booked_car"),ob.getString("amount"),ob.getString("booking_id"));
                                arrayList.add(model_bookinghistory);
                            }
//
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        recyclerView.setAdapter(adapter_bookinghistory);
                        adapter_bookinghistory.notifyDataSetChanged();


                    }else{

                        String messege = jsonObject2.getString("msg");
                        Toast.makeText(BookingHistory.this, messege, Toast.LENGTH_SHORT).show();

                    }



                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            protected String doInBackground(String... params) {


                String jsonInputString="{\"method\":\"bookingHistory\",\"mobile_number\":\""+mobile_number+"\"}";

                //  Toast.makeText(PaymentPage.this, ""+book_id, Toast.LENGTH_SHORT).show();

//                finalResult = jsonhttpParse.postRequest(method,Email,Password, HttpURL);
                finalResult = jsonhttpParse.postRequest(jsonInputString, HttpURL);

                return finalResult;
            }
        }

        UserLoginClass userLoginClass = new UserLoginClass();

        userLoginClass.execute(method,mobile_number);
    }

}
