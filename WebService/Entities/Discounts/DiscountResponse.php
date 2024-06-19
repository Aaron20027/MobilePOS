<?php
class DiscountResponse
{
    public function __construct(
        public readonly int $id,
        public readonly string $title,
        public readonly string $description,
        public readonly int $discount_type,
        public readonly int $discount_value
    ) {
    }
}
?>