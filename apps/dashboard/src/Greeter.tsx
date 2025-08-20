import { useEffect, useState } from "react";

export default function Greeter() {

  const [message, setMessage] = useState<string>();

  useEffect(() => {
    const abortController = new AbortController();

    fetch("http://localhost:8080/api/v1/dashboard/greet", {
      signal: abortController.signal,
      headers: {
        "Accept": "text/plain",
      },
    })
      .then(res => res.text())
      .then(data => setMessage(data))
      .catch(error => {
        if (error === "Component unmounted") {
          console.debug(error);
        } else {
          throw error;
        }
      });

    return () => {
      abortController.abort("Component unmounted");
    };
  }, [setMessage]);

  return (
    <>
      <p>{message}</p>
    </>
  );
}
