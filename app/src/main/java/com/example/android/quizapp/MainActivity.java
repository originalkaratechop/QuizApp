package com.example.android.quizapp;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.ActionBar;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.CountDownTimer;
import android.support.annotation.DrawableRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    View slide1;
    View slide2;
    View bodyRadio;
    View bodyType;
    View aniView;
    View resetView;
    //    LinearLayout slide2;
    int prog;
    //slide2body order
/*    TextView txtTitle;
    View fillerTop; */
    TextView txtRadio;
    TextView txtType;
    TextView txtHeader;
    TextView txtReset;
    RadioGroup rGroup;
    RadioButton rBtn1;
    RadioButton rBtn2;
    RadioButton rBtn3;
    RadioButton rBtn4;
    /*    View fillerBot;
        Button btnNext;*/
    ProgressBar pBar;
    int aCount = 4;
    int qCountRadio = 20;
    int qCountType = 5;
    int qRand;
    int qRandType;
    int qId;
    int qMax = 10;
    Button buttons[] = new Button[aCount];
    ArrayList<Integer> qListRadio;
    ArrayList<Integer> qListType;
    EditText inputName;
    EditText answerType;
    String nameValue;
    String aTypeId;
    String q;
    ImageView aniBack;
    ImageView aniContent;
    ImageView aniCheker;
    TextView aniTxt;
    private static final long DURATION_GROW = 400;
    private static final long DURATION_SHRINK = 100;
    private static final long DURATION_FADE = 1000;
    private static boolean isAnimate = false;
    CheckBox cBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); // lock portrait

        slide1 = (View) findViewById(R.id.slide1Context);
        slide2 = (View) findViewById(R.id.slide2Context);
        bodyRadio = (View) findViewById(R.id.bodyRadio);
        bodyType = (View) findViewById(R.id.bodyType);
        aniView = (View) findViewById(R.id.aniSet);
        resetView = (View) findViewById(R.id.resetOverlay);
        //  slide2 = (LinearLayout) findViewById(R.id.slide2Body);

        qListRadio = new ArrayList<Integer>();
        qListType = new ArrayList<Integer>();
        qId = 1;
        prog = 0;
        q = getResources().getString(R.string.q);
        inputName = (EditText) findViewById(R.id.inputName);
        answerType = (EditText) findViewById(R.id.answerType);

        //load slide2order
  /*      txtTitle = (TextView) findViewById(R.id.textTitle2);
        fillerTop = (View) findViewById(R.id.fillerTop);*/ // delete; added viewgroup
        txtRadio = (TextView) findViewById(R.id.textRadio);
        txtType = (TextView) findViewById(R.id.textType);
        txtHeader = (TextView) findViewById(R.id.textHeader2);
        txtReset = (TextView) findViewById(R.id.resetTxt);
        rGroup = (RadioGroup) findViewById(R.id.rGroup);
        rBtn1 = (RadioButton) findViewById(R.id.rBtn1);
        rBtn2 = (RadioButton) findViewById(R.id.rBtn2);
        rBtn3 = (RadioButton) findViewById(R.id.rBtn3);
        rBtn4 = (RadioButton) findViewById(R.id.rBtn4);
        buttons[0] = rBtn1;
        buttons[1] = rBtn2;
        buttons[2] = rBtn3;
        buttons[3] = rBtn4;
/*        fillerBot = (View) findViewById(R.id.fillerBot);
        btnNext = (Button) findViewById(R.id.btnNext);*/
        pBar = (ProgressBar) findViewById(R.id.progressBar);

        aniBack = (ImageView) findViewById(R.id.aniBack);
        aniContent = (ImageView) findViewById(R.id.aniContent);
        aniCheker = (ImageView) findViewById(R.id.aniChecker);
        aniTxt = (TextView) findViewById(R.id.aniTxt);
        cBox = (CheckBox) findViewById(R.id.checkBox);

        //  animation test block
/*
        Drawable picCup = getDrawable(R.drawable.q_cup);
        Drawable picSun = getDrawable(R.drawable.q_sun);
        winCup(aniContent, 0, picCup);
        winSun(aniBack, 0, picSun);*/


        //int id_cup = R.drawable.q_cup;
        //int id_sun = R.drawable.q_sun;
        // aniBack.setImageResource(id);
        //     winSunMassive(aniBack, id_sun);
        //     winAniCup(aniContent, id_cup);
        //      winAniSun(aniBack, id_sun);

        // start operations

        typeShuffle();
        radioShuffle();
        Log.i("tst", "type array" + qListType);
        Log.i("tst", "radio array" + qListRadio);
 /*       ViewGroup parent = (ViewGroup) findViewById(R.id.container);
        // result: layout_height=wrap_content layout_width=match_parent
        View view = LayoutInflater.from(this).inflate(R.layout.red, null);
        parent.addView(view);*/
    }

    public void start(View view) {
        if (inputName.getText().toString().isEmpty()) {
            //do nothing (yet)
            Drawable picCup = getDrawable(R.drawable.q_cup);
            Drawable picSun = getDrawable(R.drawable.q_sun);
            winCup(aniContent, 0, picCup);
            winSun(aniBack, 0, picSun);
        } else {
            slide1.setVisibility(View.GONE);
            slide2.setVisibility(View.VISIBLE);
            String head = String.format(q, qId);
            txtHeader.setText(head);
            nameValue = inputName.getText().toString();
        }
    }

    public void nextSlide(View view) {
/*        questionCounter();   tempo cut
        Log.i("tst", "" + qRand);*/

        if (qId == 3 || qId == 5 || qId == 9) {
            typeCheck();
            Log.i("tst", "type array" + qListType);
        } else {
            radioCheck();
            Log.i("tst", "radio array" + qListRadio);
        }
    }

    private void radioShuffle() {

        Log.i("tst", "+ add new radio ");
        rGroup.removeAllViews();
        //originally void questionCounter()
        qRand = new Random().nextInt(qCountRadio);
        while (qListRadio.contains(qRand)) {
            qRand = new Random().nextInt(qCountRadio);
        }
        qListRadio.add(qRand);
        //Log.i("tst", "from q shuffle" + qRand);

        int qRadioId = getResources().getIdentifier("opt" + qRand * 5, "string", getPackageName());
        txtRadio.setText(qRadioId);

        // legend
        // 0 = q
        // 1 = a correct
        // 2 = a
        // 3 = a
        // 4 = a  (total 5 for each question)

        //originally void answerShuffle()
        ArrayList<Integer> aList = new ArrayList<Integer>();
        for (int i = 0; i < aCount; i++) {
            aList.add(i);
        }
        Collections.shuffle(aList);

        for (int i = 0; i < aCount; i++) {
            rGroup.addView(buttons[aList.get(i)]);
            int buttonId = getResources().getIdentifier("opt" + (qRand * 5 + i + 1), "string", getPackageName());
            buttons[i].setText(buttonId);
            //   Log.i("tst", "" + aList.get(i));
        }

        //Log.i("tst", "from a shuffle" + qRand);
        //Log.i("tst", "=============");
    }

    private void typeShuffle() {
        //mod void questionCounter()
        qRandType = new Random().nextInt(qCountType);
        while (qListType.contains(qRandType)) {
            qRandType = new Random().nextInt(qCountType);
        }
        qListType.add(qRandType);
        int qTypeId = getResources().getIdentifier("optType" + (qRandType * 2), "string", getPackageName());
        int correct = getResources().getIdentifier("optType" + (qRandType * 2 + 1), "string", getPackageName());

        aTypeId = getResources().getString(correct);
        txtType.setText(qTypeId);
    }

    private void radioCheck() {
        if (bodyRadio.getVisibility() == View.VISIBLE) {
            if (rGroup.getCheckedRadioButtonId() == -1) {
                String err = getString(R.string.error1);
                checkMoveTxt(aniTxt, 0, err);
                // do nothing on unchecked (yet)
                //   t = Toast.makeText(this, R.string.select, Toast.LENGTH_SHORT);
                //   t.show();
            } else {
                if (rBtn1.isChecked()) {

                    rGroup.clearCheck();
                    rGroup.jumpDrawablesToCurrentState(); //skip animation. safe
                    radioShuffle();  //to remove later
                 /*   if (qId < 10) {
                        radioShuffle();
                    } else*/
                    if (qId == 10) {
                        Drawable picCup = getDrawable(R.drawable.q_cup);
                        Drawable picSun = getDrawable(R.drawable.q_sun);
                        winCup(aniContent, 0, picCup);
                        winSun(aniBack, 0, picSun);
                        resetView.setVisibility(View.VISIBLE);
                        String res = getResources().getString(R.string.ending);
                        String resetFinal = String.format(res, nameValue);
                        txtReset.setText(resetFinal);
                    } else {
                        Drawable picCheck = getDrawable(R.drawable.q_check);
                        String pass = getString(R.string.success);
                        checkMoveImg(aniCheker, 0, picCheck);
                        checkMoveTxt(aniTxt, 0, pass);
                    }
                    progUpdate();
                    //   t.cancel();
                } else {
                    Drawable picCross = getDrawable(R.drawable.q_cross);
                    String err = getString(R.string.error2);
                    checkMoveImg(aniCheker, 0, picCross);
                    checkMoveTxt(aniTxt, 0, err);
                    rGroup.clearCheck();
                    rGroup.jumpDrawablesToCurrentState(); //skip animation. safe
                }
            }
        }
    }

    private void typeCheck() {
        if (bodyType.getVisibility() == View.VISIBLE) {
            String aType = answerType.getText().toString();
            if (aTypeId.equals(aType)) {
                Drawable picCheck = getDrawable(R.drawable.q_check);
                String pass = getString(R.string.success);
                checkMoveImg(aniCheker, 0, picCheck);
                checkMoveTxt(aniTxt, 0, pass);
                answerType.setText("");

                if (qId < 9) {
                    typeShuffle();
                }
                progUpdate();
            } else if (aType.isEmpty()) {
                String err = getString(R.string.error3);
                checkMoveTxt(aniTxt, 0, err);
            } else {
                Drawable picCross = getDrawable(R.drawable.q_cross);
                String err = getString(R.string.error2);
                checkMoveImg(aniCheker, 0, picCross);
                checkMoveTxt(aniTxt, 0, err);
            }
        }
    }

    private void bodySwitch() {

        if (qId == 3 || qId == 5 || qId == 9) {
            bodyRadio.setVisibility(View.GONE);
            bodyType.setVisibility(View.VISIBLE);
        } else {
            bodyRadio.setVisibility(View.VISIBLE);
            bodyType.setVisibility(View.GONE);
        }
        /*
        if (bodyType.getVisibility() == View.VISIBLE) {
            bodyRadio.setVisibility(View.VISIBLE);
            bodyType.setVisibility(View.GONE);
        } else if (bodyRadio.getVisibility() == View.VISIBLE) {
            bodyRadio.setVisibility(View.GONE);
            bodyType.setVisibility(View.VISIBLE);
        }*/
    }

    private void progUpdate() {
        //    qId = qId + 1;
        if (qId != qMax) {
            //  if (prog != qMax) {
            qId = qId + 1;
            prog = prog + 1;
        } else {
            qId = qMax;
            prog = qMax; //remove whole block maybe
        }
        String head = String.format(q, qId);
        txtHeader.setText(head);
        pBar.setProgress(prog);
        bodySwitch();
    }

    public void resetGame(View view) {
        qListRadio.clear();
        qListType.clear();
        qId = 1;
        prog = 0;

        String head = String.format(q, qId);
        txtHeader.setText(head);

        pBar.setProgress(prog);
        resetView.setVisibility(View.GONE);
        typeShuffle();
        radioShuffle();
        bodySwitch();
        Log.i("tst", "type array" + qListType);
        Log.i("tst", "radio array" + qListRadio);

        if (cBox.isChecked()){
            slide1.setVisibility(View.VISIBLE);
            slide2.setVisibility(View.GONE);
            inputName.setText("");
            cBox.setChecked(false);
        }
    }

    // ANIMATION MINE
    private static ObjectAnimator winAlphaObj(View view, long duration, long curPlayTime, float start, float stop) {
        ObjectAnimator showAlphaObj = ObjectAnimator.ofFloat(view, View.ALPHA, start, stop);
        showAlphaObj.setDuration(duration);
        showAlphaObj.setCurrentPlayTime(curPlayTime);
        showAlphaObj.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                //insert something already
            }
        });
        return showAlphaObj;
    }

    private static ObjectAnimator winScaleXObj(View view, long duration, long curPlayTime, float start, float stop) {
        ObjectAnimator showScaleXObj = ObjectAnimator.ofFloat(view, View.SCALE_X, start, stop);
        showScaleXObj.setDuration(duration);
        showScaleXObj.setCurrentPlayTime(curPlayTime);
        return showScaleXObj;
    }

    private static ObjectAnimator winScaleYObj(View view, long duration, long curPlayTime, float start, float stop) {
        ObjectAnimator showScaleYObj = ObjectAnimator.ofFloat(view, View.SCALE_Y, start, stop);
        showScaleYObj.setDuration(duration);
        showScaleYObj.setCurrentPlayTime(curPlayTime);
        return showScaleYObj;
    }

    private static ObjectAnimator winRotateObj(View view, long duration, long curPlayTime, float start, float stop) {
        ObjectAnimator showScaleYObj = ObjectAnimator.ofFloat(view, View.ROTATION, start, stop);
        showScaleYObj.setDuration(duration);
        showScaleYObj.setCurrentPlayTime(curPlayTime);
        return showScaleYObj;
    }

    private static ObjectAnimator checkTranslateX(View view, long duration, long curPlayTime, float start, float stop) {
        ObjectAnimator showScaleYObj = ObjectAnimator.ofFloat(view, View.TRANSLATION_X, start, stop);
        showScaleYObj.setDuration(duration);
        showScaleYObj.setCurrentPlayTime(curPlayTime);
        return showScaleYObj;
    }

    private static void winCup(View view, long current, Drawable img) {
        isAnimate = true;
        final ImageView v = (ImageView) view;
        v.setImageDrawable(img);

        final ObjectAnimator step1 = winAlphaObj(v, DURATION_GROW, current, 0f, 1f);
        final ObjectAnimator step2 = winScaleXObj(v, DURATION_GROW, current, 0.2f, 1.4f);
        final ObjectAnimator step3 = winScaleYObj(v, DURATION_GROW, current, 0.2f, 1.4f);

        final ObjectAnimator step4 = winScaleXObj(v, DURATION_SHRINK, current, 1.4f, 1.2f);
        final ObjectAnimator step5 = winScaleYObj(v, DURATION_SHRINK, current, 1.4f, 1.2f);

        final ObjectAnimator step6 = winAlphaObj(v, DURATION_FADE, current, 1f, 0f);

        step1.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                step4.start();
                step5.start();
            }
        });

        step4.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                step6.setStartDelay(300);
                step6.start();
            }
        });

        step6.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                v.setImageDrawable(null);
                isAnimate = false;
            }
        });

        if (isAnimate) {
            step1.start();
            step2.start();
            step3.start();
            step4.start();
        }
    }

    private static void winSun(View view, long current, Drawable img) {
        isAnimate = true;
        final ImageView v = (ImageView) view;
        v.setImageDrawable(img);

        final ObjectAnimator step1 = winRotateObj(v, DURATION_GROW + DURATION_GROW + DURATION_FADE, current, 0f, 60f);

        final ObjectAnimator step2 = winAlphaObj(v, DURATION_GROW, current, 0f, 1f);
        final ObjectAnimator step3 = winScaleXObj(v, DURATION_GROW, current, 0.2f, 1.4f);
        final ObjectAnimator step4 = winScaleYObj(v, DURATION_GROW, current, 0.2f, 1.4f);

        final ObjectAnimator step5 = winScaleXObj(v, DURATION_GROW, current, 1.4f, 1.5f);
        final ObjectAnimator step6 = winScaleYObj(v, DURATION_GROW, current, 1.4f, 1.5f);

        final ObjectAnimator step7 = winAlphaObj(v, DURATION_FADE, current, 1f, 0f);


        step2.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                step5.start();
                step6.start();
            }
        });

        step6.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                step7.start();
            }
        });

        step7.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                v.setImageDrawable(null);
                isAnimate = false;
            }
        });

        if (isAnimate) {
            step1.start();
            step2.start();
            step3.start();
            step4.start();
        }
    }

    private static void checkMoveImg(View view, long current, Drawable img) {
        isAnimate = true;
        final ImageView v = (ImageView) view;
        v.setImageDrawable(img);

        final ObjectAnimator step1 = checkTranslateX(v, DURATION_GROW, current, -1000f, -30f);
        final ObjectAnimator step2 = winAlphaObj(v, DURATION_GROW, current, 0.1f, 1f);

        final ObjectAnimator step3 = checkTranslateX(v, DURATION_FADE, current, -30f, 30f);

        final ObjectAnimator step4 = checkTranslateX(v, DURATION_GROW, current, 30f, 1000f);
        final ObjectAnimator step5 = winAlphaObj(v, DURATION_GROW - 100, current, 1f, 0f);

        step1.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                step3.start();
            }
        });

        step3.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                step4.start();
                step5.start();
            }
        });

        step5.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                v.setImageDrawable(null);
                isAnimate = false;
            }
        });

        if (isAnimate) {
            step1.start();
            step2.start();
        }

    }

    private static void checkMoveTxt(View view, long current, String str) {
        isAnimate = true;
        final TextView v = (TextView) view;
        v.setText(str);

        final ObjectAnimator step1 = checkTranslateX(view, DURATION_GROW, current, -1000f, -30f);
        final ObjectAnimator step2 = winAlphaObj(view, DURATION_GROW, current, 0.1f, 1f);

        final ObjectAnimator step3 = checkTranslateX(view, DURATION_FADE, current, -30f, 30f);

        final ObjectAnimator step4 = checkTranslateX(view, DURATION_GROW, current, 30f, 1000f);
        final ObjectAnimator step5 = winAlphaObj(view, DURATION_GROW - 100, current, 1f, 0f);


        step1.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                step3.start();
            }
        });

        step3.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                step4.start();
                step5.start();
            }
        });

        step5.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                v.setText(null);
                isAnimate = false;
            }
        });

        if (isAnimate) {
            step1.start();
            step2.start();
        }
    }

}



 /* to remember ====================================================================================

    int qRand1 = new Random().nextInt(4);
    int qRand2 = new Random().nextInt(4);
    int qRand3 = new Random().nextInt(4);
    int qRand4 = new Random().nextInt(4);

            while (qRand2 == qRand1) {
                    qRand2 = new Random().nextInt(4);
                    }
                    while (qRand3 == qRand1 || qRand3 == qRand2) {
                    qRand3 = new Random().nextInt(4);
                    }
                    while (qRand4 == qRand1 || qRand4 == qRand2 || qRand4 == qRand3) {
                    qRand4 = new Random().nextInt(4);
                    }*/

/* removed
    //Durstenfeld shuffle
    private static void shuffleArray(int[] array) {
        int index, temp;
        Random random = new Random();
        for (int i = array.length - 1; i > 0; i--) {
            index = random.nextInt(i + 1);
            temp = array[index];
            array[index] = array[i];
            array[i] = temp;
        }
    }*/

/* //combined
    public void questionCounter() {
        qRand = new Random().nextInt(qCount);
        while (qList.contains(qRand)) {
            qRand = new Random().nextInt(qCount);
        }
        qList.add(qRand);
        int resId = getResources().getIdentifier("opt" + qRand * 5, "string", getPackageName());
        txtBody.setText(resId);
        Log.i("tst", "from q shuffle" + qRand);

        // return qRand*5;
        // 0 = q
        // 1 = a correct
        // 2 = a
        // 3 = a
        // 4 = a  (total 5 for each question)
    }

    public void answerShuffle() {
        ArrayList<Integer> aList = new ArrayList<Integer>();
        for (int i = 0; i < aCount; i++) {
            aList.add(i);
        }
        Collections.shuffle(aList);
        for (int i = 0; i < aCount; i++) {
            rGroup.addView(buttons[aList.get(i)]);
            int resId = getResources().getIdentifier("opt" + (qRand * 5 + i + 1), "string", getPackageName());
            buttons[i].setText(resId);
            //   Log.i("tst", "" + aList.get(i));
        }
        Log.i("tst", "from a shuffle" + qRand);
        Log.i("tst", "=============");
    }
*/

/*

    //animator mod example

    private static void winAniCup(final ImageView view, int img) {

        AnimatorSet set = new AnimatorSet();
        set.playSequentially(
                winImgGrow(view),
                winImgShrink(view),
                winImgHide(view));
        set.addListener(getEndListener(view, img));
        set.start();
    }

    private static void winAniSun(final ImageView view, int img) {

        AnimatorSet set = new AnimatorSet();
        set.play(winImgRotate(view));
        set.playSequentially(
                winImgGrow(view),
                winImgShrink(view),
                winImgHide(view));
        set.addListener(getEndListener(view, img));
        set.start();
    }

    private static AnimatorListenerAdapter getEndListener(final ImageView view, final int img) {
        return new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                isAnimate = true;
                view.setVisibility(View.VISIBLE);
                view.setImageResource(img);
                view.setLayerType(View.LAYER_TYPE_HARDWARE, null);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                isAnimate = false;
                view.setVisibility(View.GONE);
                view.setImageDrawable(null);
                view.setLayerType(View.LAYER_TYPE_NONE, null);
            }
        };
    }

    private static AnimatorSet winImgGrow(View view) {
        AnimatorSet set = new AnimatorSet();
        set.setDuration(DURATION_GROW).playTogether(
                ObjectAnimator.ofFloat(view, View.ALPHA, 0f, 1f),
                ObjectAnimator.ofFloat(view, View.SCALE_X, 0.2f, 1.4f),
                ObjectAnimator.ofFloat(view, View.SCALE_Y, 0.2f, 1.4f)
        );
        return set;
    }

    private static AnimatorSet winImgShrink(View view) {
        AnimatorSet set = new AnimatorSet();
        set.setDuration(DURATION_SHRINK).playTogether(
                ObjectAnimator.ofFloat(view, View.SCALE_X, 1.4f, 1.2f),
                ObjectAnimator.ofFloat(view, View.SCALE_Y, 1.4f, 1.2f)
        );
        return set;
    }

    private static AnimatorSet winImgHide(View view) {
        AnimatorSet set = new AnimatorSet();
        set.setDuration(DURATION_FADE).playTogether(
                ObjectAnimator.ofFloat(view, View.ALPHA, 1f, 0f)
        );
        set.setStartDelay(DURATION_FADE + DURATION_SHRINK);
        return set;
    }

    private static AnimatorSet winImgRotate(View view) {
        AnimatorSet set = new AnimatorSet();
        set.setDuration(DURATION_GROW + DURATION_DELAY + DURATION_SHRINK + DURATION_FADE).playTogether(
                ObjectAnimator.ofFloat(view, View.ROTATION, 0f, 360f)
        );
        return set;
    }*/

/*

    //massive combination
    private static void winSunMassive(final ImageView view, final int img) {
        view.setImageResource(img);
        view.setVisibility(View.VISIBLE);

        ObjectAnimator a1 = ObjectAnimator.ofFloat(view, View.ALPHA, 0f, 1f);
        ObjectAnimator scX1 = ObjectAnimator.ofFloat(view, View.SCALE_X, 0.2f, 1.4f);
        ObjectAnimator scY1 = ObjectAnimator.ofFloat(view, View.SCALE_Y, 0.2f, 1.4f);
        ObjectAnimator rot = ObjectAnimator.ofFloat(view, View.ROTATION, 0f, 360f);
        a1.setDuration(DURATION_GROW);
        scX1.setDuration(DURATION_GROW);
        scY1.setDuration(DURATION_GROW);
        rot.setDuration(DURATION_GROW + DURATION_DELAY + DURATION_SHRINK + DURATION_FADE);

        a1.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                ObjectAnimator scX2 = ObjectAnimator.ofFloat(view, View.SCALE_X, 1.4f, 1.2f);
                ObjectAnimator scY2 = ObjectAnimator.ofFloat(view, View.SCALE_Y, 1.4f, 1.2f);
                scX2.setDuration(DURATION_SHRINK);
                scY2.setDuration(DURATION_SHRINK);
                scX2.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        ObjectAnimator a2 = ObjectAnimator.ofFloat(view, View.ALPHA, 1f, 0f);
                        a2.setDuration(DURATION_FADE);
                        a2.addListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                super.onAnimationEnd(animation);
                                view.setImageDrawable(null);
                                view.setVisibility(View.VISIBLE);
                            }
                        });
                        a2.start();
                    }
                });
                scX2.start();
                scY2.start();
            }
        });
        a1.start();
        scX1.start();
        scY1.start();
        rot.start();
    }*/
