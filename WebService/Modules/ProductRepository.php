<?php
include_once ('../../Common/Utils.php');
enum ProductCategory: int
{
    case PIZZA = 0;
    case DOUGHTNUT = 1;
    case BEVERAGES = 2;
}

class ProductRepository
{
    private $db;
    public function __construct($db)
    {
        $this->db = $db;
    }

    public function get_products_by_category($categoryId, $available)
    {
        $q = null;
        if (is_null($categoryId)) {
            $q = $this->db->query("SELECT * FROM `product_tbl` WHERE `Availability` = ?", "i", $available);
        } else {
            $q = $this->db->query(
                "SELECT * FROM `product_tbl` WHERE `ProductCategory` = ? AND `Availability` = ?",
                "ii",
                $categoryId,
                $available
            );
        }

        return $q;
    }

    public function create_product($name, $description, $price, $categoryId, $image, $size)
    {
        // TODO: Add validation
        $q = $this->db->query(
            "INSERT INTO `product_tbl` VALUES (?, ?, ?, ?, ?, ?, ?, ?)",
            "issdisii",
            0,
            $name,
            $description,
            $price,
            $categoryId,
            $image,
            $size,
            0
        ); // 0 means available/true
        return $q;
    }

    public function delete_product($id)
    {
        $q = $this->db->query("DELETE FROM `product_tbl` WHERE `ProductId` = ?", "i", $id);
        return $q;
    }

    public function update_product($id, $name, $description, $price, $categoryId, $image, $size, $availability)
    {
        $queryStr = "UPDATE `product_tbl` SET ";
        $queryCmds = [];
        $queryArgs = [];
        $queryDataTypes = "";

        // product name
        if (!is_null($name)) {
            $queryCmds[] = "`ProductName`=?";
            $queryArgs[] = $name;
            $queryDataTypes .= "s";
        }

        // product description
        if (!is_null($description)) {
            $queryCmds[] = "`ProductDescription`=?";
            $queryArgs[] = $description;
            $queryDataTypes .= "s";
        }

        // price
        if (!is_null($price)) {
            $queryCmds[] = "`Price`=?";
            $queryArgs[] = $price;
            $queryDataTypes .= "d";
        }

        // category
        if (!is_null($categoryId)) {
            $queryCmds[] = "`ProductCategory`=?";
            $queryArgs[] = $categoryId;
            $queryDataTypes .= "i";
        }

        // image
        if (!is_null($image)) {
            $queryCmds[] = "`ProductImage`=?";
            $queryArgs[] = $image;
            $queryDataTypes .= "s";
        }

        // size
        if (!is_null($size)) {
            $queryCmds[] = "`Size`=?";
            $queryArgs[] = $size;
            $queryDataTypes .= "i";
        }

        // availability
        if (!is_null($availability)) {
            $queryCmds[] = "`Availability`=?";
            $queryArgs[] = $availability;
            $queryDataTypes .= "i";
        }

        if (count($queryArgs) > 0) {
            // SET arguments for query
            $queryStr .= join(", ", $queryCmds);

            // Query condition
            $queryStr .= " WHERE `ProductId`=?";
            $queryArgs[] = $id;
            $queryDataTypes .= "i";

            /* 
                It can be confusing. But, the last parameter of `query` method does not accept array.
                Since the expression of the last parameter of the method `query` contains ellipsis, 
                the parameter only accepts unlimited number of arguments and not an array.
                With that reason, we need to dynamically call the `query` method to treat the
                values in the array as arguments. 
            */

            // merge array to treat `$queryArgs` as individual arguments
            $func_args = array_merge([$queryStr, $queryDataTypes], $queryArgs);

            // dynamically call `query` method
            $q = call_user_func_array(array($this->db, "query"), $func_args);
            return $q;
        }

        // Invalid request. We need at least 1 argument for this api to work
        Utils::error($this->db);
    }

    public function search_product($name)
    {
        $q = $this->db->query("SELECT * FROM `product_tbl` WHERE `ProductName` LIKE ?", "s", $name . "%");
        return $q;
    }
}
?>