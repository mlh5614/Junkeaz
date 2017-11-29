package junkeaz.junkeaz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

//CLASS ISN'T BEING USED -- CAN BE DELETED

public class NewPost extends AppCompatActivity {
    Button postItemButton;
    Button uploadImageButton;
    EditText postDescription;
    String description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_post);

        postItemButton = (Button) findViewById(R.id.postItemButton);
        uploadImageButton = (Button) findViewById(R.id.uploadImageButton);
        postDescription = (EditText) findViewById(R.id.postDescription);

       /* postItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(NewPost.this, MainMenu.class));
            }
        });

        uploadImageButton.setOnClickListener (new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(NewPost.this, ImageUploads.class));
            }
        }); */



    }

    @Override
    public void onResume(){
        super.onResume();
        //idk if it matters

    }

    public void buttonOnClick(View view) {
        switch (view.getId()) {
            case R.id.postItemButton:
                description = postDescription.getText().toString();
                //startActivity(new Intent(NewPost.this, MainMenu.class));
                break;

            case R.id.uploadImageButton:
                startActivity(new Intent(NewPost.this, ImageUploads.class));
                break;
            default:
                //do nothing
                break;

        }
    }

}
