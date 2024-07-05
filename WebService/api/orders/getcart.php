<?php
include_once ('../../Common/Connection.php');
include_once ('../../Common/Utils.php');
include_once ('../../Entities/Response.php');
include_once ('../../Entities/Order/CartItemResponse.php');
include_once ('../../Modules/OrderRepository.php');

/*
 * POST - /api/orders/getcart.php
 * @username: str - [required]
 * 
 * Return: [Response(Base)]
 */

$dbInst = RestaurantDB::GetTransient();

try {
    if (Utils::InitCheck($dbInst)) {
        $username = Utils::GetPostOrNull("username");
        if (is_null($username)) {
            return Response::CreateFailResponse("Invalid argument!");
        }

        $get_result = get_cart_items($dbInst, $username);
        if ($get_result === false)
            return Response::CreateFailResponse("Failed to fetch cart items!");
        else
            return Response::CreateSuccessResponse("Successfully fetched cart items!", $get_result);
    }

    return Response::CreateFailResponse("Invalid request!");
} finally {
    $dbInst->close();
}

function get_cart_items($db, $username)
{
    $orderModule = new OrderRepository($db);
    $result = $orderModule->GetCartItems($username);
    if ($result === false) {
        return $result;
    } else if (!array_key_exists(0, $result)) {
        return [
            new CartItemResponse(
                $result["username"],
                $result["productId"],
                $result["quantity"],
                $result["size"]
            )
        ];
    }

    $cartItems = [];
    foreach ($result as $ci) {
        // append cart item to array
        $cartItems[] = new CartItemResponse(
            $ci["username"],
            $ci["productId"],
            $ci["quantity"],
            $ci["size"]
        );
    }

    return $cartItems;
}
?>