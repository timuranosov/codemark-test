package codemark.test.api.controller;

import codemark.test.db.entity.User;
import codemark.test.db.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/v1/users")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @RequestMapping(value = "/", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Object> users() {
        Iterable<User> users = userRepository.findAll();
        return ResponseEntity.ok(users);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<Object> addUser(@Valid @RequestBody User user) {
        userRepository.save(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
