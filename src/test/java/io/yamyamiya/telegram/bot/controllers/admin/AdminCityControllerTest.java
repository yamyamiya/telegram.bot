package io.yamyamiya.telegram.bot.controllers.admin;

import io.yamyamiya.telegram.bot.dto.Location;
import io.yamyamiya.telegram.bot.entity.City;
import io.yamyamiya.telegram.bot.entity.Role;
import io.yamyamiya.telegram.bot.entity.User;
import io.yamyamiya.telegram.bot.repository.RoleRepository;
import io.yamyamiya.telegram.bot.repository.UserRepository;
import io.yamyamiya.telegram.bot.service.CityService;
import io.yamyamiya.telegram.bot.service.password.Password;
import io.yamyamiya.telegram.bot.service.password.RandomPasswordGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class AdminCityControllerTest {

    @Autowired
    private CityService cityService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Autowired
    private RoleRepository roleRepository;

    private RandomPasswordGenerator randomPasswordGenerator;
    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void init() {
        randomPasswordGenerator = new RandomPasswordGenerator(encoder);
        userRepository.deleteAll();
    }

    @Test
    void shouldDenyAccessForNonAuthenticatedUsers() throws Exception {
        City city = cityService.add(new Location(1.0000, 2.000, "Tokyo"));
        mockMvc.perform(get("/admin/city/id/" + city.getId())).andExpect(status().isUnauthorized());
    }

    @Test
    void shouldDenyAccessToUserRole() throws Exception {
        Password password = randomPasswordGenerator.generatePassword();
        User user = userRepository.save(new User(0, "Test", password.getEncryptedPassword(), 0, new Date()));
        List<Role> roles = roleRepository.findAll();
        user.setRoles(new HashSet<>());
        for (Role role: roles) {
            if ("ROLE_USER".equals(role.getName())) {
                role.getUsers().add(user);
                user.getRoles().add(role);
            }
        }
        userRepository.save(user);

        City city = cityService.add(new Location(1.0000, 2.000, "Tokyo"));
        mockMvc.perform(get("/admin/city/id/" + city.getId()).with(httpBasic(String.valueOf(user.getChatId()),password.getRawPassword()))).andExpect(status().isForbidden());
    }

    @Test
    void shouldGrantAccessToAdminRole() throws Exception {
        Password password = randomPasswordGenerator.generatePassword();
        User user = userRepository.save(new User(0, "Test", password.getEncryptedPassword(), 0, new Date()));
        List<Role> roles = roleRepository.findAll();
        for (Role role: roles) {
            role.getUsers().add(user);
        }
        user.setRoles(Set.copyOf(roles));
        userRepository.save(user);

        City city = cityService.add(new Location(1.0000, 2.000, "Tokyo"));
        mockMvc.perform(get("/admin/city/id/" + city.getId()).with(httpBasic(String.valueOf(user.getChatId()),password.getRawPassword()))).andExpect(status().isOk());
    }
}