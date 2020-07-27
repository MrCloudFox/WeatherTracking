<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
    <title>WeatherTracking</title>
</head>
<body>
<style>
    body {
        margin: 1%;
    }
</style>
<h1>Weather Tracking</h1>
<form method="get">
    Выберите погодный ресурс:
    <select name="resource" id="resource" size="1" autofocus>
        <option value="rambler">Rambler</option>
        <option value="rumeteo">RuMeteo</option>
    </select>
    <%--<script type="text/javascript">--%>
        <%--document.getElementById('resource').value = "<?php echo $_GET['resource'];?>";--%>
    <%--</script>--%>
    Выберите город:
    <select name="city" id="city" size="1" autofocus>
        <option value="moscow">Москва</option>
        <option value="chelyabinsk">Челябинск</option>
        <option value="ekaterinburg">Екатеринбург</option>
    </select>
    <%--<script type="text/javascript">--%>
        <%--document.getElementById('city').value = "<?php echo $_GET['city'];?>";--%>
    <%--</script>--%>
    <input type="submit" value="Отправить">
</form>
<h4 id="selectedResource"></h4>
<h4 id="selectedCity"></h4>
<p>
<div class="container">
    <table class="table table-stripped">
        <thead>
        <tr>
            <th>Дата</th>
            <th>День недели</th>
            <th>Температура</th>
            <th>Влажность</th>
            <th>Ветер</th>
        </tr>
        </thead>
        <tbody id="data">
        </tbody>
    </table>
</div>
</p>

<script>
    var gets = (function() {
        var a = window.location.search;
        var b = new Object();
        a = a.substring(1).split("&");
        for (var i = 0; i < a.length; i++) {
            c = a[i].split("=");
            b[c[0]] = c[1];
        }
        return b;
    })()

    if("resource" in gets) {
        localStorage.setItem('resource', gets["resource"]);
        document.getElementById('resource').value = gets["resource"];
    }
    if("city" in gets) {
        localStorage.setItem('city', gets["city"]);
        document.getElementById('city').value = gets["city"];
    }

    fetch("http://localhost:8080/weather?resource=" + localStorage.getItem("resource") + "&city=" + localStorage.getItem("city")).then(
        res=>{
            res.json().then(
                data=>{
                    console.log(data);
                    if(data.length > 0) {
                        var temp = "";
                        data.forEach((u)=>{
                            temp += "<tr>";
                        temp += "<td>" + u.date + "</td>";
                        temp += "<td>" + u.dayOfWeak + "</td>";
                        temp += "<td>" + u.temperature + " °С</td>";
                        temp += "<td>" + u.humidity + " %</td>";
                        temp += "<td>" + u.wind + " м/c</td>";
                        })
                        document.getElementById("data").innerHTML = temp;
                    }
                }
            )
        }
    )
</script>
</body>
</html>