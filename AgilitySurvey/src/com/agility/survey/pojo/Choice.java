package com.agility.survey.pojo;

import java.io.Serializable;

public class Choice implements Serializable{

	private static final long serialVersionUID = 7511968496614119709L;
	
	private String text;
	private int weight;
	private boolean selected;
	
	public Choice() { }
	
	public Choice(String text, int weight) {
		this.text = text;
		this.weight = weight;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (selected ? 1231 : 1237);
		result = prime * result + ((text == null) ? 0 : text.hashCode());
		result = prime * result + weight;
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
		Choice other = (Choice) obj;
		if (selected != other.selected)
			return false;
		if (text == null) {
			if (other.text != null)
				return false;
		} else if (!text.equals(other.text))
			return false;
		if (weight != other.weight)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Choice [text=" + text + ", weight=" + weight + ", selected=" + selected + "]";
	}
	
}
