<?php
include_once ('../../Common/Connection.php');
include_once ('../../Common/Utils.php');
include_once ('../../Entities/Response.php');
include_once ('../../Entities/Products/FetchProductResponse.php');
include_once ('../../Modules/Products.php');

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
    $productModule = new Products($db);
    $productItemsDB = $productModule->get_products($categoryId);
    if ($productItemsDB === false) {
        return [];
    } else if (!array_key_exists(0, $productItemsDB)) {
        $productItemsDB = [$productItemsDB];
    }
    
    return array_map(fn($productItemDB) =>
        new FetchProductResponse(
            $productItemDB["ProductId"],
            $productItemDB["ProductName"],
            $productItemDB["ProductDescription"],
            $productItemDB["Price"],
            $productItemDB["ProductCategory"],
            $productItemDB["ProductImage"]
        ), $productItemsDB);
}

?>