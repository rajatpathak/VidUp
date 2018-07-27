package com.appentus.vidup.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.appentus.vidup.FAQ;
import com.appentus.vidup.FacebookShare;
import com.appentus.vidup.LinkedIn;
import com.appentus.vidup.R;
import com.appentus.vidup.Youtube;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.linkedin.platform.LISessionManager;
import com.linkedin.platform.errors.LIAuthError;
import com.linkedin.platform.listeners.AuthListener;
import com.linkedin.platform.utils.Scope;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;

public class Setting extends AppCompatActivity implements View.OnClickListener {
    private static final int RC_SIGN_IN = 007;
    CallbackManager callbackManager;
    LoginButton loginButton;
    FacebookCallback<LoginResult> callback;
    AccessTokenTracker accessTokenTracker;
    ProfileTracker profileTracker;
    //replace package string with your package string
    public static final String PACKAGE = "com.appentus.vidup";
    private static final String EMAIL = "email";
    SharedPreferences sharedPreferences;

    LinearLayout fblayout,linkedIn,loginButtonYoutube;
    private String email, name;
    private Profile profile;
    private ImageView help;
    private String id;
    private Toolbar toolbar;
    private TextView facebookStatus;
    private GoogleSignInClient mGoogleSignInClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        fblayout= (LinearLayout) findViewById(R.id.fblayout);
        help= (ImageView) findViewById(R.id.help);
        linkedIn= (LinearLayout) findViewById(R.id.linkedIn);
        loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButtonYoutube= (LinearLayout) findViewById(R.id.youtube);
        facebookStatus = (TextView) findViewById(R.id.facebookStatus);
        ImageView back = (ImageView) findViewById(R.id.backTop);


        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), FAQ.class));
            }
        });
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestProfile()
                .requestId()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);



// Set the dimensions of the sign-in button.

        loginButtonYoutube.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
           signIn();

            }
        });

        linkedIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        loginButton.setReadPermissions(Arrays.asList(EMAIL));
        callbackManager = CallbackManager.Factory.create();
        FacebookSdk.sdkInitialize(getApplicationContext());
        profile = Profile.getCurrentProfile().getCurrentProfile();
        sharedPreferences= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        setLogStatus(profile);
        updateWithToken();
        getSaveData();
        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
            }
        };

        fblayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (profile != null) {

                    Log.e("IDDDDD", ""+profile.getName()); // user has logged in
                    Intent i=new Intent(getApplicationContext(),FacebookShare.class);
                    i.putExtra("name",name);
                    i.putExtra("id",id);
                    i.putExtra("email",email);
                    startActivity(i);

                } else if (profile==null){

                    loginButton.performLongClick();
                    // user has not logged in
                    Log.e("Click", "null"); // user has logged in
                }
            }
        });
        profileTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile) {


            }
        };

        accessTokenTracker.startTracking();
        profileTracker.startTracking();

        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        Profile profile = Profile.getCurrentProfile();
                        GraphRequest request = GraphRequest.newMeRequest(
                                loginResult.getAccessToken(),
                                new GraphRequest.GraphJSONObjectCallback() {
                                    @Override
                                    public void onCompleted(JSONObject object, GraphResponse response) {
                                        Log.v("LoginActivity", response.toString());

                                        getFacebookData(object);
                                        //String birthday = object.getString("birthday"); // 01/31/1980 format
                                    }
                                });
                        Bundle parameters = new Bundle();
                        parameters.putString("fields", "id,name,email,gender,birthday");
                        request.setParameters(parameters);
                        request.executeAsync();
                    }

                    @Override
                    public void onCancel() {
                        // App code
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        // App code
                    }
                });





    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void setLogStatus(Profile profile) {
        if (profile!=null){

            facebookStatus.setText("Logged In");

        }else{

            facebookStatus.setText("Not logged in");

        }
    }

    private void updateWithToken() {
        Profile profile = Profile.getCurrentProfile().getCurrentProfile();
        if (profile != null) {
            loginButton.setVisibility(View.GONE);
            Log.e("IDDDDD", ""+profile.getName()); // user has logged in

        } else {
            loginButton.setVisibility(View.VISIBLE);
            // user has not logged in
            Log.e("IDDDDD", "null"); // user has logged in
        }
    }

    private Bundle getFacebookData(JSONObject object) {
        try {
            Bundle bundle = new Bundle();

             id = object.getString("id");
            Log.e("IDDDDD", object.getString("id"));
            bundle.putString("idFacebook", id);
            try {
                URL profile_pic = new URL("https://graph.facebook.com/" + id + "/picture?width=200&height=150");
                Log.e("profile_pic", profile_pic + "");
                bundle.putString("profile_picURI", profile_pic.toString());

            } catch (MalformedURLException e) {
                e.printStackTrace();
                return null;
            }

            // Application code
            try {
                email = object.getString("email");
                Log.e("onSuccesssss: ",email);
                bundle.putString("email", email);
                name = object.getString("name");
                Log.e("name: ", name);
                bundle.putString("name", name);


                saveData(email,name,id);


                Toast.makeText(getApplicationContext(), "Login Success with facebook", Toast.LENGTH_SHORT).show();
                Intent i=new Intent(getApplicationContext(),FacebookShare.class);
                i.putExtra("id",id);
                i.putExtra("name",name);
                i.putExtra("email",email);
                startActivity(i);
            } catch (JSONException e) {
                e.printStackTrace();
            }


            return bundle;
        }
        catch(JSONException e) {
            Log.e("Json Error",e.getMessage());
        }
        return null;
    }

    public void saveData(String email, String name, String id) {
        // put value in preference on button click
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("username", email);
        editor.putString("name", name);
        editor.putString("id", id);
        editor.commit();
        Log.e("Savedata",email+name);
    }

    public void getSaveData() {
        // put value in preference on button click
        email = sharedPreferences.getString("username", "");
        name= sharedPreferences.getString("name", "");
        id= sharedPreferences.getString("id", "");
        Log.e("get Savedata",email+name);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);

        super.onActivityResult(requestCode, resultCode, data);
        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }

        LISessionManager.getInstance(getApplicationContext())
                .onActivityResult(this,
                        requestCode, resultCode, data);
        Intent intent = new Intent(Setting.this, LinkedIn.class);
        startActivity(intent);
    }

    private void handleSignInResult(Task<GoogleSignInAccount> task) {
        try {
            GoogleSignInAccount account = task.getResult(ApiException.class);

            Log.e("GoogleEmail", "" + task.getResult().getEmail());
            Log.w("GoogleName", "" + task.getResult().getDisplayName());
            startActivity(new Intent(getApplicationContext(), Youtube.class));

//            updateUI(account);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("Setting", "signInResult:failed code=" + e.getStatusCode());
//            updateUI(null);
        }
    }

    public void login(){
        LISessionManager.getInstance(getApplicationContext())
                .init(this, buildScope(), new AuthListener() {
                    @Override
                    public void onAuthSuccess() {

                        Toast.makeText(getApplicationContext(), "success" ,Toast.LENGTH_LONG).show();
                        Log.e("LinkedIn JSONData",LISessionManager
                                .getInstance(getApplicationContext())
                                .getSession().getAccessToken().toString());
                    }

                    @Override
                    public void onAuthError(LIAuthError error) {

                        Toast.makeText(getApplicationContext(), "failed "
                                        + error.toString(),
                                Toast.LENGTH_LONG).show();
                    }
                }, true);
    }
    // set the permission to retrieve basic
//    information of User's linkedIn account
    private static Scope buildScope() {
        return Scope.build(Scope.R_BASICPROFILE, Scope.R_EMAILADDRESS,Scope.W_SHARE);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    @Override
    public void onClick(View v) {

    }
}
