/*
 * @(#)ExecutedSqlScript.java     1 Sep 2011
 *
 * Copyright © 2010 Andrew Phillips.
 *
 * ====================================================================
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or 
 * implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * ====================================================================
 */
package com.xebialabs.deployit.plugins.generic.ext.deployed;

import static com.google.common.collect.Maps.newHashMap;

import java.util.Map;

import com.xebialabs.deployit.plugin.api.udm.DeployableArtifact;
import com.xebialabs.deployit.plugin.api.udm.Metadata;
import com.xebialabs.deployit.plugin.api.udm.Property;
import com.xebialabs.deployit.plugin.api.udm.artifact.Artifacts;
import com.xebialabs.deployit.plugin.api.udm.artifact.DerivedArtifact;
import com.xebialabs.deployit.plugin.api.udm.artifact.PlaceholderReplacer;
import com.xebialabs.overthere.OverthereFile;

@SuppressWarnings("serial")
@Metadata(virtual = true, description = "A script executed on a generic container whose deployable artifact supports placeholder replacement")
public abstract class ExecutedScriptForCustomizableArtifact<D extends DeployableArtifact> extends ScriptPropertyResolvingExecutedScript<D> implements DerivedArtifact<D> {

    @Property(required = false, category= "Placeholders", description = "A key/value pair mapping of placeholders in the deployed artifact to their values. Special values are <ignore> and <empty>")
    private Map<String, String> placeholders = newHashMap();

    private OverthereFile placeholderProcessedFile;
    
    @Override
    public D getSourceArtifact() {
        return getDeployable();
    }

    @Override
    public void initFile(PlaceholderReplacer replacer) {
        Artifacts.replacePlaceholders(this, replacer);
    }

    @Override
    public Map<String, String> getPlaceholders() {
        return placeholders;
    }

    @Override
    public void setPlaceholders(Map<String, String> placeholders) {
        this.placeholders = placeholders;
    }
    
    @Override
    public OverthereFile getFile() {
        return placeholderProcessedFile;
    }

    @Override
    public void setFile(OverthereFile file) {
        this.placeholderProcessedFile = file;
    }
}
