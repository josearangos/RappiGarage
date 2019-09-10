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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import co.edu.udea.rappigarage_android.GlobalServices.Category.Category;
import co.edu.udea.rappigarage_android.Home.API.ProductSummary;
import co.edu.udea.rappigarage_android.Home.API.Search;
import co.edu.udea.rappigarage_android.Home.Adapters.ProductSummaryAdapter;
import co.edu.udea.rappigarage_android.Home.Adapters.ProductSummaryAdapterPro;
import co.edu.udea.rappigarage_android.Home.Filters.FiltersActivity;
import co.edu.udea.rappigarage_android.Product.Publish.ProductFormActivity;
import co.edu.udea.rappigarage_android.R;

    public class Home extends Fragment  implements  IHome.IView, SearchView.OnQueryTextListener, MenuItem.OnActionExpandListener {



    int firstVisibleItem, visibleItemCount, totalItemCount;
    Intent intent;
    private ProgressBar progressBar;
    HashMap<String,Integer> categoriesList ;
    private ChipGroup tagGroup_categories;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    //private ProductSummaryAdapter adapter;
    private String query = "";

    private  List<Search> items = new ArrayList<>();
    ProductSummaryAdapterPro adapterPro ;



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
        this.presenter.getProducts(getQuery(),0,10);



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

        items.clear();
        layoutManager = new GridLayoutManager(getContext(),2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        adapterPro = new ProductSummaryAdapterPro(recyclerView,getActivity(),items);
        recyclerView.setAdapter(adapterPro);

        adapterPro.setLoadMore(new ILoadMore() {
            @Override
            public void onLoadMode() {
                if(items.size()<= items.size()){//Maximo de items
                    items.add(null);
                    adapterPro.notifyItemInserted(items.size()-1);

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            items.remove(items.size()-1);
                            adapterPro.notifyItemRemoved(items.size());
                            int index = items.size();
                            int end = index +2;

                            //LLENA LISTA DE ITEMS CON EL METODO ADD O ADDALL
                            presenter.getProducts(getQuery(),index,end);
                        }
                    },5000); //tiempo de carga


                }else{
                    Toast.makeText(getContext(),"NO hay mas datos", Toast.LENGTH_SHORT).show();
                }
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
        //recyclerView.setAdapter(adapter);
        //adapter.notifyDataSetChanged();
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
        items.clear();
        this.presenter.getProducts(query,0,10);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        setQuery(newText);
        items.clear();
        this.presenter.getProducts(newText,0,10);
        return true;
    }
}
