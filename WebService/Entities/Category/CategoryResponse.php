<?php
class CategoryResponse
{
    public function __construct(
        public readonly int $categoryId,
        public readonly string $categoryName,

    ) {
    }
}

?>