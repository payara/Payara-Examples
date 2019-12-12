package fish.payara.examples.multiplerealms;

import fish.payara.security.annotations.FileIdentityStoreDefinition;
import fish.payara.security.annotations.RealmIdentityStoreDefinition;
import javax.enterprise.context.ApplicationScoped;
import javax.security.enterprise.authentication.mechanism.http.*;

@BasicAuthenticationMechanismDefinition  // enables basic HTTP authentication. Can be replaced by another standard mechanism (form, custom form) or by a custom mechanism by implementing HttpAuthenticationMechanism
@FileIdentityStoreDefinition("file")   // this links your application to a File realm called fileRealm, which is defined as a realm on the server
@RealmIdentityStoreDefinition("anotherrealm") // this links your application to an LDAP realm called ldapRealm, which is defined as a realm on the server
@ApplicationScoped  // to turn this class into a CDI bean
public class AuthenticationConfig {

}
