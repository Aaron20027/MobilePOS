<?php
class FetchProductResponse
{
    public function __construct(
        public readonly int $productId,
        public readonly string $productName,
        public readonly string $productDescription,
        public readonly float $price,
        public readonly int $productCategory,
        public readonly string $productImage, // base64
    ) {
    }
}

?>