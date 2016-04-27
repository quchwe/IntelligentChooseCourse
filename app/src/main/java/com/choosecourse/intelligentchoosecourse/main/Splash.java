package com.choosecourse.intelligentchoosecourse.main;



import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;

import com.choosecourse.intelligentchoosecourse.R;

public class Splash extends Activity {
	private final int SPLASH_DISPLAY_LENGHT = 2000; //
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.splash);
        new Handler().postDelayed(new Runnable(){
         @Override
         public void run() {

             Intent mainIntent = new Intent(Splash.this,LoginActivity.class);
             Splash.this.startActivity(mainIntent);
             Splash.this.finish();
         }
             
        }, SPLASH_DISPLAY_LENGHT);

    }
 /*   @Override 
	 protected void onNewIntent(Intent intent)
	{ // TODO Auto-generated method stub super.onNewIntent(intent); 
		//�˳�          
		if ((Intent.FLAG_ACTIVITY_CLEAR_TOP & intent.getFlags()) != 0) { 
			
			finish();      
			} 
	
}*/

}



