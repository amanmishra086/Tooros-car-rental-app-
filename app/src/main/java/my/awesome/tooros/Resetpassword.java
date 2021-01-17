package my.awesome.tooros;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;

public class Resetpassword extends AppCompatActivity {
TextView mobile;
EditText otp;
String strotp,strmobile;
//    String HttpURL = "https://www.cakiweb.com/tooros/api/api.php";
String HttpURL = "https://tooros.in/api/api.php";
    String finalResult ;
    Boolean CheckEditText ;
    ProgressDialog progressDialog;
    JsonHttpParse jsonhttpParse = new JsonHttpParse();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resetpassword);
        mobile=findViewById(R.id.emailcontainer);
        otp=findViewById(R.id.passwordcontainer);
        Intent intent=getIntent();
        strmobile=intent.getStringExtra("mobile");
        mobile.setText(strmobile);
    }
    public void forgetPasswordClick(View view) {

        strotp=otp.getText().toString().trim();
        Intent intent=getIntent();
        strmobile=intent.getStringExtra("mobile").trim();
        if(strotp.length()!=0&&strmobile.length()!=0){
            forgetPassword("verifyOTPforResetpass",strmobile,otp.getText().toString());
        }else{
            Snackbar.make(view, " Please Enter Otp Sent On your Number ..", Snackbar.LENGTH_LONG)
                    .setActionTextColor(getResources().getColor(android.R.color.holo_green_dark))
                    .show();
        }




    }

    private void forgetPassword(String method, final String email,final String otp1) {

        class UserLoginClass extends AsyncTask<String,Void,String> {


            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                progressDialog = ProgressDialog.show(Resetpassword.this,"Loading...",null,true,true);
            }

            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            protected void onPostExecute(String httpResponseMsg) {

                super.onPostExecute(httpResponseMsg);
               // Toast.makeText(Resetpassword.this, ""+httpResponseMsg, Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();

                JSONObject jsonObject2 = null;
                try {
                    jsonObject2 = new JSONObject(httpResponseMsg);
                    String status = jsonObject2.getString("status");

                    if(status.equals("200")){
                       Intent intent =new Intent(Resetpassword.this,newpassword.class);
                        intent.putExtra("mobile",""+email);
                        startActivity(intent);
                        String msg=jsonObject2.getString("msg");

                        Toast.makeText(Resetpassword.this, ""+msg, Toast.LENGTH_LONG).show();



                    }else{

                        String messege = jsonObject2.getString("msg");
                        Toast.makeText(Resetpassword.this, messege, Toast.LENGTH_SHORT).show();

                    }



                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            protected String doInBackground(String... params) {

                String jsonInputString="{\"method\":\"verifyOTPforResetpass\",\"mobile\":\""+email+"\",\"otp_value\":\""+otp1+"\"}";

                //  Toast.makeText(PaymentPage.this, ""+book_id, Toast.LENGTH_SHORT).show();

//                finalResult = jsonhttpParse.postRequest(method,Email,Password, HttpURL);
                finalResult = jsonhttpParse.postRequest(jsonInputString, HttpURL);

                return finalResult;
            }
        }

        UserLoginClass userLoginClass = new UserLoginClass();

        userLoginClass.execute(method,email,otp1);


    }
}
