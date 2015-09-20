package me.noneat.myai.ai.questions;

import me.noneat.myai.cMain;

import java.util.ArrayList;

/**
 * Created by Noneatme on 31.08.2015.
 */
public abstract class cAIQuestion
{
	// -- //
	// -- || PVars
	// -- \\
	private String firstResponse;
	private int questionID;

	private ArrayList<String> questions;
	private ArrayList<String> responses;

	private boolean active;
	private int currentQuestionResponseID;

	// -- //
	// -- || Constructor
	// -- \\
	public cAIQuestion(String firstResponse, int questionID)
	{
		// ArrayLists //
		this.questions      = new ArrayList<>();
		this.responses      = new ArrayList<>();
		this.firstResponse  = firstResponse;
		this.questionID     = questionID;
		this.questions.add(firstResponse);
	}

	// -- //
	// -- || addQuestion
	// -- \\
	protected int addQuestion(String sQuestion)
	{
		this.questions.add(sQuestion);
		return this.questions.indexOf(sQuestion);
	}

	// -- //
	// -- || getQuestion
	// -- \\
	protected int getQuestionID(String sQuestion)
	{
		return this.questions.indexOf(sQuestion);
	}
	// -- //
	// -- || getQuestion
	// -- \\
	public String getQuestionFromID(int iID)
	{
		return this.questions.get(iID);
	}
	// -- //
	// -- || addResponse
	// -- \\
	public int addResponse(int iQuestionID, String sAnswer)
	{

		if(this.questions.get(iQuestionID) != null)
		{
			this.responses.add(iQuestionID, sAnswer);
			return this.responses.indexOf(sAnswer);
		}

		throw new NullPointerException();
	}

	// -- //
	// -- || getResponse
	// -- \\
	public int getResponse(int iQuestionID, String sAnswer)
	{
		return (this.responses.indexOf(sAnswer));
	}

	// -- //
	// -- || overrideGlobalQuestion
	// -- \\
	public void overrideGlobalQuestion()
	{
		cMain.ai.setScriptedQuestion(this);
		this.setActive(true);
	}

	// -- //
	// -- || isActive
	// -- \\
	public boolean isActive()
	{
		return active;
	}

	// -- //
	// -- || setActive
	// -- \\
	public void setActive(boolean active)
	{
		this.active = active;
	}

	// -- //
	// -- || getCurrentQuestionResponseID
	// -- \\
	public int getCurrentQuestionResponseID()
	{
		return currentQuestionResponseID;
	}

	// -- //
	// -- || setCurrentQuestionResponseID
	// -- \\
	public void setCurrentQuestionResponseID(int currentQuestionResponseID)
	{
		this.currentQuestionResponseID = currentQuestionResponseID;
	}

	// -- //
	// -- || enableNextQuestion
	// -- \\
	public void enableNextQuestion()
	{
		this.setCurrentQuestionResponseID(this.getCurrentQuestionResponseID() + 1);
	}

	// -- //
	// -- || onAnswerGets
	// -- \\
	public void onAnswerGet() { this.onAnswerGet(-1); }
	public abstract void onAnswerGet(int iAnswer);

	// -- //
	// -- || getFirstQuestion
	// -- \\
	public String getFirstQuestion()
	{
		return this.questions.get(1);
	}

	// -- //
	// -- || setFirstResponse
	// -- \\
	public void setFirstResponse(String firstResponse)
	{
		this.firstResponse = firstResponse;
	}

}
