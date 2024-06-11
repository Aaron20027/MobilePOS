<?php
class LoginResponse
{
    public function __construct(
        public readonly string $username,
        public readonly string $sessionToken,
        public readonly string $firstName,
        public readonly string $lastName,
        public readonly int $accountType
    ) {
    }
}

?>