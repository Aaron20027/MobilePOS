<?php
include_once ('../../Common/Connection.php');
include_once ('../../Common/Utils.php');
include_once ('../../Entities/Response.php');
include_once ('../../Modules/ProductRepository.php');

/*
 * POST - /api/products/create.php
 * @name: str - [required]
 * @desc: str - [required]
 * @price: float - [required]
 * @category_id: int - [required]
 * @image: str - [required]
 * @size: int - [required]
 * 
 * Return: [Response(Base)]
 */

$dbInst = RestaurantDB::GetTransient();
$postArgsIds = ["name", "desc", "price", "category_id", "image", "size"];

try {
    if (Utils::InitCheck($dbInst)) {
        $reqArgs = array_map("Utils::GetPostOrNull", $postArgsIds);
        if (in_array(null, $reqArgs)) {
            return Response::CreateFailResponse("One or more arguments are missing!");
        }

        $create_result = create_product($dbInst, $reqArgs[0], $reqArgs[1], $reqArgs[2], $reqArgs[3], $reqArgs[4], $reqArgs[5]);
        return;
        if ($create_result) {
            return Response::CreateSuccessResponse("Successfully created product!");
        } else {
            return Response::CreateFailResponse("Failed to create product!");
        }
    }

    return Response::CreateFailResponse("Invalid request!");
} finally {
    $dbInst->close();
}

function create_product($db, $name, $description, $price, $categoryId, $image, $size)
{
    $productModule = new ProductRepository($db);
    $result = $productModule->create_product($name, $description, $price, $categoryId, $image, $size);
    return $result;
}

?>