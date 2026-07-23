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

import { useDeadLetters } from "../../hooks/useDeadLetters";

export default function RecentDeadLettersTable() {

    const {

        data,

        isLoading,

        error

    } = useDeadLetters();

    if (isLoading) {

        return <CircularProgress />;

    }

    if (error) {

        return (

            <Typography color="error">

                Unable to load dead letters.

            </Typography>

        );

    }

    return (

        <TableContainer component={Paper}>

            <Table>

                <TableHead>

                    <TableRow>

                        <TableCell>ID</TableCell>

                        <TableCell>Channel</TableCell>

                        <TableCell>Provider</TableCell>

                        <TableCell>Status</TableCell>

                        <TableCell>Failure Reason</TableCell>

                        <TableCell>Created</TableCell>

                    </TableRow>

                </TableHead>

                <TableBody>

                    {(data ?? []).map((deadLetter) => (

                        <TableRow key={deadLetter.id}>

                            <TableCell>

                                {deadLetter.id}

                            </TableCell>

                            <TableCell>

                                {deadLetter.channel}

                            </TableCell>

                            <TableCell>

                                {deadLetter.provider}

                            </TableCell>

                            <TableCell>

                                {deadLetter.status}

                            </TableCell>

                            <TableCell>

                                {deadLetter.failureReason}

                            </TableCell>

                            <TableCell>

                                {new Date(
                                    deadLetter.createdAt
                                ).toLocaleString()}

                            </TableCell>

                        </TableRow>

                    ))}

                </TableBody>

            </Table>

        </TableContainer>

    );

}