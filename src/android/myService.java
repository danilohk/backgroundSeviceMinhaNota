package com.escola.backgroundService;

import com.escola.EducaOnline.R;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.Notification.Builder;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;


public class myService extends Service{
	String TAG = "BG SERVICE";
	
    @Override
    public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @SuppressLint("NewApi")
	@Override
    public int onStartCommand(Intent intent, int flags, int startId){
        if (intent != null) {
        	 handler.postDelayed(loop,60000);
        }
        //We want this service to continue running until it is explicitly stopped
        return START_REDELIVER_INTENT;
    }
    
    Handler handler = new Handler();
    private Runnable loop = new Runnable(){
        public void run(){
        	doWork();
            handler.postDelayed(loop,60000);
        }
    };
    
    @SuppressLint("NewApi")
	public void doWork(){
    		Log.i(TAG, " DO WORK ");
			 
			int currentapiVersion = android.os.Build.VERSION.SDK_INT;
        	Intent notificationIntent = new Intent(this, com.escola.EducaOnline.plugin.class);
		    PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
		    NotificationManager mNotificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
	         
		    if (currentapiVersion < android.os.Build.VERSION_CODES.HONEYCOMB){
        		Notification notification;
	        	notification = new Notification(R.drawable.icon, "teste de notificacao", 0);
        		notification.setLatestEventInfo(this, "MINHA NOTA", "teste de notificacao", contentIntent);
        		notification.flags = Notification.FLAG_AUTO_CANCEL;
        		mNotificationManager.notify(0, notification);

        	} else {
		       //NOTIFICACAO
				Builder notification = new Notification.Builder(this)
			    .setContentTitle("MINHA NOTA")
			    .setContentText("teste de notificacao")
			    .setSmallIcon(R.drawable.icon)
			    // .setStyle(new Notification.BigTextStyle()
			    // .bigText(texto))
			    .setDefaults(-1)
				.setAutoCancel (true);
			    notification.setContentIntent(contentIntent);
			   
			    mNotificationManager.notify(1, notification.build());
        	}
    }
    
    @Override
    public boolean stopService(Intent intent) {
        return super.stopService(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }    
}
