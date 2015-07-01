package com.rubengees.vocables.dialog;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;

import com.afollestad.materialdialogs.MaterialDialog;
import com.rubengees.vocables.R;
import com.rubengees.vocables.core.Core;
import com.rubengees.vocables.core.GoogleServiceConnection;

/**
 * A dialog to interact with the PlayGames Service.
 *
 * @author Ruben Gees
 */
public class PlayGamesDialog extends DialogFragment {

    private PlayGamesDialogCallback callback;

    public static PlayGamesDialog newInstance() {
        return new PlayGamesDialog();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        MaterialDialog.Builder builder = new MaterialDialog.Builder(getActivity());
        GoogleServiceConnection connection = Core.getInstance(getActivity()).getConnection();

        builder.title(getString(R.string.dialog_play_games_title));
        if (connection.isConnected()) {
            builder.positiveText(getString(R.string.dialog_play_games_sign_out))
                    .neutralText(getString(R.string.dialog_play_games_show_achievements)).callback(new MaterialDialog.ButtonCallback() {
                @Override
                public void onPositive(MaterialDialog dialog) {
                    if (callback != null) {
                        callback.onSignOut();
                    }
                }

                @Override
                public void onNeutral(MaterialDialog dialog) {
                    if (callback != null) {
                        callback.onShowAchievements();
                    }
                }
            }).content(getString(R.string.dialog_play_games_content_singed_in));
        } else {
            builder.positiveText(getString(R.string.dialog_play_games_sign_in)).callback(new MaterialDialog.ButtonCallback() {
                @Override
                public void onPositive(MaterialDialog dialog) {
                    if (callback != null) {
                        callback.onSignIn();
                    }
                }
            }).content(getString(R.string.dialog_play_games_content_signed_out));
        }
        builder.negativeText(getString(R.string.dialog_cancel));

        return builder.build();
    }

    public void setCallback(PlayGamesDialogCallback callback) {
        this.callback = callback;
    }

    public interface PlayGamesDialogCallback {
        void onSignIn();

        void onSignOut();

        void onShowAchievements();
    }
}
