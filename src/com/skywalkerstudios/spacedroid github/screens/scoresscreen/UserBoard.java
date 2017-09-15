package com.skywalkerstudios.spacedroid.screens.scoresscreen;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import com.skywalkerstudios.spacedroid.Assets;
import com.skywalkerstudios.spacedroid.SpaceDroid;
import com.skywalkerstudios.spacedroid.Profiles.Profile;
import com.skywalkerstudios.spacedroid.screens.standardobjects.DefaultBoard;
import com.webstorms.framework.util.Util;
import com.webstorms.framework.graphics.animation.Animation;
import com.webstorms.framework.graphics.animation.Spinner;
import com.webstorms.physics.Shape;


public class UserBoard extends DefaultBoard {
	
	/* Last edit: 30 July 2012
	 * 
	 * Improvements: 
	 * - Fix the positioning for level and position text when length changes (perhaps create a UTIL class with method)
	 * - Place name above userboard
	 * 
	 * Bugs:
	 * - None (that I know of) :D
	 */
	
	private BoardInfo boardInfo;
	
	Shape smallUserBoard = new Shape();
	Shape bigUserBoard = new Shape();
	Shape smallBoardPositionStar = new Shape();
	Shape bigBoardPositionStar = new Shape();
	Shape levelStar = new Shape();
	Shape smallBoardUserPicture = new Shape();
	Shape bigBoardUserPicture = new Shape();
	Shape bestLevelBoard = new Shape();
	
	Spinner spinnerOfLevelStar = new Spinner(2);
	int differenceXname;
	int differenceXBestWave;
	int differenceXPosition;
	int differenceXLevel;
	
	Paint namePaint = new Paint();
	Paint positionPaint = new Paint();
	Paint levelPaint = new Paint();
	Paint bestWaveValuePaint = new Paint();
	Paint bestWaveTextPaint = new Paint();
	
	public UserBoard(SpaceDroid game, Profile profileData) {
		super(game);
		boardInfo = new BoardInfo(profileData.getUserName(), profileData.getUserBestScore(), profileData.getUserPosition(), profileData.getUserLevel(), profileData.getUserPicture());
		
		smallUserBoard.addRectangle(new Rect(0, 0, 143, 194));
		bigUserBoard.addRectangle(new Rect(0, 0, 234, 286));
		smallBoardPositionStar.addRectangle(new Rect(0, 0, 78, 78));
		bigBoardPositionStar.addRectangle(new Rect(0, 0, 78, 78));
		levelStar.addRectangle(new Rect(0, 0, 83, 83));
		smallBoardUserPicture.addRectangle(new Rect(0, 0, 103, 103));
		bigBoardUserPicture.addRectangle(new Rect(0, 0, 163, 163));
		bestLevelBoard.addRectangle(new Rect(0, 0, 158, 44));
		
		namePaint.setColor(Color.WHITE);
		namePaint.setTextSize(34);
		namePaint.setTypeface(Assets.standardScreen_Birdman);
		
		positionPaint.setColor(Color.WHITE);
		positionPaint.setTextSize(30);
		positionPaint.setTypeface(Assets.standardScreen_Birdman);
		
		levelPaint.setColor(Color.WHITE);
		levelPaint.setTextSize(31);
		levelPaint.setTypeface(Assets.standardScreen_Birdman);
		
		bestWaveValuePaint.setColor(Color.WHITE);
		bestWaveValuePaint.setTextSize(29);
		bestWaveValuePaint.setTypeface(Assets.standardScreen_Birdman);
		
		bestWaveTextPaint.setColor(Color.WHITE);
		bestWaveTextPaint.setTextSize(19);
		bestWaveTextPaint.setTypeface(Assets.standardScreen_Birdman);
		
		differenceXname = Util.centerObject((int) namePaint.measureText(this.boardInfo.getProfileName()), 0, this.bigUserBoard.getWidth());		
		differenceXBestWave = Util.centerObject((int) (bestWaveTextPaint.measureText("best. ") + bestWaveValuePaint.measureText(String.valueOf(this.boardInfo.getProfileBestScore()))), 0, this.bestLevelBoard.getWidth());
		differenceXPosition = Util.centerObject((int) positionPaint.measureText(String.valueOf(this.boardInfo.getProfilePosition())), 0, this.smallBoardPositionStar.getWidth());
		differenceXLevel = Util.centerObject((int) levelPaint.measureText(String.valueOf(this.boardInfo.getProfileLevel())), 0, this.levelStar.getWidth());
		
	}
	
	@Override
	public void update(int velocity) {
		super.update(velocity);
		smallUserBoard.moveShape(velocity, 0);
		bigUserBoard.moveShape(velocity, 0);
		smallBoardPositionStar.moveShape(velocity, 0);
		bigBoardPositionStar.moveShape(velocity, 0);
		levelStar.moveShape(velocity, 0);
		smallBoardUserPicture.moveShape(velocity, 0);
		bigBoardUserPicture.moveShape(velocity, 0);
		bestLevelBoard.moveShape(velocity, 0);
		this.spinnerOfLevelStar.update();
		
	}
	
	@Override
	public void render() {
		super.render();
		if(this.getType() == UserBoard.TYPE_SMALL) {
			game.getGraphics().drawBitmap(Assets.standardScreen_SmallViewBoard, null, this.smallUserBoard.getRect(), null);
			game.getGraphics().drawBitmap(this.boardInfo.getProfilePicture(), null, this.smallBoardUserPicture.getRect(), null);
			game.getGraphics().drawBitmap(Assets.scoresScreen_PositionStar, null, this.smallBoardPositionStar.getRect(), null);
			game.getGraphics().drawText("" + this.boardInfo.getProfilePosition(), this.smallBoardPositionStar.getX() + this.differenceXPosition, this.smallBoardPositionStar.getY() + 49, positionPaint);
			
		}
		else if(this.getType() == UserBoard.TYPE_BIG) {
			game.getGraphics().drawBitmap(Assets.standardScreen_BigViewBoard, null, this.bigUserBoard.getRect(), null);
			game.getGraphics().drawBitmap(this.boardInfo.getProfilePicture(), null, this.bigBoardUserPicture.getRect(), null);
			game.getGraphics().drawBitmap(Assets.scoresScreen_LevelStar, Animation.rotateMatrix(Assets.scoresScreen_LevelStar, this.levelStar, this.spinnerOfLevelStar.getValue()), null);
			game.getGraphics().drawBitmap(Assets.scoresScreen_PositionStar, null, this.bigBoardPositionStar.getRect(), null);
			game.getGraphics().drawBitmap(Assets.scoresScreen_BestLevelBoard, null, this.bestLevelBoard.getRect(), null);
			
			game.getGraphics().drawText(this.boardInfo.getProfileName(), this.bigUserBoard.getX() + this.differenceXname, this.bigUserBoard.getY() - 36, this.namePaint);
			game.getGraphics().drawText("" + this.boardInfo.getProfilePosition(), this.bigBoardPositionStar.getX() + this.differenceXPosition, this.bigBoardPositionStar.getY() + 49, positionPaint);
			game.getGraphics().drawText("" + this.boardInfo.getProfileLevel(), this.levelStar.getX() + this.differenceXLevel, this.levelStar.getY() + 50, levelPaint);
			game.getGraphics().drawText("best. ", this.bestLevelBoard.getX() + this.differenceXBestWave, this.bigUserBoard.getY() + 276, bestWaveTextPaint);
			game.getGraphics().drawText(String.valueOf(this.boardInfo.getProfileBestScore()), this.bestLevelBoard.getX() + this.differenceXBestWave + this.bestWaveTextPaint.measureText("best. "), this.bigUserBoard.getY() + 276, bestWaveValuePaint);
			
		}
		
	}
	
	@Override
	public void setX(int newX) {
		super.setX(newX);
		
		this.getShape().setLocation(newX, 361);
		smallUserBoard.setLocation(newX, 360);
		bigUserBoard.setLocation(newX, 360);
		smallBoardPositionStar.setLocation(newX + 57, 388);
		bigBoardPositionStar.setLocation(newX + 108, 388);
		levelStar.setLocation(newX + 149, 427);
		smallBoardUserPicture.setLocation(newX + 18, 439);
		bigBoardUserPicture.setLocation(newX + 34, 455);
		bestLevelBoard.setLocation(newX + 35, 599);
		
	}
	
	public class BoardInfo {
		
		private String profileName;
		private int profileBestScore;
		private int profilePosition;
		private int profileLevel;
		private Bitmap profilePicture;
		
		public BoardInfo(String profileName, int profileBestScore, int profilePosition, int profileLevel, Bitmap profilePicture) {
			this.profileName = profileName;
			this.profileBestScore = profileBestScore;
			this.profilePosition = profilePosition;
			this.profileLevel = profileLevel;
			this.profilePicture = profilePicture;
			
		}

		public String getProfileName() {
			return profileName;
		}

		public int getProfileBestScore() {
			return profileBestScore;
		}

		public int getProfilePosition() {
			return profilePosition;
		}

		public int getProfileLevel() {
			return profileLevel;
		}

		public Bitmap getProfilePicture() {
			return profilePicture;
		}
		
		
	}
	
	
}
