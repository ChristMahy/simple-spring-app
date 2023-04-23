# Authorization server

## Test de la mise en place et configuration

### Login

#### Etape 1:
Se connecter avec un navigateur à l'url suivante.
```
http://authserver:9000/oauth2/authorize?response_type=code&client_id=taco-admin-client&redirect_uri=http%3A%2F%2Fauthserver%3A9090%2Flogin%2Foauth2%2Fcode%2Ftaco-admin-client-oidc&scope=ingredient.write%2Bingredient.delete
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
--data-urlencode 'code=YfHhwRICbP4xWjIrXRwhurAZ8Da-eoL_wbBqOrLDnT8F9kEpkQB4z6Ht1RkQQ-NdXKIiml9zRmc7lHICLeNdteFB56LVns2PKg0zFTWHJ4FHdF-M5PNFRLs7ksuuckqf' \
--data-urlencode 'redirect_uri=http://authserver:9090/login/oauth2/code/taco-admin-client-oidc' \
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
--data-urlencode 'refresh_token=yuSA3TDQz_uKAM92bUdWbrbR2Qm1TGF5RVcQt6qoB1rlKwlTWcqurM59iZ0aXLfeAJTRWV-8bZ3gnqlcL24LKniDpKVHw3VxA7L0hllJ0TZTBornGq2--ylyTeQiitjD'
```

Le "refresh_token" se trouve dans le résultat de l'obtention du token.