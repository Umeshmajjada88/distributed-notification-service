export interface Notification {

    id: number;

    eventId: string;

    userId: number;

    channel: string;

    status: string;

    subject: string;

    message: string;

    retryCount: number;

    scheduledAt: string | null;

    createdAt: string;

    updatedAt: string;

}