package io.yamyamiya.telegram.bot.service.password;

import org.passay.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;

public class RandomPasswordGenerator {
    public static void main(String[] args) {
        RandomPasswordGenerator passwordGenerator = new RandomPasswordGenerator();
        System.out.println(passwordGenerator.generatePassword());
    }
    public Password generatePassword() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        PasswordGenerator passwordGenerator = new PasswordGenerator();
        List<CharacterRule> list = new ArrayList<>();
        list.add(new CharacterRule(EnglishCharacterData.UpperCase, 1));
        list.add(new CharacterRule(EnglishCharacterData.LowerCase, 1));
        list.add(new CharacterRule(EnglishCharacterData.Digit, 1));

        String rawPassword = passwordGenerator.generatePassword(12, list);
        String encodedPassword = encoder.encode(rawPassword);
        return new Password(rawPassword, encodedPassword);
    }
}
