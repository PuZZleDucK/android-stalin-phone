package com.puzzleduck.StalinPhone;

import android.app.PendingIntent;
import android.app.PendingIntent.CanceledException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;

public class StalinReceiver extends BroadcastReceiver{
 

    private PendingIntent mTransRequestSender;
    public static String state;
	@Override
	public void onReceive(Context context, Intent intent) {

		Bundle extras = intent.getExtras();
		if (extras != null) {
			state = extras.getString(TelephonyManager.EXTRA_STATE);
			Log.d("DEBUG", "STALINphone ::: State: " + state);
			
			if (state != null) 
			{
				if (state.equals(TelephonyManager.EXTRA_STATE_OFFHOOK)) {
					Log.d("DEBUG", "STALINphone ::: Off the Hook - START REC ");
					//start recording


				    PendingIntent mTransRequestSender;

		            Log.d("StalinPhone ::: ", "creating REC intent");
		            mTransRequestSender = PendingIntent.getService(context,0, new Intent(context, StalinRecService.class), 0);

		            Log.d("StalinPhone ::: ", "REC intent created");
		            try {
		    			mTransRequestSender.send();
			            Log.d("StalinPhone ::: ", "REC intent sent");
		    		} catch (CanceledException e) {
		    			// TODO Auto-generated catch block
		    			e.printStackTrace();
		    		}  
					
				}//EXTRA_STATE_OFFHOOK

			}//if state==null
		}//extras null


	}//onRec
}//class
