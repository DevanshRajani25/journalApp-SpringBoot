package com.devansh.journal_app.repository;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import com.devansh.journal_app.entity.JournalEntry;

// we are just extending MongoRepository in this file so that we can import this file in Service and can easily interact with MongoDB (No any code)
public interface JouralEntryRepository extends MongoRepository<JournalEntry, ObjectId> {  
    // Primary key of JournalEntry is String so String
}
