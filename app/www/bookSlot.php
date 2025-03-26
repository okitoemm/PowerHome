<?php
error_reporting(E_ERROR | E_PARSE);
$db_host = "localhost";
$db_uid = "root";
$db_pass = "root";
$db_name = "powerhome_db";

$db_con = mysqli_connect($db_host, $db_uid, $db_pass, $db_name);

$token = $_GET['token'];
$appliance_id = $_GET['appliance_id'];
$time_slot_id = $_GET['time_slot_id'];

$sql = "SELECT id FROM user WHERE token='$token' AND expired_at > NOW()";
$result = mysqli_query($db_con, $sql);
$row = mysqli_fetch_assoc($result);

if (!$row) {
    echo json_encode(array("success" => false, "message" => "Token invalide ou expiré."));
    exit();
}

$now = date('Y-m-d H:i:s');
$sql_insert = "INSERT INTO appliance_time_slot (appliance_id, time_slot_id, `order`, booked_at)
               VALUES ($appliance_id, $time_slot_id, 1, '$now')";

if (mysqli_query($db_con, $sql_insert)) {
    echo json_encode(array("success" => true, "message" => "Créneau réservé avec succès."));
} else {
    echo json_encode(array("success" => false, "message" => "Erreur lors de la réservation."));
}

mysqli_close($db_con);
header('Content-Type: application/json');
http_response_code(200);
?>