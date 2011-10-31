package com.puzzleduck.StalinPhone;

import java.io.File;
import java.io.IOException;

import android.app.PendingIntent;
import android.app.PendingIntent.CanceledException;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.AdapterView.OnItemClickListener;

public class StalinReceiver extends BroadcastReceiver{
 

    private PendingIntent mTransRequestSender;
    public static String state;
	@Override
	public void onReceive(Context context, Intent intent) {

//		Bundle extraData = intent.getExtras();
//		if(extraData != null)
//		{
//			String state = extraData.getString(TelephonyManager.EXTRA_STATE);
//			Log.w("DEBUG", "StalinDEBUG: State = " + state);
//			if(state.equals(TelephonyManager.EXTRA_STATE_RINGING))
//			{
//				String inNumber = extraData.getString(TelephonyManager.EXTRA_INCOMING_NUMBER);
//				Log.w("DEBUG", "StalinDEBUG: inNumber = " + inNumber);
//			}
//			
//			
//		}//if
		//props: http://www.vogella.de/articles/AndroidServices/article.html
		Bundle extras = intent.getExtras();
		if (extras != null) {
			state = extras.getString(TelephonyManager.EXTRA_STATE);
			Log.d("DEBUG", "STALINphone ::: State: " + state);
			
			if (state != null) 
			{


				if (state.equals(TelephonyManager.EXTRA_STATE_RINGING)) {
					Log.d("DEBUG", "STALINphone ::: Ringing ");
					//				String phoneNumber = extras
					//						.getString(TelephonyManager.EXTRA_INCOMING_NUMBER);
				}//EXTRA_STATE_RINGING
				if (state.equals(TelephonyManager.EXTRA_STATE_OFFHOOK)) {
					Log.d("DEBUG", "STALINphone ::: Off the Hook - START REC ");
					//start recording

					//this all needs to go in a service!!! BIG TIME
					//this receiver is never terminating... properly
					//therefore the new intent kills this one before stop and release are called
				

			
			//call recievers for processing/recording
				//NOW USING INTENTS
		        Log.d("StalinPhone ::: ", "creating recording intent");

		        mTransRequestSender = PendingIntent.getService(context,
		        		0, new Intent(context, StalinRecService.class), 0);

		        Log.d("StalinPhone ::: ", "recording intent created");
		        try {
					mTransRequestSender.send();
				} catch (CanceledException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//INTENT

				}//EXTRA_STATE_OFFHOOK

				if (state.equals(TelephonyManager.EXTRA_STATE_IDLE)) {
					//stop recording ... start dictation service
					//then when complete dictation service will start "review" UI

					
					
//					StalinRecService.this.notify();
//					
//					//NOW USING INTENTS
//			        Log.d("StalinPhone ::: ", "creating stop-recording intent");
//
//			        mTransRequestSender = PendingIntent.getService(context,
//			        		0, new Intent(context, StalinStopRecService.class), 0);
//
//			        Log.d("StalinPhone ::: ", "stop-recording intent created");
//			        try {
//						mTransRequestSender.send();
//					} catch (CanceledException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//					//INTENT
					
					
					//check that we are still running!!
					//not sure i can???


					//				String phoneNumber = extras
					//						.getString(TelephonyManager.EXTRA_INCOMING_NUMBER);

				}//EXTRA_STATE_IDLE

	        	
	        	
	        	
	        	
	        	
	        	
	            
				
				
			
			}//if state==null
		}


	}//onRec
}//class
