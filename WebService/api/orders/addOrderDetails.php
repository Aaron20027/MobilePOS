<?php
include_once ('../../Common/Connection.php');
include_once ('../../Common/Utils.php');
include_once ('../../Entities/Response.php');
include_once ('../../Modules/OrderRepository.php');

/*
 * POST - /api/orders/addOrderDetails.php
 * @productId: int - [required]
 * @orderQty: int - [required]
 * @orderCost: float - [required]
 * Return: [Response(Base)]
 */

$dbInst = RestaurantDB::GetTransient();
$postArgsIds = ["productId", "orderQty", "orderCost"];

try {
    if (Utils::InitCheck($dbInst)) {
        $reqArgs = array_map("Utils::GetPostOrNull", $postArgsIds);
        if (in_array(null, $reqArgs)) {
            return Response::CreateFailResponse("One or more arguments are missing!");
        }

        $addResult = AddCart($dbInst, $reqArgs[0], $reqArgs[1], $reqArgs[2]);
        if ($addResult)
            return Response::CreateSuccessResponse("Payment is Successful!");
        else
            return Response::CreateFailResponse("Payment Failed!!");
    }

    return Response::CreateFailResponse("Invalid request!");
} finally {
    $dbInst->close();
}

function AddCart($db, $productId, $orderQty, $orderCost)
{
    $orderRepo = new OrderRepository($db);
    $result = $orderRepo->AddOrderDetails($productId, $orderQty, $orderCost);
    return $result;
}

?>