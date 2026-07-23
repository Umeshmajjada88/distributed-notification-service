import { Client } from "@stomp/stompjs";

export function createSocketClient() {

    return new Client({

        brokerURL: "ws://localhost:8082/ws",

        reconnectDelay: 5000,

        debug: (message) => {
            console.log("[STOMP]", message);
        }

    });

}