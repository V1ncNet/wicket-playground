import { createFileRoute, Link } from "@tanstack/react-router";
import { lazy } from "react";

export const Route = createFileRoute("/_root/_default/")({
  component: HomePage,
});

const Greeter = lazy(() => import("@/features/greeting/components/Greeter"));

function HomePage() {
  return (
    <>
      <h1>Works!</h1>
      <Greeter />
      <Link to="/about">About</Link>
    </>
  );
}
