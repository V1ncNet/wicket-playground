"use server";

import { auth } from "@/auth";

export async function fetchServerMessage(): Promise<string> {
  const session = await auth();
  const response = await fetch(`${process.env.PLAYGROUND_BACKEND_BASE_URL}/api/v1/hello-world`, {
    headers: {
      "Authorization": `Bearer ${session!.accessToken}`,
      "Content-Type": "application/json",
    },
  });

  if (!response.ok) {
    throw response;
  }

  const json = await response.text();
  const { message } = JSON.parse(json);
  return message;
}
