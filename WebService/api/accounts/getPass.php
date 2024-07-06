<?php
include_once ('../../Common/Connection.php');
include_once ('../../Common/Utils.php');
include_once ('../../Entities/Response.php');
include_once ('../../Entities/Account/PasswordResponse.php');
include_once ('../../Modules/AccountRepository.php');

/*
 * POST - /api/accounts/getPass.php
 * @username: str - [required]
 *
 * Return: [PasswordResponse(Base)]
 */

$dbInst = RestaurantDB::GetTransient();

try {
    if (Utils::InitCheck($dbInst)) {
        $username = Utils::GetPostOrNull("username");
        if (is_null($username)) {
            return Response::CreateFailResponse("Invalid argument!");
        }

        $pass_result = get_password($dbInst, $username);
        if ($pass_result) {
            return Response::CreateSuccessResponse("Successfully get Password!");
        } else {
            return Response::CreateFailResponse("Failed to get Password!");
        }
    }

    return Response::CreateFailResponse("Invalid request!");
} finally {
    $dbInst->close();
}

function get_password($db, $username)
{
    $queryResult = $db->query("SELECT * FROM `accounttbl` WHERE `acc_User` = ?", "s", $username);

    return new PasswordResponse(
            $queryResult["acc_Pass"]
        );
}


?>