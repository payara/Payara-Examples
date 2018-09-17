package fish.payara.examples.hello.world.rest.filter.annotations;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import java.lang.annotation.Retention;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import java.lang.annotation.Target;
import javax.ws.rs.NameBinding;

@Target({TYPE, METHOD})
@Retention(RUNTIME)
@NameBinding
public @interface TracedEndpoint {

}
