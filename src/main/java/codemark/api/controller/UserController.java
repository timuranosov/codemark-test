package codemark.api.controller;

import codemark.api.dto.UserDTO;
import codemark.api.db.entity.User;
import codemark.api.service.UserService;
import codemark.api.utils.DTOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping("/v1/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

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
        return ResponseEntity.ok(returnSuccess());
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE, produces = "application/json")
    public ResponseEntity<Object> deleteUser(@PathVariable long id) {
        userService.delete(id);
        return ResponseEntity.ok(returnSuccess());
    }

    @RequestMapping(value = "/modify", method = RequestMethod.PUT, produces = "application/json")
    public ResponseEntity<Object> modifyUser(@Valid @RequestBody UserDTO userDTO) {
        userService.updateUser(DTOUtils.toEntity(userDTO));
        return ResponseEntity.ok(returnSuccess());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, Object> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("success", false);
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        response.put("errors", errors);
        return response;
    }

    private Map<String, Object> returnSuccess() {
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        return response;
    }
}
