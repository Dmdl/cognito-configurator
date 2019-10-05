package com.oxstreet.services.impl;

import com.oxstreet.services.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.auth.credentials.EnvironmentVariableCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.cognitoidentityprovider.CognitoIdentityProviderClient;
import software.amazon.awssdk.services.cognitoidentityprovider.model.*;

import java.util.Set;

@Service
public class AWSUserServiceImpl implements UserService {

    @Value("${cloud.aws.cognito.userpool.id}")
    private String userPoolId;

    private CognitoIdentityProviderClient cognitoclient = CognitoIdentityProviderClient.builder()
            .credentialsProvider(EnvironmentVariableCredentialsProvider.create())
            .region(Region.AP_SOUTHEAST_1)
            .build();

    public void createAppClient(String appClientName, Set<String> allowedOAuthScopes, Set<String> identityProviders) {
        CreateUserPoolClientResponse response = cognitoclient.createUserPoolClient(
                CreateUserPoolClientRequest.builder()
                        .clientName(appClientName)
                        .generateSecret(true)
                        .allowedOAuthFlows(OAuthFlowType.CLIENT_CREDENTIALS)
                        .allowedOAuthScopes(allowedOAuthScopes)
                        .supportedIdentityProviders(identityProviders)
                        .allowedOAuthFlowsUserPoolClient(true)
                        .userPoolId(userPoolId)
                        .build()
        );
        System.out.println(response.userPoolClient().clientId() + "---" + response.userPoolClient().clientSecret());
    }

    public void listAppClients() {
        ListUserPoolClientsRequest build = ListUserPoolClientsRequest.builder().userPoolId(userPoolId).build();
        ListUserPoolClientsResponse listUserPoolClientsResponse = cognitoclient.listUserPoolClients(build);
        listUserPoolClientsResponse.userPoolClients().forEach(each -> System.out.println(each.clientId() + "-" + each.clientName()));
    }

    public void listUsers() {
        ListUsersResponse listUsersResponse = cognitoclient.listUsers(ListUsersRequest.builder().userPoolId(userPoolId).limit(10).build());
        listUsersResponse.users().forEach(each -> System.out.println(each.username()));
    }

    public void removeAppClient(String appClientId) {
        cognitoclient.deleteUserPoolClient(DeleteUserPoolClientRequest.builder().userPoolId(userPoolId).clientId(appClientId).build());
    }
}
