package me.noneat.myai.sql;

import me.noneat.myai.ai.cSentenceUtils;
import me.noneat.myai.cAISettings;
import me.noneat.myai.cMain;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;

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
		try
		{
			// set main tick so we know the start ms
			this.setTickCount(System.currentTimeMillis());

			// Somehow the saem as the ABCQuestionAnswerFinder.
			// This is because the search algorithm has been just a litte bit changed.
			// declare variables
			String dbReadyString            = cSentenceUtils.getDatabaseReadyString(this.getInput(), true);
			String[] words                  = cSentenceUtils.splitSentenceIntoWords(dbReadyString.toLowerCase());
			ArrayList<String> wordArray     = new ArrayList<String>(Arrays.asList(words));
			String query                    = "SELECT * FROM " + cDatabase.TABLE_STATEMENT_SENTENCES + ";";
			ResultSet result                = cAISettings.getDatabase().createStatement().executeQuery(query);

			int maxWordCount                = words.length;         // Max Input Words
			int nearestWordCount            = 0;
			int wordSensitivity             = 2; // Je Groesser diese Variable ist desto extacter wird deine Frage erkannt
			int iResponseCat                = 0;
			int iResponseID                 = 0;
			int iResultGleichTo             = 0;
			boolean bSpeakNow               = true;
			boolean foundWord               = false;
			String nearestSentence          = "";
			cSQLAnswerFinder finder         = null;

			// Every Question
			while((!foundWord) && (result.next())) // NICHT VERANDERN, ANSONSTEN IST ES MOEGLICH DASS DER NAECHSTE DATENSATZ GENOMMEN WIRD
			{
				// Current Word count of this question that matches with the input sentence
				int curWordCount                = 0;

				// Get the variables from the row
				String curQuestion      = result.getString(2);
				String[] questionWords  = cSentenceUtils.splitSentenceIntoWords(curQuestion);

				// Is it an exact hit?
				if(curQuestion.equals(dbReadyString))
				{
					// Excacter Treffer, jawoll
					foundWord           = true;
					nearestWordCount    = curWordCount;
					nearestSentence     = curQuestion;

					iResponseCat        = result.getInt(3);
					iResponseID         = result.getInt(1);
					iResultGleichTo     = result.getInt(4);
				}
				// No, so lets go
				else
				{
					// Loop trough every word of this question in this row
					for (int i = 0; i < questionWords.length; i++)
					{
						if (!foundWord)
						{
							// Is the word in the input word array?
							if (wordArray.contains(questionWords[i].toLowerCase()))
							{
								//	System.out.println(curQuestion + ", Contains: " + questionWords[i]);
								// A match. Increase var
								// This will be done with every match in this sentence
								curWordCount++;

								// Is the match higher than the one before?
								if (curWordCount > nearestWordCount)
								{
									// Lets save this sentence, this is the nearest sentence available
									nearestWordCount = curWordCount;
									nearestSentence = curQuestion;

									iResponseCat = result.getInt(3);
									iResponseID = result.getInt(1);
									iResultGleichTo = result.getInt(4);
								}

							}
							// Is the current wordcount nothing somehow near to the base wordcount
							if ((maxWordCount - nearestWordCount) > wordSensitivity)
							{
								// Completly Rubbish, no question found
								iResultGleichTo = 0;
								nearestSentence = "";
							}
						}

					}
				}
				// MaxWordCount

				// If the found question is equal to another question in the database
				if(iResultGleichTo != 0)
				{

					// Get the vars
					iResponseID             = iResultGleichTo;

					// And the previous question
					ResultSet catResult     = cAISettings.getDatabase().createStatement().executeQuery("SELECT * FROM " + cDatabase.TABLE_STATEMENT_SENTENCES+ " WHERE iID = '" + iResponseID + "';");
					catResult.next();
					iResponseCat            = catResult.getInt(3);
				}
			}

			// Finally, is there a question found?
			if(!nearestSentence.equals(""))
			{
				// Set it
				this.setFoundQuestion(nearestSentence);
			}
			else
			{
				// Nope, lets give it a random answer
				this.setAnswer(cSentenceUtils.putAINameIntoString(cAISettings.aiManager.getRandomStatementSentenceFromCategory(1).getString(1)));
			}

			// Is there no found Statement?
			if(this.getFoundQuestion() != null)
			{
				// Kategorie Vorhanden?
				if (iResponseCat != 0)
				{
					try
					{
						// Erhalte Random Antwort
						ResultSet res = cAISettings.aiManager.getRandomStatementSentenceFromResponses(iResponseID);

						// Vorhanden?
						if(!res.isClosed())
							this.setAnswer(cSentenceUtils.putAINameIntoString(res.getString(1))); // Nehmen
						else // Ansonsten Zufallsantwort
							this.setAnswer(cSentenceUtils.putAINameIntoString(cAISettings.aiManager.getRandomStatementSentenceFromCategory(1).getString(1)));

						/*
						// Kategorie Scriptet Question?
						if(iResponseCat == 6) // Scriptet Question
						{
							bSpeakNow = false;
							finder = new cSQLScriptedQuestionManager(this.getFoundQuestion(), iResponseID, this.getInput());
						}
						*/
					}
					catch (Exception e)
					{
						e.printStackTrace();
					}
				}
				if (cSQLQuestionAnswerFinder.lastQuestions.contains(this.getFoundQuestion().replaceAll(" ", "")))
				{
					// Set Random Answer; Already Said
					this.setAnswer(cSentenceUtils.putAINameIntoString(cAISettings.aiManager.getRandomStatementSentenceFromCategory(3).getString(1)));
				}
				else
					cSQLQuestionAnswerFinder.lastQuestions.add(this.getFoundQuestion().replaceAll(" ", ""));
			}
			else
			{
				this.setAnswer(cSentenceUtils.putAINameIntoString(cAISettings.aiManager.getRandomStatementSentenceFromCategory(1).getString(1)));
			}
			// Query MSG?
			System.out.println("Query took " + this.getTickCountElapsed() + " ms");
			System.out.println("<Statement: " + this.getFoundQuestion() + ">");

			// Speak?
			if(bSpeakNow)
			{

				// Set the next answer and speak
				cMain.ai.setNextAnswer(this.getAnswer());
				cMain.ai.speak();
			}
			else
			{
				// THIRD PARTY API //
				// Google Finder //
				if((finder != null))
				{
					// Start the finder //
					finder.start();
				}
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
}
