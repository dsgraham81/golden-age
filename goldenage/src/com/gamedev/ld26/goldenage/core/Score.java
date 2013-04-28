package com.gamedev.ld26.goldenage.core;

public class Score {

	
	private static int _score;
	
	public static void ResetScore()
	{
		_score = 0;
	}
	
	public static int GetScore()
	{
		return _score;
	}
	
	public static void AddToScore(int amount)
	{
		_score += amount;
	}
	
	public static String getScoreString(int padding){
		 String tempString = String.format("%0"+padding+"d", _score);
		 if (tempString.length()> padding)
		 {
			 tempString = "";
			 for(int i =0;i < padding; i++)
			 {
				 tempString += "9";
			 }
		 }
		 tempString = "Score: " + tempString;
		return tempString;
	}

}
