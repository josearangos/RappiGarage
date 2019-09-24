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
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.ui.PlacePicker;
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
import com.jackandphantom.circularimageview.RoundedImage;
import com.jaygoo.widget.RangeSeekBar;

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
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import co.edu.udea.rappigarage_android.GlobalServices.Category.Category;
import co.edu.udea.rappigarage_android.MainActivity;
import co.edu.udea.rappigarage_android.Product.Adapters.ImagesAdapter;
import co.edu.udea.rappigarage_android.Product.Publish.API.Location;
import co.edu.udea.rappigarage_android.Product.Publish.API.Measures;
import co.edu.udea.rappigarage_android.Product.Publish.API.PhotoSource;
import co.edu.udea.rappigarage_android.Product.Publish.API.Product;
import co.edu.udea.rappigarage_android.Product.Publish.API.ProductResponse;
import co.edu.udea.rappigarage_android.R;


public class ProductFormActivity extends AppCompatActivity implements IProductForm.IView  {

    HashMap<String,Integer> categoriesList ;
    private  String cityName = "";
    private String warrantyProduct  = "";
    Measures measures;
    //Images
    private RecyclerView listImages ;
    ArrayList<String> urisPhotos = new ArrayList<>(); // Uris images
    ImagesAdapter imagesAdapter ;
    List<Integer> categories = new ArrayList<>();
    String morcilla ="AIzaSyBS7E706CIUuXJmAefzTa7kXXEyPWzRJ6o";
    PlacesClient placesClient;
    Double latitude,longitude =0.0;
    final int  PLACE_PICKER_REQUEST = 1;
    //Views
    private Button sendPhotos;
    private RoundedImage loadingImage ;
    private  Button location;
    private ChipGroup tagGroup_categories,warranty;
    private Toolbar toolbar;
    private TextInputEditText price;
    private TextInputEditText name;
    private TextInputEditText description;
    private ElegantNumberButton availableQuantity;
    private Switch condition;
    private RangeSeekBar pickerWidth;
    private RangeSeekBar pickerHeigth;
    private RangeSeekBar pickerLarge;
    private RangeSeekBar pickerWeigth;
    private ProgressBar progressBar;
    private MaterialButton publishBtn;


    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    //MVP
    private IProductForm.IPresenter presenter;

    public void addImagesSlider(){
        // add a divider after each item for more clarity
        listImages.addItemDecoration(new DividerItemDecoration(ProductFormActivity.this, LinearLayoutManager.HORIZONTAL));
        imagesAdapter = new ImagesAdapter(getUrisPhotos(), getApplicationContext());
        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(ProductFormActivity.this, LinearLayoutManager.HORIZONTAL, false);
        listImages.setLayoutManager(horizontalLayoutManager);
        listImages.setAdapter(imagesAdapter);
        imagesAdapter.notifyDataSetChanged();
    }

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


        //List images
        this.listImages = (RecyclerView)findViewById(R.id.imgList);


        //Initialice views
        this.loadingImage= (RoundedImage)findViewById(R.id.loadingImage);
        this.progressBar = (ProgressBar) findViewById(R.id.progressBar);
        this.pickerWidth = (RangeSeekBar) findViewById(R.id.ancho);
        this.pickerHeigth = (RangeSeekBar) findViewById(R.id.alto);
        this.pickerLarge = (RangeSeekBar) findViewById(R.id.largo);
        this.pickerWeigth = (RangeSeekBar) findViewById(R.id.peso);
        pickerWidth.setIndicatorTextDecimalFormat("0");
        pickerHeigth.setIndicatorTextDecimalFormat("0");
        pickerLarge.setIndicatorTextDecimalFormat("0");
        pickerWeigth.setIndicatorTextDecimalFormat("0");

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

        //if(validationForm() != false) {

            Location location = new Location(getLatitude(), getLongitude());
            measures = new Measures(
                    Integer.valueOf((int) this.pickerWidth.getLeftSeekBar().getProgress()),
                    Integer.valueOf((int) this.pickerHeigth.getLeftSeekBar().getProgress()),
                    Integer.valueOf((int) this.pickerWeigth.getLeftSeekBar().getProgress()),
                    Integer.valueOf((int) this.pickerLarge.getLeftSeekBar().getProgress()));

            Product product = new Product(
                    Double.valueOf(this.price.getText().toString()),
                    this.name.getText().toString(),
                    this.description.getText().toString(),
                    Integer.valueOf(this.availableQuantity.getNumber()),
                    getTextWarrantyProduct(this.warranty),
                    getCurrentUTC(),
                    true,
                    measures,
                    0,
                    1,
                    condition(),
                    getCityName(),
                    location,
                    null);

            this.presenter.publishProduct(product,getUrisPhotos(),this.categories);
        //}else {
          //  Toast.makeText(getApplicationContext(), "complete el formulario", Toast.LENGTH_LONG).show();

           // }
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
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


    public boolean validationForm(){
        Boolean price, name,description,location,category,Measures =false, photos = false;
        price = this.price.getText().toString().isEmpty();
        name = this.name.getText().toString().isEmpty();
        description = this.description.getText().toString().isEmpty();
        location = this.getCityName().isEmpty();
        photos = getUrisPhotos().isEmpty();


        category = this.categoriesList.isEmpty();
        if(measures!=null){
            if(measures.getHeigth() == 0 || measures.getLarge() == 0
            || measures.getWeigth() == 0 || measures.getWidth() == 0 ){
                Measures = true;
            }

        }
        if (price || name || description || location || category || Measures || photos ){
            return false;
        }
        return true;
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
                setCityName("");
                try {
                    setLatitude(place.getLatLng().latitude);
                    setLongitude(place.getLatLng().longitude);
                    setCityName(getCityNameByCoordinates(place.getLatLng().latitude,place.getLatLng().longitude));
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

        requestPermission();


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
                    addImagesSlider();
                }
            }
            break;
            case (PLACE_PICKER_REQUEST):{
                if(resultCode == RESULT_OK){


                }
            }


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
                .setPermissions(Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_NETWORK_STATE,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
                .check();
    }


    public void publishProductClick(View view) {

        //tagsCheckedId contiene los id de las etiquetas que selecciono
        ArrayList<Integer> tagsCheckedId =  new ArrayList<>();
        for (int i=0; i<tagGroup_categories.getChildCount();i++){
            Chip chip =(Chip)tagGroup_categories.getChildAt(i);
            if(chip.isChecked()){
                tagsCheckedId.add(getIdCategory(String.valueOf(chip.getText())));
                categories.add(getIdCategory(String.valueOf(chip.getText())));
            }

        }
        //imprimo los ids de las categorias seleccionadas
        publishProduct();
    }
}
