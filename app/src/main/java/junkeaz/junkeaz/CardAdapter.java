package junkeaz.junkeaz;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder> {
    //Imageloader to load image
    private ImageLoader imageLoader;
    private Context context;

    //List to store all superheroes
    private List<JunkeazListing> junkeazListings;
    private View view;
    private JunkeazListing currentItem;
    private String st;
    private String getPostId="";
    private String getClaimingUserName;
    private String getClaimingUserId;
    private String getClaimingUserEmail;
    public static String URL = "http://junkeaz.xyz/postUpdateClaim.php";

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
        holder.textViewPostId.setText(Integer.toString(junkeazListing.getId()));
        holder.textViewTitle.setText(junkeazListing.getTitle());
        holder.textViewPostingUser.setText(junkeazListing.getPostingUser());
        holder.textViewDescription.setText(junkeazListing.getDescription());
        holder.textViewStreetAddress.setText(junkeazListing.getStreetAddress());
        holder.textViewClaimStatus.setText(junkeazListing.getClaimStatus());
        holder.textViewClaimingUserEmail.setText(junkeazListing.getClaimingUserEmail());
        holder.textViewPostingUserEmail.setText(junkeazListing.getPostingUserEmail());
        holder.textViewPostingUserId.setText(junkeazListing.getPostingUserId());
        holder.textViewClaimingUserId.setText(junkeazListing.getClaimingUserId());
        holder.textViewClaimingUserName.setText(junkeazListing.getClaimingUser());

    }

    @Override
    public int getItemCount() {
        return junkeazListings.size();
    }

    public void updateClaimToDB(CharSequence postId) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            getClaimingUserName = user.getDisplayName();
            getClaimingUserId = user.getUid();
            getClaimingUserEmail = user.getEmail();
        }

        Log.v("log_tag","postId="+postId);
        getPostId = (String) postId;
        //put class in here to upload the claim if the user pressed claim
        //add contact information for both users to UI, update for claimer if success
        //also on success change claimed to yes (only do this if not claimed already (text=no))
        //functionality could be different if the user is the poster, toast 'u can't claim ur own item'
        {


            class UploadToServer extends AsyncTask<Void, Void, String> {

                private ProgressDialog pd = new ProgressDialog(context);

                protected void onPreExecute() {
                    super.onPreExecute();
                    pd.setMessage("Wait... claiming item!");
                    pd.show();
                }

                @Override
                protected String doInBackground(Void... params) {

                    ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

                    Log.v("log_tag","getPostId=" + getPostId);
                  //add nameValuePairs
                      nameValuePairs.add(new BasicNameValuePair("PostId",getPostId));
                      nameValuePairs.add(new BasicNameValuePair("ClaimingUser",getClaimingUserId));
                      nameValuePairs.add(new BasicNameValuePair("ClaimingUserName",getClaimingUserName));
                      nameValuePairs.add(new BasicNameValuePair("ClaimingUserEmail",getClaimingUserEmail));
                    try {
                        HttpClient httpclient = new DefaultHttpClient();
                        HttpPost httppost = new HttpPost(URL);
                        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                        HttpResponse response = httpclient.execute(httppost);
                        st = EntityUtils.toString(response.getEntity());
                        Log.v("log_tag", "In the try Loop" + st);


                    } catch (Exception e) {
                        Log.v("log_tag", "Error in http connection " + e.toString());

                    }
                    return "Success";

                }


                protected void onPostExecute(String result) {
                    super.onPostExecute(result);
                    pd.hide();
                    pd.dismiss();
                    Toast.makeText(context,st,Toast.LENGTH_LONG).show();

                    if (st.contains("Claimed")) {

                        //Log.v("log_tag","Wow actually claimed an item");
                        //updateUIclaim(getPostId);

                        //do something for success like update the UI to say it was claimed

                    }
                }
            }
            UploadToServer AsyncTaskUploadClassOBJ = new UploadToServer();

            AsyncTaskUploadClassOBJ.execute();
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        //Views
        public NetworkImageView imageView;
        public TextView textViewPostId;
        public TextView textViewTitle;
        public TextView textViewPostingUser;
        public TextView textViewDescription;
        public TextView textViewStreetAddress;
        public TextView textViewClaimStatus;
        public TextView textViewPostingUserId;
        public TextView textViewClaimingUserId;
        public TextView textViewClaimingUserName;
        public TextView textViewClaimingUserEmail;
        public TextView textViewPostingUserEmail;
        public Button buttonClaimItem;

        //Initializing Views
        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (NetworkImageView) itemView.findViewById(R.id.imageView);
            textViewPostId = (TextView) itemView.findViewById(R.id.textViewPostId);
            textViewTitle = (TextView) itemView.findViewById(R.id.textViewTitle);
            textViewPostingUser = (TextView) itemView.findViewById(R.id.textViewPostingUser);
            textViewDescription = (TextView) itemView.findViewById(R.id.textViewDescription);
            textViewStreetAddress = (TextView) itemView.findViewById(R.id.textViewStreetAddress);
            textViewClaimStatus = (TextView) itemView.findViewById(R.id.textViewClaimStatus);
            textViewPostingUserId = (TextView) itemView.findViewById(R.id.textViewPostingUserId);
            textViewClaimingUserId = (TextView) itemView.findViewById(R.id.textViewClaimingUserId);
            textViewClaimingUserName = (TextView) itemView.findViewById(R.id.textViewClaimingUserName);
            textViewClaimingUserEmail = (TextView) itemView.findViewById(R.id.textViewClaimingUserEmail);
            textViewPostingUserEmail = (TextView) itemView.findViewById(R.id.textViewPostingUserEmail);
            buttonClaimItem = (Button) itemView.findViewById(R.id.buttonClaimItem);
            view = itemView;
            view.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick (View view) {
                    //clicked card code
                    buttonClaimItem.setVisibility(View.VISIBLE);
                    Log.v("log_tag", "textViewPostId.getText()=" + textViewPostId.getText());
                }
            });

          //  final String s = (String) textViewPostId.getText();
            buttonClaimItem.setOnClickListener(new View.OnClickListener() {

                @Override public void onClick(View view) {
                    buttonClaimItem.setVisibility(View.GONE);
                    if (textViewClaimStatus.getText().equals("no")) {
                        updateClaimToDB(textViewPostId.getText());
                        Log.v("log_tag", "buttonClaimItemClickedForPost" + textViewPostId.getText());
                        // Log.v("log_tag","s="+s);
                    } else {
                        Toast.makeText(context,"This Item Has Already Been Claimed! Sorry!",Toast.LENGTH_LONG).show();
                    }
                }
            });


        }
    }
}
