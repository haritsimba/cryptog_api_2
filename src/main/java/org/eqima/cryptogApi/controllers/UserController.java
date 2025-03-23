package org.eqima.cryptogApi.controllers;

import org.eqima.cryptogApi.dto.AssociatedTokenAccountDto;
import org.eqima.cryptogApi.services.UserService;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "user")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("")
    public String test(){
        return "hello";
    }

    @GetMapping("/{username}")
    public ResponseEntity<?> getUserByUsername(@PathVariable(name = "username") String username){
        UserRepresentation user = userService.getUserByUsername(username);
        if(user == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(user);
    }

    @PutMapping("/{username}")
    public ResponseEntity<?> updateUserATA(@PathVariable(name = "username") String username, @RequestBody List<AssociatedTokenAccountDto> ATAs){
        return ResponseEntity.ok().body(userService.addUserATA(username,ATAs));
    }
}
