<?php
include_once ('../../Common/Connection.php');
include_once ('../../Common/Utils.php');
include_once ('../../Entities/Response.php');
include_once ('../../Modules/Products.php');
include_once ('../../Entities/Products/ProductResponse.php');

/*
 * POST - /api/products/search.php
 * @name: str - [required]
 * 
 * Return: [Response(ProductResponse)]
 */

$dbInst = RestaurantDB::GetTransient();

try {
    if (Utils::InitCheck($dbInst)) {
        $name = Utils::GetPostOrNull("name");
        if ($name) {
            $search_result = search_product($dbInst, $name);
            return Response::CreateSuccessResponse("success", $search_result);
        }
    }

    return Response::CreateFailResponse("Invalid request!");
} finally {
    $dbInst->close();
}

function search_product($db, $name)
{
    $productModule = new Products($db);
    $search_result = $productModule->search_product($name);
    if ($search_result === false) {
        return [];
    } else if (!array_key_exists(0, $search_result)) {
        $search_result = [$search_result];
    }

    return array_map(fn($productItemDB) =>
        new ProductResponse(
            $productItemDB["ProductId"],
            $productItemDB["ProductName"],
            $productItemDB["ProductDescription"],
            $productItemDB["Price"],
            $productItemDB["ProductCategory"],
            $productItemDB["ProductImage"],
            $productItemDB["Availability"]
        ), $search_result);
}

?>