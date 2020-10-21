package codemark.test.service;

import codemark.test.api.dto.UserDTO;
import codemark.test.db.entity.User;
import codemark.test.db.repository.UserRepository;
import codemark.test.exception.UserNotFoundException;
import codemark.test.utils.DTOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public void add(UserDTO userDTO) {
        userRepository.save(DTOUtils.toEntity(userDTO));
    }

    public void delete(long id) {
        userRepository.deleteById(id);
    }

    public List<User> getAll() {
        return (List<User>) userRepository.findAll();
    }

    public User getUserById(long id) {
        Optional<User> user = userRepository.findById(id);
        return user.orElseThrow(() -> new UserNotFoundException("User with id " + id + " is not found"));
    }

    public void updateUser(User user) {
        Optional<User> optional = userRepository.findById(user.getId());
        if (optional.isPresent()) {
            User entity = optional.get();
            entity.setLogin(user.getLogin());
            entity.setPassword(user.getPassword());
            entity.setRoles(user.getRoles());
            userRepository.save(entity);
        }
    }
}
