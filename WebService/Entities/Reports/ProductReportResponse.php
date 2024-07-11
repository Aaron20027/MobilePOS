<?php
class ProductReportResponse
{
    public function __construct(
        public readonly string $ProductName,
        public readonly string $categoryName
    ) {
    }
}

?>