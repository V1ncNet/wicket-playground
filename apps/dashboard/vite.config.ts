import { tanstackRouter } from "@tanstack/router-plugin/vite";
import react from "@vitejs/plugin-react";
import { defineConfig } from "vite";

// https://vite.dev/config/
export default defineConfig({
  plugins: [
    tanstackRouter({
      target: "react",
      autoCodeSplitting: false,
    }),
    react(),
  ],
  build: {
    rollupOptions: {
      input: "./src/main.tsx",
      output: {
        entryFileNames: "dashboard.js",
        assetFileNames: "[name].[ext]",
      },
    },
  },
});
