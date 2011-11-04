package com.puzzleduck.StalinPhone;

import java.io.IOException;
import java.util.Date;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaRecorder;
import android.os.Binder;
import android.os.Environment;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

public class StalinRecOtherService extends Service {
//	private MediaRecorder myRecorder;
//	public MediaRecorder otherRecorder;
    private NotificationManager mNM;

    @Override
    public void onCreate() {
    	Log.d("StalinPhone ::: ", "starting otherREC service...");
//        mNM = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
//        // show the icon in the status bar
//        showNotification();

        Thread thr = new Thread(null, mTask, "StalinRecOtherService");
    	Log.d("StalinPhone ::: ", "starting RECother service thread");
        thr.start();
    }

    @Override
    public void onDestroy() {
        // Cancel the notification -- we use the same ID that we had used to start it
//        mNM.cancel(R.string.stalin_rec_service_started);

        // Tell the user we stopped.
//        Toast.makeText(this, R.string.stalin_rec_service_stopped, Toast.LENGTH_SHORT).show();
    }

    /**
     * The function that runs in our worker thread
     */
    Runnable mTask = new Runnable() {
        public void run() {

        	TelephonyManager phoneManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        	
//        	Date now = new Date();
//        	String myFileName = Environment.getExternalStorageDirectory().getAbsolutePath();
        	String otherFileName = Environment.getExternalStorageDirectory().getAbsolutePath();
        	otherFileName += "/StalinPhone/other-"
              		 + (StalinRecService.now.getYear() + 1900)  + "-"
           		 + StalinRecService.now.getMonth() + "-"
           		 + StalinRecService.now.getDay()  + "--"
           		 + StalinRecService.now.getHours()  + "-"
           		 + StalinRecService.now.getMinutes() + "-" 
           		 + StalinRecService.now.getSeconds() +  ".3gp";
//            otherFileName += "/StalinPhone/other-"
//              		 + (now.getYear() + 1900)  + "-"
//           		 + now.getMonth() + "-"
//           		 + now.getDay()  + "--"
//           		 + now.getHours()  + "-"
//           		 + now.getMinutes() + "-" 
//           		 + now.getSeconds() +  ".3gp";

            Log.d("DEBUG", "STALINphone ::: creating otherAudioRecorder" );
//        	MediaRecorder myAudioRecorder = new MediaRecorder();
//        	myAudioRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
//        	myAudioRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
//        	myAudioRecorder.setOutputFile(myFileName);
//        	myAudioRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

//            Log.d("DEBUG", "STALINphone ::: creating otherAudioRecorder" );
        	MediaRecorder otherAudioRecorder = new MediaRecorder();
        	otherAudioRecorder.setAudioSource(MediaRecorder.AudioSource.VOICE_DOWNLINK);
        	otherAudioRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        	otherAudioRecorder.setOutputFile(otherFileName);
        	otherAudioRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        	
        	try {
//				myAudioRecorder.prepare();
				otherAudioRecorder.prepare();
			} catch (IllegalStateException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

//            Log.d("DEBUG", "STALINphone ::: about to start my rec" );

//        	myAudioRecorder.start();
        	
//        	//only one rec allowed... damn
            Log.d("DEBUG", "STALINphone ::: about to start other rec" );
        	otherAudioRecorder.start();
        	
        	
        	
//			long endTime = System.currentTimeMillis() + 15*1000;
//            while (System.currentTimeMillis() < endTime) {
//            synchronized (mBinder) {
//                try {
//                    mBinder.wait(endTime - System.currentTimeMillis());
//                } catch (Exception e) {
//                }
//            }
        	
        	
        	int i= 0;
            while ( ! (phoneManager.getCallState() == TelephonyManager.CALL_STATE_IDLE)) {
            	i++;
            	if(i==1000)
            	{
            		i=0; 
            		Log.d("DEBUG", "STALINphone ::: other phoneManager.getCallState(): " + phoneManager.getCallState() );
                    
            		
            	}
                
			}
        	
        	
        	
        	
        	
        	


//        	myAudioRecorder.stop();
        	otherAudioRecorder.stop();
//        	myAudioRecorder.release();
        	otherAudioRecorder.release();
        	
        	
        	
        	
//				String mediaState = android.os.Environment
//						.getExternalStorageState();
//				if (!mediaState
//						.equals(android.os.Environment.MEDIA_MOUNTED)) {
//					try {
//						throw new IOException(
//								"STALINphone ::: SD Card is not mounted. It is "
//										+ mediaState + ".");
//					} catch (IOException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//				}
//
//				// make sure the directory we plan to store the recording in exists?!?!
//				//				  File directory = new File(path).getParentFile();
////				myNewFolder.mkdir();
//
//				File directory = new File(Environment.getExternalStorageDirectory() + "/StalinPhone/rec"+System.currentTimeMillis()+".3gp");//.getParentFile();
//
//				Log.d("DEBUG", "STALINphone ::: directory: " + directory.getPath());
//				//					Log.d("DEBUG", "STALINphone ::: audio sources: " + MediaRecorder.getAudioSourceMax());
//				//					  recorder.setAudioSource(MediaRecorder.AudioSource.VOICE_CALL);
//				recorder.setAudioSource(MediaRecorder.AudioSource.VOICE_CALL);
//				Log.d("DEBUG",
//						"STALINphone ::: audio source set - MAX AMP: "
//								+ recorder.getMaxAmplitude());
//				recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
//				Log.d("DEBUG", "STALINphone ::: audio format set: ");
//				recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
//				Log.d("DEBUG", "STALINphone ::: audio encoder set: ");
//
//				recorder.setOutputFile( directory.getPath()  );
//				Log.d("DEBUG", "STALINphone ::: audio file set: " + directory.getPath());
//
//				try {
//					recorder.prepare();
//					Log.d("DEBUG", "STALINphone ::: prepare: ");
//				} catch (IllegalStateException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				recorder.start();
//				Log.d("DEBUG", "STALINphone ::: started: ");
//				//				String phoneNumber = extras
//				//						.getString(TelephonyManager.EXTRA_INCOMING_NUMBER);
//
//	            Log.d("DEBUG", "STALINphone ::: get (public) state: " + StalinReceiver.state);

//	            15 sec timer

                
                
//
//    			Log.d("DEBUG", "STALINphone ::: Idle - pre-STOP REC ");
//    			recorder.stop();
//    			Log.d("DEBUG", "STALINphone ::: Idle - STOP REC ");
//    			recorder.release();
//    			Log.d("DEBUG", "STALINphone ::: stop self (rec thread)");
//    			StalinRecService.this.stopSelf();
                
            }//mTask
	            
	            
	            
	            //hope i dont have to use any of this
	            // v   v   v  v   v  v   v   v   v  v
	            
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
					//				 // Done with our work...  stop the service/recording!
					//				recorder.stop();
					//				Log.d("DEBUG", "STALINphone ::: Idle - STOP REC ");
					//				recorder.release();
					//				Log.d("DEBUG", "STALINphone ::: Idle - RELEASE REC ");
					//
					//				Log.d("StalinPhone ::: ", "recording service terminating...");
					//			    
					//			    StalinRecService.this.stopSelf();

//        }
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
                new Intent(this, StalinRecOtherService.class), 0);

        // Set the info for the views that show in the notification panel.
        notification.setLatestEventInfo(this, getText(R.string.stalin_rec_service_label),
                       text, contentIntent);

        // Send the notification.
        // We use a layout id because it is a unique number.  We use it later to cancel.
        mNM.notify(R.string.stalin_rec_service_started, notification);
    }

	
}//clAss