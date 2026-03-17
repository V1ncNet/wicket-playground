import { z } from "zod";

const Entry = z.object({
  id: z.string().uuid().nullish(),
  text: z.string().nullable(),
});

export default Entry;
