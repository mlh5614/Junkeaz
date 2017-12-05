package junkeaz.junkeaz;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;


import com.android.internal.http.multipart.MultipartEntity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;


import java.io.File;
import java.io.FileNotFoundException;

/**
 * Created by dougl on 12/4/2017.
 */

public class WeWillSee extends AppCompatActivity {
    Bitmap bitmap;
    boolean check = true;
    FileNotFoundException e;
    Button SelectImageGallery, UploadImageServer;
    ImageView imageView;
    EditText editTextImageTitle;
    EditText editTextPostDescription;
    EditText editTextStreetAddress;
    ProgressDialog progressDialog ;
    String GetImageTitleEditText = "title1";
    String GetPostDescriptionEditText = "description1";
    String GetStreetAddressEditText = "123abc";
    String GetPostingUser = "user1";
    String GetClaimingUser = "unclaimed";
    String GetPostingUserName = "max";
    String GetClaimingUserName = "unclaimed";
    String GetClaimed = "no";
    String ImageTitle = "title" ;
    String ImagePath = "image_path" ;
    String StreetAddress = "street_address";
    String Claimed = "claimed";
    String ClaimingUser = "claiming_user";
    String ClaimingUserName = "claiming_user_name";
    String PostingUser = "posting_user";
    String PostingUserName = "posting_user_name";
    String PostDescription = "description";
    int column_index;
    String ServerUploadPath ="http://junkeaz.xyz/post_listing_to_server.php" ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitiy_images);


        imageView = (ImageView)findViewById(R.id.imageView);
        editTextImageTitle = (EditText)findViewById(R.id.editTextImageName);
        editTextPostDescription = (EditText)findViewById(R.id.editTextPostDescription);
        editTextStreetAddress = (EditText)findViewById(R.id.editTextStreetAddress);
        SelectImageGallery = (Button)findViewById(R.id.buttonSelect);
        UploadImageServer = (Button)findViewById(R.id.buttonUpload);
        SelectImageGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_PICK);
                startActivityForResult(Intent.createChooser(intent, "Select Image From Gallery"), 1);

            }
        });


        UploadImageServer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Get the Post Title
                GetImageTitleEditText = editTextImageTitle.getText().toString();

                if (editTextImageTitle.getText().toString().equals("")) {
                    GetImageTitleEditText = "title1";
                }


                //Get the Post Description
                GetPostDescriptionEditText = editTextPostDescription.getText().toString();

                if (editTextPostDescription.getText().toString().equals("")) {
                    GetPostDescriptionEditText = "description1";
                }

                //Get the Street Address



                //Get the User ID and Display Name
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null) {
                    GetPostingUserName = user.getDisplayName();
                    String email = user.getEmail();
                    Uri photoUrl = user.getPhotoUrl();
                    boolean emailVerified = user.isEmailVerified();
                    GetPostingUser = user.getUid();

                    //  System.out.print("user.getUid() = "+user.getUid());
                }


            imageUpload();

            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1)
            if (resultCode == Activity.RESULT_OK) {
                Uri selectedImage = data.getData();

                String filePath = getPath(selectedImage);
                String file_extn = filePath.substring(filePath.lastIndexOf(".") + 1);
                editTextImageTitle.setText(filePath);

                try {
                    if (file_extn.equals("img") || file_extn.equals("jpg") || file_extn.equals("jpeg") || file_extn.equals("gif") || file_extn.equals("png")) {
                        //FINE
                    } else {
                        //NOT IN REQUIRED FORMAT
                        throw e;
                    }

                }
                catch (FileNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
    }

    public String getPath(Uri uri) {
        String[] projection = {MediaStore.MediaColumns.DATA};
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        column_index = cursor
                .getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        cursor.moveToFirst();
        final String image_path = cursor.getString(column_index);

        return cursor.getString(column_index);
    }


    public void imageUpload() {
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost("LINK TO SERVER");

       /* MultipartEntity mpEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
        if (filePath != null) {
            File file = new File(filePath);
            Log.d("EDIT USER PROFILE", "UPLOAD: file length = " + file.length());
            Log.d("EDIT USER PROFILE", "UPLOAD: file exist = " + file.exists());
            mpEntity.addPart("avatar", new FileBody(file, "application/octet"));
        }
//error on httpmultipartmode not found
        try {
            httppost.setEntity(mpEntity);
            HttpResponse response = httpclient.execute(httppost);
        }
        catch (java.io.IOException f) {

        }*/
    }
}
