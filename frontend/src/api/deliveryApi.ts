import api from "./axios";
import { API_CONFIG } from "../config/api";
import type { Delivery } from "../types/Delivery";

export async function getDeliveryStatistics() {

    const response = await api.get(
        `${API_CONFIG.delivery}/api/v1/deliveries/statistics`
    );

    return response.data;

}

export async function getDeliveries(): Promise<Delivery[]> {

    const response = await api.get(
        `${API_CONFIG.delivery}/api/v1/deliveries`
    );

    return response.data;

}