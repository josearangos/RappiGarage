package co.edu.udea.rappigarage_android.Home.Adapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import co.edu.udea.rappigarage_android.Home.API.ProductSummary;
import co.edu.udea.rappigarage_android.Home.API.Search;
import co.edu.udea.rappigarage_android.R;

public class ProductSummaryAdapter extends RecyclerView.Adapter<ProductSummaryAdapter.Holderview> {


    private List<Search> productSummaries;
    private Context context;

    public ProductSummaryAdapter(List<Search> productSummaries, Context context) {
        this.productSummaries = productSummaries;
        this.context = context;
    }

    @NonNull
    @Override
    public Holderview onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.summaryproduct,parent,false);
        return new Holderview(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holderview holder, int position) {

            if(productSummaries.get(position).getPhotos().size() >0){
                Uri uri = Uri.parse(productSummaries.get(position).getPhotos().get(0).getSource());
                holder.imageProduct.setImageURI(uri);
            }
            holder.title.setText(productSummaries.get(position).getName());
            holder.price.setText(String.valueOf(productSummaries.get(position).getPrice()));
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context,"Clic in"+String.valueOf(productSummaries.get(position).getId()),Toast.LENGTH_SHORT).show();
                }
            });
    }

    @Override
    public int getItemCount() {
        return productSummaries.size();
    }

    public void setFilter(List<Search> summaries){
        productSummaries = new ArrayList<>();
        productSummaries.addAll(summaries);
        notifyDataSetChanged();
    }

    public static class Holderview extends RecyclerView.ViewHolder{
        SimpleDraweeView imageProduct;
        TextView title,price;

        Holderview(View itemView){
            super(itemView);
            imageProduct = (SimpleDraweeView)itemView.findViewById(R.id.imageProduct);
            title = (TextView)itemView.findViewById(R.id.title);
            price = (TextView)itemView.findViewById(R.id.price);

        }
    }

}
