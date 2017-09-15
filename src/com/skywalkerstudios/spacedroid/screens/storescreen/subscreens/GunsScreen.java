package com.skywalkerstudios.spacedroid.screens.storescreen.subscreens;

import java.util.ArrayList;
import java.util.List;

import com.flurry.android.FlurryAgent;
import com.skywalkerstudios.spacedroid.SpaceDroid;
import com.skywalkerstudios.spacedroid.merchandise.MerchandiseItem;
import com.skywalkerstudios.spacedroid.merchandise.guns.Gun;
import com.skywalkerstudios.spacedroid.merchandise.guns.Gun1;
import com.skywalkerstudios.spacedroid.merchandise.guns.Gun2;
import com.skywalkerstudios.spacedroid.merchandise.guns.Gun3;
import com.skywalkerstudios.spacedroid.screens.playscreen.World;
import com.skywalkerstudios.spacedroid.screens.playscreen.gameobjects.PlayerDroid;
import com.skywalkerstudios.spacedroid.screens.playscreen.gameobjects.Lasers.AmmoBase;
import com.skywalkerstudios.spacedroid.screens.playscreen.gameobjects.Lasers.LaserBullet;
import com.skywalkerstudios.spacedroid.screens.storescreen.StoreScreen;
import com.webstorms.framework.graphics.animation.slideview.SlideViewOptions;

public class GunsScreen extends StandardStoreScreen {

	private final int ERROR_NOT_ENOUGH_SPACE_FOR_GUNS = this.getValueOfLastError() + 1;
	
	private final String errorDialogMessageNotEnoughSpaceInSpacehip = "Sorry, your gun slots in your Spaceship are all in use";
	
	public GunsScreen(SpaceDroid game) {
		super(game);
		
	}
	
	@Override
	public void load() {
		super.load();
		FlurryAgent.onPageView();
		FlurryAgent.logEvent("GunsScreen has been launched");

	}
	
	@Override
	public void initializeSlideView(SlideViewOptions options) {
		super.initializeSlideView(options);
		this.initializeSlideViewInfo((List<Object>)(List<?>) new ArrayList<Gun>());
		this.getSlideViewInfo().add(new Gun1(this.getGame()));
		this.getSlideViewInfo().add(new Gun2(this.getGame()));
		this.getSlideViewInfo().add(new Gun3(this.getGame()));
		
	}

	@Override
	protected void onPurchase() {
		if(((MerchandiseItem) this.getSlideViewInfo().get(this.getSlideView().getIndexOfBoardClosestToRestPosition())).getClassTag().equals(Gun1.CLASS_TAG)) {
			((SpaceDroid) this.getGame()).getUserProfile().getSpaceship().placeGunIntoShip(new Gun1(this.getGame()));
			this.getGame().getGameLog().d(this, "Just placed a Gun1 into the spaceship");
			
		}
		else if(((MerchandiseItem) this.getSlideViewInfo().get(this.getSlideView().getIndexOfBoardClosestToRestPosition())).getClassTag().equals(Gun2.CLASS_TAG)) {
			((SpaceDroid) this.getGame()).getUserProfile().getSpaceship().placeGunIntoShip(new Gun2(this.getGame()));
			this.getGame().getGameLog().d(this, "Just placed a Gun2 into the spaceship");
			
		}
		else if(((MerchandiseItem) this.getSlideViewInfo().get(this.getSlideView().getIndexOfBoardClosestToRestPosition())).getClassTag().equals(Gun3.CLASS_TAG)) {
			((SpaceDroid) this.getGame()).getUserProfile().getSpaceship().placeGunIntoShip(new Gun3(this.getGame()));
			this.getGame().getGameLog().d(this, "Just placed a Gun3 into the spaceship");
			
		}
		this.getGame().getUserProfile().getSpaceship().sortGunsAccordingToDamage();
		
	}

	@Override
	protected void updatePlayerDroid(PlayerDroid playerDroid) {
		playerDroid.getGun().setShootFrequency(this.getGame().getUserProfile().getSpaceship().getShipShootingFrequency());
		playerDroid.getGun().setShootDamage(this.getGame().getUserProfile().getSpaceship().getShipShootingDamage());
		playerDroid.getGun().setShootVelocity(this.getGame().getUserProfile().getSpaceship().getShipShootingVelocity());
		playerDroid.getGun().setAmmo(this.getNewAmountOfAmmo());
		playerDroid.getGun().attachNewMagazine(this.getNewMagazine());
		
	}
	
	private int getNewAmountOfAmmo() {
		int newAmountOfAmmo = ((World) this.getGame().getWorld()).getPlayerDroid().getGun().getCurrentAmmo();
		
		if(((MerchandiseItem) this.getSlideViewInfo().get(this.getSlideView().getIndexOfBoardClosestToRestPosition())).getClassTag().equals(Gun1.CLASS_TAG)) {
			newAmountOfAmmo += new Gun1(this.getGame()).getAmountOfAmmo();
			
		}
		else if(((MerchandiseItem) this.getSlideViewInfo().get(this.getSlideView().getIndexOfBoardClosestToRestPosition())).getClassTag().equals(Gun2.CLASS_TAG)) {
			newAmountOfAmmo += new Gun2(this.getGame()).getAmountOfAmmo();
			
		}
		else if(((MerchandiseItem) this.getSlideViewInfo().get(this.getSlideView().getIndexOfBoardClosestToRestPosition())).getClassTag().equals(Gun3.CLASS_TAG)) {
			newAmountOfAmmo += new Gun3(this.getGame()).getAmountOfAmmo();
			
		}
	
		return newAmountOfAmmo;
		
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
		for(int i = 0; i < newMagazine.length; i++) {
			newMagazine[i] = new LaserBullet(this.getGame(), this.getGame().getWorld(), this.getGame().getWorld().getPlayerDroid());
			newMagazine[i].dispose();

		}

		return newMagazine;
		
		
	}
	
	@Override
	protected boolean onCheckForErrors() {
		// Check if new Gun can be purchased
		if(!((SpaceDroid) this.getGame()).getUserProfile().getSpaceship().isSpaceForNewGun()) {
			this.setErrorType(this.ERROR_NOT_ENOUGH_SPACE_FOR_GUNS);
			this.getGame().getGameLog().d(this, "Not enough gun slots");
			return true;
			
		}
		else {
			return false;
			
		}
		
	}

	@Override
	protected boolean onCheckForWarnings() {
		return false;
		
	}

	@Override
	protected int getStartPositionForStoreScreen() {
		return StoreScreen.START_POSITION_GUNS;
	}
	
	@Override
	public void renderInfoDialog() {
		super.renderInfoDialog();
		
		// Render the constants
		this.getGame().getGraphics().drawText("Ammo: ", 108, 413, this.getDialog().getDialogTextConstants());
		this.getGame().getGraphics().drawText("Freq: ", 108, 452, this.getDialog().getDialogTextConstants());
		this.getGame().getGraphics().drawText("Damage: ", 108, 492, this.getDialog().getDialogTextConstants());
		this.getGame().getGraphics().drawText("Velocity: ", 108, 531, this.getDialog().getDialogTextConstants());

		// Render the values of the specific gun
		this.getGame().getGraphics().drawText(((Gun) this.getSlideViewInfo().get(this.getSlideView().getIndexOfBoardClosestToRestPosition())).getAmountOfAmmoSS(), 373, 413, this.getDialog().getDialogTextValues());
		this.getGame().getGraphics().drawText(((Gun) this.getSlideViewInfo().get(this.getSlideView().getIndexOfBoardClosestToRestPosition())).getAmountOfFrequencySS(), 373, 452, this.getDialog().getDialogTextValues());
		this.getGame().getGraphics().drawText(((Gun) this.getSlideViewInfo().get(this.getSlideView().getIndexOfBoardClosestToRestPosition())).getAmountOfDamageSS(), 373, 492, this.getDialog().getDialogTextValues());
		this.getGame().getGraphics().drawText(((Gun) this.getSlideViewInfo().get(this.getSlideView().getIndexOfBoardClosestToRestPosition())).getAmountOfVelocitySS(), 373, 531, this.getDialog().getDialogTextValues());
		
	}

	@Override
	public void renderErrorDialog() {
		super.renderErrorDialog();
		if(this.getErrorType() == this.ERROR_NOT_ENOUGH_SPACE_FOR_GUNS) {
			this.getDialog().renderErrorDialog(this.errorDialogMessageNotEnoughSpaceInSpacehip);
			
		}
		
	}
	
	
}
