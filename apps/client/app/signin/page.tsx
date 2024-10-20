"use client";

import { signIn, useSession } from "next-auth/react";
import { Suspense, useEffect } from "react";
import { useRouter, useSearchParams } from "next/navigation";

function SignInHandler() {

  const router = useRouter();
  const params = useSearchParams();
  const callbackUrl = params.get("callbackUrl") ?? "/";
  const { status } = useSession();

  useEffect(() => {
    if (status === "unauthenticated") {
      void signIn("keycloak", { redirectTo: callbackUrl });
    } else if (status === "authenticated") {
      void router.push(callbackUrl);
    }
  }, [status]);

  return null;
}

export default function SignInPage() {
  return (
    <Suspense>
      <SignInHandler/>
    </Suspense>
  );
}
