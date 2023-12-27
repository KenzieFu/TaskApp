<?php
  require_once('inc/open_connection.php');
  $title =$_POST['title'];
  $content =$_POST['description'];
  $date = $_POST['date'];
  $type = $_POST['type'];

  $query = "INSERT INTO tasks (title, description, date,type)
            VALUES('$title','$content','$date','$type')";
  
  $query_run = mysqli_query($CON,$query);

  if($query_run){
    echo json_encode([
      "error" => false,
      "message" => 'Tasks has been successfully added!'
    ]);
  }else{echo json_encode([
    "error" => true,
    "message" => 'Tasks failed to be added!'
  ]);
    
  }
  require_once('inc/close_connection.php')
  ?>
