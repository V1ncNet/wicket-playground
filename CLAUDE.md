# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Build & Run

```bash
# Full backend build
mvn clean package

# Skip tests
mvn clean package -DskipTests

# Run with OAuth2 authentication
mvn spring-boot:run -Dspring-boot.run.arguments="--spring.profiles.active=wicket,keycloak"

# React dashboard dev server (hot reload)
cd apps/dashboard && npm run dev

# React dashboard production build
cd apps/dashboard && npm run build
```

## Testing

```bash
# Standard tests (excludes long-running)
mvn test

# Long-running tests only
mvn test -Ptests-running-long
```

Long-running tests are tagged with `@Tag("de.vinado.app.playground.test.LongRunningTests")`.

## Code Style

**Java (Checkstyle):** Max 120 characters per line, no star imports, no tabs. Runs automatically in the `validate` phase (`mvn checkstyle:check`). Suppress with `// @checkstyle:off: RuleNameHere` / `// @checkstyle:on: RuleNameHere`.

**React/TypeScript:**
```bash
cd apps/dashboard && npm run lint
```

## Docker Compose (Local Dev Stack)

```bash
# Base services only (preview + CodiMD, no auth)
docker compose -f compose.yaml up -d

# Full stack with Keycloak (OAuth2/OIDC)
docker compose -f compose.yaml -f compose.keycloak.yaml up -d
```

Keycloak runs at `http://localhost:8180`. Development user: `adult` / `B4nk`.

## Requirements

- **Maven** must be installed globally — no `mvnw` wrapper exists
- **Node.js 22.16.0** is auto-downloaded by `frontend-maven-plugin` during `mvn` builds; for local React dev, run `nvm use` (version pinned in `.nvmrc`)
- **Lombok**: IntelliJ IDEA requires "Enable annotation processing" under Settings → Build → Compiler → Annotation Processors

## Keycloak Realm Export

After changing Keycloak configuration via the web UI, export and commit:

```bash
docker compose -f compose.yaml -f compose.keycloak.yaml exec keycloak sh -c \
  "cp -rp /opt/keycloak/data/h2 /tmp ; \
  /opt/keycloak/bin/kc.sh export --dir /opt/keycloak/data/import --realm local --users realm_file \
    --db dev-file \
    --db-url 'jdbc:h2:file:/tmp/h2/keycloakdb;NON_KEYWORDS=VALUE'"
```

Then commit the updated `docker/keycloak/provisioning/local-realm.json`.

## Commits & Branching

Commits go directly to `main`. Use short imperative phrases (e.g., "Add login page", "Fix checkstyle warning").
