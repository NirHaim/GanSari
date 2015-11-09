package com.example.gansari;

public class SampleData {

	private String name;
	private boolean selected;
	private int visiblity;

	public SampleData(String name, boolean selected, int visibility) {
		super();
		this.name= name;
		this.selected= selected;
		this.visiblity= visibility;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public int getVisibility() {
		return visiblity;
	}
	
	public void setVisibility(int visibility) {
		this.visiblity = visibility;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

}