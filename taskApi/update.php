<?php
  require_once('inc/open_connection.php');
  
  $id = $_POST['tasksId'];
  $title = $_POST['title'];
  $content = $_POST['description'];
  $date = $_POST['date'];
  $type = $_POST['type'];

  $query = "UPDATE tasks SET 
  title = '$title',
  description = '$content',
  date = '$date',
  type = '$type'
  WHERE id = '$id';";

  $query_run = mysqli_query($CON, $query);

  if ($query_run) {
        echo json_encode([
        "error" => false,
        "message" => 'Tasks has been successfully updated!'
        ]);
    } else {
        echo json_encode([
        "error" => true,
        "message" => 'Tasks failed to be updated!'
        ]);
    }

  require_once('inc/close_connection.php');
?>