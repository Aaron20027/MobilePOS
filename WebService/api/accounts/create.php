<?php
include_once ('../../Common/Connection.php');
include_once ('../../Common/Utils.php');
include_once ('../../Entities/Response.php');
include_once ('../../Modules/AccountRepository.php');

/*
 * POST - /api/accounts/create.php
 * @username: str - [required]
 * @password: str - [required]
 * @fname: str - [required]
 * @lname: str - [required]
 * @accType: int - [required]
 * 
 * Return: [Response(Base)]
 */

$dbInst = RestaurantDB::GetTransient();
$postArgsIds = ["username", "password", "fname", "lname", "accType"];

try {
    if (Utils::InitCheck($dbInst)) {
        $reqArgs = array_map("Utils::GetPostOrNull", $postArgsIds);
        if (in_array(null, $reqArgs)) {
            return Response::CreateFailResponse("One or more arguments are missing!");
        }

        if (create_account($dbInst, $reqArgs[0], $reqArgs[1], $reqArgs[2], $reqArgs[3], $reqArgs[4])) {
            return Response::CreateSuccessResponse("Successfully created account!");
        } else {
            return Response::CreateFailResponse("Failed creating account!");
        }
    }

    return Response::CreateFailResponse("Invalid request!");
} finally {
    $dbInst->close();
}

function create_account($db, $username, $password, $fname, $lname, $accType)
{
    $accountModule = new AccountRepository($db);
    return $accountModule->create_account($username, md5($password), $fname, $lname, $accType);
}

?>