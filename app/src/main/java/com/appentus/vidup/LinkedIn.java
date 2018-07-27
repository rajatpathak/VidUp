package com.appentus.vidup;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.linkedin.platform.APIHelper;
import com.linkedin.platform.errors.LIApiError;
import com.linkedin.platform.listeners.ApiListener;

import org.json.JSONException;
import org.json.JSONObject;

public class LinkedIn extends AppCompatActivity {

    private ProgressDialog progress;
    private TextView user_name, user_email;
    String url = "https://api.linkedin.com/v1/people/~:(id,first-name,last-name,picture-urls::(original),industry,positions,email-address)";

    public static final String LINKED_IN_PEOPLE_PROFILE = "https://api.linkedin.com/v1/people/~/shares?format=json";
    private APIHelper apiHelper;
    private String comment="",description="",title="";
    private String postUrl="https://www.linkedin.com";
    private Button share;
    private EditText etTitle, etDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_linked_in);
        user_name = (TextView) findViewById(R.id.name);
        user_name = (TextView) findViewById(R.id.name);
        etTitle= (EditText) findViewById(R.id.etTitle);
        etDescription = (EditText) findViewById(R.id.etDescription);
        share = (Button) findViewById(R.id.shareButton);


        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            if (extras.containsKey("commnet")) {
                comment = extras.getString("commnet", "");
                // TODO: Do something with the value of isNew.
            }

        }
        linkededinApiHelper();

        shareImageOnLinkedIn(title,description);
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                description=etDescription.getText().toString();
                title=etTitle.getText().toString();
                shareImageOnLinkedIn(title,description);
            }
        });
    }
    private void linkededinApiHelper() {
        apiHelper = APIHelper.getInstance(getApplicationContext());
        apiHelper.getRequest(LinkedIn.this, url, new ApiListener() {
            @Override
            public void onApiSuccess(com.linkedin.platform.listeners.ApiResponse apiResponse) {
                showResult(apiResponse.getResponseDataAsJson());
                Log.e("JSON", apiResponse.toString());
                try {
                    Log.e("Profile emailAddress", "" + apiResponse.getResponseDataAsJson().get("emailAddress").toString());
                    Log.e("Profile formattedName", "" + apiResponse.getResponseDataAsJson().get("formattedName").toString());
                    user_email.setText(apiResponse.getResponseDataAsJson().get("emailAddress").toString());
                    user_name.setText(apiResponse.getResponseDataAsJson().get("name").toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onApiError(LIApiError error) {

            }
        });
    }


    public void showResult(JSONObject response) {

        try {

            user_email.setText(response.get("emailAddress").toString());
            user_name.setText(response.get("formattedName").toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void shareImageOnLinkedIn(String title,String etDescription) {
        String shareJsonText = "{ \n" +
                "   \"comment\":\"" + comment + "\"," +
                "   \"visibility\":{ " +
                "      \"code\":\"anyone\"" +
                "   }," +
                "   \"content\":{ " +
                "      \"title\":\""+title+"\"," +
                "      \"description\":\""+etDescription+"\"," +
                "      \"submitted-url\":\""+postUrl+"\"," +
                "      \"submitted-image-url\":\""+comment+"\"" +
                "   }" +
                "}";

                // Call the APIHealper.getInstance method and pass the current context.
        apiHelper = APIHelper.getInstance(getApplicationContext());
        apiHelper.postRequest(LinkedIn.this, LINKED_IN_PEOPLE_PROFILE,shareJsonText, new ApiListener()
                // call the apiHelper.postRequest with argument(Current context,url and content)
    {
                    @Override
                    public void onApiSuccess(com.linkedin.platform.listeners.ApiResponse apiResponse) {
                        Log.e("Response", apiResponse.toString());
                        Toast.makeText(getApplicationContext(), "Shared Sucessfully", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onApiError(LIApiError error) {
                        Log.e("Response", error.toString());
                        Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
                    }
                });
    }

}