<!DOCTYPE html>
 <html>
   <head>
     <title>Register New User</title>
     <meta charset="UTF-8">
     <meta name="viewport" content="width=device-width">
   </head>
   <body>
       <form name="Register Form" action="registered" method="POST">
       <h1>Register New User</h1>
         <p>Username: <input type="text"
                      name="username"
                      value="" /></p>

         <p>Password: <input type="password"
                      name="password"
                      value="" /></p>

         <p>Date of Birth: <input type="date"
                           name="dateOfBirth"
                           value="2014-01-29" /></p>

         <input type="submit" value="Register" name="Submit Button"/>

      </form>
   </body>
 </html>