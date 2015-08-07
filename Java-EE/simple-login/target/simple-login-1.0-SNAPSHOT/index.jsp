<!DOCTYPE html>
 <html>
   <head>
     <title>Login Page</title>
     <meta charset="UTF-8">
     <meta name="viewport" content="width=device-width">
   </head>
   <body>
       <form name="Login Form" action="dashboard" method="POST">
       <h1>Login As Existing User </h1>
         <p>Username: <input type="text"
                      name="username"
                      value="" /></p>

         <p>Password: <input type="password"
                      name="password"
                      value="" /></p>
         Don't have an existing user? <a href="${pageContext.request.contextPath}/register.jsp">here</a>
         <br>
         <input type="submit" value="Log In" name="Submit Button"/>

       </form>
   </body>
 </html>