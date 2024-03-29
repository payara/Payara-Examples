package org.woehlke.jakartaee.petclinic.deployments;

import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;

import java.io.File;

/**
 * Created with IntelliJ IDEA.
 * User: tw
 * Date: 20.01.14
 * Time: 08:45
 * To change this template use File | Settings | File Templates.
 */
public class Deployments {

    public static WebArchive createDeployment() {
        String archiveName = "target" + File.separator+ "petclinic.war";
        File archive = new File(archiveName);
        return ShrinkWrap.createFromZipFile(WebArchive.class,archive);
    }
}
