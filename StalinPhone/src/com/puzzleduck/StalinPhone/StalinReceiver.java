package com.puzzleduck.StalinPhone;

import java.io.File;
import java.io.IOException;

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

public class StalinReceiver extends BroadcastReceiver {

	private MediaRecorder recorder = new MediaRecorder();
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
			String state = extras.getString(TelephonyManager.EXTRA_STATE);
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
//					File myNewFolder = new File(
//							Environment.getExternalStorageDirectory()
//									+ "StalinPhone");
//					myNewFolder.mkdir();

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
					Log.d("DEBUG", "STALINphone ::: audio file set: ");

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

				}//EXTRA_STATE_OFFHOOK
				if (state.equals(TelephonyManager.EXTRA_STATE_IDLE)) {
					//stop recording ... start dictation service
					//then when complete dictation service will start "review" UI

					//check that we are still running!!
					//not sure i can???

					recorder.stop();
					Log.d("DEBUG", "STALINphone ::: Idle - STOP REC ");
					recorder.release();
					Log.d("DEBUG", "STALINphone ::: Idle - RELEASE REC ");

					//				String phoneNumber = extras
					//						.getString(TelephonyManager.EXTRA_INCOMING_NUMBER);

				}//EXTRA_STATE_IDLE
			}//if state==null
		}


	}//onRec
}//class
