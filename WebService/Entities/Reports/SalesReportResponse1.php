<?php
class SalesReportResponse1
{
    public function __construct(
        public readonly float $order_total,
        public readonly string $date
    ) {
    }
}

?>