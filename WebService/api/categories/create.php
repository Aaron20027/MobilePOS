<?php
include_once ('../../Common/Connection.php');
include_once ('../../Common/Utils.php');
include_once ('../../Entities/Response.php');
include_once ('../../Modules/CategoryRepository.php');

/*
 * POST - /api/categories/create.php
 * @name: str - [required]
 *
 * Return: [Response(Base)]
 */

$dbInst = RestaurantDB::GetTransient();
$postArgsIds = ["name"];

try {
    if (Utils::InitCheck($dbInst)) {
        $reqArgs = array_map("Utils::GetPostOrNull", $postArgsIds);
        if (in_array(null, $reqArgs)) {
            return Response::CreateFailResponse("One or more arguments are missing!");
        }

        $create_result = create_product($dbInst, $reqArgs[0]);
        return;
        if ($create_result) {
            return Response::CreateSuccessResponse("Successfully created category!");
        } else {
            return Response::CreateFailResponse("Failed to create category!");
        }
    }

    return Response::CreateFailResponse("Invalid request!");
} finally {
    $dbInst->close();
}

function create_product($db, $name)
{
    $categoryModule = new CategoryRepository($db);
    $result = $categoryModule->create_category($name);
    return $result;
}

?>