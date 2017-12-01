package junkeaz.junkeaz;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ViewPosts extends AppCompatActivity {

    private RecyclerView recyclerView;
    private GridLayoutManager gridLayoutManager;
    private CustomAdapter adapter;
    private List<JunkeazListing> junkeaz_listings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_posts);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        junkeaz_listings = new ArrayList<>();
        load_data_from_server(0);

        gridLayoutManager = new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(gridLayoutManager);

        adapter = new CustomAdapter(this,junkeaz_listings);
        recyclerView.setAdapter(adapter);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

                if(gridLayoutManager.findLastCompletelyVisibleItemPosition() == junkeaz_listings.size()-1){
                    load_data_from_server(junkeaz_listings.get(junkeaz_listings.size()-1).getId());
                }
            }
        });
    }

    private void load_data_from_server(final int id) {
        AsyncTask<Integer,Void,Void> task = new AsyncTask<Integer, Void, Void>() {
            @Override
            protected Void doInBackground(Integer... integers) {

                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url("https://junkeaz.000webhostapp.com/pullingFromServer.php?id="+id)
                        .build();
                try {

                    Response response = client.newCall(request).execute();
                    JSONArray array = new JSONArray(response.body().string());

                    for (int i=0; i<array.length(); i++){

                        JSONObject object = array.getJSONObject(i);

                        /*    private int id;
                        private String imageUrl;
                        private String title;
                        private String postingUser;
                        private String description;
                        private String streetAddress;
                        private String claimStatus; */

                        JunkeazListing data = new JunkeazListing(object.getInt("id"),object.getString("image_path"),object.getString("title"),object.getString("posting_user_name"),object.getString("description"),object.getString("street_address"),object.getString("claimed"));

                        junkeaz_listings.add(data);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    System.out.println("End of content");
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                adapter.notifyDataSetChanged();
            }
        };

        task.execute(id);
    }

}
