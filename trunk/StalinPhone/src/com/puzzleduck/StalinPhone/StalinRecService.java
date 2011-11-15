package com.puzzleduck.StalinPhone;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.Binder;
import android.os.Environment;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ToggleButton;

public class StalinRecService extends Service {
    private NotificationManager mNM;
    public static Date now;
    
    @Override
    public void onCreate() {
//    	Log.d("StalinPhone ::: ", "starting REC service...");
        mNM = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        // show the icon in the status bar
        showNotification();

        // Start up the thread running the service.
        Thread thr = new Thread(null, mTask, "StalinRecService");
//    	Log.d("StalinPhone ::: ", "starting REC service thread");
        thr.start();
    }

    @Override
    public void onDestroy() {
    }

    /**
     * The function that runs in our worker thread
     */
    Runnable mTask = new Runnable() {
        public void run() {

//        	
//            Log.d("StalinPhone ::: ", " Routing: " );
//        	
//            AudioManager aman = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
//            Log.d("StalinPhone ::: ", " in call Routing: " + aman.getRouting(AudioManager.ROUTE_ALL));
//            Log.d("StalinPhone ::: ", " in call Routing: " + aman.getRouting(AudioManager.ROUTE_BLUETOOTH));
//            Log.d("StalinPhone ::: ", " in call Routing: " + aman.getRouting(AudioManager.ROUTE_BLUETOOTH_A2DP));
//            Log.d("StalinPhone ::: ", " in call Routing: " + aman.getRouting(AudioManager.ROUTE_EARPIECE));
//            Log.d("StalinPhone ::: ", " in call Routing: " + aman.getRouting(AudioManager.ROUTE_HEADSET));
//            Log.d("StalinPhone ::: ", " in call Routing: " + aman.getRouting(AudioManager.ROUTE_SPEAKER));
//
//            Log.d("StalinPhone ::: ", " in call aud-params: " + aman.getParameters(AUDIO_SERVICE));
//            Log.d("StalinPhone ::: ", " in call tel-params: " + aman.getParameters(TELEPHONY_SERVICE));
//            Log.d("StalinPhone ::: ", " in call mode: " + aman.getMode());
//          (MODE_NORMAL, MODE_RINGTONE, MODE_IN_CALL or MODE_IN_COMMUNICATION)

//            aman.setRouting(MODE_APPEND, CONTEXT_IGNORE_SECURITY, START_CONTINUATION_MASK);
            
        	TelephonyManager phoneManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        	
        	StalinRecService.now = new Date();
        	String myFileName = Environment.getExternalStorageDirectory().getAbsolutePath();
            myFileName += "/StalinPhone/me-"
              		 + (StalinRecService.now.getYear() + 1900)  + "-"
           		 + StalinRecService.now.getMonth() + "-"
           		 + StalinRecService.now.getDay()  + "--"
           		 + StalinRecService.now.getHours()  + "-"
           		 + StalinRecService.now.getMinutes() + "-" 
           		 + StalinRecService.now.getSeconds() +  ".3gp";

            Log.d("DEBUG", "STALINphone ::: creating myAudioRecorder for:" + myFileName );
        	MediaRecorder myAudioRecorder = new MediaRecorder();
        	myAudioRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);//only mic working
        	myAudioRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        	myAudioRecorder.setOutputFile(myFileName);
        	myAudioRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        	
        	try {
				myAudioRecorder.prepare();
			} catch (IllegalStateException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

//            Log.d("DEBUG", "STALINphone ::: about to start my rec" );

        	myAudioRecorder.start();
        	
        	
        	int i= 0;
            while ( ! (phoneManager.getCallState() == TelephonyManager.CALL_STATE_IDLE)) {
//            	i++;
//            	if(i==500)
//            	{
//            		i=0; 
//            		Log.d("DEBUG", "STALINphone ::: phoneManager.getCallState(): " + phoneManager.getCallState() );
//            	} 
			}
        	
        	myAudioRecorder.stop();
//            Log.d("DEBUG", "STALINphone ::: stopped my rec" );
        	myAudioRecorder.release();
        	

            // Cancel the notification -- we use the same ID that we had used to start it
            mNM.cancel(R.string.stalin_rec_service_started);


//            Log.d("StalinPhone ::: ", " end call Routing: " + aman.getRouting(AudioManager.ROUTE_ALL));
//            Log.d("StalinPhone ::: ", " end call Routing: " + aman.getRouting(AudioManager.ROUTE_BLUETOOTH));
//            Log.d("StalinPhone ::: ", " end call Routing: " + aman.getRouting(AudioManager.ROUTE_BLUETOOTH_A2DP));
//            Log.d("StalinPhone ::: ", " end call Routing: " + aman.getRouting(AudioManager.ROUTE_EARPIECE));
//            Log.d("StalinPhone ::: ", " end call Routing: " + aman.getRouting(AudioManager.ROUTE_HEADSET));
//            Log.d("StalinPhone ::: ", " end call Routing: " + aman.getRouting(AudioManager.ROUTE_SPEAKER));
//        	
//            Log.d("StalinPhone ::: ", " end call aud-params: " + aman.getParameters(AUDIO_SERVICE));
//            Log.d("StalinPhone ::: ", " end call tel-params: " + aman.getParameters(TELEPHONY_SERVICE));
//            
//            Log.d("StalinPhone ::: ", " in call mode: " + aman.getMode());
//            
            
            StalinRecService.this.stopSelf();
        }//mTask
	            
    };
	
	

	
	
	

    /**
     * This is the object that receives interactions from clients.  See RemoteService
     * for a more complete example.
     */
    private final IBinder mBinder = new Binder() {
        @Override
		protected boolean onTransact(int code, Parcel data, Parcel reply,
		        int flags) throws RemoteException {
            return super.onTransact(code, data, reply, flags);
        }
    };

	
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return mBinder;
	}//ibinder

	
	

	
    /**
     * Show a notification while this service is running.
     */
    private void showNotification() {
        // In this sample, we'll use the same text for the ticker and the expanded notification
        CharSequence text = getText(R.string.stalin_rec_service_started);

        // Set the icon, scrolling text and timestamp
        Notification notification = new Notification(R.drawable.icon, text,
                System.currentTimeMillis());

        // The PendingIntent to launch our activity if the user selects this notification
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, StalinRecService.class), 0);

        // Set the info for the views that show in the notification panel.
        notification.setLatestEventInfo(this, getText(R.string.stalin_rec_service_label),
                       text, contentIntent);

        // Send the notification.
        mNM.notify(R.string.stalin_rec_service_started, notification);
    }

	
}//clAss
