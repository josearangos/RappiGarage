package co.edu.udea.rappigarage_android.Product.Detail.PayPal;

import android.animation.Animator;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;

import org.json.JSONException;
import org.json.JSONObject;

import androidx.appcompat.app.AppCompatActivity;
import co.edu.udea.rappigarage_android.MainActivity;
import co.edu.udea.rappigarage_android.R;
import co.edu.udea.rappigarage_android.SplashIntro;

public class PaymentDetails extends AppCompatActivity {

    private TextView id;
    private TextView state;
    private TextView amount;

    LottieAnimationView factura;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_details);
        this.amount = (TextView) findViewById(R.id.amount);
        this.state = (TextView) findViewById(R.id.state);
        this.id = (TextView) findViewById(R.id.id);

        factura = findViewById(R.id.factura);

        Intent intent = getIntent();

        try {
            JSONObject jsonObject = new JSONObject(intent.getStringExtra("paymentDetails"));
            showDetails(jsonObject.getJSONObject("reponse"), intent.getStringExtra("price"), intent.getStringExtra("title"));


        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    @Override
    protected void onStart() {
        super.onStart();
        factura.addAnimatorListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                    Intent myintent = new Intent(PaymentDetails.this, MainActivity.class);
                    startActivity(myintent);
                    finish();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {


            }
        });
    }

    private void showDetails(JSONObject reponse, String price, String title) {
        try {
            id.setText(reponse.getString("id"));
            state.setText(reponse.getString("state"));
            //amount.setText(reponse.getString(String.format("$%s",paymentAmount)));
        }catch (JSONException e){
            e.printStackTrace();
        }
    }
}
