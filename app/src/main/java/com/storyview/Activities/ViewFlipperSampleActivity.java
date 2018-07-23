package com.storyview.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.storyview.R;
import com.storyview.Model.Contact;
import com.storyview.Database.DatabaseHandler;
import com.storyview.Utils.ScreenshotType;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

public class ViewFlipperSampleActivity extends Activity {
    private static final int SWIPE_MIN_DISTANCE = 120;
    private static final int SWIPE_THRESHOLD_VELOCITY = 200;
    private ViewFlipper mViewFlipper;
    private Context mContext;
    private final GestureDetector detector = new GestureDetector(new SwipeGestureDetector());
    CountDownTimer timer;
    LinearLayout llmain;
    int arrayposition = 0;
    int progress = 0;
    LinearLayout playprevious, playnext;
    ArrayList<Contact> contacts;

    ProgressBar pb;
    DatabaseHandler db;
    Boolean isFlipToStop = false;
    RelativeLayout mainlayout, rlMain;
    private float startX;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_flipper);
        mContext = this;
        db = new DatabaseHandler(this);
        idMapping();
        getData();
        setonClick();


    }

    private void getData() {
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();


        if (extras != null) {
            arrayposition = extras.getInt("position");
            contacts = new ArrayList<Contact>();
            contacts = (ArrayList<Contact>) db.getAllContacts();
            Collections.reverse(contacts);
            setFlipperImage(arrayposition);
            // setFirstChild();
            startFlipping();
        }
    }


    private void startFlipping() {

        timer = new CountDownTimer(200, 200) {

            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {

                if (!isFlipToStop) {
                    progress = progress + 3;
                    pb.setProgress(progress);

                    if (progress > 100) {
                        arrayposition++;

                        setFlipperImage(arrayposition);
                        progress = 0;

                    }
                }
                timer.start();

            }
        }.start();
    }


    private void stopFlipping() {

        isFlipToStop = true;
        progress = 0;

        mViewFlipper.stopFlipping();
    }

    private void idMapping() {
        mViewFlipper = (ViewFlipper) this.findViewById(R.id.view_flipper);

        mainlayout = findViewById(R.id.mainlayout);
        rlMain = findViewById(R.id.rlMain);
        playprevious = findViewById(R.id.playprevious);
        playnext = findViewById(R.id.playnext);
        pb = findViewById(R.id.pb);

        playprevious.setOnLongClickListener(speakHoldListener);
        playprevious.setOnTouchListener(speakTouchListener);
        playnext.setOnLongClickListener(speakHoldListener);
        playnext.setOnTouchListener(speakTouchListener);

    }

    private void setonClick() {

        playprevious.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                showpreviuospoition();
            }
        });

        playnext.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                shownextpoition();

            }
        });


        mViewFlipper.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                int action = event.getActionMasked();

                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        startX = event.getX();
                        break;
                    case MotionEvent.ACTION_UP:
                        float endX = event.getX();
                        float endY = event.getY();

                        //swipe right``
                        if (startX < endX) {
                            showpreviuospoition();

                        }

                        //swipe left
                        if (startX > endX) {
                            shownextpoition();

                        }

                        break;
                }
                return true;
            }
        });

    }

    private void showpreviuospoition() {

        stopFlipping();

        mViewFlipper.setInAnimation(AnimationUtils.loadAnimation(mContext, R.anim.slide_in_left));
        mViewFlipper.setOutAnimation(AnimationUtils.loadAnimation(mContext, R.anim.slide_out_left));

        mViewFlipper.showPrevious();
        arrayposition = arrayposition - 1;
        startFlipping();
        setFlipperImage(arrayposition);


    }

    private void shownextpoition() {
        stopFlipping();

        mViewFlipper.setInAnimation(AnimationUtils.loadAnimation(mContext, R.anim.slide_in_right));
        mViewFlipper.setOutAnimation(AnimationUtils.loadAnimation(mContext, R.anim.slide_out_right));

        mViewFlipper.showNext();
        arrayposition = arrayposition + 1;
        startFlipping();
        setFlipperImage(arrayposition);
    }


    class SwipeGestureDetector extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            try {


                // right to left swipe
                if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {

                    showpreviuospoition();
                    return true;
                } else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {

                    shownextpoition();

                    return true;
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            return false;
        }
    }

    private void setFlipperImage(int res) {
        Log.e("iiii", res + "");
        isFlipToStop = false;
        if (arrayposition < contacts.size() && arrayposition >= 0) {

            ImageView imageflipper = findViewById(R.id.imageflipper);

            if (imageflipper.getParent() != null)
                ((ViewGroup) imageflipper.getParent()).removeView(imageflipper); // <- fix

            File imgFile = new File(contacts.get(arrayposition).getImagepath());
            if (imgFile.exists()) {

                imageflipper.setImageURI(Uri.fromFile(imgFile));

            }

            mainlayout.setBackgroundColor(Color.parseColor(contacts.get(arrayposition).getBgcolor()));


            mViewFlipper.addView(imageflipper);
        } else {

            Toast.makeText(ViewFlipperSampleActivity.this, "No more stories...!!", Toast.LENGTH_SHORT).show();
            stopFlipping();
            finish();
        }


    }


    private View.OnLongClickListener speakHoldListener = new View.OnLongClickListener() {

        @Override
        public boolean onLongClick(View pView) {
            // Do something when your hold starts here.
            isFlipToStop = true;
            return true;
        }
    };

    private View.OnTouchListener speakTouchListener = new View.OnTouchListener() {

        @Override
        public boolean onTouch(View pView, MotionEvent pEvent) {
            pView.onTouchEvent(pEvent);
            // We're only interested in when the button is released.
            if (pEvent.getAction() == MotionEvent.ACTION_UP) {
                // We're only interested in anything if our speak button is currently pressed.

                    isFlipToStop = false;

            }
            return false;
        }
    };

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        timer.cancel();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        timer.cancel();
    }
}