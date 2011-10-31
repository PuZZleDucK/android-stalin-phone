package com.puzzleduck.StalinPhone;

import android.app.Activity;
import android.app.PendingIntent;
import android.app.PendingIntent.CanceledException;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.AdapterView.OnItemClickListener;
 
public class StalinPhoneActivity extends Activity implements OnClickListener {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
         
//        Button startButton = (Button) findViewById(R.id.start_stalin_trans_service);
  
//        Button button = (Button)findViewById(R.id.start_stalin_trans_service);
        Log.d("StalinPhone ::: ", "buttons selected");
//        button.setOnClickListener((OnClickListener) this);//BUG!!!
        //or ((AutoCompleteTextView) findViewById(R.id.autoCompleteRegion)).setOnItemClickListener(aRegionClickListener);
//        button.setOnKeyListener(mStartTranslateListener);
        findViewById(R.id.start_stalin_trans_service).setOnClickListener( (OnClickListener) this);
//        Log.d("StalinPhone ::: ", "button 1 set up");
        findViewById(R.id.stop_stalin_trans_service).setOnClickListener( (OnClickListener) this);
        Log.d("StalinPhone ::: ", "trans buttons set up");
        findViewById(R.id.start_stalin_rec_service).setOnClickListener( (OnClickListener) this);
        findViewById(R.id.stop_stalin_rec_service).setOnClickListener( (OnClickListener) this);
        Log.d("StalinPhone ::: ", "rec buttons set up");
//        Button button = (Button)findViewById(R.id.start_stalin_trans_service);
        // Register the onClick listener with the implementation above
//        button.setOnClickListener(startStalinClickListener);

        
        
        
//        button = (Button)findViewById(R.id.stopTransService);
//        button.setOnClickListener((android.view.View.OnClickListener) mStopTranslateListener);
    
    
    
    } 

    private PendingIntent mTransRequestSender;

	@Override
	public void onClick(View v) {
        Log.d("StalinPhone ::: ", "button clicked: id" + v.getId());
      //start/kill service

        if(v.getId() == R.id.start_stalin_rec_service)
        {
//            Log.d("StalinPhone ::: ", "button clicked: start rec");

            Log.d("StalinPhone ::: ", "creating rec intent");

            mTransRequestSender = PendingIntent.getService(StalinPhoneActivity.this,
            		0, new Intent(StalinPhoneActivity.this, StalinRecService.class), 0);

            Log.d("StalinPhone ::: ", "rec intent created");
            try {
    			mTransRequestSender.send();
    		} catch (CanceledException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}        
        }
        if(v.getId() == R.id.stop_stalin_rec_service)
        {
//            Log.d("StalinPhone ::: ", "button clicked: stop rec");
            Log.d("StalinPhone ::: ", "creating stop-rec intent");

            mTransRequestSender = PendingIntent.getService(StalinPhoneActivity.this,
            		0, new Intent(StalinPhoneActivity.this, StalinStopRecService.class), 0);

            Log.d("StalinPhone ::: ", "stop-rec intent created");
            try {
    			mTransRequestSender.send();
    		} catch (CanceledException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
        }
        if(v.getId() == R.id.start_stalin_trans_service)
        {
            Log.d("StalinPhone ::: ", "button clicked: start t");
//            Log.d("StalinPhone ::: ", "creating intent");
//
//            mTransRequestSender = PendingIntent.getService(StalinPhoneActivity.this,
//            		0, new Intent(StalinPhoneActivity.this, StalinTransService.class), 0);
//
//            Log.d("StalinPhone ::: ", "intent created");
//            try {
//    			mTransRequestSender.send();
//    		} catch (CanceledException e) {
//    			// TODO Auto-generated catch block
//    			e.printStackTrace();
//    		}
        }
        if(v.getId() == R.id.stop_stalin_trans_service)
        {
            Log.d("StalinPhone ::: ", "button clicked: stop t");
//            Log.d("StalinPhone ::: ", "creating intent");
//
//            mTransRequestSender = PendingIntent.getService(StalinPhoneActivity.this,
//            		0, new Intent(StalinPhoneActivity.this, StalinTransService.class), 0);
//
//            Log.d("StalinPhone ::: ", "intent created");
//            try {
//    			mTransRequestSender.send();
//    		} catch (CanceledException e) {
//    			// TODO Auto-generated catch block
//    			e.printStackTrace();
//    		}
        }


	}
}

// receiving Intent { act=android.intent.action.NEW_OUTGOING_CALL (has extras) }