This is an example for using ``payara-micro-maven-plugin`` with its customizing the ``Start-Class`` feature.   


- **Configuration**:
You need to define the ``payara-micro-maven-plugin`` with its ``bundle`` goal in order to attach the plugin execution to the ``ìnstall`` phase.
The ``startClass`` property defines the custom start class that will be used to bootstrap payara micro. This should be implemented as one class only with this single responsibility.

```
<plugin>
    <groupId>fish.payara.maven.plugins</groupId>
    <artifactId>payara-micro-maven-plugin</artifactId>
    <version>1.0.2</version>
    <executions>
        <execution>
            <goals>
                <goal>bundle</goal>
            </goals>
        </execution>
    </executions>
    <configuration>
        <startClass>fish.payara.maven.plugin.PayaraBootstrapper</startClass>
        <payaraVersion>5.182</payaraVersion>
    </configuration>
</plugin>
```
- **Implementation**

Start class implementation would be as follows.
 
```
public class PayaraBootstrapper {

    public static void main(String... args) {
        try {
            PayaraMicro instance = PayaraMicro.getInstance();
            instance.bootStrap();
        } catch (BootstrapException e) {
            e.printStackTrace();
        }
    }
}
```

- **Build**:

Execute ``mvn clean install`` in order to build the uber-jar suffixed with ``microbundle`` in ``target``folder. The created uber-jar file will be named as: ``maven-plugin-with-start-class-1.0-microbundle.jar`` in our case.
  
- **Run**:

```
java -jar maven-plugin-with-start-class-1.0-microbundle.jar
```

- **Try**:

Navigate to ``http://localhost:8080/hello/world` and you will see the output: ``Hello World!`` in your browser.  