package com.appentus.vidup;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareVideo;
import com.facebook.share.model.ShareVideoContent;
import com.facebook.share.widget.ShareDialog;

import java.io.File;

public class FacebookShare extends AppCompatActivity {
    String name, id,email;
    private Uri videoFileUri;
    private String filePath;
    private static final String TAG = ShareVideoFragment.class.getName();

    private CallbackManager callbackManager;
    Context facebookShare;
    ShareDialog shareDialog;
    private String vidpath;

    private SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facebook_share);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            if (extras.containsKey("facebookFile")) {
                vidpath = extras.getString("facebookFile", "");
                // TODO: Do something with the value of isNew.

            }

        }

        File file = new File(getApplication().getFilesDir() + "/" + vidpath);
        Uri videoFileUri = Uri.fromFile(file);
        Log.e("Succesfully posted",videoFileUri+"");
        ImageView back = (ImageView) findViewById(R.id.backTop);


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        id=intent.getStringExtra("id");
        name=intent.getStringExtra("name");
        email=intent.getStringExtra("email");
        TextView user_name = (TextView) findViewById(R.id.UserName);
        TextView removeAccount = (TextView) findViewById(R.id.removeAccount);
        TextView user_email = (TextView) findViewById(R.id.email);
        user_name.setText(name);
        user_email.setText(email);

        facebookShare();
        removeAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });
//        addVideoFragment();
    }


    private void addVideoFragment() {
        ShareVideoFragment shareVideoFragment = new ShareVideoFragment();

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.container,shareVideoFragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }

    public void facebookShare(){

        FacebookSdk.sdkInitialize(getApplicationContext());
        // Create a callbackManager to handle the login responses.
        callbackManager = CallbackManager.Factory.create();
        shareDialog = new ShareDialog(FacebookShare.this);
        sharedPreferences= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        // this part is optional
        shareDialog.registerCallback(callbackManager, callback);

        Button shareButton = (Button)findViewById(R.id.shareButton);
        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSaveData();
                Log.e("vidPath",vidpath);
                Intent intent = new Intent();
                intent.setType("video/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Complete action using"), 1);
            }
        });
    }
    private FacebookCallback<Sharer.Result> callback = new FacebookCallback<Sharer.Result>() {
        @Override
        public void onSuccess(Sharer.Result result) {
            Log.e(TAG, "Succesfully posted");
            // Write some code to do some operations when you shared content successfully.
        }

        @Override
        public void onCancel() {
            Log.e(TAG, "Cancel occured");
            // Write some code to do some operations when you cancel sharing content.
        }

        @Override
        public void onError(FacebookException error) {
            Log.e(TAG, error.getMessage());
            // Write some code to do some operations when some error occurs while sharing content.
        }
    };

    public void getSaveData() {
        // put value in preference on button click
        vidpath = sharedPreferences.getString("vidPath", "");
        filePath=vidpath;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != RESULT_OK)
            return;

        if (requestCode == 1) {
//            Uri videoFileUri = Uri.fromFile(new File(vidpath));  //data.getData();

            ShareVideo video = new ShareVideo.Builder()
                    .setLocalUrl(videoFileUri)
                    .build();
            ShareVideoContent content = new ShareVideoContent.Builder()
                    .setVideo(video).setContentDescription("VidUp App").setContentTitle("Test VidUp")
                    .build();

            ShareDialog shareDialog = new ShareDialog(FacebookShare.this);
            shareDialog.show(content, ShareDialog.Mode.AUTOMATIC);
            Log.e("vid share1",video.toString());




        } else {
            // Call callbackManager.onActivityResult to pass login result to the LoginManager via callbackManager.
            callbackManager.onActivityResult(requestCode, resultCode, data);
            Log.e("vid share","errorrr");
        }
}

    public void openDialog(){

        final Dialog dialog = new Dialog(FacebookShare.this);
        dialog.setContentView(R.layout.custom_dialog_warring);
        dialog.setCancelable(false);
        TextView dialogButtonCancel = (TextView) dialog.findViewById(R.id.dialogCancel);
        TextView dialogButtonOk= (TextView) dialog.findViewById(R.id.dialogOk);
        TextView tvTitle= (TextView) dialog.findViewById(R.id.tvTitle);

        tvTitle.setText("");
        tvTitle.setText("Log out");

        // if button is clicked, close the custom dialog
        dialogButtonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                LoginManager.getInstance().logOut();
                  }
        });

        // if button is clicked, close the custom dialog
        dialogButtonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

            }
        });

        dialog.show();
    }
}

