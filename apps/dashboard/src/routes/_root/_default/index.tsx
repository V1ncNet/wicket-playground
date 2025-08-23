import { createFileRoute } from "@tanstack/react-router";
import { lazy } from "react";

export const Route = createFileRoute("/_root/_default/")({
  component: HomePage,
});

const Greeter = lazy(() => import("../../../Greeter"));

function HomePage() {
  return (
    <>
      <h1>Works!</h1>
      <Greeter />
    </>
  );
}
