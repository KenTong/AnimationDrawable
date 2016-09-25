package com.kenopro.lab08_animationdrawable;

import android.content.res.TypedArray;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private ImageView imageView;
    private View teamLogo;
    private AnimationDrawable m_animatedVectorDrawable;
    private TextView textView,teamName,m_tv_duration;
    private Handler handler = new Handler();
    private TypedArray mNbaLogos , mNbaName;
    private int mNabLogosCount,mNbaNameCount,mDuration;
    private StartRandomTasK mStartRandomTasK = new StartRandomTasK();
    private StopRandomTask mStopRandomTask = new StopRandomTask();
    private Button go;
    private SeekBar m_skb_duration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initFrameAnimation();
        initNbaLogos();
        initSeekBar();
    }
    private void initNbaLogos() {
        mNbaName = getmNbaName();
        mNbaLogos = getmNbaLogos();
        mNabLogosCount = getmNbaLogos().length();
        mNbaNameCount = getmNbaName().length();
        teamLogo.setBackground(mNbaLogos.getDrawable(0));

    }
    private TypedArray getmNbaLogos() {
        TypedArray logos = getResources().obtainTypedArray(R.array.nba_logos);
        return logos;
    }
    private TypedArray getmNbaName() {
        TypedArray names = getResources().obtainTypedArray(R.array.nba_logo_name);
        return names;
    }

    private void initView() {
    imageView = (ImageView)findViewById(R.id.imageView);
    teamLogo = findViewById(R.id.teamLogo);

    textView = (TextView)findViewById(R.id.textView);
    teamName = (TextView)findViewById(R.id.teamName);
    m_tv_duration = (TextView)findViewById(R.id.tv_duration);

    go = (Button)findViewById(R.id.go);

    m_skb_duration = (SeekBar)findViewById(R.id.skb_duration);

}
    private void initSeekBar() {
        m_tv_duration.setText(String.valueOf(mDuration));
        m_skb_duration.setMax(20);
        m_skb_duration.setOnSeekBarChangeListener(new seekListener());
    }
    private class seekListener implements SeekBar.OnSeekBarChangeListener {

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
            int duration = progress * 50;
            m_tv_duration.setText(String.valueOf(duration));
            mDuration = duration;
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
            //m_tv_duration.setText("Start");
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            //m_tv_duration.setText("End");
        }
    }
    private void initFrameAnimation() {
        imageView.setBackgroundResource(R.drawable.frame_animation);
        m_animatedVectorDrawable =  (AnimationDrawable) imageView.getBackground();
    }
    private class Task implements Runnable{

        @Override
        public void run() {
            m_animatedVectorDrawable.stop();
            textView.setText("時間到");
        }
    }
    private void animation5Sec() {
    int delaymillis = 5 * 1000;
    Runnable task = new Task();
        boolean result = handler.postDelayed(task , delaymillis);

        textView.setText(result? "交付成功":"交付失敗");
        m_animatedVectorDrawable.start();

    }
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_start:
                m_animatedVectorDrawable.start();
                break;
            case  R.id.btn_stop:
                m_animatedVectorDrawable.stop();
                break;
            case R.id.btn_5Sec:
                animation5Sec();
                break;
        }
    }

    private class StartRandomTasK implements Runnable {

        @Override
        public void run() {
            int index = (int)(Math.random() * mNabLogosCount);
            teamLogo.setBackground(mNbaLogos.getDrawable(index));
            teamName.setText(mNbaName.getText(index));
            handler.postDelayed(this,mDuration);
        }
    }
    private class StopRandomTask implements Runnable {

        @Override
        public void run() {
            handler.removeCallbacks(mStartRandomTasK);
            go.setEnabled(true);


        }
    }
    public void random_logo(View view){
        handler.post(mStartRandomTasK);
        handler.postDelayed(mStopRandomTask,5000);
        go.setEnabled(false);
    }

}
