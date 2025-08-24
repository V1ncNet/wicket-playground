import { createFileRoute, Link } from "@tanstack/react-router";

export const Route = createFileRoute("/_root/about")({
  component: RouteComponent,
});

function RouteComponent() {
  return (
    <>
      <Link to="/">Dashboard</Link>
    </>
  );
}
