package com.skywalkerstudios.spacedroid.Profiles;

import com.parse.ParseObject;
import com.skywalkerstudios.spacedroid.FileNames;
import com.skywalkerstudios.spacedroid.SpaceDroid;
import com.webstorms.framework.Game;
import com.webstorms.framework.WSObject;

import android.graphics.Bitmap;

public abstract class Profile extends WSObject {
	
	// Delete server methods
	
	public static final String FILENAME_PREFIX = "profile";
	public static final Integer TYPE_LOCAL = 0;
	public static final Integer TYPE_SERVER = 1;
	public static final Integer TYPE_CREATE_LOCAL = 2;
	public static final Integer TYPE_CREATE_SERVER = 3;
	
	ParseObject userData;
	
	private String userSocialID;
	private String userServerID;
	private String userName = "";
	private Bitmap userPicture;
	private int userXP;
	private int userLevel;
	private int userPosition;
	private int userBestScore;
	
	public Profile(Game game) {
		super(game);
		
	}
	
	@Override
	public SpaceDroid getGame() {
		// TODO Auto-generated method stub
		return (SpaceDroid) super.getGame();
	}
	
	public void provideServerData(ParseObject userData) {
		this.userData = userData;
	}
	
	public ParseObject getServerData() {
		return this.userData;
		
	}
	
	// Utility
	
	public int levelToExp(int level) {
	    return (int) Math.pow((((level - 1) / 0.005f) / 10), 2);
	    
	}
	
	public int expToLevel(int exp)	{
		return (int) ((Math.sqrt(exp + 1) * 10) * 0.005f) + 1;
		
	}
	
	// Setter
	
	public void setUserBestScore(int userBestScore) {
		this.userBestScore = userBestScore;
		
	}
	
	public void setUserSocialID(String userSocialID) {
		this.userSocialID = userSocialID;
	}
	
	public void setUserServerID(String userServerID) {
		this.userServerID = userServerID;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public void setUserPicture(Bitmap userPicture) {
		this.userPicture = userPicture;
	}
	
	public void setUserXP(int userXP) {
		this.userXP = userXP;
		this.userLevel = this.expToLevel(this.userXP);
		
	}
	
	public void setUserPosition(int newUserPosition) {
		this.userPosition = newUserPosition;
		
	}
	
	// Getter 
	
	public int getUserBestScore() {
		return this.userBestScore;
		
	}
	
	public String getUserSocialID() {
		return userSocialID;
	}
	
	public String getUserServerID() {
		return userServerID;
	}
	
	public String getUserName() {
		return userName;
	}
	
	public Bitmap getUserPicture() {
		return userPicture;
	}
	
	public int getUserXP() {
		return userXP;
	}
	
	public int getUserLevel() {
		return userLevel;
	}
	
	public int getUserPosition() {
		return this.userPosition;
	}
	
	// Load all the information for a friend profile
	
	public void loadProfile(int loadType, Integer numberOfProfile) {
		if(loadType == Profile.TYPE_CREATE_LOCAL) {
			this.loadProfileCreate(null, Profile.TYPE_CREATE_LOCAL);
		}
		else if(loadType == Profile.TYPE_CREATE_SERVER) {
			this.loadProfileCreate(null, Profile.TYPE_CREATE_SERVER);
		}
		else if(loadType == Profile.TYPE_LOCAL) {
			this.loadProfileLocal(numberOfProfile);
		}
		else if(loadType == Profile.TYPE_SERVER) {
			this.loadProfileServer(null);
		}
		
	}
	
	public void loadProfileCreate(Integer numberOfProfile, int loadType) {
		this.getGame().getGameLog().d(this.getClassTag(), "Creating profile...");
		
	}
	
	public void loadProfileLocal(Integer numberOfProfile) {
		this.getGame().getGameLog().d(this.getClassTag(), "Loading profile locally...");
		this.setUserSocialID(this.getGame().getIO().readPrimitiveInternalMemoryString(Profile.FILENAME_PREFIX +  numberOfProfile + "_" + FileNames.Profile.SOCIAL_ID));
		this.setUserServerID(this.getGame().getIO().readPrimitiveInternalMemoryString(Profile.FILENAME_PREFIX +  numberOfProfile + "_" + FileNames.Profile.SERVER_ID));
		this.setUserName(this.getGame().getIO().readPrimitiveInternalMemoryString(Profile.FILENAME_PREFIX +  numberOfProfile + "_" + FileNames.Profile.NAME));
		this.setUserPicture(this.getGame().getIO().readBitmapFromMemory(Profile.FILENAME_PREFIX +  numberOfProfile + "_" + FileNames.Profile.PICTURE));
		this.setUserXP(this.getGame().getIO().readPrimitiveInternalMemoryInteger(Profile.FILENAME_PREFIX +  numberOfProfile + "_" + FileNames.Profile.XP));
		this.setUserBestScore(this.getGame().getIO().readPrimitiveInternalMemoryInteger(Profile.FILENAME_PREFIX +  numberOfProfile + "_" + FileNames.Profile.BEST_SCORE));
		
	}
	
	public void loadProfileServer(Integer numberOfProfile) {
		this.getGame().getGameLog().d(this.getClassTag(), "Loading profile from Server...");
		
	}
	
	public void saveProfile(int saveType, Integer numberOfProfile) {
		if(saveType == Profile.TYPE_LOCAL) {
			this.saveProfileLocal(numberOfProfile);
			
		}
		else if(saveType == Profile.TYPE_SERVER) {
			this.saveProfileServer(null);
			
		}
		
	}
	
	public void saveProfileLocal(Integer numberOfProfile) {
		this.getGame().getGameLog().d(this.getClassTag(), "Saving profile locally...");
        this.getGame().getIO().writePrimitiveInternalMemory(Profile.FILENAME_PREFIX +  numberOfProfile + "_" + FileNames.Profile.NAME, this.getUserName());
        this.getGame().getIO().writePrimitiveInternalMemory(Profile.FILENAME_PREFIX +  numberOfProfile + "_" + FileNames.Profile.XP, this.getUserXP());
        this.getGame().getIO().writePrimitiveInternalMemory(Profile.FILENAME_PREFIX +  numberOfProfile + "_" + FileNames.Profile.BEST_SCORE, this.getUserBestScore());
		
	}
	
	public void saveProfileServer(Integer numberOfProfile) {
		this.getGame().getGameLog().d(this.getClassTag(), "Saving profile to Server...");
		
	}
	
	public void cleanProfileLocal(Integer numberOfProfile) {
		this.getGame().getGameLog().d(this.getClassTag(), "Removing local profile...");
		this.getGame().getIO().removeFile(Profile.FILENAME_PREFIX +  numberOfProfile + "_" + FileNames.Profile.SOCIAL_ID);
		this.getGame().getIO().removeFile(Profile.FILENAME_PREFIX +  numberOfProfile + "_" + FileNames.Profile.SERVER_ID);
		this.getGame().getIO().removeFile(Profile.FILENAME_PREFIX +  numberOfProfile + "_" + FileNames.Profile.NAME);
		this.getGame().getIO().removeBitmap(Profile.FILENAME_PREFIX +  numberOfProfile + "_" + FileNames.Profile.PICTURE);
		this.getGame().getIO().removeFile(Profile.FILENAME_PREFIX +  numberOfProfile + "_" + FileNames.Profile.XP);
		this.getGame().getIO().removeFile(Profile.FILENAME_PREFIX +  numberOfProfile + "_" + FileNames.Profile.BEST_SCORE);
		
	}
	
	
}
