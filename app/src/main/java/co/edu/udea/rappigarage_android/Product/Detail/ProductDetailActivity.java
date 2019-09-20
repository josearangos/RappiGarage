package co.edu.udea.rappigarage_android.Product.Detail;

import androidx.appcompat.app.AppCompatActivity;
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.jackandphantom.circularimageview.RoundedImage;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import co.edu.udea.rappigarage_android.GlobalServices.Category.Category;
import co.edu.udea.rappigarage_android.Product.Detail.API.IProductDetailService;
import co.edu.udea.rappigarage_android.Product.Detail.API.IProductSellerService;
import co.edu.udea.rappigarage_android.Product.Publish.API.PhotoSource;
import co.edu.udea.rappigarage_android.Product.Publish.API.Product;
import co.edu.udea.rappigarage_android.Product.Publish.IProductForm;
import co.edu.udea.rappigarage_android.R;
import co.edu.udea.rappigarage_android.User.RappiUser;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProductDetailActivity extends AppCompatActivity implements IProductDetail.IView{

    //Views for product
    private SliderLayout imageProduct;
    private ElegantNumberButton selectedQuantity;
    private ProgressBar progressDetail;
    private TextView productName;
    private TextView price;
    private TextView quantity;
    private TextView productDescription;
    private TextView productLocation;
    private TextView publishedAt;
    private TextView warranty;
    private ChipGroup group_categories;

    //User
    private TextView nameUser;
    private SimpleDraweeView sellerPhoto;
    private RoundedImage seller;

    //Arrays
    private List<PhotoSource> photoSource;
    private HashMap<String,Integer> categorieList;
    private ArrayList<Category> categories;

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://apideveloprappigarage.herokuapp.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        this.categorieList = new HashMap<String,Integer>();
        this.initializeViews();
        //
    }

    public void  getProductDetail(String id){
        progressDetail.setVisibility(View.VISIBLE);
        IProductDetailService iProductDetailService = retrofit.create(IProductDetailService.class);
        Call<Product> call = iProductDetailService.getProductById("Products/"+id);
        call.enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                if(response.isSuccessful()){
                    progressDetail.setVisibility(View.GONE);
                    Product product = response.body();
                    showProduct(product);

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

        this.photoSource = product.getPhotos();

        this.price.setText(Double.toString(product.getPrice()));
        this.quantity.setText(product.getAvailableQuantity().toString());
        this.productDescription.setText(product.getDescription());
        this.productLocation.setText(product.getCityName());
        this.publishedAt.setText(product.getPublishDate());
        this.warranty.setText(product.getWarranty());

        //Categorías dumis mientras albert me las trae
        this.categories = new ArrayList<>();
        Category catDum = new Category();
        catDum.setId(1);
        catDum.setName("Categoria1");
        this.categories.add(catDum);
        getUserInfo(product.getUserId());
        setImagestoSlidwer();
        getCategories();
    }

    private void setImagestoSlidwer() {
        TextSliderView textSliderView;
        for( PhotoSource pt : this.photoSource){
            textSliderView = new TextSliderView(this);
            textSliderView.image(pt.getSource());
            this.imageProduct.addSlider(textSliderView);
        }
    }

    private void getUserInfo(Integer userId) {
        IProductSellerService seller = retrofit.create(IProductSellerService.class);
            Call<RappiUser> call = seller.getRappiUserInfo("RappiUsers/"+ userId + "?filter[fields][name]=true&filter[fields][photoUrl]=true");
        call.enqueue(new Callback<RappiUser>() {
            @Override
            public void onResponse(Call<RappiUser> call, Response<RappiUser> response) {
                if(response.isSuccessful()){
                    progressDetail.setVisibility(View.GONE);
                    RappiUser userInfo = response.body();
                    showUser(userInfo);
                }
            }

            @Override
            public void onFailure(Call<RappiUser> call, Throwable t) {
                progressDetail.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }
    public void showUser(RappiUser usr){
        this.nameUser.setText(usr.getName());
        this.sellerPhoto.setImageURI(usr.getPhotoUrl());
    }

    @Override
    protected void onStop() {
        this.imageProduct.stopAutoCycle();
        super.onStop();
    }

    @Override
    public void displayLoader(boolean loader) {
        if(loader){
            this.progressDetail.setVisibility(View.VISIBLE);
        }else{
            progressDetail.setVisibility(View.GONE);
        }
    }
    @Override
    public void initializeViews() {
        this.progressDetail = findViewById(R.id.progressDetail);
        this.productName = findViewById(R.id.productName);
        this.imageProduct = (SliderLayout) findViewById(R.id.imageProduct);
        this.price = findViewById(R.id.price);
        this.quantity = findViewById(R.id.quantity);
        this.productDescription = findViewById(R.id.productDescription);
        this.productLocation = findViewById(R.id.productLocation);
        this.publishedAt = findViewById(R.id.publishedAt);
        this.warranty = findViewById(R.id.warranty);
        this.nameUser = findViewById(R.id.nameUser);
        this.sellerPhoto = findViewById(R.id.userImage);
        this.group_categories = findViewById(R.id.group_categories);

        getProductDetail("65");// Here recieves the parameter from the main view

        //Agregar evento onClick para compra del producto.
    }

    @Override
    public void getCategories() {

        //ShowCategories
        LayoutInflater inflaterCategories = LayoutInflater.from(ProductDetailActivity.this);
        for(Category category : this.categories){
            Chip chip = (Chip)inflaterCategories.inflate(R.layout.chip_item,null,false);
            chip.setText(category.getName());
            this.categorieList.put(category.getName(),category.getId());

            this.group_categories.addView(chip);

        }
    }
    @Override
    public void displayError(String error) {
        Toast.makeText(getApplicationContext(),error,Toast.LENGTH_SHORT).show();

    }

    @Override
    public void displaySuccesFull(String ms) {
        Toast.makeText(getApplicationContext(),ms,Toast.LENGTH_LONG).show();
    }

}
