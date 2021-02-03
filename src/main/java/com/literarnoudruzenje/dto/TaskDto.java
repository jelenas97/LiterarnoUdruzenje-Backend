package com.literarnoudruzenje.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;
import java.util.Map;

public class TaskDto {
	
	String taskId;
	String name;
	String assignee;
	String dateTime;
	HashMap<String,String> variables;
	
	public TaskDto() {
		super();
	}
	
	public TaskDto(String taskId, String name, String assignee, String dateTime, HashMap<String,String> variables ) {
		super();
		this.taskId = taskId;
		this.name = name;
		this.assignee = assignee;
		this.dateTime = dateTime;
		this.variables=variables;
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

	public HashMap<String, String > getVariables() {
		return variables;
	}


	public void setVariables(HashMap<String, String> variables) {
		this.variables = variables;
	}
}
