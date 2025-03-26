<?php
error_reporting(E_ERROR | E_PARSE);
$db_host = "localhost";
$db_uid = "root";
$db_pass = "root";
$db_name = "powerhome_db";

$db_con = mysqli_connect($db_host, $db_uid, $db_pass, $db_name);

$token = $_GET['token'];
$sql = "SELECT id FROM user WHERE token='$token' AND expired_at > NOW()";
$result = mysqli_query($db_con, $sql);
$row = mysqli_fetch_assoc($result);

if (!$row) {
    echo json_encode(array("success" => false, "message" => "Token invalide ou expiré."));
    exit();
}

$sql = "SELECT * FROM time_slot ORDER BY begin";
$result = mysqli_query($db_con, $sql);
$slots = array();

while ($row = mysqli_fetch_assoc($result)) {
    $slots[] = $row;
}

echo json_encode($slots);
mysqli_close($db_con);
header('Content-Type: application/json');
http_response_code(200);
?>