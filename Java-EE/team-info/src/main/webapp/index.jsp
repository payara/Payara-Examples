<!DOCTYPE html>
 <html>
   <head>
     <title>Search Team Member</title>
     <meta charset="UTF-8">
     <meta name="viewport" content="width=device-width">
   </head>
   <body>
       <form name="Search Team Member to Manage" action="select-member" method="POST">
       <h2>Manage an Existing Team Member</h2>
         <p>Team Member Name: <input type="text"
                      name="name"
                      value="" /></p>
         Add a new team member <a href="${pageContext.request.contextPath}/addmember.jsp">here</a>
         <p>
         <input type="submit" value="Search" name="Submit Button"/>
         </p>
         <div style="color: #FF0000;">${responseMessage}</div>

       </form>
   </body>
 </html>