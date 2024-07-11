<?php
class EmployeeReportResponse
{
    public function __construct(
        public readonly string $acc,
        public readonly int $disCount,
        public readonly string $acc1,
        public readonly float $avgOrdersPerEmployee
    ) {
    }
}

?>