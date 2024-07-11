<?php
include_once ('../../Common/Connection.php');
include_once ('../../Common/Utils.php');
include_once ('../../Entities/Response.php');
include_once ('../../Entities/Reports/EmployeeReportResponse1.php');
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
            $reports = $reportModule->EmployeeInfo();
            if ($reports === false) {
                return [];
            } else if (!array_key_exists(0, $reports)) {
                $reports = [$reports];
            }



            return array_map(fn($reports) =>
                new EmployeeReportResponse1(
                    $reports["acc1"],
                    $reports["disCount"]
                ), $reports);
}
?>