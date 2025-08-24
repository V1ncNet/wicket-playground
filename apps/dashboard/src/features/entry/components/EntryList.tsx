import entriesQueryOptions from "@/features/entry/app/entriesQueryOptions";
import { useQuery } from "@tanstack/react-query";

export default function EntryList() {

  const { data: entries = [] } = useQuery(entriesQueryOptions);

  return (
    <ul>
      {entries.map((item) => (
        <li key={item.id}>
          {item.id} - {item.text}
        </li>
      ))}
    </ul>
  );
}
