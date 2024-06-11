<?php
include_once ('../../Common/Connection.php');
include_once ('../../Common/Utils.php');
include_once ('../../Entities/Response.php');
include_once ('../../Modules/AccountRepository.php');

/*
 * POST - /api/accounts/delete.php
 * @username: str - [required]
 * 
 * Return: [Response(Base)]
 */

$dbInst = RestaurantDB::GetTransient();

try {
    if (Utils::InitCheck($dbInst)) {
        $username = Utils::GetPostOrNull("username");
        if (is_null($username)) {
            return Response::CreateFailResponse("Invalid argument!");
        }

        $delete_result = delete_account($dbInst, $username);
        if ($delete_result) {
            return Response::CreateSuccessResponse("Successfully deleted account!");
        } else {
            return Response::CreateFailResponse("Failed to delete account!");
        }
    }

    return Response::CreateFailResponse("Invalid request!");
} finally {
    $dbInst->close();
}

function delete_account($db, $username)
{
    $accountModule = new AccountRepository($db);
    $result = $accountModule->delete_account_by_username($username);
    return $result;
}
?>