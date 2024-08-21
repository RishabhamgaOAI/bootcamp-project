package com.example.experienceproject.model;

import java.time.Instant;

public class NotificationMessage {
    private String type;
    private String accountId;
    private String experienceId;
    private String agentId;


    // Default constructor
    public NotificationMessage() {
    }



    // Constructor with all fields
    public NotificationMessage(String type, String accountId, String experienceId, String agentId) {
        this.type = type;
        this.accountId = accountId;
        this.experienceId = experienceId;
        this.agentId = agentId;
    }

    // Getters and setters
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getExperienceId() {
        return experienceId;
    }

    public void setExperienceId(String experienceId) {
        this.experienceId = experienceId;
    }

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }


    @Override
    public String toString() {
        return "NotificationMessage{" +
                "type='" + type + '\'' +
                ", accountId='" + accountId + '\'' +
                ", experienceId='" + experienceId + '\'' +
                ", agentId='" + agentId + '\'' +
                '}';
    }
}
