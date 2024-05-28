<?php
include_once ('../Common/Connection.php');
include_once ('../Common/Utils.php');
include_once ('../Entities/Response.php');
include_once ('../Entities/Auth/LoginResponse.php');

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

    if (is_null($queryResult)) {
        return "Failed executing query!";
    }

    // Error message reference: https://stackoverflow.com/questions/14922130/which-error-message-is-better-when-users-entered-a-wrong-password
    if ($queryResult === false || !Utils::ArrayHasKeyAndEqualTo($queryResult, "acc_Pass", $password)) {
        return "You have entered an invalid username or password.";
    }

    return new LoginResponse(
        $queryResult["acc_User"],
        "random session token", // TODO: Implement session system 
        $queryResult["acc_Fname"],
        $queryResult["acc_Lname"],
        $queryResult["acc_Email"],
        $queryResult["acc_Type"],
        $queryResult["acc_Address"],
        $queryResult["acc_Image"]
    );
}

?>