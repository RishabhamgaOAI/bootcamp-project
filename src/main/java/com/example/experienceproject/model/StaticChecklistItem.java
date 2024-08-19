package com.example.experienceproject.model;

import java.io.Serial;
import java.io.Serializable;

public class StaticChecklistItem implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private String id;
    private String line;
    private String momentId;

    // Default constructor
    public StaticChecklistItem() {}

    // Parameterized constructor
    public StaticChecklistItem(String id, String line, String momentId) {
        this.id = id;
        this.line = line;
        this.momentId = momentId;
    }

    public StaticChecklistItem(String itemId) {
    }

    // Getters and setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }

    public String getMomentId() {
        return momentId;
    }

    public void setMomentId(String momentId) {
        this.momentId = momentId;
    }
}