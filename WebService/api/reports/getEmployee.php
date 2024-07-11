<?php
include_once ('../../Common/Connection.php');
include_once ('../../Common/Utils.php');
include_once ('../../Entities/Response.php');
include_once ('../../Entities/Reports/EmployeeReportResponse.php');
include_once ('../../Modules/ReportRepository.php');

/*
 * POST - /api/categories/get.php
 * @id: int - [optional]
 * Return: [EmployeeReportResponse(Base)]
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

        $BestPerform = $reportModule->BestEmployee();
        $LeastEmployee = $reportModule->LeastPerforming();
        $Avg = $reportModule->AverageEmployeeSales();


        $reports = [];
        $reports[] = new EmployeeReportResponse(
            $BestPerform["acc"],
            $BestPerform["disCount"],
            $LeastEmployee["acc1"],
            $Avg["avgOrdersPerEmployee"]
        );

        return $reports;
}
?>