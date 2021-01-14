package my.awesome.tooros;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.Locale;
import java.util.Map;

public class Profile extends AppCompatActivity {
    EditText name , password,confirmpassword,aadharcardno,dlno;
    TextView Dob , email , phone;String userid;
    Button Register;
    Uri imageuri;
    ImageView aadharpic,dlpic;
    TextView aadharSnap,dlSnap;
    Calendar myEndCalendar;
    DatePickerDialog.OnDateSetListener enddatelistener;

    String HttpURL = "https://www.cakiweb.com/tooros/api/api.php";

    Bitmap aadharfile,dlfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Register=findViewById(R.id.registerbtn);
        name=findViewById(R.id.namecontainer);
        email=findViewById(R.id.emailcontainer);
        phone=findViewById(R.id.mobilenocontainer);
        aadharcardno=findViewById(R.id.aadharcontainer);
        dlno=findViewById(R.id.dlnocontainer);
        Dob=findViewById(R.id.dobv);

        aadharSnap=findViewById(R.id.aadharsnapcontainer);
        dlSnap=findViewById(R.id.dlsnapcontainer);
        aadharpic=findViewById(R.id.aadharpic);
        dlpic=findViewById(R.id.dlpic);
        SharedPreferences sharedPreferences2 = Profile.this.getSharedPreferences("MySharedPref2", MODE_PRIVATE);
        if (sharedPreferences2 != null) {
            String name1 = sharedPreferences2.getString("Name", null);
            name.setText(name1);
          String  mobile = sharedPreferences2.getString("Mobile", null);
          phone.setText(mobile);
            String email1 = sharedPreferences2.getString("Email", null);
            email.setText(email1);
            String dob = sharedPreferences2.getString("Dob", null);
            Dob.setText(dob);
            String dlno1 = sharedPreferences2.getString("Dlno", null);
            dlno.setText(dlno1);
           String aadharno = sharedPreferences2.getString("Aadharno", null);
            aadharcardno.setText(aadharno);
            userid=sharedPreferences2.getString("userid",null);

        }
        myEndCalendar = Calendar.getInstance();
        enddatelistener = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myEndCalendar.set(Calendar.YEAR, year);
                myEndCalendar.set(Calendar.MONTH, monthOfYear);
                myEndCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                String myFormat = "dd-MM-yy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                String dateSelected=sdf.format(myEndCalendar.getTime());

                Dob.setText(dateSelected);

                //Toast.makeText(CitySelectionActivity.this,sdf.format(myEndCalendar.getTime()) , Toast.LENGTH_SHORT).show();
            }

        };

        aadharSnap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gallery=new Intent();
                gallery.setType("image/*");
                gallery.setAction(Intent.ACTION_GET_CONTENT);

                startActivityForResult(Intent.createChooser(gallery,"select picture"),0);
            }
        });
        dlSnap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gallery=new Intent();
                gallery.setType("image/*");
                gallery.setAction(Intent.ACTION_GET_CONTENT);

                startActivityForResult(Intent.createChooser(gallery,"select picture"),1);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==0 && resultCode==RESULT_OK){
            imageuri=data.getData();
            String path=imageuri.getPath();
            aadharSnap.setText(path);
            try{
                aadharfile= MediaStore.Images.Media.getBitmap(getContentResolver(),imageuri);
                //Toast.makeText(this, ""+BitMapToString(aadharfile), Toast.LENGTH_SHORT).show();
                aadharpic.setImageBitmap(aadharfile);
            }catch (IOException e){
                e.printStackTrace();
            }

        }
        if(requestCode==1 && resultCode==RESULT_OK){
            imageuri=data.getData();
            String path=imageuri.getPath();
            dlSnap.setText(path);

            try{
                 dlfile= MediaStore.Images.Media.getBitmap(getContentResolver(),imageuri);
                dlpic.setImageBitmap(dlfile);
            }catch (IOException e){
                e.printStackTrace();
            }
        }

    }
    public void onClickEndDate(View view) {
        new DatePickerDialog(Profile.this, enddatelistener, myEndCalendar
                .get(Calendar.YEAR), myEndCalendar.get(Calendar.MONTH),
                myEndCalendar.get(Calendar.DAY_OF_MONTH)).show();

    }


    public void onClickSubmit(View view) {

        final ProgressDialog loading = ProgressDialog.show(this,"Uploading...","Please wait...",false,false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, HttpURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        //Disimissing the progress dialog
                        loading.dismiss();
                        //Showing toast message of the response
                        Toast.makeText(Profile.this, s , Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        //Dismissing the progress dialog
                        loading.dismiss();

                        //Showing toast
                        Toast.makeText(Profile.this, volleyError.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //Converting Bitmap to String
                String aadharimage = BitMapToString(aadharfile);
                String dlimage = BitMapToString(dlfile);

                //Getting Image Name
                String username = name.getText().toString();
                String userphone = phone.getText().toString();
                String useremail = email.getText().toString();
                String useredob = Dob.getText().toString();
                String userdlno = dlno.getText().toString();
                String useraadharno = aadharcardno.getText().toString();

                //Creating parameters
                Map<String,String> params = new Hashtable<String, String>();

                //Adding parameters
                params.put("method", "updateProfile");
                params.put("userId", userid);
                params.put("name", username);
                params.put("dl_no", userdlno);
                params.put("aadhar_no", useraadharno);
                params.put("dob", useredob);
                params.put("dl_doc", dlimage);
                params.put("aadhar_doc", aadharimage);

               // name.setText(""+user);

                //returning parameters
                return params;
            }
        };

        //Creating a Request Queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //Adding request to the queue
        requestQueue.add(stringRequest);


        //do whatever you want from here
    }

    public String BitMapToString(Bitmap bitmap){
        ByteArrayOutputStream baos=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100, baos);
        byte [] b=baos.toByteArray();
        String temp= Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }

}

