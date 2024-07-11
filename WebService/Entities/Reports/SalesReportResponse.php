<?php
class SalesReportResponse
{
    public function __construct(
        public readonly int $transactionCount,
        public readonly string $transactionSales,
        public readonly float $avgSales
    ) {
    }
}

?>