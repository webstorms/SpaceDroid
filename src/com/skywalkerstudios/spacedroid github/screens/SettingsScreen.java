package com.skywalkerstudios.spacedroid.screens;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Rect;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.flurry.android.FlurryAgent;
import com.skywalkerstudios.spacedroid.Assets;
import com.skywalkerstudios.spacedroid.FileNames;
import com.skywalkerstudios.spacedroid.SpaceDroid;
import com.skywalkerstudios.spacedroid.screens.standardobjects.StandardScreen;
import com.webstorms.framework.graphics.animation.buttons.Button;
import com.webstorms.framework.graphics.animation.buttons.VirtualButton;

public class SettingsScreen extends StandardScreen {
	
	private final VirtualButton profileNameButton = new VirtualButton(this.getGame(), "Profile", this, 108, 298, new Rect(0, 0, 265, 72), Assets.settingsScreen_ProfileNameButton, Assets.settingsScreen_ProfileNameButtonClicked);
	private final VirtualButton tiltSensitivityButton = new VirtualButton(this.getGame(), "TiltSensitivity", this, 108, 394, new Rect(0, 0, 265, 72), Assets.settingsScreen_TiltSensitivityButton, Assets.settingsScreen_TiltSensitivityButtonClicked);
	private final VirtualButton musicButton = new VirtualButton(this.getGame(), "Music", this, 108, 489, new Rect(0, 0, 265, 72), Assets.settingsScreen_MusicButton, Assets.settingsScreen_MusicButtonClicked);
	private final VirtualButton soundButton = new VirtualButton(this.getGame(), "Sound", this, 108, 584, new Rect(0, 0, 265, 72), Assets.settingsScreen_SoundButton, Assets.settingsScreen_SoundButtonClicked);
	private final VirtualButton viabrationButton = new VirtualButton(this.getGame(), "Viabration", this, 108, 677, new Rect(0, 0, 265, 72), Assets.settingsScreen_VibrationButton, Assets.settingsScreen_VibrationButtonClicked);
	
	private final String welcomeDialogMessage = "Here you will be able to adjust all game and profile settings.";
	
	protected SettingsScreen(SpaceDroid game) {
		super(game);
		
	}
	
	@Override
	public void load() {
		super.load();
		FlurryAgent.onPageView();
		FlurryAgent.logEvent("SettingsScreen has been launched");
		this.setWelcomeScreenMessage(this.welcomeDialogMessage);
		
		if(!this.getGame().getIO().readPrimitiveInternalMemoryBoolean(FileNames.Welcome.HAS_SHOWN_SETTINGS_SCREEN_WELCOME)) {
			this.setUpdateState(this.STATE_WELCOME);
			
		}
		
	}
	
	@Override
	public void onTouch(Integer x, Integer y) {
		super.onTouch(x, y);
		if(this.getUpdateState() == this.STATE_VIEWING) {
			// Profile Name button
			this.profileNameButton.update(x, y);
			// Tilt Sensitivity button
			this.tiltSensitivityButton.update(x, y);
			
			// Music button
			this.musicButton.update(x, y);
			
			// Sound button
			this.soundButton.update(x, y);
			
			// Viabration button
			this.viabrationButton.update(x, y);
			
			// Virtual Back button
			this.getVirtualBackButton().update(x, y);
			
		}
		
	}
	
	@Override
	public void onButtonClicked(Button button) {
		if(this.getUpdateState() == this.STATE_WELCOME) {
			this.getGame().getIO().writePrimitiveInternalMemory(FileNames.Welcome.HAS_SHOWN_SETTINGS_SCREEN_WELCOME, true);
			
		}
		super.onButtonClicked(button);
		if(this.getUpdateState() == this.STATE_VIEWING) {
			if(button.equals(this.profileNameButton)) {
				this.showChangeProfileNameDialog();
				
			}
			else if(button.equals(this.tiltSensitivityButton)) {
				this.showChangeTiltSensitivityDialog();
				
			}
			else if(button.equals(this.musicButton)) {
				this.showChangeMusicVolumeDialog();
				
			}
			else if(button.equals(this.soundButton)) {
				this.showChangeSoundVolumeDialog();
				
			}
			else if(button.equals(this.viabrationButton)) {
				((SpaceDroid) this.getGame()).getUserProfile().setViabrationOn(!((SpaceDroid) this.getGame()).getUserProfile().getViabrationOn());
				
			}
			else if(button.equals(this.getVirtualBackButton()) || button.equals(this.getPhysicalBackButton()) || button.equals(this.getMenuButton())) {
				this.getGame().setScreen(new MenuScreen((SpaceDroid) this.getGame()));
				
			}
			
		}
		
	}

	private void showChangeProfileNameDialog() {
		this.getGame().runOnUiThread(new Runnable() {
			public void run() {
				
				AlertDialog builder = new AlertDialog.Builder(getGame()).create();
				builder.setTitle("User name");
				builder.setCanceledOnTouchOutside(false);
				
				// Set up the input
				final EditText input = new EditText(getGame());
				input.setText(((SpaceDroid) getGame()).getUserProfile().getUserName());
				builder.setView(input);

				// Set up the buttons
				builder.setButton(Dialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() { 
					@Override
					public void onClick(DialogInterface dialog, int which) {
						Assets.standardScreen_Click.play();
						((SpaceDroid) getGame()).getUserProfile().setUserName(input.getText().toString());
						dialog.cancel();
						
					}
					
				});
				builder.setButton(Dialog.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						Assets.standardScreen_Click.play();
						dialog.cancel();
						
					}
					
				});
				  
				builder.show();
				
			}
		});
		
	}

	private void showChangeTiltSensitivityDialog() {
		getGame().runOnUiThread(new Runnable() {
			public void run() {
				  
				final AlertDialog builder = new AlertDialog.Builder(getGame()).create();
				builder.setTitle("Tilt sensitivity");
				builder.setCanceledOnTouchOutside(false);

				LinearLayout layout = new LinearLayout(getGame());
				layout.setOrientation(LinearLayout.VERTICAL);

				final TextView output = new TextView(getGame());
				output.setPadding(10, 10, 10, 10);
				output.setTextSize(20);
				output.setTextColor(Color.WHITE);
				output.setText(Integer.toString(((SpaceDroid) getGame()).getUserProfile().getTiltSensitivity()));
				layout.addView(output);
				 	
				final SeekBar input = new SeekBar(getGame());
				input.setProgress(((SpaceDroid) getGame()).getUserProfile().getTiltSensitivity());
				input.setMax(9);
				input.setPadding(10, 10, 10, 10);
				layout.addView(input);
				 
				builder.setView(layout);

				input.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
					public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser){
						output.setText(Integer.toString(progress + 1));
				        	
					}

					@Override
					public void onStartTrackingTouch(SeekBar arg0) {
							
						
					}

					@Override
					public void onStopTrackingTouch(SeekBar arg0) {
							
						
					}
				});
				  
				// Set up the buttons
				builder.setButton(Dialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() { 
					@Override
					public void onClick(DialogInterface dialog, int which) {
						Assets.standardScreen_Click.play();
						((SpaceDroid) getGame()).getUserProfile().setTiltSensitivity(input.getProgress() + 1);
						
					}
					
				});
				builder.setButton(Dialog.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						Assets.standardScreen_Click.play();
						dialog.cancel();
						
					}
					
				});
				  
				builder.show();
				
			}
		});
		
	}

	private void showChangeMusicVolumeDialog() {
		getGame().runOnUiThread(new Runnable() {
			public void run() {
				  
				final int initialMusicVolume = getGame().getUserProfile().getMusicVolume();
				
				final AlertDialog builder = new AlertDialog.Builder(getGame()).create();
				builder.setTitle("Music volume");
				builder.setCanceledOnTouchOutside(false);

				LinearLayout layout = new LinearLayout(getGame());
				layout.setOrientation(LinearLayout.VERTICAL);

				final TextView output = new TextView(getGame());
				output.setPadding(10, 10, 10, 10);
				output.setTextSize(20);
				output.setTextColor(Color.WHITE);
				output.setText(Integer.toString(initialMusicVolume));
				layout.addView(output);
				 	
				final SeekBar input = new SeekBar(getGame());
				input.setProgress(initialMusicVolume);
				input.setMax(100);
				input.setPadding(10, 10, 10, 10);
				layout.addView(input);
				 
				builder.setView(layout);

				input.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
					public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser){
						output.setText(Integer.toString(progress));
						getGame().getUserProfile().setMusicVolume(input.getProgress());
						
					}

					@Override
					public void onStartTrackingTouch(SeekBar arg0) {
							
						
					}

					@Override
					public void onStopTrackingTouch(SeekBar arg0) {
							
						
					}
				});
				  
				// Set up the buttons
				builder.setButton(Dialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() { 
					@Override
					public void onClick(DialogInterface dialog, int which) {
						Assets.standardScreen_Click.play();
						dialog.cancel();
						
					}
					
				});
				builder.setButton(Dialog.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						Assets.standardScreen_Click.play();
						getGame().getUserProfile().setMusicVolume(initialMusicVolume);
						dialog.cancel();
						
					}
					
				});
				  
				builder.show();
				
			}
		});
		
	}

	private void showChangeSoundVolumeDialog() {
		getGame().runOnUiThread(new Runnable() {
			public void run() {
				  
				final int initialSoundVolume = getGame().getUserProfile().getSoundVolume();
				
				final AlertDialog builder = new AlertDialog.Builder(getGame()).create();
				builder.setTitle("Sound volume");
				builder.setCanceledOnTouchOutside(false);

				LinearLayout layout = new LinearLayout(getGame());
				layout.setOrientation(LinearLayout.VERTICAL);

				final TextView output = new TextView(getGame());
				output.setPadding(10, 10, 10, 10);
				output.setTextSize(20);
				output.setTextColor(Color.WHITE);
				output.setText(Integer.toString(initialSoundVolume));
				layout.addView(output);
				 	
				final SeekBar input = new SeekBar(getGame());
				input.setProgress(initialSoundVolume);
				input.setMax(100);
				input.setPadding(10, 10, 10, 10);
				layout.addView(input);
				 
				builder.setView(layout);

				input.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
					public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser){
						output.setText(Integer.toString(progress));
						getGame().getUserProfile().setSoundVolume(input.getProgress());
						
					}

					@Override
					public void onStartTrackingTouch(SeekBar arg0) {
							
						
					}

					@Override
					public void onStopTrackingTouch(SeekBar arg0) {
							
						
					}
					
				});
				  
				// Set up the buttons
				builder.setButton(Dialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() { 
					@Override
					public void onClick(DialogInterface dialog, int which) {
						Assets.standardScreen_Click.play();
						dialog.cancel();
						
					}
					
				});
				builder.setButton(Dialog.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						Assets.standardScreen_Click.play();
						getGame().getUserProfile().setSoundVolume(initialSoundVolume);
						dialog.cancel();
						
					}
					
				});
				  
				builder.show();
				
			}
		});
		
	}
	
	@Override
	public void renderViewing() {
		super.renderViewing();
		this.profileNameButton.render();
		this.tiltSensitivityButton.render();
		
		this.musicButton.render();
		if(!this.musicButton.isPressed() && this.getGame().getUserProfile().getMusicVolume() == 0) {
			this.getGame().getGraphics().drawBitmap(Assets.settingsScreen_MuteButton, null, new Rect(209, this.musicButton.getY() + 3, 209 + 66, this.musicButton.getY() + 3 + 66), null);
			
		}
		else if(this.getGame().getUserProfile().getMusicVolume() == 0) {
			this.getGame().getGraphics().drawBitmap(Assets.settingsScreen_MuteButtonClicked, null, new Rect(209, this.musicButton.getY() + 3, 209 + 66, this.musicButton.getY() + 3 + 66), null);
			
		}

		this.soundButton.render();
		if(!this.soundButton.isPressed() && this.getGame().getUserProfile().getSoundVolume() == 0) {
			this.getGame().getGraphics().drawBitmap(Assets.settingsScreen_MuteButton, null, new Rect(209, this.soundButton.getY() + 3, 209 + 66, this.soundButton.getY() + 3 + 66), null);
			
		}
		else if(this.getGame().getUserProfile().getSoundVolume() == 0) {
			this.getGame().getGraphics().drawBitmap(Assets.settingsScreen_MuteButtonClicked, null, new Rect(209, this.soundButton.getY() + 3, 209 + 66, this.soundButton.getY() + 3 + 66), null);
			
		}
		
		this.viabrationButton.render();
		if(!this.viabrationButton.isPressed() && !((SpaceDroid) this.getGame()).getUserProfile().getViabrationOn()) {
			this.getGame().getGraphics().drawBitmap(Assets.settingsScreen_MuteButton, null, new Rect(209, this.viabrationButton.getY() + 3, 209 + 66, this.viabrationButton.getY() + 3 + 66), null);
			
		}
		else if(!((SpaceDroid) this.getGame()).getUserProfile().getViabrationOn()) {
			this.getGame().getGraphics().drawBitmap(Assets.settingsScreen_MuteButtonClicked, null, new Rect(209, this.viabrationButton.getY() + 3, 209 + 66, this.viabrationButton.getY() + 3 + 66), null);
			
		}
		
		this.getVirtualBackButton().render();
		
	}
	
	
}