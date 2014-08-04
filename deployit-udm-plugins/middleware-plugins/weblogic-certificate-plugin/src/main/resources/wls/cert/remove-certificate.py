from java.security import KeyStore
from java.security.cert import CertificateFactory
from java.security.cert import X509Certificate
from java.io import FileInputStream
from java.io import FileOutputStream
from java.io import ByteArrayInputStream

print 'Destroy certificate %s from target %s' % (deployed.name, deployed.container.name)

# Load the keystore
print 'Loading keystore %s' % (deployed.container.keystore)
ks = KeyStore.getInstance(deployed.container.keystoreType)
ksfi = FileInputStream(deployed.container.keystore)
try:
    ks.load(ksfi, deployed.container.passphrase)

    if ks.containsAlias(deployed.alias):
        print 'Deleting certificate in the keystore under alias %s' % (deployed.alias)
        ks.deleteEntry(deployed.alias)
        ksfo = FileOutputStream(deployed.container.keystore)
        try:
            ks.store(ksfo, deployed.container.passphrase)
            print 'Deleted certificate %s (alias=%s) from keystore %s' % (deployed.name, deployed.alias, deployed.container.keystore)
        finally:
            ksfo.close()
    else:
        print 'Certificate not found in keystore, nothing to do'
finally:
    ksfi.close()
