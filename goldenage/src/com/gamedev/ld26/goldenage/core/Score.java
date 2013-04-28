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

}
