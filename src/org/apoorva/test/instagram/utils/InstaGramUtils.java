package org.apoorva.test.instagram.utils;

import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apoorva.test.instagram.activities.SelfieListActivity;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class InstaGramUtils {

	
    


    
	
	public static String getAccessTokenUrl(String reqestCode) {

		return "client_id=" + InstagramConstants.CLIENT_ID + "&client_secret="
				+ InstagramConstants.CLIENT_SECRET + "&grant_type=authorization_code"
				+ "&redirect_uri=" + InstagramConstants.CALLBACKURL + "&code="
				+ reqestCode;
	}

	public static EntityResponse getSelfieData(Context ctx) {
		EntityResponse res=new EntityResponse();
		try {
			
			DefaultHttpClient client = new DefaultHttpClient();
			String sourceUrl="";
			if(SelfieListActivity.isFirst==true)
			sourceUrl = InstagramConstants.APP_SELFIE_URL+ InstgramPreferences.requestToken(ctx);
			else{
				sourceUrl=next_url;
			}
			HttpGet httpGet = new HttpGet(sourceUrl);
			HttpResponse httpResponse = client.execute(httpGet);
			HttpEntity httpEntity = httpResponse.getEntity();
			if (httpEntity != null) {
				InputStream isr = httpEntity.getContent();
				res.data = IOUtils.toString(isr);
				res.responseId=InstagramConstants.RESPONSE_SUCCESS;
			}
		} catch (Exception e) {
			res.data = e.toString();
			res.responseId=InstagramConstants.RESPONSE_FAILURE;
		}
		return res;
	}
	
	

	public static boolean isNetworkAvaliable(Context ctx) {
		ConnectivityManager connectivityManager = (ConnectivityManager) ctx
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo wifi = connectivityManager
				.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		NetworkInfo mobile = connectivityManager
				.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

		return (wifi.isConnected() || mobile.isConnected()) ? true : false;
	}

	
	public static String getRequestToken(String url){
		try{
		String parts[] = url.split("=");
		return  parts[1];
		}
		catch(ArrayIndexOutOfBoundsException ar){
			return "";
		}
		
	}
	
	
	public static  EntityResponse setAccessToken(Context ctx, String token) {

		
		EntityResponse res=new EntityResponse();
		try {
			
			System.out.print("started ");
			URL url = new URL(InstagramConstants.APP_TOKEN_URL);
			HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url
					.openConnection();
			httpsURLConnection.setRequestMethod("POST");
			httpsURLConnection.setDoInput(true);
			httpsURLConnection.setDoOutput(true);
			OutputStreamWriter outputStreamWriter = new OutputStreamWriter(
					httpsURLConnection.getOutputStream());
			outputStreamWriter.write(InstaGramUtils.getAccessTokenUrl(token));
			outputStreamWriter.flush();
			String response = IOUtils.toString(httpsURLConnection
					.getInputStream());
			JSONObject jsonObject = (JSONObject) new JSONTokener(response)
					.nextValue();
			String accessTokenString = jsonObject.getString("access_token");

			InstgramPreferences.setAccessToken(ctx,	accessTokenString, token);
			res.responseId=InstagramConstants.RESPONSE_SUCCESS;
			res.data="SUCCESS";

		} catch (Exception e) {
			System.out.println(" ERROR ==>>>>>>>:" + e.toString());
			res.responseId=InstagramConstants.RESPONSE_FAILURE;
			res.data=e.toString();
		}
		return res;

	}

	public static String next_url="";
	
	public static ArrayList<ImageInfo>  getThumbNailsList(String data) {

		ArrayList<ImageInfo> listThumbNails = new ArrayList<ImageInfo>();
		try {
			JSONObject obj = new JSONObject(data);
			JSONObject status = obj.getJSONObject("meta");
			if (status.getInt("code") == 200) {
				JSONObject pagination = obj.getJSONObject("pagination");
				
				
				
				String url=pagination.getString("next_url");
				System.out.println(" pagination val is :"+url);
				
				
				next_url=url;
				
				JSONArray jobjData = obj.getJSONArray("data");
				for (int i = 0; i < jobjData.length(); i++) {
					try{
					JSONObject ob = jobjData.getJSONObject(i);
					JSONObject imObj = ob.getJSONObject("images");
					String thumbNail = imObj.getJSONObject("thumbnail").getString("url");
					String max_url = imObj.getJSONObject("standard_resolution").getString("url");
					ImageInfo info = new ImageInfo();
					info.imgThumbNailUrl=thumbNail;
					info.imgMaxUrl= max_url;
					
					listThumbNails.add(info);
					}
					catch(Exception e){		}
				}
			}
		} catch (Exception e) {
		}
		return listThumbNails;

	}
	
	
	
	
}
	
	
	
	
	
