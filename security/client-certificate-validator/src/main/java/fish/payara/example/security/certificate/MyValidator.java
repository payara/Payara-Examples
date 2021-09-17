/*
  Copyright (c) 2021 Payara Foundation and/or its affiliates. All rights reserved.

  The contents of this file are subject to the terms of either the GNU
  General Public License Version 2 only ("GPL") or the Common Development
  and Distribution License("CDDL") (collectively, the "License").  You
  may not use this file except in compliance with the License.  You can
  obtain a copy of the License at
  https://github.com/payara/Payara/blob/master/LICENSE.txt
  See the License for the specific
  language governing permissions and limitations under the License.

  When distributing the software, include this License Header Notice in each
  file and include the License file at glassfish/legal/LICENSE.txt.

  GPL Classpath Exception:
  The Payara Foundation designates this particular file as subject to the "Classpath"
  exception as provided by the Payara Foundation in the GPL Version 2 section of the License
  file that accompanied this code.

  Modifications:
  If applicable, add the following below the License Header, with the fields
  enclosed by brackets [] replaced by your own identifying information:
  "Portions Copyright [year] [name of copyright owner]"

  Contributor(s):
  If you wish your version of this file to be governed by only the CDDL or
  only the GPL Version 2, indicate your decision by adding "[Contributor]
  elects to include this software in this distribution under the [CDDL or GPL
  Version 2] license."  If you don't indicate a single choice of license, a
  recipient has the option to distribute your version of this file under
  either the CDDL, the GPL Version 2 or to extend the choice of license to
  its licensees as provided above.  However, if you add GPL Version 2 code
  and therefore, elected the GPL Version 2 license, then the option applies
  only if the new code is made subject to such option by the copyright
  holder.
 */
package fish.payara.example.security.certificate;

import fish.payara.security.client.ClientCertificateValidator;
import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.ConfigProvider;
import sun.security.provider.certpath.OCSP;
import sun.security.x509.X509CertImpl;

import javax.security.auth.Subject;
import javax.security.auth.x500.X500Principal;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.*;

public class MyValidator implements ClientCertificateValidator {

    private static final String ISSUER_KEY_STORE_LOCATION_KEY = "fish.payara.example.security.keystore.issuer.path";
    private static final String ISSUER_KEY_STORE_PASSWORD = "fish.payara.example.security.keystore.issuer.password";

    private KeyStore issuerKeyStore;

    @Override
    public boolean isValid(Subject subject, X500Principal principal, X509Certificate certificate) {
        boolean result = true;

        // Validity check
        try {
            certificate.checkValidity();
        } catch (CertificateExpiredException | CertificateNotYetValidException e) {
            result = false;
        }

        // check Online Certificate Status Protocol (OCSP) and/OR Certificate Revocation List (CRL)
        if (result) {
            try {
                if (issuerKeyStore == null) {
                    issuerKeyStore = getKeyStore();
                }

                String issuerPrincipal = certificate.getIssuerX500Principal().getName(X500Principal.RFC2253);

                X509Certificate issuerCertificate = findIssuerCertificate(issuerPrincipal, issuerKeyStore);
                if (issuerCertificate != null) {
                    OCSP.RevocationStatus revocationStatus = check(certificate, issuerCertificate);
                    if (revocationStatus.getCertStatus() != OCSP.RevocationStatus.CertStatus.GOOD) {
                        result = false;
                    }
                }
            } catch (KeyStoreException | IOException | NoSuchAlgorithmException | CertificateException | CertPathValidatorException | InvalidAlgorithmParameterException e) {
                result = false;
            }
        }

        // Custom checks

        if (result) {

            // Implement any custom check.
        }

        return result;
    }


    private static X509Certificate findIssuerCertificate(String issuerPrincipal, KeyStore keystore) throws KeyStoreException, InvalidAlgorithmParameterException, IOException, CertPathValidatorException, CertificateException {
        X509Certificate result = null;
        PKIXParameters params = new PKIXParameters(keystore);
        for (TrustAnchor ta : params.getTrustAnchors()) {
            // Get certificate
            X509Certificate trustedCert = ta.getTrustedCert();

            String principal = trustedCert.getSubjectX500Principal().getName(X500Principal.RFC2253);

            if (issuerPrincipal.equals(principal)) {
                result = trustedCert;
                break;
            }
        }
        return result;
    }

    private static KeyStore getKeyStore() throws KeyStoreException, IOException, NoSuchAlgorithmException, CertificateException {
        Config config = ConfigProvider.getConfig();
        String password = config.getValue(ISSUER_KEY_STORE_PASSWORD, String.class);
        String path = config.getValue(ISSUER_KEY_STORE_LOCATION_KEY, String.class);

        FileInputStream is = new FileInputStream(path);
        KeyStore keystore = KeyStore.getInstance(KeyStore.getDefaultType());
        keystore.load(is, password.toCharArray());
        is.close();
        return keystore;
    }

    public static OCSP.RevocationStatus check(X509Certificate cert,
                                              X509Certificate issuerCert)
            throws IOException, CertPathValidatorException, CertificateException {
        URI responderURI;

        X509CertImpl certImpl = X509CertImpl.toImpl(cert);
        responderURI = OCSP.getResponderURI(certImpl);
        if (responderURI == null) {
            throw new CertPathValidatorException("No OCSP Responder URI in certificate");
        }
        return OCSP.check(cert, issuerCert, responderURI, cert, null);
    }
}
