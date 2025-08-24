import { queryOptions, useQuery } from "@tanstack/react-query";
import { env } from "./config/env.ts";

async function greet(signal?: AbortSignal) {
  return await fetch(`${env.BASE_URL}/api/v1/dashboard/greet`, { signal })
    .then(res => res.text());
}

const greetQueryOptions = queryOptions({
  queryKey: ["greet"],
  queryFn: ({ signal }) => greet(signal),
});

export default function Greeter() {

  const { data: message } = useQuery(greetQueryOptions);

  return (
    <>
      <p>{message}</p>
    </>
  );
}
