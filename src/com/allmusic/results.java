package com.allmusic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.ExecutionException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.R.drawable;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import com.facebook.*;
import com.facebook.model.*;
import com.facebook.widget.WebDialog;
import com.facebook.widget.WebDialog.OnCompleteListener;

public class results extends Activity{

	JSONObject j;
	String jsonString;
	JSONObject results, jObject;
	JSONArray resultArray;
	String type,query;
	static String img[];
	int x;
	 public static final String stringUrl=("http://cs-server.usc.edu:28744/examples/servlet/HelloWorldExample?query=taylor&type=artists");
		@Override
		protected void onCreate(Bundle savedInstanceState) {
			// TODO Auto-generated method stub
			super.onCreate(savedInstanceState);
			setContentView(R.layout.results);
		//	setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, names));
		  
			
			System.out.println("After openactive session");
			Bundle extras = getIntent().getExtras();
			jsonString = extras.getString("json");
			
			query=extras.getString("query");
			type=extras.getString("type");
			System.out.println("JSON--------"+jsonString);
			if(jsonString.length()<1)
			{
				System.out.println("No Discography Found!");
				
				TableLayout tl = new TableLayout(getApplicationContext());
				final TableRow tr=new TableRow(getApplicationContext());
				 TextView b=new TextView(results.this);
                 b.setText("No Discography Found");
                 //img[i]=j.getString("name").trim();
                 b.setPadding(28, 100, 0, 0);
                 b.setWidth(500);
                 b.setTextSize(28);
                 tr.addView(b);
                 tl.addView(tr);
                 setContentView(tl);
			}
			else
			{
			
			
			try
			{
				if(type.equals("artists"))
				{
					System.out.println("INSIDE ARTISTS!! Type:"+type);
					 results = new JSONObject(jsonString);
					   jObject = results.getJSONObject("results");
					   resultArray = jObject.getJSONArray("result");
					//   TableLayout tv=(TableLayout) findViewById(R.id.table);
			         //   tv.removeAllViewsInLayout();
					   TableLayout tl = new TableLayout(getApplicationContext());
					   
					   for(int i=0;i<resultArray.length();i++)
					   {
						   j = resultArray.getJSONObject(i);
					     System.out.println
					                       ("Name: "+j.getString("name")+
					    		            " Year: "+j.getString("year").trim()+
					    		            " Genre: "+j.getString("genre").trim()+
					    		            " Details: "+j.getString("details").trim()+
					    		            " Cover: "+j.getString("cover").trim()+"\n"
					    		 );
					     
					     final TableRow tr=new TableRow(getApplicationContext());
		                 tr.setId(i);
		          
		                 ImageView iv = new ImageView(getApplicationContext());
		                 Bitmap bm= new DownloadImageTask(iv).execute(j.getString("cover").trim()).get();
		    	        // img[i]=j.getString("cover").trim();
		    	         
		                 iv.setImageBitmap(bm);
		    	        // iv.getLayoutParams().height+=20;
		    	         iv.setLayoutParams(new TableRow.LayoutParams(100, 100));
		    	         tr.addView(iv);
		    	         

		                 TextView b=new TextView(results.this);
		                 b.setText("Name: "+j.getString("name"));
		                 //img[i]=j.getString("name").trim();
		                 b.setPadding(10, 0, 0, 0);
		                 b.setWidth(100);
		                 b.setTextSize(12);
		                 tr.addView(b);


		                 TextView b1=new TextView(results.this);
		                 b1.setPadding(10, 0, 0, 0);
		                 b1.setTextSize(12);               
		                 b1.setText("Year: "+j.getString("year").trim());
		                 b1.setWidth(180);
		                 tr.addView(b1);
		                 
		                 TextView b2=new TextView(results.this);
		                 b2.setPadding(10, 0, 0, 0);
		                 b2.setTextSize(12);                
		                 b2.setText("Genre: "+j.getString("genre").trim());  
		                 b2.setWidth(100);
		                 tr.addView(b2);
					     
		                tr.setClickable(true);
		                tr.setOnClickListener(new View.OnClickListener() {
		                    @Override
		                    public void onClick(View view) {
		                    	System.out.println("CLICKEDDD!!!!!");
		                    	tr.setBackgroundResource(drawable.list_selector_background);
		                    	//j.get("name");
		                    	 Toast.makeText(getApplicationContext(), "It works", Toast.LENGTH_SHORT).show();
		                    	
                                 x=tr.getId();

								final CharSequence[] items = {"Red", "Green", "Blue"};
								TextView t= (TextView) tr.getChildAt(1);
								String name=t.getText().toString().substring(6);
								String pic="";
								ImageView i= (ImageView) tr.getChildAt(0);
								//i.ge
							  //  System.out.println("&&&&&&&&&&&&&&&&&&&&&&&"+img[0]);
								t= (TextView) tr.getChildAt(2);
								String year=t.getText().toString().substring(6);
								t=(TextView) tr.getChildAt(3);
								String genre=t.getText().toString().substring(7);
								String values[]={name,year,genre,pic};
								System.out.println("%%%%%%%%%%%%%%%%%%%%%"+t.getText().toString());
								
							
								showListDialog(values);
		                    }//ENDS HERE
		                });
					     tl.addView(tr);
					     
					  
					   }
					   setContentView(tl);
				
				}
				else if(type.equals("albums"))
				{
					
					System.out.println("INSIDE ALBUMS!! Type:"+type);
					 results = new JSONObject(jsonString);
					   jObject = results.getJSONObject("results");
					    resultArray = jObject.getJSONArray("result");
					//   TableLayout tv=(TableLayout) findViewById(R.id.table);
			         //   tv.removeAllViewsInLayout();
					   TableLayout tl = new TableLayout(getApplicationContext());
					   
					   for(int i=0;i<resultArray.length();i++)
					   {
					     j = resultArray.getJSONObject(i);
					     System.out.println
					                       ("Name: "+j.getString("name")+
					    		            " Year: "+j.getString("year").trim()+
					    		            " Genre: "+j.getString("genre").trim()+
					    		            " Details: "+j.getString("details").trim()+
					    		            " Title: "+j.getString("title").trim()+
					    		            " Cover: "+j.getString("cover").trim()+"\n"
					    		            
					    		 );
					     
					     final TableRow tr=new TableRow(getApplicationContext());
		                 tr.setId(i);
		          
		                 ImageView iv = new ImageView(getApplicationContext());
		                 Bitmap bm= new DownloadImageTask(iv).execute(j.getString("cover").trim()).get();
		    	        // img[i]=j.getString("cover").trim();
		    	         
		                 iv.setImageBitmap(bm);
		    	        // iv.getLayoutParams().height+=20;
		    	         iv.setLayoutParams(new TableRow.LayoutParams(100, 100));
		    	         tr.addView(iv);
		    	         
		    	         TextView b3=new TextView(results.this);
		                 b3.setText("Title: "+j.getString("title"));
		                 //img[i]=j.getString("name").trim();
		                 b3.setPadding(10, 0, 0, 0);
		                 b3.setWidth(90);
		                 b3.setTextSize(12);
		                 tr.addView(b3);

		                 TextView b=new TextView(results.this);
		                 b.setText("Name: "+j.getString("name"));
		                 //img[i]=j.getString("name").trim();
		                 b.setPadding(10, 0, 0, 0);
		                 b.setWidth(90);
		                 b.setTextSize(12);
		                 tr.addView(b);

		                 TextView b2=new TextView(results.this);
		                 b2.setPadding(10, 0, 0, 0);
		                 b2.setTextSize(12);                
		                 b2.setText("Genre: "+j.getString("genre").trim());  
		                 b2.setWidth(90);
		                 tr.addView(b2);
		                 
		                 
		                 TextView b1=new TextView(results.this);
		                 b1.setPadding(10, 0, 0, 0);
		                 b1.setTextSize(12);               
		                 b1.setText("Year: "+j.getString("year").trim());
		                 b1.setWidth(100);
		                 tr.addView(b1);
		                 
		                
					     
		                tr.setClickable(true);
		                tr.setOnClickListener(new View.OnClickListener() {
		                    @Override
		                    public void onClick(View view) {
		                    	System.out.println("CLICKEDDD!!!!!");
		                    	tr.setBackgroundResource(drawable.list_selector_background);
		                    	//j.get("name");
		                    	 Toast.makeText(getApplicationContext(), "It works", Toast.LENGTH_SHORT).show();
		                    	
		                    	  x=tr.getId();

								final CharSequence[] items = {"Red", "Green", "Blue"};
								TextView t= (TextView) tr.getChildAt(1);
								String name=t.getText().toString().substring(6);
								String pic="";
								ImageView i= (ImageView) tr.getChildAt(0);
								//i.ge
							  //  System.out.println("&&&&&&&&&&&&&&&&&&&&&&&"+img[0]);
								t= (TextView) tr.getChildAt(2);
								String year=t.getText().toString().substring(6);
								t=(TextView) tr.getChildAt(3);
								String genre=t.getText().toString().substring(7);
								String values[]={name,year,genre,pic};
								System.out.println("%%%%%%%%%%%%%%%%%%%%%"+t.getText().toString());
								
							
								showListDialog(values);
		                    }//ENDS HERE
		                });
					     tl.addView(tr);
					     
					  
					   }
					   setContentView(tl);
				
				}
				else if(type.equals("songs"))
				{
					
					
					System.out.println("INSIDE SONGS!! Type:"+type);
					 results = new JSONObject(jsonString);
					   jObject = results.getJSONObject("results");
					  resultArray = jObject.getJSONArray("result");
					//   TableLayout tv=(TableLayout) findViewById(R.id.table);
			         //   tv.removeAllViewsInLayout();
					   TableLayout tl = new TableLayout(getApplicationContext());
					   
					   for(int i=0;i<resultArray.length();i++)
					   {
					     j = resultArray.getJSONObject(i);
					     System.out.println
					                       ("Title: "+j.getString("title")+
					    		            " Performer: "+j.getString("performer").trim()+
					    		            " Composer: "+j.getString("composer").trim()+
					    		            " Details: "+j.getString("details").trim()+"\n"
					    		            
					    		 );
					     
					     final TableRow tr=new TableRow(getApplicationContext());
		                 tr.setId(i);
		          
		                 ImageView iv = new ImageView(getApplicationContext());
		                 Bitmap bm= new DownloadImageTask(iv).execute("http://beta.allmusic.fm/templates/eprom/profile_default/img/mouse_over_cd.png").get();
		    	        // img[i]=j.getString("cover").trim();
		    	         
		                 iv.setImageBitmap(bm);
		    	        // iv.getLayoutParams().height+=20;
		    	         iv.setLayoutParams(new TableRow.LayoutParams(100, 100));
		    	         tr.addView(iv);
		    	         

		                 TextView b=new TextView(results.this);
		                 b.setText("Title: "+j.getString("title"));
		                 //img[i]=j.getString("name").trim();
		                 b.setPadding(10, 0, 0, 0);
		                 b.setWidth(100);
		                 b.setTextSize(12);
		                 tr.addView(b);


		                 TextView b1=new TextView(results.this);
		                 b1.setPadding(10, 0, 0, 0);
		                 b1.setTextSize(12);               
		                 b1.setText("Performer: "+j.getString("performer").trim());
		                 b1.setWidth(180);
		                 tr.addView(b1);
		                 
		                 TextView b2=new TextView(results.this);
		                 b2.setPadding(10, 0, 0, 0);
		                 b2.setTextSize(12);                
		                 b2.setText("Composer: "+j.getString("composer").trim());  
		                 b2.setWidth(100);
		                 tr.addView(b2);
					     
		                tr.setClickable(true);
		                tr.setOnClickListener(new View.OnClickListener() {
		                    @Override
		                    public void onClick(View view) {
		                    	System.out.println("CLICKEDDD!!!!!");
		                    	tr.setBackgroundResource(drawable.list_selector_background);
		                    	//j.get("name");
		                    	 Toast.makeText(getApplicationContext(), "It works", Toast.LENGTH_SHORT).show();
		                    	
		                    	  x=tr.getId();

								final CharSequence[] items = {"Red", "Green", "Blue"};
								TextView t= (TextView) tr.getChildAt(1);
								String name=t.getText().toString().substring(6);
								String pic="";
								ImageView i= (ImageView) tr.getChildAt(0);
								//i.ge
							  //  System.out.println("&&&&&&&&&&&&&&&&&&&&&&&"+img[0]);
								t= (TextView) tr.getChildAt(2);
								String year=t.getText().toString().substring(6);
								t=(TextView) tr.getChildAt(3);
								String genre=t.getText().toString().substring(7);
								String values[]={name,year,genre,pic};
								System.out.println("%%%%%%%%%%%%%%%%%%%%%"+t.getText().toString());
								
							
								showListDialog(values);
		                    }//ENDS HERE
		                });
					     tl.addView(tr);
					     
					  
					   }
					   setContentView(tl);
				
				}
				  
		
			}
			catch (JSONException e)
			   {
				// TODO Auto-generated catch block
				e.printStackTrace();
			    } catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		  }
		}
		private void showListDialog(final String values[])
		{
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			
			
			try {
				j = resultArray.getJSONObject(x);
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			 
			try {
				if((type.equals("songs"))&&(j.getString("sample").length()>3))
				{
				builder.setTitle("Post to Facebook");
				builder.setPositiveButton("Facebook", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
					//   Toast.makeText(getApplicationContext(), disp[which], Toast.LENGTH_SHORT).show();
					   Session session = Session.openActiveSession(results.this, true, new Session.StatusCallback() {
							
							@Override
							public void call(Session session, SessionState state, Exception exception) {
								// TODO Auto-generated method stub
								 System.out.println("State:"+state);
								 if(session.isOpened())
									{
									
										try {
											publishFeedDialog(values);
										} catch (JSONException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
									
										System.out.println("DONE!!!!!");
									}
							}
						});
					
						//----------------
					}
				});
				
				


     builder.setNegativeButton("Sample Music", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						System.out.println(x);
						try {
							j = resultArray.getJSONObject(x);
						} catch (JSONException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						MediaPlayer mediaPlayer = new MediaPlayer();
						try {
							try {
								mediaPlayer.setDataSource(j.getString("sample"));
							} catch (IllegalArgumentException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (SecurityException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (IllegalStateException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
						mediaPlayer.prepare();
						} catch (IOException e) {
						e.printStackTrace();
						}
						mediaPlayer.start();
						//----------------
					}
				});
				AlertDialog dialog = builder.create();
				dialog.show();
}

else
{
				//AlertDialog.Builder builder = new AlertDialog.Builder(this);
				final String[] disp={"Facebook"};
				builder.setTitle("Post to Facebook");
				builder.setItems(disp, new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
					   Toast.makeText(getApplicationContext(), disp[which], Toast.LENGTH_SHORT).show();
					   Session session = Session.openActiveSession(results.this, true, new Session.StatusCallback() {
							
							@Override
							public void call(Session session, SessionState state, Exception exception) {
								// TODO Auto-generated method stub
								 System.out.println("State:"+state);
								 if(session.isOpened())
									{
									
										try {
											publishFeedDialog(values);
										} catch (JSONException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
									
										System.out.println("DONE!!!!!");
									}
							}
						});
					}
				});
				AlertDialog dialog = builder.create();
				dialog.show();
}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		@Override
		  public void onActivityResult(int requestCode, int resultCode, Intent data) {
		      super.onActivityResult(requestCode, resultCode, data);
		      Session.getActiveSession().onActivityResult(this, requestCode, resultCode, data);
		  }
		 
		private void publishFeedDialog(String values[]) throws JSONException  {
		    Bundle params = new Bundle();
		    System.out.println("INSIDE PUBLISHFEEDDIALOG type:"+type);
		    j = resultArray.getJSONObject(x);
		    
		    if(type.equals("artists"))
		    {System.out.println("Inside artists");
		    params.putString("name", j.getString("name"));
		    params.putString("caption", "I like "+j.getString("name")+" who is active since "+j.getString("year"));
		    params.putString("description", "Genre of Music is: "+j.getString("genre"));
		    params.putString("link", j.getString("details"));
		    params.putString("picture", j.getString("cover"));
		    
		    JSONObject properties = new JSONObject();
		    JSONObject prop1 = new JSONObject();
		    prop1.put("text", "here");
		    prop1.put("href", resultArray.getJSONObject(x).getString("details"));
		    properties.put("Look at details", prop1);
		    params.putString("properties", properties.toString());
		    }
		    
		   // else
		     if(type.equals("albums"))
		    {
		    	 System.out.println("Inside Albums");
		    	 params.putString("name", j.getString("title"));
				   params.putString("caption", "I like "+j.getString("title")+" released in "+j.getString("year"));
				  params.putString("description", "Artist: "+j.getString("name")+", Genre: "+j.get("genre"));
				   params.putString("link", j.getString("details"));
				   params.putString("picture", j.getString("cover"));
				   
				   JSONObject properties = new JSONObject();
				    JSONObject prop1 = new JSONObject();
				    prop1.put("text", "here");
				    prop1.put("href", resultArray.getJSONObject(x).getString("details"));
				    properties.put("Look at details", prop1);
				    params.putString("properties", properties.toString());
		    }
		    else if(type.equals("songs"))
		    {
		    	 params.putString("name", j.getString("title"));
				    params.putString("caption", "I like "+j.getString("title")+" composed by "+j.getString("composer"));
				    params.putString("description", "Performer: "+j.getString("performer"));
				    params.putString("link", j.getString("details"));
				    params.putString("picture", "http://beta.allmusic.fm/templates/eprom/profile_default/img/mouse_over_cd.png");
				    
				    JSONObject properties = new JSONObject();
				    JSONObject prop1 = new JSONObject();
				    prop1.put("text", "here");
				    prop1.put("href", resultArray.getJSONObject(x).getString("details"));
				    properties.put("Look at details", prop1);
				    params.putString("properties", properties.toString());
		    }
		    WebDialog feedDialog = (
		        new WebDialog.FeedDialogBuilder(results.this, Session.getActiveSession(), params)).setOnCompleteListener(new OnCompleteListener(){

		            @Override
		            public void onComplete(Bundle values,FacebookException error) {
		                if (error == null) {
		                    // When the story is posted, echo the success
		                    // and the post Id.
		                    final String postId = values.getString("post_id");
		                    if (postId != null) {
		                        Toast.makeText(results.this,
		                            "Posted story, id: "+postId,
		                            Toast.LENGTH_SHORT).show();
		                    } else {
		                        // User clicked the Cancel button
		                        Toast.makeText(results.this.getApplicationContext(), 
		                            "Publish cancelled", 
		                            Toast.LENGTH_SHORT).show();
		                    }
		                } else if (error instanceof FacebookOperationCanceledException) {
		                    // User clicked the "x" button
		                    Toast.makeText(results.this.getApplicationContext(), 
		                        "Publish cancelled", 
		                        Toast.LENGTH_SHORT).show();
		                } else {
		                    // Generic, ex: network error
		                    Toast.makeText(results.this.getApplicationContext(), 
		                        "Error posting story", 
		                        Toast.LENGTH_SHORT).show();
		                }
		            }

		        })
		        .build();
		    feedDialog.show();
		}
   }


	
 class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
    ImageView bmImage;

    public DownloadImageTask(ImageView bmImage) {
        this.bmImage = bmImage;
    }

    protected Bitmap doInBackground(String... urls) {
        String urldisplay = urls[0];
        Bitmap mIcon11 = null;
        try {
            InputStream in = new java.net.URL(urldisplay).openStream();
            mIcon11 = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        return mIcon11;
    }
    protected void onPostExecute(Bitmap result) {
        bmImage.setImageBitmap(result);
    }
}
	
/*	class Json3 extends AsyncTask<String, Void, String> {
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
		System.out.println(output);
	//    outputText.setText(output);
	}
}*/