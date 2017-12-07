package junkeaz.junkeaz;

import android.app.ProgressDialog;
        import android.content.Intent;
        import android.content.pm.PackageManager;
        import android.database.Cursor;
        import android.graphics.Bitmap;
        import android.graphics.BitmapFactory;
        import android.net.Uri;
        import android.os.AsyncTask;
        import android.os.Bundle;
        import android.provider.MediaStore;
//import android.util.Base64;
        import android.support.v7.app.AppCompatActivity;
        import android.util.Base64;
        import android.util.Log;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.ImageView;
        import android.widget.Toast;

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

        import java.io.ByteArrayOutputStream;
        import java.io.IOException;
        import java.util.ArrayList;


public class PostListing extends AppCompatActivity {
    Bitmap bitmap;
    String st;
    Button btpic, btnup;
    Boolean checkPic=false;
    ImageView imageView;
    EditText editTextImageTitle;
    EditText editTextPostDescription;
    EditText editTextStreetAddress;
    String getPostingUserEmail;
    String GetImageTitleEditText;
    String GetPostDescriptionEditText;
    String GetStreetAddressEditText;
    String GetPostingUserName;
    String GetPostingUser;
    Button SelectImageGallery, UploadImageServer;
    private Uri fileUri;
    String picturePath;
    Uri selectedImage;
    Bitmap photo;
    String ba1;
    public static String URL = "http://junkeaz.xyz/postListing4.php";
    //public static String URL = "http://junkeaz.xyz/postUpdateClaim.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitiy_images);

        //set the default image icon
        // bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.firebase_lockup_400);
        //imageView.setImageBitmap(bitmap);


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
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Image From Gallery"), 1);

            }
        });



        //  if (editTextImageTitle.getText().toString().equals("") || editTextImageTitle.equals(null)) {
        //    GetImageTitleEditText = "updateYourTitle";
        // }


        //Get the Post Description


        // if (editTextPostDescription.getText().toString().equals("") || editTextPostDescription.equals(null)) {
        //    GetPostDescriptionEditText = "updateYourDescription";
        //}

        //Get the Street Address



        //set if null or empty
//        if (editTextStreetAddress.getText().toString().equals("")  || editTextStreetAddress.getText().toString().equals(null)) {
        //         GetStreetAddressEditText = "updateYourAddress";
        //    }

        //Get the User ID and Display Name





        UploadImageServer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



            if (checkPic == true) {
                imageUploadToServer();
            }
            else {
                Toast.makeText(PostListing.this,"You Must Select An Image.",Toast.LENGTH_LONG).show();
            }
            }
        });
    }



    private void clickpic() {
        // Check Camera
        if (getApplicationContext().getPackageManager().hasSystemFeature(
                PackageManager.FEATURE_CAMERA)) {
            // Open default camera
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);

            // start the image capture Intent
            startActivityForResult(intent, 100);

        } else {
            Toast.makeText(getApplication(), "Camera not supported", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onActivityResult(int RC, int RQC, Intent I) {
        super.onActivityResult(RC, RQC, I);

        if (RC == 1 && RQC == RESULT_OK && I != null && I.getData() != null) {

            Uri uri = I.getData();

            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);

                imageView.setImageBitmap(bitmap);

                checkPic = true;
                //imageView.setImageResource(android.R.color.transparent);
            } catch (IOException e) {

                e.printStackTrace();
            }
        }
    }

    private void upload() {
        // Image location URL
        Log.e("path", "----------------" + picturePath);

        // Image
        Bitmap bm = BitmapFactory.decodeFile(picturePath);
        ByteArrayOutputStream bao;
        bao = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 90, bao);
        //bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStreamObject);
        byte[] ba = bao.toByteArray();
        //ba1 = Base64.encodeBytes(ba);

        Log.e("base64", "-----" + ba1);

        // Upload image to server
        //new uploadToServer().execute();

    }
    public void imageUploadToServer() {
        //Get the User ID and Display Name
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            GetPostingUserName = user.getDisplayName();
            getPostingUserEmail = user.getEmail();
            Uri photoUrl = user.getPhotoUrl();
            boolean emailVerified = user.isEmailVerified();
            GetPostingUser = user.getUid();

            //  System.out.print("user.getUid() = "+user.getUid());
        }
        GetImageTitleEditText = editTextImageTitle.getText().toString();
        GetPostDescriptionEditText = editTextPostDescription.getText().toString();
        GetStreetAddressEditText = editTextStreetAddress.getText().toString();
        ByteArrayOutputStream byteArrayOutputStreamObject;

        byteArrayOutputStreamObject = new ByteArrayOutputStream();

        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStreamObject);

        byte[] byteArrayVar = byteArrayOutputStreamObject.toByteArray();

        final String ConvertImage = Base64.encodeToString(byteArrayVar, Base64.NO_WRAP);

        class UploadToServer extends AsyncTask<Void, Void, String> {

            private ProgressDialog pd = new ProgressDialog(PostListing.this);

            protected void onPreExecute() {
                super.onPreExecute();
                pd.setMessage("Wait... post uploading!");
                pd.show();
            }

            @Override
            protected String doInBackground(Void... params) {

                ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("base64", ConvertImage));
                nameValuePairs.add(new BasicNameValuePair("PostingUser",GetPostingUser));
                nameValuePairs.add(new BasicNameValuePair("PostingUserName",GetPostingUserName));
                nameValuePairs.add(new BasicNameValuePair("PostingUserEmail",getPostingUserEmail));
                nameValuePairs.add(new BasicNameValuePair("PostDescription",GetPostDescriptionEditText));
                nameValuePairs.add(new BasicNameValuePair("StreetAddress",GetStreetAddressEditText));
                nameValuePairs.add(new BasicNameValuePair("PostTitle", GetImageTitleEditText));
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
                Toast.makeText(PostListing.this,st,Toast.LENGTH_LONG).show();

                if (st.contains("Been Uploaded")) {

                    imageView.setImageResource(android.R.color.transparent);
                    checkPic = false;
                    editTextImageTitle.setText("");
                    editTextPostDescription.setText("");
                    editTextStreetAddress.setText("");
                    //startActivity(new Intent(ImageUploads.this, MainMenu.class));


                    // Intent openMainActivity= new Intent(MainActivity.this, MainMenu.class);
                    // openMainActivity.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    //startActivityIfNeeded(openMainActivity, 0);
                }
            }
        }
        UploadToServer AsyncTaskUploadClassOBJ = new UploadToServer();

        AsyncTaskUploadClassOBJ.execute();
    }
}