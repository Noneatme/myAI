package me.noneat.myai.sql;

/**
 * Created by Noneatme on 17.08.2015.
 * Version: 1.0.0
 * Purpose: The algorithm to find the proper answer to a statement.
 * License: See top folder / document root
 */
public class cSQLStatementAnswerFinder extends cSQLAnswerFinder
{
	public cSQLStatementAnswerFinder(String sStatement)
	{
		this.setInput(sStatement);
	}

	@Override
	public synchronized void run()
	{
		this.setTickCount(System.currentTimeMillis());

	}
}
