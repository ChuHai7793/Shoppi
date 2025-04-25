package com.shoppi.admin.user;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import com.shoppi.common.entity.Role;
import com.shoppi.common.entity.User;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class UserRepositoryTests {
	
	@Autowired
	private UserRepository repo;
	
	@Autowired
	private TestEntityManager entityManager;
	
	@Test
	public void testCreateNewUserWithOneRole() {
		Role roleAdmin = entityManager.find(Role.class, 1);
		User userTest = new User("tuan@gmail.com","12345","Tuan","Le");
		userTest.addRole(roleAdmin);
		
		User savedUser = repo.save(userTest);
		assertThat(savedUser.getId()).isGreaterThan(0);
		
	}
	
	@Test
	public void testCreateNewUserWithTwoRoles() {
		
		User userTuan = new User("hai@gmail.com","12345","Hai","Chu");
		Role roleEditor = new Role(4);
		Role roleAssistant = new Role(6);
	
		userTuan.addRole(roleEditor);
		userTuan.addRole(roleAssistant);
		
		User savedUser = repo.save(userTuan);
		assertThat(savedUser.getId()).isGreaterThan(0);
	}
	
	
	@Test
	public void testListAllUsers() {
		Iterable<User> listUsers = repo.findAll();
		listUsers.forEach(user -> System.out.println(user));
	}
	
	@Test
	public void testGetUserById() {
		User userNam = repo.findById(1).get();
		System.out.println(userNam);
		assertThat(userNam).isNotNull();
	}
	
	
	@Test
	public void testUpdateUserDetails() {
		User userNam = repo.findById(1).get();
		
		userNam.setEnabled(true);
		userNam.setEmail("newNam@gmail.com");
		
		repo.save(userNam);
	}
	
	
	@Test
	public void testUpdateUserRoles() {
		User userTuan = repo.findById(2).get();
		
		Role roleEditor = new Role(4);
		Role roleShipper = new Role(5);

		userTuan.getRoles().remove(roleEditor);
		userTuan.addRole(roleShipper);
		
		repo.save(userTuan);
	}
	
	
	@Test
	public void testDeleteUser() {
		repo.deleteById(1);
	}

}
