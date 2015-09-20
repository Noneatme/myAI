package me.noneat.myai.ai.questions;

/**
 * Created by Noneatme on 31.08.2015.
 */
public class cAIQuestionAskQuestion extends cAIQuestion
{
	public cAIQuestionAskQuestion(String firstQuestion, int questionID)
	{
		super(firstQuestion, questionID);

		int b1 = this.addQuestion("What would you like to ask?");
		int b2 = this.addQuestion("Is this the second question?");
	}

	@Override
	public void onAnswerGet(int iAnswerID)
	{
		switch(iAnswerID)
		{
			case 1:
				break;
			case 2:
				break;
		}
		// Has to be called on every callback so the next question is enabled.
		this.enableNextQuestion();
	}
}
