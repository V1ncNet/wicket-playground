import { ReactNode } from "react";

type Props = {
  children: Readonly<ReactNode>;
};

export default function RootLayout({ children }: Props) {
  return (
    <html lang="en">
    <body>
    {children}
    </body>
    </html>
  );
}
