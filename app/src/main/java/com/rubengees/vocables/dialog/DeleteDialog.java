package com.rubengees.vocables.dialog;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;

import com.afollestad.materialdialogs.MaterialDialog;
import com.rubengees.vocables.R;

/**
 * A Dialog to confirm deletion of all Vocables.
 *
 * @author Ruben Gees
 */
public class DeleteDialog extends DialogFragment {

    private DeleteDialogCallback callback;

    public static DeleteDialog newInstance() {
        return new DeleteDialog();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        MaterialDialog.Builder builder = new MaterialDialog.Builder(getActivity());

        builder.title(getString(R.string.dialog_delete_title)).content(getString(R.string.dialog_delete_content))
                .positiveText(getString(R.string.dialog_delete_ok)).negativeText(getString(R.string.dialog_cancel)).callback(new MaterialDialog.ButtonCallback() {
            @Override
            public void onPositive(MaterialDialog dialog) {
                super.onPositive(dialog);

                if (callback != null) {
                    callback.onDelete();
                }
            }
        });

        return builder.build();
    }

    public void setCallback(DeleteDialogCallback callback) {
        this.callback = callback;
    }

    public interface DeleteDialogCallback {
        void onDelete();
    }
}
