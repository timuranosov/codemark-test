package codemark.api;

import codemark.api.controller.UserController;
import codemark.api.db.entity.User;
import codemark.api.dto.RoleDTO;
import codemark.api.dto.UserDTO;
import codemark.api.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class UserControllerTest {
    @Autowired
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    private MockMvc mockMvc;

    private static final String CONTROLLER_URL = "/v1/users";
    private static final String TEST_LOGIN = "login";

    @Before
    public void setup() {
        UserController userController = new UserController(userService);

        mockMvc = MockMvcBuilders
                .standaloneSetup(userController)
                .build();
    }

    @Test
    public void whenSuccessfulUserInsertion_thenReturn200() throws Exception {
        UserDTO userDTO = new UserDTO();
        userDTO.setName(TEST_LOGIN);
        userDTO.setLogin(TEST_LOGIN);
        userDTO.setPassword("Q1w2e3");

        Set<RoleDTO> roles = new HashSet<>();
        RoleDTO roleDTO = new RoleDTO();
        roleDTO.setName("ADMIN");
        roles.add(roleDTO);

        userDTO.setRoles(roles);

        mockMvc.perform(MockMvcRequestBuilders
                .post(CONTROLLER_URL + "/add")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(userDTO))
        ).andExpect(status().isOk());

        List<User> users = userService.getAll();
        Assert.assertEquals(1, users.size());

        User user = users.get(0);
        Assert.assertEquals(TEST_LOGIN, user.getLogin());
    }

    @Test
    public void whenIncorrectPassword_thenReturn400() throws Exception {
        UserDTO userDTO = new UserDTO();
        userDTO.setName(TEST_LOGIN);
        userDTO.setLogin(TEST_LOGIN);
        userDTO.setPassword("123");

        Set<RoleDTO> roles = new HashSet<>();
        RoleDTO roleDTO = new RoleDTO();
        roleDTO.setName("ADMIN");
        roles.add(roleDTO);

        userDTO.setRoles(roles);

        mockMvc.perform(MockMvcRequestBuilders
                .post(CONTROLLER_URL + "/add")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(userDTO))
        ).andExpect(status().isBadRequest());
    }

    @Test
    public void whenNameIsEmpty_thenReturn400() throws Exception {
        UserDTO userDTO = new UserDTO();
        userDTO.setLogin(TEST_LOGIN);
        userDTO.setPassword("Q1w2e3");

        Set<RoleDTO> roles = new HashSet<>();
        RoleDTO roleDTO = new RoleDTO();
        roleDTO.setName("ADMIN");
        roles.add(roleDTO);

        userDTO.setRoles(roles);

        mockMvc.perform(MockMvcRequestBuilders
                .post(CONTROLLER_URL + "/add")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(userDTO))
        ).andExpect(status().isBadRequest());
    }

    @Test
    public void whenUserExists_thenSuccessfulDeletionAndReturn200() throws Exception {
        UserDTO userDTO = new UserDTO();
        userDTO.setName(TEST_LOGIN);
        userDTO.setLogin(TEST_LOGIN);
        userDTO.setPassword("Q1w2e3");

        Set<RoleDTO> roles = new HashSet<>();
        RoleDTO roleDTO = new RoleDTO();
        roleDTO.setName("ADMIN");
        roles.add(roleDTO);

        userDTO.setRoles(roles);

        userService.add(userDTO);

        List<User> users = userService.getAll();
        Assert.assertEquals(1, users.size());

        User user = users.get(0);

        mockMvc.perform(MockMvcRequestBuilders
                .delete(CONTROLLER_URL + "/delete/{id}", user.getId())
        ).andExpect(status().isOk());

        users = userService.getAll();
        Assert.assertEquals(0, users.size());
    }

    @Test
    public void whenUserExists_thenSuccessfulModificationAndReturn200() throws Exception {
        UserDTO userDTO = new UserDTO();
        userDTO.setName(TEST_LOGIN);
        userDTO.setLogin(TEST_LOGIN);
        userDTO.setPassword("Q1w2e3");

        Set<RoleDTO> roles = new HashSet<>();
        RoleDTO roleDTO = new RoleDTO();
        roleDTO.setName("ADMIN");
        roles.add(roleDTO);

        userDTO.setRoles(roles);

        userService.add(userDTO);

        List<User> users = userService.getAll();
        Assert.assertEquals(1, users.size());

        userDTO.setName("new_name");
        userDTO.setId(users.get(0).getId());

        mockMvc.perform(MockMvcRequestBuilders
                .put(CONTROLLER_URL + "/modify")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(userDTO))
        ).andExpect(status().isOk());

        users = userService.getAll();
        Assert.assertEquals(1, users.size());

        User user = users.get(0);
        Assert.assertEquals("new_name", user.getName());
    }

    @Test
    public void whenUserExistsAndPassedPasswordIsIncorrect_thenReturn400() throws Exception {
        UserDTO userDTO = new UserDTO();
        userDTO.setName(TEST_LOGIN);
        userDTO.setLogin(TEST_LOGIN);
        userDTO.setPassword("123");

        Set<RoleDTO> roles = new HashSet<>();
        RoleDTO roleDTO = new RoleDTO();
        roleDTO.setName("ADMIN");
        roles.add(roleDTO);

        userDTO.setRoles(roles);

        userService.add(userDTO);

        List<User> users = userService.getAll();
        Assert.assertEquals(1, users.size());

        userDTO.setName("new_name");
        userDTO.setId(users.get(0).getId());

        mockMvc.perform(MockMvcRequestBuilders
                .put(CONTROLLER_URL + "/modify")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(userDTO))
        ).andExpect(status().isBadRequest());
    }

    @Test
    public void whenUserExistsAndPassedNameIsEmpty_thenReturn400() throws Exception {
        UserDTO userDTO = new UserDTO();
        userDTO.setName(TEST_LOGIN);
        userDTO.setLogin(TEST_LOGIN);
        userDTO.setPassword("Q1w2e3");

        Set<RoleDTO> roles = new HashSet<>();
        RoleDTO roleDTO = new RoleDTO();
        roleDTO.setName("ADMIN");
        roles.add(roleDTO);

        userDTO.setRoles(roles);

        userService.add(userDTO);

        List<User> users = userService.getAll();
        Assert.assertEquals(1, users.size());

        userDTO.setName(null);

        mockMvc.perform(MockMvcRequestBuilders
                .put(CONTROLLER_URL + "/modify")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(userDTO))
        ).andExpect(status().isBadRequest());
    }

    @After
    public void tearDown() {
        userService.truncate();
    }
}
