<?php
include_once ('../../Common/Connection.php');
include_once ('../../Common/Utils.php');
include_once ('../../Entities/Response.php');
include_once ('../../Entities/Account/AccountResponse.php');
include_once ('../../Modules/AccountRepository.php');

/*
 * POST - /api/accounts/get.php
 * @name: str - [optional]
 * 
 * Return: [AccountResponse(Base)]
 */

$dbInst = RestaurantDB::GetTransient();

try {
    if (Utils::InitCheck($dbInst)) {
        $name = Utils::GetPostOrNull("name");
        $get_result = get_accounts($dbInst, $name);
        return Response::CreateSuccessResponse("Successfully fetched accounts!", $get_result);
    }

    return Response::CreateFailResponse("Invalid request!");
} finally {
    $dbInst->close();
}

function get_accounts($db, $name)
{
    $accountModule = new AccountRepository($db);
    $accounts = $accountModule->get_accounts($name);
    if ($accounts === false) {
        return [];
    } else if (!array_key_exists(0, $accounts)) {
        $accounts = [$accounts];
    }

    return array_map(
        fn($account) =>
        new AccountResponse(
            $account["username"],
            $account["fname"],
            $account["lname"],
            $account["accType"],
            $account["accStatus"]
        ),
        $accounts
    );
}
?>