package co.edu.udea.rappigarage_android.Product.Detail;

import androidx.appcompat.app.AppCompatActivity;
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import android.os.Bundle;

import co.edu.udea.rappigarage_android.R;

public class ProductDetail extends AppCompatActivity {

    private ElegantNumberButton selectedQuantity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
    }
}
