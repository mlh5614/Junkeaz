package junkeaz.junkeaz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainMenu extends AppCompatActivity {
    Button profileButton;
    Button newPostButton;
    Button viewListings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        profileButton = (Button) findViewById(R.id.profileButton);
        newPostButton = (Button) findViewById(R.id.newPostButton);
        viewListings = (Button) findViewById(R.id.viewListings);
    }

    @Override
    public void onResume(){
        super.onResume();
        //idk if it matters

    }

    public void buttonOnClick(View view) {
        switch (view.getId()) {
            case R.id.profileButton:
                Intent openProfileActivity= new Intent(MainMenu.this, MainActivity.class);
                openProfileActivity.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivityIfNeeded(openProfileActivity, 0);
                //startActivity(new Intent (MainMenu.this, MainActivity.class));
                //startActivity(new Intent(MainMenu.this, Profile.class));
                break;

            case R.id.newPostButton:
                Intent openPostActivity= new Intent(MainMenu.this, PostListing.class);
                openPostActivity.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivityIfNeeded(openPostActivity, 0);
                //startActivity(new Intent(MainMenu.this, ImageUploads.class));
                break;
            case R.id.viewListings:
                Intent openListActivity= new Intent(MainMenu.this, ViewListings.class);
                openListActivity.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivityIfNeeded(openListActivity, 0);
                //Intent intent = new Intent(MainMenu.this, ViewListings.class);
                //startActivity(intent);
                //startActivity(new Intent( MainMenu.this, ViewListings.class));
                break;
            default:
                //do nothing
                break;

        }
    }
}
