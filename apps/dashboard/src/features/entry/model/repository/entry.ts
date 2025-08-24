import { env } from "@/config/env";
import Entry from "@/features/entry/model/entry";
import { z } from "zod";

export async function createEntry(entry: z.infer<typeof Entry>, signal?: AbortSignal): Promise<string> {
  const method = "POST";
  const headers = {
    "Content-Type": "application/json",
  };
  const body = JSON.stringify(entry);

  const res = await fetch(`${env.BASE_URL}/api/v1/dashboard/entries`, { method, headers, body, signal });
  if (!res.ok) throw new Error(await res.text());

  const location = res.headers.get("location")!;
  return location.split("/").pop()!;
}

export async function findAllEntries(signal?: AbortSignal): Promise<z.infer<typeof Entry>[]> {
  const headers = {
    "Accept": "application/json",
  };

  const res = await fetch(`${env.BASE_URL}/api/v1/dashboard/entries`, { headers, signal });
  if (!res.ok) throw new Error(await res.text());

  const json = await res.json();
  return z.array(Entry).parseAsync(json.entries);
}
