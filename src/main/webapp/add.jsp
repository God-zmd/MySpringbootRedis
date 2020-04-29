<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2020/4/27
  Time: 10:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page isELIgnored="false"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form action="/redisAddServlet" method="post">
    学号：<input type="text" name="stuId"><br>
    姓名：<input type="text" name="stuName"><br>
    出生日期：<input type="date" name="birthday"><br>
    描述：<input type="text" name="description"><br>
    平均分：<input type="text" name="avgscore"><br>
    <input type="submit" value="提交">
</form>

</body>
</html>
