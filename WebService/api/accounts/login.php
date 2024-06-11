<?php
include_once ('../../Common/Connection.php');
include_once ('../../Common/Utils.php');
include_once ('../../Entities/Response.php');
include_once ('../../Modules/SessionRepository.php');
include_once ('../../Entities/Account/LoginResponse.php');

/*
 * POST - /api/accounts/login.php
 * @username: str - [required]
 * @password: str - [required and hashed using MD5]
 * 
 * Return: [LoginResponse(Base)]
 */

$dbInst = RestaurantDB::GetTransient();

try {
    if (Utils::InitCheck($dbInst)) {
        $username = Utils::GetPostOrNull('username');
        $password = Utils::GetPostOrNull('password'); // hashed value

        // check if we have valid args
        if ($username && $password) {
            $loginResult = login_user($dbInst, $username, $password);
            if ($loginResult instanceof LoginResponse) {
                return Response::CreateSuccessResponse("success", $loginResult);
            } else {
                return Response::CreateFailResponse($loginResult);
            }
        }
    }

    return Response::CreateFailResponse("Invalid request!");
} finally {
    $dbInst->close();
}

function login_user($db, $username, $password)
{
    $queryResult = $db->query("SELECT * FROM `accounttbl` WHERE `acc_User` = ?", "s", $username);

    // Error message reference: https://stackoverflow.com/questions/14922130/which-error-message-is-better-when-users-entered-a-wrong-password
    if ($queryResult === false || !Utils::ArrayHasKeyAndEqualTo($queryResult, "acc_Pass", $password)) {
        return "You have entered an invalid username or password.";
    }

    //TODO: Check if account is enabled or disabled

    $sessionRespository = new SessionRepository($db);
    $currentIP = $_SERVER['REMOTE_ADDR'];
    $hasSession = $sessionRespository->check_session($username);
    $sessionToken = $sessionRespository->validate_session($username, $currentIP);

    // create or update session if `$hasSession` and `$sessionToken` do not match
    if ($hasSession != $sessionToken) {
        $sessionRespository->update_session($sessionToken, $username, $currentIP, $hasSession);
    }

    return new LoginResponse(
        $queryResult["acc_User"],
        $sessionToken,
        $queryResult["acc_Fname"],
        $queryResult["acc_Lname"],
        $queryResult["acc_Type"]
    );
}

?>