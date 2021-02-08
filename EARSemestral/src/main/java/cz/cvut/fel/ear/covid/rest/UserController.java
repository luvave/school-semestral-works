package cz.cvut.fel.ear.covid.rest;

import cz.cvut.fel.ear.covid.model.User;
import cz.cvut.fel.ear.covid.security.model.AuthenticationToken;
import cz.cvut.fel.ear.covid.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/register",consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> register(@RequestBody User user) {
        userService.newAuthor(user);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }


    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_EDITOR')")
    @GetMapping(value = "/current", produces = MediaType.APPLICATION_JSON_VALUE)
    public User getCurrent(Principal principal) {
        final AuthenticationToken auth = (AuthenticationToken) principal;
        return auth.getPrincipal().getUser();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping(value = "/exist", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HashMap> userExist(User user) {
        HashMap ret = new HashMap<String, String>();
        if(userService.exists(user.getUsername())){
            ret.put("userExist","true");
        }
        else {
            ret.put("userExist","false");
        }
        return new ResponseEntity<>(ret, HttpStatus.OK);
    }
}
