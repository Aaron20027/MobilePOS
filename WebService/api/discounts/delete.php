<?php
include_once ('../../Common/Connection.php');
include_once ('../../Common/Utils.php');
include_once ('../../Entities/Response.php');
include_once ('../../Modules/DiscountRepository.php');

/*
 * POST - /api/discounts/delete.php
 * @id: int - [required]
 * 
 * Return: [Response(Base)]
 */

$dbInst = RestaurantDB::GetTransient();

try {
    if (Utils::InitCheck($dbInst)) {
        $discountId = Utils::GetPostOrNull("id");
        if (is_null($discountId)) {
            return Response::CreateFailResponse("Invalid argument!");
        }

        $delete_result = delete_discount($dbInst, $discountId);
        if ($delete_result) {
            return Response::CreateSuccessResponse("Successfully deleted discount!");
        } else {
            return Response::CreateFailResponse("Failed to delete discount!");
        }
    }

    return Response::CreateFailResponse("Invalid request!");
} finally {
    $dbInst->close();
}

function delete_discount($db, $id)
{
    $discountModule = new DiscountRepository($db);
    $result = $discountModule->delete_discount($id);
    return $result;
}
?>