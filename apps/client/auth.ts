import NextAuth, { Account, Session } from "next-auth";
import Keycloak from "@auth/core/providers/keycloak";
import { JWT } from "next-auth/jwt";

const backchannelUrl = process.env.AUTH_KEYCLOAK_BACKCHANNLE_URL ?? process.env.AUTH_KEYCLOAK_ISSUER!;

export const { auth, handlers } = NextAuth({
  providers: [
    Keycloak({
      wellKnown: `${backchannelUrl}/.well-known/openid-connect`,
      authorization: `${process.env.AUTH_KEYCLOAK_ISSUER}/protocol/openid-connect/auth`,
      token: `${backchannelUrl}/protocol/openid-connect/token`,
      userinfo: `${backchannelUrl}/protocol/openid-connect/userinfo`,
      jwks_endpoint: `${backchannelUrl}/protocol/openid-connect/certs`,
    }),
  ],
  callbacks: {
    authorized: async ({ auth }) => {
      return !!auth?.user;
    },
    jwt: async ({ token, account }) => {
      if (account) {
        token.accessToken = account.access_token;
        token.accessTokenExpiresAt = Date.now() + account.expires_in! * 1000;
        token.refreshToken = account.refresh_token;
        return token;
      }

      if (Date.now() < token.accessTokenExpiresAt!) {
        return token;
      }

      return await refreshAccessToken(token);
    },
    session: async ({ session, token }: { session: Session; token?: JWT; }) => {
      if (token?.error === "RefreshAccessTokenError") {
        session.user = undefined;
        return session;
      }

      session.user!.id = token?.sub;
      session.accessToken = token?.accessToken;
      return session;
    },
  },
});

async function refreshAccessToken(token: JWT) {
  try {
    const url = `${backchannelUrl}/protocol/openid-connect/token`;
    const formData = new URLSearchParams({
      client_id: process.env.AUTH_KEYCLOAK_ID!,
      client_secret: process.env.AUTH_KEYCLOAK_SECRET!,
      grant_type: "refresh_token",
      refresh_token: token.refreshToken!,
    });

    const response = await fetch(url, {
      body: formData,
      method: "POST",
      headers: {
        "Content-Type": "application/x-www-form-urlencoded",
      },
    });

    const account: Account = await response.json();

    if (!response.ok) {
      throw account;
    }

    return {
      ...token,
      accessToken: account.access_token,
      accessTokenExpiresAt: Date.now() + account.expires_in! * 1000,
      refreshToken: account.refresh_token ?? token.refreshToken,
    };
  } catch (error) {
    return {
      ...token,
      error: "RefreshAccessTokenError",
    };
  }
}

declare module "next-auth" {

  interface Account {
    expires_in?: number;
    refresh_expires_in?: number;
    session_state?: string;
  }

  interface Session {
    accessToken?: string;
  }
}

declare module "next-auth/jwt" {

  interface JWT {
    accessToken?: string;
    accessTokenExpiresAt?: number;
    refreshToken?: string;
    error?: string;
  }
}
