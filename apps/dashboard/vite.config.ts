import { tanstackRouter } from "@tanstack/router-plugin/vite";
import react from "@vitejs/plugin-react";
import { defineConfig, loadEnv, type UserConfig } from "vite";

// https://vite.dev/config/
export default ({ mode }: { mode: string }): UserConfig => {
  process.env = { ...process.env, ...loadEnv(mode, process.cwd()) };

  return defineConfig({
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
    server: {
      proxy: {
        "/dashboard-config.js": {
          target: process.env.VITE_SERVER_CONFIG_BASE_URL,
          changeOrigin: true,
        },
      },
    },
  });
}
