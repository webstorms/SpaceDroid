package com.skywalkerstudios.spacedroid;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;

import com.flurry.android.FlurryAgent;
import com.parse.Parse;
import com.parse.ParseFacebookUtils;
import com.skywalkerstudios.spacedroid.Profiles.UserProfile;
import com.skywalkerstudios.spacedroid.screens.SplashScreen;
import com.skywalkerstudios.spacedroid.screens.playscreen.World;
import com.webstorms.framework.Game;
import com.webstorms.framework.WSLog;
import com.webstorms.framework.WSScreen;
import com.webstorms.framework.graphics.animation.Spinner;

public class SpaceDroid extends Game {
	
	public static final boolean DEBUG = false;
	
	private static final String ANALYTICS_APP_API_KEY = "TRFT5D35G955V63NQZ5F";
	private static final String SERVER_COMMUNICATION_APP_ID = "Rgh8wjbdDvOLSBB4mIreUVHsmOk5UJmaVo3J0yHX";
	private static final String SERVER_COMMUNICATION_CLIENT_KEY = "2pfJ395AJU6ogptaLJu97uL9y8qdiaEbusu3YhCY";
	private static final String FACEBOOK_COMMUNICATION_APP_ID = "442855932391462";
	
	// Variables to be kept in the heap
	private UserProfile user = new UserProfile(this);
	private World world;
	private boolean hasShownAppCrashNotification;
	private Spinner spinnerOfSmallRefreshIcon = new Spinner(3);
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Parse.initialize(this, SpaceDroid.SERVER_COMMUNICATION_APP_ID, SpaceDroid.SERVER_COMMUNICATION_CLIENT_KEY);
		ParseFacebookUtils.initialize(SpaceDroid.FACEBOOK_COMMUNICATION_APP_ID, true);
		
	}
	
	@Override
	public void onCreateGameEngine() {
		this.setTAG("SpaceDroid");
		if(DEBUG) {
			this.setLogType(WSLog.debugging);
			
		}
		else {
			this.setLogType(WSLog.shipping);
			
		}
		this.setScreenResizeTypeForAllScreens(WSScreen.stretchScreen);
		this.setFramesPerSecond(30);
		this.setAssets(new Assets(this));
		
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		if(!DEBUG) {
			FlurryAgent.onStartSession(this, SpaceDroid.ANALYTICS_APP_API_KEY);
			FlurryAgent.setCaptureUncaughtExceptions(true);
			
		}
		
	}
	
	@Override
	protected void onStop() {
		super.onStop();
		if(!DEBUG) {
			FlurryAgent.onEndSession(this);
			
		}
		
	}
	
	@Override
	protected void onCreateGame() {
		setScreen(new SplashScreen(this));
		
	}

	@Override
	public void onDisposeGame() {
		// Stop any outgoing network operations if they exist
		if(this.user.isInteractingWithServer()) {
			this.terminateGame();
			
		}
		
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	  super.onActivityResult(requestCode, resultCode, data);
	  ParseFacebookUtils.finishAuthentication(requestCode, resultCode, data);
		  
	}

	public Spinner getSpinner() {
		return this.spinnerOfSmallRefreshIcon;
		
	}
	
	public UserProfile getUserProfile() {
		return this.user;
	}
	
	public boolean isNetworkAvailable() {
	    return ((ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo() != null;
	    
	}
	
	public World getWorld() {
		return this.world;
		
	}
	
	public void disposeWorld() {
		this.world.resetAllAudio();
		this.world = null;
		
	}

	public World createNewWorld() {
		this.world = new World(this);
		return this.world;

	}

	public boolean hasShownAppCrashNotification() {
		return this.hasShownAppCrashNotification;
		
	}

	public void setShownAppCrashNotification() {
		this.hasShownAppCrashNotification = true;
		
	}
	
	
}