package com.example.experienceproject.controller;

import com.example.experienceproject.model.Experience;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class ExperienceWebSocketController {

    @MessageMapping("/updateExperience")
    @SendTo("/topic/experienceUpdates")
    public Experience broadcastExperienceUpdate(Experience experience) {
        // This method broadcasts the experience update to all subscribers
        return experience;
    }
}