package com.falcon.restaurants.screens.common.dialogs;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;

public class ServerErrorDialogFragment extends BaseDialog {

    static final String ERROR = "ERROR";

    public ServerErrorDialogFragment() {}

    public static ServerErrorDialogFragment newInstance(String error) {
        ServerErrorDialogFragment fragment = new ServerErrorDialogFragment();
        Bundle args = new Bundle();
        args.putString(ERROR, error);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        String error = getArguments().getString(ERROR);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setTitle("Server Error");
        alertDialogBuilder.setMessage("Error: " + error);
        alertDialogBuilder.setPositiveButton("OK",  new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // on success
            }
        });

        alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });
        return alertDialogBuilder.create();
    }

}