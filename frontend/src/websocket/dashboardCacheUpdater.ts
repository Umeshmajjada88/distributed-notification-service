import { QueryClient } from "@tanstack/react-query";

import type { NotificationStatusEvent } from "../types/NotificationStatusEvent";
import type { Delivery } from "../types/Delivery";

export function updateDashboardCache(

    queryClient: QueryClient,

    event: NotificationStatusEvent

) {

    queryClient.setQueryData<Delivery[]>(

        ["deliveries"],

        (oldDeliveries) => {

            if (!oldDeliveries) {

                return oldDeliveries;

            }

            return oldDeliveries.map(delivery =>

                delivery.id === event.deliveryId

                    ? {

                        ...delivery,

                        status: event.status

                    }

                    : delivery

            );

        }

    );

}