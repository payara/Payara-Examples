
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JCache CDI Example</title>
    </head>
    <body>
        <h1>This is an example of JCache CDI</h1>
        <p>Click the button to activate a slow method that generates some random text (Make sure hazelcast is enabled)</p>
        <p>The next time you click the button, the same text is generated much faster thanks to caching (try for different lengths!)</p>
        <form action="NewServlet" method="POST">
            <p>Enter the amount of characters to be generated (between 0 and 100)</p>
            <input type ="number" name ="number" value = "30" min = "1" max ="99">
            <input type="submit" value ="GenerateText">
        </form>
        </br>
        ${result}
        </br>
        <form action = "CacheController" method ="POST">
            <input type ="submit" value ="RemoveAllCache">
        </form>
        
    </body>
</html>
