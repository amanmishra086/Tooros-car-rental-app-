package my.awesome.tooros;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
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

import org.json.JSONException;
import org.json.JSONObject;

public class PaymentPage extends AppCompatActivity implements PaymentResultListener {
TextView gst,total,basefair,coupondiscount,picupcharges,weekdaychages,totalcharges,startdate,enddate,timeduration,carname,startt,endt,geartype,fuel;
ImageView carimage;
EditText Couponcode;
Button book,apply;
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
        totalcharges=findViewById(R.id.textView22);
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
       Intent intent=getIntent();
       Bundle bundle=getIntent().getExtras();
       if(bundle!=null){
           int resid=bundle.getInt("carimage");
           carimage.setImageResource(resid);
       }
       String carn=intent.getExtras().getString("carname");
       String  geart=intent.getExtras().getString("geartype");
       String fuelt=intent.getExtras().getString("fuel");
       String base_fare=intent.getExtras().getString("cost");
       String weekendcost=intent.getExtras().getString("weekendcost");
       carname.setText(""+carn);
       geartype.setText(""+geart);
       fuel.setText(""+fuelt);
       basefair.setText(""+base_fare);
       weekdaychages.append(""+weekendcost);
       float gstpercentage=5.0f;
       float cost=Float.parseFloat(base_fare);
       float gstcost=(cost*(gstpercentage/100));
       gst.setText(""+gstcost);
       total.setText(""+(cost+gstcost));

        Toast.makeText(PaymentPage.this, ""+carn, Toast.LENGTH_SHORT).show();
        SharedPreferences sharedPreferences = PaymentPage.this.getSharedPreferences("Date", MODE_PRIVATE);
        String stdate= sharedPreferences.getString("startdate",null);
        String end= sharedPreferences.getString("Enddate",null);
        String startime=sharedPreferences.getString("starttime",null);
        String endtime=sharedPreferences.getString("endtime",null);
        int duration=sharedPreferences.getInt("dif",0);
        String dur=String.valueOf(duration);
      //  timeduration.append(" hrs");
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
        if(stdate!=" "&& end !=" "){
            startdate.setText(stdate);
            enddate.setText(end);
            startt.setText("09:00");
            endt.setText("09:00");
//            timeduration.setText(""+dur);
//            timeduration.append(" hrs");
        }else{

        }
        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //take coupon code and recive amount from api
            }
        });
        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //take all input send to api and proceed for payment

                bookCab("bookCab");
                startPayment(Float.parseFloat(total.getText().toString()));



            }
        });

    }

    public void bookCab(final String method){

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

                Toast.makeText(PaymentPage.this, ""+httpResponseMsg, Toast.LENGTH_SHORT).show();

                if(msg){

                   // Toast.makeText(PaymentPage.this, "helloooooo", Toast.LENGTH_SHORT).show();

                    JSONObject jsonObject = null;
                    try {
                        jsonObject = new JSONObject(httpResponseMsg);
                        //  String messege= jsonObject.getString("otp");
                        String booking_id= jsonObject.getString("booking_id");
                        String payment_id= jsonObject.getString("payment_id");

//                       Intent intent=new Intent(Signup.this,Login.class);
//                      //  intent.putExtra("phone",strphone);
//                      //  intent.putExtra("otp",messege);
//                        startActivity(intent);

                        Toast.makeText(PaymentPage.this, booking_id, Toast.LENGTH_SHORT).show();
                        //Toast.makeText(PaymentPage.this, "Bookcab method done", Toast.LENGTH_SHORT).show();


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

                String jsonInputString="{\"method\":\"bookCab\",\"car_id\":\"6\",\"city\":\"1\",\"pickup_date\":\"2021-01-15\",\"pickup_time\":\"09:00 AM\",\"2021-01-16\":\"2021-10-28\",\"dropup_time\":\"21:00 PM\",\"booking_amount\":\"5026.67\",\"name\":\"Aman\",\"mobile\":\"9636730565\",\"email\":\"abc@gmail.com\",\"message\":\"api testing\",\"coupon\":\"\",\"dlno\":\"DL123456\",\"dob\":\"25-03-2000\",\"security\":\"Online\"}";
//                finalResult = jsonhttpParse.postRequest(method,Email,Password, HttpURL);
                finalResult = jsonhttpParse.postRequest(jsonInputString, HttpURL);

                return finalResult;
            }
        }

        BookCabClass bookCabClass = new BookCabClass();

        bookCabClass.execute(method);
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

            options.put("name",""+"Aman mishra");
            options.put("description", "Confirm Booking..");
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");
            //options.put("order_id", "order_DBJOWzybf0sJbb");//from response of step 3.
            options.put("theme.color", "#FF0000");
            options.put("currency", "INR");
            options.put("amount", ""+total*100);//pass amount in currency subunits
            options.put("prefill.email",""+"abc@gmail.com");
            options.put("prefill.contact",""+"9636730565");
            checkout.open(activity, options);
        } catch(Exception e) {
            Log.e(TAG, "Error in starting Razorpay Checkout", e);
        }
    }

    @Override
    public void onPaymentSuccess(String s) {
        Toast.makeText(this, "Payment success--"+s, Toast.LENGTH_SHORT).show();
        //UserLoginFunction1();
    }

    @Override
    public void onPaymentError(int i, String s) {

        // Toast.makeText(this, i+"-"+s, Toast.LENGTH_SHORT).show();
        try {
            Toast.makeText(this, "error is coming--"+String.valueOf(i)+"--"+s, Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            Toast.makeText(this, "Exception is coming--"+e, Toast.LENGTH_SHORT).show();
        }

    }
}
