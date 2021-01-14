package my.awesome.tooros;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;

public class ForgetPassword extends AppCompatActivity {

    EditText email;
    String HttpURL = "https://www.cakiweb.com/tooros/api/api.php";
    Button signin;
    String stremail, strpassword;
    String finalResult ;
    Boolean CheckEditText ;
    ProgressDialog progressDialog;
    int pay1=0;
    JsonHttpParse jsonhttpParse = new JsonHttpParse();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        email=findViewById(R.id.emailcontainer);

    }

    public void onClickSubmitInBtn(View view) {

        stremail=email.getText().toString().trim();

        if(stremail.length()!=0){
            forgetPassword("chkResetPassword",email.getText().toString());
        }else{
            Snackbar.make(view, " Please Enter registered email or phone ..", Snackbar.LENGTH_LONG)
                    .setActionTextColor(getResources().getColor(android.R.color.holo_green_dark))
                    .show();

        }

    }

    private void forgetPassword(String method, final String email) {

        class UserLoginClass extends AsyncTask<String,Void,String> {


            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                progressDialog = ProgressDialog.show(ForgetPassword.this,"Loading...",null,true,true);
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
                        Intent intent =new Intent(ForgetPassword.this,Resetpassword.class);
                        intent.putExtra("mobile",""+email);
                        startActivity(intent);

                        String msg=jsonObject2.getString("msg");

                        Toast.makeText(ForgetPassword.this, ""+msg, Toast.LENGTH_LONG).show();



                    }else{

                        String messege = jsonObject2.getString("msg");
                        Toast.makeText(ForgetPassword.this, messege, Toast.LENGTH_SHORT).show();

                    }



                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            protected String doInBackground(String... params) {


                String jsonInputString="{\"method\":\"chkResetPassword\",\"mobile_number\":\""+email+"\"}";

                //  Toast.makeText(PaymentPage.this, ""+book_id, Toast.LENGTH_SHORT).show();

//                finalResult = jsonhttpParse.postRequest(method,Email,Password, HttpURL);
                finalResult = jsonhttpParse.postRequest(jsonInputString, HttpURL);

                return finalResult;
            }
        }

        UserLoginClass userLoginClass = new UserLoginClass();

        userLoginClass.execute(method,email);


    }

}
