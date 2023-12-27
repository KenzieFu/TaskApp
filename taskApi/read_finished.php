<?php
  require_once('inc/open_connection.php');

  $result = [];

  $query = "SELECT id, title, date, status FROM notes WHERE status = 1 ORDER BY date ASC";
  $query_run = mysqli_query($CON, $query);

  while ($row = mysqli_fetch_assoc($query_run)) {
    $result[] = $row;
  }

  echo json_encode($result);

  require_once('inc/close_connection.php');
?>