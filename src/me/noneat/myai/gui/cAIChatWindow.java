package me.noneat.myai.gui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import me.noneat.myai.cAISettings;
import me.noneat.myai.cMain;
import me.noneat.myai.cmd.cGuiInputManager;

import java.awt.SystemTray;


/**
 * Created by Noneatme on 28.08.2015.
 */
public class cAIChatWindow extends Application
{
	private GridPane grid;
	private Scene scene1;

	private cGuiInputManager man;

	private boolean bRadioStarted   = false;
	private boolean bRadioStarting  = false;

	private Label radioStationLabel;

	@Override
	public void start(final Stage primaryStage) throws Exception
	{
		Parent root = FXMLLoader.load(getClass().getResource("gui.fxml"));
		primaryStage.setTitle(cAISettings.APPLICATION_NAME + " " + cAISettings.VERSION);
		primaryStage.initStyle(StageStyle.DECORATED);

		this.scene1 = new Scene(root);
		primaryStage.setScene(this.scene1);
		primaryStage.getIcons().add(new Image("/me/noneat/myai/assets/images/icons/noneatme_big.png"));


		// NONEAT.ME//

		//	WebView web2 = (WebView) this.scene1.lookup("#browser-noneatme");
		//	web2.getEngine().load("http://noneat.me:7778/");



		/*
		this.grid       = new GridPane();
		this.grid.setAlignment(Pos.CENTER);
		this.grid.setHgap(10);
		this.grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));
		*/

		primaryStage.show();
		this.generateComponents();
		primaryStage.setOnCloseRequest(event -> cMain.abort());

		if (SystemTray.isSupported())
		{
			Platform.setImplicitExit(false);
			new cSystemTrayIcon().setTrayIcon(primaryStage);
		}

		java.net.CookieHandler.setDefault(new com.sun.webkit.network.CookieManager());


	}

	private void generateComponents()
	{
		// CSS //
	/*
		Text scenetitle     = new Text("Welcome");
		Text infoText       = new Text(cAISettings.APPLICATION_NAME + " " + cAISettings.VERSION + " is currently in heavy development. You are testing the early alpha kernel features.");

		scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		scenetitle.setId("titel-text");

		infoText.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		infoText.setWrappingWidth(this.iScreenWidth-25);
		infoText.setId("info-text");

		this.grid.add(scenetitle, 0, 0, 2, 1);
		this.grid.add(infoText, 1, 5);
	*/


		TextField hes = (TextField) this.scene1.lookup("#textfield-myai");
		hes.setOnKeyPressed(this::onMyAITextfieldKeyPressed);

		TextArea area = (TextArea) this.scene1.lookup("#textarea-myai");
		area.setEditable(false);

		// 1 //
		WebView web = (WebView) this.scene1.lookup("#webview-weather2");

		WebView web3 = (WebView) this.scene1.lookup("#browser-kalendar");

		// IMAGES TABS //
		TabPane pane = (TabPane) this.scene1.lookup("#tabpane1");

		// Tab1 Image //<<
		Tab tab1 = pane.getTabs().get(0);       // Wetter
		Tab tab2 = pane.getTabs().get(1);       // Kalender
		Tab tab3 = pane.getTabs().get(2);       // MyAI
		Tab tab4 = pane.getTabs().get(3);       // RRadio

		GridPane gPane = (GridPane) pane.lookup("#radio-gridpane");

		// BUTTONS //

		// 1LIVE //
		Button btn1   = (Button) pane.lookup("#radio-list-button-1");
		btn1.setOnAction(event -> onRadioButtonClick("1live"));
		Button btn2   = (Button) pane.lookup("#radio-list-button-2");
		btn2.setOnAction(event -> onRadioButtonClick("top100"));

		Button btnstart   = (Button) pane.lookup("#radio-button1");
		btnstart.setOnAction(event -> onRadioButtonStartClick());

		Button btnstop   = (Button) pane.lookup("#radio-button2");
		btnstop.setOnAction(event -> onRadioButtonStopClick());

		this.radioStationLabel  = (Label) pane.lookup("#radio-label-1");

		try
		{
			ImageView icon = new ImageView(new javafx.scene.image.Image("me/noneat/myai/assets/images/icons/weather.png"));
			ImageView icon2 = new ImageView(new javafx.scene.image.Image("me/noneat/myai/assets/images/icons/document.png"));
			ImageView icon3 = new ImageView(new javafx.scene.image.Image("me/noneat/myai/assets/images/icons/chat.png"));
			ImageView icon4 = new ImageView(new javafx.scene.image.Image("me/noneat/myai/assets/images/icons/radio.png"));

			tab1.setGraphic(icon);
			tab2.setGraphic(icon2);
			tab3.setGraphic(icon3);
			tab4.setGraphic(icon4);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

	}

	private void onRadioButtonClick(String sRadio)
	{
		String sURL             = "";
		String sStation         = "";
		switch(sRadio)
		{
			case "1live":
				sURL        = "http://www.wdr.de/wdrlive/media/einslive.m3u";
				sStation    = "1Live";
				break;

			case "top100":
				sURL        = "http://top100station.de/stream/winamp.pls";
				sStation    = "Top100Station";
				break;
		}

		if(sURL.length() > 1)
		{
			this.radioStationLabel.setText("Station: " + sStation);
		}
		else
		{
			this.radioStationLabel.setText("Station: -");
		}
	}

	private void onRadioButtonStartClick()
	{

	}

	private void onRadioButtonStopClick()
	{
		this.radioStationLabel.setText("Station: -");
	}

	private void onMyAITextfieldKeyPressed(KeyEvent event)
	{
		if(event.getCode() == KeyCode.ENTER)
		{
			TextField textField = (TextField) this.scene1.lookup("#textfield-myai");
			String str = textField.getText();

			TextArea txt = (TextArea) this.scene1.lookup("#textarea-myai");

			txt.setText(txt.getText() + "You: " + str + "\n");

			this.man = new cGuiInputManager(str);
			textField.setText("");

		}
	}


	public void writeAIMessage(String msg)
	{
		TextArea txt = (TextArea) this.scene1.lookup("#textarea-myai");
		txt.setText(txt.getText() + cMain.ai.getAIName() + ": " + msg + "\n\n");
		txt.positionCaret(txt.getText().length());
	}
}
