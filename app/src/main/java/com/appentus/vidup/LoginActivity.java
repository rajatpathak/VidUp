package com.appentus.vidup;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import com.appentus.vidup.apis.ApiClient;
import com.appentus.vidup.apis.ApiInterface;
import com.appentus.vidup.apis.Models.Login;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {


    private static final String BASE_URL = "http://bizzcam.com/";
    ApiInterface apiService;
    private static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;
    private String email="",password="";
    EditText etEmail,etPassword;
    private ImageView passwordView,passwordHide;
    private int count;
    private ProgressBar pb_loader;
    private SharedPreferences sharedPreferences;
    private String username="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        checkAndRequestPermissions();
        ButterKnife.bind(this);
        LinearLayout focuslayout = (LinearLayout) findViewById(R.id.focus);
        focuslayout.requestFocus();
        etEmail=(EditText)findViewById(R.id.etEmail);
        passwordView=(ImageView)findViewById(R.id.passwordView);
        passwordHide=(ImageView)findViewById(R.id.passwordHide);
        pb_loader=(ProgressBar)findViewById(R.id.pb_loader);
        etPassword=(EditText)findViewById(R.id.etPassword);
        sharedPreferences= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        getSaveData();
        etEmail.setText(username);
        etPassword.setText(password);
        passwordView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    etPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    etPassword.setSelection(etPassword.getText().length());
                    passwordView.setVisibility(View.GONE);
                    passwordHide.setVisibility(View.VISIBLE);
            }
        });
        passwordHide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    etPassword.setTransformationMethod(new PasswordTransformationMethod());
                    etPassword.setSelection(etPassword.getText().length());
                    passwordHide.setVisibility(View.GONE);
                    passwordView.setVisibility(View.VISIBLE);
            }
        });
        apiService = ApiClient.getClient(getApplicationContext(),BASE_URL).create(ApiInterface.class);
    }

    @OnClick({R.id.tvsignup,R.id.btnsignin})
    public void OnViewClicked(View view){
        switch (view.getId())
        {
            case R.id.btnsignin:
                email=etEmail.getText().toString();
                password=etPassword.getText().toString();
                saveData(email,password);
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
              /**

                pb_loader.setVisibility(View.VISIBLE);

                email=etEmail.getText().toString();
                password=etPassword.getText().toString();
                String parm=email+password;
//                Call<Login> call = apiService.getUser(email,password);
                Call<Login> call = apiService.getUser(email,password);
                Log.e("email/pasword:",email+"/"+password);
                call.enqueue(new Callback<Login>() {
                    @Override
                    public void onResponse(Call<Login> call, Response<Login> response) {
                        try {
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            pb_loader.setVisibility(View.GONE);
                            if(response.body().getUserId()  !=null) {
                                Log.e("onResponse", "" + response.body().getEmail() + "  " + response.body().getUserId());
                            }
                        } catch (Exception e) {
                            Log.e("onResponse", "There is an error"+e.getMessage());
                            e.printStackTrace();
                            pb_loader.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onFailure(Call<Login> call, Throwable t) {
                        pb_loader.setVisibility(View.GONE);
                    }
                });
               **/
                break;
            case R.id.tvsignup:
                finish();
                startActivity(new Intent(LoginActivity.this,SignUp.class));
                break;
        }
    }
    public void saveData(String username,String password) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("logUsername", username);
        editor.putString("logPassword", password);
        editor.commit();
        Log.e("Savedata",username+" / " + password);
        pb_loader.setVisibility(View.GONE);
    }

    public void getSaveData() {
        // put value in preference on button click
        username = sharedPreferences.getString("logUsername", "");
        password= sharedPreferences.getString("logPassword", "");

    }
    private boolean checkAndRequestPermissions() {
        int ACCESS_CAMERA = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        int ACCESS_AUDIO = ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO);
        int SOTRAGE = ContextCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE);
        List<String> listPermissionsNeeded = new ArrayList<>();

        if (ACCESS_CAMERA != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.CAMERA);
        }

        if (ACCESS_AUDIO != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.RECORD_AUDIO);
        }
        if (SOTRAGE != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }

        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), REQUEST_ID_MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;
    }
}
