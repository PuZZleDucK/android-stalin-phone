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
import android.hardware.Sensor;
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

        	StalinRecService.now = new Date();
        	String recFileName = Environment.getExternalStorageDirectory().getAbsolutePath();
        	String picFileName = "";
        	recFileName += "/StalinPhone/";
            recFileName += "record_" + (StalinRecService.now.getYear() + 1900)  + "-"
	  		 + StalinRecService.now.getMonth() + "-"
	  		 + StalinRecService.now.getDay()  + "__"
	  		 + StalinRecService.now.getHours()  + "."
	  		 + StalinRecService.now.getMinutes() + "." 
	  		 + StalinRecService.now.getSeconds() + "/";

            File myNewFolder = new File(recFileName);
            myNewFolder.mkdir();

	  		 String textFileName = recFileName;
	  		 String posttextFileName = recFileName;
	  		picFileName = recFileName + "img.";
		  		recFileName +=  "audio.3gp";
		  		textFileName +=  "info-start.txt";
		  		posttextFileName +=  "info-end.txt";
		  		
	        	LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
	        	//providers
	        	List<String> providers = locationManager.getAllProviders();
	        	String outputText = "\n Start of call:\n==================";
	        	outputText += "\n Location data:\n------------------";
	        	for(int pCount = providers.size()-1; 0 <= pCount; pCount--)
	        	{
	        		
	        		outputText += "\n\nLocation-provider: " + providers.get(pCount);
	            	Location callLoc = locationManager.getLastKnownLocation(providers.get(pCount)) ;

	            	outputText += "\nLat/Long: " + callLoc.getLatitude();
	            	outputText += "/" + callLoc.getLongitude();
//	            	outputText += "\n-(desc)->" + callLoc.describeContents();
	            	outputText += "\nAltitude: " + callLoc.getAltitude();
	            	outputText += "\nBearing: " + callLoc.getBearing();
	            	outputText += "\nSpeed: " + callLoc.getSpeed();
	            	outputText += "\nTime:" + callLoc.getTime();
	            	outputText += "\nAccuracy: " + callLoc.getAccuracy();
//	            	outputText += "\nProvider: " + callLoc.getProvider();
//		              Log.d("DEBUG", "STALINphone1 :::" + outputText );

	        	}
	        	

		  		
		  		
	         	WifiManager wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);

	        	outputText += "\n\n Network data:\n------------------";
	         	switch(wifiManager.getWifiState())
	         	{
	         	case WifiManager.WIFI_STATE_DISABLED:
	         		outputText += "\nWifi State: Disabled\n";
         			break;
	         	case WifiManager.WIFI_STATE_DISABLING:
	         		outputText += "\nWifi State: Disabling\n";
         			break;
	         	case WifiManager.WIFI_STATE_ENABLED:
	         		outputText += "\nWifi State: Enabled\n";
         			break;
	         	case WifiManager.WIFI_STATE_ENABLING:
	         		outputText += "\nWifi State: Enabling\n";
         			break;
	         	case WifiManager.WIFI_STATE_UNKNOWN:
	         		outputText += "\nWifi State: Unknown\n";
         			break;
	         	}

//	              Log.d("STALINphone:::", "STALINphone2 :::" + outputText );
	         	
	         	WifiInfo wInfo = wifiManager.getConnectionInfo();

//	              Log.d("STALINphone:::", "STALINphone  2 info");
         		if (wifiManager != null) 
         		{
					outputText += "\nSSID: " + wInfo.getSSID();
					outputText += "\nMAC Add: " + wInfo.getMacAddress();
					outputText += "\nIP: " + wInfo.getIpAddress();
					outputText += "\nNet ID: " + wInfo.getNetworkId();
					outputText += "\nSpeed: " + wInfo.getLinkSpeed();
					outputText += "\nHidden SSID: " + wInfo.getHiddenSSID();
					outputText += "\nRSSI: " + wInfo.getRssi();
					outputText += "\nSup State: " + wInfo.getSupplicantState();
				}
         		
				List <WifiConfiguration> wConfigList = wifiManager.getConfiguredNetworks();

	        	outputText += "\n\n Known networks:\n------------------";
//	              Log.d("STALINphone:::", "STALINphone  2 config");
    	        	for(int pCount = wConfigList.size()-1; 0 <= pCount; pCount--)
    	        	{
         			
    	        		WifiConfiguration wConfig = wConfigList.get(pCount);
    	        		
    		         	outputText += "\n\nSSID: " + wConfig.SSID;
    		         	outputText += "\nStatus: " + wConfig.status;
//         			outputText += "\nAuth Alg: " + wConfig.allowedAuthAlgorithms;
		         	outputText += "\npsKey: " + wConfig.preSharedKey;
		         	outputText += "\nPriority: " + wConfig.priority;
//		         	outputText += "\nKey index: " + wConfig.wepTxKeyIndex;
		         	outputText += "\nBSSID: " + wConfig.BSSID;
//		         	outputText += "\nNet ID: " + wConfig.networkId;
		         	outputText += "\nCyphers: " + wConfig.allowedGroupCiphers;
		         	outputText += "\nKey man: " + wConfig.allowedKeyManagement;
		         	outputText += "\nPairwise Cyphers: " + wConfig.allowedPairwiseCiphers;
		         	outputText += "\nProtocols: " + wConfig.allowedProtocols;
		         	outputText += "\nHidden SSID: " + wConfig.hiddenSSID;
		         	outputText += "\nwepKeys: " + wConfig.wepKeys.toString();
         		}
	         	

//	              Log.d("DEBUG", "STALINphone3 :::" + outputText );

		        	outputText += "\n\n DNS Info:\n------------------";
	         	DhcpInfo dInfo = wifiManager.getDhcpInfo();
	         	if (dInfo != null) 
	         	{
					outputText += "\nDNS 1: " + dInfo.dns1;
					outputText += "\nDNS 2: " + dInfo.dns2;
					outputText += "\nDNS gate: " + dInfo.gateway;
					outputText += "\nDNS ip: " + dInfo.ipAddress;
					outputText += "\nDNS lease: " + dInfo.leaseDuration;
					outputText += "\nDNS netmask: " + dInfo.netmask;
					outputText += "\nDNS server add: " + dInfo.serverAddress;
				}
	         	

	        	outputText += "\n\n Nearby Networks:\n------------------";
				List<ScanResult> sResults = wifiManager.getScanResults();
	        	for(int sCount = sResults.size()-1; 0 <= sCount; sCount--)
	        	{
	        		ScanResult sResult = sResults.get(sCount);
	        		outputText += "\n\nSSid: " + sResult.SSID;
	        		outputText += "\nCapabilities: " + sResult.capabilities;
	        		outputText += "\nFreq: " + sResult.frequency;
	        		outputText += "\nLevel: " + sResult.level;
	        		outputText += "\nBSSid: " + sResult.BSSID;
	        	}
		  		

//	              Log.d("DEBUG", "STALINphone4 :::" + outputText );
	              
	        	SensorManager sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
	        	List<Sensor> sList = sensorManager.getSensorList(Sensor.TYPE_ALL);
	        	outputText += "\n\n Sensor data:\n------------------";
	        	for(int sCount = sList.size()-1; 0 <= sCount; sCount--)
	        	{
	        		Sensor sResult = sList.get(sCount);
	        		outputText += "\n\nSensor: " + sResult.getName();
	        		outputText += "\nVendor: " + sResult.getVendor();
//	        		outputText += "\nSensor type: " + sResult.getType();
	        		outputText += "\nPower: " + sResult.getPower();
	        		outputText += "\nResolution: " + sResult.getResolution();
	        	}	
	        	
	        	

//	              Log.d("DEBUG", "STALINphone5 :::" + outputText );
	              
	        	PowerManager powerManager = (PowerManager) getSystemService(Context.POWER_SERVICE);
	        	outputText += "\n\n Screen on: " + powerManager.isScreenOn();
	        	
	        	
	        	ClipboardManager clipManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
	        	if(clipManager.hasText())
	        	{
	        		outputText += "\n\n Clipboard:\n------------------";
	        		outputText += "\n" + clipManager.getText();
	        	}
	        	

	              Log.d("DEBUG", "STALINphone6 :::" + outputText );

        		outputText += "\n\n Phone Information:\n------------------";
	        	
	        	

	        	TelephonyManager phoneManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

	        	outputText += "\nPhone sim operator name: " + phoneManager.getSimOperatorName();
	        	outputText += "\nPhone operator name: " + phoneManager.getNetworkOperatorName();
	        	outputText += "\nPhone country: " + phoneManager.getNetworkCountryIso();
	        	outputText += "\nPhone roaminig: " + phoneManager.isNetworkRoaming();
	        	
	        	outputText += "\nPhone activity: " + phoneManager.getDataActivity();
	        	outputText += "\nPhone location: " + phoneManager.getCellLocation();
	        	outputText += "\nPhone state: " + phoneManager.getDataState();
	        	outputText += "\nPhone dev ver: " + phoneManager.getDeviceSoftwareVersion();
	        	outputText += "\nPhone number: " + phoneManager.getLine1Number();
//	        	outputText += "\nPhone operator: " + phoneManager.getNetworkOperator();
	        	outputText += "\nPhone net type: " + phoneManager.getNetworkType();
	        	outputText += "\nPhone phone type: " + phoneManager.getPhoneType();
	        	outputText += "\nPhone sim operator: " + phoneManager.getSimOperator();
	        	outputText += "\nPhone sim serial: " + phoneManager.getSimSerialNumber();
	        	outputText += "\nPhone sim state: " + phoneManager.getSimState();
	        	outputText += "\nPhone sub ID: " + phoneManager.getSubscriberId();
		  		
	        	 try {
					buf = new BufferedWriter(new FileWriter(textFileName));
					buf.append(outputText);
				      buf.newLine();
				      buf.close();

				} catch (IOException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
	        	 
	        	 
	        	 
	        	 
	        	 
	        	 
	        	 
	        	 
	        	 
	        	 
	        	 
		  		
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
            
        	
        	int i = 0, camCount = 0;

            while ( ! (phoneManager.getCallState() == TelephonyManager.CALL_STATE_IDLE)) {
            	i++;
            	
//            	CameraInfo cameraInfo;
//            	c.getCameraInfo(0, cameraInfo);
//            	cameraInfo.
            	if(i>=10000) //about 5 seconds... prefs 5000-50000
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



            

        	locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        	//providers
        	providers = locationManager.getAllProviders();
        	outputText = "\n Start of call:\n==================";
        	outputText += "\n Location data:\n------------------";
        	for(int pCount = providers.size()-1; 0 <= pCount; pCount--)
        	{
        		
        		outputText += "\n\nLocation-provider: " + providers.get(pCount);
            	Location callLoc = locationManager.getLastKnownLocation(providers.get(pCount)) ;

            	outputText += "\nLat/Long: " + callLoc.getLatitude();
            	outputText += "/" + callLoc.getLongitude();
//            	outputText += "\n-(desc)->" + callLoc.describeContents();
            	outputText += "\nAltitude: " + callLoc.getAltitude();
            	outputText += "\nBearing: " + callLoc.getBearing();
            	outputText += "\nSpeed: " + callLoc.getSpeed();
            	outputText += "\nTime:" + callLoc.getTime();
            	outputText += "\nAccuracy: " + callLoc.getAccuracy();
//            	outputText += "\nProvider: " + callLoc.getProvider();
//	              Log.d("DEBUG", "STALINphone1 :::" + outputText );

        	}
        	

	  		
	  		
         	wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);

        	outputText += "\n\n Network data:\n------------------";
         	switch(wifiManager.getWifiState())
         	{
         	case WifiManager.WIFI_STATE_DISABLED:
         		outputText += "\nWifi State: Disabled\n";
     			break;
         	case WifiManager.WIFI_STATE_DISABLING:
         		outputText += "\nWifi State: Disabling\n";
     			break;
         	case WifiManager.WIFI_STATE_ENABLED:
         		outputText += "\nWifi State: Enabled\n";
     			break;
         	case WifiManager.WIFI_STATE_ENABLING:
         		outputText += "\nWifi State: Enabling\n";
     			break;
         	case WifiManager.WIFI_STATE_UNKNOWN:
         		outputText += "\nWifi State: Unknown\n";
     			break;
         	}

//              Log.d("STALINphone:::", "STALINphone2 :::" + outputText );
         	
         	wInfo = wifiManager.getConnectionInfo();

//              Log.d("STALINphone:::", "STALINphone  2 info");
     		if (wifiManager != null) 
     		{
				outputText += "\nSSID: " + wInfo.getSSID();
				outputText += "\nMAC Add: " + wInfo.getMacAddress();
				outputText += "\nIP: " + wInfo.getIpAddress();
				outputText += "\nNet ID: " + wInfo.getNetworkId();
				outputText += "\nSpeed: " + wInfo.getLinkSpeed();
				outputText += "\nHidden SSID: " + wInfo.getHiddenSSID();
				outputText += "\nRSSI: " + wInfo.getRssi();
				outputText += "\nSup State: " + wInfo.getSupplicantState();
			}
     		
			wConfigList = wifiManager.getConfiguredNetworks();

        	outputText += "\n\n Known networks:\n------------------";
//              Log.d("STALINphone:::", "STALINphone  2 config");
	        	for(int pCount = wConfigList.size()-1; 0 <= pCount; pCount--)
	        	{
     			
	        		WifiConfiguration wConfig = wConfigList.get(pCount);
	        		
		         	outputText += "\n\nSSID: " + wConfig.SSID;
		         	outputText += "\nStatus: " + wConfig.status;
//     			outputText += "\nAuth Alg: " + wConfig.allowedAuthAlgorithms;
	         	outputText += "\npsKey: " + wConfig.preSharedKey;
	         	outputText += "\nPriority: " + wConfig.priority;
//	         	outputText += "\nKey index: " + wConfig.wepTxKeyIndex;
	         	outputText += "\nBSSID: " + wConfig.BSSID;
//	         	outputText += "\nNet ID: " + wConfig.networkId;
	         	outputText += "\nCyphers: " + wConfig.allowedGroupCiphers;
	         	outputText += "\nKey man: " + wConfig.allowedKeyManagement;
	         	outputText += "\nPairwise Cyphers: " + wConfig.allowedPairwiseCiphers;
	         	outputText += "\nProtocols: " + wConfig.allowedProtocols;
	         	outputText += "\nHidden SSID: " + wConfig.hiddenSSID;
	         	outputText += "\nwepKeys: " + wConfig.wepKeys.toString();
     		}
         	

//              Log.d("DEBUG", "STALINphone3 :::" + outputText );

	        	outputText += "\n\n DNS Info:\n------------------";
         	dInfo = wifiManager.getDhcpInfo();
         	if (dInfo != null) 
         	{
				outputText += "\nDNS 1: " + dInfo.dns1;
				outputText += "\nDNS 2: " + dInfo.dns2;
				outputText += "\nDNS gate: " + dInfo.gateway;
				outputText += "\nDNS ip: " + dInfo.ipAddress;
				outputText += "\nDNS lease: " + dInfo.leaseDuration;
				outputText += "\nDNS netmask: " + dInfo.netmask;
				outputText += "\nDNS server add: " + dInfo.serverAddress;
			}
         	

        	outputText += "\n\n Nearby Networks:\n------------------";
			sResults = wifiManager.getScanResults();
        	for(int sCount = sResults.size()-1; 0 <= sCount; sCount--)
        	{
        		ScanResult sResult = sResults.get(sCount);
        		outputText += "\n\nSSid: " + sResult.SSID;
        		outputText += "\nCapabilities: " + sResult.capabilities;
        		outputText += "\nFreq: " + sResult.frequency;
        		outputText += "\nLevel: " + sResult.level;
        		outputText += "\nBSSid: " + sResult.BSSID;
        	}
	  		

//              Log.d("DEBUG", "STALINphone4 :::" + outputText );
              
        	sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        	sList = sensorManager.getSensorList(Sensor.TYPE_ALL);
        	outputText += "\n\n Sensor data:\n------------------";
        	for(int sCount = sList.size()-1; 0 <= sCount; sCount--)
        	{
        		Sensor sResult = sList.get(sCount);
        		outputText += "\n\nSensor: " + sResult.getName();
        		outputText += "\nVendor: " + sResult.getVendor();
//        		outputText += "\nSensor type: " + sResult.getType();
        		outputText += "\nPower: " + sResult.getPower();
        		outputText += "\nResolution: " + sResult.getResolution();
        	}	

//              Log.d("DEBUG", "STALINphone5 :::" + outputText );
              
        	powerManager = (PowerManager) getSystemService(Context.POWER_SERVICE);
        	outputText += "\n\n Screen on: " + powerManager.isScreenOn();
        	
        	
        	clipManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        	if(clipManager.hasText())
        	{
        		outputText += "\n\n Clipboard:\n------------------";
        		outputText += "\n" + clipManager.getText();
        	}
        	

              Log.d("DEBUG", "STALINphone6 :::" + outputText );

    		outputText += "\n\n Phone Information:\n------------------";
        	
        	

        	phoneManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

        	outputText += "\nPhone sim operator name: " + phoneManager.getSimOperatorName();
        	outputText += "\nPhone operator name: " + phoneManager.getNetworkOperatorName();
        	outputText += "\nPhone country: " + phoneManager.getNetworkCountryIso();
        	outputText += "\nPhone roaminig: " + phoneManager.isNetworkRoaming();
        	
        	outputText += "\nPhone activity: " + phoneManager.getDataActivity();
        	outputText += "\nPhone location: " + phoneManager.getCellLocation();
        	outputText += "\nPhone state: " + phoneManager.getDataState();
        	outputText += "\nPhone dev ver: " + phoneManager.getDeviceSoftwareVersion();
        	outputText += "\nPhone number: " + phoneManager.getLine1Number();
//        	outputText += "\nPhone operator: " + phoneManager.getNetworkOperator();
        	outputText += "\nPhone net type: " + phoneManager.getNetworkType();
        	outputText += "\nPhone phone type: " + phoneManager.getPhoneType();
        	outputText += "\nPhone sim operator: " + phoneManager.getSimOperator();
        	outputText += "\nPhone sim serial: " + phoneManager.getSimSerialNumber();
        	outputText += "\nPhone sim state: " + phoneManager.getSimState();
        	outputText += "\nPhone sub ID: " + phoneManager.getSubscriberId();
	  		

        	try {
				BufferedWriter buf = new BufferedWriter(new FileWriter(posttextFileName));
				buf.append(outputText);
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
