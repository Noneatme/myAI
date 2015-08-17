package me.noneat.myai.sql;

/**
 * Created by Jonas Barascu on 17.08.2015.
 */

// -- //
// -- || SQLAnswerFinder
// -- \\
public abstract class cSQLAnswerFinder extends Thread
{
	// -- //
	// -- ||
	// -- \\
	private String sAnswer;
	private String sInput;
	private int iLastRowCount;
	private long tickCount;

	private String foundQuestion;

	// -- //
	// -- || run
	// -- \\
	public abstract void run();

	// -- //
	// -- || setAnswer
	// -- \\
	protected String setAnswer(String answer)
	{
		this.sAnswer = answer;
		return this.getAnswer();
	}

	// -- //
	// -- || getAnswer
	// -- \\
	protected String getAnswer()
	{
		return this.sAnswer;
	}

	// -- //
	// -- || setInput
	// -- \\
	protected String setInput(String input)
	{
		this.sInput = input;
		return this.getInput();
	}

	// -- //
	// -- || getInput
	// -- \\
	protected String getInput()
	{
		return this.sInput;
	}

	// -- //
	// -- || getLastRowCount
	// -- \\
	protected int getLastRowCount()
	{
		return iLastRowCount;
	}

	// -- //
	// -- ||setLastRowCount
	// -- \\
	protected void setLastRowCount(int iLastRowCount)
	{
		this.iLastRowCount = iLastRowCount;
	}

	// -- //
	// -- || setTickCount
	// -- \\
	protected void setTickCount(long tickCount)
	{
		this.tickCount = tickCount;
	}

	// -- //
	// -- || getTickCount
	// -- \\
	protected long getTickCount()
	{
		return this.tickCount;
	}

	// -- //
	// -- || getTickCountElapsed
	// -- \\
	protected long getTickCountElapsed()
	{
		return System.currentTimeMillis()-this.getTickCount();
	}

	// -- //
	// -- || getFoundQuestion
	// -- \\
	protected String getFoundQuestion()
	{
		return foundQuestion;
	}

	// -- //
	// -- || setFoundQuestion
	// -- \\
	protected void setFoundQuestion(String foundQuestion)
	{
		this.foundQuestion = foundQuestion;
	}
}
