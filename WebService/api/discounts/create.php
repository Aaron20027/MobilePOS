<?php
include_once ('../../Common/Connection.php');
include_once ('../../Common/Utils.php');
include_once ('../../Entities/Response.php');
include_once ('../../Modules/DiscountRepository.php');

/*
 * POST - /api/discounts/create.php
 * @title: str - [required]
 * @desc: str - [required]
 * @type: int - [required]
 * @value: float - [required]
 * @avail: int - [required]
 * Return: [Response(Base)]
 */

$dbInst = RestaurantDB::GetTransient();
$postArgsIds = ["title", "desc", "type", "value", "avail"];

try {
    if (Utils::InitCheck($dbInst)) {
        $reqArgs = array_map("Utils::GetPostOrNull", $postArgsIds);
        if (in_array(null, $reqArgs)) {
            return Response::CreateFailResponse("One or more arguments are missing!".$postArgsIds);
        }

        if (create_discount($dbInst, $reqArgs[0], $reqArgs[1], $reqArgs[2], $reqArgs[3], $reqArgs[4])) {
            return Response::CreateSuccessResponse("Successfully created discount!");
        } else {
            return Response::CreateFailResponse("Failed creating discount!");
        }
    }

    return Response::CreateFailResponse("Invalid request!");
} finally {
    $dbInst->close();
}

function create_discount($db, $title, $desc, $t, $val, $avail)
{
    $discountModule = new DiscountRepository($db);
    return $discountModule->create_discount($title, $desc, $t, $val, $avail);
}

?>