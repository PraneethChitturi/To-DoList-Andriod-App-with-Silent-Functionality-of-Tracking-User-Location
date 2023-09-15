<!DOCTYPE html>
<html>
<head>
<style>
table, th, td {
    border: 1px solid black;
}
</style>
</head>
<body>
    <header> Manasa's App- User Location Data </header>
<?php
$servername = "localhost";
$username = "id21255094_nencheppandengey";
$password = "Passwordcheppanasal@1";
$dbname = "id21255094_nencheppandengey";

// Create connection
$conn = new mysqli($servername, $username, $password, $dbname);
// Check connection
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}

$sql = "SELECT * FROM `UserGeoLocationTable`";
$result = $conn->query($sql);

if ($result->num_rows > 0) {
    echo "<table><tr><th>PublicIPAddress</th><th>City</th><th>State</th><th>Country</th><th>Longitude</th><th>Latitude</th></tr>";
    // output data of each row
    while($row = $result->fetch_assoc()) {
        echo "<tr><td>" . $row["PublicIPAddress"]. "</td><td>" . $row["City"]. "</td><td>". $row["State"]. "</td><td>". $row["Country"]. "</td><td>". $row["Longitude"]. "</td><td>". $row["Latitude"]. "</td></tr>";
    }
    echo "</table>";
} else {
    echo "0 results";
}

$conn->close();
?>

</body>
</html>