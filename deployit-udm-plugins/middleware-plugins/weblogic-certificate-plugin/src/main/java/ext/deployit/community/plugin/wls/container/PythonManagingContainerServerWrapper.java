package ext.deployit.community.plugin.wls.container;

import java.util.Map;
import java.util.Set;

import com.xebialabs.deployit.plugin.api.reflect.Type;
import com.xebialabs.deployit.plugin.api.udm.Metadata;
import com.xebialabs.deployit.plugin.api.udm.base.BaseConfigurationItem;
import com.xebialabs.deployit.plugin.overthere.Host;
import com.xebialabs.deployit.plugin.python.PythonManagingContainer;
import com.xebialabs.deployit.plugin.wls.container.Server;
import com.xebialabs.overthere.CmdLine;
import com.xebialabs.overthere.OverthereFile;

/**
 * 
 * wrapper for com.xebialabs.deployit.plugin.wls.container.Server to make it a PythonManagingContainer. Delegates all PythonManagingContainer methods
 * except getHost() to the Domain of the server. This implies that properties like runtimePath are identical on both this server and the adminServer.
 * It also implies that the Server must have its Host set. 
 *
 */
@Metadata(description="internal wrapper for wls.Server to allow Python scripts to run on server rather than domain", virtual=true)
public class PythonManagingContainerServerWrapper extends BaseConfigurationItem implements PythonManagingContainer {

	private static final long serialVersionUID = 4156722146055273113L;
	private final Server server;

	public PythonManagingContainerServerWrapper(Server server) {
		this.server = server;
		
	}
	
	@Override
	public void setTags(Set<String> tags) {
		getServer().setTags(tags);				
	}
	
	@Override
	public Set<String> getTags() {
		return getServer().getTags();
	}
	
	@Override
	public <T> void setProperty(String key, T value) {
		getServer().setProperty(key, value);
	}
	
	@Override
	public void setId(String id) {
		getServer().setId(id);
	}
	
	@Override
	public <T> void putSyntheticProperty(String key, T value) {
		getServer().putSyntheticProperty(key, value);
	}
	
	@Override
	public boolean hasProperty(String key) {
		return getServer().hasProperty(key);
	}
	
	@Override
	public Type getType() {
		return getServer().getType();
	}
	
	@Override
	public <T> T getSyntheticProperty(String key) {
		return getServer().getSyntheticProperty(key);
	}
	
	@Override
	public Map<String, Object> getSyntheticProperties() {
		return getServer().getSyntheticProperties();
	}
	
	@Override
	public <T> T getProperty(String key) {
		return getServer().getProperty(key);
	}
	
	@Override
	public String getName() {
		return getServer().getName();
	}
	
	@Override
	public String getId() {
		return getServer().getId();
	}
	
	@Override
	public boolean runWithDaemon() {
		return getServer().getDomain().runWithDaemon();
	}
	
	@Override
	public CmdLine getScriptCommandLine(OverthereFile script) {
		return getServer().getDomain().getScriptCommandLine(script);
	}
	
	@Override
	public String getRuntimePath() {
		return getServer().getDomain().getRuntimePath();
	}
	
	@Override
	public Host getHost() {
		return getServer().getHost();
	}
	
	public Server getServer() {
		return server;
	}
}
