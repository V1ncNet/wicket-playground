import { env } from "@/config/env";

export async function greet(signal?: AbortSignal) {
  return await fetch(`${env.BASE_URL}/api/v1/dashboard/greet`, { signal })
    .then(res => res.text());
}
