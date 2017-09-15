package com.skywalkerstudios.spacedroid.screens.storescreen.subscreens;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Color;
import android.graphics.Paint.Align;
import android.text.TextPaint;

import com.flurry.android.FlurryAgent;
import com.skywalkerstudios.spacedroid.SpaceDroid;
import com.skywalkerstudios.spacedroid.merchandise.MerchandiseItem;
import com.skywalkerstudios.spacedroid.merchandise.ammo.Ammo;
import com.skywalkerstudios.spacedroid.merchandise.ammo.Ammo1;
import com.skywalkerstudios.spacedroid.merchandise.ammo.Ammo2;
import com.skywalkerstudios.spacedroid.merchandise.ammo.Ammo3;
import com.skywalkerstudios.spacedroid.screens.playscreen.World;
import com.skywalkerstudios.spacedroid.screens.playscreen.gameobjects.PlayerDroid;
import com.skywalkerstudios.spacedroid.screens.playscreen.gameobjects.Lasers.AmmoBase;
import com.skywalkerstudios.spacedroid.screens.playscreen.gameobjects.Lasers.LaserBullet;
import com.skywalkerstudios.spacedroid.screens.storescreen.StoreScreen;
import com.webstorms.framework.graphics.animation.slideview.SlideViewOptions;

public class AmmoScreen extends StandardStoreScreen {

	private final int ERROR_NO_GUNS_IN_SPACESHIP_FOR_AMMO = this.getValueOfLastError() + 1;
	
	private final int WARNING_SPACESHIP_ALLREADY_HAS_AMMO = this.getValueOfLastWarning() + 1;
	private final int WARNING_SPACESHIP_ALLREADY_HAS_AMMO_OF_STRONGER_TYPE = this.getValueOfLastWarning() + 2;
	
	private final String errorDialogMessageNoGunsInSpaceshipForAmmo = "Sorry, the Spaceship doesn't contain any guns for the ammo";
	private final String warningDialogMessageSpaceshipAllreadyContainsAmmo = "Spaceship allready contains some ammo. Continue.";
	private final String warningDialogMessageSpaceshipAllreadyContainsAmmoOfStrongerType = "Spaceship still contains some stronger ammo. Continue.";
	
	TextPaint defaultPaintConstants = new TextPaint();
	TextPaint defaultPaintValues = new TextPaint();
	
	public AmmoScreen(SpaceDroid game) {
		super(game);
		
	}
	
	@Override
	public void load() {
		super.load();
		FlurryAgent.onPageView();
		FlurryAgent.logEvent("AmmoScreen has been launched");

		defaultPaintConstants.setColor(Color.rgb(215, 215, 205));
		defaultPaintConstants.setTextSize(29);
		defaultPaintConstants.setTextAlign(Align.LEFT);

		defaultPaintValues.setColor(Color.rgb(215, 215, 205));
		defaultPaintValues.setTextSize(29);
		defaultPaintValues.setTextAlign(Align.RIGHT);

	}
	
	@Override
	public void initializeSlideView(SlideViewOptions options) {
		super.initializeSlideView(options);
		this.initializeSlideViewInfo((List<Object>)(List<?>) new ArrayList<Ammo>());
		this.getSlideViewInfo().add(new Ammo1(this.getGame()));
		this.getSlideViewInfo().add(new Ammo2(this.getGame()));
		this.getSlideViewInfo().add(new Ammo3(this.getGame()));
		
	}

	@Override
	protected void onPurchase() {
		this.getGame().getGameLog().d(this, "Ammo: MerchandiseBoardName: " + ((MerchandiseItem) this.getSlideViewInfo().get(this.getSlideView().getIndexOfBoardClosestToRestPosition())).getClassTag());
		this.getGame().getGameLog().d(this, "Ammo: Ammo Tag: " + Ammo2.class.getSimpleName());
		
		if(((MerchandiseItem) this.getSlideViewInfo().get(this.getSlideView().getIndexOfBoardClosestToRestPosition())).getClassTag().equals(Ammo1.class.getSimpleName())) {
			((SpaceDroid) this.getGame()).getUserProfile().getSpaceship().setAmmo(new Ammo1(this.getGame()));
			
		}
		else if(((MerchandiseItem) this.getSlideViewInfo().get(this.getSlideView().getIndexOfBoardClosestToRestPosition())).getClassTag().equals(Ammo2.class.getSimpleName())) {
			((SpaceDroid) this.getGame()).getUserProfile().getSpaceship().setAmmo(new Ammo2(this.getGame()));
			
		}
		else if(((MerchandiseItem) this.getSlideViewInfo().get(this.getSlideView().getIndexOfBoardClosestToRestPosition())).getClassTag().equals(Ammo3.class.getSimpleName())) {
			((SpaceDroid) this.getGame()).getUserProfile().getSpaceship().setAmmo(new Ammo3(this.getGame()));
			
		}
		
	}

	@Override
	protected void updatePlayerDroid(PlayerDroid playerDroid) {
		playerDroid.replenishAmmo();
		playerDroid.getGun().attachNewMagazine(this.getNewMagazine());
		playerDroid.getGun().setShootDamage(this.getGame().getUserProfile().getSpaceship().getShipShootingDamage()); // Not necessary 
		
		
	}
	
	private LaserBullet[] getNewMagazine() {		
		// 1. Add all active bullets to the tempMag of the PlayerDroid
		// 2. Return the new mag

		// Copy all the active bullets to the tempMagazine magazine
		List<AmmoBase> tempMagazineList = new ArrayList<AmmoBase>();

		// Add existing TempMag to the new TempMag that we are creating
		if(((World) this.getGame().getWorld()).getPlayerDroid().getTempMagazine() != null) {
			for(int i = 0; i < ((World) this.getGame().getWorld()).getPlayerDroid().getTempMagazine().length; i++) {
				tempMagazineList.add(((World) this.getGame().getWorld()).getPlayerDroid().getTempMagazine()[i]);

			}

		}

		for(int i = 0; i < this.getGame().getWorld().getPlayerDroid().getGun().getMagazineSize(); i++) {
			if(!this.getGame().getWorld().getPlayerDroid().getGun().getMagazine()[i].isDisposable()) {
				tempMagazineList.add(this.getGame().getWorld().getPlayerDroid().getGun().getMagazine()[i]);
				this.getGame().getGameLog().d(this, "Adding new Bullet to TempMagazine");

			}

		}
		LaserBullet[] tempMagazineArray = tempMagazineList.toArray(new LaserBullet[tempMagazineList.size()]);
		this.getGame().getWorld().getPlayerDroid().setTempMagazine(tempMagazineArray);

		// Initialize the new Magazine and apply it to the Spaceship
		LaserBullet[] newMagazine = new LaserBullet[((SpaceDroid) this.getGame()).getUserProfile().getSpaceship().getShipMaxAmountOfAmmo()];
		this.getGame().getWorld().getPlayerDroid().getGun().setAmmoType(this.getGame().getUserProfile().getSpaceship().getAmmo().getType());
		for(int i = 0; i < newMagazine.length; i++) {
			newMagazine[i] = new LaserBullet(this.getGame(), this.getGame().getWorld(), this.getGame().getWorld().getPlayerDroid());
			newMagazine[i].dispose();

		}

		return newMagazine;
		
		
	}
	
	@Override
	protected boolean onCheckForErrors() {
		// Check if there are guns in the spaceship for the ammo
		if(this.getGame().getUserProfile().getSpaceship().getAmountOfGunSlotsInUse() == 0) {
			this.setErrorType(this.ERROR_NO_GUNS_IN_SPACESHIP_FOR_AMMO);
			return true;
			
		}
		else {
			return false;
			
		}
		
	}

	@Override
	protected boolean onCheckForWarnings() {
		if(this.getGame().getWorld() == null || this.getGame().getWorld().getPlayerDroid().getGun().getCurrentAmmo() > 0) {
			if(((Ammo) this.getSlideViewInfo().get(this.getSlideView().getIndexOfBoardClosestToRestPosition())).getRank() > this.getGame().getUserProfile().getSpaceship().getAmmo().getRank()) {
				this.setWarningType(this.WARNING_SPACESHIP_ALLREADY_HAS_AMMO_OF_STRONGER_TYPE);
				
			}
			else {
				this.setWarningType(this.WARNING_SPACESHIP_ALLREADY_HAS_AMMO);
				
			}
			
			return true;
			
		}
		else {
			return false;
			
		}
		
	}

	@Override
	protected int getStartPositionForStoreScreen() {
		return StoreScreen.START_POSITION_AMMO;
	}
	
	@Override
	public void renderInfoDialog() {
		super.renderInfoDialog();
		
		// Render the constants
		this.getGame().getGraphics().drawText("Amount:", 108, 413, this.getDialog().getDialogTextConstants());
		this.getGame().getGraphics().drawText("Add. Damage:", 108, 452, this.getDialog().getDialogTextConstants());
		this.getGame().getGraphics().drawText("% ", 108 + 20, 492, this.defaultPaintConstants);
		this.getGame().getGraphics().drawText(":", 108 + 22 + 20, 492, this.getDialog().getDialogTextConstants());
		this.getGame().getGraphics().drawText("P: ", 108 + 20, 532, this.getDialog().getDialogTextConstants());
		
		// Render the values
		this.getGame().getGraphics().drawText(((Ammo) this.getSlideViewInfo().get(this.getSlideView().getIndexOfBoardClosestToRestPosition())).getAmountOfAmmo() + " U", 373, 413, this.getDialog().getDialogTextValues());
		
		this.getGame().getGraphics().drawText("+", 380 - this.getDialog().getDialogTextConstants().measureText("" + ((Ammo) this.getSlideViewInfo().get(this.getSlideView().getIndexOfBoardClosestToRestPosition())).getAmountOfDamagePercentage()) - 48, 492, this.defaultPaintValues);
		this.getGame().getGraphics().drawText("" + ((Ammo) this.getSlideViewInfo().get(this.getSlideView().getIndexOfBoardClosestToRestPosition())).getAmountOfDamagePercentage(), 343, 492, this.getDialog().getDialogTextValues());
		this.getGame().getGraphics().drawText("%", 373, 492, this.defaultPaintValues);
		
		this.getGame().getGraphics().drawText("+", 380 - this.getDialog().getDialogTextConstants().measureText("" + ((Ammo) this.getSlideViewInfo().get(this.getSlideView().getIndexOfBoardClosestToRestPosition())).getAmountOfDamageHP()) - 49, 532, this.defaultPaintValues);
		this.getGame().getGraphics().drawText(((Ammo) this.getSlideViewInfo().get(this.getSlideView().getIndexOfBoardClosestToRestPosition())).getAmountOfDamageHP() + " P", 373, 532, this.getDialog().getDialogTextValues());
		
	}

	@Override
	public void renderErrorDialog() {
		super.renderErrorDialog();
		if(this.getErrorType() == this.ERROR_NO_GUNS_IN_SPACESHIP_FOR_AMMO) {
			this.getDialog().renderErrorDialog(this.errorDialogMessageNoGunsInSpaceshipForAmmo);
			
		}
		
	}

	@Override
	public void renderWarningDialog() {
		super.renderWarningDialog();
		if(this.getWarningType() == this.WARNING_SPACESHIP_ALLREADY_HAS_AMMO) {
			this.getDialog().renderWarningDialog(this.warningDialogMessageSpaceshipAllreadyContainsAmmo);
			
		}
		else if(this.getWarningType() == this.WARNING_SPACESHIP_ALLREADY_HAS_AMMO_OF_STRONGER_TYPE) {
			this.getDialog().renderWarningDialog(this.warningDialogMessageSpaceshipAllreadyContainsAmmoOfStrongerType);
			
		}
		
	}
	
	
}