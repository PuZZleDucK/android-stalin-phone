package com.puzzleduck.StalinPhone;

import java.util.List;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

public class StalinTransService extends Service implements RecognitionListener {
    NotificationManager mNM;

    @Override
    public void onCreate() {
    	Log.d("StalinPhone ::: ", "TRANS service starting...");
        mNM = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        showNotification();
//    	Log.d("StalinPhone ::: ", "show notification success");

        Thread thr = new Thread(null, mTask, "StalinTransService");
    	Log.d("StalinPhone ::: ", "starting TRANS thread");
        thr.start();
    }

    @Override
    public void onDestroy() {
        // Cancel the notification -- we use the same ID that we had used to start it
        mNM.cancel(R.string.stalin_trans_service_started);
        Toast.makeText(this, R.string.stalin_trans_service_stopped, Toast.LENGTH_SHORT).show();
    }

    /**
     * The function that runs in our worker thread
     */
    Runnable mTask = new Runnable() {
        public void run() {
        	

//        	TelephonyManager phoneManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        	
        	
        	

        	//prep transcriber
//        	Intent recognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        	Intent recognizerIntent = new Intent(RecognizerIntent.ACTION_WEB_SEARCH);
        	Log.d("StalinPhone ::: ", "TRANS Recog intent created");
        	PackageManager pm = getPackageManager();
        	Log.d("StalinPhone ::: ", "TRANS packages got");
        	List <ResolveInfo> activities = pm.queryIntentActivities(recognizerIntent, 0);
        	Log.d("StalinPhone ::: ", "TRANS activities: " + activities.size());
            	Log.d("StalinPhone ::: ", "TRANS activities: " + activities.toString());
        	
        	//create transcriber   ... to use other engines add parameter: ComponentName serviceComponent
        	SpeechRecognizer transcriber = SpeechRecognizer.createSpeechRecognizer( StalinTransService.this.getApplicationContext() );
        	Log.d("StalinPhone ::: ", "TRANS transcriber created");
        	//        	createSpeechRecognizer(Context). This class's methods must be invoked only from the main 
//        	application thread. Please note that the application must have RECORD_AUDIO permission 
//        	to use this class

//        	//this should  refresh settings, not working yet
//            sendOrderedBroadcast(recognizerIntent, null,
//                    new SupportedLanguageBroadcastReceiver(), null, Activity.RESULT_OK, null, null);
     
        	recognizerIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, getClass().getPackage().getName());
            // Given an hint to the recognizer about what the user is going to say
        	recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
            //5 results, where the first result is the one with higher confidence.
        	recognizerIntent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 5);
            // system locale). Most of the applications do not have to set this parameter.
        	//leaving language default
        	Log.d("StalinPhone ::: ", "TRANS extras ready");
        	
//use broadcasts instead
//        	startActivityForResult(recognizerIntent, 0);//request code 0
//        	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//                if (requestCode == VOICE_RECOGNITION_REQUEST_CODE && resultCode == RESULT_OK) {
//                    // Fill the list view with the strings the recognizer thought it could have heard
//                    ArrayList<String> matches = data.getStringArrayListExtra(
//                            RecognizerIntent.EXTRA_RESULTS);
//                    mList.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,
//                            matches));
//                }
//
//                super.onActivityResult(requestCode, resultCode, data);
//            }

        	
        	transcriber.setRecognitionListener(StalinTransService.this);
        	Log.d("StalinPhone ::: ", "TRANS set");
        	transcriber.startListening (recognizerIntent);

        	Log.d("StalinPhone ::: ", "TRANS started listening");
        	
//        	RESULTS_RECOGNITION
//        	Key used to retrieve an ArrayList from the Bundle passed to the onResults(Bundle)
//        	and onPartialResults(Bundle) methods. These strings are the possible recognition 
//        	results, where the first element is the most likely candidate.
        	
        	
        	

            // Normally we would do some work here...  for our sample, we will
            // just sleep for 30 seconds.
            long endTime = System.currentTimeMillis() + 15*1000;
            while (System.currentTimeMillis() < endTime) {
                synchronized (mBinder) {
                    try {
                        mBinder.wait(endTime - System.currentTimeMillis());
                    } catch (Exception e) {
                    }
                }
            }
        	
        	
        	
        	

        	Log.d("StalinPhone ::: ", "TRANS about to stop");
        	//will need later: 
        	transcriber.stopListening();
        	
        	
        	
        	
        	
        	//usefull for review panel: String CONFIDENCE_SCORES 
        	
        	
        	
        	
        	
        	

            // Done with our work...  stop the service!
        	Log.d("StalinPhone ::: ", "TRANS service terminated...");
            StalinTransService.this.stopSelf();
        }
    };
	
    /**
     * Show a notification while this service is running.
     */
    private void showNotification() {
        // In this sample, we'll use the same text for the ticker and the expanded notification
        CharSequence text = getText(R.string.stalin_trans_service_started);

        // Set the icon, scrolling text and timestamp
        Notification notification = new Notification(R.drawable.icon, text,
                System.currentTimeMillis());

        // The PendingIntent to launch our activity if the user selects this notification
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, StalinTransService.class), 0);

        // Set the info for the views that show in the notification panel.
        notification.setLatestEventInfo(this, getText(R.string.stalin_trans_service_label),
                       text, contentIntent);

        // Send the notification.
        // We use a layout id because it is a unique number.  We use it later to cancel.
        mNM.notify(R.string.stalin_trans_service_started, notification);
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

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return mBinder;
	}//ibinder

	@Override
	public void onBeginningOfSpeech() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onBufferReceived(byte[] buffer) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onEndOfSpeech() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onError(int error) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onEvent(int eventType, Bundle params) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPartialResults(Bundle partialResults) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onReadyForSpeech(Bundle params) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onResults(Bundle results) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onRmsChanged(float rmsdB) {
		// TODO Auto-generated method stub
		
	}
	
}//clAss
