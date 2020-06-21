package com.example.workoutapp.ui;

import android.app.ProgressDialog;
import android.content.Context;


/**
 * This class is for loading dialog.
 * It takes a message to display if there's any.
 */
public class LoadingDialog {

    private ProgressDialog progressDialog;
    private Context context;

    public LoadingDialog(Context context) {
        this.context = context;
    }

    public void show() {
        show(null);
    }

    public void show(String msg) {
        if (context == null)
            return;

        if (msg == null)
            msg = "Loading...";

        if (progressDialog == null) {
            progressDialog = new ProgressDialog(context);

            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setIndeterminate(true);
            progressDialog.setCancelable(false);
        } else if (isShowing()) {
            progressDialog.setMessage(msg);
            return;
        }

        progressDialog.setMessage(msg);
        progressDialog.show();
    }

    public void dismiss() {
        if (progressDialog == null)
            return;

        try {
            if (progressDialog.isShowing())
                progressDialog.dismiss();
        } catch (Exception e) {
        }
    }

    public boolean isShowing() {
        if (progressDialog == null)
            return false;

        return progressDialog.isShowing();
    }

}
