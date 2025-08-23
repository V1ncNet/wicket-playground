import { queryOptions, useQuery } from "@tanstack/react-query";

async function greet(signal?: AbortSignal) {
  return await fetch("http://localhost:8080/api/v1/dashboard/greet", { signal })
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
