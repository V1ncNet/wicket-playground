import Entry from "@/features/entry/model/entry";
import { createEntry } from "@/features/entry/model/repository/entry";
import { useMutation, useQueryClient } from "@tanstack/react-query";
import { type FormEvent, useRef } from "react";

export default function EntryForm() {

  const queryClient = useQueryClient();

  const { mutateAsync, isPending } = useMutation({
    mutationFn: createEntry,
    onSuccess: async () => {
      await queryClient.invalidateQueries({ queryKey: ["entries"] });
    },
  });

  const textInputRef = useRef<HTMLInputElement>(null);

  const handleSubmit = async (e: FormEvent<HTMLFormElement>) => {
    e.preventDefault();

    const form = e.currentTarget;
    const formData = new FormData(form);
    const data = Object.fromEntries(formData.entries());
    const entry = await Entry.parseAsync(data);

    await mutateAsync(entry, {
      onSuccess: () => {
        form.reset();
        textInputRef.current?.focus();
      },
    });
  };

  return (
    <form onSubmit={handleSubmit} className="mb-3">
      <div className="mb-3">
        <label htmlFor="entry-text" className="form-label">Text</label>
        <input ref={textInputRef} type="text" name="text" className="form-control" id="entry-text" />
      </div>
      <button type="submit" className="btn btn-primary" disabled={isPending}>
        {isPending ? "Submitting..." : "Submit"}
      </button>
    </form>
  );
}
