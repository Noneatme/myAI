package me.noneat.myai.cmd;

import me.noneat.myai.cAISettings;
import me.noneat.myai.cMain;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Noneatme on 12.08.2015.
 */
// -- //
// -- || Class cUserInput
// -- \\
public class cUserInput
{
	// -- //
	// -- || Class cUserInput
	// -- \\
	private String sInput;

	// -- //
	// -- || cUserInput
	// -- \\
	public cUserInput(String input)
	{
		// Reset the close timeout for the program
		cAISettings.closeHandler.resetCloseHandlerRequest();

		// Set the input
		this.sInput = input;

		// Get Lower Case String
		String lowerCaseInput = this.sInput.toLowerCase();

		switch(lowerCaseInput)
		{
			case "-help":
				System.out.println("Available Commands:\n" +
									"-help: Displays the help\n" +
									"-learn_mode: Toggles the learn mode on / off\n" +
									"-exit, -CTRL^C, -quit: Terminates the application\n" +
									"-abort: Aborts the curent sentence in the learnmode\n");
				break;
			// Learn Mode //
			case "-learn_mode":
				// Activate Leaarn mode
				cMain.ai.setLearnMode(!cMain.ai.getLearnMode());
				break;
			// Exit //
			case "-exit":
			case "-CTRL^C":
			case "-quit":
				System.out.println("Goodbye");
				cMain.abort();
				break;
			// Abort Learn Mode //
			case "-abort":
				// Learn Mode Activated?
				if(cMain.ai.getLearnMode())
				{
					// Learning the first or second questions
					if(cMain.ai.getLearnStatusMode() == 1 || cMain.ai.getLearnStatusMode() == 2)
					{
						// Set learn mode to 0
						cMain.ai.setLearnStatusMode(0);
						System.out.println("Learn Mode Question cleared, time to input the next question.");
					}
					else
					{
						// Disable Learn Mode
						cMain.ai.setLearnMode(false);
						cMain.ai.setLearnStatusMode(0);
					}

					break; // Break only here beacuse we want the default value to be called if this condition is not true
				}
				// Default
			default:
				// Set the Ai's input to think of
				cMain.ai.setInput(this.sInput);

				// Ssave the statement
				PreparedStatement stm = cAISettings.getDatabase().createPreparedStatement("INSERT INTO ai_userinput (sInput, iAnswerTo) VALUES (?, ?);");

				try
				{
					stm.setString(1, input);
					stm.setString(2, String.valueOf(cMain.ai.getiLastResponseID()));
					cAISettings.getDatabase().executeStatement(stm);
				}
				catch (SQLException e)
				{
					e.printStackTrace();
				}

				break;
		}

	}
}
