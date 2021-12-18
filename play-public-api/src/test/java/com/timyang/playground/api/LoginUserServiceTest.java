package com.timyang.playground.api;

import com.timyang.playground.api.controller.dto.UserRegistrationRequest;
import com.timyang.playground.api.entitys.LoginUser;
import com.timyang.playground.api.service.LoginUserService;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class LoginUserServiceTest extends BasicSpringBootTest {

    @Autowired
    @Qualifier("loginUserService")
    LoginUserService loginUserService;

    @Test
    public void testNewAdmin() {
        final UserRegistrationRequest testUser = UserRegistrationRequest.builder()
                .username("admin-test")
                .password("123456").build();

        loginUserService.save(testUser);

        final LoginUser loginUser = loginUserService.findUserByUsername("admin-test");
        Assert.assertNotNull(loginUser);
        Assert.assertTrue(loginUserService.checkSecret("123456", loginUser.getPassword()));
    }
}
