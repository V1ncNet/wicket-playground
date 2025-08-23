import { QueryClient, QueryClientProvider } from "@tanstack/react-query";
import { type Register, RouterProvider } from "@tanstack/react-router";

type Props = {
  queryClient: QueryClient
} & Register

export default function App({ queryClient, router }: Props) {
  return (
    <QueryClientProvider client={queryClient}>
      <RouterProvider router={router} />
    </QueryClientProvider>
  );
}
