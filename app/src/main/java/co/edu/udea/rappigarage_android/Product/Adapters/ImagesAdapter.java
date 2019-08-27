package co.edu.udea.rappigarage_android.Product.Adapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.jackandphantom.circularimageview.CircleImage;
import com.jackandphantom.circularimageview.RoundedImage;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import co.edu.udea.rappigarage_android.R;

public class ImagesAdapter extends RecyclerView.Adapter<ImagesAdapter.ImagesViewHolder> {

    private ArrayList<String> horizontalImgList;
    Context context;

    public ImagesAdapter(ArrayList<String> horizontalImgList, Context context) {
        this.horizontalImgList = horizontalImgList;
        this.context = context;
    }

    @NonNull
    @Override
    public ImagesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_camera, parent, false);
        ImagesViewHolder ivh = new ImagesViewHolder(view);
        return ivh;

    }

    @Override
    public void onBindViewHolder(@NonNull ImagesViewHolder holder, int position) {
        holder.imageView.setImageURI(Uri.parse(horizontalImgList.get(position)));
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(context,  horizontalImgList.get(position)+" is selected" , Toast.LENGTH_SHORT).show();
                horizontalImgList.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, horizontalImgList.size());
                holder.imageView.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public int getItemCount() {
        return horizontalImgList.size();
    }

    public class ImagesViewHolder extends RecyclerView.ViewHolder {
        RoundedImage imageView;
        FloatingActionButton delete ;
        public ImagesViewHolder(View view) {
            super(view);
            imageView=(RoundedImage)  view.findViewById(R.id.image);
            delete =(FloatingActionButton) view.findViewById(R.id.delete);
        }
    }

}
