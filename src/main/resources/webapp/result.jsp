<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Quiz result</title>
</head>
<body>

<% if ("correct".equals(request.getAttribute("reply"))) { %>
<p>Congratulations! Would you like to try again?</p>
<input type="submit" value="yes" onClick="window.location.href='play'">
<input type="submit" value="no" onClick="window.location.href='index.jsp'">
<% } else { %>
<p>Oops, that was wrong. Do you want to try again?</p>
<input type="submit" value="yes" onClick="window.location.href='play'">
<input type="submit" value="no" onClick="window.location.href='index.jsp'">
<% } %>

</body>
</html>
