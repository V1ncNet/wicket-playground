import { Suspense } from "react";
import { fetchServerMessage } from "./actions";

export const dynamic = "force-dynamic";

export default async function Home() {

  const message = await fetchServerMessage();

  return (
    <>
      <Suspense>
        <h1>{message}</h1>
      </Suspense>
    </>
  );
}
