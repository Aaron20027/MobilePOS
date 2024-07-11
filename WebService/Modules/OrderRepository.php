<?php
include_once ('../../Common/Utils.php');

class OrderRepository
{
    private $db;
    public function __construct($db)
    {
        $this->db = $db;
    }

   public function get_last() {

          $getQuery = $this->db->query("SELECT MAX(pay_id) AS pay_id FROM `payment_tbl`");
          return $getQuery;

      }

    public function AddOrderDetails($productId, $orderQty, $orderCost)
             {

                  $addResult = $this->db->query("SELECT MAX(order_id) AS order_id FROM `order_tbl`");

                  var_dump($addResult);
                  if(isset($addResult['order_id'])) {
                       $orderId = $addResult['order_id'];
                       }

                       if(is_null($orderId)){
                                      $orderId=1;
                                      }


                  $addResult1 = $this->db->query(
                        "INSERT INTO `order_detail_tbl`(`order_Id`, `prod_id`, `order_qty`, `order_cost`) VALUES (?, ?, ?, ?)",
                        "iiid",
                        $orderId,
                        $productId,
                        $orderQty,
                        $orderCost
                  );


             }

   public function AddOrder($accountId, $orderTotal)
          {

               $addResult = $this->db->query("SELECT MAX(pay_id) AS pay_id FROM `payment_tbl`");

               var_dump($addResult);

               if(isset($addResult['pay_id'])) {
                    $payId = $addResult['pay_id'];
                    }

               if(is_null($payId)){
               $payId=1;
               }



               $addResult1 = $this->db->query(
                                  "INSERT INTO `order_tbl`(`order_Id`, `account_id_fk`, `order_total`, `pay_id_fk`) VALUES (?, ?, ?, ?)",
                                  "isdi",
                                  0,
                                  $accountId,
                                  $orderTotal,
                                  $payId
                              );


          }


    public function AddPayment($payamnt, $date, $method, $discountId,$discountAmnt)

    {
              $current_date = date("Y-m-d");


               $addResult = $this->db->query(
                   "INSERT INTO `payment_tbl`(`pay_id`, `pay_amnt`, `date`, `pay_method`, `discount_id`, `discount_amnt`) VALUES (?, ?, ?, ?, ?, ?)",
                   "idsiid",
                   0,
                   $payamnt,
                   $current_date,
                   $method,
                   $discountId,
                   $discountAmnt
               );


    }


    public function AddCart1($username, $productId, $quantity, $size)
    {
        $itemQ = $this->db->query(
            "SELECT quantity FROM `cart_tbl` WHERE `username` = ? and `productId` = ? and `size` = ?",
            "sii",
            $username,
            $productId,
            $size
        );

        // false means quantity is 0
        if ($itemQ === false) {
            // add to cart
            $addResult = $this->db->query(
                "INSERT INTO `cart_tbl`(`username`, `productId`, `quantity`, `size`) VALUES (?, ?, ?, ?)",
                "siii",
                $username,
                $productId,
                $quantity,
                $size
            );

            return $addResult;
        } else {
            // update cart
            $intQuantity = $itemQ["quantity"];
            $incResult = $this->db->query(
                "UPDATE `cart_tbl` SET `quantity`=? WHERE `username` = ? AND `productId` = ? and `size` = ?",
                "isii",
                (int)$quantity + $intQuantity,
                $username,
                $productId,
                $size
            );

            return $incResult;
        }
    }

    public function AddCart($username, $productId, $quantity, $size)
        {
            $itemQ = $this->db->query("SELECT MAX() FROM `cart_tbl` WHERE `username` = ? and `productId` = ? and `size` = ?",
                "sii",
                $username,
                $productId,
                $size
            );

            // false means quantity is 0
            if ($itemQ === false) {
                // add to cart
                $addResult = $this->db->query(
                    "INSERT INTO `cart_tbl`(`username`, `productId`, `quantity`, `size`) VALUES (?, ?, ?, ?)",
                    "siii",
                    $username,
                    $productId,
                    $quantity,
                    $size
                );

                return $addResult;
            } else {
                // update cart
                $intQuantity = $itemQ["quantity"];
                $incResult = $this->db->query(
                    "UPDATE `cart_tbl` SET `quantity`=? WHERE `username` = ? AND `productId` = ? and `size` = ?",
                    "isii",
                    (int)$quantity + $intQuantity,
                    $username,
                    $productId,
                    $size
                );

                return $incResult;
            }
        }

    public function RemoveCart($username, $productId, $quantity, $size)
    {
        $itemQ = $this->db->query(
            "SELECT quantity FROM `cart_tbl` WHERE `username` = ? and `productId` = ? and `size` = ?",
            "sii",
            $username,
            $productId,
            $size
        );

        if ($itemQ === false)
            return false;

        $intQuantity = (int)$itemQ["quantity"];
        $newQuantity = $intQuantity - $quantity;
        if ($newQuantity <= 0) {
            // delete item in cart
            $delQuery = $this->db->query(
                "DELETE FROM `cart_tbl` WHERE `username` = ? and `productId` = ? and `size` = ?",
                "sii",
                $username,
                $productId,
                $size
            );

            return $delQuery;
        } else {
            // update cart
            $incResult = $this->db->query(
                "UPDATE `cart_tbl` SET `quantity`=? WHERE `username` = ? AND `productId` = ? and `size` = ?",
                "isii",
                $newQuantity,
                $username,
                $productId,
                $size
            );

            return $incResult;
        }
        // if id and size already exist, decrement
    }

    public function EditCart($username, $productId, $quantity, $size)
    {
        $itemQ = $this->db->query(
            "SELECT quantity FROM `cart_tbl` WHERE `username` = ? and `productId` = ? and `size` = ?",
            "sii",
            $username,
            $productId,
            $size
        );

        if ($itemQ === false)
            return false;

        $intQuantity = (int)$quantity;
        if ($intQuantity <= 0) {
            // delete item in cart
            $delQuery = $this->db->query(
                "DELETE FROM `cart_tbl` WHERE `username` = ? and `productId` = ? and `size` = ?",
                "sii",
                $username,
                $productId,
                $size
            );

            return $delQuery;
        } else {
            // update cart
            $editResult = $this->db->query(
                "UPDATE `cart_tbl` SET `quantity`=? WHERE `username` = ? AND `productId` = ? and `size` = ?",
                "isii",
                $intQuantity,
                $username,
                $productId,
                $size
            );

            return $editResult;
        }
        // if id and size already exist, decrement
    }

    public function GetCartItems($username)
    {
        // check if we have items in cart
        $cartCount = $this->db->query("SELECT COUNT(*) FROM `cart_tbl` WHERE `username` = ?", "s", $username);
        if ($cartCount === False) {
            Utils::error($this->db);
        }

        $cartItemsDB = $this->db->query("SELECT * FROM `cart_tbl` WHERE `username` = ?", "s", $username);
        return $cartItemsDB;
    }

    public function FinalizeOrder($username, $discount_id)
    {
        $cartQuery = $this->db->query(
            "SELECT cart_tbl.quantity, pt.Price as price FROM cart_tbl
            INNER JOIN product_tbl AS pt
            ON cart_tbl.productId = pt.ProductId
            WHERE cart_tbl.username = ?",
            "s",
            $username);
        
        // validate cart query
        if ($cartQuery === false)
        {
            return false;
        }

        if (count($cartQuery) > 0)
        {
            // create sales info
            $subTotal = 0;
            foreach ($cartQuery as $cItem)
            {
                $cItemQuantity = $cItem["quantity"];
                $cItemPrice = $cItem["price"];
                $subTotal += $cItemQuantity * $cItemPrice;
            }

            // after discount computation
            $total = $subTotal;
            
            // check discount
            if (!is_null($discount_id))
            {
                $discountCheckQuery = $this->db->query(
                    "SELECT type, value FROM `discount_tbl` WHERE id = ?",
                    "i",
                    $discount_id
                );

                if ($discountCheckQuery === false)
                {
                    return false;
                }

                $discountType = $discountCheckQuery["type"];
                $discountVal = $discountCheckQuery["value"];
                if ($discountType == 0)
                {
                    // percentage discount
                    $total *= ($discountVal * 0.01);
                }
                else if ($discountType == 1)
                {
                    // value discount
                    $total -= $discountVal;
                }
            }

            // insert the items into orders table
            $insertQuery = $this->db->query(
                "INSERT INTO `sales_tbl`(`orderId`, `subtotal`, `total`, `discount_id`) VALUES (?, ?, ?, ?)",
                "iddi",
                0,
                $subTotal,
                $total,
                0
            );

            if ($insertQuery === false)
            {
                return false;
            }

            // delete all items in cart
            $deleteQuery = $this->db->query(
                "DELETE FROM `cart_tbl` WHERE `username` = ?",
                "s",
                $username
            );

            if ($deleteQuery === false)
            {
                return false;
            }

            return true;
        }

        return false;
    }
}
?>