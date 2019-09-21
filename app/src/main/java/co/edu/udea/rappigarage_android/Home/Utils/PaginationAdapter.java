package co.edu.udea.rappigarage_android.Home.Utils;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import co.edu.udea.rappigarage_android.Home.API.Search;
import co.edu.udea.rappigarage_android.R;

public class PaginationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int ITEM = 0;
    private static final int LOADING = 1;

    private List<Search> items;
    private Context context;

    private boolean isLoadingAdded = false;

    public PaginationAdapter(Context context) {
        this.context = context;
        items = new ArrayList<>();
    }

    public List<Search> getItems() {
        return items;
    }

    public void setItems(List<Search> items) {
        this.items = items;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType) {
            case ITEM:
                viewHolder = getViewHolder(parent, inflater);
                break;
            case LOADING:
                View v2 = inflater.inflate(R.layout.item_loading, parent, false);
                viewHolder = new LoadingVH(v2);
                break;
        }
        return viewHolder;
    }

    @NonNull
    private RecyclerView.ViewHolder getViewHolder(ViewGroup parent, LayoutInflater inflater) {
        RecyclerView.ViewHolder viewHolder;
        View v1 = inflater.inflate(R.layout.summaryproduct, parent, false);
        viewHolder = new SearcVH(v1);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        Search search = items.get(position); // Movie

        switch (getItemViewType(position)) {
            case ITEM:
                final SearcVH searcVH = (SearcVH) holder;

                if(items.get(position).getPhotos().size() >0){
                    Uri uri = Uri.parse(search.getPhotos().get(0).getSource());
                    searcVH.imageProduct.setImageURI(uri);
                }
                searcVH.title.setText(search.getName());
                searcVH.price.setText("$"+String.valueOf(search.getPrice()));
                //System.out.println("ID:"+String.valueOf(search.getId()));
                break;

            case LOADING:
//                Do nothing
                break;
        }

    }

    @Override
    public int getItemCount() {
        return items == null ? 0 : items.size();
    }

    @Override
    public int getItemViewType(int position) {
        return (position == items.size() - 1 && isLoadingAdded) ? LOADING : ITEM;
    }


    /*
   Helpers
   _________________________________________________________________________________________________
    */

    public void add(Search r) {
        items.add(r);
        notifyItemInserted(items.size() - 1);
    }

    public void addAll(List<Search> searches) {
        for (Search result : searches) {
            add(result);
        }
    }

    public void remove(Search r) {
        int position = items.indexOf(r);
        if (position > -1) {
            items.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void clear() {
        isLoadingAdded = false;
        while (getItemCount() > 0) {
            remove(getItem(0));
        }
    }

    public boolean isEmpty() {
        return getItemCount() == 0;
    }


    public void addLoadingFooter() {
        isLoadingAdded = true;
        add(new Search());
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;

        int position = items.size() - 1;
        Search result = getItem(position);

        if (result != null) {
            items.remove(position);
            notifyItemRemoved(position);
        }
    }

    public Search getItem(int position) {
        return items.get(position);
    }


   /*
   View Holders
   _________________________________________________________________________________________________
    */

    /**
     * Main list's content ViewHolder
     */
    protected class SearcVH extends RecyclerView.ViewHolder {
        SimpleDraweeView imageProduct;
        TextView title,price;


        public SearcVH(View itemView) {
            super(itemView);

            imageProduct = (SimpleDraweeView)itemView.findViewById(R.id.imageProduct);
            title = (TextView)itemView.findViewById(R.id.title);
            price = (TextView)itemView.findViewById(R.id.price);
        }
    }


    protected class LoadingVH extends RecyclerView.ViewHolder {

        public LoadingVH(View itemView) {
            super(itemView);
        }
    }


}
