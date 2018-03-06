package com.sidharth.demo.springcloud.core.Repo;

import com.sidharth.demo.springcloud.core.Model.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author sidharthdash ON 2/28/18
 */
public interface UserRepo extends Repository<User,Long>{
    User findOneByUsername(String username);

    @Modifying
    @Query(value = "insert into users (username,passwpord,enabled) VALUES (:username,:password, :enabled)", nativeQuery = true)
    @Transactional
    void  addUser(@Param("username") String username, @Param("password") String password,@Param("enabled") boolean enabled);

}
