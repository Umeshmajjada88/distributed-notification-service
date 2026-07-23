import api from "./axios";
import { API_CONFIG } from "../config/api";
import type { Notification } from "../types/Notification";

console.log("Notification API =", API_CONFIG.notification);

export async function getNotifications(): Promise<Notification[]> {

    const url =
        `${API_CONFIG.notification}/api/v1/notifications`;

    const response = await api.get(url);

    return response.data.data;

}

export async function getNotificationStatistics() {

    const url = `${API_CONFIG.notification}/api/v1/notifications/statistics`;

    console.log("Calling:", url);

    const response = await api.get(url);

    return response.data;
}