<!DOCTYPE html>
 <html>
   <head>
     <title>Add New Team Member</title>
     <meta charset="UTF-8">
     <meta name="viewport" content="width=device-width">
   </head>
   <body>
       <form name="New Team Member Form" action="add-member" method="POST">
       <h2>Register New Team Member</h2>
         <p>Team Member Name: <input type="text"
                      name="name"
                      value="" /></p>

         <p>Team Member Bio: <input type="text"
                      name="bio"
                      value="" /></p>

         <p>Team Member Email: <input type="text"
                        name="email"
                        value=""/></p>

         <p>Date of Birth: <input type="date"
                       name="dateOfBirth"
                       value="1990-01-29" /></p>

         <p>Date of Hire: <input type="date"
                       name="dateOfHire"
                       value="2015-01-01"/></p>

         <input type="submit" value="Create" name="Submit Button"/>

      </form>
      <form action="${pageContext.request.contextPath}/index.jsp">
              <p>
              <input type="submit" value="Home">
              </p>
          </form>
   </body>
 </html>