package com.android.bigserj;


import android.app.Activity;
import android.widget.Toast;

public class ToastMessage {

    // всплывающее сообщение
    public static void showToast(Activity someActivity, String text) {
        Toast.makeText(someActivity, text, Toast.LENGTH_SHORT).show();
    }

}
