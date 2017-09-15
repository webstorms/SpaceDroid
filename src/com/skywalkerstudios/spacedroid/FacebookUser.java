package com.skywalkerstudios.spacedroid;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.parse.ParseFacebookUtils;
import com.parse.facebook.FacebookError;
import com.parse.facebook.Util;

public class FacebookUser {

	public static final String TYPE_SMALL = "small";
	public static final String TYPE_MEDIUM = "normal";
	public static final String TYPE_LARGE = "large";
	
	// NOTE: Only invoke methods once the user has successfully been logged in! Otherwise your in big shit!
	
	public static String getName() {
		String response;
		String name = "";
		try {
			response = ParseFacebookUtils.getFacebook().request("me");
			JSONObject json = Util.parseJson(response);
			name = json.getString("first_name");
		} 
		catch (MalformedURLException e) {
			e.printStackTrace();
		} 
		catch (IOException e) {
			e.printStackTrace();
		} 
		catch (FacebookError e) {
			e.printStackTrace();
		} 
		catch (JSONException e) {
			e.printStackTrace();
		}
		
		return name;
	}
	
	public static String getName(String id) {
		String response;
		String name = "";
		try {
			response = ParseFacebookUtils.getFacebook().request(id);
			JSONObject json = Util.parseJson(response);
			name = json.getString("first_name");
		} 
		catch (MalformedURLException e) {
			e.printStackTrace();
		} 
		catch (IOException e) {
			e.printStackTrace();
		} 
		catch (FacebookError e) {
			e.printStackTrace();
		} 
		catch (JSONException e) {
			e.printStackTrace();
		}
		
		return name;
	}
	
	public static String getFaceBookID() {
		String response;
		String id = "";
		try {
			response = ParseFacebookUtils.getFacebook().request("me");
			JSONObject json = Util.parseJson(response);
			id = json.getString("id"); 
		} 
		catch (MalformedURLException e) {
			e.printStackTrace();
		} 
		catch (IOException e) {
			e.printStackTrace();
		} 
		catch (FacebookError e) {
			e.printStackTrace();
		} 
		catch (JSONException e) {
			e.printStackTrace();
		}
		
		return id;
		
	}
	
	public static Bitmap getProfilePicture(String id, String type) {
		URL img_value = null;
		Bitmap profilePicture = null;
		try {
			img_value = new URL("http://graph.facebook.com/"+id+"/picture?type=" + type);
			profilePicture = BitmapFactory.decodeStream(img_value.openConnection().getInputStream());
			
		} 
		catch (MalformedURLException e) {
			e.printStackTrace();
			Log.d("SpaceDroid", "MalformedURLException");
			
		} 
		catch (IOException e) {
			e.printStackTrace();
			Log.d("SpaceDroid", "IOException");
			
		}
		
		return profilePicture;
		
	}
	
	public static List<String> getFaceBookFriendsIDs() {
		
		List<String> friendsList = null;
		try {
			JSONObject result = new JSONObject(ParseFacebookUtils.getFacebook().request("me/friends"));
			JSONArray friendsArray = result.getJSONArray("data");
			
			friendsList = new ArrayList<String>();
			for (int i = 0; i < friendsArray.length(); i++) {
				friendsList.add(friendsArray.getJSONObject(i).getString("id"));
			}
		} 
		catch (MalformedURLException e) {
			e.printStackTrace();
			
		} 
		catch (JSONException e) {
			e.printStackTrace();
			
		} 
		catch (IOException e) {
			e.printStackTrace();
			
		}
		
		return friendsList;
		
	}
	
	
}
