import { useQuery } from "@tanstack/react-query";
import { getDeliveries } from "../api/deliveryApi";
import type { Delivery } from "../types/Delivery";

export function useDeliveries() {

    return useQuery<Delivery[]>({

        queryKey: ["deliveries"],

        queryFn: getDeliveries,

        staleTime: 30000,

        refetchOnWindowFocus: false

    });

}