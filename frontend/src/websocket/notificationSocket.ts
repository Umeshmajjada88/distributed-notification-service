import { createSocketClient } from "./socket";
import type { NotificationStatusEvent } from "../types/NotificationStatusEvent";

const client = createSocketClient();

export function connectNotificationSocket(

    onMessage: (event: NotificationStatusEvent) => void

) {

    client.onConnect = () => {

        console.log("Connected to WebSocket");

        client.subscribe(

            "/topic/notifications",

            message => {

                const event: NotificationStatusEvent =
                    JSON.parse(message.body);

                console.log("Incoming Event:", event);

                onMessage(event);

            }

        );

    };

    client.activate();

}

export function disconnectNotificationSocket() {

    client.deactivate();

}