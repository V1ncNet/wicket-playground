import { lazy } from "react";

const Greeter = lazy(() => import("./Greeter"));

export default function App() {
  return (
    <>
      <h1>Works!</h1>
      <Greeter />
    </>
  );
}
