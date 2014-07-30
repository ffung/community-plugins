package com.xebialabs.deployit.plugin.wls.container;

import com.xebialabs.deployit.plugin.api.inspection.Inspect;
import com.xebialabs.deployit.plugin.api.inspection.InspectionContext;
import com.xebialabs.deployit.plugin.api.udm.Metadata;
import com.xebialabs.deployit.plugin.api.udm.Property;
import com.xebialabs.deployit.plugin.api.udm.base.BaseContainer;
import com.xebialabs.deployit.plugin.overthere.Host;
import com.xebialabs.deployit.plugin.overthere.HostContainer;
import com.xebialabs.deployit.plugin.python.PythonManagedContainer;
import com.xebialabs.deployit.plugin.python.PythonManagingContainer;

@Metadata(description = "Keystores ensure the secure storage and management of private keys and trusted certificate authorities (CAs).", virtual = true)
public class CustomKeyStore extends BaseContainer implements
		PythonManagedContainer, HostContainer {
	private static final long serialVersionUID = -231510225511705527L;

	@Property(required = true, asContainment = true, description = "A server instance this custom keystore is deployed to. 'asContainment'=true, means a custom keystore is 'contained' under a Server")
	private Server server;

	public CustomKeyStore() {
	}

	@Override
	public PythonManagingContainer getManagingContainer() {
		/* deployment is performed via the managed Server */
		return new PythonManagingContainerServerWrapper(server);
	}

	@Override
	public Host getHost() {
		return getServer().getHost();
	}

	public Server getServer() {
		return server;
	}

	public void setServer(Server server) {
		this.server = server;
	}

	@Inspect
	public void inspect(InspectionContext ctx) {
		/* let subclass or container perform inspection */
	}
}
