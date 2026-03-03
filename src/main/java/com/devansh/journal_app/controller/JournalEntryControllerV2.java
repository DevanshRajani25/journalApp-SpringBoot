package com.devansh.journal_app.controller;
import java.time.LocalDateTime;
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
import com.devansh.journal_app.services.JouralEntryService;

@RestController
@RequestMapping("/journal")
public class JournalEntryControllerV2 {

    @Autowired
    private JouralEntryService journalEntryService;

    @GetMapping("/get-data")
    // <access-modifier> <return-datatype> <method-name>
    public ResponseEntity<?> getallEntries(){
        List<JournalEntry> alldata = journalEntryService.fetchEntries();
        if(alldata != null && !alldata.isEmpty()){
            return new ResponseEntity<>(alldata, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/post-data")
    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry myEntry){
        try {
            myEntry.setDate(LocalDateTime.now());
            journalEntryService.saveEntry(myEntry);
            return new ResponseEntity<>(myEntry, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // If data found, then return else return null
    @GetMapping("/id/{myId}")
    public ResponseEntity<JournalEntry> getJournalbyId(@PathVariable ObjectId myId){

        // return journalEntryService.fetchJournalbyId(myId).orElse(null);
        Optional<JournalEntry> journalEntry = journalEntryService.fetchJournalbyId(myId);
        if(journalEntry.isPresent()){
            return new ResponseEntity<>(journalEntry.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/id/{deleteId}")
    public ResponseEntity<?> deleteJournalbyId(@PathVariable ObjectId deleteId){
        journalEntryService.deletebyId(deleteId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    
    @PutMapping("/id/{updateId}")
    public ResponseEntity<?> updateJournalbyId(@PathVariable ObjectId updateId, @RequestBody JournalEntry updatedEntry){

        JournalEntry oldEntry = journalEntryService.fetchJournalbyId(updateId).orElse(null);

        // If updatedEntry already existing --> we have to update it
        if(oldEntry != null){
            oldEntry.setTitle(updatedEntry.getTitle() != null && !updatedEntry.getTitle().equals("") ? updatedEntry.getTitle() : oldEntry.getTitle());
            oldEntry.setContent(updatedEntry.getContent() != null && !updatedEntry.getContent().equals("") ? updatedEntry.getContent() : oldEntry.getContent());
            journalEntryService.saveEntry(oldEntry);
            return new ResponseEntity<>(updatedEntry, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
