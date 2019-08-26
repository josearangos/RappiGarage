package co.edu.udea.rappigarage_android.Product.Publish;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.fxn.pix.Options;
import com.fxn.pix.Pix;
import com.fxn.utility.ImageQuality;
import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.textfield.TextInputEditText;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.jesusm.holocircleseekbar.lib.HoloCircleSeekBar;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import co.edu.udea.rappigarage_android.GlobalServices.Category.Category;
import co.edu.udea.rappigarage_android.Product.Publish.API.Location;
import co.edu.udea.rappigarage_android.Product.Publish.API.Measures;
import co.edu.udea.rappigarage_android.Product.Publish.API.Photo;
import co.edu.udea.rappigarage_android.Product.Publish.API.Product;
import co.edu.udea.rappigarage_android.Product.Publish.API.ProductResponse;
import co.edu.udea.rappigarage_android.R;


public class ProductFormActivity extends AppCompatActivity implements IProductForm.IView  {

    HashMap<String,Integer> categoriesList ;
    ArrayList<String> urisPhotos = new ArrayList<>(); // Uris images
    String morcilla = "AIzaSyBS7E706CIUuXJmAefzTa7kXXEyPWzRJ6o";
    PlacesClient placesClient;
    Double latitude,longitude =0.0;

    public ArrayList<String> getUrisPhotos() {
        return urisPhotos;
    }

    public void setUrisPhotos(ArrayList<String> urisPhotos) {
        this.urisPhotos = urisPhotos;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    //Views
    private Button sendPhotos;
    private ImageView loadingImage ;
    private Button location;
    private ChipGroup tagGroup_categories,warranty;
    private Toolbar toolbar;
    private TextInputEditText price;
    private TextInputEditText name;
    private TextInputEditText description;
    private ElegantNumberButton availableQuantity;
    private Switch condition;
    private HoloCircleSeekBar pickerWidth;
    private HoloCircleSeekBar pickerHeigth;
    private HoloCircleSeekBar pickerLarge;
    private HoloCircleSeekBar pickerWeigth;
    private ProgressBar progressBar;
    private MaterialButton publishBtn;

    //MVP
    private IProductForm.IPresenter presenter;

    public ProductFormActivity() {
        this.presenter = new ProductFormPresenter(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_form);
        categoriesList = new HashMap<String,Integer>();
        initializeViews();
        this.presenter = new ProductFormPresenter(this);
        this.presenter.getCategories();
        settingGooglePlaces();
        requestPermission();


    }


    @Override
    public void displayLoader(boolean loader) {
        if(loader){
            progressBar.setVisibility(View.VISIBLE);
        }else{
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void initializeViews() {

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(R.string.productForm);
        actionBar.setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });



        //Temporal photos Button
        this.sendPhotos = (Button)findViewById(R.id.sendPhotos);
        //Initialice views
        this.loadingImage= (ImageView)findViewById(R.id.loadingImage);
        this.progressBar = (ProgressBar) findViewById(R.id.progressBar);
        this.pickerWidth = (HoloCircleSeekBar) findViewById(R.id.pickerWidth);
        this.pickerHeigth = (HoloCircleSeekBar) findViewById(R.id.pickerHeigth);
        this.pickerLarge = (HoloCircleSeekBar) findViewById(R.id.pickerLarge);
        this.pickerWeigth = (HoloCircleSeekBar) findViewById(R.id.pickerWeigth);
        this.condition = (Switch) findViewById(R.id.condition);
        this.availableQuantity = (ElegantNumberButton) findViewById(R.id.availableQuantity);
        this.description = (TextInputEditText) findViewById(R.id.description);
        this.name = (TextInputEditText) findViewById(R.id.name);
        this.price = (TextInputEditText) findViewById(R.id.price);
        this.toolbar = findViewById(R.id.toolbar);

        //warranty
        this.warranty = findViewById(R.id.warranty);


        //Categories group
        this.tagGroup_categories = findViewById(R.id.tagGroup_categories);
        this.publishBtn = findViewById(R.id.publishBtn);
        this.publishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //tagsCheckedId contiene los id de las etiquetas que selecciono
                ArrayList<Integer> tagsCheckedId =  new ArrayList<>();
                for (int i=0; i<tagGroup_categories.getChildCount();i++){
                    Chip chip =(Chip)tagGroup_categories.getChildAt(i);
                    if(chip.isChecked()){
                        tagsCheckedId.add(getIdCategory(String.valueOf(chip.getText())));
                    }

                }
                //imprimo los ids de las categorias seleccionadas
                Toast.makeText(getApplicationContext(),String.valueOf(tagsCheckedId.toString()),Toast.LENGTH_SHORT).show();
                //PUBLICAR PRODUCTO SIN IMAGEN
              //  publishPhotos(getUrisPhotos());
                publishProduct();
            }
        });
        this.sendPhotos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                publishPhotos(getUrisPhotos());
            }
        });

    }




    @Override
    public void getCategories(ArrayList<Category> categories) {

        //ShowCategories
        LayoutInflater  inflaterCategories = LayoutInflater.from(ProductFormActivity.this);
        for(Category category : categories){
            Chip chip = (Chip)inflaterCategories.inflate(R.layout.chip_item,null,false);
            chip.setText(category.getName());
            this.categoriesList.put(category.getName(),category.getId());
            this.tagGroup_categories.addView(chip);

        }


    }

    @Override
    public void displayError(String error) {
        Toast.makeText(getApplicationContext(),error,Toast.LENGTH_SHORT).show();

    }


    public String getTextWarrantyProduct(ChipGroup chipGroup){
        String warrantyProduct = "";

        for (int i=0; i<chipGroup.getChildCount();i++){
            Chip chip =(Chip)chipGroup.getChildAt(i);
            if(chip.isChecked()){
                warrantyProduct = String.valueOf(chip.getText());
            }

        }
        return warrantyProduct;
    }


    @Override
    public void publishProduct() {
        List< Photo> photos = new ArrayList<Photo>();
        Location location = new Location(getLatitude(),getLongitude());
        Measures measures =new Measures(this.pickerWidth.getValue(),this.pickerHeigth.getValue(),this.pickerWeigth.getValue(),this.pickerLarge.getValue());
        photos.add(new Photo("https://avatars0.githubusercontent.com/u/13352055?s=460&v=4"));


        Product product = new Product(
                Double.valueOf(this.price.getText().toString()),
                this.name.getText().toString(),
                this.description.getText().toString(),
                Integer.valueOf(this.availableQuantity.getNumber()),
                getTextWarrantyProduct(this.warranty),
                getCurrentUTC(),
                true,
                measures,
                3,
                1,
                condition(),
                location,
                photos);

        this.presenter.publishProduct(product);
    }

    public String getCurrentUTC(){

        TimeZone tz = TimeZone.getTimeZone("UTC");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'"); // Quoted "Z" to indicate UTC, no timezone offset
        df.setTimeZone(tz);
        String nowAsISO = df.format(new Date());
        return nowAsISO;
    }

    @Override
    public void publishProductResponse(ProductResponse productResponse) {
        Toast.makeText(getApplicationContext(),String.valueOf(productResponse.getId()),Toast.LENGTH_LONG).show();
    }

    @Override
    public void publishPhotos(ArrayList<String> urisPhotos) {
         this.presenter.publishPhotos(urisPhotos);
    }

    @Override
    public void displaySuccesFull(String ms) {
        Toast.makeText(getApplicationContext(),ms,Toast.LENGTH_LONG).show();
    }

    public Integer getIdCategory(String name){
        return this.categoriesList.get(name);
    }

    public void settingGooglePlaces(){
        if(!Places.isInitialized()){
            Places.initialize(getApplicationContext(),morcilla);
        }
        placesClient = Places.createClient(this);
        final AutocompleteSupportFragment autocompleteSupportFragment =
                (AutocompleteSupportFragment)getSupportFragmentManager().findFragmentById(R.id.autoCompleteFragmentLocation);
        autocompleteSupportFragment.setPlaceFields(Arrays.asList(Place.Field.NAME,Place.Field.LAT_LNG));
        autocompleteSupportFragment.setHint("Buscar lugar");
        autocompleteSupportFragment.setCountry("CO");

        autocompleteSupportFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull Place place) {

                try {
                    setLatitude(place.getLatLng().latitude);
                    setLongitude(place.getLatLng().longitude);
                    String city = getCityNameByCoordinates(place.getLatLng().latitude,place.getLatLng().longitude);
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onError(@NonNull Status status) {

            }
        });

    }



    public String getCityNameByCoordinates(double lat, double lon) throws IOException {
        Geocoder mGeocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
        List<Address> addresses = mGeocoder.getFromLocation(lat, lon, 1);
        if (addresses != null && addresses.size() > 0) {
            return addresses.get(0).getLocality();
        }
        return null;
    }

    public String condition(){
        if(this.condition.isChecked()){
            return "Used";
        }else{
            return "New";
        }
    }


    public void loadingImage(View view) {

        Options options = Options.init()
                .setRequestCode(100)
                .setCount(10)
                .setFrontfacing(false)
                .setImageQuality(ImageQuality.LOW)
                .setPreSelectedUrls(urisPhotos)
                .setScreenOrientation(Options.SCREEN_ORIENTATION_PORTRAIT)
                .setPath("/rappiGarage/images");
        options.setPreSelectedUrls(urisPhotos);
        Pix.start(ProductFormActivity.this, options);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case (100): {
                if (resultCode == RESULT_OK) {
                    setUrisPhotos(data.getStringArrayListExtra(Pix.IMAGE_RESULTS));
                    loadingImage.setImageURI(Uri.parse(urisPhotos.get(0)));
                }
            }
            break;
        }
    }


    public void requestPermission(){
        PermissionListener permissionlistener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                Toast.makeText(ProductFormActivity.this, "Permission Granted", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPermissionDenied(List<String> deniedPermissions) {
                Toast.makeText(ProductFormActivity.this, "Permission Denied\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
            }


        };

        TedPermission.with(this)
                .setPermissionListener(permissionlistener)
                .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                .setPermissions(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
                .check();
    }



}
