<?php
include_once ('../../Common/Connection.php');
include_once ('../../Common/Utils.php');
include_once ('../../Entities/Response.php');
include_once ('../../Modules/DiscountRepository.php');

/*
 * POST - /api/discounts/update.php
 * Description: At least one of the optional arguments must be used or the request
 * will be considered invalid.
 * @id: int - [required]
 * @title: str - [optional]
 * @desc: str - [optional]
 * @type: int - [optional]
 * @value: int - [optional]
 * 
 * Return: [Response(Base)]
 */

$dbInst = RestaurantDB::GetTransient();
$postArgsIds = ["id", "title", "desc", "type", "value"];

try {
    if (Utils::InitCheck($dbInst)) {
        $reqArgs = array_map("Utils::GetPostOrNull", $postArgsIds);

        // Check if id is not null and if there are at least two values in array that are not null
        if (!$reqArgs[0] || count(array_filter($reqArgs, fn($v) => $v != null)) < 2) {
            return Response::CreateFailResponse("One or more arguments are invalid!");
        }

        $update_result = update_discount($dbInst, $reqArgs[0], $reqArgs[1], $reqArgs[2], $reqArgs[3], $reqArgs[4]);
        if ($update_result) {
            return Response::CreateSuccessResponse("Successfully updated discount!");
        } else {
            return Response::CreateFailResponse("Failed to update discount!");
        }
    }

    return Response::CreateFailResponse("Invalid request!");
} finally {
    $dbInst->close();
}

function update_discount($db, $id, $title, $desc, $t, $va)
{
    $discountModule = new DiscountRepository($db);
    $result = $discountModule->update_discount($id, $title, $desc, $t, $va);
    return $result;
}
?>