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

public class StalinRecService extends Service {

	public MediaRecorder recorder = new MediaRecorder();
    private NotificationManager mNM;

    @Override
    public void onCreate() {
    	Log.d("StalinPhone ::: ", "starting rec service...");
        mNM = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);

        // show the icon in the status bar
        showNotification();

//    	Log.d("StalinPhone ::: ", "show notification success");

        // Start up the thread running the service.  Note that we create a
        // separate thread because the service normally runs in the process's
        // main thread, which we don't want to block.
//        this.getApplicationContext().
        Thread thr = new Thread(null, mTask, "StalinTransService");
    	Log.d("StalinPhone ::: ", "starting recording thread");
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


//cant do this here?
//    		Bundle extras = intent.getExtras();
//			String state = extras.getString(TelephonyManager.EXTRA_STATE);
        		
				
				//http://www.androidtutorials.org/
				String mediaState = android.os.Environment
						.getExternalStorageState();
				if (!mediaState
						.equals(android.os.Environment.MEDIA_MOUNTED)) {
					try {
						throw new IOException(
								"STALINphone ::: SD Card is not mounted. It is "
										+ mediaState + ".");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

				// make sure the directory we plan to store the recording in exists
				//				  File directory = new File(path).getParentFile();
//				File myNewFolder = new File(
//						Environment.getExternalStorageDirectory()
//								+ "StalinPhone");
//				myNewFolder.mkdir();

				//					Log.d("DEBUG", "STALINphone ::: proposed-directory: " + myNewFolder + "/rec01.3gp");

				File directory = new File(Environment.getExternalStorageDirectory() + "/StalinPhone/rec"+System.currentTimeMillis()+".mp4");//.getParentFile();
				
				//					Log.d("DEBUG", "STALINphone ::: directory: " + directory.getAbsolutePath());
				//				  if (!directory.exists() && !directory.mkdirs()) {
				//					  try {
				//						throw new IOException("STALINphone ::: Path to file could not be created.");
				//					} catch (IOException e) {
				//						// TODO Auto-generated catch block
				//						e.printStackTrace();
				//					}
				//				  }
				Log.d("DEBUG", "STALINphone ::: directory: " + directory.getPath());
				//					Log.d("DEBUG", "STALINphone ::: audio sources: " + MediaRecorder.getAudioSourceMax());
				//					  recorder.setAudioSource(MediaRecorder.AudioSource.VOICE_CALL);
				recorder.setAudioSource(MediaRecorder.AudioSource.VOICE_CALL);
				Log.d("DEBUG",
						"STALINphone ::: audio source set - MAX AMP: "
								+ recorder.getMaxAmplitude());
				recorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
				Log.d("DEBUG", "STALINphone ::: audio format set: ");
				recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
				Log.d("DEBUG", "STALINphone ::: audio encoder set: ");

				//					ContentResolver contentResolver = new ContentResolver();
				//					ContentValues values = new ContentValues(3);
				//					values.put(MediaStore.MediaColumns.TITLE, SOME_NAME_HERE);

				//					Uri base = MediaStore.Audio.Media.INTERNAL_CONTENT_URI;
				//					Uri newUri = contentResolver.insert(base, values);
				//					String path = contentResolver.getDataFilePath(newUri);
				//					ContentResolver.

				recorder.setOutputFile( directory.getPath()  );
				Log.d("DEBUG", "STALINphone ::: audio file set: " + directory.getPath());

				try {
					recorder.prepare();
					Log.d("DEBUG", "STALINphone ::: prepare: ");
				} catch (IllegalStateException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				recorder.start();
				Log.d("DEBUG", "STALINphone ::: started: ");

				//				String phoneNumber = extras
				//						.getString(TelephonyManager.EXTRA_INCOMING_NUMBER);

        
				
				
//MOVED TO STOP_REC_SERVICE				
//	            // Normally we would look for the end of the call, but for now:
//	            // just sleep for 30 seconds.
//				long endTime = System.currentTimeMillis() + 15*1000;
//	            while (System.currentTimeMillis() < endTime) {
//	                synchronized (mBinder) {
//	                    try {
//	                        mBinder.wait(endTime - System.currentTimeMillis());
//	                    } catch (Exception e) {
//	                    }
//	                }
//	            }
//	            //TelephonyManager.EXTRA_STATE

//				Log.d("DEBUG", "STALINphone ::: get (public) state: " + StalinReceiver.state);
//	            while (StalinReceiver.state == null) {
//				}

	            Log.d("DEBUG", "STALINphone ::: get (public) state: " + StalinReceiver.state);


				long endTime = System.currentTimeMillis() + 15*1000;
	            while (System.currentTimeMillis() < endTime) {
                synchronized (mBinder) {
                    try {
                        mBinder.wait(endTime - System.currentTimeMillis());
                    } catch (Exception e) {
                    }
                }
            }
	            

//				//trying wait instead.. not realy
//	            try {
//					this.wait();
//				} catch (InterruptedException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
				
	            
	            //CLOSE... gets the timing right, but no recording :(
//	            while (! StalinReceiver.state.equals("IDLE")) {
//		            Log.d("DEBUG", "STALINphone ::: get (public) state: " + StalinReceiver.state);
//						//.getIntent().getExtras().getString(TelephonyManager.EXTRA_STATE).equals("IDLE") ) {
//						//	                synchronized (mBinder) {
//						//	                    try {
//						////	                        mBinder.wait(endTime - System.currentTimeMillis());
//						//	                    } catch (Exception e) {
//						//	                    }
//						//	                }
//					}
					//				
					//				
					//				
					//				
					//				
					//				
					//        
					//				 // Done with our work...  stop the service/recording!
					//				recorder.stop();
					//				Log.d("DEBUG", "STALINphone ::: Idle - STOP REC ");
					//				recorder.release();
					//				Log.d("DEBUG", "STALINphone ::: Idle - RELEASE REC ");
					//
					//				Log.d("StalinPhone ::: ", "recording service terminating...");
					//			    
					//			    StalinRecService.this.stopSelf();

	            	
	            	// Done with our work...  stop the service/recording!
//			Log.d("DEBUG", "STALINphone ::: about to end recording");
//				recorder.stop();
////				Log.d("DEBUG", "STALINphone ::: Idle - STOP REC ");
//				recorder.release();
//				Log.d("DEBUG", "STALINphone ::: stop self (rec thread)");
//			    StalinRecService.this.stopSelf();


        
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
        // We use a layout id because it is a unique number.  We use it later to cancel.
        mNM.notify(R.string.stalin_rec_service_started, notification);
    }

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

	
}//clAss
