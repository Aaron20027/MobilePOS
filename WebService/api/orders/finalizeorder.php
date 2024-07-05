<?php
include_once ('../../Common/Connection.php');
include_once ('../../Common/Utils.php');
include_once ('../../Entities/Response.php');
include_once ('../../Modules/OrderRepository.php');

/*
 * POST - /api/orders/finalizeorder.php
 * @username: str - [required]
 * @discount_id: int - [optional]
 * 
 * Return: [Response(Base)]
 */

$dbInst = RestaurantDB::GetTransient();

try {
    if (Utils::InitCheck($dbInst)) {
        $username = Utils::GetPostOrNull("username");
        $discountId = Utils::GetPostOrNull("discount_id");
        $finalizeOrderResult = finalize_order($dbInst, $username, $discountId);
        if ($finalizeOrderResult) {
            return Response::CreateSuccessResponse("Successfully finalized order!");
        } else {
            return Response::CreateFailResponse("Failed finalizing order!");
        }
    }

    return Response::CreateFailResponse("Invalid request!");
} finally {
    $dbInst->close();
}

function finalize_order($db, $username, $discountId)
{
    $orderRepository = new OrderRepository($db);
    return $orderRepository->FinalizeOrder($username, $discountId);
}
?>