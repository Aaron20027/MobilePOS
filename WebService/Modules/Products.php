<?php

enum ProductCategory: int
{
    case PIZZA = 0;
    case DOUGHTNUT = 1;
    case BEVERAGES = 2;
}
class Products
{
    private $db;
    public function __construct($db)
    {
        $this->db = $db;
    }

    public function get_products($categoryId)
    {
        $q = null;
        if (is_null($categoryId)) {
            $q = $this->db->query("SELECT * FROM `product_tbl`");
        } else {
            $q = $this->db->query("SELECT * FROM `product_tbl` WHERE `ProductCategory` = ?", "i", $categoryId);
        }

        return $q;
    }
}
?>