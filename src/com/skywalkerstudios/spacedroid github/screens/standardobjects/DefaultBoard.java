package com.skywalkerstudios.spacedroid.screens.standardobjects;

import android.graphics.Rect;

import com.skywalkerstudios.spacedroid.Assets;
import com.skywalkerstudios.spacedroid.SpaceDroid;
import com.skywalkerstudios.spacedroid.screens.scoresscreen.UserBoard;
import com.webstorms.framework.graphics.animation.slideview.Board;
import com.webstorms.physics.Shape;

public class DefaultBoard extends Board {

	public static final int TYPE_SMALL = 0;
	public static final int TYPE_BIG = 1;
	public static final int BIG_USERBOARD_WIDTH = 234;
	public static final int SMALL_USERBOARD_WIDTH = 143;
	
	public SpaceDroid game;
	private int type;
	
	protected DefaultBoard(SpaceDroid game) {
		super(game); // Default Bitmap
		this.setBitmap(Assets.standardScreen_SmallViewBoard);
		this.game = game;
		this.getShape().addRectangle(new Rect(0, 0, 143, 194)); // Default dimensions
		
	}
	
	public void setType(int type) {
		this.type = type;
		int x = this.getX();
		int y = this.getY();
		if(type == UserBoard.TYPE_SMALL) {
			this.getShape().overrideRectangle(0, new Rect(0, 0, 143, 194));
			this.getShape().setLocation(x, y);
			this.setBitmap(Assets.standardScreen_SmallViewBoard);
			
		}
		else {
			this.getShape().overrideRectangle(0, new Rect(0, 0, 234, 286));
			this.getShape().setLocation(x, y);
			this.setBitmap(Assets.standardScreen_BigViewBoard);
			
		}
		
	}
	
	public int getType() {
		return this.type;
		
	}
	
	
}
