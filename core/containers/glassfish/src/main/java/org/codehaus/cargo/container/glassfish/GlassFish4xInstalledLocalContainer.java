/*
 * ========================================================================
 *
 * Codehaus CARGO, copyright 2004-2011 Vincent Massol.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * ========================================================================
 */
package org.codehaus.cargo.container.glassfish;

import org.codehaus.cargo.container.ContainerCapability;
import org.codehaus.cargo.container.configuration.LocalConfiguration;
import org.codehaus.cargo.container.glassfish.internal.AbstractGlassFishInstalledLocalDeployer;

/**
 * GlassFish 4.x installed local container.
 * 
 * @version $Id$
 */
public class GlassFish4xInstalledLocalContainer extends GlassFish3xInstalledLocalContainer
{

    /**
     * Container capability instance.
     */
    private static final ContainerCapability CAPABILITY = new GlassFish4xContainerCapability();

    /**
     * Calls parent constructor, which saves the configuration.
     * 
     * @param localConfiguration Configuration.
     */
    public GlassFish4xInstalledLocalContainer(LocalConfiguration localConfiguration)
    {
        super(localConfiguration);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected AbstractGlassFishInstalledLocalDeployer getLocalDeployer()
    {
        return new GlassFish4xInstalledLocalDeployer(this);
    }

    /**
     * {@inheritDoc}
     */
    public ContainerCapability getCapability()
    {
        return CAPABILITY;
    }

    /**
     * {@inheritDoc}
     */
    public String getId()
    {
        return "glassfish4x";
    }

    /**
     * {@inheritDoc}
     */
    public String getName()
    {
        return "GlassFish 4.x";
    }

}