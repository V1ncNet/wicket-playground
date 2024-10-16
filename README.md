# Playground

## Snippets

```shell
$ docker compose exec keycloak \
 /opt/keycloak/bin/kc.sh export --dir /opt/keycloak/data/import --realm local --users realm_file
```

```shell
$ curl -X POST 'http://localhost:8180/realms/local/protocol/openid-connect/token' \
 -H 'Content-Type: application/x-www-form-urlencoded' \
 --data-urlencode 'username=admin' \
 --data-urlencode 'password=secret' \
 --data-urlencode 'client_id=admin-cli' \
 --data-urlencode 'client_secret=SKbyJ5gfwx6l0f9KNqqYolWXzEwPYu09' \
 --data-urlencode 'grant_type=password' | jq
```
