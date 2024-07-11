<?php
class ProductReportResponse1
{
    public function __construct(
        public readonly string $categoryName,
        public readonly int $total_amount_sold
    ) {
    }
}

?>