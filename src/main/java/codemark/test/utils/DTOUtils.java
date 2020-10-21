package codemark.test.utils;

import codemark.test.api.dto.RoleDTO;
import codemark.test.api.dto.UserDTO;
import codemark.test.db.entity.Role;
import codemark.test.db.entity.User;

import java.util.Set;
import java.util.stream.Collectors;

public class DTOUtils {
    public static UserDTO toDTO(User user) {
        UserDTO userDTO = new UserDTO();

        userDTO.setId(user.getId());
        userDTO.setName(user.getName());
        userDTO.setLogin(user.getLogin());
        userDTO.setPassword(user.getPassword());
        userDTO.setRoles(toDTO(user.getRoles()));

        return userDTO;
    }

    public static User toEntity(UserDTO userDTO) {
        User entity = new User();

        entity.setId(userDTO.getId());
        entity.setName(userDTO.getName());
        entity.setLogin(userDTO.getLogin());
        entity.setPassword(userDTO.getPassword());
        entity.setRoles(toEntity(userDTO.getRoles()));

        return entity;
    }

    public static Set<RoleDTO> toDTO(Set<Role> roles) {
        return roles.stream().map(DTOUtils::toDTO).collect(Collectors.toSet());
    }

    public static RoleDTO toDTO(Role role) {
        RoleDTO roleDTO = new RoleDTO();

        roleDTO.setId(role.getId());
        roleDTO.setName(role.getName());

        return roleDTO;
    }

    public static Set<Role> toEntity(Set<RoleDTO> roleDTOS) {
        return roleDTOS.stream().map(DTOUtils::toEntity).collect(Collectors.toSet());
    }

    public static Role toEntity(RoleDTO roleDTO) {
        Role role = new Role();

        role.setName(roleDTO.getName());

        return role;
    }
}
