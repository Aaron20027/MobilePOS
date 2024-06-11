<?php
include_once ("../../Common/Utils.php");
class AccountRepository
{
    private $db;
    public function __construct($db)
    {
        $this->db = $db;
    }

    public function username_exists($username)
    {
        $usernameQuery = $this->db->query("SELECT COUNT(*) as count FROM `accounttbl` WHERE `acc_User` = ?", "s", strtolower($username));
        return $usernameQuery["count"] > 0;
    }

    public function create_account($username, $password, $firstName, $lastName, $accountType)
    {
        $checkUsername = $this->username_exists($username);
        if ($checkUsername) {
            Utils::error($this->db, "Username is already taken!");
        }

        $createUserQuery = $this->db->query("INSERT INTO `accounttbl` VALUES (?,?,?,?,?,1)", "ssssi", strtolower($username), $firstName, $lastName, $password, $accountType);
        if (!$createUserQuery) {
            return false;
        }

        return true;
    }

    public function update_account($username, $password, $firstName, $lastName, $accountType, $accountStatus)
    {
        if (!$this->username_exists(strtolower($username))) {
            return false;
        }

        $queryStr = "UPDATE `accounttbl` SET ";
        $queryCmds = [];
        $queryArgs = [];
        $queryDataTypes = "";

        if (!is_null($password)) {
            $hashedPwd = md5($password);
            $queryCmds[] = "`acc_Pass`=?";
            $queryArgs[] = $hashedPwd;
            $queryDataTypes .= "s";
        }

        if (!is_null($firstName)) {
            $queryCmds[] = "`acc_Fname`=?";
            $queryArgs[] = $firstName;
            $queryDataTypes .= "s";
        }

        if (!is_null($lastName)) {
            $queryCmds[] = "`acc_Lname`=?";
            $queryArgs[] = $lastName;
            $queryDataTypes .= "s";
        }

        if (!is_null($accountType)) {
            $queryCmds[] = "`acc_Type`=?";
            $queryArgs[] = $accountType;
            $queryDataTypes .= "i";
        }

        if (!is_null($accountStatus)) {
            $queryCmds[] = "`acc_Status`=?";
            $queryArgs[] = $accountStatus;
            $queryDataTypes .= "i";
        }

        if (count($queryArgs) > 0) {
            // SET arguments for query
            $queryStr .= join(", ", $queryCmds);

            // Query condition
            $queryStr .= " WHERE `acc_User`=?";
            $queryArgs[] = $username;
            $queryDataTypes .= "s";

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

    public function get_accounts($name)
    {
        $selectedAttr = "acc_User as username, acc_Fname as fname, acc_Lname as lname, acc_Type as accType, acc_Status as accStatus";
        $initQuery = "SELECT " . $selectedAttr . " FROM `accounttbl`";
        $getQuery = null;
        if (!is_null($name) || strlen($name) == 0)
        {
            $initQuery .= " WHERE `acc_Fname` LIKE ?";
            $getQuery = $this->db->query($initQuery, "s", "%" . $name . "%");
        }
        else
        {
            $getQuery = $this->db->query($initQuery);
        }

        return $getQuery;
    }

    public function delete_account_by_username($username)
    {
        if (!$this->username_exists(strtolower($username))) {
            return false;
        }

        $delQuery = $this->db->query("DELETE FROM `accounttbl` WHERE `acc_User` = ?", "s", $username);
        return $delQuery;
    }
}
?>