import type { ReactNode } from "react";

import { useNotificationSocket } from "../hooks/useNotificationSocket";

interface Props {

    children: ReactNode;

}

export default function WebSocketProvider({

    children

}: Props) {

    useNotificationSocket();

    return <>{children}</>;

}