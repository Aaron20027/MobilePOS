<?php
include_once ('../../Common/Connection.php');
include_once ('../../Common/Utils.php');
include_once ('../../Entities/Response.php');
include_once ('../../Modules/OrderRepository.php');

/*
 * POST - /api/orders/removecart.php
 * @username: str - [required]
 * @prodId: int - [required]
 * @quantity: int - [required]
 * @size: int - [required]
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

        $removeResult = RemoveCart($dbInst, $reqArgs[0], $reqArgs[1], $reqArgs[2], $reqArgs[3]);
        if ($removeResult)
            return Response::CreateSuccessResponse("Successfully removed item(s) to cart!");
        else
            return Response::CreateFailResponse("Failed to remove item to cart!");
    }

    return Response::CreateFailResponse("Invalid request!");
} finally {
    $dbInst->close();
}

function RemoveCart($db, $username, $prodId, $quantity, $size)
{
    $orderRepo = new OrderRepository($db);
    $result = $orderRepo->RemoveCart($username, $prodId, $quantity, $size);
    return $result;
}

?>