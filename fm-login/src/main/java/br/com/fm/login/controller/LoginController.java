package br.com.fm.login.controller;


import br.com.fm.login.dto.UserRequest;
import br.com.fm.mongodb.entity.UserEntity;
import br.com.fm.mongodb.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/auth")
public class LoginController {


    @Autowired
    private UserRepository userRepository;


    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody @Valid UserRequest request) {

        return ResponseEntity.ok().body("Endpoint test");

    }


    @PostMapping("/create")
    public ResponseEntity<UserEntity> create(@RequestBody @Valid UserRequest request) {


        UserEntity userEntity = new UserEntity();

        userEntity.setEmail(request.getEmail());
        userEntity.setName(request.getName());
        userEntity.setPassword(request.getPassword());

        userRepository.save(userEntity);


        return ResponseEntity.ok().body(userEntity);

    }

    public static void main(String[] args) {
        System.out.println(new BCryptPasswordEncoder().encode("12345678"));

    }


}