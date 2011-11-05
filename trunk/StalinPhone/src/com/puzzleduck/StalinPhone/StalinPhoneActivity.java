package com.puzzleduck.StalinPhone;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.lang.Runtime;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Binder;
import android.os.Bundle;
import android.os.Environment;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ToggleButton;
 
public class StalinPhoneActivity extends Activity implements OnClickListener, RecognitionListener {
    
	MediaPlayer mMediaPlayer;
	Intent recognizerIntent;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        findViewById(R.id.start_stalin_trans_service).setOnClickListener( (OnClickListener) this);
        findViewById(R.id.start_su).setOnClickListener( (OnClickListener) this);
        findViewById(R.id.toggleButton1).setOnClickListener( (OnClickListener) this);
        Log.d("StalinPhone ::: ", "trans button set up");
//icon is Stalin Jamming from uncyclopedia :] thanks sock puppet
    } 

	@Override
	public void onClick(View v) {
        Log.d("StalinPhone ::: ", "button clicked: id" + v.getId());
		
		

      //work out action needed and start/kill service
        if(v.getId() == R.id.toggleButton1)
        {
        	
        	
        	
    		EditText debugText = (EditText) findViewById(R.id.debugTranscription);
    		debugText.append("Running command: " );
          Log.d("StalinPhone ::: ", " StalinPhone REC/TESTING !!!!!!!!!!!!!StalinPhone !!!!: " );

          
          //public Process exec (String prog, String[] envp, File directory)
          try 
          {
			Process myCommand = Runtime.getRuntime().exec("/system/xbin/ls", null, null );
        	try {
				Thread.sleep(100); //woot.... cant believe that worked :) sweet
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
    		debugText.append("Running cmd... output: " + myCommand.getInputStream().available());

    		byte[] commandOut = new byte[myCommand.getInputStream().available()];
    		myCommand.getInputStream().read(commandOut,0,myCommand.getInputStream().available());
    		debugText.append( "\n\t Output: " + new String(commandOut) );	

		} catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
          
          

		
		
		
		
			int sampleRate = 8000; //can be 44100, 22050, 11025, 8000
//			4100Hz is currently the only rate that is guaranteed to work on all devices, 
//			but other rates such as 22050, 16000, and 11025 may work on some devices.
			
//			See CHANNEL_IN_MONO and CHANNEL_IN_STEREO. CHANNEL_IN_MONO //hack: CHANNEL_CONFIGURATION_MONO
			int audioChannel = AudioFormat.CHANNEL_CONFIGURATION_MONO;
			//.CHANNEL_IN_BACK fail
			//.CHANNEL_IN_BACK_PROCESSED fail
			//default seemed to work
			
			//See ENCODING_PCM_16BIT and ENCODING_PCM_8BIT
			int audioFormat = AudioFormat.ENCODING_PCM_16BIT;
			//default fail?
			int minBufferSize = AudioRecord.getMinBufferSize(sampleRate, audioChannel, audioFormat);
//	          Log.d("StalinPhone ::: ", " buffer size: " + minBufferSize);
//			minBufferSize = 52000000;
	          Log.d("StalinPhone ::: ", " buffer size: " + minBufferSize);
			AudioRecord myRecorder = new AudioRecord(
        		  MediaRecorder.AudioSource.VOICE_UPLINK, 
        		  sampleRate,
        		  audioChannel,
        		  AudioFormat.ENCODING_DEFAULT,
        		  minBufferSize);
			
			myRecorder.startRecording();
          
		
			
			
      	StalinRecService.now = new Date();
      	String myFileName = Environment.getExternalStorageDirectory().getAbsolutePath();
          myFileName += "/StalinPhone/AaTest-"
            		 + (StalinRecService.now.getYear() + 1900)  + "-"
         		 + StalinRecService.now.getMonth() + "-"
         		 + StalinRecService.now.getDay()  + "--"
         		 + StalinRecService.now.getHours()  + "-"
         		 + StalinRecService.now.getMinutes() + "-" 
         		 + StalinRecService.now.getSeconds() +  ".raw";	
			
                byte data[] = new byte[minBufferSize];
                FileOutputStream os = null;
                try 
                {
                        os = new FileOutputStream(myFileName);
                } catch (FileNotFoundException e) 
                {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                }
                ToggleButton recButton = (ToggleButton) findViewById(R.id.toggleButton1);
                int read = 0;
                if(null != os){
                        while(recButton.isChecked()) //recording till button off?
                        {  
                                read = myRecorder.read(data, 0, minBufferSize);
                                
                                if(AudioRecord.ERROR_INVALID_OPERATION != read)
                                {
                                        try 
                                        {
                                                os.write(data);
                                        } catch (IOException e) 
                                        {
                                                e.printStackTrace();
                                        }
                                }
                        }
                        
                        try {
                                os.close();
                        } catch (IOException e) {
                                e.printStackTrace();
                        }
                }
        
                
                //stop
                if(null != myRecorder)
                {
//                        isRecording = false;
                        
                	myRecorder.stop();
                	myRecorder.release();
                        
                	myRecorder = null;
//                        recordingThread = null;
                }
                
//                copyWaveFile(getTempFilename(),getFilename());
//                deleteTempFile();

			
			
			
		
		

			//delay
			myRecorder.stop();
			myRecorder.release();
		
		
		
		
          
          
          

//      	Log.d("StalinPhone ::: ", "starting debug REC service...");
//      	NotificationManager mNM = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
//          // show the icon in the status bar
//        CharSequence text = getText(R.string.stalin_rec_service_started);
//        Notification notification = new Notification(R.drawable.icon, text,
//                System.currentTimeMillis());
//        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
//                new Intent(this, StalinRecService.class), 0);
//        notification.setLatestEventInfo(this, getText(R.string.stalin_rec_service_label),
//                       text, contentIntent);
//
//        // Send the notification.
//        // We use a layout id because it is a unique number.  We use it later to cancel.
//        mNM.notify(R.string.stalin_rec_service_started, notification);
//          
//      	TelephonyManager phoneManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
//      	
//      	StalinRecService.now = new Date();
//      	String myFileName = Environment.getExternalStorageDirectory().getAbsolutePath();
//          myFileName += "/StalinPhone/aTest-"
//            		 + (StalinRecService.now.getYear() + 1900)  + "-"
//         		 + StalinRecService.now.getMonth() + "-"
//         		 + StalinRecService.now.getDay()  + "--"
//         		 + StalinRecService.now.getHours()  + "-"
//         		 + StalinRecService.now.getMinutes() + "-" 
//         		 + StalinRecService.now.getSeconds() +  ".3gp";
//
//        File thisFile = new File( myFileName );
//        Log.d("DEBUG", "STALINphone ::: path:" + thisFile.getParent() );
//        new File(thisFile.getParent()).mkdirs(); 
//
//          Log.d("DEBUG", "STALINphone ::: creating myAudioRecorder for:" + myFileName );
//      	MediaRecorder myAudioRecorder = new MediaRecorder();
//      	myAudioRecorder.setAudioSource(MediaRecorder.AudioSource.VOICE_UPLINK);//only mic working  
//      	//cam records works like mike?
//      	//default work
//      	//mic..louder than cam?
//      	//
//      	//down...sounds like mic
//      	//rec ... like mic
//      	//uplink.. like mic
//      	
//      	
//      	myAudioRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
//      	myAudioRecorder.setOutputFile(myFileName);
//      	myAudioRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
//      	
//      	try {
//				myAudioRecorder.prepare();
//			} catch (IllegalStateException e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			} catch (IOException e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			}
//
//          Log.d("DEBUG", "STALINphone ::: about to start my rec" );
//
//      	myAudioRecorder.start();
//      	
//    	try {
//			Thread.sleep(80000); // 8000 too short ... x 10 for long rec
//		} catch (InterruptedException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
////      	int i= 0;
////          while ( ! (phoneManager.getCallState() == TelephonyManager.CALL_STATE_IDLE)) {
////          	i++;
////          	if(i==500)
////          	{
////          		i=0; 
////          		Log.d("DEBUG", "STALINphone ::: phoneManager.getCallState(): " + phoneManager.getCallState() );
////          	} 
////			}
//      	
//      	myAudioRecorder.stop();
//          Log.d("DEBUG", "STALINphone ::: stopped my rec" );
//      	myAudioRecorder.release();
//      	
//
//          // Cancel the notification -- we use the same ID that we had used to start it
//          mNM.cancel(R.string.stalin_rec_service_started);
        }
        if(v.getId() == R.id.start_stalin_trans_service)
        {
            Log.d("StalinPhone ::: ", "button clicked: start t");
////            Log.d("StalinPhone ::: ", "creating intent");
            
        	//prep transcriber
        	recognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
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

        	recognizerIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, getClass().getPackage().getName());
            // Given an hint to the recognizer about what the user is going to say
        	recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
            //5 results, where the first result is the one with higher confidence.
        	recognizerIntent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 10);
        	recognizerIntent.putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, true);
        	recognizerIntent.putExtra(RecognizerIntent.EXTRA_PROMPT, "not sure what to say... keep quiet?!?");
        	recognizerIntent.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_MINIMUM_LENGTH_MILLIS, 200000l);//get from file
        	recognizerIntent.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_COMPLETE_SILENCE_LENGTH_MILLIS, 200000l);//300 second silence
//        	recognizerIntent.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_MINIMUM_LENGTH_MILLIS, get this from file!!!);
        	recognizerIntent.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_POSSIBLY_COMPLETE_SILENCE_LENGTH_MILLIS, 200000l); //100 seconds

        	Log.d("StalinPhone ::: ", "TRANS extras ready");
        	
        	
        	transcriber.setRecognitionListener(this);
//        	Log.d("StalinPhone ::: ", "TRANS set");
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

        	//usefull for review panel: String CONFIDENCE_SCORES 
        	
        	
        	
        	
//        	Log.d("StalinPhone ::: ", "TRANS about to stop");
//        	//will need later: 
//        	transcriber.stopListening();
//        	
            
        }

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

//restart audio
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
