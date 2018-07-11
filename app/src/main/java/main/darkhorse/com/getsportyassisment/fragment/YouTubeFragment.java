package main.darkhorse.com.getsportyassisment.fragment;

import android.util.Log;
import android.view.View;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;

import main.darkhorse.com.getsportyassisment.custom_classes.Config;

/**
 * Created by shekhar on 10/7/18.
 */
public class YouTubeFragment extends YouTubePlayerSupportFragment {

    public void youTubeFragmentInitialize(final String videoId, final YouTubeFragment fragment, final View parent) {

        fragment.initialize(Config.KEY, new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, final YouTubePlayer youTubePlayer, boolean wasRestored) {

                youTubePlayer.setShowFullscreenButton(false);
                if (!wasRestored) {
                    youTubePlayer.loadVideo(videoId);
                }
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

                Log.e("app", youTubeInitializationResult.toString());
            }
        });
    }
}