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
                startActivity(new Intent(MainMenu.this, Profile.class));
                break;

            case R.id.newPostButton:
                startActivity(new Intent(MainMenu.this, NewPost.class));
                break;
            case R.id.viewListings:
               // startActivity(new Intent( MainMenu.this, ImageUploads.class));
                break;
            default:
                //do nothing
                break;

        }
    }
}
