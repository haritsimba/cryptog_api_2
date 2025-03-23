# cryptog_api_2

## Configuration de Keycloak

Suivez ces étapes pour configurer Keycloak afin de gérer l'attribut personnalisé `ATAs`.

### 1. Créer un nouveau client
- Allez dans l'interface d'administration de Keycloak.
- Créez un nouveau client dans votre royaume (realm).
- Configurez les paramètres du client selon vos besoins.

### 2. Activer le profil utilisateur
- Allez dans **Realm Settings** > **User Profile**.
- Créez un nouvel attribut personnalisé nommé `ATAs`.
- Configurez l'attribut pour qu'il soit stocké sous forme de JSON.

### 3. Créer un nouveau scope client
- Allez dans **Client Scopes**.
- Créez un nouveau scope client nommé `ATAs`.
- Ajoutez un mapper de revendications (claim mapper) pour inclure l'attribut `ATAs` dans le token.

### 4. Associer le scope client au client
- Allez dans votre client (par exemple, `volanaka`).
- Ajoutez le scope client `ATAs` à votre client.