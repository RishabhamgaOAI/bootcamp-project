package com.example.experienceproject.model;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

public class Experience implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private String id;
    private String name;
    private List<StaticChecklistItem> staticChecklistItems;
    private List<DynamicPrompt> dynamicPrompts;
    private List<String> callSummaryFields;
    private List<String> agentIds;

    // No-argument constructor
    public Experience() {
    }

    // Full constructor
    public Experience(String id, String name, List<StaticChecklistItem> staticChecklistItems, List<DynamicPrompt> dynamicPrompts, List<String> callSummaryFields, List<String> agentIds) {
        this.id = id;
        this.name = name;
        this.staticChecklistItems = staticChecklistItems;
        this.dynamicPrompts = dynamicPrompts;
        this.callSummaryFields = callSummaryFields;
        this.agentIds = agentIds;
    }

    // Constructor with only ID
    public Experience(String id) {
        this.id = id;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<StaticChecklistItem> getStaticChecklistItems() {
        return staticChecklistItems;
    }

    public void setStaticChecklistItems(List<StaticChecklistItem> staticChecklistItems) {
        this.staticChecklistItems = staticChecklistItems;
    }

    public List<DynamicPrompt> getDynamicPrompts() {
        return dynamicPrompts;
    }

    public void setDynamicPrompts(List<DynamicPrompt> dynamicPrompts) {
        this.dynamicPrompts = dynamicPrompts;
    }

    public List<String> getCallSummaryFields() {
        return callSummaryFields;
    }

    public void setCallSummaryFields(List<String> callSummaryFields) {
        this.callSummaryFields = callSummaryFields;
    }

    public List<String> getAgentIds() {
        return agentIds;
    }

    public void setAgentIds(List<String> agentIds) {
        this.agentIds = agentIds;
    }
}