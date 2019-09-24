package co.edu.udea.rappigarage_android.Product.Detail.PayPal;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import androidx.appcompat.app.AppCompatActivity;
import co.edu.udea.rappigarage_android.R;

public class PaymentDetails extends AppCompatActivity {

    private TextView id;
    private TextView state;
    private TextView amount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_details);
        this.amount = (TextView) findViewById(R.id.amount);
        this.state = (TextView) findViewById(R.id.state);
        this.id = (TextView) findViewById(R.id.id);


        Intent intent = getIntent();

        try {
            JSONObject jsonObject = new JSONObject(intent.getStringExtra("paymentDetails"));
            showDetails(jsonObject.getJSONObject("reponse"), intent.getStringExtra("price"), intent.getStringExtra("title"));


        } catch (JSONException e) {
            e.printStackTrace();
        }


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
