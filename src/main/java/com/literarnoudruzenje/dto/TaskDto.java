package com.literarnoudruzenje.dto;

public class TaskDto {
	
	String taskId;
	String name;
	String assignee;
	String dateTime;
	
	public TaskDto() {
		super();
	}
	
	public TaskDto(String taskId, String name, String assignee, String dateTime) {
		super();
		this.taskId = taskId;
		this.name = name;
		this.assignee = assignee;
		this.dateTime = dateTime;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAssignee() {
		return assignee;
	}

	public void setAssignee(String assignee) {
		this.assignee = assignee;
	}

	public String getDateTime() {
		return dateTime;
	}

	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}
}
