package me.noneat.myai.sql;

import me.noneat.myai.cAISettings;
import me.noneat.myai.cMain;

import static java.nio.file.StandardCopyOption.*;

import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.*;

/**
 * Created by Noneatme on 12.08.2015.
 * Version: 1.0.0
 * Purpose: Manages the database connection
 * License: See top folder / document root
 */
// -- //
// -- || cDatabase
// -- \\
public class cDatabase
{
	// -- //
	// -- || PVars
	// -- \\
	private String m_sDatabaseFile;
	private Connection con;
	private Statement stat;

	public static final String DB_TYPE                              = "sqlite"; // Change to MySQL if you want a MySQL Server instaed

	public static final String TABLE_QUESTIONS_ASKABLE              = "ai_questions_askable";
	public static final String TABLE_QUESTIONS_CATEGORIES           = "ai_questions_categories";
	public static final String TABLE_QUESTIONS_RESPONSES            = "ai_questions_responses";
	public static final String TABLE_AI_SYSTEM                      = "ai_system";

	public static final String TABLE_USER_INPUT                     = "user_input";
	public static final String TABLE_USER_INFORMATIONS              = "user_informations";

	public static final String TABLE_STATEMENT_CATEGORIES           = "ai_statement_categories";
	public static final String TABLE_STATEMENT_SENTENCES            = "ai_statement_sentences";
	public static final String TABLE_STATEMENT_RESPONSES            = "ai_statement_responses";

	// -- //
	// -- || Constructor
	// -- \\
	public cDatabase(String sDatabaseFile)
	{
		switch(cDatabase.DB_TYPE)
		{
			case "sqlite":
				this.m_sDatabaseFile = sDatabaseFile;
				try
				{
					Class.forName("org.sqlite.JDBC");
					try
					{
						this.con = DriverManager.getConnection("jdbc:sqlite:" + this.m_sDatabaseFile);
						if(this.stat == null || this.stat.isClosed())
							stat = this.con.createStatement();

					}
					catch(Exception ex)
					{
						ex.printStackTrace();
					}
				}
				catch(Exception ex)
				{
					System.err.println("SQLite Database Library is missing!");
					ex.printStackTrace();
					cMain.abort();
				}
				break;
		}
	}

	// -- //
	// -- || ExecuteUpdate
	// -- \\
	public int executeUpdate(String sUpdate)
	{
		try
		{
			if(this.stat == null || this.stat.isClosed())
				stat = this.con.createStatement();

			final int i = stat.executeUpdate(sUpdate);

			return i;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}

		return 0;
	}

	// -- //
	// -- || createPrepatedStatement
	// -- \\
	public PreparedStatement createPreparedStatement(String sQuery)
	{
		try
		{
			PreparedStatement stat = this.con.prepareStatement(sQuery);
			return stat;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return null;
	}

	// -- //
	// -- || ExecuteStatement
	// -- \\
	public boolean executeStatement(PreparedStatement statement)
	{
		try
		{
			return statement.execute();
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return false;
	}

	// -- //
	// -- || ExecuteQuery
	// -- \\
	public ResultSet executeQuery(String sQuery)
	{
		try
		{
			if(this.stat == null || this.stat.isClosed())
				this.stat = this.con.createStatement();

			return this.stat.executeQuery(sQuery);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return null;
	}

	// -- //
	// -- || renewTables
	// -- \\
	private boolean renewTables() throws Exception
	{
		if(cAISettings.DEBUG)
		{

		}
		else
		{
			throw new Exception();
		}
		return false;
	}
	// -- //
	// -- || checkTables
	// -- \\ LOADS ALL DATAS
	public void checkTables()
	{
		try
		{
			// CHECK SYSTEM
			ResultSet rSystem = this.executeQuery("SELECT * FROM " + cDatabase.TABLE_AI_SYSTEM + ";");
			rSystem.next();

			// APPLY VARS
			cMain.ai.setAIName(rSystem.getString(1));
			cMain.ai.setAIDateCreated(rSystem.getString(2));
			cMain.ai.setAIOwner(rSystem.getString(3));


			// AI MANAGER
			cAISettings.aiManager.load();
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}

	public Statement createStatement() throws SQLException
	{
		return this.con.createStatement();
	}

	public Connection getConnection()
	{
		return this.con;
	}
}