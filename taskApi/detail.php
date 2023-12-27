<?php
  require_once('inc/open_connection.php');
  
  $id = $_GET['tasksId'];

  $query = "SELECT id,title,content,date,type
                   FROM tasks
                   WHERE id='$id'";

  $query_run = mysqli_query($CON, $query);

  $result = mysqli_fetch_assoc($query_run);

  echo json_encode($result);

  require_once('inc/close_connection.php');
?>