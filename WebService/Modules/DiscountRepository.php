<?php
include_once ('../../Common/Utils.php');

class DiscountRepository
{
    private $db;
    public function __construct($db)
    {
        $this->db = $db;
    }

    public function create_discount($title, $desc, $t, $val, $avail)
    {

        $createQuery = $this->db->query(
            "INSERT INTO `discount_tbl` VALUES (?, ?, ?, ?, ?, ?)",
            "issidi",
            0,
            $title,
            $desc,
            $t,
            $val,
            $avail
        );

        return $createQuery;
    }

    public function update_discount($id, $title, $desc, $t, $val, $avail)
    {
        $queryStr = "UPDATE `discount_tbl` SET ";
        $queryCmds = [];
        $queryArgs = [];
        $queryDataTypes = "";

        // title
        if (!is_null($title)) {
            $queryCmds[] = "`title`=?";
            $queryArgs[] = $title;
            $queryDataTypes .= "s";
        }

        // description
        if (!is_null($desc)) {
            $queryCmds[] = "`description`=?";
            $queryArgs[] = $desc;
            $queryDataTypes .= "s";
        }

        // TODO: Add edit rules for discount type and value

        // discount type
        if (!is_null($t)) {
            $queryCmds[] = "`type`=?";
            $queryArgs[] = $t;
            $queryDataTypes .= "i";
        }

        // value
        if (!is_null($val)) {
            $queryCmds[] = "`value`=?";
            $queryArgs[] = $val;
            $queryDataTypes .= "d";
        }

        if (!is_null($avail)) {
            $queryCmds[] = "`avail`=?";
            $queryArgs[] = $avail;
            $queryDataTypes .= "i";
        }



        if (count($queryArgs) > 0) {
            // SET arguments for query
            $queryStr .= join(", ", $queryCmds);

            // Query condition
            $queryStr .= " WHERE `id`=?";
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

    public function get_discounts()
    {
        $getQuery = $this->db->query("SELECT * FROM `discount_tbl`");
        return $getQuery;
    }

    public function delete_discount($id)
    {
        $q = $this->db->query("DELETE FROM `discount_tbl` WHERE `id` = ?", "i", $id);
        return $q;
    }
}
?>