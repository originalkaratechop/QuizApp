package com.example.android.quizapp;

import android.app.ActionBar;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    View slide1;
    LinearLayout slide2;
    int prog = 0;
    //slide2body order
/*    TextView txtTitle;
    View fillerTop;
    TextView txtBody;*/
    LinearLayout rGroup;
    RadioButton rBtn1;
    RadioButton rBtn2;
    RadioButton rBtn3;
    RadioButton rBtn4;
/*    View fillerBot;
    Button btnNext;*/
    ProgressBar pBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); // lock portrait

        slide1 = (View) findViewById(R.id.slide1Content);
        slide2 = (LinearLayout) findViewById(R.id.slide2Body);

        //load slide2order
  /*      txtTitle = (TextView) findViewById(R.id.textTitle2);
        fillerTop = (View) findViewById(R.id.fillerTop);
        txtBody = (TextView) findViewById(R.id.textBody2);*/ // delete; added viewgroup
        rGroup = (LinearLayout) findViewById(R.id.rGroup);
        rBtn1 = (RadioButton) findViewById(R.id.rBtn1);
        rBtn2 = (RadioButton) findViewById(R.id.rBtn2);
        rBtn3 = (RadioButton) findViewById(R.id.rBtn3);
        rBtn4 = (RadioButton) findViewById(R.id.rBtn4);
/*        fillerBot = (View) findViewById(R.id.fillerBot);
        btnNext = (Button) findViewById(R.id.btnNext);*/
        pBar = (ProgressBar) findViewById(R.id.progressBar);

 /*       ViewGroup parent = (ViewGroup) findViewById(R.id.container);
        // result: layout_height=wrap_content layout_width=match_parent
        View view = LayoutInflater.from(this).inflate(R.layout.red, null);
        parent.addView(view);*/
    }

    public void start(View view) {
        slide1.setVisibility(View.GONE);
        slide2.setVisibility(View.VISIBLE);
    }

    public void nextSlide(View view) {
        if (prog != 20) {
            prog = +prog + 1;
        } else {
            prog = 20;
        }
        pBar.setProgress(prog);

        rGroup.removeAllViews();

        int qRand1 = new Random().nextInt(4) + 1;
        int qRand2 = new Random().nextInt(4) + 1;
        int qRand3 = new Random().nextInt(4) + 1;
        int qRand4 = new Random().nextInt(4) + 1;

        while (qRand2 == qRand1){
            qRand2 = new Random().nextInt(4) + 1;
        }
        while (qRand3 == qRand1 || qRand3 == qRand2 ){
            qRand3 = new Random().nextInt(4) + 1;
        }
        while (qRand4 == qRand1 || qRand4 == qRand2 || qRand4 == qRand3 ){
            qRand4 = new Random().nextInt(4) + 1;
        }

        String value1 = Integer.toString(qRand1);
        String value2 = Integer.toString(qRand2);
        String value3 = Integer.toString(qRand3);
        String value4 = Integer.toString(qRand4);
        String rBtn = "R.id.rBtn";
        String rBtnR1 = rBtn.concat(value1);
        String rBtnR2 = rBtn.concat(value2);
        String rBtnR3 = rBtn.concat(value3);
        String rBtnR4 = rBtn.concat(value4);
        int id1 = this.getResources().getIdentifier(rBtn+qRand1, "id", this.getPackageName());
        int id2 = this.getResources().getIdentifier("R.id.rBtn"+value2, "id", this.getPackageName());
        int id3 = this.getResources().getIdentifier("rBtn"+value3, "id", this.getPackageName());
        int id4 = this.getResources().getIdentifier("rBtn"+qRand1, "id", this.getPackageName());
    /*    rBtn1 = findViewById(id1);
        rBtn2 = findViewById(id2);
        rBtn3 = findViewById(id3);
        rBtn4 = findViewById(id4);*/

        int resourceId = this.getResources().getIdentifier("rBtn"+value1, "string", this.getPackageName());
        Log.i("tst", "");
        Log.i("tst", ""+ id1);
        Log.i("tst", ""+ id2);
        Log.i("tst", ""+ id3);
        Log.i("tst", ""+ id4);
        Log.i("tst", ""+ rBtnR4);

        rGroup.addView(rBtn3);
        rGroup.addView(rBtn2);
        rGroup.addView(rBtn1);
        rGroup.addView(rBtn4);

    }
}
