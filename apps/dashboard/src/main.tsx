import { QueryClient } from "@tanstack/react-query";
import { createRouter } from "@tanstack/react-router";
import { StrictMode } from "react";
import { createRoot } from "react-dom/client";
import App from "./App.tsx";
import { env } from "./config/env";
import { routeTree } from "./routeTree.gen";

const queryClient = new QueryClient();

const router = createRouter({
  routeTree,
  context: {
    queryClient,
  },
  basepath: env.BASE_PATH,
  defaultPreload: "intent",
  scrollRestoration: true,
  defaultStructuralSharing: true,
  defaultPreloadStaleTime: 0,
});

createRoot(document.getElementById("root")!).render(
  <StrictMode>
    <App queryClient={queryClient} router={router} />
  </StrictMode>,
);

declare module "@tanstack/react-router" {

  interface Register {
    router: typeof router;
  }
}
