import greetQueryOptions from "@/features/greeting/app/greetQueryOptions";
import { useQuery } from "@tanstack/react-query";

export default function Greeter() {

  const { data: message } = useQuery(greetQueryOptions);

  return (
    <>
      <p>{message}</p>
    </>
  );
}
