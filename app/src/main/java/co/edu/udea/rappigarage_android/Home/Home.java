package co.edu.udea.rappigarage_android.Home;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import co.edu.udea.rappigarage_android.GlobalServices.APIClient;
import co.edu.udea.rappigarage_android.GlobalServices.Category.Category;
import co.edu.udea.rappigarage_android.Home.API.IProduct;
import co.edu.udea.rappigarage_android.Home.API.ProductSummary;
import co.edu.udea.rappigarage_android.Home.API.Search;
import co.edu.udea.rappigarage_android.Home.Adapters.ProductSummaryAdapter;
import co.edu.udea.rappigarage_android.Home.Adapters.ProductSummaryAdapterPro;
import co.edu.udea.rappigarage_android.Home.Filters.FiltersActivity;
import co.edu.udea.rappigarage_android.Home.Utils.PaginationAdapter;
import co.edu.udea.rappigarage_android.Home.Utils.PaginationScrollListener;
import co.edu.udea.rappigarage_android.Product.Publish.ProductFormActivity;
import co.edu.udea.rappigarage_android.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Home extends Fragment  implements  IHome.IView, SearchView.OnQueryTextListener, MenuItem.OnActionExpandListener {


    //List products
    PaginationAdapter adapter;
    GridLayoutManager layoutManager;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;

    //API
    private IProduct apiInterface;

    //
    private static final int PAGE_START = 0;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    // limiting to 5 for this tutorial, since total pages in actual API is very large. Feel free to modify.
    private int TOTAL_PAGES = 2000;
    private int currentPage = PAGE_START;
    private int LastPosition = 0;


    Intent intent;
    HashMap<String,Integer> categoriesList ;
    private ChipGroup tagGroup_categories;

    //private ProductSummaryAdapter adapter;
    private String query = "";

    private  List<Search> items = new ArrayList<>();


        public String getQuery() {
            return query;
        }

        public void setQuery(String query) {
            this.query = query;
        }

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
        LastPosition = 0;

        this.tagGroup_categories = view.findViewById(R.id.tagGroup_categories);
        progressBar = (ProgressBar)view.findViewById(R.id.progressBar);
        recyclerView =view.findViewById(R.id.productList);

        //API
        apiInterface = APIClient.getApiClient().create(IProduct.class);

        adapter = new PaginationAdapter(getContext());
        layoutManager = new GridLayoutManager(getContext(),2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        recyclerView.addOnScrollListener(new PaginationScrollListener(layoutManager) {
            @Override
            protected void loadMoreItems() {
                isLoading = true;
                currentPage += 1;

                // mocking network delay for API call
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        loadNextPage();


                    }
                }, 1000);
            }

            @Override
            public int getTotalPageCount() {
                return TOTAL_PAGES;
            }

            @Override
            public boolean isLastPage() {
                return isLastPage;
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }
        });
        loadFirstPage();

       }

    private void loadFirstPage() {
        items.clear();
           LastPosition = LastPosition + 5 ;
            Call<ProductSummary> call = apiInterface.getProductsforQuery(getQuery(),0,5);
            call.enqueue(new Callback<ProductSummary>() {
                @Override
                public void onResponse(Call<ProductSummary> call, Response<ProductSummary> response) {

                    if(response.isSuccessful()){
                        items = response.body().getSearch();
                        progressBar.setVisibility(View.GONE);
                        adapter.clear();
                        adapter.addAll(items);
                        if(items.size()<5){
                            TOTAL_PAGES =currentPage;
                        }
                        if (currentPage != TOTAL_PAGES) adapter.addLoadingFooter();
                        else isLastPage = true;
                    }
                }

                @Override
                public void onFailure(Call<ProductSummary> call, Throwable t) {
                    t.printStackTrace();
                    progressBar.setVisibility(View.GONE);
                }
            });
        }

    private void loadNextPage() {

        Call<ProductSummary> call = apiInterface.getProductsforQuery(getQuery(),LastPosition,5);
        call.enqueue(new Callback<ProductSummary>() {
            @Override
            public void onResponse(Call<ProductSummary> call, Response<ProductSummary> response) {

                adapter.removeLoadingFooter();
                isLoading = false;

                if(response.isSuccessful()){
                    items = response.body().getSearch();
                    adapter.addAll(items);
                    LastPosition = LastPosition +5;
                    if(items.size()<5){
                        TOTAL_PAGES =currentPage;
                    }
                    if (currentPage != TOTAL_PAGES) adapter.addLoadingFooter();
                    else isLastPage = true;
                }
            }

            @Override
            public void onFailure(Call<ProductSummary> call, Throwable t) {
                t.printStackTrace();
                progressBar.setVisibility(View.GONE);
            }
        });


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
        items.addAll(productSummaries);
        //adapter = new ProductSummaryAdapter(productSummaries,getContext());
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
        public boolean onOptionsItemSelected(MenuItem item) {
            switch (item.getItemId()) {
                // Respond to the action bar's Up/Home button
                case R.id.filtres:
                    intent = new Intent(getActivity(), FiltersActivity.class);
                    startActivity(intent);
                    return true;
            }
            return super.onOptionsItemSelected(item);
        }

    @Override
    public boolean onMenuItemActionCollapse(MenuItem item) {
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        setQuery(query);
        LastPosition= 0;
        loadFirstPage();
        /*
        *
        LastPosition = 0;
        currentPage = 0;
        loadNextPage();
        *
        *
        * */
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {


      /*
      *   setQuery(newText);
        loadNextPage();
      *
      *
      * */
        return true;
    }
}
