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

import org.json.JSONException;
import org.json.JSONObject;

public class OtpVerification extends AppCompatActivity {
    String HttpURL = "https://www.cakiweb.com/tooros/api/api.php";
TextView mobileno;
EditText otp;String phone;
    String finalResult ;

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
                    Intent intent=new Intent(OtpVerification.this,Login.class);
                    startActivity(intent);



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
}
