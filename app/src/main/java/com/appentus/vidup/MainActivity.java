package com.appentus.vidup;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.hardware.Camera;
import android.media.CamcorderProfile;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.PowerManager;
import android.preference.PreferenceManager;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Base64;
import android.util.Base64OutputStream;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.appentus.vidup.Activities.Setting;
import com.appentus.vidup.Activities.VideoViewActivity;
import com.appentus.vidup.apis.ApiClient;
import com.appentus.vidup.apis.ApiInterface;
import com.appentus.vidup.apis.VideoFile;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends Activity implements SurfaceHolder.Callback {
    String encodedString = null;;
    private SurfaceHolder surfaceHolder;
    private SurfaceView surfaceView;
    public MediaRecorder mrec = new MediaRecorder();
    private Button startRecording = null;
    private Button stop = null;
    File video;
    private Camera mCamera;

    @BindView(R.id.socialLayout)
    LinearLayout linearLayout;
    @BindView(R.id.socialLayout2)
    LinearLayout linearLayout2;
    LinearLayout stopRec;

    @BindView(R.id.ibRecording)
    LinearLayout ibRecording;
    @BindView(R.id.ibShare)
    ImageView ibShare;
    @BindView(R.id.ibFbShare)
    ImageView ibFbShare;

    @BindView(R.id.ibMail)
    ImageView ibMail;



    ImageView youtubeIm,linkedInIm;


    ImageView flashLight,help,helpLand,switchCamera,switchCameraLand,flashLightLand;
    ImageView imPlay,imPlayland;

    SharedPreferences sharedPreferences;

    int count=0;
    MediaRecorder recorder;
    SurfaceHolder holder;
   public static boolean isRecording = false;
     ProgressBar mProgress,mProgressLand;

    int pStatus = 0;
    int timeStatus = 0;
    private Handler handler = new Handler();
    TextView tv,tvLand;
    private PowerManager.WakeLock mWakeLock;
    private TextView btnsocialConntect;
    private ApiInterface apiService;

    Camera.CameraInfo cameraInfo;
    FrameLayout land,portrate;
    private String path;
    static  String filePath;
    public FrameLayout rootLayout;
    private int flashCode;
    private int vidTest;
    private File file;
    private String vidpath;
    private int cameraCode;
    private ProgressDialog progress;
    private LinearLayout progressMain;
    private String operationCode="";
    private int rotate;
    private int isrec;
    private ImageView logoutVerti,logoutHori;

    private int countBt;
    private EditText etComment;
    private CardView cardProgress;
    private boolean isStarted=false;
    private Dialog mdialog;
    String dialogVisiblity="";
    public  static boolean dialogCheck=false;
    public  static Uri filePathUri;

    @TargetApi(Build.VERSION_CODES.M)
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
      //        initRecorder();


        Log.e("Lyf Cycle","Oncreate");
        setContentView(R.layout.activity_main);

        String checkCode;
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        /**
        if (extras != null) {
            if (extras.containsKey("checkCode")) {
               checkCode = extras.getString("checkCode", "");
                if (checkCode.equals("1")){
                    dialogVisiblity="visible";
                    Log.e("dialog call from","bundle");
                    checkCode="not";
                    alertPopup("bundle");
                }
                // TODO: Do something with the value of isNew.
            }

        }

        **/
        sharedPreferences= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        progress = new ProgressDialog(this);
        progress.setTitle("Loading");
        progress.setMessage("Wait while loading...");
        progress.setCancelable(false); // disable dismiss by tapping outside of the dialog

        Resources res = getResources();
        Drawable drawable = res.getDrawable(R.drawable.rind);
        mProgress = (ProgressBar) findViewById(R.id.circularProgressbar);

        mProgressLand = (ProgressBar) findViewById(R.id.circularProgressbarLand);
        progressMain= (LinearLayout) findViewById(R.id.progressMain);
        mProgress.setProgress(0);   // Main Progress
        mProgress.setSecondaryProgress(59); // Secondary Progress
        mProgress.setMax(59); // Maximum Progress
        mProgress.setProgressDrawable(drawable);
        mProgressLand.setProgress(0);   // Main Progress
        mProgressLand.setSecondaryProgress(59); // Secondary Progress
        mProgressLand.setMax(59); // Maximum Progress
        mProgressLand.setProgressDrawable(drawable);

        apiService = ApiClient.getClient(getApplicationContext(),"http://newteethcam.azurewebsites.net/").create(ApiInterface.class);
        surfaceView = (SurfaceView) findViewById(R.id.surface_camera);
        land= (FrameLayout) findViewById(R.id.landscapView);
        portrate= (FrameLayout) findViewById(R.id.portratView);

        viewInitializing();

        updateLayout(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE);


        mCamera = Camera.open(0);
        setCamera(mCamera);
        surfaceHolder = surfaceView.getHolder();
        surfaceHolder.addCallback(this);
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

        logoutHori.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });
        logoutVerti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });
        help.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.M)
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {

         startActivity(new Intent(getApplicationContext(),FAQ.class));
            }
        });

  helpLand.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.M)
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(),FAQ.class));
            }
        });



        youtubeIm.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.M)
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {

                Intent i=new Intent(getApplicationContext(),Youtube.class);
                i.putExtra("uri",filePathUri);
            }
        });

        linkedInIm.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.M)
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),LinkedIn.class);
                i.putExtra("commnet",etComment.getText()+"");
                startActivity(i);
                }
        });


        flashLight.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.M)
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                if (count==0){
                    flashLight(0);
                    count=1;
                }
                else if (count==1){
                    flashLight(1);
                    count=0;
                }
            }
        });
        flashLightLand.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.M)
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {

                if (flashCode==0){
                if (count==0){
                    flashLight(0);
                    count=1;
                }
                else if (count==1){
                    flashLight(1);
                    count=0;
                }
                }
                else if (flashCode==1){
                    Snackbar.make(rootLayout, "Cant change camera setting while isRecording", Snackbar.LENGTH_SHORT).show();
                }
            }
        });


        switchCamera.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.M)
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {


              switchCameraFun();
            }
        });

        switchCameraLand.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.M)
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
              switchCameraFun();
            }
        });



        imPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSaveData();
//                alertPopup();
                if (operationCode.equals("")) {


                    final Dialog dialog = new Dialog(MainActivity.this);
                    dialog.setContentView(R.layout.custom_dialog_simple);
                    TextView dialogButton = (TextView) dialog.findViewById(R.id.dialogButtonOK);
                    // if button is clicked, close the custom dialog
                    dialogButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                            Toast.makeText(getApplicationContext(), "Dismissed..!!", Toast.LENGTH_SHORT).show();
                        }
                    });
                    dialog.show();

                }


            }
        });




        //landscape video isRecording button//
        imPlayland.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialogCheck=true;
                if (countBt==0) {
                    countBt++;
                    try {
                        if (mCamera != null) {
                            releaseAllMediaRecorder();
                            releaseCameraHardware();
                        }
                        startRecording();
                        runTimer();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    imPlay.setVisibility(View.GONE);
                    switchCamera.setVisibility(View.GONE);
                    tvLand.setVisibility(View.VISIBLE);
                    stopRec.setVisibility(View.VISIBLE);
                    mProgressLand.setVisibility(View.VISIBLE);
                }
                else{
                    imPlayland.setEnabled(false);
                }

            }
        });


        stopRec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                isStarted = true;
                cardProgress.setVisibility(View.VISIBLE);
                countBt=0;

                pStatus=0;

                resetRecorderTime();

                alertPopup("stop");
                dialogVisiblity="visible";
                saveData(filePath);
                filePathUri= Uri.parse(new File(file.getAbsolutePath()).toString());
                Log.e("FIleURI", "" +filePathUri);
//                    encodeTobase64(file);
                progress.dismiss();
                progressMain.setVisibility(View.GONE);
                cardProgress.setVisibility(View.GONE);
            }
        });


   tvLand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countBt=0;
                resetRecorderTime();
                //                    encodeTobase64(file);
                saveData(filePath);
                progress.dismiss();
                progressMain.setVisibility(View.GONE);
                alertPopup("tvLand");


            }
        });






        ButterKnife.bind(this);


        ibShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (count==0){
                    linearLayout2.setVisibility(View.VISIBLE);
//                    sharePannel();
                    count=1;
                }
                else if(count==1){
                    linearLayout2.setVisibility(View.GONE);
                    count=0;
                }

            }
        });

        ibFbShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              Intent i=new Intent(getApplicationContext(),FacebookShare.class);
              i.putExtra("facebookFile",filePath);
              startActivity(i);
            }
        });


        ibMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                Snackbar.make(rootLayout, "We are coming soon...", Snackbar.LENGTH_SHORT).show();

            }
        });



        btnsocialConntect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              Intent i=new Intent(getApplicationContext(),Setting.class);


              startActivity(i);
            }
        });

    }

    private void updateLayout(boolean b) {
        if (b) {

            Log.e("CheckMode","LandScape");
            pStatus=0;
            land.setVisibility(View.VISIBLE);
            portrate.setVisibility(View.GONE);
                Log.e("startREc","start");
                releaseAllMediaRecorder();
                releaseCameraHardware();

        } else {
            Log.e("CheckMode","Portrate "+dialogCheck);

            land.setVisibility(View.GONE);
            portrate.setVisibility(View.VISIBLE);
            if (isRecording){
                progressMain.setVisibility(View.VISIBLE);
                resetRecorderTime();
                progressMain.setVisibility(View.VISIBLE);
                resetRecorderTime();
                //                    encodeTobase64(file);
                saveData(filePath);
                getSaveData();
                Log.e("getPopData",""+vidpath);
                progress.dismiss();
                progressMain.setVisibility(View.GONE);
//                    alertPopup();
                Log.e("popshow","direct");

                if (dialogCheck){
                    if (!MainActivity.this.isFinishing()){

                        uiHandler();
                    }

                    Log.e("popshow","ui");
                } else{
                    mProgress.setVisibility(View.GONE);
                }
            }
        }
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

           outState.putBoolean("isRecording",true);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
           isRecording=savedInstanceState.getBoolean("isRecording");
    }

    private void resetRecorderTime() {

        tv.setVisibility(View.GONE);
        tvLand.setVisibility(View.GONE);
        imPlay.setVisibility(View.VISIBLE);
        imPlayland.setVisibility(View.VISIBLE);
        stopRec.setVisibility(View.INVISIBLE);
        mProgress.setVisibility(View.GONE);
        mProgressLand.setVisibility(View.GONE);
        tv.setText("00:00");
        tvLand.setText("00:00");
        pStatus=0;
        releaseAllMediaRecorder();
        releaseCameraHardware();
        flashCode=0;
    }



    private void viewInitializing() {
        btnsocialConntect = (TextView) findViewById(R.id.btnsocialConntect);
        tv = (TextView) findViewById(R.id.tv);
        tvLand= (TextView) findViewById(R.id.tvLand);
        rootLayout= (FrameLayout) findViewById(R.id.rootLayout);
        imPlay= (ImageView) findViewById(R.id.imPlay);
        imPlayland= (ImageView) findViewById(R.id.imPlayLand);
        youtubeIm= (ImageView) findViewById(R.id.youtubeIm);
        linkedInIm= (ImageView) findViewById(R.id.linkedInIm);
        ibFbShare= (ImageView) findViewById(R.id.ibFbShare);
        stopRec= (LinearLayout) findViewById(R.id.stopRec);
        ibMail= (ImageView) findViewById(R.id.ibMail);
        flashLight= (ImageView) findViewById(R.id.flashLight);
        flashLightLand= (ImageView) findViewById(R.id.flashLightLand);
        switchCamera= (ImageView) findViewById(R.id.switchCamera);
        switchCameraLand= (ImageView) findViewById(R.id.switchCameraLand);
        help= (ImageView) findViewById(R.id.help);
        helpLand= (ImageView) findViewById(R.id.helpLand);
        logoutVerti= (ImageView) findViewById(R.id.logoutVerti);
        logoutHori= (ImageView) findViewById(R.id.logoutHori);
        etComment= (EditText) findViewById(R.id.etCommnet);
        cardProgress= (CardView) findViewById(R.id.cardProgress);
//        surfaceView = (SurfaceView) findViewById(R.id.surface_camera);
    }

    private void switchCameraFun() {
        if (mCamera!=null){
            releaseCameraHardware();
        }
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            if (count == 0) {
                mCamera = Camera.open(1);
                setCamera(mCamera);
                count = 1;
                cameraCode = 1;
            } else if (count == 1) {
                mCamera = Camera.open(0);
                setCamera(mCamera);
                count = 0;
                cameraCode = 0;
            }
        }
        else  if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){

            if (count == 0) {
                mCamera = Camera.open(1);
                setCamera(mCamera);
                count = 1;
                cameraCode = 1;
            } else if (count == 1) {
                mCamera = Camera.open(0);
                setCamera(mCamera);
                count = 0;
                cameraCode = 0;
            }

        }
        try {
            mCamera.setPreviewDisplay(surfaceHolder);
        } catch (IOException e) {
            e.printStackTrace();
        }
        mCamera.startPreview();

    }

    private void runTimer() {

        new Thread(new Runnable() {

            @Override
            public void run() {

                // TODO Auto-generated method stub
                while (pStatus <= 59) {
                    if (isStarted){

                        break;
                    }
                    pStatus += 1;
                    handler.post(new Runnable() {

                        @Override
                        public void run() {


                            // TODO Auto-generated method stub
                            mProgress.setProgress(pStatus);
                            mProgressLand.setProgress(pStatus);
                            if (pStatus<=9){
                                tv.setText("00:0"+pStatus);
                               tvLand.setText("00:0"+pStatus);

                            }
                            else {
                                tv.setText("00:" + pStatus);
                                tvLand.setText("00:" + pStatus);

                            }
                            Log.e("AutoStop",pStatus+"");
                            if (pStatus==59&&dialogCheck){
                                autoStop(pStatus);
                                Log.e("AutoStop",pStatus+""+dialogCheck);
                                handler.removeCallbacks(Thread.currentThread());
                            }
                        }
                    });
                    try {
                        // Sleep for 100 milliseconds.
                        // Just to display the progress slowly
                        Thread.sleep(1000);
                        //thread will take approx 1 seconds to finish
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    if (pStatus==59){
                        break;
                    }
                    }
                Log.e("AutoStop","handler check");
            }
        }).start();


    }

    private void autoStop(int pStatus) {
        countBt=0;
        resetRecorderTime();

//            encodeTobase64(file);
        saveData(filePath);
        progress.dismiss();
        progressMain.setVisibility(View.GONE);
        Log.e("dialog call from","autostop");

        alertPopup("autoStop");


        dialogVisiblity="visible";

    }


    private void uiHandler() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                while (timeStatus < 5) {
                    timeStatus += 1;
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            // TODO Auto-generated method stub
                            if (timeStatus==3){
                                Log.e("dialog Callfrom","UiHandler");
                                alertPopup("ui handler");
                                handler.removeCallbacks(Thread.currentThread());
                                dialogVisiblity="visible";
                                Log.e("popshow","directUI");
                            }
                        }
                    });
                    try {
                        // Sleep for 100 milliseconds.
                        // Just to display the progress slowly
                        Thread.sleep(1000); //thread will take approx 1 seconds to finish
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    private void updateLayout12(int pStatus) {
        tvLand.setText(pStatus+"");
    }

    private void alertPopup(String s) {

        mdialog = new Dialog(MainActivity.this);
        mdialog .setContentView(R.layout.custom_dialog_layout);
        TextView dialogButtonCancel = (TextView) mdialog .findViewById(R.id.dialogButtonCancel);
         ImageView dialogButtonUpload = (ImageView) mdialog .findViewById(R.id.dialogButtonUpload);
         ImageView dialogButtonPlay = (ImageView) mdialog .findViewById(R.id.dialogButtonPlay);
        mdialog .setCancelable(false);

        Log.e("Showing from",s);

         // if button is clicked, close the custom dialog
        dialogButtonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mdialog .dismiss();
                dialogVisiblity="invisible";
                final Dialog dialog = new Dialog(MainActivity.this);
                dialog.setContentView(R.layout.custom_dialog_warring);
                dialog.setCancelable(false);
                TextView dialogButtonCancel = (TextView) dialog.findViewById(R.id.dialogCancel);
                TextView dialogButtonOk= (TextView) dialog.findViewById(R.id.dialogOk);

                // if button is clicked, close the custom dialog
                dialogButtonOk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        dialogCheck=false;
                        isRecording=false;
                        getSaveData();
                        File file=new File(filePath);

                        boolean deleted = file.delete();
                        if (deleted){
                            dialogVisiblity="invisible";
                            Toast.makeText(getApplicationContext(),"Deleted..!!",Toast.LENGTH_SHORT).show();
                            mProgressLand.setVisibility(View.GONE);
                            mProgress.setVisibility(View.GONE);
                            mdialog.dismiss();
                            saveData("");
                        }
                    }
                });

                // if button is clicked, close the custom dialog
                dialogButtonCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        dialogVisiblity="visible";
                        mdialog.show();
                    }
                });

                dialog.show();

            }
        });

        // if button is clicked, close the custom dialog
        dialogButtonPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSaveData();
                mdialog .dismiss();
                Toast.makeText(getApplicationContext(),"Play..!!",Toast.LENGTH_SHORT).show();
                Intent i=new Intent(MainActivity.this,VideoViewActivity.class);
                i.putExtra("path",vidpath);
                Log.e("getPopData",""+vidpath);
                i.putExtra("checkCode","1");
                startActivity(i);
            }
        });
        if (!mdialog.isShowing()){

            mdialog .show();
        }

}


    private void recordingTimeApiCall(final String fileExt, final String appKey, final String  file) {

        Call<VideoFile> call = apiService.uploadvideo(fileExt,appKey,file);
        call.enqueue(new Callback<VideoFile>() {
            @Override
            public void onResponse(Call<VideoFile> call, Response<VideoFile> response) {
                if (response.isSuccessful()){

                    Log.e("JSONResponseParm", "" +fileExt+""+appKey+""+file);
                    Log.e("JSONResponse", "" + response.body());
                }
                else
                    Log.e("JSONResponseParm", "" +fileExt+" "+appKey+" "+file);
                    Log.e("RecordingTimeCallNot", "" + response.body());
            }

            @Override
            public void onFailure(Call<VideoFile> call, Throwable t) {
                Log.e("RecordingTimeCallError", t.toString());
            }
        });

    }


    protected void startRecording() throws IOException
      {
          if(mCamera!=null){

              mCamera=null;
          }
          if (cameraCode==0){

              mCamera = Camera.open();
              setCamera(mCamera);
          }
          else if (cameraCode==1){
              mCamera = Camera.open(1);
              setCamera(mCamera);
          }

          String filename;
          path= Environment.getExternalStorageDirectory().getAbsolutePath().toString();

          Date date=new Date();
          filename="/rec"+date.toString().replace(" ", "_").replace(":", "_")+".mp4";
          //create empty file it must use
          file=new File(path,filename);
          filePath=path+filename;
          isRecording =true;
          isrec=1;

          mrec = new MediaRecorder();
          mCamera.unlock();

          // Please maintain sequence of following code.
          //
          // If you change sequence it will not work.


        //Old code
          mrec.setCamera(mCamera);
          mrec.setAudioSource(MediaRecorder.AudioSource.CAMCORDER);
          mrec.setVideoSource(MediaRecorder.VideoSource.CAMERA);

          mrec.setProfile(CamcorderProfile.get(CamcorderProfile.QUALITY_HIGH));

//          mrec.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
//          mrec.setVideoEncoder(MediaRecorder.VideoEncoder.MPEG_4_SP);
//          mrec.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

          mrec.setMaxDuration(59000);
          mrec.setPreviewDisplay(surfaceHolder.getSurface());
          mrec.setOutputFile(path+filename);
          mrec.prepare();
      mrec.start();
          flashCode=1;
          pStatus=0;
    }



    protected void stopRecording() {
        if(mrec!=null)
        {
            mrec.stop();
            mrec.release();
            mCamera.release();
            mCamera.lock();
        }
    }

    private void releaseMediaRecorder(){
        if (mrec != null) {
            mrec.reset(); // clear recorder configuration
            mrec.release(); // release the recorder object
        }
    }

    private void releaseCamera(){
        if (mCamera != null) {
            mCamera.release(); // release the camera for other applications
            mCamera = null;
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            if (mCamera != null) {
                setCamera(mCamera);

//                Camera.Parameters params = mCamera.getParameters();
//                mCamera.setDisplayOrientation(90);
//                mCamera.setParameters(params);
//                try {
//                    mCamera.setPreviewDisplay(surfaceHolder);
//                    mCamera.startPreview();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
                Log.i("Surface", "Created");
            }

            else {
                Toast.makeText(getApplicationContext(), "Camera not available!",
                        Toast.LENGTH_LONG).show();

                finish();
            }

        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (mCamera != null) {

            try {

                mCamera.setPreviewDisplay(surfaceHolder);
                mCamera.startPreview();

            } catch (IOException e) {
                e.printStackTrace();
            }
            Log.i("Surface", "Created");
        }
        else {
            Toast.makeText(getApplicationContext(), "Camera not available!",
                    Toast.LENGTH_LONG).show();

            finish();
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        if (mCamera!=null) {
            mCamera.stopPreview();
            mCamera.release();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("Lyf Cycle","OnPause");
        if (mCamera != null) {
            mCamera.setPreviewCallback(null);
            surfaceView.getHolder().removeCallback(this);
            mCamera.stopPreview();

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("Lyf Cycle","onResume");
        if (mCamera== null) {
            mCamera = Camera.open();
        }
        setCamera(mCamera);
        surfaceHolder = surfaceView.getHolder();
        surfaceHolder.addCallback(this);
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        Log.e("Lyf Cycle","CallonResume");
    }



    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void flashLight(int mode) {
        Log.e("Flash ","Light On/Off");
        if (mCamera!=null){

            mCamera=Camera.open();
            setCamera(mCamera);
        }
        Camera.Parameters parameters = mCamera.getParameters();
        if (mode==0){
            parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
        }
        else if (mode==1){
            parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
        }
        mCamera.setParameters(parameters);
        try {
            mCamera.setPreviewDisplay(surfaceView.getHolder());
            mCamera.startPreview();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void setCamera(Camera camera) {
        //method to set a camera instance
        mCamera = camera;
        Log.e("cameraSet","Call");
        /**
         * add camera orientation and display rotation according to screen landscape or portrait
         */
        setCameraRotation();
    }

//    camera display orientation
public void setCameraRotation() {
    try {
        Log.e("cameraSet","Call");
        Camera.CameraInfo camInfo = new Camera.CameraInfo();

        if (cameraCode== 0)
            Camera.getCameraInfo(Camera.CameraInfo.CAMERA_FACING_BACK, camInfo);
        else
            Camera.getCameraInfo(Camera.CameraInfo.CAMERA_FACING_FRONT, camInfo);
        int cameraRotationOffset = camInfo.orientation;
        // ...

        Camera.Parameters parameters = mCamera.getParameters();



        int rotation = MainActivity.this.getWindowManager().getDefaultDisplay().getRotation();
        int degrees = 0;
        switch (rotation) {
            case Surface.ROTATION_0:
                degrees = 0;
                break; // Natural orientation
            case Surface.ROTATION_90:
                degrees = 90;
                break; // Landscape left
            case Surface.ROTATION_180:
                degrees = 180;
                break;// Upside down
            case Surface.ROTATION_270:
                degrees = 270;
                break;// Landscape right
        }
        int displayRotation;
        if (camInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
            displayRotation = (cameraRotationOffset + degrees) % 360;
            displayRotation = (360 - displayRotation) % 360; // compensate
            // the
            // mirror
        } else { // back-facing
            displayRotation = (cameraRotationOffset - degrees + 360) % 360;
        }

        mCamera.setDisplayOrientation(displayRotation);


        if (camInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
            rotate = (360 + cameraRotationOffset + degrees) % 360;
        } else {
            rotate = (360 + cameraRotationOffset - degrees) % 360;
        }

        parameters.set("orientation", "portrait");
        parameters.setRotation(rotate);
        parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_VIDEO);
        parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_INFINITY);
        mCamera.setParameters(parameters);

    } catch (Exception e) {

    }
}






    public void releaseAllMediaRecorder(){
        if (mrec != null) {
            // clear recorder configuration
            mrec.reset();
            // release the recorder object
            mrec.release();
            mrec = null;
            // Lock camera for later use i.e taking it back from MediaRecorder.
            // MediaRecorder doesn't need it anymore and we will release it if the activity pauses.
//            mCamera.lock();
        }
    }

    private void releaseCameraHardware(){
        if (mCamera != null){
            // release the camera for other applications
            mCamera.release();
            mCamera = null;
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("Lyf Cycle","OnDestroy");
       releaseAllMediaRecorder();

       releaseCameraHardware();
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("operationCode", "");
        editor.commit();


    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Checks the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Toast.makeText(this, "landscape", Toast.LENGTH_SHORT).show();
            updateLayout(newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE);

        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
//            stopRecording();
            updateLayout(false);
            Toast.makeText(this, "portrait", Toast.LENGTH_SHORT).show();


        }
    }


    // method for bitmap to base64
    public String encodeTobase64(File file) throws IOException {
        cardProgress.setVisibility(View.VISIBLE);
        InputStream inputStream = null;//You can get an inputStream using any IO API
        inputStream = new FileInputStream(file.getAbsoluteFile());
        byte[] buffer = new byte[8192];
        int bytesRead;
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        Base64OutputStream output64 = new Base64OutputStream(output, Base64.DEFAULT);
        try {
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                output64.write(buffer, 0, bytesRead);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        output64.close();

        encodedString = output.toString();
        Log.e("fileEncoded",encodedString);
        Log.e("fileEN",encodedString);
        recordingTimeApiCall("mov","133",encodedString);
        Log.e("fileENFinish",encodedString);
        cardProgress.setVisibility(View.GONE);
        return encodedString;

    }
    public void saveData(String vidPath) {

        // put value in preference on button click
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("vidPath", vidPath);
        editor.putString("operationCode", "1");
        editor.commit();
//        Log.e("Savedata",vidPath);

    }
    public void getSaveData() {
        // put value in preference on button click
        vidpath = sharedPreferences.getString("vidPath", "");
        operationCode = sharedPreferences.getString("operationCode", "");
        filePath=vidpath;

    }

    public void logout(){


        startActivity(new Intent(MainActivity.this, LoginActivity.class));

    }


}

