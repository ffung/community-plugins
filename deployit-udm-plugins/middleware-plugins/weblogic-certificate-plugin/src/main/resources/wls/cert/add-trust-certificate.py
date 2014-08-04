from java.security import KeyStore
from java.security.cert import CertificateFactory
from java.security.cert import X509Certificate
from java.io import FileInputStream
from java.io import FileOutputStream
from java.io import ByteArrayInputStream
from javax.xml.bind import DatatypeConverter

print 'Install certificate %s to target %s' % (deployed.name, deployed.container.name)

if deployed.certificate:
    # Load the keystore
    print 'Loading keystore %s' % (deployed.container.keystore)
    ks = KeyStore.getInstance(deployed.container.keystoreType)

    ksfi = FileInputStream(deployed.container.keystore)
    try:
        ks.load(ksfi, deployed.container.passphrase)

        # Load certificate(base64 encoded DER/PEM-encoded certificate)
        print 'Loading the certificate'
        inStream = ByteArrayInputStream(DatatypeConverter.parseBase64Binary(String(deployed.certificate)))
        cf = CertificateFactory.getInstance("X.509")
        cert = cf.generateCertificate(inStream)

        # Save the certificate
        print 'Storing certificate %s (alias=%s) in keystore %s' % (deployed.name, deployed.alias, deployed.container.keystore)
        ks.setCertificateEntry(deployed.alias, cert)

        ksfo = FileOutputStream(deployed.container.keystore)
        try:
            ks.store(ksfo, deployed.container.passphrase)
            print 'Stored certificate %s (alias=%s) in keystore %s' % (deployed.name, deployed.alias, deployed.container.keystore)
        finally:
            ksfo.close()
    finally:
        ksfi.close()
else:
    print 'Empty certificate, nothing to do'