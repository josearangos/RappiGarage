package co.edu.udea.rappigarage_android.Home;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;
import java.util.HashMap;

import androidx.fragment.app.Fragment;
import co.edu.udea.rappigarage_android.GlobalServices.Category.Category;
import co.edu.udea.rappigarage_android.Home.API.ProductSummary;
import co.edu.udea.rappigarage_android.Product.Publish.ProductFormActivity;
import co.edu.udea.rappigarage_android.R;

public class Home extends Fragment  implements  IHome.IView{

    private ProgressBar progressBar;
    HashMap<String,Integer> categoriesList ;
    private ChipGroup tagGroup_categories;

    //MVP
    private IHome.IPresenter presenter;

    public Home() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        categoriesList = new HashMap<String,Integer>();
        this.presenter = new HomePresenter(this);
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        initializeViews(view);
        this.presenter.getCategories();
        return view;
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
    public void initializeViews(View view ) {
        this.tagGroup_categories = view.findViewById(R.id.tagGroup_categories);
        progressBar = (ProgressBar)view.findViewById(R.id.progressBar);
    }

    @Override
    public void displayCategories(ArrayList<Category> categories) {
        //ShowCategories
        LayoutInflater  inflaterCategories = LayoutInflater.from(getActivity());
        for(Category category : categories){
            Chip chip = (Chip)inflaterCategories.inflate(R.layout.chip_item,null,false);
            chip.setText(category.getName());
            this.categoriesList.put(category.getName(),category.getId());
            this.tagGroup_categories.addView(chip);
        }
    }

    @Override
    public void displayProducts(ArrayList<ProductSummary> productSummaries) {

    }

    @Override
    public void displayError(String error) {

    }

    @Override
    public void displaySuccesFull(String ms) {

    }
}
