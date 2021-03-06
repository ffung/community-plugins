package ext.deployit.community.plugin.wls.contributor;

import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Function;
import com.google.common.base.Predicates;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Iterables;
import com.google.common.collect.Sets;
import com.google.common.primitives.Ints;
import com.xebialabs.deployit.plugin.api.deployment.planning.Contributor;
import com.xebialabs.deployit.plugin.api.deployment.planning.DeploymentPlanningContext;
import com.xebialabs.deployit.plugin.api.deployment.specification.Delta;
import com.xebialabs.deployit.plugin.api.deployment.specification.Deltas;
import com.xebialabs.deployit.plugin.api.deployment.specification.Operation;
import com.xebialabs.deployit.plugin.api.flow.Step;
import com.xebialabs.deployit.plugin.api.reflect.DescriptorRegistry;
import com.xebialabs.deployit.plugin.api.reflect.Type;
import com.xebialabs.deployit.plugin.python.PythonDeploymentStep;
import com.xebialabs.deployit.plugin.wls.container.Server;
import com.xebialabs.deployit.plugin.wls.contributor.Predicates2;
import ext.deployit.community.plugin.wls.container.CustomKeyStore;

public class CustomKeyStoreRestartSSLContributor {
	protected static final Logger logger = LoggerFactory.getLogger(CustomKeyStoreRestartSSLContributor.class);

	@Contributor
	public void restartAfterDeployResourcesOrCopiedArtifact(Deltas deltas,
			DeploymentPlanningContext result) {
		result.addSteps(Iterables.transform(
				// unique on ks.getServer()
				gatherTargets(deltas.getDeltas()),
				new Function<Server, Step>() {
					public Step apply(Server server) {
						return new PythonDeploymentStep(
							  Ints.tryParse((String)server.getProperty("restartSslChannelsOrder")),
							  server.getManagingContainer(),
							  (String)server.getProperty("restartSslChannelsScript"),
								new ImmutableMap.Builder<String, Object>().put("target", server).put("type", server.getType().getName()).build(),
								"Restart SSL channels on " + server);
					}
				}));
	}

	private static Set<Server> gatherTargets(List<Delta> operations) {
		logger.trace("CustomKeyStoreRestartSSLContributor.gatherTargets");
		Type certificateType = DescriptorRegistry.getDescriptor(
				"wls.Certificate").getType();
		Iterable<Delta> certificatesNoNoops = Iterables.filter(operations, Predicates.and(
				Predicates.not(Predicates2.operationIs(Operation.NOOP)),
				Predicates2.deltaOf(certificateType)));

		Iterable<Server> targetedWlsServers = Iterables.transform(certificatesNoNoops,
				new Function<Delta, Server>() {
					public Server apply(Delta delta) {
						return ((CustomKeyStore) Predicates2.extractDeployed()
								.apply(delta).getContainer()).getServer();
					}
				});

		Set<Server> servers = Sets.newHashSet(targetedWlsServers);
	    logger.debug("servers {}", servers);
	    return servers;
	}
}
