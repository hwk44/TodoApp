package com.example.demo.service;

import com.example.demo.model.UserEntity;
import com.example.demo.pesristense.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public UserEntity create(final UserEntity userEntity){
        // 들어온 사용자 정보가 null 이면 throw
        if(userEntity == null || userEntity.getUsername() == null){
            throw new RuntimeException("Invalid arguments");
        }
        // 이미 있는 사용자면 throw
        final String username = userEntity.getUsername();
        if(userRepository.existsByUsername(username)){
            log.warn("Username already exists {}", username);
            throw new RuntimeException("Username already exists");
        }
        // save
        return userRepository.save(userEntity);
    }

    // 암호화 x
//    public UserEntity getByCredentials(final String username, final String password){
//        return userRepository.findByUsernameAndPassword(username,password);
//    }
    
    // 암호화
    public UserEntity getByCredentials(final String username, final String password,
                                       final PasswordEncoder encoder){


        final UserEntity originalUser = userRepository.findByUsername(username);

        // matches 메서드를 이용해 패스워드가 같은지 확인
        if (originalUser != null &&
                encoder.matches(password, originalUser.getPassword())) {
            return originalUser;
        }
        return null;
    }
}
