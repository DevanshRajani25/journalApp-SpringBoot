// Controller (for handling incoming req.) --> Service (for bussiness logic) --> Repository (to interact with DB, extending MongoRepo)

package com.devansh.journal_app.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.devansh.journal_app.entity.JournalEntry;
import com.devansh.journal_app.entity.UserEntity;
import com.devansh.journal_app.repository.JouralEntryRepository;

@Component
public class JouralEntryService {
    
    @Autowired
    private JouralEntryRepository journalEntryRepository;

    @Autowired
    private UserService userService;

    // GET - Retrieve all data
    public List<JournalEntry> fetchEntries(){
        return journalEntryRepository.findAll();
    }

    // GET - Find by ID (Optional --> Return only if found)
    public Optional<JournalEntry> fetchJournalbyId(ObjectId fetchId){
        return journalEntryRepository.findById(fetchId);
    }

    // POST - Creating Entry
    @Transactional  // execute block of code as a single operation -> If all successful then commit, else rollback
    public void saveEntry(JournalEntry journalEntry, String userName){
        try {
            // fetching user (from username) -> saving jounralEntry into DB & saving that journalEntry into journalEntries field of User table
            UserEntity user = userService.findByUserName(userName);
            journalEntry.setDate(LocalDateTime.now());
            JournalEntry saved = journalEntryRepository.save(journalEntry);
            user.getJournalEntries().add(saved);
            userService.saveUser(user);
        } catch (Exception e) {
            System.out.println("Error: "+e);
        }
    }

    // PUT - Update Entry
    public void updateEntry(JournalEntry journalEntry){
        journalEntryRepository.save(journalEntry);
    }

    // DELETE - Delete entry by ID
    public boolean deletebyId(ObjectId deleteId, String userName){
        UserEntity user = userService.findByUserName(userName);
        if(user!=null){
            user.getJournalEntries().removeIf(x -> x.getId().equals(deleteId));
            userService.saveUser(user);
            journalEntryRepository.deleteById(deleteId);
            return true;
        }
        else{
            return false;
        }
    }

}
