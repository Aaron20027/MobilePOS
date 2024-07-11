<?php
include_once ('../../Common/Connection.php');
include_once ('../../Common/Utils.php');
include_once ('../../Entities/Response.php');
include_once ('../../Entities/Reports/SalesReportResponse1.php');
include_once ('../../Modules/ReportRepository.php');

/*
 * POST - /api/categories/get.php
 * @id: int - [optional]
 * Return: [SalesReportResponse1(Base)]
 */

$dbInst = RestaurantDB::GetTransient();

try {
    if (Utils::InitCheck($dbInst)) {
        $salesResult = fetch_sales($dbInst);
        return Response::CreateSuccessResponse("success", $salesResult);
    }

    return Response::CreateFailResponse("Invalid request!");
} finally {
    $dbInst->close();
}

function fetch_sales($db)
{

    $reportModule = new ReportRepository($db);
    $reports = $reportModule->SalesInfo();
    if ($reports === false) {
        return [];
    } else if (!array_key_exists(0, $reports)) {
        $reports = [$reports];
    }



    return array_map(fn($reports) =>
        new SalesReportResponse1(
            $reports["order_total"],
            $reports["date"]
        ), $reports);
}
?>