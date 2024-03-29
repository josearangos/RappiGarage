package co.edu.udea.rappigarage_android;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;
import co.edu.udea.rappigarage_android.User.Login;
import com.airbnb.lottie.LottieAnimationView;

public class SplashIntro extends AppCompatActivity {

    LottieAnimationView introrappi;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_intro);
        introrappi = findViewById(R.id.introrappi);
        intent = new Intent(this,Login.class);
    }

    @Override
    protected void onStart() {
        super.onStart();

        introrappi.addAnimatorListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {

                final SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.namePreference), Context.MODE_PRIVATE);

                if(sharedPreferences.getString("token",null) != null){
                    Intent myintent = new Intent(SplashIntro.this,MainActivity.class);
                    startActivity(myintent);
                    finish();
                }else {
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {


            }
        });
    }
}
