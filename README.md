# Wicket Playground

This project houses some PoC for integrations of frameworks, protocols and 3rd-party service outside the Apache Wicket ecosystem.

The application provides the following features:

* Spring Boot application bootstrapper
* Pluggable Wicket modules
* 3rd-party PDF preview service embedded in an iframe
* Bootstrap 5
* Wicket Webjars
* Static resource compressors
* Sass-compiled static resources
* Render JavaScript resources to footer
* Wicket test suites


## Keycloak

Keycloak is an authorization provider that implements the OAuth2 and OpenID Connect protocols. It manages software
clients, users, their roles and claims for the project.

### Users

| Username       | Password       | Description            | Realm  | URL                                         |
|----------------|----------------|------------------------|--------|---------------------------------------------|
| `landlord`[^1] | `Prop3r7y`[^1] | Keycloak administrator | master | http://localhost:8180/admin/master/console/ |

[^1]: Corresponds to the values of `KEYCLOAK_ADMIN` and `KEYCLOAK_ADMIN_PASSWORD`, set for Composes' _keycloak-server_.

### Configuration Export

This section explains how to export updated configurations so that they can be managed by Git. The development
configuration for Keycloak is part of this project to distribute changes through Git.

First, make sure your development stack is up and running. Perform your necessary changes in the Keycloak web UI. Next,
perform the following command. This will start a new Keycloak instance inside the running container.

```shell
docker compose exec keycloak-server \
  /opt/keycloak/bin/kc.sh export --dir /opt/keycloak/data/import --realm playground --users realm_file
```
