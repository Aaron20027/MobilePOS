<?php
include_once ('../../Common/Connection.php');
include_once ('../../Common/Utils.php');
include_once ('../../Entities/Response.php');
include_once ('../../Modules/OrderRepository.php');

/*
 * POST - /api/orders/addPayment.php
 * @payamnt: float - [required]
 * @date: int - [required]
 * @method: int - [required]
 * @discountId: int - [required]
 * @discountAmt: float - [required]
 * Return: [Response(Base)]
 */

$dbInst = RestaurantDB::GetTransient();
$postArgsIds = ["payamnt", "date", "method", "discountId","discountAmt"];

try {
    if (Utils::InitCheck($dbInst)) {
        $reqArgs = array_map("Utils::GetPostOrNull", $postArgsIds);


        if (in_array(null, $reqArgs)) {
            return Response::CreateFailResponse("One or more arguments are missing!");
        }

        $addResult = AddCart($dbInst, $reqArgs[0], date("Y/m/d"), $reqArgs[2], $reqArgs[3], $reqArgs[4]);
        if ($addResult)
            return Response::CreateSuccessResponse("Payment is Successful!");
        else
            return Response::CreateFailResponse("Payment Failed!!");
    }

    return Response::CreateFailResponse("Invalid request!");
} finally {
    $dbInst->close();
}

function AddCart($db, $payamnt, $date, $method, $discountId,$discountAmnt)
{
    $orderRepo = new OrderRepository($db);
    $result = $orderRepo->AddPayment($payamnt, $date, $method, $discountId,$discountAmnt);
    return $result;
}

?>