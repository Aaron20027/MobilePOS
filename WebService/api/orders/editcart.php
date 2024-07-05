<?php
include_once ('../../Common/Connection.php');
include_once ('../../Common/Utils.php');
include_once ('../../Entities/Response.php');
include_once ('../../Modules/OrderRepository.php');

/*
 * POST - /api/orders/editcart.php
 * @username: str - [required]
 * @prodId: int - [optional]
 * @quantity: int - [optional]
 * @size: int - [optional]
 * 
 * Return: [Response(Base)]
 */

$dbInst = RestaurantDB::GetTransient();
$postArgsIds = ["username", "prodId", "quantity", "size"];

try {
    if (Utils::InitCheck($dbInst)) {
        $reqArgs = array_map("Utils::GetPostOrNull", $postArgsIds);
        if (in_array(null, $reqArgs)) {
            return Response::CreateFailResponse("One or more arguments are missing!");
        }

        $editResult = EditCart($dbInst, $reqArgs[0], $reqArgs[1], $reqArgs[2], $reqArgs[3]);
        if ($editResult)
            return Response::CreateSuccessResponse("Successfully edited item in cart!");
        else
            return Response::CreateFailResponse("Failed to edit item in cart!");
    }

    return Response::CreateFailResponse("Invalid request!");
} finally {
    $dbInst->close();
}

function EditCart($db, $username, $prodId, $quantity, $size)
{
    $orderRepo = new OrderRepository($db);
    $result = $orderRepo->EditCart($username, $prodId, $quantity, $size);
    return $result;
}

?>