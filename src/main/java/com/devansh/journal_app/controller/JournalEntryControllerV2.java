package com.devansh.journal_app.controller;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devansh.journal_app.entity.JournalEntry;
import com.devansh.journal_app.entity.UserEntity;
import com.devansh.journal_app.services.JouralEntryService;
import com.devansh.journal_app.services.UserService;

@RestController
@RequestMapping("/journal")
public class JournalEntryControllerV2 {

    @Autowired
    private JouralEntryService journalEntryService;

    @Autowired
    private UserService userService;

    @GetMapping("{userName}")
    // <access-modifier> <return-datatype> <method-name>
    public ResponseEntity<?> getallJounralEntriesofUser(@PathVariable String userName){
        UserEntity user = userService.findByUserName(userName);
        List<JournalEntry> alldata = user.getJournalEntries();
        if(alldata != null && !alldata.isEmpty()){
            return new ResponseEntity<>(alldata, HttpStatus.OK);
        }
        return new ResponseEntity<>("No any entries found for this user!",HttpStatus.NOT_FOUND);
    }

    @PostMapping("{userName}")
    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry myEntry, @PathVariable String userName){
        try {
            journalEntryService.saveEntry(myEntry, userName);
            return new ResponseEntity<>(myEntry, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // If data found then return, else return null
    @GetMapping("/id/{myId}")
    public ResponseEntity<JournalEntry> getJournalbyId(@PathVariable ObjectId myId){

        // return journalEntryService.fetchJournalbyId(myId).orElse(null);
        Optional<JournalEntry> journalEntry = journalEntryService.fetchJournalbyId(myId);
        if(journalEntry.isPresent()){
            return new ResponseEntity<>(journalEntry.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("id/{userName}/{deleteId}")
    public ResponseEntity<?> deleteJournalbyId(@PathVariable ObjectId deleteId, @PathVariable String userName){
        boolean delete_success = journalEntryService.deletebyId(deleteId, userName);
        if(delete_success){
            return new ResponseEntity<>("Entry deleted Successfully!",HttpStatus.NO_CONTENT);
        }
        else{
            return new ResponseEntity<>("No Journal entry found for this user!", HttpStatus.NOT_FOUND);
        }
    }
    
    @PutMapping("id/{userName}/{updateId}")
    public ResponseEntity<?> updateJournalbyId(
        @PathVariable ObjectId updateId, 
        @RequestBody JournalEntry updatedEntry,
        @PathVariable String userName
    ){

        JournalEntry oldEntry = journalEntryService.fetchJournalbyId(updateId).orElse(null);

        // If updatedEntry already existing --> we have to update it
        if(oldEntry != null){
            oldEntry.setTitle(updatedEntry.getTitle() != null && !updatedEntry.getTitle().equals("") ? updatedEntry.getTitle() : oldEntry.getTitle());
            oldEntry.setContent(updatedEntry.getContent() != null && !updatedEntry.getContent().equals("") ? updatedEntry.getContent() : oldEntry.getContent());
            journalEntryService.updateEntry(oldEntry);
            return new ResponseEntity<>(updatedEntry, HttpStatus.OK);
        }
        return new ResponseEntity<>("No ID found to update for the user!",HttpStatus.NOT_FOUND);
    }
}
