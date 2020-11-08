package my.awesome.tooros;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

//import com.example.tooros.R;

public class SplashActivity extends AppCompatActivity {

    private static int splash_time=1500;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
                Intent intent=new Intent(SplashActivity.this,CitySelectionActivity.class);
                startActivity(intent);

            }
        },splash_time);
    }
}
