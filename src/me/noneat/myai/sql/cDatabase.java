package me.noneat.myai.sql;

import me.noneat.myai.cAISettings;
import me.noneat.myai.cMain;

import javax.swing.*;
import java.sql.*;

/**
 * Created by Noneatme on 12.08.2015.
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

	// -- //
	// -- || Constructor
	// -- \\
	public cDatabase(String sDatabaseFile)
	{
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
			ResultSet rSystem = this.executeQuery("SELECT * FROM ai_system;");
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