package me.noneat.myai;

import me.noneat.myai.ai.cAIManager;
import me.noneat.myai.cmd.cUserInputManager;
import me.noneat.myai.sql.cDatabase;
import me.noneat.myai.util.cCloseHandler;
import me.noneat.myai.util.cConsoleUtil;

/**
 * Created by Noneatme on 12.08.2015.
 */
// -- //
// -- || Class AI Settings
// -- \\
public class cAISettings
{
	// -- //
	// -- || PVArs
	// -- \\
	public static String VERSION            = "0.0.1";
	public static boolean DEBUG             = true;
	public static String DATABASE_PATH      = "main.db";
	public static boolean USE_ASCII_CHARS   = false;

	protected static boolean instanced      = false;

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

	public static cDatabase getDatabase()
	{
		return database;
	}
}
