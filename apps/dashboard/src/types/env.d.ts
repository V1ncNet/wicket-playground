export {};

declare global {

  interface Window {
    __ENV__?: {
      [key: string]: string | undefined;
    };
  }
}
