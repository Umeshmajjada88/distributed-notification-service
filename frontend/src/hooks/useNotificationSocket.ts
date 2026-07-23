import { useEffect } from "react";
import { useQueryClient } from "@tanstack/react-query";

import {
    connectNotificationSocket,
    disconnectNotificationSocket
} from "../websocket/notificationSocket";

import { updateDashboardCache } from "../websocket/dashboardCacheUpdater";

export function useNotificationSocket() {

    const queryClient = useQueryClient();

    useEffect(() => {

        connectNotificationSocket((event) => {

            console.log("WebSocket Event:", event);

            updateDashboardCache(
                queryClient,
                event
            );

        });

        return () => {

            disconnectNotificationSocket();

        };

    }, [queryClient]);

}