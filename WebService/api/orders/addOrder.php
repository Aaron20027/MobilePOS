<?php
include_once ('../../Common/Connection.php');
include_once ('../../Common/Utils.php');
include_once ('../../Entities/Response.php');
include_once ('../../Modules/OrderRepository.php');

/*
 * POST - /api/orders/addPayment.php
 * @accountId: str - [required]
 * @orderTotal: float - [required]
 *
 * Return: [Response(Base)]
 */

$dbInst = RestaurantDB::GetTransient();
$postArgsIds = ["accountId", "orderTotal"];

try {
    if (Utils::InitCheck($dbInst)) {
        $reqArgs = array_map("Utils::GetPostOrNull", $postArgsIds);
        if (in_array(null, $reqArgs)) {
            return Response::CreateFailResponse("One or more arguments are missing!");
        }

        $addResult = AddCart($dbInst, $reqArgs[0], $reqArgs[1]);
        if ($addResult)
            return Response::CreateSuccessResponse("Payment is Successful!");
        else
            return Response::CreateFailResponse("Payment Failed!!");
    }

    return Response::CreateFailResponse("Invalid request!");
} finally {
    $dbInst->close();
}

function AddCart($db, $accountId, $orderTotal)
{
    $orderTotal = (float) $orderTotal;
    $orderRepo = new OrderRepository($db);
    $result = $orderRepo->AddOrder($accountId, $orderTotal);
    return $result;
}

?>