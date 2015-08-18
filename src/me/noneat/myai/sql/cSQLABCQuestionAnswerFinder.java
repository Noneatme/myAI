package me.noneat.myai.sql;

import me.noneat.myai.ai.cSentenceUtils;
import me.noneat.myai.cAISettings;
import me.noneat.myai.cMain;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Noneatme on 17.08.2015.
 */

// -- //
// -- || SQLQuestionAnswerFinder
// -- \\
public class cSQLABCQuestionAnswerFinder extends cSQLAnswerFinder
{
	public static ArrayList<String> lastQuestions = new ArrayList<>();

	// -- //
	// -- || PVARS
	// -- \\
	public cSQLABCQuestionAnswerFinder(String sText)
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

			String[] words                  = cSentenceUtils.splitSentenceIntoWords(cSentenceUtils.getDatabaseReadyString(this.getInput(), true));
			ArrayList<String> wordArray     = new ArrayList<String>(Arrays.asList(words));
			String query                    = "SELECT * FROM ai_questions;";
			ResultSet result                = cAISettings.getDatabase().createStatement().executeQuery(query);

			int maxWordCount                = words.length;
			int nearestWordCount            = 0;
			int wordSensitivity             = 2; // Je Groesser diese Variable ist desto extacter wird deine Frage erkannt
			int iQuestionCat                = 0;
			int iQuestionID                 = 0;
			boolean bSpeakNow               = true;
			boolean foundWord               = false;
			String nearestSentence          = "";
			cSQLAnswerFinder finder         = null;

			// Every Question
			while((!foundWord) && (result.next())) // NICHT VERANDERN, ANSONSTEN IST ES MOEGLICH DASS DER NAECHSTE DATENSATZ GENOMMEN WIRD
			{
				int curWordCount                = 0;

				String curQuestion      = result.getString(2);
				String[] questionWords  = cSentenceUtils.splitSentenceIntoWords(curQuestion);

				for (int i = 0; i < questionWords.length; i++)
				{
					if(wordArray.contains(questionWords[i]))
					{
						curWordCount++;
						if((curWordCount == maxWordCount) && (curWordCount > questionWords.length-wordSensitivity))
						{
							foundWord           = true;
							nearestSentence     = curQuestion;
						//	System.out.println("Excact");
							break; // Word Found
						}
						else
						{
							if ((nearestWordCount < curWordCount) && (nearestWordCount > maxWordCount - wordSensitivity))
							{
								nearestWordCount    = curWordCount;
								nearestSentence     = curQuestion;
								System.out.println("Guessing: " + nearestSentence);
							}
						}
					}
					else
					{
					//	curWordCount = curWordCount-1;
					}
				}

				iQuestionCat    = result.getInt(3);
				iQuestionID     = result.getInt(1);
				if (result.getInt(4) != 0)
				{

					iQuestionID             = result.getInt(4);
					//iQuestionCat    = result.getInt(4);
					ResultSet catResult     = cAISettings.getDatabase().createStatement().executeQuery("SELECT * FROM ai_questions WHERE iQuestionID = '" + result.getInt(4) + "';");
					catResult.next();
					iQuestionCat            = catResult.getInt(3);
				}
			}

			if(!nearestSentence.equals(""))
			{
				this.setFoundQuestion(nearestSentence);
			}
			else
			{
				this.setAnswer(cSentenceUtils.putAINameIntoString(cAISettings.aiManager.getRandomSentenceFromCategory(2).getString(1)));
			}

			// Frage Gefunden?
			if(this.getFoundQuestion() != null)
			{
				// Kategorie Vorhanden?
				if (iQuestionCat != 0)
				{
					try
					{
						// Erhalte Random Antwort
						ResultSet res = cAISettings.aiManager.getRandomSentenceFromResponses(iQuestionID);

						// Vorhanden?
						if(!res.isClosed())
							this.setAnswer(cSentenceUtils.putAINameIntoString(res.getString(1))); // Nehmen
						else // Ansonsten Zufallsantwort
							this.setAnswer(cSentenceUtils.putAINameIntoString(cAISettings.aiManager.getRandomSentenceFromCategory(2).getString(1)));

						// Kategorie Scriptet Question?
						if(iQuestionCat == 6) // Scriptet Question
						{
							bSpeakNow = false;
							finder = new cSQLScriptedQuestionManager(this.getFoundQuestion(), iQuestionID);
						}
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

			// Query MSG?
			System.out.println("Query took " + this.getTickCountElapsed() / 1000 + " ms");
			System.out.println("<Question: " + this.getFoundQuestion() + ">");

			// Speak?
			if(bSpeakNow)
			{
				cMain.ai.setNextAnswer(this.getAnswer());
				cMain.ai.speak();
			}
			else
			{
				if((finder != null))
				{
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
