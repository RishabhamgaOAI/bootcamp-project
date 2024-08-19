package com.example.experienceproject.service;

import com.example.experienceproject.model.Account;
import com.example.experienceproject.model.DynamicPrompt;
import com.example.experienceproject.model.StaticChecklistItem;
import com.example.experienceproject.model.Experience;
import com.example.experienceproject.repository.AccountRepository;
import com.example.experienceproject.repository.ExperienceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import java.util.Iterator;


import java.util.ArrayList;
import java.util.List;

import static org.springframework.data.mongodb.core.query.Query.query;

@Service
public class ExperienceService {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private AccountRepository accountRepository;


    @Autowired
    private ExperienceRepository experienceRepository;

    private Account getAccountById(String accountId) {
        return accountRepository.findById(accountId).orElse(null);
    }

    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    public ExperienceService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }
    private Experience findExperienceById(Account account, String experienceId) {
        return account.getExperiences().stream()
                .filter(exp -> exp.getId().equals(experienceId))
                .findFirst()
                .orElse(null);
    }


    public Experience addStaticChecklistItem(String accountId, String experienceId, StaticChecklistItem newItem) {
        // Find the account and experience by IDs
        Account account = getAccountById(accountId);
        if (account == null) {
            throw new RuntimeException("Account not found");
        }

        Experience experience = findExperienceById(account, experienceId);
        if (experience == null) {
            throw new RuntimeException("Experience not found");
        }

        // Add the new static checklist item
        if (experience.getStaticChecklistItems() == null) {
            experience.setStaticChecklistItems(new ArrayList<>());
        }
        experience.getStaticChecklistItems().add(newItem);

        // Update the experience in the account
        Update update = new Update().set("experiences.$", experience);
        Criteria criteria = Criteria.where("id").is(accountId)
                .and("experiences.id").is(experienceId);
        mongoTemplate.findAndModify(
                query(criteria),
                update,
                Account.class
        );

        return experience;
    }


    // Update a static checklist item
    public Experience updateStaticChecklistItem(String accountId, String experienceId, String checklistItemId, StaticChecklistItem updatedItem) {
        // Find the account and experience by IDs
        Account account = getAccountById(accountId);
        if (account == null) {
            throw new RuntimeException("Account not found");
        }

        Experience experience = findExperienceById(account, experienceId);
        if (experience == null) {
            throw new RuntimeException("Experience not found");
        }

        // Update the specific static checklist item
        List<StaticChecklistItem> checklistItems = experience.getStaticChecklistItems();
        if (checklistItems != null) {
            for (int i = 0; i < checklistItems.size(); i++) {
                StaticChecklistItem item = checklistItems.get(i);
                if (item.getId().equals(checklistItemId)) {
                    checklistItems.set(i, updatedItem);
                    break;
                }
            }
        } else {
            throw new RuntimeException("Checklist items not found");
        }

        // Update the experience in the account
        Update update = new Update().set("experiences.$", experience);
        Criteria criteria = Criteria.where("id").is(accountId)
                .and("experiences.id").is(experienceId);
        mongoTemplate.findAndModify(
                new Query(criteria),
                update,
                FindAndModifyOptions.options().returnNew(true),
                Account.class
        );

        return experience;
    }

    // Delete a static checklist item
    public Experience deleteStaticChecklistItem(String accountId, String experienceId, String checklistItemId) {
        // Find the account by ID
        Account account = getAccountById(accountId);
        if (account == null) {
            throw new RuntimeException("Account not found");
        }

        // Find the experience by ID
        Experience experience = findExperienceById(account, experienceId);
        if (experience == null) {
            throw new RuntimeException("Experience not found");
        }

        // Remove the static checklist item
        List<StaticChecklistItem> checklistItems = experience.getStaticChecklistItems();
        if (checklistItems != null) {
            Iterator<StaticChecklistItem> iterator = checklistItems.iterator();
            while (iterator.hasNext()) {
                StaticChecklistItem item = iterator.next();
                if (item.getId().equals(checklistItemId)) {
                    iterator.remove(); // Remove the item from the list
                    break;
                }
            }
        } else {
            throw new RuntimeException("Checklist items not found");
        }

        // Update the experience in the account
        Update update = new Update().set("experiences.$", experience);
        Criteria criteria = Criteria.where("id").is(accountId)
                .and("experiences.id").is(experienceId);
        mongoTemplate.findAndModify(
                new Query(criteria),
                update,
                FindAndModifyOptions.options().returnNew(true),
                Account.class
        );

        return experience;
    }


    // Add a new DynamicPrompt to an Experience
    public Experience addDynamicPrompt(String accountId, String experienceId, DynamicPrompt newPrompt) {
        // Find the account and experience by IDs
        Account account = getAccountById(accountId);
        if (account == null) {
            throw new RuntimeException("Account not found");
        }

        Experience experience = findExperienceById(account, experienceId);
        if (experience == null) {
            throw new RuntimeException("Experience not found");
        }

        // Add the new dynamic prompt
        if (experience.getDynamicPrompts() == null) {
            experience.setDynamicPrompts(new ArrayList<>());
        }
        experience.getDynamicPrompts().add(newPrompt);

        // Update the experience in the account
        Update update = new Update().set("experiences.$", experience);
        Criteria criteria = Criteria.where("id").is(accountId)
                .and("experiences.id").is(experienceId);
        mongoTemplate.findAndModify(
                new Query(criteria),
                update,
                FindAndModifyOptions.options().returnNew(true),
                Account.class
        );


        return experience;
    }

    // Update an existing DynamicPrompt in an Experience
    public Experience updateDynamicPrompt(String accountId, String experienceId, DynamicPrompt updatedPrompt) {
        // Find the account and experience by IDs
        Account account = getAccountById(accountId);
        if (account == null) {
            throw new RuntimeException("Account not found");
        }

        Experience experience = findExperienceById(account, experienceId);
        if (experience == null) {
            throw new RuntimeException("Experience not found");
        }

        // Update the dynamic prompt
        List<DynamicPrompt> prompts = experience.getDynamicPrompts();
        if (prompts != null) {
            for (int i = 0; i < prompts.size(); i++) {
                if (prompts.get(i).getId().equals(updatedPrompt.getId())) {
                    prompts.set(i, updatedPrompt);
                    break;
                }
            }
        }

        // Update the experience in the account
        Update update = new Update().set("experiences.$", experience);
        Criteria criteria = Criteria.where("id").is(accountId)
                .and("experiences.id").is(experienceId);
        mongoTemplate.findAndModify(
                new Query(criteria),
                update,
                FindAndModifyOptions.options().returnNew(true),
                Account.class
        );


        return experience;
    }

    // Delete a DynamicPrompt from an Experience
    public Experience deleteDynamicPrompt(String accountId, String experienceId, String promptId) {
        // Find the account and experience by IDs
        Account account = getAccountById(accountId);
        if (account == null) {
            throw new RuntimeException("Account not found");
        }

        Experience experience = findExperienceById(account, experienceId);
        if (experience == null) {
            throw new RuntimeException("Experience not found");
        }

        // Remove the dynamic prompt
        List<DynamicPrompt> prompts = experience.getDynamicPrompts();
        if (prompts != null) {
            Iterator<DynamicPrompt> iterator = prompts.iterator();
            while (iterator.hasNext()) {
                if (iterator.next().getId().equals(promptId)) {
                    iterator.remove();
                    break;
                }
            }
        } else {
            throw new RuntimeException("Dynamic prompts not found");
        }

        // Update the experience in the account
        Update update = new Update().set("experiences.$", experience);
        Criteria criteria = Criteria.where("id").is(accountId)
                .and("experiences.id").is(experienceId);
        mongoTemplate.findAndModify(
                new Query(criteria),
                update,
                FindAndModifyOptions.options().returnNew(true),
                Account.class
        );


        return experience;
    }



    // Add agent to an experience
    public Account addAgentToExperience(String accountId, String experienceId, String agentId) {
        Update update = new Update().addToSet("experiences.$.agentIds", agentId);
        Criteria criteria = Criteria.where("id").is(accountId)
                .and("experiences._id").is(experienceId);
        return mongoTemplate.findAndModify(
                new Query(criteria),
                update,
                FindAndModifyOptions.options().returnNew(true),
                Account.class
        );
    }

    // Remove agent from an experience
    public Account removeAgentFromExperience(String accountId, String experienceId, String agentId) {
        Update update = new Update().pull("experiences.$.agentIds", agentId);
        Criteria criteria = Criteria.where("id").is(accountId)
                .and("experiences._id").is(experienceId);
        return mongoTemplate.findAndModify(
                new Query(criteria),
                update,
                FindAndModifyOptions.options().returnNew(true),
                Account.class
        );
    }
}