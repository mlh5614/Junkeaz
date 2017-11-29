package junkeaz.junkeaz;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Profile extends AppCompatActivity {
    Button button;
    Button mainMenuButton;
    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListener;
    ImageView imageView;
    TextView textName, textEmail;

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mainMenuButton = (Button) findViewById(R.id.mainMenuButton);
        button = (Button) findViewById(R.id.sign_out_button);
        mAuth = FirebaseAuth.getInstance();

        imageView = findViewById(R.id.imageView);
        textName = findViewById(R.id.textName);
        textEmail = findViewById(R.id.textEmail);

        FirebaseUser user = mAuth.getCurrentUser();

        Glide.with(this)
                .load(user.getPhotoUrl())
                .into(imageView);
        textName.setText(user.getDisplayName());
        textEmail.setText((user.getEmail()));

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() == null){
                    startActivity(new Intent(Profile.this, MainActivity.class));
                }
            }
        };

        mainMenuButton.setOnClickListener(new View.OnClickListener() {
           @Override
            public void onClick(View v) {
               startActivity(new Intent(Profile.this, MainMenu.class));
           }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
            }
        });

    }

    @Override
    public void onResume(){
        super.onResume();
        //idk if it matters

    }

}
