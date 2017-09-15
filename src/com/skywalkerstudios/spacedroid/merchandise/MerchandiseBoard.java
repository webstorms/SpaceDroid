package com.skywalkerstudios.spacedroid.merchandise;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import com.skywalkerstudios.spacedroid.Assets;
import com.skywalkerstudios.spacedroid.SpaceDroid;
import com.skywalkerstudios.spacedroid.screens.scoresscreen.UserBoard;
import com.skywalkerstudios.spacedroid.screens.standardobjects.DefaultBoard;
import com.webstorms.framework.util.Util;
import com.webstorms.physics.Shape;

public class MerchandiseBoard extends DefaultBoard {
	
	private MerchandiseViewable boardInfo;
	
	Shape smallUserBoard = new Shape();
	Shape bigUserBoard = new Shape();
	Shape smallBoardUserPicture = new Shape();
	Shape bigBoardUserPicture = new Shape();
	
	int differenceXname;
	
	Paint namePaint = new Paint();
	
	public MerchandiseBoard(SpaceDroid game, MerchandiseViewable itemInfo) {
		super(game);
		this.boardInfo = new MerchandiseViewable(this.getGame());
		this.boardInfo.setMerchandiseItemName(itemInfo.getMerchandiseItemName());
		this.boardInfo.setMerchandiseItemPicture(itemInfo.getMerchandiseItemPicture());
		
		smallUserBoard.addRectangle(new Rect(0, 0, 143, 194));
		bigUserBoard.addRectangle(new Rect(0, 0, 234, 286));
		
		smallBoardUserPicture.addRectangle(new Rect(0, 0, 103, 103));
		bigBoardUserPicture.addRectangle(new Rect(0, 0, 163, 163));
		
		namePaint.setColor(Color.WHITE);
		namePaint.setTextSize(34);
		namePaint.setTypeface(Assets.standardScreen_Birdman);
		
		differenceXname = Util.centerObject((int) namePaint.measureText(this.boardInfo.getMerchandiseItemName()), 0, this.bigUserBoard.getWidth());	
		
	}
	
	@Override
	public void update(int velocity) {
		super.update(velocity);
		smallUserBoard.moveShape(velocity, 0);
		bigUserBoard.moveShape(velocity, 0);
		
		smallBoardUserPicture.moveShape(velocity, 0);
		bigBoardUserPicture.moveShape(velocity, 0);
		
	}
	
	@Override
	public void render() {
		super.render();
		if(this.getType() == UserBoard.TYPE_SMALL) {
			game.getGraphics().drawBitmap(Assets.standardScreen_SmallViewBoard, null, this.smallUserBoard.getRect(), null);
			game.getGraphics().drawBitmap(this.boardInfo.getMerchandiseItemPicture(), null, this.smallBoardUserPicture.getRect(), null);
		//	game.getGraphics().drawBitmap(Assets.scoresScreen_PositionStar, null, this.smallBoardPositionStar.getHolderPolygons().get(0), null);
		//	game.getGraphics().drawText("" + this.userProfile.getUserPosition(), this.smallBoardPositionStar.getX() + this.differenceXPosition, this.smallBoardPositionStar.getY() + 49, positionPaint);
			
		}
		else if(this.getType() == UserBoard.TYPE_BIG) {
			game.getGraphics().drawBitmap(Assets.standardScreen_BigViewBoard, null, this.bigUserBoard.getRect(), null);
			game.getGraphics().drawBitmap(this.boardInfo.getMerchandiseItemPicture(), null, this.bigBoardUserPicture.getRect(), null);
		//	game.getGraphics().drawBitmap(Assets.scoresScreen_LevelStar, Animation.rotateMatrix(Assets.scoresScreen_LevelStar, this.levelStar, rotationOfLevelStar), null);
		//	game.getGraphics().drawBitmap(Assets.scoresScreen_PositionStar, null, this.bigBoardPositionStar.getHolderPolygons().get(0), null);
		//	game.getGraphics().drawBitmap(Assets.scoresScreen_BestLevelBoard, null, this.bestLevelBoard.getHolderPolygons().get(0), null);
			
			game.getGraphics().drawText(this.boardInfo.getMerchandiseItemName(), this.bigUserBoard.getX() + this.differenceXname, this.bigUserBoard.getY() - 36, this.namePaint);
		//	game.getGraphics().drawText("" + this.userProfile.getUserPosition(), this.bigBoardPositionStar.getX() + this.differenceXPosition, this.bigBoardPositionStar.getY() + 49, positionPaint);
		//	game.getGraphics().drawText("" + this.userProfile.getUserLevel(), this.levelStar.getX() + this.differenceXLevel, this.levelStar.getY() + 50, levelPaint);
		//	game.getGraphics().drawText("best. ", this.bestLevelBoard.getX() + this.differenceXBestWave, this.bigUserBoard.getY() + 276, bestWaveTextPaint);
		//	game.getGraphics().drawText(String.valueOf(this.userProfile.getUserBestScore()), this.bestLevelBoard.getX() + this.differenceXBestWave + this.bestWaveTextPaint.measureText("best. "), this.bigUserBoard.getY() + 276, bestWaveValuePaint);
			
		}
		
	}
	
	@Override
	public void setX(int newX) {
		super.setX(newX);
		this.getShape().setLocation(newX, 361);
		smallUserBoard.setLocation(newX, 360);
		bigUserBoard.setLocation(newX, 360);
	//	smallBoardPositionStar.setLocation(newX + 57, 388);
	//	bigBoardPositionStar.setLocation(newX + 108, 388);
	//	levelStar.setLocation(newX + 149, 427);
		smallBoardUserPicture.setLocation(newX + 18, 439);
		bigBoardUserPicture.setLocation(newX + 34, 455);
	//	bestLevelBoard.setLocation(newX + 35, 599);
		
	}
	
	
}
