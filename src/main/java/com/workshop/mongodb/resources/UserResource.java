package com.workshop.mongodb.resources;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.workshop.mongodb.domains.User;
import com.workshop.mongodb.dto.UserDTO;
import com.workshop.mongodb.services.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping(value = "/users")
public class UserResource {

    private final UserService userService;

    UserResource(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> findAll() {
        List<User> list = userService.findAll();
        List<UserDTO> listDto = list.stream().map(x -> new UserDTO(x)).toList();

        return ResponseEntity.ok().body(listDto);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<UserDTO> findById(@PathVariable String id) {

        User obj = userService.findById(id);
        return ResponseEntity.ok().body(new UserDTO(obj));
    }

    @PostMapping
    public ResponseEntity<UserDTO> insert(@RequestBody UserDTO objDto) {
        User obj = new UserDTO().fromDTO(objDto);
        obj = userService.insert(obj);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();

        return ResponseEntity.created(uri).body(new UserDTO(obj));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
