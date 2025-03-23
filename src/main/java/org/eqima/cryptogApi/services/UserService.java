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

    public UserRepresentation addATAs(UserRepresentation user, List<AssociatedTokenAccountDto> ATAList) {
        // Récupérer les attributs de l'utilisateur
        Map<String, List<String>> userAttributes = user.getAttributes();
        if (userAttributes == null) {
            userAttributes = new HashMap<>();
        }

        // Récupérer les ATAs existants ou initialiser une nouvelle liste
        List<AssociatedTokenAccountDto> userATAs = new ArrayList<>();
        if (userAttributes.containsKey("ATAs")) {
            String json = userAttributes.get("ATAs").get(0); // Récupérer le JSON des ATAs
            Type listType = new TypeToken<List<AssociatedTokenAccountDto>>() {}.getType();
            userATAs = gson.fromJson(json, listType); // Désérialiser le JSON en liste d'objets
        }

        // Ajouter les nouveaux ATAs à la liste existante
        userATAs.addAll(ATAList);

        // Sérialiser la liste mise à jour en JSON
        String updatedATAsJson = gson.toJson(userATAs);

        // Mettre à jour les attributs de l'utilisateur
        userAttributes.put("ATAs", Collections.singletonList(updatedATAsJson)); // Stocker directement la chaîne JSON
        user.setAttributes(userAttributes);

        System.out.println(userAttributes);
        // Mettre à jour l'utilisateur dans Keycloak
        UserResource userResource = keycloakConfig.users().get(user.getId());
        userResource.update(user);

        // Récupérer l'utilisateur mis à jour pour vérification
        UserRepresentation updatedUser = userResource.toRepresentation();
        System.out.println("Updated User Attributes: " + updatedUser.getAttributes());

        return updatedUser;
    }
    public UserRepresentation addUserATA(String username,List<AssociatedTokenAccountDto> ATAList){
        UserRepresentation user = keycloakConfig.users().searchByUsername(username,true).get(0);
        return addATAs(user,ATAList);
    }
}
