package ua.lviv.lgs.security;

import java.util.Collections;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import ua.lviv.lgs.UserRegisterRequest;
import ua.lviv.lgs.domain.User;
import ua.lviv.lgs.domain.UserRole;
import ua.lviv.lgs.repository.UserRepository;

@Service
public class UserService {
  private static final Set<UserRole> DEFAULT_USER_ROLES = Collections.singleton(UserRole.ROLE_USER);

  private UserRepository userRepository;
  private PasswordEncoder passwordEncoder;

  @Autowired
  public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
  }

  public void save(UserRegisterRequest userRegisterRequest) {
    User user = new User();
    user.setEmail(userRegisterRequest.getEmail());

    // default
    user.setRoles(DEFAULT_USER_ROLES);
    user.setUsername(userRegisterRequest.getUsername());

    String encodePassword = passwordEncoder.encode(userRegisterRequest.getPassword());
    user.setPassword(encodePassword);

    userRepository.save(user);
  }
}
