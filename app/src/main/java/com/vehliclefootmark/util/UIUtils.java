package com.vehliclefootmark.util;


import android.app.ProgressDialog;
import android.content.Context;

public class UIUtils {

    static ProgressDialog dialog;

    public static void showProgressDialog(Context context, String message) {
        cancelProgressDialog();
        dialog = new ProgressDialog(context);
        dialog.setIndeterminate(true);
        dialog.setMessage(message);
        dialog.setCancelable(false);
        dialog.show();
    }

    public static void cancelProgressDialog() {
        try {
            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
                dialog = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
