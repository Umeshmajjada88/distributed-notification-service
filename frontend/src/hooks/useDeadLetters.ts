import { useQuery } from "@tanstack/react-query";
import { getDeadLetters } from "../api/deadLetterApi";
import type { DeadLetter } from "../types/DeadLetter";

export function useDeadLetters() {

    return useQuery<DeadLetter[]>({

        queryKey: ["deadLetters"],

        queryFn: getDeadLetters,

        staleTime: 30000,

        refetchOnWindowFocus: false

    });

}