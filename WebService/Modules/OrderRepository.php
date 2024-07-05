<?php
include_once ('../../Common/Utils.php');

class OrderRepository
{
    private $db;
    public function __construct($db)
    {
        $this->db = $db;
    }

    public function AddCart($username, $productId, $quantity, $size)
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