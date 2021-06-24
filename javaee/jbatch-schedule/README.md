# Schedule a batch job in regular intervals

Contains:

* [jbatch-schedule.xml](src/main/resources/META-INF/batch-jobs/jbatch-schedule.xml) - a definition of the batch job, defines a property and a single task that executes a batchlet.
* [SimpleBatchlet.java](src/main/java/fish/payara/examples/jbatch/schedule/SimpleBatchlet.java) - the batchlet that reads a file name from the job property and writes a line with timestamp into that file.
* [SimpleSchedule.java](src/main/java/fish/payara/examples/jbatch/ejb/SimpleSchedule.java) - an EJB component with a schedule that triggers it every 30 seconds. The EJB then starts the batch job as defined by the XML definition.
