<?php
include_once ('../../Common/Utils.php');

class CategoryRepository
{
    private $db;
    public function __construct($db)
    {
        $this->db = $db;
    }

    public function get_category()
    {
         $getQuery = $this->db->query("SELECT * FROM `categories_tbl`");
         return $getQuery;
    }

    public function create_category($name)
    {
        // TODO: Add validation
        $q = $this->db->query(
            "INSERT INTO `categories_tbl` VALUES (?, ?)",
            "is",
            0,
            $name
        );
        return $q;
    }






}
?>