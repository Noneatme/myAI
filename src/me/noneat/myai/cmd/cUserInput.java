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
		cAISettings.closeHandler.resetCloseHandlerRequest();

		this.sInput = input;
		cMain.ai.setInput(this.sInput);
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

	}
}
