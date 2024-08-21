package com.example.experienceproject.controller;

import com.example.experienceproject.model.Account;
import com.example.experienceproject.model.DynamicPrompt;
import com.example.experienceproject.model.Experience;
import com.example.experienceproject.model.StaticChecklistItem;
import com.example.experienceproject.service.AccountService;
import com.example.experienceproject.service.ExperienceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("api/accounts")
public class AccountController {

    @Autowired
    private ExperienceService experienceService;

    @Autowired
    private AccountService accountService;

    // Endpoimt to add multiple experiences to an account
    @PostMapping("/{accountId}/experiences")
    public ResponseEntity<Account> addExperiencesToAccount(@PathVariable String accountId, @RequestBody List<Experience> experiences) {
        Account updatedAccount = accountService.addExperiencesToAccount(accountId, experiences);
        return ResponseEntity.ok(updatedAccount);
    }

    // Endpoint to add an agent to an experience
    @PostMapping("/{accountId}/experiences/{experienceId}/agents")
    public ResponseEntity<Account> addAgentToExperience(
            @PathVariable String accountId,
            @PathVariable String experienceId,
            @RequestBody String agentId) {
        Account updatedAccount = experienceService.addAgentToExperience(accountId, experienceId, agentId);
        if (updatedAccount != null) {
            return ResponseEntity.ok(updatedAccount);
        }
        return ResponseEntity.notFound().build();
    }

    // Endpoint to remove an agent from an experience
    @DeleteMapping("/{accountId}/experiences/{experienceId}/agents/{agentId}")
    public ResponseEntity<Account> removeAgentFromExperience(
            @PathVariable String accountId,
            @PathVariable String experienceId,
            @PathVariable String agentId) {
        Account updatedAccount = experienceService.removeAgentFromExperience(accountId, experienceId, agentId);
        if (updatedAccount != null) {
            return ResponseEntity.ok(updatedAccount);
        }
        return ResponseEntity.notFound().build();
    }


    
    @GetMapping
    public List<Account> getAllAccounts() {
        return accountService.getAllAccounts();
    }

    @PostMapping
    public Account createAccount(@RequestBody Account account) {
        return accountService.createAccount(account);
    }

    // Endpoint to create an experience for an account
    @PostMapping("/{accountId}/experience")
    public ResponseEntity<Account> addExperienceToAccount(
            @PathVariable String accountId,
            @RequestBody Experience experience) {
        Account updatedAccount = accountService.addExperienceToAccount(accountId, experience);
        if (updatedAccount != null) {
            return ResponseEntity.ok(updatedAccount);
        }
        return ResponseEntity.notFound().build();
    }

    // Endpoint to delete an experience from an account
    @DeleteMapping("/{accountId}/experiences/{experienceId}")
    public ResponseEntity<Account> deleteExperienceFromAccount(
            @PathVariable String accountId,
            @PathVariable String experienceId) {
        Account updatedAccount = accountService.deleteExperienceFromAccount(accountId, experienceId);
        if (updatedAccount != null) {
            return ResponseEntity.ok(updatedAccount);
        }
        return ResponseEntity.notFound().build();
    }

    // Add a static checklist item
    @PostMapping("/{accountId}/experiences/{experienceId}/static-checklist-items")
    public ResponseEntity<Experience> addStaticChecklistItem(
            @PathVariable String accountId,
            @PathVariable String experienceId,
            @RequestBody StaticChecklistItem newItem) {
        Experience experience = experienceService.addStaticChecklistItem(accountId, experienceId, newItem);
        return ResponseEntity.ok(experience);
    }
    // Update a static checklist item
    @PutMapping("/{accountId}/experiences/{experienceId}/static-checklist-items/{checklistItemId}")
    public ResponseEntity<Experience> updateStaticChecklistItem(
            @PathVariable String accountId,
            @PathVariable String experienceId,
            @PathVariable String checklistItemId,
            @RequestBody StaticChecklistItem updatedItem) {
        Experience experience = experienceService.updateStaticChecklistItem(accountId, experienceId, checklistItemId, updatedItem);
        return ResponseEntity.ok(experience);
    }

    // Delete a static checklist item
    @DeleteMapping("/{accountId}/experiences/{experienceId}/static-checklist-items/{checklistItemId}")
    public ResponseEntity<Experience> deleteStaticChecklistItem(
            @PathVariable String accountId,
            @PathVariable String experienceId,
            @PathVariable String checklistItemId) {


        Experience updatedExperience = experienceService.deleteStaticChecklistItem(accountId, experienceId, checklistItemId);
        return ResponseEntity.ok(updatedExperience);

    }

    // Add a dynamic prompt
    @PostMapping("/{accountId}/experiences/{experienceId}/dynamic-prompts")
    public ResponseEntity<Experience> addDynamicPrompt(
            @PathVariable String accountId,
            @PathVariable String experienceId,
            @RequestBody DynamicPrompt newPrompt) {
        Experience updatedExperience = experienceService.addDynamicPrompt(accountId, experienceId, newPrompt);
        return ResponseEntity.ok(updatedExperience);
        }


    // Update a dynamic prompt
    @PutMapping("/{accountId}/experiences/{experienceId}/dynamic-prompts")
    public ResponseEntity<Experience> updateDynamicPrompt(
            @PathVariable String accountId,
            @PathVariable String experienceId,
            @RequestBody DynamicPrompt updatedPrompt) {


       Experience updatedExperience = experienceService.updateDynamicPrompt(accountId, experienceId, updatedPrompt);
       return ResponseEntity.ok(updatedExperience);

    }

    @DeleteMapping("/{accountId}/experiences/{experienceId}/dynamic-prompts/{promptId}")
    public ResponseEntity<Experience> deleteDynamicPrompt(
            @PathVariable String accountId,
            @PathVariable String experienceId,
            @PathVariable String promptId) {

        try {
            Experience updatedExperience = experienceService.deleteDynamicPrompt(accountId, experienceId, promptId);
            return ResponseEntity.ok(updatedExperience);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }



}