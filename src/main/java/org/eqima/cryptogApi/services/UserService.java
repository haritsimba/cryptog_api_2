package org.eqima.cryptogApi.services;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.eqima.cryptogApi.dto.AssociatedTokenAccountDto;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.*;

@Service
public class UserService {

    @Autowired
    RealmResource keycloakConfig;
    @Autowired
    Gson gson;
    public UserRepresentation getUserByUsername(String username){
        List<UserRepresentation> users = keycloakConfig
                .users()
                .searchByUsername(username,true);
        if(!users.isEmpty()){
            return users.stream().findFirst().get();
        }
        return null;
    }

    public UserRepresentation addUserATAs(UserRepresentation user, List<AssociatedTokenAccountDto> ATAList) {

        Map<String, List<String>> userAttributes = user.getAttributes();
        if (userAttributes == null) {
            userAttributes = new HashMap<>();
        }

        List<AssociatedTokenAccountDto> userATAs = new ArrayList<>();
        if (userAttributes.containsKey("ATAs")) {
            String json = userAttributes.get("ATAs").get(0); // Récupérer le JSON des ATAs
            Type listType = new TypeToken<List<AssociatedTokenAccountDto>>() {}.getType();
            userATAs = gson.fromJson(json, listType); // Désérialiser le JSON en liste d'objets
        }

        userATAs.addAll(ATAList);

        String updatedATAsJson = gson.toJson(userATAs);

        userAttributes.put("ATAs", Collections.singletonList(updatedATAsJson)); // Stocker directement la chaîne JSON
        user.setAttributes(userAttributes);
        UserResource userResource = keycloakConfig.users().get(user.getId());
        userResource.update(user);
        return userResource.toRepresentation();
    }


    public UserRepresentation getAndUpdateUserATAs(String username, List<AssociatedTokenAccountDto> ATAList){
        UserRepresentation user = keycloakConfig.users().searchByUsername(username,true).get(0);
        return addUserATAs(user,ATAList);
    }
}
