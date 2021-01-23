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
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class newpassword extends AppCompatActivity {
EditText newpassword,confirmpassword;
String strnewp,strcnfp,phone;
//    String HttpURL = "https://www.cakiweb.com/tooros/api/api.php";
String HttpURL = "https://tooros.in/api/api.php";
    String finalResult ;
    Boolean CheckEditText ;
    ProgressDialog progressDialog;
    int pay=0;
    JsonHttpParse jsonhttpParse = new JsonHttpParse();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newpassword);
        newpassword=findViewById(R.id.emailcontainer);
        confirmpassword=findViewById(R.id.passwordcontainer);
        strnewp=newpassword.getText().toString().trim();
        strcnfp=confirmpassword.getText().toString().trim();
        Intent intent=getIntent();
       phone= intent.getStringExtra("mobile");
        pay = intent.getIntExtra("val",0);
    }
    public boolean check(){
        if(strnewp.equals(strcnfp)){
            return true;
           // Toast.makeText(newpassword.this, "please enter correct password", Toast.LENGTH_SHORT).show();
        }
        return false;
    }
    public void forgetPasswordClick(View view) {

        strnewp=newpassword.getText().toString().trim();
        strcnfp=confirmpassword.getText().toString().trim();
        if(strnewp.length()!=0&&strcnfp.length()!=0){
            if(check()==true) {
                forgetPassword("resetPassword",phone,newpassword.getText().toString(), confirmpassword.getText().toString());
            }
        }else{
            Snackbar.make(view, " Please Enter password correctly ..", Snackbar.LENGTH_LONG)
                    .setActionTextColor(getResources().getColor(android.R.color.holo_green_dark))
                    .show();
        }




    }

    private void forgetPassword(String method,final String mobile, final String newpass,final String confirmpass) {

        class UserLoginClass extends AsyncTask<String,Void,String> {


            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                progressDialog = ProgressDialog.show(newpassword.this,"Loading...",null,true,true);
            }

            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            protected void onPostExecute(String httpResponseMsg) {

                super.onPostExecute(httpResponseMsg);
               // Toast.makeText(newpassword.this, ""+httpResponseMsg, Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();

                JSONObject jsonObject2 = null;
                try {
                    jsonObject2 = new JSONObject(httpResponseMsg);
                    String status = jsonObject2.getString("status");

                    if(status.equals("200")){

                        String msg=jsonObject2.getString("msg");

                        JSONArray result = jsonObject2.getJSONArray("result");
                        for (int i=0; i<result.length(); i++ ){
                            JSONObject ob=result.getJSONObject(i);

                            SharedPreferences sharedPreferences2 = newpassword.this.getSharedPreferences("MySharedPref2", MODE_PRIVATE);
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
                                startActivity(new Intent(newpassword.this, PaymentPage.class));
                            }else{
                                finish();
                                startActivity(new Intent(newpassword.this, CitySelectionActivity.class));
                            }




                        }

                        Toast.makeText(newpassword.this, ""+msg, Toast.LENGTH_LONG).show();

//                        Intent intent =new Intent(newpassword.this,Login.class);
//                        startActivity(intent);

                    }else{

                        String messege = jsonObject2.getString("msg");
                        Toast.makeText(newpassword.this, messege, Toast.LENGTH_SHORT).show();

                    }



                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            protected String doInBackground(String... params) {

                String jsonInputString="{\"method\":\"resetPassword\",\"mobile\":\""+mobile+"\",\"confirm_password\":\""+confirmpass+"\",\"new_password\":\""+newpass+"\"}";

                //  Toast.makeText(PaymentPage.this, ""+book_id, Toast.LENGTH_SHORT).show();
// finalResult = jsonhttpParse.postRequest(method,Email,Password, HttpURL);
                finalResult = jsonhttpParse.postRequest(jsonInputString, HttpURL);
                return finalResult;
            }
        }

        UserLoginClass userLoginClass = new UserLoginClass();

        userLoginClass.execute(method,mobile,newpass,confirmpass);


    }
}
