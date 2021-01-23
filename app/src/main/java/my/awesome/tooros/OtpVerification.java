package my.awesome.tooros;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class OtpVerification extends AppCompatActivity {
//    String HttpURL = "https://www.cakiweb.com/tooros/api/api.php";
String HttpURL = "https://tooros.in/api/api.php";
TextView mobileno;
EditText otp;
    String phone;
    String finalResult ;
    int pay=0;
    ProgressDialog progressDialog;

    JsonHttpParse jsonhttpParse = new JsonHttpParse();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_verification);
        mobileno=findViewById(R.id.phone);
        otp=findViewById(R.id.otp);

        Intent intent=getIntent();
         phone=intent.getExtras().getString("phone");
        pay = intent.getIntExtra("val",0);
        mobileno.setText(phone);



//        SharedPreferences sharedPreferences = OtpVerification.this.getSharedPreferences("profile", MODE_PRIVATE);
//        String phone1= sharedPreferences.getString("phone",null);
//        if(phone1!=null){
//            mobileno.setText(phone1);
//        }else{
//            Toast.makeText(OtpVerification.this, "Enter your phone no in signup page!!!", Toast.LENGTH_SHORT).show();
//        }
    }


    public void onClickverify(View view) {

        String otpvalue=otp.getText().toString().trim();

        verifyRegistration("verifyRegistration",phone,otpvalue);


    }
    public void verifyRegistration(final String method,final String phone, final String otp){

        class UserLoginClass extends AsyncTask<String,Void,String> {


            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                progressDialog = ProgressDialog.show(OtpVerification.this,"Verifying OTP...",null,true,true);
            }

            @Override
            protected void onPostExecute(String httpResponseMsg) {

                super.onPostExecute(httpResponseMsg);

                progressDialog.dismiss();

                // Toast.makeText(Signup.this, httpResponseMsg, Toast.LENGTH_SHORT).show();

                if(httpResponseMsg.contains("200")){

                    Toast.makeText(OtpVerification.this, "Account activated successfully...", Toast.LENGTH_SHORT).show();



                    try {
                        JSONObject jsonObject = new JSONObject(httpResponseMsg);
                        JSONArray result = jsonObject.getJSONArray("result");
                        for (int i=0; i<result.length(); i++ ){
                            JSONObject ob=result.getJSONObject(i);

                            SharedPreferences sharedPreferences2 = OtpVerification.this.getSharedPreferences("MySharedPref2", MODE_PRIVATE);
                            final SharedPreferences.Editor myEdit = sharedPreferences2.edit();
                            myEdit.putString("Name",ob.getString("name"));
                            myEdit.putString("Mobile",ob.getString("mobile"));
                            myEdit.putString("Email",ob.getString("email"));
                            myEdit.putString("Dob",ob.getString("dob"));
                            myEdit.putString("Dlno",ob.getString("dl_no"));
                            myEdit.putString("Aadharno",ob.getString("aadhar_no"));
                            myEdit.putString("Aadhardoc",ob.getString("aadhar_doc"));
                            myEdit.putString("Dldoc",ob.getString("dl_doc"));
                            myEdit.putString("userid",ob.getString("user_id"));

                            myEdit.apply();


//                                //to check if user is already login or not
//                                SharedPreferences sharedPreferencesForLoginOrNot = Login.this.getSharedPreferences("loginOrNot", MODE_PRIVATE);
//                                final SharedPreferences.Editor loginedit = sharedPreferencesForLoginOrNot.edit();
//                                loginedit.putString("info","yes");
//                                // loginedit.putString("username","Aman");
//                                loginedit.apply();

                            // Toast.makeText(Login.this, ""+pay1, Toast.LENGTH_SHORT).show();
                            if(pay==1) {
                                finish();
                                startActivity(new Intent(OtpVerification.this, PaymentPage.class));
                            }else{
                                finish();
                                startActivity(new Intent(OtpVerification.this, CitySelectionActivity.class));
                            }




                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }





                }else{
                    JSONObject jsonObject = null;
                    try {
                        jsonObject = new JSONObject(httpResponseMsg);
                        String messege = jsonObject.getString("msg");
                        Toast.makeText(OtpVerification.this, messege, Toast.LENGTH_SHORT).show();


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }

            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            protected String doInBackground(String... params) {


                String jsonInputString="{\"method\":\"verifyRegistration\",\"mobile\":\""+phone+"\",\"otp_value\":\""+otp+"\"}";

//                finalResult = jsonhttpParse.postRequest(method,Email,Password, HttpURL);
                finalResult = jsonhttpParse.postRequest(jsonInputString, HttpURL);

                return finalResult;
            }
        }

        UserLoginClass userLoginClass = new UserLoginClass();

        userLoginClass.execute(method,phone,otp);
    }

    public void resendOtpClick(View view) {

        resendVerificationOTP("resendVerificationOTP",phone);

    }

    private void resendVerificationOTP(String method, final String phone) {

        class UserLoginClass extends AsyncTask<String,Void,String> {


            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                progressDialog = ProgressDialog.show(OtpVerification.this,"Loading...",null,true,true);
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

//                        String msg=jsonObject2.getString("msg");
//
//                        Toast.makeText(OtpVerification.this, ""+msg, Toast.LENGTH_LONG).show();

                        Toast.makeText(OtpVerification.this, "OTP Sent", Toast.LENGTH_SHORT).show();



                    }else{

                        String messege = jsonObject2.getString("msg");
                        Toast.makeText(OtpVerification.this, messege, Toast.LENGTH_SHORT).show();

                    }



                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            protected String doInBackground(String... params) {


                String jsonInputString="{\"method\":\"resendVerificationOTP\",\"mobile\":\""+phone+"\"}";

                //  Toast.makeText(PaymentPage.this, ""+book_id, Toast.LENGTH_SHORT).show();

//                finalResult = jsonhttpParse.postRequest(method,Email,Password, HttpURL);
                finalResult = jsonhttpParse.postRequest(jsonInputString, HttpURL);

                return finalResult;
            }
        }

        UserLoginClass userLoginClass = new UserLoginClass();

        userLoginClass.execute(method,phone);

    }
}
