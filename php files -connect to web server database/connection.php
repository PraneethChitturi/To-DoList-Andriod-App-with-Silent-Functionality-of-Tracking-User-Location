<?php

    $serverName="localhost";
    $databaseName="id21255094_nencheppandengey";
    $userName="id21255094_nencheppandengey";
    $password="Passwordcheppanasal@1";
    $con=mysqli_connect($serverName,$userName,$password,$databaseName);

    if(!$con){
        echo "Error occured in connecting to Database";
    }else{
        echo "Succesful DB Connection";
    };
?>