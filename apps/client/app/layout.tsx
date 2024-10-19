import { ReactNode } from "react";
import { SessionProvider } from "next-auth/react";

type Props = {
  children: Readonly<ReactNode>;
};

export default function RootLayout({ children }: Props) {
  return (
    <html lang="en">
    <body>
    <SessionProvider refetchInterval={300}>
    {children}
    </SessionProvider>
    </body>
    </html>
  );
}
