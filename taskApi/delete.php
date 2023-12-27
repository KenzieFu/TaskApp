<?php
  require_once('inc/open_connection.php');
  
  $id = $_POST['tasksId'];

  $query = "DELETE FROM tasks WHERE id = '$id';";

  $query_run = mysqli_query($CON, $query);

  if ($query_run) {
        echo json_encode([
        "error" => false,
        "message" => 'Tasks has been successfully deleted!'
        ]);
    } else {
        echo json_encode([
        "error" => true,
        "message" => 'Tasks failed to be deleted!'
        ]);
    }

  require_once('inc/close_connection.php');
?>