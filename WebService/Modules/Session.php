<?php
include_once ("../../Common/Utils.php");

class SessionRepository
{
    private $db;
    public function __construct($db)
    {
        $this->db = $db;
    }

    // return last session token if an entry exist in database
    public function check_session($username)
    {
        $checkSessionQuery = $this->db->query("SELECT COUNT(*) as `result`, session_token FROM `session_tbl` WHERE `username` = ?", "s", $username);

        if (is_null($checkSessionQuery)) {
            Utils::error($this->db);
        }

        if (Utils::ArrayHasKeyAndEqualTo($checkSessionQuery, "result", 1) && $checkSessionQuery["session_token"]) {
            return $checkSessionQuery["session_token"];
        } else {
            return "";
        }
    }

    // if session token is not valid, generate new token
    public function validate_session($username, $ip)
    {
        // User can only log into one network at a time. IP address is used as network id.
        $validateSessionQuery = $this->db->query("SELECT COUNT(*) as `result`, ip, session_token FROM `session_tbl` WHERE `username` = ? and `ip` = ?", "ss", $username, $ip);

        if (is_null($validateSessionQuery)) {
            Utils::error($this->db);
        }

        $token = $validateSessionQuery["session_token"];
        if (!$token || strlen($token) != 32) {
            // If user is logged into a different network already, log the user out by generating a new token after logged in.
            $token = Utils::GenerateSessionToken($username, $ip);
        }

        return $token;
    }

    public function update_session($token, $username, $ip, $is_update)
    {
        $q = null;
        if ($is_update) {
            $q = $this->db->query("UPDATE `session_tbl` SET `session_token`= ?,`ip`= ? WHERE `username` = ?", "sss", $token, $ip, $username);
        } else {
            $q = $this->db->query("INSERT INTO `session_tbl`(`username`, `session_token`, `ip`) VALUES (?,?,?)", "sss", $username, $token, $ip);
        }

        if (is_null($q)) {
            Utils::error($this->db);
        }
    }
}

?>