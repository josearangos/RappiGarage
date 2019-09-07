package co.edu.udea.rappigarage_android.Product.Detail;

import androidx.appcompat.app.AppCompatActivity;
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.google.android.material.textfield.TextInputEditText;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import co.edu.udea.rappigarage_android.Product.Publish.API.Product;
import co.edu.udea.rappigarage_android.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProductDetail extends AppCompatActivity {

    //Views for product
    private ElegantNumberButton selectedQuantity;
    private ProgressBar progressDetail;
    private TextView productName;
    private TextView price;
    private TextView quantity;
    private TextView productDescription;
    private TextView productLocation;
    private TextView publishedAt;
    private TextView warranty;
    //User
    private TextView nameUser;
    private TextView userLocation;


    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://apideveloprappigarage.herokuapp.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        this.progressDetail = findViewById(R.id.progressDetail);
        this.productName = findViewById(R.id.productName);
        this.price =  findViewById(R.id.price);
        this.quantity =  findViewById(R.id.quantity);
        this.productDescription =  findViewById(R.id.productDescription);
        this.productLocation =  findViewById(R.id.productLocation);
        this.publishedAt =  findViewById(R.id.publishedAt);
        this.warranty =  findViewById(R.id.warranty);
        this.nameUser =  findViewById(R.id.nameUser);
        this.userLocation =  findViewById(R.id.userLocation);

        getProductDetail("15");
    }

    public void  getProductDetail(String id){
        progressDetail.setVisibility(View.VISIBLE);
        IProductDetail iProductDetail = retrofit.create(IProductDetail.class);
        Call<Product> call = iProductDetail.getProductById("Products/"+id);
        call.enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                if(response.isSuccessful()){
                    progressDetail.setVisibility(View.GONE);
                    Product product = response.body();
                    showProduct(product);
                    System.out.println("NOMBREEEE"+product.getName());
                }
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {
                progressDetail.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });

    }

    public  void showProduct(Product product){
        this.productName.setText(product.getName());
        this.price.setText(Double.toString(product.getPrice()));
        this.quantity.setText(product.getAvailableQuantity().toString());
        this.productDescription.setText(product.getDescription());
        this.productLocation.setText(product.getCityName());
        this.publishedAt.setText(product.getPublishDate());
        this.warranty.setText(product.getWarranty());
        this.nameUser.setText("");// Se debe hacer petición del usuario
        this.userLocation.setText("");
    }
}
