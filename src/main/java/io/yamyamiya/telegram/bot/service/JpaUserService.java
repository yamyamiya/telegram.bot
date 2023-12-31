package io.yamyamiya.telegram.bot.service;

import io.yamyamiya.telegram.bot.entity.Role;
import io.yamyamiya.telegram.bot.entity.User;
import io.yamyamiya.telegram.bot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * JpaUserService class implements methods of {@link UserService}
 * contains userRepository {@link UserRepository}
 */
@Service
public class JpaUserService implements UserService, UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> getAll() {
        return new ArrayList<>(userRepository.findAll());
    }

    @Override
    public User getById(int id) {
        Optional<User> user = userRepository.findById(id);
        return user.orElse(null);
    }

    @Override
    public User add(User user) {

        User findResult = userRepository.findByChatId(user.getChatId());
        if(findResult!=null){
            return findResult;
        }
        User newUser = new User(0, user.getName());

        Set<Role> roles = new HashSet<>();
        roles.add(new Role(1, "ROLE_USER"));
        newUser.setRoles(roles);

        newUser.setPassword(user.getPassword());
        newUser.setChatId(user.getChatId());
        newUser.setAddedAt(user.getAddedAt());

        return userRepository.save(newUser);
    }

    @Override
    public void deleteById(int id) {
        userRepository.deleteById(id);

    }

    @Override
    public void deleteByName(String name) {
        userRepository.deleteByName(name);

    }

    @Override
    public int getCount() {
        return (int)userRepository.count();
    }

    @Override
    public User getByChatId(long chatId) {
        return userRepository.findByChatId(chatId);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user =userRepository.findByChatId(Long.parseLong(username));
        if(user==null){
            throw new UsernameNotFoundException("User not found");
        }
        return user;
    }
}
