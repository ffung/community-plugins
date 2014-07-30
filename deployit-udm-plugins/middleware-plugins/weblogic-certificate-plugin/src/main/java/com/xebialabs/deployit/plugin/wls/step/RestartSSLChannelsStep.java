package com.xebialabs.deployit.plugin.wls.step;

import com.google.common.collect.ImmutableMap;
import com.xebialabs.deployit.plugin.python.PythonDeploymentStep;
import com.xebialabs.deployit.plugin.wls.container.Server;
import com.xebialabs.deployit.plugin.wls.container.WlsContainer;

public class RestartSSLChannelsStep extends PythonDeploymentStep {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4733671172821837893L;

	public RestartSSLChannelsStep(int order, Server target) {
		super(order, target.getManagingContainer(),
				"wls/container/restart_ssl_channels.py",
				buildPythonVars(target), "Restart SSL channels " + target);
	}

	private static ImmutableMap<String, Object> buildPythonVars(
			WlsContainer target) {
		return new ImmutableMap.Builder<String, Object>().put("target", target).put("type", "Server").build();
	}
}