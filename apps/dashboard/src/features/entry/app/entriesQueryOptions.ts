import { findAllEntries } from "@/features/entry/model/repository/entry";
import { queryOptions } from "@tanstack/react-query";

export default queryOptions({
  queryKey: ["entries"],
  queryFn: ({ signal }) => findAllEntries(signal),
});
