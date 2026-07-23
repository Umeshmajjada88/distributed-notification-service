import Box from "@mui/material/Box";
import Typography from "@mui/material/Typography";

import StatsGrid from "../../components/dashboard/StatsGrid";
import RecentNotificationsTable from "../../components/dashboard/RecentNotificationsTable";
import RecentDeliveriesTable from "../../components/dashboard/RecentDeliveriesTable";
import RecentDeadLettersTable from "../../components/dashboard/RecentDeadLettersTable";

export default function DashboardPage() {

    return (

        <>

            <Typography
                variant="h4"
                gutterBottom>

                Dashboard

            </Typography>

            <StatsGrid />

            <Box sx={{ mt: 4 }}>

                <Typography
                    variant="h5"
                    gutterBottom>

                    Recent Notifications

                </Typography>

                <RecentNotificationsTable />

            </Box>

            <Box sx={{ mt: 4 }}>

                <Typography
                    variant="h5"
                    gutterBottom>

                    Recent Deliveries

                </Typography>

                <RecentDeliveriesTable />

            </Box>

            <Box sx={{ mt: 4 }}>

                <Typography
                    variant="h5"
                    gutterBottom>

                    Dead Letter Queue

                </Typography>

                <RecentDeadLettersTable />

            </Box>

        </>

    );

}