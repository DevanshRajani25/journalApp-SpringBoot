// This is the demo Controller class who will handle requests and store it in temp list (Non-Persistant)

package com.devansh.journal_app.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bson.types.ObjectId;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devansh.journal_app.entity.JournalEntry;

@RestController
@RequestMapping("/_journal")
public class JournalEntryController {

    // local table (instead of database)
    private Map<ObjectId, JournalEntry> journalEntries = new HashMap<>();

    @GetMapping("/get-data")
    // <access-modifier> <return-datatype> <method-name>
    public List<JournalEntry> getData(){
        return new ArrayList<>(journalEntries.values());
    }

    @PostMapping("/post-data")
    public boolean postData(@RequestBody JournalEntry myEntry){
        journalEntries.put(myEntry.getId(), myEntry);
        return true;
    }

    @GetMapping("/id/{myId}")
    public JournalEntry getJournalbyId(@PathVariable ObjectId myId){
        return journalEntries.get(myId);
    }

    @DeleteMapping("/id/{deleteId}")
    public JournalEntry deleteJournalbyId(@PathVariable ObjectId deleteId){
        return journalEntries.remove(deleteId);
    }
    
    @PutMapping("/id/{updateId}")
    public JournalEntry updateJournalbyId(@PathVariable ObjectId updateId, @RequestBody JournalEntry updateEntry){
        return journalEntries.put(updateId, updateEntry);
    }
}

// I have changed datatype of id from Long --> String, String --> ObjectId