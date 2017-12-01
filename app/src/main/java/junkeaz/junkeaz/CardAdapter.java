package junkeaz.junkeaz;

import android.content.Context;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder> {
    //Imageloader to load image
    private ImageLoader imageLoader;
    private Context context;

    //List to store all superheroes
    List<JunkeazListing> junkeazListings;

    //Constructor of this class
    public CardAdapter(List<JunkeazListing> junkeazListings, Context context){
        super();
        //Getting all superheroes
        this.junkeazListings = junkeazListings;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.listing_layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        //Getting the particular item from the list
        JunkeazListing junkeazListing =  junkeazListings.get(position);

        //Loading image from url
        imageLoader = CustomVolleyRequest.getInstance(context).getImageLoader();
        imageLoader.get(junkeazListing.getImageUrl(), ImageLoader.getImageListener(holder.imageView, R.mipmap.ic_launcher, android.R.drawable.ic_dialog_alert));

        //Showing data on the views
        holder.imageView.setImageUrl(junkeazListing.getImageUrl(), imageLoader);
        holder.textViewTitle.setText(junkeazListing.getTitle());
        holder.textViewPostingUser.setText(junkeazListing.getPostingUser());
        holder.textViewDescription.setText(junkeazListing.getDescription());
        holder.textViewStreetAddress.setText(junkeazListing.getStreetAddress());
        holder.textViewClaimStatus.setText(junkeazListing.getClaimStatus());
    }

    @Override
    public int getItemCount() {
        return junkeazListings.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        //Views
        public NetworkImageView imageView;
        public TextView textViewTitle;
        public TextView textViewPostingUser;
        public TextView textViewDescription;
        public TextView textViewStreetAddress;
        public TextView textViewClaimStatus;

        //Initializing Views
        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (NetworkImageView) itemView.findViewById(R.id.imageView);
            textViewTitle = (TextView) itemView.findViewById(R.id.textViewTitle);
            textViewPostingUser = (TextView) itemView.findViewById(R.id.textViewPostingUser);
            textViewDescription = (TextView) itemView.findViewById(R.id.textViewDescription);
            textViewStreetAddress = (TextView) itemView.findViewById(R.id.textViewStreetAddress);
            textViewClaimStatus = (TextView) itemView.findViewById(R.id.textViewClaimStatus);
        }
    }
}
