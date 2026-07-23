import api from "./axios";
import { API_CONFIG } from "../config/api";
import type { DeadLetter } from "../types/DeadLetter";

export async function getDeadLetterStatistics() {

    const response = await api.get(
        `${API_CONFIG.delivery}/api/v1/dead-letters/statistics`
    );

    return response.data;

}

export async function getDeadLetters(): Promise<DeadLetter[]> {

    const response = await api.get(
        `${API_CONFIG.delivery}/api/v1/dead-letters`
    );

    return response.data;

}