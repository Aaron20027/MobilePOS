<?php

class CartItemResponse
{
    public function __construct(
        public readonly string $username,
        public readonly int $productId,
        public readonly int $quantity,
        public readonly int $size
    ) {
    }
}
?>