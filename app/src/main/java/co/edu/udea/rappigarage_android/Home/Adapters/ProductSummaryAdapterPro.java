package co.edu.udea.rappigarage_android.Home.Adapters;


import android.app.Activity;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import co.edu.udea.rappigarage_android.Home.API.ProductSummary;
import co.edu.udea.rappigarage_android.Home.API.Search;
import co.edu.udea.rappigarage_android.Home.ILoadMore;
import co.edu.udea.rappigarage_android.R;

class LoadingViewHolder extends RecyclerView.ViewHolder{

    ProgressBar progressBar;

    public LoadingViewHolder(@NonNull View itemView) {
        super(itemView);
        progressBar = (ProgressBar)itemView.findViewById(R.id.progressBarP);
    }
}

class ItemViewHolder extends  RecyclerView.ViewHolder{
    SimpleDraweeView imageProduct;
    TextView title,price;

    public ItemViewHolder(@NonNull View itemView) {
        super(itemView);
        imageProduct = (SimpleDraweeView)itemView.findViewById(R.id.imageProduct);
        title = (TextView)itemView.findViewById(R.id.title);
        price = (TextView)itemView.findViewById(R.id.price);
    }
}

public class ProductSummaryAdapterPro extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private final int VIEW_TYPE_ITEM = 0, VIEW_TYPE_LOADING = 1;
    ILoadMore loadMore;
    boolean isLoading;
    Activity activity;
    List<Search> items;
    int visibleThreshold = 5;
    int lastVisibleItem , totalItemCount;



    @Override
    public int getItemViewType(int position) {
        return items.get(position) == null ? VIEW_TYPE_LOADING: VIEW_TYPE_ITEM;

    }

    public List<Search> getItems() {
        return items;
    }

    public void setItems(List<Search> items) {
        this.items = items;
    }

    public ProductSummaryAdapterPro(RecyclerView recyclerView, Activity activity, List<Search> items) {
        this.activity = activity;
        this.items = items;

        final GridLayoutManager gridLayoutManager = (GridLayoutManager) recyclerView.getLayoutManager();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                totalItemCount  = gridLayoutManager.getItemCount();
                lastVisibleItem = gridLayoutManager.findLastVisibleItemPosition();
                if(!isLoading && totalItemCount <= (lastVisibleItem+visibleThreshold)){
                    if(loadMore != null)
                        loadMore.onLoadMode();
                    isLoading = true;

                }
            }
        });


    }

    public void setLoadMore(ILoadMore loadMore) {
        this.loadMore = loadMore;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == VIEW_TYPE_ITEM){
            View view = LayoutInflater.from(activity).inflate(R.layout.summaryproduct,parent,false);
            return  new ItemViewHolder(view);
        }
        if(viewType == VIEW_TYPE_LOADING){
            View view = LayoutInflater.from(activity).inflate(R.layout.item_loading,parent,false);
            return  new LoadingViewHolder(view);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof  ItemViewHolder){
            Search productSummary = items.get(position);
            ItemViewHolder viewHolder = (ItemViewHolder)holder;
            if(items.get(position).getPhotos().size() >0){
                Uri uri = Uri.parse(items.get(position).getPhotos().get(0).getSource());
                viewHolder.imageProduct.setImageURI(uri);
            }
            viewHolder.title.setText(items.get(position).getName());
            viewHolder.price.setText("$"+String.valueOf(items.get(position).getPrice()));

            viewHolder.itemView.setOnClickListener(v -> {
                System.out.println("CLICCCCCCCCCCCCCCC");
                    Toast.makeText(activity.getApplicationContext(),"Clic in"+String.valueOf(items.get(position).getId()),Toast.LENGTH_SHORT).show();

            });
        }
        else if(holder instanceof LoadingViewHolder){
            LoadingViewHolder loadingViewHolder = (LoadingViewHolder)holder;
            loadingViewHolder.progressBar.setIndeterminate(true);
        }

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setLoaded() {
        isLoading = false;
    }
}
