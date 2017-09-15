package com.skywalkerstudios.spacedroid.screens.standardobjects;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Paint.Align;
import android.text.TextPaint;
import android.text.method.ScrollingMovementMethod;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.skywalkerstudios.spacedroid.Assets;
import com.webstorms.framework.graphics.animation.buttons.VirtualButton;
import com.webstorms.framework.util.Executor;
import com.webstorms.framework.util.Util;
import com.webstorms.physics.Shape;

public class Dialog {

	public static final String TITLE_INFO = "INFO";
	public static final String TITLE_ENQUIRY = "ENQUIRY";
	public static final String TITLE_CONFIRMATION = "CONFIRMATION";
	public static final String TITLE_ERROR = "ERROR";
	public static final String TITLE_WARNING = "WARNING";
	public static final String TITLE_WELCOME = "WELCOME";
	
	private final static int X_OF_MESSAGE_BODY = 108;
	private final static int Y_OF_MESSAGE_BODY = 260;
	
	private StandardScreen source;
	private MessageBodyExecutor messageBodyExecutor;
	private boolean isShowing;
	
	// Dialog Components
	private final Shape dialogShape = new Shape(29, 93, new Rect(0, 0, 427, 616));
	private final Paint dialogAlphaChanel = new Paint();
	private final TextPaint dialogTitle = new TextPaint();
	private final TextPaint dialogTextConstants = new TextPaint();
	private final TextPaint dialogTextValues = new TextPaint();
	private final VirtualButton okayButton;
	private final VirtualButton yesButton;
	private final VirtualButton noButton;	
	private final VirtualButton notNowButton;
	private final TextView messageBody;
	private final LinearLayout layoutForMessageBody;
	
	public Dialog(StandardScreen source) {
		this.source = source;
		
		// Init paints
		dialogAlphaChanel.setAlpha(225);

		dialogTitle.setColor(Color.rgb(215, 215, 205));
		dialogTitle.setTypeface(Assets.standardScreen_Birdman);
		dialogTitle.setTextSize(31);

		dialogTextConstants.setColor(Color.rgb(215, 215, 205));
		dialogTextConstants.setTypeface(Assets.standardScreen_Birdman);
		dialogTextConstants.setTextSize(29);
		dialogTextConstants.setTextAlign(Align.LEFT);

		dialogTextValues.setColor(Color.rgb(215, 215, 205));
		dialogTextValues.setTypeface(Assets.standardScreen_Birdman);
		dialogTextValues.setTextSize(29);
		dialogTextValues.setTextAlign(Align.RIGHT);

		// Init buttons
		okayButton = new VirtualButton(this.source.getGame(), "Okay", this.source, 108, 582, new Rect(0, 0, 265, 72), Assets.standardScreen_OkayButton, Assets.standardScreen_OkayButtonClicked);
		yesButton = new VirtualButton(this.source.getGame(), "Yes", this.source, 108, 487, new Rect(0, 0, 265, 72), Assets.standardScreen_YesButton, Assets.standardScreen_YesButtonClicked);
		noButton = new VirtualButton(this.source.getGame(), "No", this.source, 108, 582, new Rect(0, 0, 265, 72), Assets.standardScreen_NoButton, Assets.standardScreen_NoButtonClicked);
		notNowButton = new VirtualButton(this.source.getGame(), "NotNow", this.source, 108, 582, new Rect(0, 0, 265, 72), Assets.standardScreen_NotNowButton, Assets.standardScreen_NotNowButtonClicked);
		
		messageBody = new TextView(source.getGame());
		layoutForMessageBody = new LinearLayout(source.getGame());
		this.initMessageBody();
		messageBodyExecutor = new MessageBodyExecutor(new Runnable() {
			
			@Override
			public void run() {
				renderMessageBody(messageBodyExecutor.getMessageBody(), messageBodyExecutor.getBottomPadding());
				
			}
			
		});
		
	}
	
	private static class MessageBodyExecutor extends Executor {

		private String messageBody;
		private int bottomPadding;
		
		// Setter
		public MessageBodyExecutor(Runnable runnable) {
			super(runnable);
			
		}
		
		public void setMessageBody(String messageBody) {
			this.messageBody = messageBody;
			
		}
		
		public void setBottomPadding(int bottomPadding) {
			this.bottomPadding = bottomPadding;
			
		}
		
		// Getter
		public String getMessageBody() {
			return this.messageBody;
			
		}
		
		public int getBottomPadding() {
			return this.bottomPadding;
			
		}
		
		
	}
	
	// Getter methods

	public boolean isShowing() {
		return this.isShowing;
		
	}
	
	public Shape getShape() {
		return this.dialogShape;
		
	}
	
	public TextPaint getDialogTextConstants() {
		return this.dialogTextConstants;

	}

	public TextPaint getDialogTextValues() {
		return this.dialogTextValues;

	}

	public VirtualButton getOkayButton() {
		return this.okayButton;

	}

	public VirtualButton getYesButton() {
		return this.yesButton;

	}

	public VirtualButton getNoButton() {
		return this.noButton;

	}

	public VirtualButton getNotNowButton() {
		return this.notNowButton;

	}
	
	public void initMessageBody() {
		source.getGame().runOnUiThread(new Runnable() {
			public void run() {
				messageBody.setTextSize(20);
				messageBody.setTextColor(Color.rgb(215, 215, 205));
				messageBody.setTypeface(Assets.standardScreen_Birdman);
				messageBody.setMovementMethod(new ScrollingMovementMethod());
				layoutForMessageBody.setOrientation(LinearLayout.VERTICAL);
				layoutForMessageBody.addView(messageBody);
				source.getGame().addContentView(layoutForMessageBody, new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
				
			}
			
		});
		
	}
	
	public void disposeDialog() {
		// Dipose message body content
		source.getGame().runOnUiThread(new Runnable() {
			public void run() {
				messageBody.setText("");
				
			}
			
		});
		this.isShowing = false;
		this.messageBodyExecutor.reset();
		
	}
	
	private void renderMessageBody(final String message, final int bottomPadding) {
		source.getGame().runOnUiThread(new Runnable() {
			public void run() {
				messageBody.setText(message);
				messageBody.scrollTo(0, 0);
				
				float horizontalRatio = source.getGame().getWSScreen().getRealWidth() / (float) source.getGame().getWSScreen().getGameScreenWidth();
				float verticalRatio = source.getGame().getWSScreen().getRealHeight() / (float) source.getGame().getWSScreen().getGameScreenHeight();
				
				layoutForMessageBody.setPadding((int) (Dialog.X_OF_MESSAGE_BODY * horizontalRatio), (int) (Dialog.Y_OF_MESSAGE_BODY * verticalRatio), (int) (Dialog.X_OF_MESSAGE_BODY * horizontalRatio), (int) (bottomPadding * verticalRatio));
				
			}
			
		});
		
	}
	
	public void renderCustomDialog(final String title, final String message, final int bottomPadding) {
		this.isShowing = true;
		
		// Dialog
		this.source.getGame().getGraphics().drawBitmap(Assets.standardScreen_LargeDialog, null, this.dialogShape.getRect(), dialogAlphaChanel);
				
		// Title
		source.getGame().getGraphics().drawText(title, Util.centerObject((int) this.dialogTitle.measureText(title), 0, source.getGame().getWSScreen().getGameScreenWidth()), 193, this.dialogTitle);
		
		// Message
		this.messageBodyExecutor.setMessageBody(message);
		this.messageBodyExecutor.setBottomPadding(bottomPadding);
		this.messageBodyExecutor.execute();
		
		
	}
	
	public void renderInfoDialog(String messageBody) {
		this.renderCustomDialog(Dialog.TITLE_INFO, messageBody, 218 + 20);
		
		// Okay button
		this.okayButton.render();

	}

	public void renderEnquiryDialog(String messageBody) {
		this.renderCustomDialog(Dialog.TITLE_ENQUIRY, messageBody, 313 + 20);
		
		// Buttons
		this.yesButton.render();
		this.notNowButton.render();

	}

	public void renderConfirmationDialog(String messageBody) {
		this.renderCustomDialog(Dialog.TITLE_CONFIRMATION, messageBody, 218 + 20);

		// Okay button
		this.okayButton.render();

	}

	public void renderErrorDialog(String messageBody) {
		this.renderCustomDialog(Dialog.TITLE_ERROR, messageBody, 218 + 20);

		// Okay button
		this.okayButton.render();

	}

	public void renderWarningDialog(String messageBody) {
		this.renderCustomDialog(Dialog.TITLE_WARNING, messageBody, 313 + 20);

		// Buttons
		this.yesButton.render();
		this.noButton.render();

	}
	
	public void renderWelcomeDialog(String messageBody) {
		this.renderCustomDialog(Dialog.TITLE_WELCOME, messageBody, 218 + 20);

		// Buttons
		this.okayButton.render();

	}
	
	
}
