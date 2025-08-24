function resolve(key: keyof NonNullable<Window["__ENV__"]>, fallback?: string | null): string | undefined {
  let value: string | undefined | null;

  value = import.meta.env[`VITE_${key}`];

  if (typeof window !== "undefined" && window.__ENV__) {
    value = window.__ENV__[key] ?? value;
  }

  value = value ?? fallback;

  if (typeof value === "undefined") {
    throw new Error(`Missing mandatory configuration: '${key}'`);
  }

  return value ?? undefined;
}

export const env = {
  BASE_PATH: resolve("BASE_PATH", null),
};
