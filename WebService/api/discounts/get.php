<?php
include_once ('../../Common/Connection.php');
include_once ('../../Common/Utils.php');
include_once ('../../Entities/Response.php');
include_once ('../../Entities/Discounts/DiscountResponse.php');
include_once ('../../Modules/DiscountRepository.php');

/*
 * POST - /api/discounts/get.php
 * 
 * Return: [DiscountResponse(Base)]
 */

$dbInst = RestaurantDB::GetTransient();

try {
    if (Utils::InitCheck($dbInst)) {
        $discountResult = fetch_discounts($dbInst);
        return Response::CreateSuccessResponse("success", $discountResult);
    }

    return Response::CreateFailResponse("Invalid request!");
} finally {
    $dbInst->close();
}

function fetch_discounts($db)
{
    $discountModule = new DiscountRepository($db);
    $discounts = $discountModule->get_discounts();
    if ($discounts === false) {
        return [];
    } else if (!array_key_exists(0, $discounts)) {
        $discounts = [$discounts];
    }

    return array_map(fn($discount) =>
        new DiscountResponse(
            $discount["id"],
            $discount["title"],
            $discount["description"],
            $discount["type"],
            $discount["value"],
            $discount["code"]
        ), $discounts);
}

?>