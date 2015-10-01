package me.noneat.myai.gui.popupdisplay;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.*;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.web.WebView;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import me.noneat.myai.cAISettings;
import me.noneat.myai.cMain;
import me.noneat.myai.cmd.cGuiInputManager;
import me.noneat.myai.gui.cSystemTrayIcon;

import java.awt.*;

/**
 * Package: me.noneat.myai.gui.popupdisplay
 * Author: Noneatme
 * Date: 01.09.2015-12:02-2015.
 * Version: 1.0.0
 * License: See LICENSE.md in the top folder.
 */
public class cPopupDisplayGUI extends Application
{
	private GridPane grid;
	private Scene scene1;
	private cGuiInputManager man;
	private TextArea outputArea;

	public static boolean loading = false;

	private Thread paintThread;


	@Override
	public void start(final Stage primaryStage) throws Exception
	{
		Parent root = FXMLLoader.load(getClass().getResource("popupdisplay.fxml"));
		primaryStage.setTitle(cAISettings.APPLICATION_NAME + " " + cAISettings.VERSION);
		primaryStage.initStyle(StageStyle.UTILITY);

		this.scene1 = new Scene(root);

		primaryStage.setScene(this.scene1);
		primaryStage.getIcons().add(new Image("/me/noneat/myai/assets/images/icons/noneatme_big.png"));
		primaryStage.setResizable(false);

		generateComponents();
		primaryStage.show();
		primaryStage.setOnCloseRequest(event -> cMain.abort());
		primaryStage.setAlwaysOnTop(true);


		if (SystemTray.isSupported())
		{
			Platform.setImplicitExit(false);
			new cSystemTrayIcon().setTrayIcon(primaryStage);
		}

		// Set Pos //
		Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
		primaryStage.setX((primaryScreenBounds.getWidth() - primaryStage.getWidth() - 5));
		primaryStage.setY(((primaryScreenBounds.getHeight()/2)+primaryStage.getHeight()));

		this.paintThread.start();

	}

	private void generateComponents()
	{
		TextField textField = (TextField) this.scene1.lookup("#pane1").lookup("#pane2").lookup("#textfield-input");
		TextArea textArea = (TextArea) this.scene1.lookup("#pane1").lookup("#pane2").lookup("#textarea-output");

		textArea.setStyle("");
		textField.setOnKeyReleased(event -> this.onMyAITextfieldKeyPressed(event));
		textArea.setEditable(false);

		textArea.setWrapText(true);

		ImageView iv = (ImageView) this.scene1.lookup("#imageview2");
		ImageView loadingIv = (ImageView) this.scene1.lookup("#imageview3");


		this.outputArea = textArea;

		// Canvas IMAGE //

		this.paintThread = new Thread(){
			private boolean opState     = false;
			private double opValue      = 0.0;
			private double maxOPValue   = 1.0;
			private double minOPValue   = 0.0;

			private boolean loadingBool = false;
			private double loadingTrans = 0.0;
			private double rotateVal = 0;

			// Simple Pulsating effect
			public void run()
			{
				while(true)
				{
					try
					{
						if(opState)
						{
							if(opValue > maxOPValue)
							{
								opValue = maxOPValue;
								opState = false;
							}
							else
								opValue = opValue+0.01;
						}
						else
						{
							if(opValue < minOPValue)
							{
								opValue = minOPValue;
								opState = true;
							}
							else
								opValue = opValue-0.01;
						}
						iv.setOpacity(opValue);


						if(cPopupDisplayGUI.loading)
						{

							loadingTrans = loadingTrans+0.1;

							if(loadingTrans >= 1)
							{

									loadingTrans = 1;
							}

						}
						else
						{

							loadingTrans = loadingTrans-0.1;
							if(loadingTrans <= 0)
							{

								loadingTrans = 0;
							}

						}

						loadingIv.setOpacity(loadingTrans);
						loadingIv.setRotate(rotateVal);

						rotateVal = rotateVal+2.0;

						if(rotateVal > 360.0)
						{
							rotateVal = 0.0;
						}

						sleep(25);

						loadingIv.getParent().requestLayout();


					}
					catch(Exception ex){ex.printStackTrace();}
				}
			}
		};


	}


	private void setLoading(boolean bool)
	{
		cPopupDisplayGUI.loading = bool;
	}


	private void onMyAITextfieldKeyPressed(KeyEvent event)
	{
		if(event.getCode() == KeyCode.ENTER)
		{
			TextField textField = (TextField) this.scene1.lookup("#textfield-input");
			String str = textField.getText();

			TextArea txt = (TextArea) this.scene1.lookup("#textarea-output");

			txt.setText(txt.getText() + "You: " + str + "\n");

			this.man = new cGuiInputManager(str);
			textField.setText("");

			txt.positionCaret(txt.getText().length());

			setLoading(true);
		}
	}

	public void beginWriteAIMessage()
	{
		this.outputArea.setText(this.outputArea.getText() + cMain.ai.getAIName() + ": ");
		this.outputArea.positionCaret(this.outputArea.getText().length());

	}
	public void endWriteAIMessage()
	{
		this.outputArea.setText(this.outputArea.getText() + "\n");
		this.outputArea.positionCaret(this.outputArea.getText().length());
		setLoading(false);
	}

	public void writeAIChar(char msg)
	{
		this.outputArea.setText(this.outputArea.getText() + msg);
		this.outputArea.positionCaret(this.outputArea.getText().length());
	}
}
