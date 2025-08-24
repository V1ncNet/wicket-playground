import { z } from "zod";

const Entry = z.object({
  id: z.uuid().nullish(),
  text: z.string().nullable(),
});

export default Entry;
