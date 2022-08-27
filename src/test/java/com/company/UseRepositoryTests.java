package com.company;

import com.company.user.User;
import com.company.user.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class UseRepositoryTests {
    @Autowired private UserRepository repo;

    @Test
    public void testAddNew(){
        User user = new User();

        user.setEmail("fabriola@gmail.com");
        user.setFirstName("davidson");
        user.setLastName("nicole");
        user.setPassword("azerty");
        User savedUser = repo.save(user);
        Assertions.assertThat(savedUser).isNotNull();
        Assertions.assertThat(savedUser.getId()).isGreaterThan(0);
    }

    @Test
    public void testListAll(){
        Iterable<User> users = repo.findAll();
        Assertions.assertThat(users).hasSizeGreaterThan(0);

        for(User user : users){
            System.out.println(user);
        }
    }

    @Test
    public void testUpdate(){
       Integer userId = 1;
       Optional<User> optionalUser = repo.findById(userId);
       User user =optionalUser.get();
       user.setPassword("qwerty");
       repo.save(user);

       User updateUser = repo.findById(userId).get();
       Assertions.assertThat(updateUser.getPassword()).isEqualTo("qwerty");

    }

    @Test
    public void testDelete(){
        Integer userId = 2;
        repo.deleteById(userId);
        Optional<User> optionalUser = repo.findById(userId);
        Assertions.assertThat(optionalUser).isNotPresent();

    }
}
