<?php
include_once ('../../Common/Connection.php');
include_once ('../../Common/Utils.php');
include_once ('../../Entities/Response.php');
include_once ('../../Modules/Products.php');

/*
 * POST - /api/products/update.php
 * Description: At least one of the optional arguments must be used or the request
 * will be considered invalid.
 * @id: int - [required]
 * @name: str - [optional]
 * @desc: str - [optional]
 * @price: float - [optional]
 * @category_id: int - [optional]
 * @image: str - [optional]
 * @size: int - [optional]
 * @availability: int - [optional]
 * 
 * Return: [Response(Base)]
 */

$dbInst = RestaurantDB::GetTransient();
$postArgsIds = ["id", "name", "desc", "price", "category_id", "image", "size", "availability"];

try {
    if (Utils::InitCheck($dbInst)) {
        $reqArgs = array_map("Utils::GetPostOrNull", $postArgsIds);

        // Check if id is not null and if there are at least two values in array that are not null
        if (!$reqArgs[0] || count(array_filter($reqArgs, fn($v) => $v != null)) < 2) {
            return Response::CreateFailResponse("One or more arguments are invalid!");
        }

        $update_result = update_product($dbInst, $reqArgs[0], $reqArgs[1], $reqArgs[2], $reqArgs[3], $reqArgs[4], $reqArgs[5], $reqArgs[6], $reqArgs[7]);
        if ($update_result) {
            return Response::CreateSuccessResponse("Successfully updated product!");
        } else {
            return Response::CreateFailResponse("Failed to update product!");
        }
    }

    return Response::CreateFailResponse("Invalid request!");
} finally {
    $dbInst->close();
}

function update_product($db, $id, $name, $description, $price, $categoryId, $image, $size, $availability)
{
    $productModule = new Products($db);
    $result = $productModule->update_product($id, $name, $description, $price, $categoryId, $image, $size, $availability);
    return $result;
}
?>