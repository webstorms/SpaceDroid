package com.skywalkerstudios.spacedroid.screens.hangarscreen;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import com.flurry.android.FlurryAgent;
import com.skywalkerstudios.spacedroid.Assets;
import com.skywalkerstudios.spacedroid.FileNames;
import com.skywalkerstudios.spacedroid.SpaceDroid;
import com.skywalkerstudios.spacedroid.merchandise.ammo.Ammo1;
import com.skywalkerstudios.spacedroid.merchandise.ammo.Ammo2;
import com.skywalkerstudios.spacedroid.merchandise.ammo.Ammo3;
import com.skywalkerstudios.spacedroid.screens.MenuScreen;
import com.skywalkerstudios.spacedroid.screens.standardobjects.StandardScreen;
import com.webstorms.framework.util.Util;
import com.webstorms.framework.graphics.animation.buttons.Button;
import com.webstorms.framework.graphics.animation.buttons.VirtualButton;
import com.webstorms.physics.Shape;

public class HangarScreen extends StandardScreen {
	
	private final int ERROR_NO_CONTENT_TO_SELL = this.getValueOfLastError() + 1;
	
	private final VirtualButton sellButton = new VirtualButton(this.getGame(), "Sell", this, 361, 713, new Rect(0, 0, 106, 69), Assets.hangarScreen_SellButton, Assets.hangarScreen_SellButtonClicked);
	private final VirtualButton infoButton = new VirtualButton(this.getGame(), "Info", this, 242, 713, new Rect(0, 0, 93, 69), Assets.standardScreen_InfoButton, Assets.standardScreen_InfoButtonClicked);
	
	private final String errorDialogMessageNoContentToSell = "Sorry, you possess no content that can be sold.";
	private final String welcomeDialogMessage = "In the Hangar you can view your spaceship and find all information regarding it.";
	
	// Hangar board objects
	private final Shape board = new Shape();
	private final Shape spaceshipImage = new Shape();
	private final Shape ammoImage = new Shape();
	Paint spaceshipNamePaint = new Paint();
	Paint hpPaint = new Paint();
	String spaceshipName = ((SpaceDroid) this.getGame()).getUserProfile().getSpaceship().getMerchandiseItemName();
	String spaceshipHp = ((SpaceDroid) this.getGame()).getUserProfile().getSpaceship().getAmountOfHealth() + " Hp";
	int xPositionOfSpaceshipName;
	int xPositionOfHp;
	int amountOfGunSlots = ((SpaceDroid) this.getGame()).getUserProfile().getSpaceship().getAmountOfGunSlots();
	
	private final Shape[] gunSlots = new Shape[15];
	
	public HangarScreen(SpaceDroid game) {
		super(game);
		
	}

	@Override
	public void load() {
		super.load();
		FlurryAgent.onPageView();
		FlurryAgent.logEvent("HangarScreen has been launched");
		
		this.setWelcomeScreenMessage(this.welcomeDialogMessage);
		// Board componnts
		board.addRectangle(new Rect(0, 0, 454, 325));
		board.setLocation(13, 360);

		spaceshipNamePaint.setColor(Color.WHITE);
		spaceshipNamePaint.setTextSize(34);
		spaceshipNamePaint.setTypeface(Assets.standardScreen_Birdman);

		hpPaint.setColor(Color.WHITE);
		hpPaint.setTextSize(23);
		hpPaint.setTypeface(Assets.standardScreen_Birdman);

		spaceshipImage.addRectangle(new Rect(0, 0, 210, 210));
		spaceshipImage.setLocation(222, 412);

		ammoImage.addRectangle(new Rect(0, 0, 40, 40));
		ammoImage.setLocation(222, 612);

		this.xPositionOfSpaceshipName = Util.centerObject((int) spaceshipNamePaint.measureText(spaceshipName), board.getX(), (board.getX() + board.getWidth()));
		if(this.getGame().getUserProfile().getSpaceship().getAmountOfGunSlotsInUse() != 0) {
			this.xPositionOfHp = Util.centerObject((int) hpPaint.measureText(spaceshipHp), (this.spaceshipImage.getX() + this.ammoImage.getWidth()), (this.spaceshipImage.getX() + this.spaceshipImage.getWidth()));

		}
		else {
			this.xPositionOfHp = Util.centerObject((int) hpPaint.measureText(spaceshipHp), this.spaceshipImage.getX(), (this.spaceshipImage.getX() + this.spaceshipImage.getWidth()));

		}

		initializeGunSlots();		

		if(!this.getGame().getIO().readPrimitiveInternalMemoryBoolean(FileNames.Welcome.HAS_SHOWN_HANGAR_SCREEN_WELCOME)) {
			this.setUpdateState(this.STATE_WELCOME);
			
		}

	}
	
	public void initializeGunSlots() {
		
		this.getGame().getGameLog().d(this, "gunSlots.length: " + gunSlots.length);
		
		for(int i = 0; i < gunSlots.length; i++) {
			gunSlots[i] = new Shape();
			gunSlots[i].addRectangle(new Rect(0, 0, 36, 36));
					
		}
		
		gunSlots[0].setLocation(58, 412);
		gunSlots[1].setLocation(110, 412);
		gunSlots[2].setLocation(162, 412);
		
		gunSlots[3].setLocation(58, 459);
		gunSlots[4].setLocation(110, 459);
		gunSlots[5].setLocation(162, 459);
		
		gunSlots[6].setLocation(58, 505);
		gunSlots[7].setLocation(110, 505);
		gunSlots[8].setLocation(162, 505);
		
		gunSlots[9].setLocation(58, 552);
		gunSlots[10].setLocation(110, 552);
		gunSlots[11].setLocation(162, 552);
		
		gunSlots[12].setLocation(58, 598);
		gunSlots[13].setLocation(110, 598);
		gunSlots[14].setLocation(162, 598);
		
	} 
	
	@Override
	public void onTouch(Integer x, Integer y) {
		super.onTouch(x, y);
		if(this.getUpdateState() == this.STATE_VIEWING) {
			this.getVirtualBackButton().update(x, y);
			this.infoButton.update(x, y);
			this.sellButton.update(x, y);
			
		}
		
	}
	
	@Override
	public void onButtonClicked(Button button) {
		if(this.getUpdateState() == this.STATE_WELCOME) {
			this.getGame().getIO().writePrimitiveInternalMemory(FileNames.Welcome.HAS_SHOWN_HANGAR_SCREEN_WELCOME, true);
			
		}
		super.onButtonClicked(button);
		if(this.getUpdateState() == this.STATE_VIEWING) {
			if(button.equals(this.infoButton)) {
				this.setUpdateState(this.STATE_INFO);
				
			}
			else if(button.equals(this.sellButton)) {
				if(((SpaceDroid) this.getGame()).getUserProfile().getSpaceship().hasSellingItems() != true) {
					this.setUpdateState(this.STATE_ERROR);
					this.setErrorType(this.ERROR_NO_CONTENT_TO_SELL);
					
				}
				else {
					this.getGame().setScreen(new SellScreen((SpaceDroid) this.getGame()));
					
				}
				
			}
			else if(button.equals(this.getVirtualBackButton()) || button.equals(this.getPhysicalBackButton()) || button.equals(this.getMenuButton())) {
				this.getGame().setScreen(new MenuScreen((SpaceDroid) this.getGame()));
				
			}
			
		}
		else if(this.getUpdateState() == this.STATE_INFO) {
			if(button.equals(this.getDialog().getOkayButton()) || button.equals(this.getPhysicalBackButton())) {
				this.setUpdateState(this.STATE_VIEWING);
				
			}
			else if(button.equals(this.getMenuButton())) {
				this.getGame().setScreen(new MenuScreen((SpaceDroid) this.getGame()));
				
			}
			
		}
		else if(this.getUpdateState() == this.STATE_ERROR) {
			if(button.equals(this.getDialog().getOkayButton()) || button.equals(this.getPhysicalBackButton())) {
				this.setUpdateState(this.STATE_VIEWING);
				
			}
			else if(button.equals(this.getMenuButton())) {
				this.getGame().setScreen(new MenuScreen((SpaceDroid) this.getGame()));
				
			}
			
		}
		
	}

	@Override
	public void renderViewing() {
		super.renderViewing();
		this.renderHangarBoard();
		this.getVirtualBackButton().render();
		this.infoButton.render();
		this.sellButton.render();
		
	}
	
	@Override
	public void renderInfoDialog() {
		this.getDialog().renderInfoDialog("");
		
		this.getGame().getGraphics().drawText("Damage: ", 108, 260, this.getDialog().getDialogTextConstants());
		this.getGame().getGraphics().drawText("Ammo: ", 108, 298, this.getDialog().getDialogTextConstants());
		this.getGame().getGraphics().drawText("Velocity: ", 108, 336, this.getDialog().getDialogTextConstants());
		this.getGame().getGraphics().drawText("Freq: ", 108, 374, this.getDialog().getDialogTextConstants());
		
		this.getGame().getGraphics().drawText(((SpaceDroid) this.getGame()).getUserProfile().getSpaceship().getDamage(), 373, 260, this.getDialog().getDialogTextValues());
		this.getGame().getGraphics().drawText(((SpaceDroid) this.getGame()).getUserProfile().getSpaceship().getAmountOfAmmo(), 373, 298, this.getDialog().getDialogTextValues());
		this.getGame().getGraphics().drawText(((SpaceDroid) this.getGame()).getUserProfile().getSpaceship().getVelocity(), 373, 336, this.getDialog().getDialogTextValues());
		this.getGame().getGraphics().drawText(((SpaceDroid) this.getGame()).getUserProfile().getSpaceship().getFrequency(), 373, 374, this.getDialog().getDialogTextValues());
		
	}

	@Override
	public void renderErrorDialog() {
		if(this.getErrorType() == this.ERROR_NO_CONTENT_TO_SELL) {
			this.getDialog().renderErrorDialog(this.errorDialogMessageNoContentToSell);
			
		}
		
	}
	
	public void renderHangarBoard() {
		this.getGame().getGraphics().drawBitmap(Assets.hangarScreen_Board, null, this.board.getRect(), null);
		this.getGame().getGraphics().drawText(this.spaceshipName, this.xPositionOfSpaceshipName, this.board.getY() - 36, this.spaceshipNamePaint);
		
		this.getGame().getGraphics().drawBitmap(((SpaceDroid) this.getGame()).getUserProfile().getSpaceship().getMerchandiseItemPicture(), null, this.spaceshipImage.getRect(), null);
		this.getGame().getGraphics().drawText(this.spaceshipHp, this.xPositionOfHp, 650, this.hpPaint);
		
		if(this.getGame().getUserProfile().getSpaceship().getAmountOfGunSlotsInUse() != 0) {
			if(this.getGame().getUserProfile().getSpaceship().getAmmo().getType().equals(Ammo1.CLASS_TAG)) {
				this.getGame().getGraphics().drawBitmap(Assets.merchandise_Ammo1, null, this.ammoImage.getRect(), null);
				
			}
			else if(this.getGame().getUserProfile().getSpaceship().getAmmo().getType().equals(Ammo2.CLASS_TAG)) {
				this.getGame().getGraphics().drawBitmap(Assets.merchandise_Ammo2, null, this.ammoImage.getRect(), null);
				
			}
			else if(this.getGame().getUserProfile().getSpaceship().getAmmo().getType().equals(Ammo3.CLASS_TAG)) {
				this.getGame().getGraphics().drawBitmap(Assets.merchandise_Ammo3, null, this.ammoImage.getRect(), null);
				
			}
			
		}
		
		this.renderGunSlots();
		
	}
	
	private void renderGunSlots() {
		for(int i = 0; i < amountOfGunSlots; i++) {
			if(((SpaceDroid) this.getGame()).getUserProfile().getSpaceship().getAllGunSlots()[i] != null) {
				this.getGame().getGraphics().drawBitmap(((SpaceDroid) this.getGame()).getUserProfile().getSpaceship().getAllGunSlots()[i].getMerchandiseItemPicture(), null, this.gunSlots[i].getRect(), null);
				
			}
			else {
				this.getGame().getGraphics().drawBitmap(Assets.hangarScreen_EmptyGunSlot, null, this.gunSlots[i].getRect(), null);
				
			}
			
		}
		
	}
	
	
}
