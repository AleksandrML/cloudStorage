package com.example.cloudStorage;

import com.example.cloudStorage.models.ProjectUser;
import com.example.cloudStorage.models.UserCreateRequest;
import com.example.cloudStorage.repositories.UserRepository;
import com.example.cloudStorage.services.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;

@SpringBootTest(classes = TestConfig.class)
class UnitTests {

	@Test
	void userFindTest() {

		// given
		String userName = "some_user";
		UserRepository userRepository = Mockito.mock(UserRepository.class);
		Mockito.when(userRepository.findByUsername(userName)).thenReturn(Optional.empty());

		// when
		UserService userService = new UserService(userRepository, new BCryptPasswordEncoder());

		// then
		Assertions.assertThrows(EntityNotFoundException.class, () -> userService.readUserByUsername(userName));
	}

	@Test
	void userCreatTest() {

		//given
		UserCreateRequest userCreateRequest = new UserCreateRequest();
		userCreateRequest.setUsername("user");
		userCreateRequest.setPassword("password");
		UserRepository userRepository = Mockito.mock(UserRepository.class);
		Mockito.when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(new ProjectUser()));

		// when
		UserService userService = new UserService(userRepository, new BCryptPasswordEncoder());

		// then
		RuntimeException exception = Assertions.assertThrows(RuntimeException.class,
				() -> userService.createUser(userCreateRequest));
		Assertions.assertEquals("User already registered. Please use different username.", exception.getMessage());

	}

}
