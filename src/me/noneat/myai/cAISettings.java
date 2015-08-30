package me.noneat.myai;

import me.noneat.myai.ai.cAIManager;
import me.noneat.myai.cmd.cUserInputManager;
import me.noneat.myai.sql.cDatabase;
import me.noneat.myai.util.cCloseHandler;
import me.noneat.myai.util.cConsoleUtil;

/**
 * Created by Noneatme on 12.08.2015.
 * Version: 1.0.0
 * Purpose: AI Settings
 * License: See top folder / document root
 */
// -- //
// -- || Class AI Settings
// -- \\
public class cAISettings
{
	// -- //
	// -- || PVArs
	// -- \\
	public static String APPLICATION_NAME   = "myAI / Soupe";
	public static String VERSION            = "0.0.2";
	public static String DATABASE_PATH      = "main.db";
	public static boolean DEBUG             = true;
	public static boolean USE_ASCII_CHARS   = false;
	protected static boolean instanced      = false;
	public static long TERMINATE_IDLE_TIME  = 20000;
	public static boolean USE_GUI           = true;     // Change this to false if you want the console output instead
														// Useful for debugging

	// -- //
	// -- || Singleton Instances
	// -- \\
	public static cConsoleUtil consoleUtil  = new cConsoleUtil();
	public static cDatabase database;
	public static cUserInputManager inputManager;
	public static cCloseHandler closeHandler;
	public static cAIManager aiManager;

	// -- //
	// -- || createInstances
	// -- \\
	public static void createInstances()
	{
		if(!instanced)
		{
			instanced       = true;

			database        = new cDatabase(DATABASE_PATH);
			inputManager    = new cUserInputManager();
			closeHandler    = new cCloseHandler();
			aiManager       = new cAIManager();


			// Database
			database.checkTables();
			closeHandler.start();

			System.out.println(closeHandler.getState());
		}
	}
	// -- //
	// -- || getDatabase
	// -- \\
	public static cDatabase getDatabase()
	{
		return database;
	}
}

