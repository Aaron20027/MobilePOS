<?php
include_once ('../../Common/Connection.php');
include_once ('../../Common/Utils.php');
include_once ('../../Entities/Response.php');
include_once ('../../Modules/AccountRepository.php');

/*
 * POST - /api/accounts/update.php
 * Description: At least one of the optional arguments must be used or the request
 * will be considered invalid.
 * @username: str - [required]
 * @password: str - [optional]
 * @fname: str - [optional]
 * @lname: str - [optional]
 * @accType: int - [optional]
 * @accStatus: int - [optional]
 * 
 * Return: [Response(Base)]
 */

$dbInst = RestaurantDB::GetTransient();
$postArgsIds = ["username", "password", "fname", "lname", "accType", "accStatus"];

try {
    if (Utils::InitCheck($dbInst)) {
        $reqArgs = array_map("Utils::GetPostOrNull", $postArgsIds);

        // Check if username is not null and if there are at least two values in array that are not null
        if (!$reqArgs[0] || count(array_filter($reqArgs, fn($v) => $v != null)) < 2) {
            return Response::CreateFailResponse("One or more arguments are invalid!");
        }

        $update_result = update_account($dbInst, $reqArgs[0], $reqArgs[1], $reqArgs[2], $reqArgs[3], $reqArgs[4], $reqArgs[5]);
        if ($update_result) {
            return Response::CreateSuccessResponse("Successfully updated account!");
        } else {
            return Response::CreateFailResponse("Failed to update account!");
        }
    }

    return Response::CreateFailResponse("Invalid request!");
} finally {
    $dbInst->close();
}

function update_account($db, $username, $password, $fname, $lname, $accType, $accStatus)
{
    $accountModule = new AccountRepository($db);
    $result = $accountModule->update_account($username, $password, $fname, $lname, $accType, $accStatus);
    return $result;
}
?>