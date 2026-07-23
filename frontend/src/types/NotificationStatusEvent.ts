export interface NotificationStatusEvent {

    notificationId: number;

    deliveryId: number;

    eventId?: string;

    status: string;

    channel: string;

    provider: string;

    message: string;

    timestamp: string;

}