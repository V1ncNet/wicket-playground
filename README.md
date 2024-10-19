# Playground

## Snippets

```shell
$ docker compose exec -it keycloak sh -c \
  "cp -Rp /opt/keycloak/data/h2 /tmp; \
    /opt/keycloak/bin/kc.sh export --dir /opt/keycloak/data/import --realm local --users same_file \
    --db dev-file --db-url 'jdbc:h2:file:/tmp/h2/keycloakdb;NON_KEYWORDS=VALUE'; \
  "
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


## Users

| Username | Password/Secret | Description            | Realm  | URL                                         |
|----------|-----------------|------------------------|--------|---------------------------------------------|
| `user`   | `secret`        | Default user           | local  | http://localhost:8180/admin/local/console/  |
| `admin`  | `secret`        | Keycloak administrator | master | http://localhost:8180/admin/master/console/ |
