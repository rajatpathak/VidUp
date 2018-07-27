package com.appentus.vidup;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignUp extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.tvsignin,R.id.btnsignup})
    public void OnViewClicked(View view){
        switch (view.getId())
        {
            case R.id.btnsignup:
                finish();
                startActivity(new Intent(this,LoginActivity.class));
                break;
            case R.id.tvsignin:
                finish();
                startActivity(new Intent(this,LoginActivity.class));
                break;
        }
    }


}
