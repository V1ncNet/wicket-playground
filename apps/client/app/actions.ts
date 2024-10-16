"use server";

export async function fetchServerMessage(): Promise<string> {
  const response = await fetch(`${process.env.PLAYGROUND_BACKEND_BASE_URL}/api/v1/hello-world`, {
    headers: {
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
