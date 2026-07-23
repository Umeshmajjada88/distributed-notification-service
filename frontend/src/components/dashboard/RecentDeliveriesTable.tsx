import {
    CircularProgress,
    Paper,
    Table,
    TableBody,
    TableCell,
    TableContainer,
    TableHead,
    TableRow,
    Typography
} from "@mui/material";

import { useDeliveries } from "../../hooks/useDeliveries";

export default function RecentDeliveriesTable() {

    const {
        data,
        isLoading,
        error
    } = useDeliveries();

    if (isLoading) {
        return <CircularProgress />;
    }

    if (error) {
        return (
            <Typography color="error">
                Unable to load deliveries.
            </Typography>
        );
    }

    return (

        <TableContainer component={Paper}>

            <Table>

                <TableHead>

                    <TableRow>

                        <TableCell>ID</TableCell>

                        <TableCell>Provider</TableCell>

                        <TableCell>Status</TableCell>

                        <TableCell>Attempts</TableCell>

                        <TableCell>Updated</TableCell>

                    </TableRow>

                </TableHead>

                <TableBody>

                    {(data ?? []).map((delivery) => (

                        <TableRow key={delivery.id}>

                            <TableCell>
                                {delivery.id}
                            </TableCell>

                            <TableCell>
                                {delivery.provider}
                            </TableCell>

                            <TableCell>
                                {delivery.status}
                            </TableCell>

                            <TableCell>
                                {delivery.attemptCount}
                            </TableCell>

                            <TableCell>
                                {new Date(
                                    delivery.updatedAt
                                ).toLocaleString()}
                            </TableCell>

                        </TableRow>

                    ))}

                </TableBody>

            </Table>

        </TableContainer>

    );

}