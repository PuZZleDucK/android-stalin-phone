package com.puzzleduck.StalinPhone;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
 
public class StalinPhoneActivity extends Activity implements OnClickListener, RecognitionListener {
    
	MediaPlayer mMediaPlayer;
	Intent recognizerIntent;
	
	/** Called when the activity is first created. */
	
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
         
//        Log.d("StalinPhone ::: ", "buttons selected");
        findViewById(R.id.start_stalin_trans_service).setOnClickListener( (OnClickListener) this);
//        findViewById(R.id.stop_stalin_trans_service).setOnClickListener( (OnClickListener) this);
        Log.d("StalinPhone ::: ", "trans button set up");
//        findViewById(R.id.start_stalin_rec_service).setOnClickListener( (OnClickListener) this);
//        findViewById(R.id.stop_stalin_rec_service).setOnClickListener( (OnClickListener) this);
//        Log.d("StalinPhone ::: ", "rec buttons set up");
//icon is Stalin Jamming from uncyclopedia :] thanks sock puppet
    } 

//    private PendingIntent mTransRequestSender;

	@Override
	public void onClick(View v) {
        Log.d("StalinPhone ::: ", "button clicked: id" + v.getId());
		
		
		
      //work out action needed and start/kill service
//        if(v.getId() == R.id.start_stalin_rec_service)
////        {
////            Log.d("StalinPhone ::: ", "creating REC intent");
////            mTransRequestSender = PendingIntent.getService(StalinPhoneActivity.this,
////            		0, new Intent(StalinPhoneActivity.this, StalinRecService.class), 0);
////
////            Log.d("StalinPhone ::: ", "REC intent created");
////            try {
////    			mTransRequestSender.send();
////    		} catch (CanceledException e) {
////    			// TODO Auto-generated catch block
////    			e.printStackTrace();
////    		}        
//        }
//        if(v.getId() == R.id.stop_stalin_rec_service)
//        {
////            Log.d("StalinPhone ::: ", "creating stop-REC intent");
////            mTransRequestSender = PendingIntent.getService(StalinPhoneActivity.this,
////            		0, new Intent(StalinPhoneActivity.this, StalinStopRecService.class), 0);
////
////            Log.d("StalinPhone ::: ", "stop-REC intent created");
////            try {
////    			mTransRequestSender.send();
////    		} catch (CanceledException e) {
////    			// TODO Auto-generated catch block
////    			e.printStackTrace();
////    		}
//        }
        if(v.getId() == R.id.start_stalin_trans_service)
        {
            Log.d("StalinPhone ::: ", "button clicked: start t");
////            Log.d("StalinPhone ::: ", "creating intent");
//            Log.d("StalinPhone ::: ", "creating TRANS intent");
//            mTransRequestSender = PendingIntent.getService(StalinPhoneActivity.this,
//            		0, new Intent(StalinPhoneActivity.this, StalinTransService.class), 0);
//
//            Log.d("StalinPhone ::: ", "trans intent created");
//            try {
//    			mTransRequestSender.send();
//    		} catch (CanceledException e) {
//    			// TODO Auto-generated catch block
//    			e.printStackTrace();
//    		}
            
            
            
            
            


        	//prep transcriber
        	recognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
//        	Intent recognizerIntent = new Intent(RecognizerIntent.ACTION_WEB_SEARCH);
//        	Log.d("StalinPhone ::: ", "TRANS Recog intent created");
//        	PackageManager pm = getPackageManager();
//        	Log.d("StalinPhone ::: ", "TRANS packages got");
//        	List <ResolveInfo> activities = pm.queryIntentActivities(recognizerIntent, 0);
//        	Log.d("StalinPhone ::: ", "TRANS activities: " + activities.size());
//            	Log.d("StalinPhone ::: ", "TRANS activities: " + activities.toString());
        	
        	//create transcriber   ... to use other engines add parameter: ComponentName serviceComponent
        	SpeechRecognizer transcriber = SpeechRecognizer.createSpeechRecognizer( this.getApplicationContext() );
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
        	recognizerIntent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 10);
            // system locale). Most of the applications do not have to set this parameter.
        	//leaving language default
        	

        	recognizerIntent.putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, true);
        	recognizerIntent.putExtra(RecognizerIntent.EXTRA_PROMPT, "not sure what to say... keep quiet?!?");
        	recognizerIntent.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_MINIMUM_LENGTH_MILLIS, 200000l);//get from file
        	recognizerIntent.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_COMPLETE_SILENCE_LENGTH_MILLIS, 200000l);//300 second silence
//        	recognizerIntent.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_MINIMUM_LENGTH_MILLIS, get this from file!!!);
        	recognizerIntent.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_POSSIBLY_COMPLETE_SILENCE_LENGTH_MILLIS, 200000l); //100 seconds


        	
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

        	
        	transcriber.setRecognitionListener(this);
//        	Log.d("StalinPhone ::: ", "TRANS set");
//        	transcriber.startListening (recognizerIntent);
        	startActivityForResult(recognizerIntent, 1234);//request code = 0


        	Log.d("StalinPhone ::: ", "TRANS intent sent... now play media after 8sec delay");
        	try {
				Thread.sleep(8000);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

        	
        	
            String path = "/sdcard/StalinPhone/111-10-2--16-47-54.3gp";
            mMediaPlayer = new MediaPlayer();
            try {
				mMediaPlayer.setDataSource(path);
	        	Log.d("StalinPhone ::: ", "TRANS  play media ... set path");
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            try {
				mMediaPlayer.prepare();
	        	Log.d("StalinPhone ::: ", "TRANS  play media ... prep");
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            mMediaPlayer.start();
        	Log.d("StalinPhone ::: ", "TRANS  play media ... started");

        	
//        	RESULTS_RECOGNITION
//        	Key used to retrieve an ArrayList from the Bundle passed to the onResults(Bundle)
//        	and onPartialResults(Bundle) methods. These strings are the possible recognition 
//        	results, where the first element is the most likely candidate.
        	
        	
        	

//            // Normally we would do some work here...  for our sample, we will
//            // just sleep for 10 seconds.
//            long endTime = System.currentTimeMillis() + 5*1000;
//            while (System.currentTimeMillis() < endTime) {
//                synchronized (mBinder) {
//                    try {
//                        mBinder.wait(endTime - System.currentTimeMillis());
//                    } catch (Exception e) {
//                    }
//                }
//            }
//        	
//        	
//        	
//        	
//
//        	Log.d("StalinPhone ::: ", "TRANS about to stop");
//        	//will need later: 
//        	transcriber.stopListening();
//        	
        	
        	
        	
        	
        	//usefull for review panel: String CONFIDENCE_SCORES 
        	
        	
        	
        	
            
            
            
            
            
            
            
            
            
        }
//        if(v.getId() == R.id.stop_stalin_trans_service)
//        {
//            Log.d("StalinPhone ::: ", "button clicked: stop t");
////            Log.d("StalinPhone ::: ", "creating intent");
////    		}
//        }

	}//onClick

	
	


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

	
//	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return mBinder;
	}//ibinder

	
	

    /**
     * Handle the results from the recognition activity.
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("StalinPhone ::: ", " StalinPhone ::: onActivityResult requestCode: " + requestCode);
        Log.d("StalinPhone ::: ", " StalinPhone ::: onActivityResult resultCode: " + resultCode);
        Log.d("StalinPhone ::: ", " StalinPhone ::: onActivityResult data: " + data );
        if (requestCode == 1234 && resultCode == RESULT_OK) {
            // Fill the list view with the strings the recognizer thought it could have heard
            ArrayList<String> matches = data.getStringArrayListExtra(
                    RecognizerIntent.EXTRA_RESULTS);
            
    		EditText debugText = (EditText) findViewById(R.id.debugTranscription);
            Log.d("StalinPhone ::: ", " StalinPhone ::: onActivityResult matches : " + matches.toString() );
    		debugText.append("Result recieved (key-set): " + matches.toString() + "\n\n----------\n" );
//            mList.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,
//                    matches));
        }

        Log.d("StalinPhone ::: ", " pre super ");
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("StalinPhone ::: ", " post super ");
        if(mMediaPlayer.isPlaying())
        {   	
        	mMediaPlayer.pause();
        	//now trigger alternative recorder :

        	startActivityForResult(recognizerIntent, 1234);//request code = 0

        	Log.d("StalinPhone ::: ", "TRANS resuming play media after 2sec delay");
        	try {
				Thread.sleep(2000);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

        	
        	
        	

            mMediaPlayer.start();
        }else
        {
        	//finished... nothing
        }
    }

	
	
	
	
	@Override
	public void onBeginningOfSpeech() {
		// TODO Auto-generated method stub
        Log.d("StalinPhone ::: ", " StalinPhone ::: onBeginningOfSpeech");
		
	}

	@Override
	public void onBufferReceived(byte[] buffer) {
		// TODO Auto-generated method stub
        Log.d("StalinPhone ::: ", " StalinPhone ::: onBufferReceived");
		
	}

	@Override
	public void onEndOfSpeech() {
		// TODO Auto-generated method stub
        Log.d("StalinPhone ::: ", " StalinPhone ::: onEndOfSpeech");
		
	}

	@Override
	public void onError(int error) {
		// TODO Auto-generated method stub
        Log.d("StalinPhone ::: ", " StalinPhone ::: onError");
		
	}

	@Override
	public void onEvent(int eventType, Bundle params) {
		// TODO Auto-generated method stub
        Log.d("StalinPhone ::: ", " StalinPhone ::: onEvent");
		
	}

	@Override
	public void onPartialResults(Bundle partialResults) {
		// TODO Auto-generated method stub
        Log.d("StalinPhone ::: ", " StalinPhone ::: onPartialResults");
		
	}

	@Override
	public void onReadyForSpeech(Bundle params) {
		// TODO Auto-generated method stub
        Log.d("StalinPhone ::: ", " StalinPhone ::: onReadyForSpeech");
		
	}

	@Override
	public void onResults(Bundle results) {
		// TODO Auto-generated method stub
        Log.d("StalinPhone ::: ", " StalinPhone ::: onResults");
//		debugTranscription
		EditText debugText = (EditText) findViewById(R.id.debugTranscription);
		debugText.append("Result recieved (key-set): " + results.keySet() + "\n\n----------\n" );
	}

	@Override
	public void onRmsChanged(float rmsdB) {
		// TODO Auto-generated method stub
        Log.d("StalinPhone ::: ", " StalinPhone :::  onRmsChanged");
		
	}
}//class
