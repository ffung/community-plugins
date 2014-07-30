# Apache Weblogic plugin #

This document describes the functionality provided by the Weblogic Certificate plugin.

See the **Deployit Reference Manual** for background information on Deployit and deployment concepts.

# Overview #


The Weblogic Certificate plugin is a Deployit plugin used to deploy certificates and keys and deploy them to a CustomIdentityKeyStore or CustomTrustKeyStore.

##Features##

* Install a certificate;
* Modify a certificate / key
* Remove a certificate / key
* Restart SSL step on affected managed servers

# Requirements #

* **Deployit requirements**
	* **Deployit**: version 4.0.+
	* **Other Deployit Plugins**: wls-plugin-4.0.x

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
