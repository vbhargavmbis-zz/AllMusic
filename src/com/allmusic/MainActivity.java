package com.allmusic;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener
{
	
    public static String stringUrl2=("http://cs-server.usc.edu:28744/examples/servlet/HelloWorldExample?query=taylor&type=artists");
    String resp;
    String query;
    String type;
    
    Button button; 
    Spinner spinner;
    EditText editText;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        
        Button btn= (Button) findViewById(R.id.button1);
        spinner = (Spinner) findViewById(R.id.spinner1);
        editText = (EditText) findViewById(R.id.editText1);
        btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) 
			{
				
				PackageInfo info;
				try {
				    info = getPackageManager().getPackageInfo("com.allmusic", PackageManager.GET_SIGNATURES);
				    for (Signature signature : info.signatures) {
				        MessageDigest md;
				        md = MessageDigest.getInstance("SHA");
				        md.update(signature.toByteArray());
				        String something = new String(Base64.encode(md.digest(), 0));
				        //String something = new String(Base64.encodeBytes(md.digest()));
				        Log.e("------------hash key", something);
				    }
				} catch (NameNotFoundException e1) {
				    Log.e("!!!!!!!name not found", e1.toString());
				} catch (NoSuchAlgorithmException e) {
				    Log.e("no such an algorithm", e.toString());
				} catch (Exception e) {
				    Log.e("exception", e.toString());
				}
				
				
				query  = editText.getText().toString();
				query=query.replace(' ', '+');
			if(query.length()>0)
			{
				type = spinner.getSelectedItem().toString();
				type=type.replace('A', 'a');
				type=type.replace('S', 's');
				System.out.println("QUERY--"+query+"TYPE--"+type);
				String stringUrl="http://cs-server.usc.edu:28744/examples/servlet/HelloWorldExample?query="+query+"&type="+type;
				System.out.println(stringUrl);
				
				Json2 j = new Json2();
				j.execute(new String[] { stringUrl});
								//outputText.setText(j.)
			}
			else
			{
			   Toast.makeText(getApplicationContext(), "Please enter some text", Toast.LENGTH_SHORT).show();
			}
				
			}
		});      
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


	@Override
	public void onClick(DialogInterface arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}
   


 class Json2 extends AsyncTask<String, Void, String> {
	 
    @Override
    protected String doInBackground(String... urls) {
        String output = null;
        for (String url : urls) {
            output = getOutputFromUrl(url);
        }
        return output;
    }
    private String getOutputFromUrl(String url) {
        StringBuffer output = new StringBuffer("");
        try {
            InputStream stream = getHttpConnection(url);
            BufferedReader buffer = new BufferedReader(
                    new InputStreamReader(stream));
            String s = "";
            while ((s = buffer.readLine()) != null)
                output.append(s);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return output.toString();
    }

    private InputStream getHttpConnection(String urlString)
            throws IOException {
        InputStream stream = null;
        URL url = new URL(urlString);
        URLConnection connection = url.openConnection();
        try {
            HttpURLConnection httpConnection = (HttpURLConnection) connection;
            httpConnection.setRequestMethod("GET");
            httpConnection.connect();

            if (httpConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                stream = httpConnection.getInputStream();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return stream;
    }
    @Override
    protected void onPostExecute(String output) {
    	//TextView outputText; 
    	Intent intent= new Intent("com.allmusic.results");
		intent.putExtra("json", output);
		intent.putExtra("query", query);
		intent.putExtra("type", type);
		startActivity(intent);

   	 TextView outputText= (TextView) findViewById(R.id.textView2);
    	System.out.println(output);
      //  outputText.setText(output);
    }
 }
}






