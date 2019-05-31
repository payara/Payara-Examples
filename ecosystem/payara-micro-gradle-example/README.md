This is a sample web application to example the use of ``fish.payara.micro-gradle-plugin``.

``fish.payara.micro-gradle-plugin`` provides 3 goals, ``microBundle``, ``microStart`` and ``microStop``. After defining our plugin, we'll first bundle payara-micro within our webapp
and start it in ``uberJar`` mode, meaning that using the created bundled jar in ``target``folder.  

The plugin configuration is as follows:
```
plugins {
  id "fish.payara.micro-gradle-plugin" version "1.0.1"
}

payaraMicro {
    deployWar = true 
    useUberJar = false
    daemon = false
    commandLineOptions = [port: 8787]
    javaCommandLineOptions = [Dtest: 'test123', ea:true] 
}
```

In order to start our application, executing ``gradle microStart`` would be enough to deploy a war file
 and application will be accessible on ``http://localhost:8787/payara-micro-gradle-example-1.0-SNAPSHOT``.