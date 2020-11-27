package my.awesome.tooros;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

public class Signup extends AppCompatActivity {
    EditText name , email , phone, password,confirmpassword,aadharcardno,dlno;
    TextView Dob;
    String strname, stremail, strphone, strpassword,strconfirmpassword,straadharcardno,strdlno,strdob;
    Button Register;
    String finalResult ;
    String HttpURL = "https://www.cakiweb.com/mechanic/request-api/api.php";
    Boolean CheckEditText ;
    ProgressDialog progressDialog;
    HashMap<String,String> hashMap = new HashMap<>();
    HttpParse httpParse = new HttpParse();
    Calendar myEndCalendar;
    DatePickerDialog.OnDateSetListener enddatelistener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        Register=findViewById(R.id.registerbtn);
        name=findViewById(R.id.namecontainer);
        email=findViewById(R.id.emailcontainer);
        phone=findViewById(R.id.mobilenocontainer);
        password=findViewById(R.id.passwordcontainer);
        confirmpassword=findViewById(R.id.repasswordcontainer);
        aadharcardno=findViewById(R.id.aadharcontainer);
        dlno=findViewById(R.id.dlnocontainer);
        Dob=findViewById(R.id.dobv);
        myEndCalendar = Calendar.getInstance();
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

                String dateSelected=sdf.format(myEndCalendar.getTime());

                Dob.setText(dateSelected);

                //Toast.makeText(CitySelectionActivity.this,sdf.format(myEndCalendar.getTime()) , Toast.LENGTH_SHORT).show();
            }

        };

    }



    public void onClickSubmit(View view) {
        //startActivity(new Intent(Custom19.this,MobileVerification.class));

        // Checking whether EditText is Empty or Not
        CheckEditTextIsEmptyOrNot();

        if(CheckEditText){

            SharedPreferences sharedPreferences1=Signup.this.getSharedPreferences("profie",MODE_PRIVATE);
            final SharedPreferences.Editor edit=sharedPreferences1.edit();
            edit.putString("name",strname);
            edit.putString("email",stremail);
            edit.putString("phone",strphone);
            edit.apply();
            // If EditText is not empty and CheckEditText = True then this block will execute.

            UserRegisterFunction("registerUser",strname,stremail, strphone, strpassword,strdob,straadharcardno,strdlno);

        }
        else {

            // If EditText is empty then this block will execute .
            Toast.makeText(Signup.this, "Please fill all form fields.", Toast.LENGTH_LONG).show();

        }

    }
    public void CheckEditTextIsEmptyOrNot(){

        strname = name.getText().toString();
        stremail = email.getText().toString();
        strphone = phone.getText().toString();
        strpassword = password.getText().toString();
        strconfirmpassword = confirmpassword.getText().toString();
        straadharcardno= aadharcardno.getText().toString();
        strdlno = dlno.getText().toString();
         strdob=Dob.getText().toString().trim();
        if(TextUtils.isEmpty(strname) || TextUtils.isEmpty(stremail) || TextUtils.isEmpty(strphone) || TextUtils.isEmpty(strpassword) || TextUtils.isEmpty(strconfirmpassword) ||  TextUtils.isEmpty(straadharcardno) ||  TextUtils.isEmpty(strdlno) )
        {

            CheckEditText = false;

        }
        else {

            CheckEditText = true ;
        }

    }

    public void UserRegisterFunction(final String method,final String name, final String Email, final String phone, final String password,final String dob, final String aadharno,final String dlno){

        class UserRegisterFunctionClass extends AsyncTask<String,Void,String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                progressDialog = ProgressDialog.show(Signup.this,"Loading Data",null,true,true);
            }

            @Override
            protected void onPostExecute(String httpResponseMsg) {

                super.onPostExecute(httpResponseMsg);

                progressDialog.dismiss();
                boolean msg=httpResponseMsg.contains("200");

                if(msg){

                    JSONObject jsonObject = null;
                    try {
                        jsonObject = new JSONObject(httpResponseMsg);
                      //  String messege= jsonObject.getString("otp");

                       Intent intent=new Intent(Signup.this,Login.class);
                      //  intent.putExtra("phone",strphone);
                      //  intent.putExtra("otp",messege);
                        startActivity(intent);

                        //Toast.makeText(Custom19.this, messege, Toast.LENGTH_SHORT).show();


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else {

                    JSONObject jsonObject = null;
                    try {
                        jsonObject = new JSONObject(httpResponseMsg);
                        String messege = jsonObject.getString("msg");
                        Toast.makeText(Signup.this, messege, Toast.LENGTH_SHORT).show();


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            protected String doInBackground(String... params) {

                hashMap.put("method",params[0]);

                hashMap.put("customer_name",params[1]);

                hashMap.put("customer_email",params[2]);

                hashMap.put("customer_mobile",params[3]);

                hashMap.put("customer_password",params[4]);
                hashMap.put("customer_dob",params[5]);
                hashMap.put("customer_aadharno",params[6]);
                hashMap.put("customer_dlno",params[7]);

                finalResult = httpParse.postRequest(hashMap, HttpURL);

                return finalResult;
            }
        }

        UserRegisterFunctionClass userRegisterFunctionClass = new UserRegisterFunctionClass();

        userRegisterFunctionClass.execute(method,name,Email,phone,password,dob,aadharno,dlno);
    }
    public void onClickEndDate(View view) {
        new DatePickerDialog(Signup.this, enddatelistener, myEndCalendar
                .get(Calendar.YEAR), myEndCalendar.get(Calendar.MONTH),
                myEndCalendar.get(Calendar.DAY_OF_MONTH)).show();

    }

    public void onClickAadharsnap(View view) {
    }

    public void onClickDlsnap(View view) {
    }
}
