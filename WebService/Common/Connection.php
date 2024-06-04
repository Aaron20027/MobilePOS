<?php

// constants
define("DB_HOST", "localhost");
define("DB_USER", "root");
define("DB_PASS", "");
define("DB_NAME", "restaurantdb");

class RestaurantDB
{
    private $conn;

    private function __construct()
    {
        $this->conn = null;
    }

    public function open()
    {
        $this->conn = new mysqli(DB_HOST, DB_USER, DB_PASS, DB_NAME);
        return !$this->conn->connect_error;
    }

    // returns null if query failed
    // returns false if no result
    public function query($statement, $types = "", ...$qArgs)
    {
        $stmt = $this->conn->prepare($statement);

        // bind param if not empty
        if ($types && $qArgs) {
            $stmt->bind_param($types, ...$qArgs);
        }

        if ($stmt->execute()) {
            $result = $stmt->get_result();

            // update queries only returns boolean
            if (is_bool($result)) {
                return $result;
            } else {
                $dataArr = [];
                while ($data = $result->fetch_assoc())
                {
                    $dataArr[] = $data;
                }

                if (count($dataArr) === 0)
                {
                    return false;
                }
                else if (count($dataArr) === 1)
                {
                    return $dataArr[0];
                }
                else
                {
                    return $dataArr;
                }
            }
        }

        Utils::error($this);
    }

    public function close()
    {
        // only close connection when we have valid connection
        if ($this->conn != null && !$this->conn->connect_error) {
            $this->conn->close();
        }
    }

    static function GetTransient()
    {
        return new RestaurantDB();
    }
}
?>