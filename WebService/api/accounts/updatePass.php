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
 *
 * Return: [Response(Base)]
 */

$dbInst = RestaurantDB::GetTransient();
$postArgsIds = ["username", "password"];

try {
    if (Utils::InitCheck($dbInst)) {
        $reqArgs = array_map("Utils::GetPostOrNull", $postArgsIds);

        // Check if username is not null and if there are at least two values in array that are not null
        if (!$reqArgs[0] || count(array_filter($reqArgs, fn($v) => $v != null)) < 2) {
            return Response::CreateFailResponse("One or more arguments are invalid!");
        }

        $update_result = update_account($dbInst, $reqArgs[0], $reqArgs[1];
        if ($update_result) {
            return Response::CreateSuccessResponse("Successfully updated password!");
        } else {
            return Response::CreateFailResponse("Failed to update password!");
        }
    }

    return Response::CreateFailResponse("Invalid request!");
} finally {
    $dbInst->close();
}

function update_password($db, $username, $password)
{
    $accountModule = new AccountRepository($db);
    $result = $accountModule->update_password($username, $password);
    return $result;
}
?>