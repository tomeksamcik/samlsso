<!--
<form method="POST" action="j_security_check">
<input type="text" name="j_username">
<input type="password" name="j_password">
<input type="submit">
</form>
-->
<form action="<%= request.getAttribute("OAUTH_FORM_ACTION")%>" method=post>
    <p><strong>Please Enter Your User Name: </strong>
    <input type="text" name="j_username" size="25">
    <p><p><strong>Please Enter Your Password: </strong>
    <input type="password" size="15" name="j_password">
    <p><p>
    <input type="submit" value="Submit">
    <input type="reset" value="Reset">
</form>
