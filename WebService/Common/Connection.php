<?php

// constants
define("DB_HOST", "localhost");
define("DB_USER", "id22405775_root");
define("DB_PASS", "Aaron2002$");
define("DB_NAME", "id22405775_restaurantdb");

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

    // returns data if there's data
    // returns boolean if no result
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
            if ($result === false) {
                return $stmt->affected_rows > 0;
            } else {
                $dataArr = [];
                while ($data = $result->fetch_assoc()) {
                    $dataArr[] = $data;
                }

                if (count($dataArr) === 0) {
                    return false;
                } else if (count($dataArr) === 1) {
                    return $dataArr[0];
                } else {
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