package com.example.admin.lockscreen;

import android.content.BroadcastReceiver;
        import android.content.Context;
        import android.content.Intent;
        import android.telephony.TelephonyManager;
        import android.util.Log;

/**
 * Created by Admin on 1/12/2018.
 */

public class MyReciver extends BroadcastReceiver {
    private MyService myService;

    public MyReciver(MyService myService) {
        this.myService = myService;
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        Log.e("ACTION", intent.getAction());
        if (intent.getAction().equals("android.intent.action.SCREEN_OFF")) {
            myService.showLayoutLock();
        }
        if(intent.getAction().equals("com.khacchung.startpreviewscreen")){
            myService.showLayoutLock();
        }
        if (intent.getAction().equals("android.intent.action.PHONE_STATE")) {
            if (intent.getStringExtra(TelephonyManager.EXTRA_STATE).equals(TelephonyManager.EXTRA_STATE_IDLE)) {
                myService.showLayoutLock();
            } else if (intent.getStringExtra(TelephonyManager.EXTRA_STATE).equals(TelephonyManager.EXTRA_STATE_RINGING)) {
                myService.removeAllView();
            }
        }
    }
}
