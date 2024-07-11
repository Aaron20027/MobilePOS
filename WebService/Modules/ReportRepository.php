<?php
include_once ("../../Common/Utils.php");
class ReportRepository
{
        private $db;
       public function __construct($db)
       {
           $this->db = $db;
       }

        public function getTransaction()
           {
               $transactionCount = $this->db->query("SELECT COUNT(*) as count FROM `payment_tbl`");
               return $transactionCount["count"] ;
           }

        public function getSales()
            {
                $transactionSales = $this->db->query("SELECT SUM(order_total) as total_sales FROM `order_tbl`");
                 return $transactionSales["total_sales"] ;
            }

        public function avgSales()
            {
                $avgSales = $this->db->query("SELECT AVG(order_total) as avg_sales FROM `order_tbl`");
                 return $avgSales["avg_sales"] ;
            }

        public function SalesInfo()
            {
                 $salesInfo = $this->db->query("SELECT o.order_total, p.date FROM `order_tbl` as o INNER JOIN payment_tbl as p ON o.pay_id_fk = p.pay_id");
                 return $salesInfo;
            }

         public function TopProduct()
                    {
                         $TopProduct = $this->db->query("SELECT p.ProductName, MAX(s.total_quantity) AS max_quantity_sold
                                                        FROM (
                                                            SELECT prod_id, SUM(order_qty) AS total_quantity
                                                            FROM order_detail_tbl
                                                            GROUP BY prod_id
                                                        ) AS s
                                                        JOIN product_tbl p ON s.prod_id = p.ProductId;");
                         return $TopProduct["ProductName"];
                    }

         public function TopCategory(){
            $TopCategory = $this->db->query("SELECT c.categoryName, SUM(o.order_qty) AS total_quantity_sold
                                                      FROM `order_detail_tbl` o
                                                      JOIN product_tbl p ON o.prod_id = p.ProductId
                                                      JOIN categories_tbl c ON p.ProductCategory = c.categoryId
                                                      GROUP BY c.categoryName
                                                      ORDER BY total_quantity_sold DESC
                                                      LIMIT 1;");

            return $TopCategory["categoryName"];


         }

         public function CategoryInfo()
                     {
                          $categoryInfo = $this->db->query("SELECT c.categoryName, SUM(o.order_qty) AS total_amount_sold
                                                         FROM order_detail_tbl o
                                                         JOIN product_tbl p ON o.prod_id = p.ProductId
                                                         JOIN categories_tbl c ON p.ProductCategory = c.categoryId
                                                         GROUP BY c.categoryName;");
                          return $categoryInfo;
                     }


         public function BestEmployee()
         {
             $employeeInfo = $this->db->query("SELECT account_id_fk as acc, COUNT(account_id_fk) as disCount
                                                                                               FROM Order_tbl
                                                                                               GROUP BY account_id_fk
                                                                                               ORDER BY disCount DESC
                                                                                               LIMIT 1;");
             return $employeeInfo;
         }

         public function LeastPerforming()
         {
              $employeeInfo = $this->db->query("SELECT account_id_fk as acc1, COUNT(account_id_fk) as disCount
                                                FROM Order_tbl
                                                GROUP BY account_id_fk
                                                ORDER BY disCount ASC
                                                LIMIT 1;");
              return $employeeInfo;
         }
         public function AverageEmployeeSales()
         {
              $employeeInfo = $this->db->query("SELECT AVG(order_count) as avgOrdersPerEmployee
                                                FROM (
                                                    SELECT COUNT(*) as order_count
                                                    FROM Order_tbl
                                                    GROUP BY account_id_fk
                                                ) as subquery;");

              return $employeeInfo;
         }

         public function EmployeeInfo(){
                        $employeeInfo = $this->db->query("SELECT account_id_fk as acc1, COUNT(account_id_fk) as disCount
                                                                                                          FROM Order_tbl
                                                                                                          GROUP BY account_id_fk
                                                                                                          ORDER BY disCount DESC");

                       return $employeeInfo;
         }







}
?>