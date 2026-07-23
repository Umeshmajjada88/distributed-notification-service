export interface Delivery {

    id: number;

    eventId: string;

    notificationId: number;

    provider: string;

    status: string;

    attemptCount: number;

    failureReason: string | null;

    nextRetryAt: string | null;

    createdAt: string;

    updatedAt: string;

}