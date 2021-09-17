# OCSP validation

Example Client Certificate Validator that performs an OCSP ( Online Certificate Status Protocol ) check on a certificate presented for validation to the endpoint.

To perform the OCSP call, it requires the certificate that issued the certificate presented to the endpoint. This issuer certificate is looked up in a KeyStore identified by the MP Config key `fish.payara.example.security.keystore.issuer.path` (KeyStore is read with the password found in the key `fish.payara.example.security.keystore.issuer.password` ).

For more info, look at the `MyValidation` class.