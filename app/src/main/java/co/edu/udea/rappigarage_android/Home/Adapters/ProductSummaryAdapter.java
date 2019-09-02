package co.edu.udea.rappigarage_android.Home.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import co.edu.udea.rappigarage_android.Home.API.ProductSummary;
import co.edu.udea.rappigarage_android.R;

public class ProductSummaryAdapter extends RecyclerView.Adapter<ProductSummaryAdapter.Holderview> {


    private ArrayList<ProductSummary> productSummaries;
    private Context context;

    public ProductSummaryAdapter(ArrayList<ProductSummary> productSummaries, Context context) {
        this.productSummaries = productSummaries;
        this.context = context;
    }

    @NonNull
    @Override
    public Holderview onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layaout = LayoutInflater.from(parent.getContext()).inflate(R.layout.summaryproduct,parent,false);
        return new Holderview(layaout);
    }

    @Override
    public void onBindViewHolder(@NonNull Holderview holder, int position) {
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

    public void setFilter(ArrayList<ProductSummary> summaries){
        productSummaries = new ArrayList<>();
        productSummaries.addAll(summaries);
        notifyDataSetChanged();
    }

    class Holderview extends RecyclerView.ViewHolder{
        ImageView imageProduct;
        TextView title,price;

        Holderview(View itemView){
            super(itemView);
            imageProduct = (ImageView)itemView.findViewById(R.id.imageProduct);
            title = (TextView)itemView.findViewById(R.id.title);
            price = (TextView)itemView.findViewById(R.id.price);

        }
    }

}
