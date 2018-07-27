package com.appentus.vidup;

import android.Manifest;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.googleapis.extensions.android.gms.auth.GooglePlayServicesAvailabilityIOException;
import com.google.api.client.googleapis.extensions.android.gms.auth.UserRecoverableAuthIOException;
import com.google.api.client.googleapis.media.MediaHttpUploader;
import com.google.api.client.googleapis.media.MediaHttpUploaderProgressListener;
import com.google.api.client.http.AbstractInputStreamContent;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.InputStreamContent;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.ExponentialBackOff;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.YouTubeScopes;
import com.google.api.services.youtube.model.Video;
import com.google.api.services.youtube.model.VideoSnippet;
import com.google.api.services.youtube.model.VideoStatus;
import com.nabinbhandari.android.permissions.PermissionHandler;
import com.nabinbhandari.android.permissions.Permissions;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;
public class Youtube extends Activity {
    static final int REQUEST_PERMISSION_GET_ACCOUNTS = 1003;
    private ProgressBar progressBar;
    private Button btnSelectFromGallery;



    GoogleAccountCredential mCredential;
    private TextView mOutputText;
    private Button mCallApiButton;



    private static final String TAG = "MainActivity";
    private static final int CAPTURE_RETURN = 1;
    private static final int GALLERY_RETURN = 2;


    static final int REQUEST_ACCOUNT_PICKER = 1000;
    static final int REQUEST_AUTHORIZATION = 1001;
    static final int REQUEST_GOOGLE_PLAY_SERVICES = 1002;


    private static final String PREF_ACCOUNT_NAME = "accountName";

    private static final String[] SCOPES = {YouTubeScopes.YOUTUBE_READONLY, YouTubeScopes.YOUTUBE_UPLOAD};
    private String vidpath,filePath;
    private SharedPreferences sharedPreferences;
    private String checkCode;

    /**
     * Create the main activity.
     *
     * @param savedInstanceState previously saved instance data.
     */








    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube);



        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

         if (extras != null) {
         if (extras.containsKey("uri")) {
         checkCode = extras.getString("uri", "");
         // TODO: Do something with the value of isNew.
         }

         }
        mCallApiButton = findViewById(R.id.mCallApiButton);
        mOutputText = findViewById(R.id.tv_outputText);
        sharedPreferences= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        getSaveData();
//        mCredential= GoogleAccountCredential.usingOAuth2(getApplicationContext(), Arrays.asList(SCOPES));
//        mCredential.setSelectedAccount(new Account("Rahul53prajapat@gmail.com", "com.appentus.vidup"));
        // Initialize credentials and service object.
        mCredential = GoogleAccountCredential.usingOAuth2(
                getApplicationContext(), Arrays.asList(SCOPES))
                .setBackOff(new ExponentialBackOff());





        mCallApiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Permissions.check(Youtube.this, new String[]{Manifest.permission.GET_ACCOUNTS,
                                Manifest.permission.READ_EXTERNAL_STORAGE},
                        "Accounts and Storage permissions are required ", new Permissions.Options()
                                .setSettingsDialogTitle("Warning!").setRationaleDialogTitle("Info"),
                        new PermissionHandler() {
                            @Override
                            public void onGranted() {
                                mCallApiButton.setEnabled(false);
                                mOutputText.setText("");
                                getResultsFromApi();
                                mCallApiButton.setEnabled(true);
                            }
                        });


            }
        });


        mCallApiButton.performClick();

    }

    private void getResultsFromApi() {

        if (!isGooglePlayServicesAvailable()) {
            acquireGooglePlayServices();
        } else if (mCredential.getSelectedAccountName() == null) {
            chooseAccount();
            Log.e("getName",mCredential.getSelectedAccountName()+"");
        } else if (!isDeviceOnline()) {
            mOutputText.setText("No network connection available.");
        } else {
//            new SaveTokenAsync().execute();
            mOutputText.setText("Credentials Initialized");
//            initVideoPicker();
            Log.e("uriYouTube",checkCode);
            new UploadVideoAsync(Uri.fromFile(new File(checkCode))).execute();
        }
    }

    private void chooseAccount() {
//        String accountName = getPreferences(Context.MODE_PRIVATE)
//                .getString(PREF_ACCOUNT_NAME, null);
//        if (accountName != null) {
//            mCredential.setSelectedAccountName(accountName);
//
////                new SaveTokenAsync().execute();
//
//            getResultsFromApi();
//        } else {
//            // Start a dialog from which the user can choose an account
//            startActivityForResult(
//                    mCredential.newChooseAccountIntent(),
//                    REQUEST_ACCOUNT_PICKER);
//        }

        if (EasyPermissions.hasPermissions(
                this, Manifest.permission.GET_ACCOUNTS)) {
            String accountName = getPreferences(Context.MODE_PRIVATE)
                    .getString(PREF_ACCOUNT_NAME, null);
            if (accountName != null) {
                mCredential.setSelectedAccountName(accountName);
                getResultsFromApi();
            } else {
                // Start a dialog from which the user can choose an account
                startActivityForResult(
                        mCredential.newChooseAccountIntent(),
                        REQUEST_ACCOUNT_PICKER);
            }
        } else {
            // Request the GET_ACCOUNTS permission via a user dialog
            EasyPermissions.requestPermissions(
                    this,
                    "This app needs to access your Google account (via Contacts).",
                    REQUEST_PERMISSION_GET_ACCOUNTS,
                    Manifest.permission.GET_ACCOUNTS);
        }
    }

    /**
     * Called when an activity launched here (specifically, AccountPicker
     * and authorization) exits, giving you the requestCode you started it with,
     * the resultCode it returned, and any additional data from it.
     *
     * @param requestCode code indicating which activity result is incoming.
     * @param resultCode  code indicating the result of the incoming
     *                    activity result.
     * @param data        Intent (containing result data) returned by incoming
     *                    activity result.
     */
    @Override
    protected void onActivityResult(
            int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_GOOGLE_PLAY_SERVICES:
                if (resultCode != RESULT_OK) {
                    mOutputText.setText(
                            "This app requires Google Play Services. Please install ");
                } else {
                    getResultsFromApi();
                }
                break;
            case REQUEST_ACCOUNT_PICKER:
                if (resultCode == RESULT_OK && data != null &&
                        data.getExtras() != null) {
                    String accountName =
                            data.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
                    if (accountName != null) {

                        mCredential.setSelectedAccountName(accountName);


                        SharedPreferences settings =
                                getPreferences(Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = settings.edit();
                        editor.putString(PREF_ACCOUNT_NAME, accountName);
                        editor.apply();

                        getResultsFromApi();
                    }
                }
                break;
            case REQUEST_AUTHORIZATION:
                if (resultCode == RESULT_OK) {
                    getResultsFromApi();
                }
                break;


            case CAPTURE_RETURN:
            case GALLERY_RETURN:
                if (resultCode == RESULT_OK) {

                    Log.e("dataVid",data.getData().toString()+"");
                    new UploadVideoAsync(data.getData()).execute();
                }
                break;

        }
    }



/*    public class SaveTokenAsync extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... voids) {
            try {
                token = mCredential.getToken();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (GoogleAuthException e) {
                e.printStackTrace();
            }

            return token;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if (s != null) {
                SharedPreferences settings =
                        getPreferences(Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = settings.edit();

                editor.putString(Constants.TOKEN, token);
                editor.apply();

                Constants.ACCESS_TOKEN = token;
            } else {
                Toast.makeText(MainActivity.this, "Token empty", Toast.LENGTH_SHORT).show();
            }
        }
    }*/


   /* private void initCaptureButtons() {


        btnCaptureVideo = (Button) findViewById(R.id.btnCaptureVideo);
        btnCaptureVideo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                Intent i = new Intent();
                i.setAction("android.media.action.VIDEO_CAPTURE");
                startActivityForResult(i, CAPTURE_RETURN);
            }
        });

        btnSelectFromGallery = (Button) findViewById(R.id.btnSelectFromGallery);
        btnSelectFromGallery.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_PICK);
                intent.setType("video/*");

                List<ResolveInfo> list = getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
                if (list.size() <= 0) {
                    Log.d(TAG, "no video picker intent on this hardware");
                    return;
                }

                startActivityForResult(intent, GALLERY_RETURN);
            }
        });

        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        progressBar.setVisibility(View.GONE);
        btnSelectFromGallery.setEnabled(true);
        btnCaptureVideo.setEnabled(true);
    }*/

    private String uploadYoutube(Uri data) {
        Log.e("VideoId", "" +"uploading video"+data);
        HttpTransport transport = AndroidHttp.newCompatibleTransport();
//        JsonFactory jsonFactory = new AndroidJsonFactory(); // GsonFactory
        JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();

        HttpRequestInitializer initializer = new HttpRequestInitializer() {
            @Override
            public void initialize(HttpRequest request) throws IOException {
                mCredential.initialize(request);
                request.setLoggingEnabled(true);
//                request.setIOExceptionHandler(new HttpBackOffIOExceptionHandler(new ExponentialBackOff()));
            }
        };

        YouTube.Builder youtubeBuilder = new YouTube.Builder(transport, jsonFactory, initializer);
        youtubeBuilder.setApplicationName(getString(R.string.app_name));
//        youtubeBuilder.setYouTubeRequestInitializer(new YouTubeRequestInitializer(API_KEY));
        YouTube youtube = youtubeBuilder.build();

        String PRIVACY_STATUS = "public"; // or public,private
        String PARTS = "snippet,status,contentDetails";

        String videoId = null;
        try {
            Video videoObjectDefiningMetadata = new Video();
            videoObjectDefiningMetadata.setStatus(new VideoStatus().setPrivacyStatus(PRIVACY_STATUS));

            VideoSnippet snippet = new VideoSnippet();
            snippet.setTitle("vidUp.Appentus." + System.currentTimeMillis());
            Log.e("Titile",snippet.getTitle());
            snippet.setDescription("MyDescription");
            snippet.setTags(Arrays.asList(new String[]{"TaG1,TaG2"}));
            videoObjectDefiningMetadata.setSnippet(snippet);

            YouTube.Videos.Insert videoInsert = youtube.videos().insert(
                    PARTS,
                    videoObjectDefiningMetadata,
                    getMediaContent(getFileFromUri(data, Youtube.this)));/*.setOauthToken(token);*/
//                    .setKey(API_KEY);

            MediaHttpUploader uploader = videoInsert.getMediaHttpUploader();
            uploader.setDirectUploadEnabled(false);

            MediaHttpUploaderProgressListener progressListener = new MediaHttpUploaderProgressListener() {
                public void progressChanged(MediaHttpUploader uploader) throws IOException {
                    Log.d(TAG, "progressChanged: " + uploader.getUploadState());
                    switch (uploader.getUploadState()) {
                        case INITIATION_STARTED:
                            Log.e(TAG, "progressChanged:"+"started");
                            break;
                        case INITIATION_COMPLETE:
                            Log.e(TAG, "progressChanged: Completed");
                            break;
                        case MEDIA_IN_PROGRESS:
                            Log.e(TAG, "progressChanged: Inprogress");
                            break;
                        case MEDIA_COMPLETE:
                            Log.e(TAG, "progressChanged: MCompleted");
                        case NOT_STARTED:
                            Log.e(TAG, "progressChanged: upload_not_started"+uploader.toString());
                            break;
                    }
                }
            };
            uploader.setProgressListener(progressListener);

            Log.e(TAG, "Uploading..");
            Video returnedVideo = videoInsert.execute();
            Log.e(TAG, "Video upload completed");
            videoId = returnedVideo.getId();
            Log.e("VideoId", "" +" video"+returnedVideo.getFileDetails());
            Log.e(TAG, String.format("videoId = [%s]", videoId));

        } catch (final GooglePlayServicesAvailabilityIOException availabilityException) {
            Log.e(TAG, "GooglePlayServicesAvailabilityIOException", availabilityException);
        } catch (UserRecoverableAuthIOException userRecoverableException) {
            Log.e(TAG, String.format("UserRecoverableAuthIOException: %s",
                    userRecoverableException.toString()));
            startActivityForResult(userRecoverableException.getIntent(), REQUEST_AUTHORIZATION);
        } catch (IOException e) {
            Log.e(TAG, "IOException", e);
        }

        return videoId;

    }

    public class UploadVideoAsync extends AsyncTask<Uri, Void, String> {

        Uri data;

        ProgressDialog progressDialog;


        public UploadVideoAsync(Uri data) {
            this.data = data;

            Log.e("data",data+"");
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(Youtube.this);
            progressDialog.setMessage("Uploading Video to youtube");
            progressDialog.show();
        }

        @Override
        protected String doInBackground(Uri... voids) {
            Log.i("VideoId", "" + "Do in background");
            String s=uploadYoutube(data);
            Log.e("data",s+"");
            return s;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Toast.makeText(Youtube.this, "VideoId is " + s, Toast.LENGTH_SHORT).show();
            Log.i("VideoId", "" + s);
            progressDialog.dismiss();
        }
    }





    //<--------------------Utils---------------->


    /**
     * Checks whether the device currently has a network connection.
     *
     * @return true if the device has a network connection, false otherwise.
     */
    private boolean isDeviceOnline() {
        ConnectivityManager connMgr =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }

    /**
     * Check that Google Play services APK is installed and up to date.
     *
     * @return true if Google Play Services is available and up to
     * date on this device; false otherwise.
     */
    private boolean isGooglePlayServicesAvailable() {
        GoogleApiAvailability apiAvailability =
                GoogleApiAvailability.getInstance();
        final int connectionStatusCode =
                apiAvailability.isGooglePlayServicesAvailable(this);
        return connectionStatusCode == ConnectionResult.SUCCESS;
    }

    /**
     * Attempt to resolve a missing, out-of-date, invalid or disabled Google
     * Play Services installation via a user dialog, if possible.
     */
    private void acquireGooglePlayServices() {
        GoogleApiAvailability apiAvailability =
                GoogleApiAvailability.getInstance();
        final int connectionStatusCode =
                apiAvailability.isGooglePlayServicesAvailable(this);
        if (apiAvailability.isUserResolvableError(connectionStatusCode)) {
            showGooglePlayServicesAvailabilityErrorDialog(connectionStatusCode);
        }
    }


    /**
     * Display an error dialog showing that Google Play Services is missing
     * or out of date.
     *
     * @param connectionStatusCode code describing the presence (or lack of)
     *                             Google Play Services on this device.
     */
    void showGooglePlayServicesAvailabilityErrorDialog(
            final int connectionStatusCode) {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        Dialog dialog = apiAvailability.getErrorDialog(
                Youtube.this,
                connectionStatusCode,
                REQUEST_GOOGLE_PLAY_SERVICES);
        dialog.show();
    }


    private static File getFileFromUri(Uri uri, Activity activity) {

        try {
            String filePath = null;

            String[] proj = {MediaStore.Video.VideoColumns.DATA};

            Cursor cursor = activity.getContentResolver().query(uri, proj, null, null, null);

            if (cursor.moveToFirst()) {
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Video.VideoColumns.DATA);
                filePath = cursor.getString(column_index);
            }

            cursor.close();

            File file = new File(filePath);
            cursor.close();
            return file;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


    private AbstractInputStreamContent getMediaContent(File file) throws FileNotFoundException {
        InputStreamContent mediaContent = new InputStreamContent(
                "video/*",
                new BufferedInputStream(new FileInputStream(file)));

        mediaContent.setLength(file.length());

        return mediaContent;
    }


    private void initVideoPicker() {

        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_PICK);
        intent.setType("video/*");
        List<ResolveInfo> list = getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        if (list.size() <= 0) {
            Log.e(TAG, "no video picker intent on this hardware");
            Toast.makeText(getApplication(), "No video picker found on device", Toast.LENGTH_SHORT).show();
            return;
        }
        startActivityForResult(intent, GALLERY_RETURN);

    }











    public void getSaveData() {
        // put value in preference on button click
        vidpath = sharedPreferences.getString("vidPath", "");

        filePath=vidpath;

    }












}