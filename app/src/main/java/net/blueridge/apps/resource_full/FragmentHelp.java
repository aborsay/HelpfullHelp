package net.blueridge.apps.resource_full;

import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.VideoView;

public class FragmentHelp extends Fragment{

    private boolean mPlayingVideo =false;
    // private VideoView mVideoView;
    protected Handler mAudioHandler = new Handler();
    private long mAudioDuration=0;

    public FragmentHelp() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_help, container, false);
        view.findViewById(R.id.helpvideo).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(!mPlayingVideo) {
                    v.findViewById(R.id.helpvideo).setVisibility(ImageView.INVISIBLE);
                    final VideoView mVideoView;
                    mVideoView = (VideoView)getView().findViewById(R.id.videoView);

                    Uri mVideoUri= Uri.parse("android.resource://" + getActivity().getPackageName() + "/"
                            + R.raw.resource_full_help);
                    mPlayingVideo=true;
                    mVideoView.setVisibility(VideoView.VISIBLE);
                    mVideoView.setMediaController(null);
                    mVideoView.setVideoURI(mVideoUri);



                    mVideoView.requestFocus();
                    // mVideoView.setZOrderOnTop(true);
                    mVideoView.seekTo(10);

                    mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

                        @Override
                        public void onPrepared(MediaPlayer mp) {
                            mp.start();
                            mVideoView.setZOrderOnTop(false);
                            mVideoView.setBackgroundColor(Color.TRANSPARENT);
                            mAudioDuration = mp.getDuration();
                            mAudioHandler.postDelayed(runAfterVideo, mAudioDuration + 500);
                        }
                    });

                }
            }
        });
        return view;
    }



// do something

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    private Runnable runAfterVideo = new Runnable(){
        @Override
        public void run(){

            getView().findViewById(R.id.helpvideo).setVisibility(ImageView.VISIBLE);
            mPlayingVideo = false;

            /*  */
        }
    };
}
