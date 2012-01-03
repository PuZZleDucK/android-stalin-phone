package com.puzzleduck.StalinPhone;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import android.app.Activity;
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
import android.hardware.Camera.Size;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationManager;
import android.media.CamcorderProfile;
import android.media.MediaRecorder;
import android.net.DhcpInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Binder;
import android.os.Environment;
import android.os.IBinder;
import android.os.Looper;
import android.os.Parcel;
import android.os.PowerManager;
import android.os.RemoteException;
import android.telephony.TelephonyManager;
import android.text.ClipboardManager;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.RemoteViews;
import android.widget.VideoView;

public class StalinRecService extends Service {
    private NotificationManager mNM;
//    private static Date now;
    private File pictureFile;
    private BufferedWriter buf;
    Notification notification;
    @Override
    public void onCreate() {
    	
    	//create custom view with video surface
    	RemoteViews contentView = new RemoteViews(getPackageName(), R.layout.stalinvideonotification);

    	contentView.setImageViewResource(R.id.videoView1, R.layout.stalinvideonotification);
    	contentView.setTextViewText(R.id.title, "Custom notification");
    	contentView.setTextViewText(R.id.text, "This is a custom layout");
    	
    	
    	
    	
    	
    	
    	
//    	Log.d("StalinPhone ::: ", "starting REC service...");
        mNM = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        //new vid notification 
        notification = new Notification();
        notification.contentView = contentView;	
        
        Intent notificationIntent = new Intent(this, StalinRecService.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
        notification.contentIntent = contentIntent;
        
        mNM.notify(1, notification);
 
        
        
        // show the icon in the status bar-old
//        showNotification();

        
        // Start up the thread running the service.
        Thread thr = new Thread(null, mTask, "StalinRecService");
//    	thr.runOnUiThread();

//    	Log.d("StalinPhone ::: ", "starting REC service thread");
        thr.start();
    }

    @Override
    public void onDestroy() {
    	

        // Cancel the notification -- we use the same ID that we had used to start it
        mNM.cancel(R.string.stalin_rec_service_started);
    }

    /**
     * The function that runs in our worker thread
     */
//  runOnUiThread receives a runnable object, and you 
//  don't have to worry about starting it, that's done by the OS for you.
  
    Runnable mTask = new Runnable() {
        public void run() {

//            Looper.prepare();

//            mHandler = new Handler() {
//                public void handleMessage(Message msg) {
//                    // process incoming messages here
//                }
//            };
        	
        	
//        	When using this interface from a thread 
//        	other than the one running its SurfaceView, 
//        	you will want to carefully read the methods 
//        	lockCanvas() and Callback.surfaceCreated(). 
        	
        	
//        	SurfaceHolder holder = StalinPhoneActivity.videoView.getHolder();
            Log.d("DEBUG", "STALINphone-stalinCAM ::: pre-context" );
            Context thisContext = getBaseContext();
            Log.d("DEBUG", "STALINphone-stalinCAM ::: context-name: " + thisContext.getApplicationInfo().name );
            Log.d("DEBUG", "STALINphone-stalinCAM ::: pre-get holder" );

//        	SurfaceHolder holder = new VideoView(thisContext).getHolder();
        	SurfaceHolder holder = (SurfaceHolder) notification.contentView;//need to extend my own notification?!?!   damn
//        	SurfaceHolder holder = notification.contentView.setImageViewResource(viewId, srcId);
        	
            Log.d("DEBUG", "STALINphone-stalinCAM ::: get holder" );
        	StalinHandler handler = new StalinHandler();
            Log.d("DEBUG", "STALINphone-stalinCAM ::: get handler" );
            holder.addCallback(handler);
            Log.d("DEBUG", "STALINphone-stalinCAM ::: attach" );
            holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
            Log.d("DEBUG", "STALINphone-stalinCAM ::: set type" );

//            Looper.loop();

        	
        	

//        	Context context = StalinRecService.this.getApplicationContext();

        	
//        	StalinRecService.now = new Date();
        	Calendar cal = Calendar.getInstance();
        	String apm;

        	String recFileName = Environment.getExternalStorageDirectory().getAbsolutePath();
        	recFileName += "/StalinVid/";
            recFileName += "record_" + cal.get(Calendar.YEAR)  + "-"//(StalinRecService.now.getYear() + 1900)
	  		 + cal.get(Calendar.MONTH) + "-" //StalinRecService.now.getMonth()
	  		 + (cal.get(Calendar.DAY_OF_MONTH))  + "__"//StalinRecService.now.getDay()
	  		 + cal.get(Calendar.HOUR)  + "."//StalinRecService.now.getHours()
	  		 + cal.get(Calendar.MINUTE) + "."//StalinRecService.now.getMinutes() 
	  		 + cal.get(Calendar.SECOND) + " ";//StalinRecService.now.getSeconds()
            
        	if(cal.get(Calendar.AM_PM)==0)
        	{
        		recFileName += "AM" + "/";
//   	  		 + apm + "/";//StalinRecService.now.getSeconds()
        	}else
        	{
        		recFileName += "PM" + "/";
//   	  		 + apm + "/";//StalinRecService.now.getSeconds()
        	}

            File myNewFolder = new File(recFileName);
            myNewFolder.mkdirs();

//            Log.d("DEBUG", "STALINphone0 :::"  );
            
	  		 String textFileName = recFileName;
	  		 String posttextFileName = recFileName;
	  		 String vidFileName = recFileName;
	  		String picFileName = recFileName + "img-cap.";
		  		recFileName +=  "audio.3gp";
		  		textFileName +=  "info-start.txt";
		  		posttextFileName +=  "info-end.txt";
		  		vidFileName +=  "video.mp4";
//		  		String outputText = "";

		  		TelephonyManager phoneManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
	        	 
		  		writePhoneStateData(textFileName, "Start");
		  		
//	            Log.d("DEBUG", "STALINphone06 :::"  );
	        	 
	        	 
	        	 
	        	 
	        	 
//	        	/** Check if this device has a camera */
//	        	Context context = StalinRecService.this.getApplicationContext();
//	        	    if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)){
//	        	        // this device has a camera
//	        	    } else {
//	        	        // no camera on this device
//	        	    }   	 
	        	 
	        	 
	        	 
		  		//removing temp for vid
//        	MediaRecorder myAudioRecorder = new MediaRecorder();
//        	myAudioRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);//only mic working
////        	myAudioRecorder.setAudioSource(MediaRecorder.AudioSource.CAMCORDER);//only switching to cc for in & out working
//        	//add prefs for this:
//        	myAudioRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
//        	myAudioRecorder.setOutputFile(recFileName);
//        	myAudioRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
//        	
//        	try {
//				myAudioRecorder.prepare();
//			} catch (IllegalStateException e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			} catch (IOException e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			}
//
//        	myAudioRecorder.start();
        	
        	
//            Detect and Access Camera - Create code to check for the existence of cameras and request access.

		  		
		  		
		  		
		  		
//default camera app debug:
//		  		D/CameraSettings(21476): 
//		  			Parameters: [
//		  			             antibanding-values=off,50hz,60hz,auto, 
//		  			             antibanding=auto, 
//		  			             brightness-def=3, 
//		  			             brightness-max=6, 
//		  			             brightness-min=0, 
//		  			             brightness=3, 
//		  			             cam-mode=1, 
//		  			             contrast-def=5, 
//		  			             contrast-max=10, 
//		  			             contrast-min=0, 
//		  			             contrast=5, 
//		  			             effect-values=none,mono,negative,solarize,sepia,posterize,aqua, 
//		  			             effect=none, 
//		  			             enable-caf=on, 
//		  			             exposure-compensation-step=0.5, 
//		  			             exposure-compensation=0, 
//		  			             flash-mode-values=off,auto,on,torch, 
//		  			             flash-mode=off, 
//		  			             focal-length=4.31, 
//		  			             focus-mode-values=auto,infinity, 
//		  			             focus-mode=auto, 
//		  			             front-camera-mode-values=mirror,reverse, 
//		  			             front-camera-mode=mirror, 
//		  			             horizontal-view-angle=54.8, 
//		  			             iso-values=auto,deblur,100,200,400,800,1250, 
//		  			             iso=auto, jpeg-quality=100, 
//		  			             jpeg-thumbnail-height=576, 
//		  			             jpeg-thumbnail-quality=75, 
//		  			             jpeg-thumbnail-size-values=768x576,640x480,512x384,0x0, 
//		  			             jpeg-thumbnail-width=768, 
//		  			             max-exposure-compensation=4, 
//		  			             max-zoom=5, 
//		  			             meter-mode-values=meter-average,meter-center,meter-spot, 
//		  			             meter-mode=meter-center, 
//		  			             min-exposure-compensation=-4, 
//		  			             picture-format-values=jpeg, 
//		  			             picture-format=jpeg, 
//		  			             picture-size-values=2592x1952,2592x1728,2592x1552,2560x1920,2560x1712,2048x1536,2048x1360,2048x1216,2016x1344,1600x1200,1584x1056,1280x960,1280x848,1280x768,1248x832,1024x768,640x480,640x416,640x384,624x416,512x384,400x400,272x272, 
//		  			             picture-size=1024x768, 
//		  			             preview-format-values=yuv420sp, 
//		  			             preview-format=yuv420sp, 
//		  			             preview-frame-rate-values=15, 
//		  			             preview-frame-rate=27, 
//		  			             preview-size-values=1280x720,800x480,768x432,720x480,640x480,576x432,480x320,400x240,384x288,352x288,320x240,272x272,240x240,240x160,176x144, 
//		  			             preview-size=720x480, 
//		  			             saturation-def=5, 
//		  			             saturation-max=10, 
//		  			             saturation-min=1, 
//		  			             saturation=5, 
//		  			             sharpness-def=10, 
//		  			             sharpness-max=30, 
//		  			             sharpness-min=0, 
//		  			             sharpness=10, 
//		  			             smart-contrast=off, 
//		  			             taking-picture-zoom-max=20, 
//		  			             taking-picture-zoom-min=0, 
//		  			             taking-picture-zoom=0, 
//		  			             vertical-view-angle=42.5, 
//		  			             whitebalance-values=auto,incandescent,fluorescent,daylight,cloudy-daylight, 
//		  			             whitebalance=auto, 
//		  			             zoom-ratios=100,114,131,151,174,200, 
//		  			             zoom-supported=true, 
//		  			             zoom=0]
//		  				D/QualcommCameraHardware(20027): requested preview size 720 x 480
//		  				D/QualcommCameraHardware(20027): requested picture size 1024 x 768

		  		
		  		
		  		
		  		//fresh start2
	            Camera c = null;
	            try {
	                c = Camera.open(); // attempt to get a Camera instance
//	                Log.d("DEBUG", "STALINphone-stalinCAM ::: open cam" );
	            }
	            catch (Exception e){
					e.printStackTrace();
	                // Camera is not available (in use or does not exist)
	            }       
	            
	            

	            MediaRecorder myVideoRecorder = new MediaRecorder();
	            
//	            myVideoRecorder.setPreviewDisplay(null);
	              Log.d("DEBUG", "STALINphone-stalinCAM ::: pre new preview" );
	            myVideoRecorder.setPreviewDisplay((Surface) holder);
	              Log.d("DEBUG", "STALINphone-stalinCAM ::: post new preview" );
	            

	            Camera.Parameters cParams = c.getParameters();
	            cParams.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);//off
	            cParams.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_VIDEO);//256
//	            cParams.setPictureFormat(Camera.Parameters.???);
//	            cParams.setSceneMode(Camera.Parameters.SCENE_MODE_SPORTS);//
	            cParams.setPictureSize(640, 480);// android.hardware.Camera$Size@1ff2700
	            cParams.setPreviewSize(640, 480);

	            c.setParameters(cParams);
	            

              Log.d("DEBUG", "STALINphone-stalinCAM ::: prev start" );
              c.startPreview();
              

            c.unlock();
            Log.d("DEBUG", "STALINphone-stalinCAM ::: unlock" );

            
          Log.d("DEBUG", "STALINphone-stalinCAM ::: set-cam" );
          myVideoRecorder.setCamera(c);
            

          myVideoRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
	            myVideoRecorder.setAudioSource(MediaRecorder.AudioSource.DEFAULT);

		  		
	            myVideoRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);//output format
           

	            Log.d("DEBUG", "STALINphone-stalinCAM ::: size" );
            myVideoRecorder.setVideoSize(640, 480); //still to try
              
              
            myVideoRecorder.setVideoFrameRate(20); //might be auto-determined due to lighting
            myVideoRecorder.setVideoEncodingBitRate(3000000);// 3 megapixel, or the max of

            Log.d("DEBUG", "STALINphone-stalinCAM ::: enc" );
            myVideoRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.H264);// MPEG_4_SP

            myVideoRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_WB);
            myVideoRecorder.setAudioSamplingRate(16000);

            Log.d("DEBUG", "STALINphone-stalinCAM ::: dur" );
            myVideoRecorder.setMaxDuration(30000); // limit to 30 seconds

            Log.d("DEBUG", "STALINphone-stalinCAM ::: prev2" );
            myVideoRecorder.setPreviewDisplay(null);

            Log.d("DEBUG", "STALINphone-stalinCAM ::: file" );
            myVideoRecorder.setOutputFile(vidFileName);//output file.
            
            try {
				myVideoRecorder.prepare();
			} catch (IllegalStateException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
            Log.d("DEBUG", "STALINphone-stalinCAM ::: start__________________" );
            myVideoRecorder.start();


	            
	            
	            
	            
	            
	            
	            
              
	            
	            
//	            CamcorderProfile cProfile = CamcorderProfile.get(CamcorderProfile.QUALITY_LOW);
//	            
//
//	            Log.d("DEBUG", "STALINphone-stalinCAM ::: new set profile" );
//	            myVideoRecorder.setProfile( cProfile );
//	            
//
//	            myVideoRecorder.setOutputFile(vidFileName);//output file.
//	            Log.d("DEBUG", "STALINphone-stalinCAM ::: file" );
//	            
//	            
//	            myVideoRecorder.setMaxDuration(50000); // 50 seconds
//	            myVideoRecorder.setMaxFileSize(5000000); // Approximately 5 megabytes
//	            Log.d("DEBUG", "STALINphone-stalinCAM ::: sizes" );
//
//	            
////	            myVideoRecorder.setPreviewDisplay(holder.getSurface());
//
//	            try {
//	            	myVideoRecorder.prepare();
//		            Log.d("DEBUG", "STALINphone-stalinCAM ::: prep  --------------------------------------------" );
//	            } catch (IllegalStateException e) {
//	                e.printStackTrace();
//	            } catch (IOException e) {
//	                e.printStackTrace();
//	            }
//
//	            
//
//	            Log.d("DEBUG", "STALINphone-stalinCAM ::: start" );
//	            myVideoRecorder.start();
	            
	            
	            
	            
	            

//
    
//            
//            
//// KEEP... USEFULL INFO       KEEP... USEFULL INFO      KEEP... USEFULL INFO      
//            Camera.Parameters cParams = c.getParameters();
////            Log.d("DEBUG", "STALINphone-stalinCAM ::: param:" + cParams.flatten() );
////            Log.d("DEBUG", "STALINphone-stalinCAM ::: param flash:" + cParams.getFlashMode() );//off
//            Log.d("DEBUG", "STALINphone-stalinCAM ::: param focus:" + cParams.getFocusMode() );//256
//////            Log.d("DEBUG", "STALINphone-stalinCAM ::: param:" + cParams.getHorizontalViewAngle() );
////            Log.d("DEBUG", "STALINphone-stalinCAM ::: param format:" + cParams.getPictureFormat() );
////            Log.d("DEBUG", "STALINphone-stalinCAM ::: param mode:" + cParams.getSceneMode() );//
////            Log.d("DEBUG", "STALINphone-stalinCAM ::: param size:" + cParams.getPictureSize() );// android.hardware.Camera$Size@1ff2700
////            Log.d("DEBUG", "STALINphone-stalinCAM ::: param flash:" + cParams.getSupportedFlashModes() );//[off, auto, on, torch]
////            Log.d("DEBUG", "STALINphone-stalinCAM ::: param Focus modes:" + cParams.getSupportedFocusModes() );//[auto, infinity]
////            Log.d("DEBUG", "STALINphone-stalinCAM ::: param formats:" + cParams.getSupportedPictureFormats() );
////            Log.d("DEBUG", "STALINphone-stalinCAM ::: param sizes:" + cParams.getSupportedPictureSizes() );
////            List<Size> sizes = cParams.getSupportedPictureSizes();
////            for(int i = sizes.size()-1; i >=0; i--)
////            {
////            	 Log.d("DEBUG", "STALINphone-stalinCAM ::: param sizes:   - [" + sizes.get(i).width + " x " + sizes.get(i).height + " ] ");
////            }
////            Log.d("DEBUG", "STALINphone-stalinCAM ::: param Scene modes:" + cParams.getSupportedSceneModes() );
////            Log.d("DEBUG", "STALINphone-stalinCAM ::: param zoom:" + cParams.isZoomSupported() );//true
////            Log.d("DEBUG", "STALINphone-stalinCAM ::: param s-zoom:" + cParams.isSmoothZoomSupported() );//false
//     
//            cParams.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);//off
//            cParams.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_VIDEO);//256
////            cParams.setPictureFormat(Camera.Parameters.???);
////            cParams.setSceneMode(Camera.Parameters.SCENE_MODE_SPORTS);//
//            cParams.setPictureSize(720, 480);// android.hardware.Camera$Size@1ff2700
//           
//            c.setParameters(cParams);
//             
//            cParams = c.getParameters();
////          Log.d("DEBUG", "STALINphone-stalinCAM ::: param:" + cParams.flatten() );
////          Log.d("DEBUG", "STALINphone-stalinCAM ::: param flash:" + cParams.getFlashMode() );//off
//            Log.d("DEBUG", "STALINphone-stalinCAM ::: param focus after set:" + cParams.getFocusMode() );//256
//         
//
//    	    try {
//                Log.d("DEBUG", "STALINphone-stalinCAM ::: null preview" );
//                
////                The SurfaceHolder must already contain a surface when this method is 
////                called. If you are using SurfaceView, you will need to register a 
////                SurfaceHolder.Callback with addCallback(SurfaceHolder.Callback) and 
////                wait for surfaceCreated(SurfaceHolder) before calling setPreviewDisplay() 
////                or starting preview.
//                
////                SurfaceHolder dh = new SurfaceHolder();
//                
//                
//				c.setPreviewDisplay(null);
	            
	            
	            
//                Log.d("DEBUG", "STALINphone-stalinCAM ::: null preview done" );
//			} catch (IOException e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			}
//    	    
//
// 
////            android.hardware.Camera.getCameraInfo(0, cameraInfo);
////          CameraInfo cInfo = null;
////          c.getCameraInfo(0, cInfo);
////          Log.d("DEBUG", "STALINphone-stalinCAM ::: info:" + cInfo.facing );
////          Log.d("DEBUG", "STALINphone-stalinCAM ::: info:" + cInfo.orientation );
//
//    	    
//            
//            
//            
//

//            Log.d("DEBUG", "STALINphone-stalinCAM ::: cam" );
////            myVideoRecorder.setAudioSource(MediaRecorder.AudioSource.CAMCORDER);
////            Log.d("DEBUG", "STALINphone-stalinCAM ::: aud" );
////            myVideoRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
////            Log.d("DEBUG", "STALINphone-stalinCAM ::: vid" );
//
//            //AMR_ for audio only...
////            myVideoRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);//output format
//            
//            Log.d("DEBUG", "STALINphone-stalinCAM ::: duration" );
//            myVideoRecorder.setMaxDuration(600000);
//
//
//
////          //old style
////            //extra
////            myVideoRecorder.setVideoFrameRate(27);
////            Log.d("DEBUG", "STALINphone-stalinCAM ::: frame" );
////            
////            myVideoRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);//sound enc
//////            myVideoRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);//sound encoding.
////            myVideoRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.H263);//video enc
//////            myVideoRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.MPEG_4_SP);//video encoding type.
//            //new style
//
//
//            
//            myVideoRecorder.setPreviewDisplay(null);
//            Log.d("DEBUG", "STALINphone-stalinCAM ::: null preview 2!!!!!" );
//            
//            myVideoRecorder.setMaxFileSize(60000000);
//
//            
//
//            try {
//				myVideoRecorder.prepare();
//	            Log.d("DEBUG", "STALINphone-stalinCAM ::: prep" );
//			} catch (IllegalStateException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//            Log.d("DEBUG", "STALINphone-stalinCAM ::: post prep" );
//            
//            
//            
//
//            myVideoRecorder.start();
//            Log.d("DEBUG", "STALINphone-stalinCAM ::: ____start____" );
    	    
            //remove for video
//            c.startPreview();

            //you can get further information about its capabilties using the Camera.getParameters() method and checking the returned Camera.Parameters object
        	int i = 0, camCount = 0;

            while ( ! (phoneManager.getCallState() == TelephonyManager.CALL_STATE_IDLE)) {
            	i++;
            	
//            	CameraInfo cameraInfo;
//            	c.getCameraInfo(0, cameraInfo);
//            	cameraInfo.
            	
//            	//removed for video
//            	if(i>=10000) //about 5 seconds... prefs 5000-50000
//            	{
//            		i=0;
//            		camCount++;
////            		Capture and Save Files - Setup the code for capturing pictures or videos and saving the output.
//            		pictureFile = new File(picFileName + camCount + ".jpg");
//                    c.takePicture(null, null, null, new PictureCallback() {
//                        @Override
//                        public void onPictureTaken(byte[] data, Camera camera) {
//                            if (pictureFile == null){
////                                Log.d("STALIN", "Error creating media file, check storage permissions: ");
//                                return;
//                            }
//                            try {
//                                FileOutputStream fos = new FileOutputStream(pictureFile);
//                                fos.write(data);
//                                fos.close();
//                            } catch (FileNotFoundException e) {
////                                Log.d("STALIN", "File not found: " + e.getMessage());
//                            } catch (IOException e) {
////                                Log.d("STALIN", "Error accessing file: " + e.getMessage());
//                            }
////                            Log.d("DEBUG", "STALINphone-stalinCAM ::: pic CB end" );
//                        }
//                    });// image callback
//            	} // occasional event, eg photo
			}// while not .CALL_STATE_IDLE



            
            
            
            

	  		writePhoneStateData(posttextFileName, "End");
            
	  		//removed for vid
//            Release the Camera - After using the camera, your application must properly release it for use by other applications.
//            c.release();

            //removed to add video
//        	myAudioRecorder.stop();
////            Log.d("DEBUG", "STALINphone ::: stopped my rec" );
//        	myAudioRecorder.release();
        	
            

	  		myVideoRecorder.stop();
            Log.d("DEBUG", "STALINphone-stalinCAM ::: stop" );
	  		myVideoRecorder.reset();
	  		myVideoRecorder.release();
		    c.lock();
		    c.release();
		    
		    
		    
		    
		    
		    
            
            
            
            

            // Cancel the notification -- we use the same ID that we had used to start it
            mNM.cancel(R.string.stalin_rec_service_started);

            
            StalinRecService.this.stopSelf();
        }//mTask
	            
    };
	
	
private void writePhoneStateData(String thisFileName, String which)
{
	String outputText = "";
	LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//	outputText += "\n Location data:\n------------------";
	if (locationManager != null) 
	{
    	outputText += "\n " + which;
    	outputText += " of call:\n==================";
		List<String> providers = locationManager.getAllProviders();
    	if (providers != null) 
    	{
			//providers
			for (int pCount = providers.size() - 1; 0 <= pCount; pCount--) {
				//						Log.d("DEBUG", "STALINphone1.1 :::");
				outputText += "\n\nLocation-provider: "	+ providers.get(pCount) + "\n------------------";
				Location callLoc = locationManager.getLastKnownLocation(providers.get(pCount));

				if (callLoc != null) {
					outputText += "\nLat/Long: " + callLoc.getLatitude();
					outputText += "/" + callLoc.getLongitude();
					//	            	outputText += "\n-(desc)->" + callLoc.describeContents();
					outputText += "\nAltitude: " + callLoc.getAltitude();
					outputText += "\nBearing: " + callLoc.getBearing();
					outputText += "\nSpeed: " + callLoc.getSpeed();
					outputText += "\nTime:" + callLoc.getTime();
					outputText += "\nAccuracy: " + callLoc.getAccuracy();
				}
				//	            	outputText += "\nProvider: " + callLoc.getProvider();
				//						Log.d("DEBUG", "STALINphone1 :::");

			}
		}
	}

//    Log.d("DEBUG", "STALINphone1.5 ::: "  );
	
	WifiManager wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);

	if (wifiManager != null) 
	{
		outputText += "\n\n Network data:\n------------------";
		switch (wifiManager.getWifiState()) {
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
		
		

     	WifiInfo wInfo = wifiManager.getConnectionInfo();

//          Log.d("STALINphone:::", "STALINphone  2 info");
 		if (wInfo != null) 
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

    	if (wConfigList != null) 
    	{
			outputText += "\n\n Known networks:\n------------------";
			//		              Log.d("STALINphone:::", "STALINphone  2 config");
			for (int pCount = wConfigList.size() - 1; 0 <= pCount; pCount--) {

				WifiConfiguration wConfig = wConfigList.get(pCount);

				if (wConfig != null) 
				{
					outputText += "\n\nSSID: " + wConfig.SSID;
					outputText += "\nStatus: " + wConfig.status;
					//	         			outputText += "\nAuth Alg: " + wConfig.allowedAuthAlgorithms;
					outputText += "\npsKey: " + wConfig.preSharedKey;
					outputText += "\nPriority: " + wConfig.priority;
					//			         	outputText += "\nKey index: " + wConfig.wepTxKeyIndex;
					outputText += "\nBSSID: " + wConfig.BSSID;
					//			         	outputText += "\nNet ID: " + wConfig.networkId;
					outputText += "\nCyphers: " + wConfig.allowedGroupCiphers;
					outputText += "\nKey man: " + wConfig.allowedKeyManagement;
					outputText += "\nPairwise Cyphers: " + wConfig.allowedPairwiseCiphers;
					outputText += "\nProtocols: " + wConfig.allowedProtocols;
					outputText += "\nHidden SSID: " + wConfig.hiddenSSID;
					//			         	outputText += "\nwepKeys: " + wConfig.wepKeys.toString();
				}
			}
		}
    	
     	DhcpInfo dInfo = wifiManager.getDhcpInfo();
     	if (dInfo != null) 
     	{
			outputText += "\n\n DNS Info:\n------------------";
			outputText += "\nDNS 1: " + dInfo.dns1;
			outputText += "\nDNS 2: " + dInfo.dns2;
			outputText += "\nDNS gate: " + dInfo.gateway;
			outputText += "\nDNS ip: " + dInfo.ipAddress;
			outputText += "\nDNS lease: " + dInfo.leaseDuration;
			outputText += "\nDNS netmask: " + dInfo.netmask;
			outputText += "\nDNS server add: " + dInfo.serverAddress;
		}
     	

		List<ScanResult> sResults = wifiManager.getScanResults();
    	outputText += "\n\n Nearby Networks:\n------------------";
    	if (sResults != null) {
			for (int sCount = sResults.size() - 1; 0 <= sCount; sCount--) {
				ScanResult sResult = sResults.get(sCount);
				outputText += "\n\nSSid: " + sResult.SSID;
				outputText += "\nCapabilities: "
						+ sResult.capabilities;
				outputText += "\nFreq: " + sResult.frequency;
				outputText += "\nLevel: " + sResult.level;
				outputText += "\nBSSid: " + sResult.BSSID;
			}
		}
  		
		
	}
	

//      Log.d("STALINphone:::", "STALINphone2 :::" + outputText );
 	

//      Log.d("DEBUG", "STALINphone4 :::"  );
      
	SensorManager sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
	List<Sensor> sList;
	if (sensorManager != null) {
		sList = sensorManager.getSensorList(Sensor.TYPE_ALL);
		if (sList != null) {
			outputText += "\n\n Sensor data:\n------------------";
			for (int sCount = sList.size() - 1; 0 <= sCount; sCount--) {
				Sensor sResult = sList.get(sCount);
				outputText += "\n\nSensor: " + sResult.getName();
				outputText += "\nVendor: " + sResult.getVendor();
				//	        		outputText += "\nSensor type: " + sResult.getType();
				outputText += "\nPower: " + sResult.getPower();
				outputText += "\nResolution: "
						+ sResult.getResolution();
			}
		}
	}
	PowerManager powerManager = (PowerManager) getSystemService(Context.POWER_SERVICE);
	if (powerManager != null) {
		outputText += "\n\n Screen on: " + powerManager.isScreenOn();
	}
	
	ClipboardManager clipManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
	if(clipManager != null && clipManager.hasText())
	{
		outputText += "\n\n Clipboard:\n------------------";
		outputText += "\n" + clipManager.getText();
	}
	

//      Log.d("DEBUG", "STALINphone6 :::" + outputText );


	TelephonyManager phoneManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

	if (phoneManager != null) {
		outputText += "\n\n Phone Information:\n------------------";
    	
		outputText += "\nPhone sim operator name: "
				+ phoneManager.getSimOperatorName();
		outputText += "\nPhone operator name: "
				+ phoneManager.getNetworkOperatorName();
		outputText += "\nPhone country: "
				+ phoneManager.getNetworkCountryIso();
		outputText += "\nPhone roaminig: "
				+ phoneManager.isNetworkRoaming();
		outputText += "\nPhone activity: "
				+ phoneManager.getDataActivity();
		outputText += "\nPhone location: "
				+ phoneManager.getCellLocation();
		outputText += "\nPhone state: "
				+ phoneManager.getDataState();
		outputText += "\nPhone dev ver: "
				+ phoneManager.getDeviceSoftwareVersion();
		outputText += "\nPhone number: "
				+ phoneManager.getLine1Number();
		//	        	outputText += "\nPhone operator: " + phoneManager.getNetworkOperator();
		outputText += "\nPhone net type: "
				+ phoneManager.getNetworkType();
		outputText += "\nPhone phone type: "
				+ phoneManager.getPhoneType();
		outputText += "\nPhone sim operator: "
				+ phoneManager.getSimOperator();
		outputText += "\nPhone sim serial: "
				+ phoneManager.getSimSerialNumber();
		outputText += "\nPhone sim state: "
				+ phoneManager.getSimState();
		outputText += "\nPhone sub ID: "
				+ phoneManager.getSubscriberId();
	}
	try {
		buf = new BufferedWriter(new FileWriter(thisFileName));
		buf.append(outputText);
	      buf.newLine();
	      buf.close();

	} catch (IOException e2) {
		// TODO Auto-generated catch block
		e2.printStackTrace();
	}

}
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
