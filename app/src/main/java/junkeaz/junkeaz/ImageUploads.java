package junkeaz.junkeaz;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.widget.EditText;
import android.widget.ImageView;
import android.net.Uri;
import android.provider.MediaStore;
import java.io.BufferedReader;
import java.net.URLEncoder;
import java.util.Map;
import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;
import java.io.UnsupportedEncodingException;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ImageUploads extends AppCompatActivity {
    Bitmap bitmap;
    boolean check = true;
    Button SelectImageGallery, UploadImageServer;
    ImageView imageView;
    EditText editTextImageTitle;
    EditText editTextPostDescription;
    EditText editTextStreetAddress;
    ProgressDialog progressDialog ;
    String GetImageTitleEditText = "title1";
    String GetPostDescriptionEditText = "description1";
    String GetStreetAddressEditText = "123 abc st";
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
    String ServerUploadPath ="https://junkeaz.000webhostapp.com/post_listing_to_server.php" ;

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
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Image From Gallery"), 1);

            }
        });


        UploadImageServer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Get the Post Title
                GetImageTitleEditText = editTextImageTitle.getText().toString();

                //Get the Post Description
                GetPostDescriptionEditText = editTextPostDescription.getText().toString();

                //Get the User ID and Display Name
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null) {
                    GetPostingUserName = user.getDisplayName();
                    String email = user.getEmail();
                    Uri photoUrl = user.getPhotoUrl();
                    boolean emailVerified = user.isEmailVerified();
                    GetPostingUser = user.getUid();
                }

                //Get the Street Address
                GetStreetAddressEditText = editTextStreetAddress.getText().toString();

                ImageUploadToServerFunction();
            }
        });
    }

    @Override
    public void onResume(){
        super.onResume();
        //idk if it matters

    }

    @Override
    protected void onActivityResult(int RC, int RQC, Intent I) {
        super.onActivityResult(RC, RQC, I);

        if (RC == 1 && RQC == RESULT_OK && I != null && I.getData() != null) {

            Uri uri = I.getData();

            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);

                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {

                e.printStackTrace();
            }
        }
    }

    public void ImageUploadToServerFunction(){

        ByteArrayOutputStream byteArrayOutputStreamObject ;

        byteArrayOutputStreamObject = new ByteArrayOutputStream();

        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStreamObject);

        byte[] byteArrayVar = byteArrayOutputStreamObject.toByteArray();

        final String ConvertImage = Base64.encodeToString(byteArrayVar, Base64.DEFAULT);

        class AsyncTaskUploadClass extends AsyncTask<Void,Void,String> {

            @Override
            protected void onPreExecute() {

                super.onPreExecute();

                progressDialog = ProgressDialog.show(ImageUploads.this,"Image is Uploading","Please Wait",false,false);
            }

            @Override
            protected void onPostExecute(String string1) {

                super.onPostExecute(string1);

                // Dismiss the progress dialog after done uploading.
                progressDialog.dismiss();

                // Printing uploading success message coming from server on android app.
                Toast.makeText(ImageUploads.this,string1,Toast.LENGTH_LONG).show();

                if (string1.equals("Your Post Has Been Uploaded.")) {
                    startActivity(new Intent(ImageUploads.this, MainMenu.class));

                }

                // Setting image as transparent after done uploading.
                imageView.setImageResource(android.R.color.transparent);


            }

            @Override
            protected String doInBackground(Void... params) {

                ImageUploads.ImageProcessClass imageProcessClass = new ImageUploads.ImageProcessClass();

                HashMap<String,String> HashMapParams = new HashMap<String,String>();

                //posting_user,posting_user_name,title,description,image_path,street_address,claimed,claiming_user,claiming_user_name

                //posting_user
                HashMapParams.put(PostingUser, GetPostingUser);

                //posting_user_name
                HashMapParams.put(PostingUserName, GetPostingUserName);

                //title
                HashMapParams.put(ImageTitle, GetImageTitleEditText);

                //description
                HashMapParams.put(PostDescription, GetPostDescriptionEditText);

                //image_path
                HashMapParams.put(ImagePath, ConvertImage);

                //street_address
                HashMapParams.put(StreetAddress, GetStreetAddressEditText);

                //claimed
                HashMapParams.put(Claimed, GetClaimed);

                //claiming_user
                HashMapParams.put(ClaimingUser, GetClaimingUser);

                //claiming_user_name
                HashMapParams.put(ClaimingUserName, GetClaimingUserName);

                String FinalData = imageProcessClass.ImageHttpRequest(ServerUploadPath, HashMapParams);

                return FinalData;
            }
        }
        AsyncTaskUploadClass AsyncTaskUploadClassOBJ = new AsyncTaskUploadClass();

        AsyncTaskUploadClassOBJ.execute();
    }

    public class ImageProcessClass{

        public String ImageHttpRequest(String requestURL,HashMap<String, String> PData) {

            StringBuilder stringBuilder = new StringBuilder();

            try {

                URL url;
                HttpURLConnection httpURLConnectionObject ;
                OutputStream OutPutStream;
                BufferedWriter bufferedWriterObject ;
                BufferedReader bufferedReaderObject ;
                int RC ;

                url = new URL(requestURL);

                httpURLConnectionObject = (HttpURLConnection) url.openConnection();

                httpURLConnectionObject.setReadTimeout(19000);

                httpURLConnectionObject.setConnectTimeout(19000);

                httpURLConnectionObject.setRequestMethod("POST");

                httpURLConnectionObject.setDoInput(true);

                httpURLConnectionObject.setDoOutput(true);

                OutPutStream = httpURLConnectionObject.getOutputStream();

                bufferedWriterObject = new BufferedWriter(

                        new OutputStreamWriter(OutPutStream, "UTF-8"));

                bufferedWriterObject.write(bufferedWriterDataFN(PData));

                bufferedWriterObject.flush();

                bufferedWriterObject.close();

                OutPutStream.close();

                RC = httpURLConnectionObject.getResponseCode();

                if (RC == HttpsURLConnection.HTTP_OK) {

                    bufferedReaderObject = new BufferedReader(new InputStreamReader(httpURLConnectionObject.getInputStream()));

                    stringBuilder = new StringBuilder();

                    String RC2;

                    while ((RC2 = bufferedReaderObject.readLine()) != null){

                        stringBuilder.append(RC2);
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return stringBuilder.toString();
        }

        private String bufferedWriterDataFN(HashMap<String, String> HashMapParams) throws UnsupportedEncodingException {

            StringBuilder stringBuilderObject;

            stringBuilderObject = new StringBuilder();

            for (Map.Entry<String, String> KEY : HashMapParams.entrySet()) {

                if (check)

                    check = false;
                else
                    stringBuilderObject.append("&");

                stringBuilderObject.append(URLEncoder.encode(KEY.getKey(), "UTF-8"));

                stringBuilderObject.append("=");

                stringBuilderObject.append(URLEncoder.encode(KEY.getValue(), "UTF-8"));
            }

            return stringBuilderObject.toString();
        }

    }
}
