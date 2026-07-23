import type { ReactNode } from "react";
import { ThemeProvider } from "@mui/material/styles";
import { CssBaseline } from "@mui/material";
import { QueryClient, QueryClientProvider } from "@tanstack/react-query";
import theme from "../theme/theme";
import WebSocketProvider from "./WebSocketProvider";

const queryClient = new QueryClient();

interface Props {
    children: ReactNode;
}

export default function AppProviders({ children }: Props) {

    return (
        <QueryClientProvider client={queryClient}>
            <ThemeProvider theme={theme}>
                <CssBaseline />
                <WebSocketProvider>
                    {children}
                </WebSocketProvider>
            </ThemeProvider>
        </QueryClientProvider>
    );
}