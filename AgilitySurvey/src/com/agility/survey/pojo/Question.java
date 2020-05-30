package com.agility.survey.pojo;

import java.io.Serializable;
import java.util.Arrays;

public class Question implements Serializable {

	private static final long serialVersionUID = 4423992833776071087L;
	
	private Score score;
	private long questionId;
	private String category;
	private String prompt;
	private Choice[] choices;
	private	String[] supportingSentences;
	private int[] gradeThresholds;
	
	public Question() { }
	
	public Question(String prompt, Choice[] choices, String[] supportingSentences, int[] gradeThresholds, String category) {
		this.category = category;
		this.prompt = prompt;
		this.choices = choices;
		this.supportingSentences = supportingSentences;
		this.gradeThresholds = gradeThresholds;
	}
	
	public Question(long questionId, String prompt, Choice[] choices, String[] supportingSentences, int[] gradeThresholds, String category) {
		this.questionId = questionId;
		this.category = category;
		this.prompt = prompt;
		this.choices = choices;
		this.supportingSentences = supportingSentences;
		this.gradeThresholds = gradeThresholds;
	}
	
	public int grade() {
		int grade = 0;
		for(Choice choice : choices) {
			if(choice.isSelected()) {
				grade += choice.getWeight();
			}
		}
		if(grade < gradeThresholds[0]) {
			this.score = Score.LOW;
		} else if(grade <= gradeThresholds[1]) {
			this.score = Score.MEDIUM;
		} else {
			this.score = Score.HIGH;
		}
		return grade;
	}
	
	public String getSupportingSentenceByGrade() {
		String ss = null;
		if(this.score == Score.LOW) {
			ss = supportingSentences[0];
		} else if(this.score == Score.MEDIUM) {
			ss = supportingSentences[1];
		} else {
			ss = supportingSentences[2];
		}
		return ss;
	}

	public Score getScore() {
		return score;
	}

	public void setScore(Score score) {
		this.score = score;
	}

	public long getQuestionId() {
		return questionId;
	}

	public void setQuestionId(long questionId) {
		this.questionId = questionId;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getPrompt() {
		return prompt;
	}

	public void setPrompt(String prompt) {
		this.prompt = prompt;
	}

	public Choice[] getChoices() {
		return choices;
	}

	public void setChoices(Choice[] choices) {
		this.choices = choices;
	}

	public String[] getSupportingSentences() {
		return supportingSentences;
	}

	public void setSupportingSentences(String[] supportingSentences) {
		this.supportingSentences = supportingSentences;
	}

	public int[] getGradeThresholds() {
		return gradeThresholds;
	}

	public void setGradeThresholds(int[] gradeThresholds) {
		this.gradeThresholds = gradeThresholds;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((category == null) ? 0 : category.hashCode());
		result = prime * result + Arrays.hashCode(choices);
		result = prime * result + Arrays.hashCode(gradeThresholds);
		result = prime * result + ((prompt == null) ? 0 : prompt.hashCode());
		result = prime * result + Arrays.hashCode(supportingSentences);
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
		Question other = (Question) obj;
		if (category == null) {
			if (other.category != null)
				return false;
		} else if (!category.equals(other.category))
			return false;
		if (!Arrays.equals(choices, other.choices))
			return false;
		if (!Arrays.equals(gradeThresholds, other.gradeThresholds))
			return false;
		if (prompt == null) {
			if (other.prompt != null)
				return false;
		} else if (!prompt.equals(other.prompt))
			return false;
		if (!Arrays.equals(supportingSentences, other.supportingSentences))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Question [category=" + category + ", prompt=" + prompt + ", choices=" + Arrays.toString(choices)
				+ ", supportingSentences=" + Arrays.toString(supportingSentences) + ", gradeThresholds="
				+ Arrays.toString(gradeThresholds) + "]";
	}
	
}
