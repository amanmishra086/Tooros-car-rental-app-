package my.awesome.tooros;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class PaymentPage extends AppCompatActivity implements PaymentResultListener {
TextView gst,total,basefair,coupondiscount,securitycharges,picupcharges,weekdaychages,weekendcharges,startdate,enddate,timeduration,carname,startt,endt,geartype,fuel;
ImageView carimage;
EditText Couponcode;
Button book,apply;
    String book_id,booking_id,payment_id,transaction_id;
    String name,email,mobile,dob,aadharno,dlno,userid;
    ProgressDialog progressDialog;

    JsonHttpParse jsonhttpParse = new JsonHttpParse();
    String finalResult ;
    String HttpURL = "https://www.cakiweb.com/tooros/api/api.php";

    private static final String TAG = PaymentPage.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_page);


        startdate=findViewById(R.id.startdate);
        enddate=findViewById(R.id.enddate);
        carimage=findViewById(R.id.carimage);
        weekdaychages=findViewById(R.id.textView21);
        weekendcharges=findViewById(R.id.textView22);
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
        geartype=findViewById(R.id.textView9);
        fuel=findViewById(R.id.textView10);
        apply=findViewById(R.id.apply);
        securitycharges=findViewById(R.id.security);
       Intent intent=getIntent();
       Bundle bundle=getIntent().getExtras();
       if(bundle!=null){
//           int resid=bundle.getInt("carimage");
//           carimage.setImageResource(resid);
           String url=bundle.getString("carimage");
           Picasso.with(this).load(url.replace("http","https")).fit().centerInside().into(carimage);
       }
       String carn=intent.getExtras().getString("carname");
       String  geart=intent.getExtras().getString("geartype");
       String fuelt=intent.getExtras().getString("fuel");
       String base_fare=intent.getExtras().getString("cost");
       String weekendcost=intent.getExtras().getString("weekendcost");
       final String car_id=intent.getExtras().getString("car_id");
       carname.setText(""+carn);
       geartype.setText(""+geart);
       fuel.setText(""+fuelt);
//       basefair.setText(""+base_fare);
//       weekdaychages.append(""+weekendcost);



        SharedPreferences sharedPreferences = PaymentPage.this.getSharedPreferences("Date", MODE_PRIVATE);
        final String stdate= sharedPreferences.getString("startdate",null);
        final String end= sharedPreferences.getString("Enddate",null);
        final String startime=sharedPreferences.getString("starttime",null);
        final String endtime=sharedPreferences.getString("endtime",null);
        int duration=sharedPreferences.getInt("dif",0);
        String dur=String.valueOf(duration);
      // timeduration.append(" hrs");
//        if(stdate!=" "&& end !=" "&&startime!=null&&endtime!=null&&dur!=null){
//            startdate.setText(stdate);
//            enddate.setText(end);
//            startt.setText(startime);
//            endt.setText(endtime);
//           timeduration.setText(""+dur);
//           timeduration.append(" hrs");
//        }else{
//
//        }
        SharedPreferences sharedPreferences2 = PaymentPage.this.getSharedPreferences("MySharedPref2", MODE_PRIVATE);
        if (sharedPreferences2 != null) {
            name = sharedPreferences2.getString("Name", null);
            mobile = sharedPreferences2.getString("Mobile", null);
            email = sharedPreferences2.getString("Email", null);
            dob = sharedPreferences2.getString("Dob", null);
            dlno = sharedPreferences2.getString("Dlno", null);
            aadharno = sharedPreferences2.getString("Aadharno", null);
            userid=sharedPreferences2.getString("user_id",null);

        }
        if(stdate!=" "&& end !=" "){
            startdate.setText(stdate);
            enddate.setText(end);
            startt.setText(startime);
            endt.setText(endtime);

        }else{

        }

        final String concatpdate=stdate+" "+startime;
        final String concatDdate=end+" "+endtime;

        getPriceDetails("getPriceDetails",car_id,concatpdate,concatDdate,"","Online");


        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // weekdaychages.append("000");


                //take coupon code and recive amount from api
            }
        });
        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //take all input send to api and proceed for payment
                SharedPreferences sharedPreferences = PaymentPage.this.getSharedPreferences("loginOrNot", MODE_PRIVATE);
               String check=sharedPreferences.getString("info",null);
               if(check=="no"||check==null) {
                   Intent intent=new Intent(PaymentPage.this,Login.class);
                   int pay=1;
                   intent.putExtra("val",pay);
                   startActivity(intent);
               }else {
                   bookCab("bookCab",stdate,end,startime,endtime,car_id,total.getText().toString());

                   beforePayment("beforePayment",book_id);

                   startPayment(Float.parseFloat(total.getText().toString()));
               }


            }
        });

    }
    public void getPriceDetails(final String method, final String car_id, final String concatpdate, final String concatDdate, String coupon, final String security){

        class BookCabClass extends AsyncTask<String,Void,String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                progressDialog = ProgressDialog.show(PaymentPage.this,"Loading Data",null,true,true);
            }

            @Override
            protected void onPostExecute(String httpResponseMsg) {

                super.onPostExecute(httpResponseMsg);

                progressDialog.dismiss();
                boolean msg=httpResponseMsg.contains("200");

              //  Toast.makeText(PaymentPage.this, ""+httpResponseMsg, Toast.LENGTH_SHORT).show();


                if(msg){

                    // Toast.makeText(PaymentPage.this, "helloooooo", Toast.LENGTH_SHORT).show();

                    JSONObject jsonObject = null;
                    try {
                        jsonObject = new JSONObject(httpResponseMsg);

                        JSONObject ob=new JSONObject(jsonObject.getString("result"));
                            String weekdays_hr=ob.getString("weekdays_hr");
                            String weekend_hr=ob.getString("weekend_hr");
                            String weekdays_rs=ob.getString("weekdays_rs");
                            String weekend_rs=ob.getString("weekend_rs");
                            String security_rs=ob.getString("security_rs");
                            String total_rs=ob.getString("total_rs");
                      //  Toast.makeText(PaymentPage.this, ""+jsonObject1.getString("total_rs"), Toast.LENGTH_SHORT).show();

                        //Toast.makeText(PaymentPage.this, ""+weekend_rs, Toast.LENGTH_SHORT).show();
                        String weekendprice;

                        weekendprice=""+(int)Float.parseFloat(weekend_rs);
                        basefair.setText(""+ (int)(Float.parseFloat(weekdays_rs)+Float.parseFloat(weekend_rs)));

                        float totalhr=Float.parseFloat(weekdays_hr)+Float.parseFloat(weekend_hr);
                        timeduration.setText(totalhr+" hrs");



                        weekdaychages.append(""+(int)Float.parseFloat(weekdays_rs));
                        weekendcharges.append(""+weekendprice);
                        securitycharges.setText(security_rs);


                        //total.setText(total_rs);

                        float gstpercentage=5.0f;
                        int cost= (int) Float.parseFloat(total_rs);
                        int gstcost= (int) (cost*(gstpercentage/100));
                        gst.setText(""+gstcost);
                        total.setText(""+(cost+gstcost));






                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else {

                    JSONObject jsonObject = null;
                    try {
                        jsonObject = new JSONObject(httpResponseMsg);
                        String messege = jsonObject.getString("msg");
                        Toast.makeText(PaymentPage.this, messege, Toast.LENGTH_SHORT).show();


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            protected String doInBackground(String... params) {



                //String jsonInputString="{\"method\":\"registerUser\",\"name\":\""+strname+"\",\"email\":\""+stremail+"\",\"mobile\":\""+strphone+"\",\"password\":\""+strpassword+"\",\"dl_no\":\""+strdlno+"\",\"aadhar_no\":\""+straadharcardno+"\",\"dob\":\""+strdob+"\"}";



                String jsonInputString="{\"method\":\"getPriceDetails\",\"car_id\":\""+car_id+"\",\"concatPdate\":\""+concatpdate+"\",\"concatDdate\":\""+concatDdate+"\",\"coupon\":\"\",\"security\":\"Online\"}";
//                finalResult = jsonhttpParse.postRequest(method,Email,Password, HttpURL);
                finalResult = jsonhttpParse.postRequest(jsonInputString, HttpURL);

                return finalResult;
            }
        }

        BookCabClass bookCabClass = new BookCabClass();

        bookCabClass.execute(method);
    }

    public void bookCab(String method, final String stdate, final String end, final String startime, final String endtime, final String car_id, final String price){

        class BookCabClass extends AsyncTask<String,Void,String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                progressDialog = ProgressDialog.show(PaymentPage.this,"Loading Data",null,true,true);
            }

            @Override
            protected void onPostExecute(String httpResponseMsg) {

                super.onPostExecute(httpResponseMsg);

                progressDialog.dismiss();
                boolean msg=httpResponseMsg.contains("200");

                //Toast.makeText(PaymentPage.this, ""+httpResponseMsg, Toast.LENGTH_SHORT).show();

                if(msg){

                   // Toast.makeText(PaymentPage.this, "helloooooo", Toast.LENGTH_SHORT).show();

                    JSONObject jsonObject = null;
                    try {
                        jsonObject = new JSONObject(httpResponseMsg);
                        //  String messege= jsonObject.getString("otp");
                         book_id= ""+jsonObject.getString("book_id");
                         booking_id= jsonObject.getString("booking_id");
                         payment_id= jsonObject.getString("payment_id");



                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else {

                    JSONObject jsonObject = null;
                    try {
                        jsonObject = new JSONObject(httpResponseMsg);
                        String messege = jsonObject.getString("msg");
                        Toast.makeText(PaymentPage.this, messege, Toast.LENGTH_SHORT).show();


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            protected String doInBackground(String... params) {


                //String jsonInputString="{\"method\":\"registerUser\",\"name\":\""+strname+"\",\"email\":\""+stremail+"\",\"mobile\":\""+strphone+"\",\"password\":\""+strpassword+"\",\"dl_no\":\""+strdlno+"\",\"aadhar_no\":\""+straadharcardno+"\",\"dob\":\""+strdob+"\"}";

                    String jsonInputString = "{\"method\":\"bookCab\",\"car_id\":\"" + car_id + "\",\"city\":\"1\",\"pickup_date\":\"" + stdate + "\",\"pickup_time\":\""+startime+"\",\"dropup_date\":\"" + end + "\",\"dropup_time\":\""+endtime+"\",\"booking_amount\":\"" + price + "\",\"name\":\"" + name + "\",\"mobile\":\""+mobile+"\",\"email\":\""+email+"\",\"message\":\"api testing\",\"coupon\":\"\",\"dlno\":\""+dlno+"\",\"dob\":\""+dob+"\",\"security\":\"Online\"}";
//                finalResult = jsonhttpParse.postRequest(method,Email,Password, HttpURL);
                    finalResult = jsonhttpParse.postRequest(jsonInputString, HttpURL);

                    return finalResult;
                }

        }

        BookCabClass bookCabClass = new BookCabClass();

        bookCabClass.execute(method);
    }
    public void beforePayment(final String method,final String book_id){

        class UserLoginClass extends AsyncTask<String,Void,String> {


            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                progressDialog = ProgressDialog.show(PaymentPage.this,"Loading...",null,true,true);
            }

            @Override
            protected void onPostExecute(String httpResponseMsg) {

                super.onPostExecute(httpResponseMsg);

                progressDialog.dismiss();

                JSONObject jsonObject2 = null;
                try {
                    jsonObject2 = new JSONObject(httpResponseMsg);
                    String status = jsonObject2.getString("status");

                    if(status.equals("200")){

                        transaction_id=jsonObject2.getString("transaction_id");

                    }else{

                        String messege = jsonObject2.getString("msg");
                        Toast.makeText(PaymentPage.this, messege, Toast.LENGTH_SHORT).show();

                    }



                } catch (JSONException e) {
                    e.printStackTrace();
                }



            }

            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            protected String doInBackground(String... params) {


                String jsonInputString="{\"method\":\"beforePayment\",\"book_id\":\""+book_id+"\"}";

              //  Toast.makeText(PaymentPage.this, ""+book_id, Toast.LENGTH_SHORT).show();

//                finalResult = jsonhttpParse.postRequest(method,Email,Password, HttpURL);
                finalResult = jsonhttpParse.postRequest(jsonInputString, HttpURL);

                return finalResult;
            }
        }

        UserLoginClass userLoginClass = new UserLoginClass();

        userLoginClass.execute(method,book_id);
    }

    private void afterPayment(final String method, final String booking_id, final String transaction_id, final String payment_id, String status, final String amount, String response_raw_data) {

        class UserLoginClass extends AsyncTask<String,Void,String> {


            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                progressDialog = ProgressDialog.show(PaymentPage.this,"Loading...",null,true,true);
            }

            @Override
            protected void onPostExecute(String httpResponseMsg) {

                super.onPostExecute(httpResponseMsg);

                progressDialog.dismiss();

                JSONObject jsonObject2 = null;
                try {
                    jsonObject2 = new JSONObject(httpResponseMsg);
                    String status = jsonObject2.getString("status");

                    if(status.equals("200")){



                    }else{

                        String messege = jsonObject2.getString("msg");
                        Toast.makeText(PaymentPage.this, messege, Toast.LENGTH_SHORT).show();

                    }



                } catch (JSONException e) {
                    e.printStackTrace();
                }

                // Toast.makeText(Signup.this, httpResponseMsg, Toast.LENGTH_SHORT).show();



            }

            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            protected String doInBackground(String... params) {


                String jsonInputString="{\"method\":\"afterPayment\",\"booking_id\":\""+booking_id+"\",\"transaction_id\":\""+transaction_id+"\",\"payment_id\":\""+payment_id+"\",\"status\":1,\"amount\":\""+amount+"\",\"response_raw_data\":\"\"}";

//                finalResult = jsonhttpParse.postRequest(method,Email,Password, HttpURL);
                finalResult = jsonhttpParse.postRequest(jsonInputString, HttpURL);

                return finalResult;
            }
        }

        UserLoginClass userLoginClass = new UserLoginClass();

        userLoginClass.execute(method);
    }


    public void startPayment(Float total) {

        /**
         * Instantiate Checkout
         */
        Checkout.preload(getApplicationContext());
        Checkout checkout = new Checkout();
        //checkout.setKeyID("rzp_test_YZ0rZv8DFWccCl");

        /**
         * Set your logo here
         */
        checkout.setImage(R.drawable.caricon);

        /**
         * Reference to current activity
         */
        final PaymentPage activity = this;

        /**
         * Pass your payment options to the Razorpay Checkout as a JSONObject
         */
        try {
            JSONObject options = new JSONObject();

            options.put("name",""+name);
            options.put("description", "Confirm Booking..");
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");
            //options.put("order_id", "order_DBJOWzybf0sJbb");//from response of step 3.
            options.put("theme.color", "#FF0000");
            options.put("currency", "INR");
            options.put("amount", ""+total*100);//pass amount in currency subunits
            options.put("prefill.email",""+email);
            options.put("prefill.contact",""+mobile);
            checkout.open(activity, options);
        } catch(Exception e) {
            Log.e(TAG, "Error in starting Razorpay Checkout", e);
        }
    }

    @Override
    public void onPaymentSuccess(String s) {

        progressDialog.dismiss();

        String status="1";

        String amount=total.getText().toString();

        afterPayment("afterPayment",booking_id,transaction_id,payment_id,status,amount,"");

        Intent intent=new Intent(this,ThankYou.class);
        intent.putExtra("invoice",s);
        intent.putExtra("startdate",startdate.getText());
        intent.putExtra("enddate",enddate.getText());
        intent.putExtra("starttime",startt.getText());
        intent.putExtra("endtime",endt.getText());
        startActivity(intent);
    }

    @Override
    public void onPaymentError(int i, String s) {

        progressDialog.dismiss();

        // Toast.makeText(this, i+"-"+s, Toast.LENGTH_SHORT).show();
        try {
            Toast.makeText(this, "error is coming--"+String.valueOf(i)+"--"+s, Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            Toast.makeText(this, "Exception is coming--"+e, Toast.LENGTH_SHORT).show();
        }

    }

    public void termsAndConditionClick(View view) {
        startActivity(new Intent(this,TermsAndCondition.class));
    }

    public void privacyPolicyClick(View view) {

        startActivity(new Intent(this,PolicyActivity.class));

    }
}
