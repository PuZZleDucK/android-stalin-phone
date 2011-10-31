package com.puzzleduck.StalinPhone;

import java.io.File;
import java.io.IOException;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.PendingIntent.CanceledException;
import android.content.Intent;
import android.media.MediaRecorder;
import android.os.Binder;
import android.os.Bundle;
import android.os.Environment;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

public class StalinStopRecService extends Service {

	public MediaRecorder recorder = new MediaRecorder();
    private NotificationManager mNM;

    @Override
    public void onCreate() {
    	Log.d("StalinPhone ::: ", "starting stop-rec service...");
        mNM = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        showNotification();
//    	Log.d("StalinPhone ::: ", "show notification success");

        Thread thr = new Thread(null, mTask, "StalinTransService");
        
        //need to recover running thread here 
        //Thread thr = 
        
    	Log.d("StalinPhone ::: ", "starting stop-recording thread");

        thr.start();
    }

    @Override
    public void onDestroy() {
        // Cancel the notification -- we use the same ID that we had used to start it
        mNM.cancel(R.string.stalin_rec_service_started);

        // Tell the user we stopped.
        Toast.makeText(this, R.string.stalin_rec_service_stopped, Toast.LENGTH_SHORT).show();
    }

    /**
     * The function that runs in our worker thread
     */
    Runnable mTask = new Runnable() {
        public void run() {

			Log.d("DEBUG", "STALINphone ::: Idle - pre-STOP REC ");
			recorder.stop();
			Log.d("DEBUG", "STALINphone ::: Idle - STOP REC ");
			recorder.release();
			Log.d("DEBUG", "STALINphone ::: stop self (rec thread)");
			StalinStopRecService.this.stopSelf();
        
        }
    };//Runnable
	
    private void showNotification() {
        // In this sample, we'll use the same text for the ticker and the expanded notification
        CharSequence text = getText(R.string.stalin_rec_service_started);

        // Set the icon, scrolling text and timestamp
        Notification notification = new Notification(R.drawable.icon, text,
                System.currentTimeMillis());

        // The PendingIntent to launch our activity if the user selects this notification
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, StalinStopRecService.class), 0);

        // Set the info for the views that show in the notification panel.
        notification.setLatestEventInfo(this, getText(R.string.stalin_rec_service_label),
                       text, contentIntent);

        // Send the notification.
        // We use a layout id because it is a unique number.  We use it later to cancel.
        mNM.notify(R.string.stalin_rec_service_started, notification);
    }//showNotification

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
    };//IBinder

	
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return mBinder;
	}//ibinder

}//clAss
