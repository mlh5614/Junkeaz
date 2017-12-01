package junkeaz.junkeaz;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

    private Context context;
    private List<JunkeazListing> junkeaz_listings;

    public CustomAdapter(Context context, List<JunkeazListing> junkeaz_listings) {
        this.context = context;
        this.junkeaz_listings = junkeaz_listings;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card,parent,false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.description.setText(junkeaz_listings.get(position).getDescription());
        Glide.with(context).load(junkeaz_listings.get(position).getImageUrl()).into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return junkeaz_listings.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        public TextView description;
        public ImageView imageView;


        public ViewHolder(View itemView) {
            super(itemView);
            description = (TextView) itemView.findViewById(R.id.description);
            imageView = (ImageView) itemView.findViewById(R.id.image);
        }
    }
}
