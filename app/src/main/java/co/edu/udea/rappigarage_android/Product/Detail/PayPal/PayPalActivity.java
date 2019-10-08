package co.edu.udea.rappigarage_android.Product.Detail.PayPal;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import co.edu.udea.rappigarage_android.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;



import org.json.JSONException;

import java.math.BigDecimal;

public class PayPalActivity extends AppCompatActivity {

    String price = "";
    String title = "";

    public String getPrice() {
        return price;
    }

    public String getTitleP() {
        return title;
    }

    public void setPrice(String price) {
        this.price = price;
    }



    public void setTitle(String title) {
        this.title = title;
    }

    public static final int PAYPAL_REQUEST_CODE=7171;

    private static PayPalConfiguration config = new PayPalConfiguration()
            .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
            .clientId(Config.PAYPAL_CLIENT_ID);


    @Override
    protected void onDestroy() {
        stopService(new Intent(this,PayPalService.class));
        super.onDestroy();

    }

    Button buy ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_pal);
        Bundle extras =getIntent().getExtras();
        setPrice(extras.getString("price"));
        setTitle(extras.getString("title"));

        Intent intent = new Intent(this,PayPalService.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION,config);
        startService(intent);
        processPayment();


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

       if(requestCode == PAYPAL_REQUEST_CODE){
           if(resultCode == RESULT_OK){
               PaymentConfirmation confirmation = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
               if(confirmation !=null){
                   try {
                       String paymentDetails = confirmation.toJSONObject().toString(4);
                       startActivity(new Intent(this, PaymentDetails.class)
                               .putExtra("price",getPrice())
                               .putExtra("title",getTitleP())
                               .putExtra("paymentDetails",paymentDetails));
                       finish();
                   } catch (JSONException e) {
                       e.printStackTrace();
                   }
               }
           }else if(resultCode == Activity.RESULT_CANCELED){
               Toast.makeText(this, "Ops algo anda mal intenta mas tarde", Toast.LENGTH_SHORT).show();
           }

       }else if(resultCode == PaymentActivity.RESULT_EXTRAS_INVALID){

           Toast.makeText(this, "Invalid", Toast.LENGTH_SHORT).show();
       }


    }

    private void processPayment() {

        PayPalPayment payPalPayment = new PayPalPayment(new BigDecimal(getPrice()),"USD",getTitleP(),PayPalPayment.PAYMENT_INTENT_SALE);

        Intent intent = new Intent(this, PaymentActivity.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT,payPalPayment);
        startActivityForResult(intent,PAYPAL_REQUEST_CODE);
    }


}
