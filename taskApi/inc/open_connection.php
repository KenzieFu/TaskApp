<?php

  $HOST = 'localhost';
  $USER = 'root';
  $PASS = '';
  $DB = 'db_taskapp';

  $CON = mysqli_connect($HOST, $USER, $PASS, $DB);

  if (!$CON) {

    die("Connection Failed: ".mysqli_connect_error());
    
  }

?>