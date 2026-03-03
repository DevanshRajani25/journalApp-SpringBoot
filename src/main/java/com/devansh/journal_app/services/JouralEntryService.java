// Controller (for handling incoming req.) --> Service (for bussiness logic) --> Repository (to interact with DB, extending MongoRepo)

package com.devansh.journal_app.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
    // public void updatebyId(ObjectId updateId){
    //     // return journalEntryRepository.
    // }

    // DELETE - Delete entry by ID
    public void deletebyId(ObjectId deleteId){
        journalEntryRepository.deleteById(deleteId);
    }

}
