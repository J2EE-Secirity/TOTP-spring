package au.com.totp.example.security.config;

import au.com.totp.example.security.model.SBTUser;
import au.com.totp.example.security.model.SBTUserRole;
import au.com.totp.example.security.respository.RoleRepository;
import au.com.totp.example.security.respository.UserRepository;
import org.apache.commons.codec.binary.Base32;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.util.*;

@Component
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    private boolean alreadySetup = false;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // API

    @Override
    @Transactional
    public void onApplicationEvent(final ContextRefreshedEvent event) {
        if (alreadySetup) {
            return;
        }

        // == create initial user
        SBTUser user = createUser("user", "password");
        createRole("ROLE_USER", user);

        alreadySetup = true;
    }

    @Transactional
    private SBTUserRole createRole(String role, SBTUser user) {
        SBTUserRole userRole = new SBTUserRole();
        userRole.setRole(role);
        userRole.setUser(user);

        userRole = roleRepository.save(userRole);
        return userRole;
    }

    @Transactional
    private SBTUser createUser(String username, String password) {
        SBTUser user = new SBTUser();
        user.setUsername(username);
        user.setEnabled(true);
        user.setPassword(passwordEncoder.encode(password));
        user.setSecret(generateSecret());

        user = userRepository.save(user);
        return user;
    }

    private String generateSecret() {
        byte [] buffer = new byte[10];
        new SecureRandom().nextBytes(buffer);
        return new String(new Base32().encode(buffer));
    }
}