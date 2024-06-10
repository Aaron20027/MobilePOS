<?php
include_once ('../../Common/Connection.php');
include_once ('../../Common/Utils.php');
include_once ('../../Entities/Response.php');
include_once ('../../Entities/Products/ProductResponse.php');
include_once ('../../Modules/ProductRepository.php');

/*
 * POST - /api/products/get.php
 * @category_id: int - [optional]
 * 
 * Return: [Response(ProductResponse)]
 */

$dbInst = RestaurantDB::GetTransient();

try {
    if (Utils::InitCheck($dbInst)) {
        $categoryId = Utils::GetPostOrNull('category_id');
        $fetchProductResponse = fetch_products($dbInst, $categoryId);
        return Response::CreateSuccessResponse("success", $fetchProductResponse);
    }

    return Response::CreateFailResponse("Invalid request!");
} finally {
    $dbInst->close();
}

function fetch_products($db, $categoryId)
{
    $productModule = new ProductRepository($db);
    $productItemsDB = $productModule->get_products_by_category($categoryId, true); // Available value set to true by default
    if ($productItemsDB === false) {
        return [];
    } else if (!array_key_exists(0, $productItemsDB)) {
        $productItemsDB = [$productItemsDB];
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
        ), $productItemsDB);
}

?>