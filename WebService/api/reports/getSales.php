<?php
include_once ('../../Common/Connection.php');
include_once ('../../Common/Utils.php');
include_once ('../../Entities/Response.php');
include_once ('../../Entities/Reports/SalesReportResponse.php');
include_once ('../../Modules/ReportRepository.php');

/*
 * POST - /api/categories/get.php
 * @id: int - [optional]
 * Return: [SalesReportResponse(Base)]
 */

$dbInst = RestaurantDB::GetTransient();

try {
    if (Utils::InitCheck($dbInst)) {
        $report = fetch_report($dbInst);
        return Response::CreateSuccessResponse("success", $report);
    }

    return Response::CreateFailResponse("Invalid request!");
} finally {
    $dbInst->close();
}

function fetch_report($db)
{

    $reportModule = new ReportRepository($db);

        $transactionCount = $reportModule->getTransaction();
        $transactionSales = $reportModule->getSales();
        $avgSales = $reportModule->avgSales();

        $reports = [];
        $reports[] = new SalesReportResponse(
            $transactionCount,
            $transactionSales,
            $avgSales
        );

        return $reports;
}
?>