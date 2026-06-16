---
name: dev-start
description: Start the full local development stack for this project. Use when beginning a development session.
disable-model-invocation: true
---

Start the complete development environment in the correct order:

1. **Start Docker Compose services** based on whether OAuth2 is needed:

   Without Keycloak (basic mode):
   ```bash
   docker compose -f compose.yaml up -d
   ```

   With Keycloak (OAuth2/OIDC authentication):
   ```bash
   docker compose -f compose.yaml -f compose.keycloak.yaml up -d
   ```

2. **Start the Spring Boot backend** with the appropriate profile:

   Without authentication:
   ```bash
   mvn spring-boot:run
   ```

   With Keycloak authentication:
   ```bash
   mvn spring-boot:run -Dspring-boot.run.arguments="--spring.profiles.active=wicket,keycloak"
   ```

3. **Optionally start the React dev server** for hot-reloading dashboard changes:
   ```bash
   cd apps/dashboard && npm run dev
   ```

Ask the user whether they want Keycloak (OAuth2) enabled before starting if `$ARGUMENTS` is empty. If `$ARGUMENTS` contains "keycloak" or "auth", use the Keycloak variant.

**Service URLs once running:**
- Application: http://localhost:8080
- Keycloak admin: http://localhost:8180/admin
- CodiMD: http://localhost:8280

**Default dev credentials:** `adult` / `B4nk`
