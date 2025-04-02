# Wicket Playground

This project houses some PoC for integrations of frameworks, protocols and 3rd-party service outside the Apache Wicket
ecosystem.

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
* OAuth2 secured Wicket page
* Profile-driven environments
* Keycloak smoke test
* Profile-driven test executions
* Social Login for HackMD's CodiMD with Keycloak
* Embed CodiMD notes in a Wicket page

To enable authentication start the Sprint Boot application with the
[_Spring profile_](https://docs.spring.io/spring-framework/reference/core/beans/environment.html#beans-definition-profiles-enable)
`wicket,keycloak` active. To do so, override the `application.yml` or use the runtime property
`-Dspring.profiles.active=wicket,keycloak`. Also use the corresponding Docker Compose file in addition to the default
one. Start the entire stack using `docker compose -f compose.yaml -f compose.keycloak.yaml up -d`.


## Keycloak

Keycloak is an authorization provider that implements the OAuth2 and OpenID Connect protocols. It manages software
clients, users, their roles and claims for the project.

### Users

Keycloak is preconfigured with a variety of users that are more or less useful. The username-password-combination
`adult:B4nk` might be the only one you ever need for development and manual testing.

| Username             | Password            | Description            | Realm  | URL                                         |
|----------------------|---------------------|------------------------|--------|---------------------------------------------|
| <mark>`adult`</mark> | <mark>`B4nk`</mark> | Realm superuser        | local  | http://localhost:8180/admin/local/console/  |
| `landlord`[^1]       | `Prop3r7y`[^1]      | Keycloak administrator | master | http://localhost:8180/admin/master/console/ |

[^1]: Corresponds to the values of `KEYCLOAK_ADMIN` and `KEYCLOAK_ADMIN_PASSWORD`, set for Composes' _keycloak_.

### Configuration Export

This section explains how to export updated configurations so that they can be managed by Git. The development
configuration for Keycloak is part of this project to distribute changes through Git.

First, make sure your development stack is up and running. Perform your necessary changes in the Keycloak web UI. Next,
perform the following command. This will start a new Keycloak instance inside the running container.

```shell
docker compose -f compose.yaml -f compose.keycloak.yaml exec keycloak sh -c \
  "cp -rp /opt/keycloak/data/h2 /tmp ; \
  /opt/keycloak/bin/kc.sh export --dir /opt/keycloak/data/import --realm local --users realm_file \
    --db dev-file \
    --db-url 'jdbc:h2:file:/tmp/h2/keycloakdb;NON_KEYWORDS=VALUE'"
```


## CodiMD Pads

[CodiMD](https://github.com/hackmdio/codimd) is an open-source, collaborative and self-hosted service for managing notes
in Markdown syntax.

### Without Authentication

Start the application and navigate to <http://localhost:8080/note> to see an embedded CodiMD pad within a Wicket page.

### With Authentication

Unfortunately, the integration into other services and a Docker environment is finicky and has a few caveats and
potential deal-breaker.

#### 1. Hostname

Like any other OAuth2 client, CodiMD needs to know where to find the OAuth2 authorization server, but neither CodiMD nor
Keycloak separate their configuration endpoints that is easily compatible with a Docker setup. So both services must
communicate via the _default gateway_ using the `host.docker.internal` hostname. You may have to add the host to you
`localhost` IP address `127.0.0.1` in `/etc/hosts`.

If anyone has a way to resolve the workaround, feel free to contribute, write an issue or DM me. I'm so desperate for a
clean and convenient solution. Btw, running the Docker Compose services in network mode `host` is not an option, because
it defeats the purpose of Docker IMO and doesn't work on macOS anyway.

#### 2. SSO

The Wicket page directly reference a note ID. As long as you are not logged in CodiMD will throw an _Internal Server
Error_ and logs that the URL is not valid. Because of the [first issue](#1-hostname), SSO cannot work unless
`host.docker.internal` is used everywhere. To work around the issue, navigate to <http://localhost:8280> and login a
second time. Get back to <http://localhost:8080/note> afterward and reload if you need to.
