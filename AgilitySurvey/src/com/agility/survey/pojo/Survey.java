package com.agility.survey.pojo;

import java.util.Arrays;

public class Survey {

	private Score score;
	private long surveyId;
	private String name;
	private String[] categories;
	private Question[] questions;
	
	public Survey() { }
	
	public Survey(String name, String[] categories, Question[] questions) {
		this.name = name;
		this.categories = categories;
		this.questions = questions;
	}
	
	public Survey(long surveyId, String name, String[] categories, Question[] questions) {
		this.surveyId = surveyId;
		this.name = name;
		this.categories = categories;
		this.questions = questions;
	}

	public Score getScore() {
		return score;
	}

	public void setScore(Score score) {
		this.score = score;
	}

	public long getSurveyId() {
		return surveyId;
	}

	public void setSurveyId(long surveyId) {
		this.surveyId = surveyId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String[] getCategories() {
		return categories;
	}

	public void setCategories(String[] categories) {
		this.categories = categories;
	}

	public Question[] getQuestions() {
		return questions;
	}

	public void setQuestions(Question[] questions) {
		this.questions = questions;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(categories);
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + Arrays.hashCode(questions);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Survey other = (Survey) obj;
		if (!Arrays.equals(categories, other.categories))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (!Arrays.equals(questions, other.questions))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Survey [name=" + name + ", categories=" + Arrays.toString(categories) + ", questions="
				+ Arrays.toString(questions) + "]";
	}

	public void grade() {
		Arrays.stream(questions)
		      .forEach(Question::grade);
		long lowScores = Arrays.stream(questions)
						       .filter(x -> x.getScore() == Score.LOW)
						       .count();
		long mediumScores = Arrays.stream(questions)
							      .filter(y -> y.getScore() == Score.MEDIUM)
							      .count();
		long highScores = Arrays.stream(questions)
				                .filter(z -> z.getScore() == Score.HIGH)
				                .count();
		if(highScores > lowScores && highScores > mediumScores) {
			this.score = Score.HIGH;
		} else if(mediumScores > lowScores && mediumScores > highScores) {
			this.score = Score.MEDIUM;
		} else if(lowScores > highScores && lowScores > mediumScores) {
			this.score = Score.LOW;
		}
	}
	
}
