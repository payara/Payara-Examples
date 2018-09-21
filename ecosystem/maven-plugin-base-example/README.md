This is a sample web application to example the use of ``payara-micro-maven-plugin``.

``payara-micro-maven-plugin`` provides 2 goals, ``bundle`` and ``start``. After defining our plugin, we'll first bundle payara-micro within our webapp
and start it in ``uberJar`` mode, meaning that using the created bundled jar in ``target``folder.  

The plugin configuration is as follows:
```
<plugin>
    <groupId>fish.payara.maven.plugins</groupId>
    <artifactId>payara-micro-maven-plugin</artifactId>
    <version>1.0.2</version>
    <executions>
        <execution>
            <id>bundle</id>
            <goals>
                <goal>bundle</goal>
            </goals>
        </execution>
        <execution>
            <id>start</id>
            <goals>
                <goal>start</goal>
            </goals>
        </execution>
    </executions>
    <configuration>
        <useUberJar>true</useUberJar>
    </configuration>
</plugin>
```
In order to bundle and start our application, executing 
```
mvn clean install payara-micro:start
```
would be enough. We don't need to execute ``bundle`` goal by its own since it's already attached to ``install`` phase.