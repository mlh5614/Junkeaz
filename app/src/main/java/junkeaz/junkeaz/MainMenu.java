package junkeaz.junkeaz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
    }

    public void buttonOnClick(View view) {
        switch (view.getId()) {
            case R.id.profileButton:
                startActivity(new Intent(MainMenu.this, Profile.class));
                break;

            case R.id.newPostButton:
                startActivity(new Intent(MainMenu.this, ImageUploads.class));
                break;
            case R.id.viewListings:
                startActivity(new Intent( MainMenu.this, NewPost.class));
                break;

        }
    }
}
