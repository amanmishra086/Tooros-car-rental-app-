package my.awesome.tooros;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class Login extends AppCompatActivity {
    EditText email,password;
    String HttpURL = "https://www.cakiweb.com/mechanic/json-api/api.php";
    Button signin;
    String stremail, strpassword;
    String finalResult ;
    Boolean CheckEditText ;
    ProgressDialog progressDialog;

    JsonHttpParse jsonhttpParse = new JsonHttpParse();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email=findViewById(R.id.emailcontainer);
        password=findViewById(R.id.passwordcontainer);
        signin=findViewById(R.id.loginbtn);
        // public static final String UserEmail = "";


//        stremail=email.getText().toString();
//        strpassword=password.getText().toString();


        }

        public void SignUpPageClick(View view) {
            startActivity(new Intent(Login.this,Signup.class));



        }

        public void onClickSignInBtn2(View view){



        }



        public void onClickSignInBtn(View view) {
            CheckEditTextIsEmptyOrNot();

            if(CheckEditText){


                UserLoginFunction("login",stremail,strpassword);

            }
            else {

                Toast.makeText(Login.this, "Please fill all form fields.", Toast.LENGTH_LONG).show();

            }

        }


        public void CheckEditTextIsEmptyOrNot(){

            stremail = email.getText().toString();
            strpassword = password.getText().toString();

            if(TextUtils.isEmpty(stremail) || TextUtils.isEmpty(strpassword))
            {
                CheckEditText = false;
            }
            else {

                CheckEditText = true ;
            }
        }

        public void UserLoginFunction(final String method,final String Email, final String Password){

            class UserLoginClass extends AsyncTask<String,Void,String> {


                @Override
                protected void onPreExecute() {
                    super.onPreExecute();

                    progressDialog = ProgressDialog.show(Login.this,"Loading Data",null,true,true);
                }

                @Override
                protected void onPostExecute(String httpResponseMsg) {

                    super.onPostExecute(httpResponseMsg);

                    progressDialog.dismiss();

                    // Toast.makeText(Signup.this, httpResponseMsg, Toast.LENGTH_SHORT).show();

                    if(httpResponseMsg.contains("200")){
                        // Toast.makeText(Signup.this, httpResponseMsg, Toast.LENGTH_SHORT).show();
                        Toast.makeText(Login.this, "Logged in successfully !", Toast.LENGTH_SHORT).show();
                        SharedPreferences sharedPreferences2 = Login.this.getSharedPreferences("MySharedPref2", MODE_PRIVATE);
                        final SharedPreferences.Editor myEdit = sharedPreferences2.edit();
                        myEdit.putString("login",httpResponseMsg);
                        myEdit.apply();

                        startActivity(new Intent(Login.this,CitySelectionActivity.class));


                    }else{
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(httpResponseMsg);
                            String messege = jsonObject.getString("msg");
                            Toast.makeText(Login.this, messege, Toast.LENGTH_SHORT).show();


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }






                }

                @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                @Override
                protected String doInBackground(String... params) {

//                hashMap.put("method",params[0]);
//                hashMap.put("customer_email",params[1]);
//                hashMap.put("customer_password",params[2]);

                    // Toast.makeText(Signup.this, hashMap.toString(), Toast.LENGTH_SHORT).show();
                    String jsonInputString="{\"method\":\"login\",\"customer_email\":\""+Email+"\",\"customer_password\":\""+Password+"\"}";

//                finalResult = jsonhttpParse.postRequest(method,Email,Password, HttpURL);
                    finalResult = jsonhttpParse.postRequest(jsonInputString, HttpURL);

                    return finalResult;
                }
            }

            UserLoginClass userLoginClass = new UserLoginClass();

            userLoginClass.execute(method,Email,Password);
        }

    }

