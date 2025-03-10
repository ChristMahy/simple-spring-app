# Authorization server

## Test de la mise en place et configuration

### Login

#### Etape 1:
Se connecter avec un navigateur à l'url suivante.
```
http://authserver:9000/oauth2/authorize?response_type=code&client_id=taco-admin-client&redirect_uri=http%3A%2F%2Fclientserver%3A9090%2Flogin%2Foauth2%2Fcode%2Ftaco-admin-client-oidc&scope=ingredient.write+ingredient.delete
```
#### Etape 2:
Consentir aux deux scopes choisis.
#### Etape 3:
Dans l'url du navigateur se trouve un code, le copier.

### Obtention du token

Il s'obtient avec l'adresse suivante:

```
curl --location 'http://authserver:9000/oauth2/token' \
--header 'Content-Type: application/x-www-form-urlencoded' \
--header 'Authorization: Basic dGFjby1hZG1pbi1jbGllbnQ6dGFjby1zZWNyZXQ=' \
--data-urlencode 'code=<CODE>' \
--data-urlencode 'redirect_uri=http://clientserver:9090/login/oauth2/code/taco-admin-client-oidc' \
--data-urlencode 'grant_type=authorization_code'
```

L'autorisation s'obtient avec la combinaison de l'utilisateur et mot de passe utilisés lors de la configuration du serveur d'autorisation (cfr. les "clients").
Le code s'obtient après les étapes du login.

### Refresh du token

```
curl --location 'http://authserver:9000/oauth2/token' \
--header 'Authorization: Basic dGFjby1hZG1pbi1jbGllbnQ6dGFjby1zZWNyZXQ=' \
--header 'Content-Type: application/x-www-form-urlencoded' \
--data-urlencode 'grant_type=refresh_token' \
--data-urlencode 'refresh_token=<REFRESH_TOKEN>'
```

Le "refresh_token" se trouve dans le résultat de l'obtention du token.