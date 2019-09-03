package co.edu.udea.rappigarage_android.Home;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import co.edu.udea.rappigarage_android.GlobalServices.Category.Category;
import co.edu.udea.rappigarage_android.Home.API.ProductSummary;
import co.edu.udea.rappigarage_android.Home.API.Search;
import co.edu.udea.rappigarage_android.Home.Adapters.ProductSummaryAdapter;
import co.edu.udea.rappigarage_android.Product.Publish.ProductFormActivity;
import co.edu.udea.rappigarage_android.R;

public class Home extends Fragment  implements  IHome.IView, SearchView.OnQueryTextListener, MenuItem.OnActionExpandListener {

    private ProgressBar progressBar;
    HashMap<String,Integer> categoriesList ;
    private ChipGroup tagGroup_categories;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<ProductSummary> productSummaries;
    private ProductSummaryAdapter adapter;


    //MVP
    private IHome.IPresenter presenter;

    public Home() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        categoriesList = new HashMap<String,Integer>();
        this.presenter = new HomePresenter(this);
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        initializeViews(view);
        this.presenter.getCategories();
        this.presenter.getProducts("camisa",0,10);

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
        recyclerView =view.findViewById(R.id.productList);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
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
    public void displayProducts(List<Search> productSummaries) {
        adapter = new ProductSummaryAdapter(productSummaries,getContext());
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void displayError(String error) {
        System.out.println("ERROR"+error);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.searchmenu, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(this);
        searchView.setQueryHint("Search");

        super.onCreateOptionsMenu(menu, inflater);

    }

    @Override
    public void displaySuccesFull(String ms) {

    }

    @Override
    public boolean onMenuItemActionExpand(MenuItem item) {
        return true;
    }

    @Override
    public boolean onMenuItemActionCollapse(MenuItem item) {
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        this.presenter.getProducts(query,0,10);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        this.presenter.getProducts(newText,0,10);
        return true;
    }
}
