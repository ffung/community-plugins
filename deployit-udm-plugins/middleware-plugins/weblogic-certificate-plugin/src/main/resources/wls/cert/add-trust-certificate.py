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
    ks.load(FileInputStream(deployed.container.keystore), deployed.container.passphrase)
    
    # Load certificate(base64 encoded DER/PEM-encoded certificate)
    print 'Loading the certificate'
    inStream = ByteArrayInputStream(DatatypeConverter.parseBase64Binary(String(deployed.certificate)))
    cf = CertificateFactory.getInstance("X.509")
    cert = cf.generateCertificate(inStream)
    
    # Save the certificate
    print 'Storing certificate %s (alias=%s) in keystore %s' % (deployed.name, deployed.alias, deployed.container.keystore)
    ks.setCertificateEntry(deployed.alias, cert)
    ks.store(FileOutputStream(deployed.container.keystore), deployed.container.passphrase)
    
    print 'Stored certificate %s (alias=%s) in keystore %s' % (deployed.name, deployed.alias, deployed.container.keystore)
else:
    print 'Empty certificate, nothing to do'