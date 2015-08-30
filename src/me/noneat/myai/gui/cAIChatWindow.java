package me.noneat.myai.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import me.noneat.myai.cAISettings;
import me.noneat.myai.cMain;

/**
 * Created by Noneatme on 28.08.2015.
 */
public class cAIChatWindow extends Application
{
	private GridPane grid;
	private Scene scene1;

	private int iScreenWidth        = 400;
	private int iScreenHeight       = 350;

	@Override
	public void start(Stage primaryStage) throws Exception
	{
		Parent root = FXMLLoader.load(getClass().getResource("gui.fxml"));
		primaryStage.setTitle(cAISettings.APPLICATION_NAME + " " + cAISettings.VERSION);
		primaryStage.setWidth(400);
		primaryStage.setHeight(350);
		primaryStage.show();

		this.scene1 = new Scene(root, 400, 350);
		primaryStage.setScene(this.scene1);

		/*
		this.grid       = new GridPane();
		this.grid.setAlignment(Pos.CENTER);
		this.grid.setHgap(10);
		this.grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));


		this.generateComponents();
		*/
		primaryStage.setOnCloseRequest(event -> cMain.abort());
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
	}
}
