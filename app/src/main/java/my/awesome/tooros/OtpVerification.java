package my.awesome.tooros;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class OtpVerification extends AppCompatActivity {
TextView mobileno;
EditText otp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_verification);
        mobileno=findViewById(R.id.phone);
        otp=findViewById(R.id.otp);
        SharedPreferences sharedPreferences = OtpVerification.this.getSharedPreferences("profile", MODE_PRIVATE);
        String phone1= sharedPreferences.getString("phone",null);
        if(phone1!=null){
            mobileno.setText(phone1);
        }else{
            Toast.makeText(OtpVerification.this, "Enter your phone no in signup page!!!", Toast.LENGTH_SHORT).show();
        }
    }


    public void onClickverify(View view) {
    }
}
