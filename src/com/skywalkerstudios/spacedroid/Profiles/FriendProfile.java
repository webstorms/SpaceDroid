package com.skywalkerstudios.spacedroid.Profiles;

import com.skywalkerstudios.spacedroid.FacebookUser;
import com.skywalkerstudios.spacedroid.FileNames;
import com.skywalkerstudios.spacedroid.SpaceDroid;

public class FriendProfile extends Profile {
	
	public FriendProfile(SpaceDroid game) {
		super(game);
		
	}
	
	@Override
	public void saveProfileLocal(Integer numberOfProfile) {
		super.saveProfileLocal(numberOfProfile);
		this.getGame().getIO().writePrimitiveInternalMemory(Profile.FILENAME_PREFIX +  numberOfProfile + "_" + FileNames.Profile.SOCIAL_ID, this.getUserSocialID());
        this.getGame().getIO().writePrimitiveInternalMemory(Profile.FILENAME_PREFIX +  numberOfProfile + "_" + FileNames.Profile.SERVER_ID, this.getUserServerID());
        this.getGame().getIO().writeBitmapToMemory(Profile.FILENAME_PREFIX +  numberOfProfile + "_" + FileNames.Profile.PICTURE, this.getUserPicture());
        
	}
	
	@Override
	public void loadProfileLocal(Integer numberOfProfile) {
		super.loadProfileLocal(numberOfProfile);
		this.getGame().getIO().writePrimitiveInternalMemory(Profile.FILENAME_PREFIX +  numberOfProfile + "_" + FileNames.Profile.SOCIAL_ID, this.getUserSocialID());
		this.getGame().getIO().writePrimitiveInternalMemory(Profile.FILENAME_PREFIX +  numberOfProfile + "_" + FileNames.Profile.SERVER_ID, this.getUserServerID());
		this.getGame().getIO().writeBitmapToMemory(Profile.FILENAME_PREFIX +  numberOfProfile + "_" + FileNames.Profile.PICTURE, this.getUserPicture());
		
	}

	@Override
	public void loadProfileServer(Integer numberOfProfile) {
		super.loadProfileServer(numberOfProfile);
		this.setUserSocialID(userData.getString(FileNames.Profile.SOCIAL_ID));
		this.setUserServerID(userData.getString(FileNames.Profile.SERVER_ID));
		this.setUserPicture(FacebookUser.getProfilePicture(this.getUserSocialID(), FacebookUser.TYPE_LARGE));
		this.setUserName(userData.getString(FileNames.Profile.NAME)); // FacebookUser.getName(this.getUserSocialID())
		this.setUserXP(userData.getInt(FileNames.Profile.XP));
		this.setUserBestScore(userData.getInt(FileNames.Profile.BEST_SCORE));
		this.getGame().getGameLog().d(this.getClassTag(), "Loaded profile: " + getUserName());
		
	}

	@Override
	public void cleanProfileLocal(Integer numberOfProfile) {
		super.cleanProfileLocal(numberOfProfile);
		this.getGame().getGameLog().d(this.getClassTag(), "Cleaned profile: " + this.getUserName());
		
	}
	
	
}
