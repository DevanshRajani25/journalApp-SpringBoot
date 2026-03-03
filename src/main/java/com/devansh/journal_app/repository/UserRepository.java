package com.devansh.journal_app.repository;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import com.devansh.journal_app.entity.UserEntity;

// we are just extending MongoRepository in this file so that we can import this file in Service and can easily interact with MongoDB (No any code)
public interface UserRepository extends MongoRepository<UserEntity, ObjectId> {  
    UserEntity findByUserName(String userName);
}
