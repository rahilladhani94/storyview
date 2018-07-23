package com.storyview.Activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;


import com.storyview.R;
import com.storyview.Utils.ScreenshotType;
import com.storyview.Utils.ScreenshotUtils;
import com.storyview.Model.Contact;
import com.storyview.Database.DatabaseHandler;
import com.storyview.Utils.UIUtil;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class CreateStatus extends AppCompatActivity {


    ImageView  ivTextSize, ivColor, ivSend;
    EditText edtText;
    DatabaseHandler db;
    String strfontstyle = "n";
    RelativeLayout rlroort, rlstatus;
    ImageView img;
    ArrayList<String> colorsArray = new ArrayList<String>();
    ArrayList<String> textSizeArray = new ArrayList<String>();
    ArrayList<String> fontstyleArray = new ArrayList<String>();

    TextView tvTextStyle;
    ScrollView sc;
    int selectedcolor = 0;
    int selectedtextsize = 0;
    int selectedfontstyle = 0;

    RelativeLayout bottom, rltop;
    public static final int RequestPermissionCode = 7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createstatus);
        getSupportActionBar().hide();
        db = new DatabaseHandler(this);
        idMapping();
        setonclick();
    }

    private void idMapping() {
        img = findViewById(R.id.img);

        sc = findViewById(R.id.sc);
        tvTextStyle = findViewById(R.id.tvTextStyle);
        ivTextSize = findViewById(R.id.ivTextSize);
        ivSend = findViewById(R.id.ivSend);
        ivColor = findViewById(R.id.ivColor);


        edtText = (EditText) findViewById(R.id.edtText);
        rlroort = findViewById(R.id.rlroort);
        rltop = findViewById(R.id.rltop);
        bottom = findViewById(R.id.bottom);


        colorsArray.add("#F37B22");
        colorsArray.add("#ff0000");
        colorsArray.add("#85C226");
        colorsArray.add("#000000");


        textSizeArray.add("12");
        textSizeArray.add("16");
        textSizeArray.add("25");
        textSizeArray.add("30");
        textSizeArray.add("35");


        fontstyleArray.add("font/Redressed.ttf");
        fontstyleArray.add("font/bevan.ttf");
        fontstyleArray.add("font/gotham_bold.otf");
        fontstyleArray.add("font/MyriadPro_Bold.otf");
        fontstyleArray.add("font/AlexRegular.ttf");
        fontstyleArray.add("font/RockSalt.ttf");



        strfontstyle = fontstyleArray.get(selectedfontstyle);
        setEdittextTypeface(strfontstyle);

        int temp = Integer.parseInt(textSizeArray.get(selectedtextsize));
        edtText.setTextSize(TypedValue.COMPLEX_UNIT_DIP, temp);

        rltop.setBackgroundColor(Color.parseColor(colorsArray.get(selectedcolor)));

    }

    private void setEdittextTypeface(String path) {
        Typeface face=Typeface.createFromAsset(getAssets(), path);
        edtText.setTypeface(face);

        tvTextStyle.setTypeface(face);
    }

    private void setonclick() {
        tvTextStyle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedfontstyle++;
                if (selectedfontstyle >= fontstyleArray.size()) {
                    selectedfontstyle = 0;
                }
                strfontstyle = fontstyleArray.get(selectedfontstyle);
                setEdittextTypeface(strfontstyle);

            }
        });


        ivTextSize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedtextsize++;
                if (selectedtextsize >= textSizeArray.size()) {
                    selectedtextsize = 0;
                }
                int temp = Integer.parseInt(textSizeArray.get(selectedtextsize));
                edtText.setTextSize(TypedValue.COMPLEX_UNIT_DIP, temp);

            }
        });

        ivSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (CheckingPermissionIsEnabledOrNot()) {
                    if (edtText.getText().toString().trim().length() > 0) {

                        saveStory();


                    }
                } else {


                    RequestMultiplePermission();

                }


            }
        });

        ivColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                selectedcolor++;
                if (selectedcolor >= colorsArray.size()) {
                    selectedcolor = 0;
                }

                rltop.setBackgroundColor(Color.parseColor(colorsArray.get(selectedcolor)));

                //  finish();
//                Intent ii =new Intent(CreateStatus.this,ViewpagerActivityDemo.class);
//                startActivity(ii);
            }
        });
    }

    private void saveStory() {
        UIUtil.hideKeyboard(CreateStatus.this);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        edtText.setCursorVisible(false);
        UIUtil.showDialog(CreateStatus.this,"Saving Your story..!");

        new Handler().postDelayed(new Runnable() {


            @Override
            public void run() {

                takeScreenshot(ScreenshotType.CUSTOM);
            }
        }, 100);


    }

    private void takeScreenshot(ScreenshotType screenshotType) {

        bottom.setVisibility(View.GONE);
        Bitmap b = null;


        b = ScreenshotUtils.getScreenShot(sc);






        if (b != null) {

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd'T'HHmmss");
            String timeStamp = dateFormat.format(new Date());
            String imageFileName = "picture_" + timeStamp + ".jpg";



            File saveFile = ScreenshotUtils.getMainDirectoryName(this);//get the path to save screenshot
            File file = ScreenshotUtils.store(b, "" + imageFileName + ".jpg", saveFile);//save the screenshot to selected path

            Log.e("file.getAbsolutePath()", "" + file.getAbsolutePath());


            Date c = Calendar.getInstance().getTime();
            System.out.println("Current time => " + c);

            SimpleDateFormat df = new SimpleDateFormat("dd MMM hh:mm a");
            String formattedDate = df.format(c);

            db.addContact(new Contact("" + edtText.getText().toString().trim(), "" + strfontstyle, "" + textSizeArray.get(selectedtextsize), colorsArray.get(selectedcolor),"" + file.getAbsolutePath(),formattedDate));

            UIUtil.dismissDialog();
            finish();

        } else {
        }
        //If bitmap is null show toast message

    }

    private void RequestMultiplePermission() {

        // Creating String Array with Permissions.
        ActivityCompat.requestPermissions(CreateStatus.this, new String[]
                {
                        Manifest.permission.CAMERA,
                        WRITE_EXTERNAL_STORAGE
                }, RequestPermissionCode);

    }

    // Calling override method.
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {

            case RequestPermissionCode:

                if (grantResults.length > 0) {

                    boolean CameraPermission = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean WritePermission = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                    if (CameraPermission && WritePermission) {
                        if (edtText.getText().toString().trim().length() > 0) {
                            saveStory();
                        }

                    } else {
                        Toast.makeText(CreateStatus.this, "Permission Denied", Toast.LENGTH_LONG).show();

                    }
                }

                break;
        }
    }

    // Checking permission is enabled or not using function starts from here.
    public boolean CheckingPermissionIsEnabledOrNot() {

        int FirstPermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA);
        int sPermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);


        return FirstPermissionResult == PackageManager.PERMISSION_GRANTED &&
                sPermissionResult == PackageManager.PERMISSION_GRANTED;
    }

}
