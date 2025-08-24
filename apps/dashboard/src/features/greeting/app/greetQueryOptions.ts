import { greet } from "@/features/greeting/model/repository/greeting";
import { queryOptions } from "@tanstack/react-query";

export default queryOptions({
  queryKey: ["greet"],
  queryFn: ({ signal }) => greet(signal),
});
