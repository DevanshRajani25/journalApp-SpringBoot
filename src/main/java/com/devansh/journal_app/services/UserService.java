// Controller (for handling incoming req.) --> Service (for bussiness logic) --> Repository (to interact with DB, extending MongoRepo)

package com.devansh.journal_app.services;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.devansh.journal_app.entity.UserEntity;
import com.devansh.journal_app.repository.UserRepository;

@Component
public class UserService {
    
    @Autowired
    private UserRepository userRepository;

    public void saveUser(UserEntity user){
        userRepository.save(user);
    }

    public List<UserEntity> getAllUsers(){
        return userRepository.findAll();
    }

    public Optional<UserEntity> findById(ObjectId id){
        return userRepository.findById(id);
    }

    public void deleteById(ObjectId id){
        userRepository.deleteById(id);
    }

    public UserEntity findByUserName(String userName){
        return userRepository.findByUserName(userName);
    }

}
