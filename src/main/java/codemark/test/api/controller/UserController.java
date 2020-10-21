package codemark.test.api.controller;

import codemark.test.api.dto.UserDTO;
import codemark.test.db.entity.User;
import codemark.test.service.UserService;
import codemark.test.utils.DTOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@RestController
@RequestMapping("/v1/users")
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Object> users() {
        List<User> users = userService.getAll();
        List<UserDTO> userDTOS = new ArrayList<>();
        users.forEach(user -> {
            user.setRoles(new HashSet<>());
            userDTOS.add(DTOUtils.toDTO(user));
        });
        return ResponseEntity.ok(userDTOS);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Object> getUser(@PathVariable long id) {
        User user = userService.getUserById(id);
        return ResponseEntity.ok(DTOUtils.toDTO(user));
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<Object> addUser(@Valid @RequestBody UserDTO userDTO) {
        userService.add(userDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE, produces = "application/json")
    public ResponseEntity<Object> deleteUser(@PathVariable long id) {
        userService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/modify", method = RequestMethod.PUT, produces = "application/json")
    public ResponseEntity<Object> deleteUser(@RequestBody UserDTO userDTO) {
        userService.updateUser(DTOUtils.toEntity(userDTO));
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
