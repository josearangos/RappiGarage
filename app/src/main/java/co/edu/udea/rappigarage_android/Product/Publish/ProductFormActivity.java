package co.edu.udea.rappigarage_android.Product.Publish;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.button.MaterialButton;
import android.support.design.chip.Chip;
import android.support.design.chip.ChipGroup;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.internal.ei;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.jesusm.holocircleseekbar.lib.HoloCircleSeekBar;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import co.edu.udea.rappigarage_android.GlobalServices.Category.Category;
import co.edu.udea.rappigarage_android.R;

public class ProductFormActivity extends AppCompatActivity implements IProductForm.IView   {

    HashMap<String,Integer> categoriesList;

    String morcilla ="";
    PlacesClient placesClient;
    //Views
    private ChipGroup tagGroup_categories;
    private Toolbar toolbar;
    private TextInputEditText price;
    private TextInputEditText name;
    private TextInputEditText description;
    private ElegantNumberButton availableQuantity;
    private Switch condition;
    private HoloCircleSeekBar picker;
    private HoloCircleSeekBar picker2;
    private HoloCircleSeekBar picker3;
    private HoloCircleSeekBar picker4;
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

        //Initialice views
        this.progressBar = (ProgressBar) findViewById(R.id.progressBar);
        this.picker4 = (HoloCircleSeekBar) findViewById(R.id.picker4);
        this.picker3 = (HoloCircleSeekBar) findViewById(R.id.picker3);
        this.picker2 = (HoloCircleSeekBar) findViewById(R.id.picker2);
        this.picker = (HoloCircleSeekBar) findViewById(R.id.picker);
        this.condition = (Switch) findViewById(R.id.condition);
        this.availableQuantity = (ElegantNumberButton) findViewById(R.id.availableQuantity);
        this.description = (TextInputEditText) findViewById(R.id.description);
        this.name = (TextInputEditText) findViewById(R.id.name);
        this.price = (TextInputEditText) findViewById(R.id.price);
        this.toolbar = findViewById(R.id.toolbar);

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
                //Toast.makeText(getApplicationContext(),String.valueOf(tagsCheckedId.toString()),Toast.LENGTH_SHORT).show();

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
        autocompleteSupportFragment.setPlaceFields(Arrays.asList(Place.Field.NAME,Place.Field.LAT_LNG,Place.Field.NAME));
        autocompleteSupportFragment.setHint("Buscar lugar");
        autocompleteSupportFragment.setText("Asociacion Universitaria de Antioquia AUDEA");
        autocompleteSupportFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull Place place) {

            }

            @Override
            public void onError(@NonNull Status status) {

            }
        });

    }




}
