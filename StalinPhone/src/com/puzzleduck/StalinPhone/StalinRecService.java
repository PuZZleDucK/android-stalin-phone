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


//<identifier id="android.graphics.NinePatch"/>
// nine patch already implimented :) nice.

//might be fun to play with
//<identifier id="android.media.ToneGenerator.TONE_CDMA_ABBR_ALERT

//<identifier id="android.media.AudioManager.getRouting_changed(int)"/>
//<identifier id="android.media.AudioManager.setRouting_changed(int, int, int)"/>
//<identifier id="android.media.MediaRecorder.getAudioSourceMax_added()"/>

//<identifier id="android.telephony"/>
//<identifier id="android.telephony.TelephonyManager"/>


public class StalinRecService extends Service {
//	private MediaRecorder myRecorder;
//	public MediaRecorder otherRecorder;
//	AudioManager aman = (AudioManager) this.getSystemService(Context.AUDIO_SERVICE);
	
	
    private NotificationManager mNM;
    public static Date now;
    @Override
    public void onCreate() {
    	Log.d("StalinPhone ::: ", "starting REC service...");
        mNM = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        // show the icon in the status bar
        showNotification();

        // Start up the thread running the service.  Note that we create a
        // separate thread because the service normally runs in the process's
        // main thread, which we don't want to block.

        Thread thr = new Thread(null, mTask, "StalinRecService");
    	Log.d("StalinPhone ::: ", "starting REC service thread");
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

        	
        	
        	
        	
        	
        	
            Log.d("StalinPhone ::: ", " Routing: " );
        	
            AudioManager aman = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
            Log.d("StalinPhone ::: ", " in call Routing: " + aman.getRouting(AudioManager.ROUTE_ALL));
            Log.d("StalinPhone ::: ", " in call Routing: " + aman.getRouting(AudioManager.ROUTE_BLUETOOTH));
            Log.d("StalinPhone ::: ", " in call Routing: " + aman.getRouting(AudioManager.ROUTE_BLUETOOTH_A2DP));
            Log.d("StalinPhone ::: ", " in call Routing: " + aman.getRouting(AudioManager.ROUTE_EARPIECE));
            Log.d("StalinPhone ::: ", " in call Routing: " + aman.getRouting(AudioManager.ROUTE_HEADSET));
            Log.d("StalinPhone ::: ", " in call Routing: " + aman.getRouting(AudioManager.ROUTE_SPEAKER));

            Log.d("StalinPhone ::: ", " in call aud-params: " + aman.getParameters(AUDIO_SERVICE));
            Log.d("StalinPhone ::: ", " in call tel-params: " + aman.getParameters(TELEPHONY_SERVICE));
            Log.d("StalinPhone ::: ", " in call mode: " + aman.getMode());
//          (MODE_NORMAL, MODE_RINGTONE, MODE_IN_CALL or MODE_IN_COMMUNICATION)

            aman.setRouting(MODE_APPEND, CONTEXT_IGNORE_SECURITY, START_CONTINUATION_MASK);
            
//            public void setMode (int mode)
//            Since: API Level 1
//
//            Sets the audio mode.
//
//            The audio mode encompasses audio routing AND the behavior of the telephony layer. Therefore this method should only be used by applications that replace the platform-wide management of audio settings or the main telephony application. In particular, the MODE_IN_CALL mode should only be used by the telephony application when it places a phone call, as it will cause signals from the radio layer to feed the platform mixer.
//            Parameters
//            mode 	the requested audio mode (MODE_NORMAL, MODE_RINGTONE, MODE_IN_CALL or MODE_IN_COMMUNICATION). Informs the HAL about the current audio state so that it can route the audio appropriately. 

            
//            public void setRouting (int mode, int routes, int mask)
//            Since: API Level 1
//
//            This method is deprecated.
//            Do not set audio routing directly, use setSpeakerphoneOn(), setBluetoothScoOn() methods instead.
//
//            Sets the audio routing for a specified mode
//            Parameters
//            mode 	audio mode to change route. E.g., MODE_RINGTONE.
//            routes 	bit vector of routes requested, created from one or more of ROUTE_xxx types. Set bits indicate that route should be on
//            mask 	bit vector of routes to change, created from one or more of ROUTE_xxx types. Unset bits indicate the route should be left unchanged
            
            
//            int 	STREAM_ALARM 	The audio stream for alarms
//            int 	STREAM_DTMF 	The audio stream for DTMF Tones
//            int 	STREAM_MUSIC 	The audio stream for music playback
//            int 	STREAM_NOTIFICATION 	The audio stream for notifications
//            int 	STREAM_RING 	The audio stream for the phone ring
//            int 	STREAM_SYSTEM 	The audio stream for system sounds
//            int 	STREAM_VOICE_CALL 	The audio stream for phone calls 
            
            
            
            
            
//        	public void setRouting (int mode, int routes, int mask)
//        	Sets the audio routing for a specified mode
//        	Parameters
//        	mode 	audio mode to change route. E.g., MODE_RINGTONE.
//        	routes 	bit vector of routes requested, created from one or more of ROUTE_xxx types. Set bits indicate that route should be on
//        	mask 	bit vector of routes to change, created from one or more of ROUTE_xxx types. Unset bits indicate the route should be left unchanged
        	
//        	public void setParameters (String keyValuePairs)
//        	Sets a variable number of parameter values to audio hardware.
//        	Parameters
//        	keyValuePairs 	list of parameters key value pairs in the form: key1=value1;key2=value2;...
        	
//        	public void setMode (int mode)
//        	Sets the audio mode.
//        	The audio mode encompasses audio routing AND the behavior of the telephony layer. Therefore this method should only be used by applications that replace the platform-wide management of audio settings or the main telephony application. In particular, the MODE_IN_CALL mode should only be used by the telephony application when it places a phone call, as it will cause signals from the radio layer to feed the platform mixer.
//        	Parameters
//        	mode 	the requested audio mode (MODE_NORMAL, MODE_RINGTONE, MODE_IN_CALL or MODE_IN_COMMUNICATION). Informs the HAL about the current audio state so that it can route the audio appropriately. 
        	
//        	public int getRouting (int mode)
//        	Returns the current audio routing bit vector for a specified mode.
//        	Parameters
//        	mode 	audio mode to get route (e.g., MODE_RINGTONE)
//        	Returns
//        	    * an audio route bit vector that can be compared with ROUTE_xxx bits


        	
        	
        	
//          Log.d("StalinPhone ::: ", " StalinPhone commands!!!!: " );

          
          //public Process exec (String prog, String[] envp, File directory)
//          try {
//			Process myCommand = Runtime.getRuntime().exec("/system/xbin/ls", null, null );
//        	try {
//				Thread.sleep(100); //woot.... cant believe that worked :) sweet
//			} catch (InterruptedException e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			}
////    		debugText.append("Running cmd... output: " + myCommand.getInputStream().available());
//
//    		byte[] commandOut = new byte[myCommand.getInputStream().available()];
//    		myCommand.getInputStream().read(commandOut,0,myCommand.getInputStream().available());
////    		debugText.append( "\n\t Output: " + new String(commandOut) );	
//            Log.d("StalinPhone ::: ", "command output: " + new String(commandOut) );
//
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}


        	//hacking into android.media.audiofx.AudioEffect.Descriptor might be usefull... maybe fx 
			//can get to downlink
        	
        	
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
//        	myAudioRecorder.setAudioSource(MediaRecorder.AudioSource.VOICE_DOWNLINK);//only mic working
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

            Log.d("DEBUG", "STALINphone ::: about to start my rec" );

        	myAudioRecorder.start();
        	
        	
        	int i= 0;
            while ( ! (phoneManager.getCallState() == TelephonyManager.CALL_STATE_IDLE)) {
            	i++;
            	if(i==500)
            	{
            		i=0; 
            		Log.d("DEBUG", "STALINphone ::: phoneManager.getCallState(): " + phoneManager.getCallState() );
            	} 
			}
        	
        	myAudioRecorder.stop();
            Log.d("DEBUG", "STALINphone ::: stopped my rec" );
        	myAudioRecorder.release();
        	

            // Cancel the notification -- we use the same ID that we had used to start it
            mNM.cancel(R.string.stalin_rec_service_started);

            // Tell the user we stopped.
//            Toast.makeText(this, R.string.stalin_rec_service_stopped, Toast.LENGTH_SHORT).show();
        	
            Log.d("StalinPhone ::: ", " end call Routing: " + aman.getRouting(AudioManager.ROUTE_ALL));
            Log.d("StalinPhone ::: ", " end call Routing: " + aman.getRouting(AudioManager.ROUTE_BLUETOOTH));
            Log.d("StalinPhone ::: ", " end call Routing: " + aman.getRouting(AudioManager.ROUTE_BLUETOOTH_A2DP));
            Log.d("StalinPhone ::: ", " end call Routing: " + aman.getRouting(AudioManager.ROUTE_EARPIECE));
            Log.d("StalinPhone ::: ", " end call Routing: " + aman.getRouting(AudioManager.ROUTE_HEADSET));
            Log.d("StalinPhone ::: ", " end call Routing: " + aman.getRouting(AudioManager.ROUTE_SPEAKER));
        	
            Log.d("StalinPhone ::: ", " end call aud-params: " + aman.getParameters(AUDIO_SERVICE));
            Log.d("StalinPhone ::: ", " end call tel-params: " + aman.getParameters(TELEPHONY_SERVICE));
            
            Log.d("StalinPhone ::: ", " in call mode: " + aman.getMode());
            
            
            StalinRecService.this.stopSelf();
                

        
        
        
        
        
        

        
        
		
		
		
		
		
		
		
//ADVANCED RECORDER... did not work		
//		int sampleRate = 8000; //can be 44100, 22050, 11025, 8000
//		int audioChannel = AudioFormat.CHANNEL_CONFIGURATION_MONO;

//    			frameworks/base/media/java/android/media/AudioFormat.java:25	AudioFormat()	No description text
//    			frameworks/base/media/java/android/media/AudioFormat.java:44	CHANNEL_CONFIGURATION_DEFAULT	No description text
//    			frameworks/base/media/java/android/media/AudioFormat.java:41	CHANNEL_CONFIGURATION_INVALID	No description text
//    			frameworks/base/media/java/android/media/AudioFormat.java:47	CHANNEL_CONFIGURATION_MONO	No description text
//    			frameworks/base/media/java/android/media/AudioFormat.java:50	CHANNEL_CONFIGURATION_STEREO	No description text
//    			frameworks/base/media/java/android/media/AudioFormat.java:83	CHANNEL_IN_BACK	No description text
//    			frameworks/base/media/java/android/media/AudioFormat.java:87	CHANNEL_IN_BACK_PROCESSED	No description text
//    			frameworks/base/media/java/android/media/AudioFormat.java:79	CHANNEL_IN_DEFAULT	No description text
//    			frameworks/base/media/java/android/media/AudioFormat.java:82	CHANNEL_IN_FRONT	No description text
//    			frameworks/base/media/java/android/media/AudioFormat.java:86	CHANNEL_IN_FRONT_PROCESSED	No description text
//    			frameworks/base/media/java/android/media/AudioFormat.java:80	CHANNEL_IN_LEFT	No description text
//    			frameworks/base/media/java/android/media/AudioFormat.java:84	CHANNEL_IN_LEFT_PROCESSED	No description text
//    			frameworks/base/media/java/android/media/AudioFormat.java:94	CHANNEL_IN_MONO	No description text
//    			frameworks/base/media/java/android/media/AudioFormat.java:88	CHANNEL_IN_PRESSURE	No description text
//    			frameworks/base/media/java/android/media/AudioFormat.java:81	CHANNEL_IN_RIGHT	No description text
//    			frameworks/base/media/java/android/media/AudioFormat.java:85	CHANNEL_IN_RIGHT_PROCESSED	No description text
//    			frameworks/base/media/java/android/media/AudioFormat.java:95	CHANNEL_IN_STEREO	No description text
//    			frameworks/base/media/java/android/media/AudioFormat.java:93	CHANNEL_IN_VOICE_DNLINK	No description text
//    			frameworks/base/media/java/android/media/AudioFormat.java:92	CHANNEL_IN_VOICE_UPLINK	No description text
//    			frameworks/base/media/java/android/media/AudioFormat.java:89	CHANNEL_IN_X_AXIS	No description text
//    			frameworks/base/media/java/android/media/AudioFormat.java:90	CHANNEL_IN_Y_AXIS	No description text
//    			frameworks/base/media/java/android/media/AudioFormat.java:91	CHANNEL_IN_Z_AXIS	No description text
//    			frameworks/base/media/java/android/media/AudioFormat.java:73	CHANNEL_OUT_5POINT1	No description text
//    			frameworks/base/media/java/android/media/AudioFormat.java:75	CHANNEL_OUT_7POINT1	No description text
//    			frameworks/base/media/java/android/media/AudioFormat.java:66	CHANNEL_OUT_BACK_CENTER	No description text
//    			frameworks/base/media/java/android/media/AudioFormat.java:62	CHANNEL_OUT_BACK_LEFT	No description text
//    			frameworks/base/media/java/android/media/AudioFormat.java:63	CHANNEL_OUT_BACK_RIGHT	No description text
//    			frameworks/base/media/java/android/media/AudioFormat.java:60	CHANNEL_OUT_FRONT_CENTER	No description text
//    			frameworks/base/media/java/android/media/AudioFormat.java:58	CHANNEL_OUT_FRONT_LEFT	No description text
//    			frameworks/base/media/java/android/media/AudioFormat.java:64	CHANNEL_OUT_FRONT_LEFT_OF_CENTER	No description text
//    			frameworks/base/media/java/android/media/AudioFormat.java:59	CHANNEL_OUT_FRONT_RIGHT	No description text
//    			frameworks/base/media/java/android/media/AudioFormat.java:65	CHANNEL_OUT_FRONT_RIGHT_OF_CENTER	No description text
//    			frameworks/base/media/java/android/media/AudioFormat.java:61	CHANNEL_OUT_LOW_FREQUENCY	No description text
//    			frameworks/base/media/java/android/media/AudioFormat.java:67	CHANNEL_OUT_MONO	No description text
//    			frameworks/base/media/java/android/media/AudioFormat.java:69	CHANNEL_OUT_QUAD	No description text
//    			frameworks/base/media/java/android/media/AudioFormat.java:68	CHANNEL_OUT_STEREO	No description text
//    			frameworks/base/media/java/android/media/AudioFormat.java:71	CHANNEL_OUT_SURROUND	No description text

//		int audioFormat = AudioFormat.ENCODING_PCM_16BIT;
//		int minBufferSize = AudioRecord.getMinBufferSize(sampleRate, audioChannel, audioFormat);
//        Log.d("StalinPhone ::: ", " buffer size: " + minBufferSize);
//		AudioRecord myRecorder = new AudioRecord(
//    		  MediaRecorder.AudioSource.MIC, 
//    		  sampleRate,
//    		  audioChannel,
//    		  AudioFormat.ENCODING_DEFAULT,
//    		  minBufferSize);
//		
//		myRecorder.startRecording();
//      
//	
//		
//		
//  	StalinRecService.now = new Date();
//  	String myFileName = Environment.getExternalStorageDirectory().getAbsolutePath();
//      myFileName += "/StalinPhone/aMe-"
//        		 + (StalinRecService.now.getYear() + 1900)  + "-"
//     		 + StalinRecService.now.getMonth() + "-"
//     		 + StalinRecService.now.getDay()  + "--"
//     		 + StalinRecService.now.getHours()  + "-"
//     		 + StalinRecService.now.getMinutes() + "-" 
//     		 + StalinRecService.now.getSeconds() +  ".raw";	
//		
//            byte data[] = new byte[minBufferSize];
//            FileOutputStream os = null;
//            try 
//            {
//                    os = new FileOutputStream(myFileName);
//            } catch (FileNotFoundException e) 
//            {
//                    // TODO Auto-generated catch block
//                    e.printStackTrace();
//            }
////            ToggleButton recButton = (ToggleButton) findViewById(R.id.toggleButton1);
//            int read = 0;
//            if(null != os){
//                    while(! (phoneManager.getCallState() == TelephonyManager.CALL_STATE_IDLE)) //recording 
//                    {  
//                            read = myRecorder.read(data, 0, minBufferSize);
//
//                      	  Log.d("DEBUG", "STALINphone ::: read :: " + read + " ::: " + data);
//                            if(AudioRecord.ERROR_INVALID_OPERATION != read)
//                            {
//                                    try 
//                                    {
//                                            os.write(data);
//                                    } catch (IOException e) 
//                                    {
//                                            e.printStackTrace();
//                                    }
//                            }
//                    }
//                    
//                    try {
//                    	  Log.d("DEBUG", "STALINphone ::: closing output" );
//                        	
//                    	os.close();
//                    } catch (IOException e) {
//                            e.printStackTrace();
//                    }
//            }
//    
//            
//            //stop
//            if(null != myRecorder)
//            {
////                    isRecording = false;
//                    
//            	myRecorder.stop();
//            	myRecorder.release();
//                    
//            	myRecorder = null;
////                    recordingThread = null;
//            }
//            
//            
//            
//
//          Log.d("DEBUG", "STALINphone ::: stopped my rec" );
//      	          // Cancel the notification -- we use the same ID that we had used to start it
//          mNM.cancel(R.string.stalin_rec_service_started);
//
//          // Tell the user we stopped.
////          Toast.makeText(this, R.string.stalin_rec_service_stopped, Toast.LENGTH_SHORT).show();
//      	
//            
//		StalinRecService.this.stopSelf();
        
        
        
        
        
        
        
        
        
        
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
        // We use a layout id because it is a unique number.  We use it later to cancel.
        mNM.notify(R.string.stalin_rec_service_started, notification);
    }

	
}//clAss
