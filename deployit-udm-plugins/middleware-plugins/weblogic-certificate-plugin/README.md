#  Weblogic Certificate plugin #

This document describes the functionality provided by the Weblogic Certificate plugin.

See the **XL Deploy Reference Manual** for background information on XL Deploy and deployment concepts.

# Overview #


The Weblogic Certificate plugin is a XL Deploy plugin used to deploy certificates and keys to a CustomIdentityKeyStore or CustomTrustKeyStore. See [_Configuring keystores_](http://docs.oracle.com/cd/E24329_01/apirefs.1211/e24401/taskhelp/security/ConfigureKeystoresAndSSL.html) for details.

##Features##

* Install a certificate;
* Modify a certificate / key
* Remove a certificate / key
* Adds restart SSL step on affected managed servers

# Requirements #

* **XL Deploy requirements**
	* **XL Deploy**: version 4.0.+
	* **Other XL Deploy Plugins**: wls-plugin-4.0.x

# Installation

Place the plugin JAR file into your `SERVER_HOME/plugins` directory. 

# Usage #

The plugin works with the standard deployment package of DAR format. Please see the [_Packaging Manual_](http://docs.xebialabs.com/releases/4.0/deployit/packagingmanual.html) for more details about the DAR format and the ways to compose one. 

The following is a sample deployment-manifest.xml file that can be used to install a certificate.
```xml
<udm.DeploymentPackage version="1.0" application="CertificateApp">
  <orchestrator />
  <deployables>
    <wls.TrustCertificateSpec name="some-ca">
      <tags />
      <alias>some-ca</alias>
      <certificate>MIIF....</certificate>
    </wls.TrustCertificateSpec>
    <wls.IdentityCertificateSpec name="some-identity">
      <tags />
      <alias>some-identity</alias>
      <certificate>MIIF....</certificate>
      <key>MIIE...</key>
    </wls.IdentityCertificateSpec>
  </deployables>
</udm.DeploymentPackage>
```
