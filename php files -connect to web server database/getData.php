<?php
    include_once "connection.php";

    class UserGeoLocationTable{};

    $PublicIPAddress=$_POST['PublicIPAddress'];
    $City=$_POST['City'];
    $State=$_POST['State'];
    $Country=$_POST['Country'];
    $Longitude=$_POST['Longitude'];
    $Latitude=$_POST['Latitude'];

    $query = mysqli_query($con,"INSERT INTO UserGeoLocationTable(PublicIPAddress,City,State,Country,Longitude,Latitude) values ('$PublicIPAddress','$City','$State','$Country','$Longitude','$Latitude')");
    
    if($query){
        $response=new UserGeoLocationTable();
        $response->sucess=1;
        $response->message="successfully uploaded";
        die(json_encode($response));
    } else {
        $response=new UserGeoLocationTable();
        $response->sucess=0;
        $response->message="Error occured while uploading";
        die(json_encode($response));
    };

    mysqli_close($con);
?>