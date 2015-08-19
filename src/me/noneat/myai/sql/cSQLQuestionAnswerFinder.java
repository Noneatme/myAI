package me.noneat.myai.sql;

import me.noneat.myai.ai.cSentenceUtils;
import me.noneat.myai.cAISettings;
import me.noneat.myai.cMain;

import javax.xml.transform.Result;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Noneatme on 17.08.2015.
 * Purpose: The search algorithm to find the proper question of a users input
 * Version: 1.0.0
 * License: See top folder / document root
 */

// -- //
// -- || SQLQuestionAnswerFinder
// -- \\
public class cSQLQuestionAnswerFinder extends cSQLAnswerFinder
{
	public static ArrayList<String> lastQuestions = new ArrayList<>();

	// -- //
	// -- || PVARS
	// -- \\
	public cSQLQuestionAnswerFinder(String sText)
	{
		this.setInput(sText);
	}

	// -- //
	// -- || Run
	// -- \\

	@Override
	public synchronized void run()
	{
		try
		{
			this.setTickCount(System.currentTimeMillis());

			String[] words = cSentenceUtils.splitSentenceIntoWords(this.getInput());

			String query = "SELECT * FROM " + cDatabase.TABLE_QUESTIONS_ASKABLE + " WHERE ";

		/*
		String query        = "SELECT * FROM ai_questions WHERE ";
		String addQuery1    = "sQuestion LIKE '%";
		String addQuery2    = "%' OR sQuestion LIKE '%";
		String addQuery3    = "%';";


		query += addQuery1;
		for(int i = 0; i < words.length; i++)
		{
			query += words[i];

			if(i != words.length-1)
				query += addQuery2;
			else
				query += addQuery3;
		}


		*/

			int iQuestionCat = 0;

			// For all Words
			for (int i = 0; i < words.length; i++)
			{
				// Build Query
				query += " sQuestion LIKE '%" + words[i].toLowerCase() + "%'";
				//System.out.println(query);
				//System.out.println(query);
				System.out.println(query);
			/*
			if(i-1 == words.length)
				query += ";";
			else
				query += " AND ";
			*/

				// Get Temp Reuslt
				// Define TempResult
				ResultSet tempResult = null;
				try
				{
					// Find the current Query with AND
					tempResult = cAISettings.getDatabase().createStatement().executeQuery(query);
				}
				catch (SQLException e)
				{
					e.printStackTrace();
				}
				// Try
				try
				{
					// Define Variables
					int rowCount = 0;
					String firstRowData = null;

					// Get Row Count //
					while (tempResult.next())
					{
						rowCount++;
						if (rowCount == 1)
						{
							firstRowData = tempResult.getString(2);
							iQuestionCat = tempResult.getInt(1);
							if (tempResult.getInt(4) != 0)
								iQuestionCat = tempResult.getInt(4);
						}
					}

					// Are there rows
					if (rowCount > 0)
					{
						// Less Rows than the row before?
						if ((this.getLastRowCount() <= rowCount))
						{
							this.setLastRowCount(rowCount);

							if (words.length-1 == i)
							{
								System.out.println("Found " + this.getLastRowCount() + " possible questions");

								if (this.getLastRowCount() > 1)
								{
									// Set Random Answer; Not Found
									this.setAnswer(cSentenceUtils.putAINameIntoString(cAISettings.aiManager.getRandomSentenceFromCategory(2).getString(1)));
								}
								else
								{
									this.setFoundQuestion(firstRowData);
								}
							}
						}
						else
						{
							//System.out.println("Found " + this.getLastRowCount() + " possible questions");
							this.setFoundQuestion(firstRowData);
						}
						query += " AND";
					}
					else
					{
						// Set Random Answer; Not Found
						this.setAnswer(cSentenceUtils.putAINameIntoString(cAISettings.aiManager.getRandomSentenceFromCategory(2).getString(1)));
						break;
					}
				}
				catch (Exception ex)
				{
					ex.printStackTrace();
				}

			}

			System.out.println("<Question: " + this.getFoundQuestion() + ">");

			if(this.getFoundQuestion() != null)
			{
				if (iQuestionCat != 0)
				{
					try
					{
						ResultSet res = cAISettings.aiManager.getRandomSentenceFromResponses(iQuestionCat);
						this.setAnswer(cSentenceUtils.putAINameIntoString(res.getString(1)));
					}
					catch (Exception e)
					{
						e.printStackTrace();
					}
				}

				if (cSQLQuestionAnswerFinder.lastQuestions.contains(this.getFoundQuestion().replaceAll(" ", "")))
				{
					// Set Random Answer; Already Said
					this.setAnswer(cSentenceUtils.putAINameIntoString(cAISettings.aiManager.getRandomSentenceFromCategory(5).getString(1)));
				}
				else
					cSQLQuestionAnswerFinder.lastQuestions.add(this.getFoundQuestion().replaceAll(" ", ""));

			}
			else
			{
				this.setAnswer(cSentenceUtils.putAINameIntoString(cAISettings.aiManager.getRandomSentenceFromCategory(2).getString(1)));
			}

			System.out.println("Query took " + this.getTickCountElapsed() / 1000 + " ms");
			cMain.ai.setNextAnswer(this.getAnswer());
			cMain.ai.speak();
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}

}
