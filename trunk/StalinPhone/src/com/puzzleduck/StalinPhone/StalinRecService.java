package com.puzzleduck.StalinPhone;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.hardware.Camera.PictureCallback;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationManager;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.net.DhcpInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Binder;
import android.os.Environment;
import android.os.IBinder;
import android.os.Parcel;
import android.os.PowerManager;
import android.os.RemoteException;
import android.telephony.TelephonyManager;
import android.text.ClipboardManager;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ToggleButton;

public class StalinRecService extends Service {
    private NotificationManager mNM;
    private static Date now;
    private File pictureFile;
    private BufferedWriter buf;
    
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

        	TelephonyManager phoneManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        	
        	ClipboardManager clipManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        	PowerManager powerManager = (PowerManager) getSystemService(Context.POWER_SERVICE);
        	SensorManager sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        	
        	

        	
        	
        	
        	
        	StalinRecService.now = new Date();
        	String recFileName = Environment.getExternalStorageDirectory().getAbsolutePath();
        	String picFileName = "";
        	recFileName += "/StalinPhone/";
            recFileName += "me-" + (StalinRecService.now.getYear() + 1900)  + "-"
	  		 + StalinRecService.now.getMonth() + "-"
	  		 + StalinRecService.now.getDay()  + "--"
	  		 + StalinRecService.now.getHours()  + "-"
	  		 + StalinRecService.now.getMinutes() + "-" 
	  		 + StalinRecService.now.getSeconds() + "/";

            File myNewFolder = new File(recFileName);
            myNewFolder.mkdir();
	  		
	  		 String textFileName = recFileName;
	  		picFileName = recFileName + "img.";
		  		recFileName +=  "audio.3gp";
		  		textFileName +=  "text.txt";

		  		
	        	LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
	        	//providers
	        	List<String> providers = locationManager.getAllProviders();
	        	String locText = "\n Location data at start of call:\n==================";
	        	for(int pCount = providers.size()-1; 0 <= pCount; pCount--)
	        	{
	        		
	        		locText += "\nLocation-provider: " + providers.get(pCount);
	            	Location callLoc = locationManager.getLastKnownLocation(providers.get(pCount)) ;
	            	locText += "\n-(desc)->" + callLoc.describeContents();
	            	locText += "\n-(acc )->" + callLoc.getAccuracy();
	            	locText += "\n-(alt )->" + callLoc.getAltitude();
	            	locText += "\n-(bear)->" + callLoc.getBearing();
	            	locText += "\n-(lat )->" + callLoc.getLatitude();
	            	locText += "\n-(lon )->" + callLoc.getLongitude();
	            	locText += "\n-(prov)->" + callLoc.getProvider();
	            	locText += "\n-(spd )->" + callLoc.getSpeed();
	            	locText += "\n-(time)->" + callLoc.getTime();
	              Log.d("DEBUG", "STALINphone ::: loctest:" + locText );

	        	}
	        	
	        	 try {
					buf = new BufferedWriter(new FileWriter(textFileName));
					buf.append(locText);
				      buf.newLine();
				      buf.close();

				} catch (IOException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}

		  		
		  		
	         	WifiManager wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
	         	
	         	

	         	switch(wifiManager.getWifiState())
	         	{
	         	case WifiManager.WIFI_STATE_DISABLED:
         			//???
         			break;
	         	case WifiManager.WIFI_STATE_DISABLING:
         			//???
         			break;
	         	case WifiManager.WIFI_STATE_ENABLED:
         			//???
         			break;
	         	case WifiManager.WIFI_STATE_ENABLING:
         			//???
         			break;
	         	case WifiManager.WIFI_STATE_UNKNOWN:
         			//???
         			break;
         				
	         	}
	         	
	         	
	         	WifiInfo wInfo = wifiManager.getConnectionInfo();
	         	wInfo.getHiddenSSID();
	         	wInfo.getIpAddress();
	         	wInfo.getLinkSpeed();
	         	wInfo.getMacAddress();
	         	wInfo.getNetworkId();
	         	wInfo.getRssi();
	         	wInfo.getSSID();
	         	wInfo.getSupplicantState();
	         	
//	         	WifiConfiguration wConfig = (WifiConfiguration) wifiManager.getConfiguredNetworks();
//	         	wConfig.;
	         	
	         	
	         	DhcpInfo dInfo = wifiManager.getDhcpInfo();
	         	int x = dInfo.dns1;
	         	
	         	List<ScanResult> sResults = wifiManager.getScanResults();
		  		
		  		
		  		
		  		
		  		
		  		
		  		
		  		
//            Log.d("DEBUG", "STALINphone ::: creating myAudioRecorder for:" + recFileName );
        	MediaRecorder myAudioRecorder = new MediaRecorder();
        	myAudioRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);//only mic working
        	//add prefs for this:
        	myAudioRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        	myAudioRecorder.setOutputFile(recFileName);
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
        	
        	
//            Detect and Access Camera - Create code to check for the existence of cameras and request access.

        	/** Check if this device has a camera */

        	Context context = StalinRecService.this.getApplicationContext();
        	    if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)){
        	        // this device has a camera
        	    } else {
        	        // no camera on this device
        	    }

//              Log.d("DEBUG", "STALINphone-stalinCAM ::: about to start c" );
            Camera c = null;
            try {
//            	c= Camera.this.
                c = Camera.open(); // attempt to get a Camera instance
//                Log.d("DEBUG", "STALINphone-stalinCAM ::: open c" );
            }
            catch (Exception e){
                // Camera is not available (in use or does not exist)
            }
//            return c; // returns null if camera is unavailable
            
//            CameraPreview preview = new CameraPreview(c);
            c.startPreview();

//            Log.d("DEBUG", "STALINphone-stalinCAM ::: preview c" );
            
            
//you can get further information about its capabilties using the Camera.getParameters() method and checking the returned Camera.Parameters object
            
            
        	
        	//???maybe
//        	Create a Preview Class - Create a camera preview class that extends SurfaceView and implements the SurfaceHolder interface. This class previews the live images from the camera.

        	//not needed //            Build a Preview Layout - Once you have the camera preview class, create a view layout that incorporates the preview and the user interface controls you want.
        	//not needed //            Setup Listeners for Capture - Connect listeners for your interface controls to start image or video capture in response to user actions, such as pressing a button.

        	
        	
        	
        	int i = 0, camCount = 0;
        	
            while ( ! (phoneManager.getCallState() == TelephonyManager.CALL_STATE_IDLE)) {
            	i++;
            	
//            	CameraInfo cameraInfo;
//            	c.getCameraInfo(0, cameraInfo);
//            	cameraInfo.
            	if(i>=10000)
            	{
//                    Log.d("DEBUG", "STALINphone-stalinCAM ::: pic" );
            		i=0;
            		camCount++;
//            		Log.d("DEBUG", "STALINphone ::: phoneManager.getCallState(): " + phoneManager.getCallState() );
            		//capture image
//            		Capture and Save Files - Setup the code for capturing pictures or videos and saving the output.
            		pictureFile = new File(picFileName + camCount + ".jpg");
//                    Log.d("DEBUG", "STALINphone-stalinCAM ::: pic file" );
            		

                    c.takePicture(null, null, null, new PictureCallback() {

                        @Override
                        public void onPictureTaken(byte[] data, Camera camera) {

//                            Log.d("DEBUG", "STALINphone-stalinCAM ::: pic CB start" );
                            if (pictureFile == null){
//                                Log.d("STALIN", "Error creating media file, check storage permissions: ");
                                return;
                            }

                            try {
                                FileOutputStream fos = new FileOutputStream(pictureFile);
                                fos.write(data);
                                fos.close();
                            } catch (FileNotFoundException e) {
//                                Log.d("STALIN", "File not found: " + e.getMessage());
                            } catch (IOException e) {
//                                Log.d("STALIN", "Error accessing file: " + e.getMessage());
                            }
//                            Log.d("DEBUG", "STALINphone-stalinCAM ::: pic CB end" );
                        }
                    });// image callback
            	} // occasional event, eg photo
			}// while not .CALL_STATE_IDLE

            providers = locationManager.getAllProviders();
        	locText = "\n Location data at end of call:\n==================";
        	for(int pCount = providers.size()-1; 0 <= pCount; pCount--)
        	{
        		
        		locText += "\nLocation-provider: " + providers.get(pCount);
            	Location callLoc = locationManager.getLastKnownLocation(providers.get(pCount)) ;
            	locText += "\n-(desc)->" + callLoc.describeContents();
            	locText += "\n-(acc )->" + callLoc.getAccuracy();
            	locText += "\n-(alt )->" + callLoc.getAltitude();
            	locText += "\n-(bear)->" + callLoc.getBearing();
            	locText += "\n-(lat )->" + callLoc.getLatitude();
            	locText += "\n-(lon )->" + callLoc.getLongitude();
            	locText += "\n-(prov)->" + callLoc.getProvider();
            	locText += "\n-(spd )->" + callLoc.getSpeed();
            	locText += "\n-(time)->" + callLoc.getTime();
        	}

        	try {
//				BufferedWriter buf = new BufferedWriter(new FileWriter(textFileName));
				buf.append(locText);
			      buf.newLine();
			      buf.close();

			} catch (IOException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
            
//            Release the Camera - After using the camera, your application must properly release it for use by other applications.
            c.release();

        	myAudioRecorder.stop();
//            Log.d("DEBUG", "STALINphone ::: stopped my rec" );
        	myAudioRecorder.release();
        	

            // Cancel the notification -- we use the same ID that we had used to start it
            mNM.cancel(R.string.stalin_rec_service_started);

            
            StalinRecService.this.stopSelf();
        }//mTask
	            
    };
	
	

//    private PictureCallback mPicture = 
	
	

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
