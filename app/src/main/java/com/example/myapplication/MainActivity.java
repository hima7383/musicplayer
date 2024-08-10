package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    ImageButton play,prvSong,nextSong;
    TextView timePassed,totalTime;
    SeekBar songProgress;
    MediaPlayer music;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        play=findViewById(R.id.playMusic);
        prvSong=findViewById(R.id.prevSong);
        nextSong=findViewById(R.id.nextSong);
        timePassed=findViewById(R.id.timePassed);
        totalTime=findViewById(R.id.totalTime);
        songProgress=findViewById(R.id.songProgress);
        music=MediaPlayer.create(MainActivity.this,R.raw.centralcee);
        music.seekTo(0);

        String duration=calcTime(music.getDuration());
        totalTime.setText(duration);
        //music.setVolume(0.5f,0.5f);
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(music.isPlaying()){
                    play.setBackgroundResource(R.drawable.play);
                    music.pause();
                }
                else{
                    play.setBackgroundResource(R.drawable.pu);
                    music.start();
                }
            }
        });
        songProgress.setMax(music.getDuration());
        songProgress.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean isFromUser) {
                if(isFromUser){
                    music.seekTo(progress);
                    seekBar.setProgress(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

new Thread(new Runnable() {
    @Override
    public void run() {
        while (music!=null){
            {
                try {
                    final double cur=music.getCurrentPosition();
                    final String timeGone=calcTime((int)cur);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            timePassed.setText(timeGone);
                            songProgress.setProgress((int)cur);
                        }
                    });
                    //Thread.sleep(1000);
                }catch (Exception e){}
            }
        }
    }
}).start();
    }
    public String calcTime(int time){
        String result="";
        int minutes=time/1000/60;
        int seconds=time/1000%60;
        result+=minutes+":";
        if(seconds<=9)result+="0";
        result+=seconds;
        return result;
    }
}

