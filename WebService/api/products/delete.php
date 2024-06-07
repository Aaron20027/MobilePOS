<?php
include_once ('../../Common/Connection.php');
include_once ('../../Common/Utils.php');
include_once ('../../Entities/Response.php');
include_once ('../../Modules/ProductRepository.php');

/*
 * POST - /api/products/delete.php
 * @id: int - [required]
 * 
 * Return: [Response(Base)]
 */

$dbInst = RestaurantDB::GetTransient();

try {
    if (Utils::InitCheck($dbInst)) {
        $productId = Utils::GetPostOrNull("id");
        if (is_null($productId)) {
            return Response::CreateFailResponse("Invalid argument!");
        }

        $delete_result = delete_product($dbInst, $productId);
        if ($delete_result) {
            return Response::CreateSuccessResponse("Successfully deleted product!");
        } else {
            return Response::CreateFailResponse("Failed to delete product!");
        }
    }

    return Response::CreateFailResponse("Invalid request!");
} finally {
    $dbInst->close();
}

function delete_product($db, $id)
{
    $productModule = new ProductRepository($db);
    $result = $productModule->delete_product($id);
    return $result;
}
?>