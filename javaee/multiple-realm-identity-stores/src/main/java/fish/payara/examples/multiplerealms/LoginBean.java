package fish.payara.examples.multiplerealms;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.security.enterprise.AuthenticationStatus;
import javax.security.enterprise.SecurityContext;
import static javax.security.enterprise.authentication.mechanism.http.AuthenticationParameters.withParams;
import javax.security.enterprise.credential.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;

@Named
@RequestScoped
public class LoginBean {

    @Inject
    SecurityContext securityContext;
    
    @NotNull
    private String username;

    @NotNull
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void login() {
        Credential credential = new UsernamePasswordCredential(
                username, new Password(password));
        AuthenticationStatus status = securityContext
                .authenticate(
                        (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest(),
                        (HttpServletResponse)FacesContext.getCurrentInstance().getExternalContext().getResponse(),
                        withParams().credential(credential));
    }

}
