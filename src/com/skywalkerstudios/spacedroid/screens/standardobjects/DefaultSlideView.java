package com.skywalkerstudios.spacedroid.screens.standardobjects;

import android.graphics.Rect;

import com.skywalkerstudios.spacedroid.Assets;
import com.skywalkerstudios.spacedroid.screens.scoresscreen.UserBoard;
import com.webstorms.framework.Game;
import com.webstorms.framework.graphics.animation.slideview.SlideViewOptions;

public class DefaultSlideView extends com.webstorms.framework.graphics.animation.slideview.SlideView {

	public static final int LEFT_BOARDER_SLIDEVIEW = 86;
	public static final int RIGHT_BOARDER_SLIDEVIEW = 401;
	
	public static final int SHIFT_LEFT = 0;
	public static final int SHIFT_RIGHT = 1;
	
	boolean shiftedRight;
	boolean shiftedLeft;
	
	public DefaultSlideView(Game game, SlideViewOptions options, Rect dimensions) {
		super(game, options, dimensions);
		this.setBitmap(Assets.standardScreen_SlideBar);
		this.getShape().setLocation(0, 361);
		
	}
	
	@Override
	public void updateSingleSlideObject(int index, int velocity) {
		super.updateSingleSlideObject(index, velocity);
		// Check if this User Board should be made big
		if(this.getSlideViewInfo().getSuppliedBoards().get(index).getX() > DefaultSlideView.LEFT_BOARDER_SLIDEVIEW && this.getSlideViewInfo().getSuppliedBoards().get(index).getX() + UserBoard.BIG_USERBOARD_WIDTH < DefaultSlideView.RIGHT_BOARDER_SLIDEVIEW) {
			
			
			
			if(((DefaultBoard) this.getSlideViewInfo().getSuppliedBoards().get(index)).getType() != UserBoard.TYPE_BIG) {
				
				
			//	this.getGame().getGameLog().d(this, "Should be made big!");
				
				// If the boards have not been shifted, then do so
			//	if(this.shiftedRight == false) {
					((DefaultBoard) this.getSlideViewInfo().getSuppliedBoards().get(index)).setType(UserBoard.TYPE_BIG);
			//		this.shiftedRight = true;
			//		this.shiftedLeft = false;
					this.shiftUserBoards(DefaultSlideView.SHIFT_RIGHT, index);
					
		//		}
			}
			
		}
		else {
			if(((DefaultBoard) this.getSlideViewInfo().getSuppliedBoards().get(index)).getType() != UserBoard.TYPE_SMALL) {
				// If the boards have not been shifted, then do so
		//		if(this.shiftedLeft == false) {
					((DefaultBoard) this.getSlideViewInfo().getSuppliedBoards().get(index)).setType(UserBoard.TYPE_SMALL);
		//			this.shiftedLeft = true;
		//			this.shiftedRight = false;
					this.shiftUserBoards(DefaultSlideView.SHIFT_LEFT, index);
					
		//		}
			}
			
		}
		
	}

	public void shiftUserBoards(int shiftType, int startingPoint) {
		int shiftDifference = UserBoard.BIG_USERBOARD_WIDTH - UserBoard.SMALL_USERBOARD_WIDTH;
		if(shiftType == DefaultSlideView.SHIFT_LEFT) {
			this.getGame().getGameLog().d(this, "Shifting left");
			for(int i = startingPoint + 1; i < this.getSlideViewInfo().getSuppliedBoards().size(); i++) { // + 1 since we don't want to move the big board!
				this.getSlideViewInfo().getSuppliedBoards().get(i).update(-shiftDifference);
			}
			
		}
		else if(shiftType == DefaultSlideView.SHIFT_RIGHT) {
			this.getGame().getGameLog().d(this, "Shifting right");
			for(int i = startingPoint + 1; i < this.getSlideViewInfo().getSuppliedBoards().size(); i++) { // + 1 since we don't want to move the big board!
				this.getSlideViewInfo().getSuppliedBoards().get(i).update(shiftDifference);
				
			}
			
		}
		
	}
	
	
}
