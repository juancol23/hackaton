package weekendfever.riva.com;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import weekendfever.riva.com.view.menu.Dashboard;

public class SplashActivity extends AppCompatActivity {

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        if(user != null) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (user != null) {
                        startActivity(new Intent(SplashActivity.this, Dashboard.class));
                        finish();

                    }
                }
            }, 100);
        }else{
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                        startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                        finish();
                }
            }, 100);
        }





    }
}
