package com.michalenok.wallet.keycloak;

import com.michalenok.wallet.model.dto.request.UserCreateDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;

import javax.ws.rs.core.Response;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static com.michalenok.wallet.keycloak.KeycloakConfig.keycloak;
import static org.keycloak.admin.client.CreatedResponseUtil.getCreatedId;

@Log4j2
@Service
@RequiredArgsConstructor
public class KeycloakService {
    public void addUser(UserCreateDto userDTO){
        CredentialRepresentation credential = Credentials
                .createPasswordCredentials(userDTO.password());
        UserRepresentation user = new UserRepresentation();
        user.setUsername(userDTO.mail());
        user.setEmail(userDTO.mail());
        user.setCredentials(Collections.singletonList(credential));
        user.setEnabled(true);
        user.setRealmRoles(userDTO.role().stream().toList());
        UsersResource instance = getInstance();
        Response response = instance.create(user);
        String userId = getCreatedId(response);
        setRole(userId);
    }

    private void setRole(String userId){
        RealmResource realmResource = keycloak.realm("wallet");
        UsersResource usersResource = realmResource.users();
        RoleRepresentation userRealmRole = new RoleRepresentation();
        userRealmRole = realmResource.roles().get("user").toRepresentation();

        UserResource userResource = usersResource.get(userId);
        userResource.roles().realmLevel().add(Collections.singletonList(userRealmRole));
    }

    public List<UserRepresentation> getUser(String userName){
        UsersResource usersResource = getInstance();
        List<UserRepresentation> user = usersResource.search(userName, true);
        return user;
    }

    public void updateUser(String userId, UserCreateDto userDTO){
        CredentialRepresentation credential = Credentials
                .createPasswordCredentials(userDTO.password());
        UserRepresentation user = new UserRepresentation();
        user.setUsername(userDTO.mail());
        user.setEmail(userDTO.mail());
        user.setCredentials(Collections.singletonList(credential));

        UsersResource usersResource = getInstance();
        usersResource.get(userId).update(user);
    }
    public void deleteUser(String userId){
        UsersResource usersResource = getInstance();
        usersResource.get(userId)
                .remove();
    }


    public void sendVerificationLink(String userId){
        UsersResource usersResource = getInstance();
        usersResource.get(userId)
                .sendVerifyEmail();
    }

    public void sendResetPassword(String userId){
        UsersResource usersResource = getInstance();

        usersResource.get(userId)
                .executeActionsEmail(Arrays.asList("UPDATE_PASSWORD"));
    }

    public UsersResource getInstance(){
        log.info("UsersResource getInstance {}", KeycloakConfig.getInstance().realm(KeycloakConfig.realm).users());
        return KeycloakConfig.getInstance().realm("wallet").users();
    }

}
