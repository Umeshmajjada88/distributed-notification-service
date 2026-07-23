export interface DeadLetter {

    id: number;

    deliveryId: number;

    notificationId: number;

    eventId: string;

    channel: string;

    provider: string;

    attemptCount: number;

    failureReason: string;

    status: string;

    createdAt: string;

}