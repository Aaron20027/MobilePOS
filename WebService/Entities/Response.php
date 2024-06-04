<?php

class Response
{
    public $success;
    public $message;
    public $data;
    function __construct($s, $m, $d)
    {
        $this->success = $s;
        $this->message = $m;
        $this->data = $d;
    }

    public static function CreateFailResponse($m, $d = null)
    {
        return Response::CreateResponse(false, $m, $d);
    }

    public static function CreateSuccessResponse($m, $d = null)
    {
        return Response::CreateResponse(true, $m, $d);
    }

    public static function CreateResponse($s, $m, $d = null)
    {
        ob_end_clean();
        echo json_encode(get_object_vars(new Response($s, $m, $d)));
    }
}

?>