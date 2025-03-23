# cryptog_api_2
<h2>Configuration de Keycloak</h2>
<p>Suivez ces étapes pour configurer Keycloak afin de gérer l'attribut personnalisé <code>ATAs</code>.</p>

    <h3>1. Créer un nouveau client</h3>
    <ul>
        <li>Allez dans l'interface d'administration de Keycloak.</li>
        <li>Créez un nouveau client dans votre royaume (realm).</li>
        <li>Configurez les paramètres du client selon vos besoins.</li>
    </ul>

    <h3>2. Activer le profil utilisateur</h3>
    <ul>
        <li>Allez dans <strong>Realm Settings</strong> > <strong>User Profile</strong>.</li>
        <li>Créez un nouvel attribut personnalisé nommé <code>ATAs</code>.</li>
        <li>Configurez l'attribut pour qu'il soit stocké sous forme de JSON.</li>
    </ul>

    <h3>3. Créer un nouveau scope client</h3>
    <ul>
        <li>Allez dans <strong>Client Scopes</strong>.</li>
        <li>Créez un nouveau scope client nommé <code>ATAs</code>.</li>
        <li>Ajoutez un mapper de revendications (claim mapper) pour inclure l'attribut <code>ATAs</code> dans le token.</li>
    </ul>

    <h3>4. Associer le scope client au client</h3>
    <ul>
        <li>Allez dans votre client (par exemple, <code>volanaka</code>).</li>
        <li>Ajoutez le scope client <code>ATAs</code> à votre client.</li>
    </ul>

    <h2>Utilisation du Code</h2>
    <p>Voici comment utiliser le code pour gérer l'attribut <code>ATAs</code> dans Keycloak.</p>

    <h3>1. Ajouter des ATAs à un utilisateur</h3>
