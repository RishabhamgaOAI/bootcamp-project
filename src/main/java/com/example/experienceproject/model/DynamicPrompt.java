package com.example.experienceproject.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serial;
import java.io.Serializable;

@Document(collection = "dynamicPrompts")
public class DynamicPrompt implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    private String id; // MongoDB ID field
    private String promptId;
    private String promptContent;

    // Default constructor
    public DynamicPrompt() {}

    // Parameterized constructor
    public DynamicPrompt(String id, String promptId, String promptContent) {
        this.id = id;
        this.promptId = promptId;
        this.promptContent = promptContent;
    }

    public DynamicPrompt(String promptId) {
    }

    // Getters and setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPromptId() {
        return promptId;
    }

    public void setPromptId(String promptId) {
        this.promptId = promptId;
    }

    public String getPromptContent() {
        return promptContent;
    }

    public void setPromptContent(String promptContent) {
        this.promptContent = promptContent;
    }
}