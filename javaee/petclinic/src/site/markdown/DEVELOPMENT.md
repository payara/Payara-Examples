# Development
 
## Developer Reminder
* Create serialVersionUID for Classes with "implements Serializable": use the JDK tool “serialver“
  
## Frontend 
  
### Drag and Drop
* [jquery-drag-and-drop-plugins](https://jqueryhouse.com/jquery-drag-and-drop-plugins/)
* [jquery draggable](https://mdbootstrap.com/plugins/jquery/draggable/#introduction)
* [shopify draggable: UniqueDropzone](https://github.com/Shopify/draggable/tree/master/examples/src/content/Droppable/UniqueDropzone)
* [shopify draggable: documentation](https://github.com/Shopify/draggable#documentation)
* [shopify draggable: example](https://shopify.github.io/draggable/examples/simple-list.html)
* [drag-and-drop-with-jquery-your-essential-guide](https://www.elated.com/drag-and-drop-with-jquery-your-essential-guide/)
* [using-jquery-ui-drag-drop](https://medium.com/@okandavut/using-jquery-ui-drag-drop-64a24e75e805)
  
### Shortcuts
* [fontawesome](https://fontawesome.com/icons?d=gallery&m=free)
* [ckeditor](https://ckeditor.com/docs/ckeditor4/latest/guide/dev_installation.html)

### Templates
* [thymeleaf](https://www.thymeleaf.org/doc/tutorials/3.0/usingthymeleaf.html)

## Backend 
* [Alternative PostgreSQL JDBC 4.2* Driver](http://impossibl.github.io/pgjdbc-ng/docs/current/user-guide/#drivers)
    
## Heroku
### java
* [heroku: getting-started-with-java](https://devcenter.heroku.com/articles/getting-started-with-java)
* [heroku: java-support java-version](https://devcenter.heroku.com/articles/java-support#specifying-a-java-version)

### Running Locally
TBD:  
Make sure you have Java and Maven installed.  Also, install the [Heroku CLI](https://cli.heroku.com/).
  
```sh
$ git clone https://github.com/heroku/java-getting-started.git
$ cd java-getting-started
$ mvn install
$ heroku local:start
```
  
Your app should now be running on [localhost:5000](http://localhost:5000/).

If you're going to use a database, ensure you have a local `.env` file that reads something like this:
  
```
JDBC_DATABASE_URL=jdbc:postgresql://localhost:5432/java_database_name
```
  
#### Deploying to Heroku

```sh
$ heroku create
$ git push heroku master
$ heroku open
```
  
#### Documentation
For more information about using Java on Heroku, see these Dev Center articles: 
* [Java on Heroku](https://devcenter.heroku.com/categories/java)
