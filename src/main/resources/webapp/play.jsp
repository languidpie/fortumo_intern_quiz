<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.google.gson.Gson" %>
<%@ page import="quiz.view.QuestionView" %>
<html>
<head>
    <title>Quiz</title>
</head>
<body>

<% String questionString = (String) request.getAttribute("question");
    Gson gson = new Gson();
    QuestionView question = gson.fromJson(questionString, QuestionView.class);
%>


<p>Hello, <%= request.getSession().getAttribute("name") %>
</p>
<p>Answer the following question! :)</p>
</p>
<p>Difficulty: <%= question.getDifficulty()%>
</p>
<p>Category: <%= question.getCategory()%>
</p>
<p><%=question.getQuestion()%>
</p>
<form id="insert-answer" action="/answer" method="post">
    <input type="text" name="answer"><br><br>
    <input type="hidden" name="question_id" value="<%= question.getId()%>">
    <input type="hidden" name="name" value="<%= request.getSession().getAttribute("name")%>">
</form>

</body>
</html>
