<html>
<head>
    <title>Welcome!</title>
</head>
<body>
<table border="2">
    <tr>
        <th>Id</th>
        <th>Name</th>
        <th>Age</th>
        <th>Address</th>
        <th>Phones</th>
    </tr>
    <#list users>
    <#items as user>
    <tr>
        <td>${user.id}</td>
        <td>${user.name}</td>
        <td>${user.age}</td>
        <td>${user.address}</td>
        <td>${user.phone}</td>
    </#items>
</#list>
</tr>
</table>
<br>
<br>
<input value="Create user" type="button" onclick="location.href='http://${address}:${port}/users/create'"/>
<input value="Update list" type="button" onclick="location.href='http://${address}:${port}/'"/>
</body>
</html>