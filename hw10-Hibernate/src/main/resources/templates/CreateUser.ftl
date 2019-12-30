<html>
<head></head>
<body>
<form method="POST" action="http://${address}:${port}/users/add">
    Name: <input type="text" name="name"><br>
    Age: <input type="text" name="age"><br>
    Street: <input type="text" name="street"><br>
    Number: <input type="text" name="number"><br>
    <input value="Create" type="submit"/>
</form>
<input value="Show all" type="button" onclick="location.href='http://${address}:${port}/"/>
</body>
</html>