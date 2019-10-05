package com.oxstreet.services.impl;

import com.google.common.collect.Sets;
import com.oxstreet.services.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Set;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AWSUserServiceImplTest {

    @Autowired
    private UserService userService;

    @Test
    public void createAppClient() {
        Set<String> allowedOAuthScopes = Sets.newHashSet("oxstreet-dev-oauth/users:put"
                , "oxstreet-dev-oauth/products:put", "oxstreet-dev-oauth/products:write");
        Set<String> identityProviders = Sets.newHashSet("COGNITO", "Google");
        String appClientName = "oxstreet-dev-system-user";
        userService.createAppClient(appClientName, allowedOAuthScopes, identityProviders);
    }

    @Test
    public void getAppClients() {
        userService.listAppClients();
    }

    @Test
    public void getUsers() {
        userService.listUsers();
    }

    @Test
    public void removeAppClient() {
        userService.removeAppClient("6hspftb177bljdkukik4v897r3");
    }
}
