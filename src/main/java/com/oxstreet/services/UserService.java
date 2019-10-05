package com.oxstreet.services;

import java.util.Set;

public interface UserService {

    void listAppClients();

    void createAppClient(String appClientName, Set<String> allowedOAuthScopes, Set<String> identityProviders);

    void listUsers();

    void removeAppClient(String appClientId);
}
