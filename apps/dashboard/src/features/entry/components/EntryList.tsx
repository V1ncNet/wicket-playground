import entriesQueryOptions from "@/features/entry/app/entriesQueryOptions";
import { deleteEntry } from "@/features/entry/model/repository/entry";
import { useMutation, useQuery } from "@tanstack/react-query";

export default function EntryList() {

  const { data: entries = [], refetch } = useQuery(entriesQueryOptions);

  const { mutateAsync: deleteEntryMutation } = useMutation({
    mutationFn: deleteEntry,
    onSuccess: async () => {
      await refetch();
    },
  });

  const handleDelete = (id: string) => async () => {
    await deleteEntryMutation(id);
  };

  return (
    <ul>
      {entries.map((item) => (
        <li key={item.id}>
          {item.id} - {item.text}
          <button type="button" className="btn btn-link" onClick={handleDelete(item.id!)}>
            <i className="bi bi-trash"></i>
          </button>
        </li>
      ))}
    </ul>
  );
}
