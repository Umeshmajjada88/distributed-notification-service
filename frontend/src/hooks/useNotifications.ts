import { useQuery } from "@tanstack/react-query";
import { getNotifications } from "../api/notificationApi";
import type { Notification } from "../types/Notification";

export function useNotifications() {

    return useQuery<Notification[]>({

        queryKey: ["notifications"],

        queryFn: getNotifications,

        staleTime: 30000,

        refetchOnWindowFocus: false

    });

}