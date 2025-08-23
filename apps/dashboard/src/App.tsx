import { QueryClient, QueryClientProvider } from "@tanstack/react-query";
import { lazy } from "react";

type Props = {
  queryClient: QueryClient
}

const Greeter = lazy(() => import("./Greeter"));

export default function App({ queryClient }: Props) {
  return (
    <QueryClientProvider client={queryClient}>
      <h1>Works!</h1>
      <Greeter />
    </QueryClientProvider>
  );
}
