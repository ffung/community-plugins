from java.io import FileInputStream
from java.io import FileOutputStream
from java.io import ByteArrayInputStream
from java.security import KeyStore
from java.security import KeyFactory
from java.security import PrivateKey
from java.security.cert import CertificateFactory
from java.security.cert import X509Certificate
from java.security.spec import PKCS8EncodedKeySpec
from javax.xml.bind import DatatypeConverter

print 'Install certificate %s to target %s' % (deployed.name, deployed.container.name)

# Load the keystore
print 'Loading keystore %s' % (deployed.container.keystore)
ks = KeyStore.getInstance(deployed.container.keystoreType)
ksfi = FileInputStream(deployed.container.keystore)

try:
    ks.load(ksfi, deployed.container.passphrase)

     # Load certificates (base64 encoded DER/PEM-encoded certificates)
     # http://docs.oracle.com/javase/7/docs/api/java/security/cert/CertificateFactory.html#generateCertificates(java.io.InputStream)
    print 'Loading the certificate(chain)'
    inStream = ByteArrayInputStream(DatatypeConverter.parseBase64Binary(String(deployed.certificate)))
    cf = CertificateFactory.getInstance("X.509")
    chain = cf.generateCertificates(inStream).toArray(jarray.zeros(0, java.security.cert.Certificate))

    # Save the private key entry (base64 PKCS8 DER encoded)
    print 'Storing key/certificate %s (alias=%s) in keystore %s' % (deployed.name, deployed.alias, deployed.container.keystore)
    kf = KeyFactory.getInstance(deployed.keyAlgorithm)
    key = kf.generatePrivate(PKCS8EncodedKeySpec(DatatypeConverter.parseBase64Binary(String(deployed.key))))
    ks.setEntry(deployed.alias, KeyStore.PrivateKeyEntry(key, chain), KeyStore.PasswordProtection(deployed.keyPassword))

    ksfo = FileOutputStream(deployed.container.keystore)
    try:
        ks.store(ksfo, deployed.container.passphrase)
        print 'Stored key/certificate %s (alias=%s) in keystore %s' % (deployed.name, deployed.alias, deployed.container.keystore)
    finally:
        ksfo.close()
finally:
    ksfi.close()
