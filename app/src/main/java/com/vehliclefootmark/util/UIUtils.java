package com.vehliclefootmark.util;


import android.app.ProgressDialog;
import android.content.Context;
import android.icu.util.Calendar;
import android.text.format.DateFormat;

import java.text.SimpleDateFormat;

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

    public static String convertMillsToDate(long millis){
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");

        System.out.println(millis);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millis);
        return formatter.format(calendar.getTime());
    }
}
