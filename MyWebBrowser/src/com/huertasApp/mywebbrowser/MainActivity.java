package com.huertasApp.mywebbrowser;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;


@SuppressLint("SetJavaScriptEnabled") public class MainActivity extends Activity implements OnClickListener   {
	
	//String url = "https://www.temple.edu/";
	WebView myBrowser;
	EditText url_input;
	Button btnGO;
	ImageButton btnBack,btnForw;
	String StrUrl = "";
    
 
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    
        
        // Set up the Url field text
        url_input = (EditText)findViewById(R.id.Et_URL);
        
        // Set up the buttons
        btnBack = (ImageButton)findViewById(R.id.btnBackID);
        btnForw = (ImageButton)findViewById(R.id.btnForwID);
        btnGO = (Button)findViewById(R.id.btnGo);
        
        
        myBrowser = (WebView)findViewById(R.id.wvBrowser);
        myBrowser.getSettings().setBuiltInZoomControls(true);
        myBrowser.getSettings().setJavaScriptEnabled(true);
        myBrowser.setWebViewClient(new WebViewClient());
        //myBrowser = new WebView(this);
       // setContentView(myBrowser);
  
       
		
        btnBack.setOnClickListener(this);
        btnForw.setOnClickListener(this);
        btnGO.setOnClickListener(this);
        
        
        
        
    }
    
   
    
    
    @SuppressLint("HandlerLeak") Handler handler = new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
           
        	String html = (String) msg.obj; //Extract the string from the Message
        	Log.e("",html);
            
            // text will contain the string "your string message"
        	
    		//myBrowser.loadData(html, "text/html", "UTF-8");
    		
    		myBrowser.loadDataWithBaseURL(StrUrl, html, "text/html", "UTF-8","about:blank");
        }
    };
    

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		
		
		switch(arg0.getId()){
			
			case R.id.btnGo: 
				
				
				if(isOnline()){
					
					Thread loadContent = new Thread(){
						
						@Override
						public void run(){
							
						URL url = null;
							
						try{
							
							url = new URL(url_input.getText().toString());
							StrUrl = url.toString();
							BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
							
							String response = "",tmpResponse = "";
							tmpResponse = reader.readLine();
							while(tmpResponse != null){
								response = response + tmpResponse;
								tmpResponse = reader.readLine();
								
							} // end while loop
							
							
					
							
							//// Transfer the message to the handler
							
							Log.e("Browser output",response);
							Message msg = Message.obtain();
							msg.obj = response;
							msg.setTarget(handler); // Set the Handler
							msg.sendToTarget(); //Send the message
							
				
							
							//showContent.sendMessage(msg);
							
						}catch(Exception e){
							e.printStackTrace();
							
						}
							
							
							
						} // End run function
					
					}; // End Thread

					loadContent.start();
				
				}else{
					
					alertDialog("You don't have an internet connection");
				}
				
		
				
		        break;
	
		    case R.id.btnBackID:
		    		
		    	  
		        break;
				
		    case R.id.btnForwID:
		    		
		    	
		    	  
			    break;
			 default:
		} // End switch block
		
	} // End onclick function
	
	
	
	
	
	
	
	public boolean isOnline() {
	    ConnectivityManager cm =
	        (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo netInfo = cm.getActiveNetworkInfo();
	    if (netInfo != null && netInfo.isConnectedOrConnecting()) {
	        return true;
	    }
	    return false;
	}
   
	
	
	public void alertDialog(String Message){
		
		
		 new AlertDialog.Builder(this)
		    .setTitle("TaxiCab Message")
		    .setMessage(Message)
		    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
		        public void onClick(DialogInterface dialog, int which) { 
		        	
		         // Include function if you need it on this event
		        
		        }
		     })
		     /*
			    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
			        public void onClick(DialogInterface dialog, int which) { 
			            // do nothing
			        }
			     })
		     */
		    .setIcon(android.R.drawable.ic_dialog_alert)
		     .show();
		
	}
	
	
	
	
	
} // end class



