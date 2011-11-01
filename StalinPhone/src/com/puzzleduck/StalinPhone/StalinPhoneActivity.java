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
         
        Log.d("StalinPhone ::: ", "buttons selected");
        findViewById(R.id.start_stalin_trans_service).setOnClickListener( (OnClickListener) this);
        findViewById(R.id.stop_stalin_trans_service).setOnClickListener( (OnClickListener) this);
        Log.d("StalinPhone ::: ", "trans buttons set up");
//        findViewById(R.id.start_stalin_rec_service).setOnClickListener( (OnClickListener) this);
//        findViewById(R.id.stop_stalin_rec_service).setOnClickListener( (OnClickListener) this);
        Log.d("StalinPhone ::: ", "rec buttons set up");
//icon is Stalin Jamming from uncyclopedia :] thanks sock puppet
    } 

    private PendingIntent mTransRequestSender;

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
        if(v.getId() == R.id.stop_stalin_rec_service)
        {
//            Log.d("StalinPhone ::: ", "creating stop-REC intent");
//            mTransRequestSender = PendingIntent.getService(StalinPhoneActivity.this,
//            		0, new Intent(StalinPhoneActivity.this, StalinStopRecService.class), 0);
//
//            Log.d("StalinPhone ::: ", "stop-REC intent created");
//            try {
//    			mTransRequestSender.send();
//    		} catch (CanceledException e) {
//    			// TODO Auto-generated catch block
//    			e.printStackTrace();
//    		}
        }
        if(v.getId() == R.id.start_stalin_trans_service)
        {
            Log.d("StalinPhone ::: ", "button clicked: start t");
//            Log.d("StalinPhone ::: ", "creating intent");
            Log.d("StalinPhone ::: ", "creating TRANS intent");
            mTransRequestSender = PendingIntent.getService(StalinPhoneActivity.this,
            		0, new Intent(StalinPhoneActivity.this, StalinTransService.class), 0);

            Log.d("StalinPhone ::: ", "trans intent created");
            try {
    			mTransRequestSender.send();
    		} catch (CanceledException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
        }
        if(v.getId() == R.id.stop_stalin_trans_service)
        {
            Log.d("StalinPhone ::: ", "button clicked: stop t");
//            Log.d("StalinPhone ::: ", "creating intent");
//    		}
        }

	}//onClick
}//class
