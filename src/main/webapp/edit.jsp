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
<form action="/redisEditServlet" method="post">
field(只读)：<input type="text"  readonly="readonly" id="field" name="field" value="${param.field}"><br>
学  号(只读)：<input type="text" readonly="readonly" id="stuId" name="stuId" value="${param.stuId}"><br>
姓  名：<input type="text" id="stuName" name="stuName" value="${param.stuName}"><br>
出生日期：<input type="text" id="birthday" name="birthday" value="${param.birthday}"><br>
描  述：<input type="text" id="description" name="description" value="${param.description}"><br>
平均 分：<input type="text" id="avgscore" name="avgscore" value="${param.avgscore}"><br>
<input type="submit" value="提交">
</form>

</body>
</html>
