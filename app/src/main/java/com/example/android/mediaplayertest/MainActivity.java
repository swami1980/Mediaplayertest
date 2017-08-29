package com.example.android.mediaplayertest;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import static android.media.AudioManager.AUDIOFOCUS_GAIN;
import static com.example.android.mediaplayertest.R.id.b1;
import static com.example.android.mediaplayertest.R.layout.activity_main;

public class MainActivity extends AppCompatActivity   {
Activity context= this;
     static MediaPlayer mPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(activity_main);
        releaseMediaPlayer();
        mPlayer = MediaPlayer.create(context, R.raw.sample);
       final  AudioManager am = (AudioManager) MainActivity.this.getSystemService(Context.AUDIO_SERVICE);
       final AudioManager.OnAudioFocusChangeListener afChangeListener =
                new AudioManager.OnAudioFocusChangeListener() {
                    /*
                     * @param focusChange the type of focus change, one of {@link AudioManager#AUDIOFOCUS_GAIN},
                     *                    {@link AudioManager#AUDIOFOCUS_LOSS}, {@link AudioManager#AUDIOFOCUS_LOSS_TRANSIENT}
                     *                    and {@link AudioManager#AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK}.
                     */
                    @Override
                    public void onAudioFocusChange(int focusChange) {
                        if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                            mPlayer.pause();

                        } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT) {
                            mPlayer.pause();

                        } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN)

                        {
                            mPlayer.start();
                        }
                    }
                };

        Button b = (Button) findViewById(b1);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



// Request audio focus for playback
                int result = am.requestAudioFocus(afChangeListener,
                        // Use the music stream.
                        AudioManager.STREAM_MUSIC,
                        // Request permanent focus.
                        AUDIOFOCUS_GAIN);

                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                mPlayer.start();
                mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        releaseMediaPlayer();
                        Toast.makeText(MainActivity.this, "Song over",
                                Toast.LENGTH_LONG).show();
                    }
                });
                }
            }
        });

        Button b1 = (Button) findViewById(R.id.b2);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              mPlayer.pause();
            }
        });

        }


    private void releaseMediaPlayer() {
        // If the media player is not null, then it may be currently playing a sound.
        if (mPlayer != null) {
            // Regardless of the current state of the media player, release its resources
            // because we no longer need it.
            mPlayer.release();

            // Set the media player back to null. For our code, we've decided that
            // setting the media player to null is an easy way to tell that the media player
            // is not configured to play an audio file at the moment.
            mPlayer = null;
        }
    }
}
