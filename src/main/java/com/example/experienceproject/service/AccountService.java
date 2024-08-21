package com.example.experienceproject.service;

import com.example.experienceproject.model.Account;
import com.example.experienceproject.model.Experience;
import com.example.experienceproject.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class AccountService {
   
    @Autowired
    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Autowired
    private MongoTemplate mongoTemplate;

    public void deleteAccount(String id) {
        accountRepository.deleteById(id);
    }


    @Cacheable(value = "accountsCache", key = "'allAccounts'")
    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    public Account createAccount(Account account) {
        // You can add additional validation or processing here if needed
        return accountRepository.save(account);
    }

    public Account addExperienceToAccount(String accountId, Experience experience) {
        Criteria criteria = Criteria.where("id").is(accountId);
        Update update = new Update().push("experiences", experience);
        return mongoTemplate.findAndModify(new Query(criteria), update, FindAndModifyOptions.options().returnNew(true), Account.class);
    }

    public Account addExperiencesToAccount(String accountId, List<Experience> experiences) {
        Criteria criteria = Criteria.where("id").is(accountId);
        Update update = new Update().push("experiences").each(experiences.toArray());
        return mongoTemplate.findAndModify(new Query(criteria), update, FindAndModifyOptions.options().returnNew(true), Account.class);
    }

    public Account deleteExperienceFromAccount(String accountId, String experienceId) {
        Criteria criteria = Criteria.where("id").is(accountId);
        Update update = new Update().pull("experiences", new Experience(experienceId));
        return mongoTemplate.findAndModify(new Query(criteria), update, FindAndModifyOptions.options().returnNew(true), Account.class);
    }

}



// 1. if the api throws the error ( database connection/ invalid value ) where will error with go?
// => error will be propogated up the call stack. error will be thrown to the caller of these methods. to make application not crash, we should handle the error
// 2.for agregate query how to write in java ( pipelines)
// => we can either use Spring Data repositories where we can define custom query method with the @Query annotation or we can use mongotemplate (for complex aggregation)
// 3.@service and @bean ( get context )
// => @Service is a specialization of @Component and is used to mark classes as service components in the Spring IoC container.
// => bean is an object that is created, managed, and used by the Spring IoC (Inversion of Control) container. 
// 4.retryWrites in mongodb connection uri ( functioning )
// => retryWrites=true is the default setting for MongoDB drivers. It allows the driver to automatically retry write operations that fail due to a network error or other transient error.
// 5.create multiple accounts/experience using thread pool
// 6.how to threads spawn and allocate when we start the service and calls the api ( multiple)
// => since I havn't created thread pool, application uses the thread pool provided by the web server.