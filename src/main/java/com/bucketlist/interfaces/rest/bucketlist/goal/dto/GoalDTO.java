package com.bucketlist.interfaces.rest.bucketlist.goal.dto;

import javax.validation.constraints.NotNull;

public class GoalDTO {

	@NotNull
	private String description;

	GoalDTO() {
	}
	
	private GoalDTO(final String description) {
		this.description = description;
	}
	
	public static final GoalDTO of(final String description) {
		return new GoalDTO(description);
	}
	
	public String getDescription() {
		return description;
	}
}
