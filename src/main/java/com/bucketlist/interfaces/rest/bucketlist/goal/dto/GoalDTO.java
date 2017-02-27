package com.bucketlist.interfaces.rest.bucketlist.goal.dto;

import javax.validation.constraints.NotNull;

public class GoalDTO {

	@NotNull
	private String description;
	@NotNull
	private boolean achieved;

	GoalDTO() {
	}
	
	private GoalDTO(final String description, final boolean achieved) {
		this.description = description;
		this.achieved = achieved;
	}

	public static final GoalDTO of(final String description) {
		return new GoalDTO(description, false);
	}
	
	public static final GoalDTO of(final String description, final boolean achieved) {
		return new GoalDTO(description, achieved);
	}
	
	public String getDescription() {
		return description;
	}
	
	public boolean isAchieved() {
		return achieved;
	}
	
	public GoalDTO achieve() {
		return new GoalDTO(this.description, true);
	}
}
