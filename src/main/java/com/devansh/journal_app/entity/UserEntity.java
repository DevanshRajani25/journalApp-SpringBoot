package com.devansh.journal_app.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.mongodb.lang.NonNull;

import lombok.Data;

@Data
@Document(collection = "users")
public class UserEntity {

    @Id
    private ObjectId id;

    // Indexing on username -> username must be unique
    @Indexed(unique = true)
    @NonNull
    private String userName;

    @NonNull
    private String password;

    @DBRef // Making relation between User & journal_entries
    // journalEntries will store objectIDs from User
    List<JournalEntry> journalEntries = new ArrayList<>();

    private LocalDateTime updated_time;
}
