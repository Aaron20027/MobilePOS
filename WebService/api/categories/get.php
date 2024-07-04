<?php
include_once ('../../Common/Connection.php');
include_once ('../../Common/Utils.php');
include_once ('../../Entities/Response.php');
include_once ('../../Entities/Category/CategoryResponse.php');
include_once ('../../Modules/CategoryRepository.php');

/*
 * POST - /api/categories/get.php
 * @id: int - [optional]
 * Return: [CategoryResponse(Base)]
 */

$dbInst = RestaurantDB::GetTransient();

try {
    if (Utils::InitCheck($dbInst)) {
        $categoriesResult = fetch_categories($dbInst);
        return Response::CreateSuccessResponse("success", $categoriesResult);
    }

    return Response::CreateFailResponse("Invalid request!");
} finally {
    $dbInst->close();
}

function fetch_categories($db)
{

    $categoriesModule = new CategoryRepository($db);
    $categories = $categoriesModule->get_category();
    if ($categories === false) {
        return [];
    } else if (!array_key_exists(0, $categories)) {
        $categories = [$categories];
    }

    echo $categories["categoryId"];

    return array_map(fn($categories) =>
        new CategoryResponse(
            $categories["categoryId"],
            $categories["categoryName"]
        ), $categories);
}
?>