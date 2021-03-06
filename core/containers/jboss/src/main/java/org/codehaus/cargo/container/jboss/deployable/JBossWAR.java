/*
 * ========================================================================
 *
 * Codehaus CARGO, copyright 2004-2011 Vincent Massol, 2012-2015 Ali Tokmen.
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
package org.codehaus.cargo.container.jboss.deployable;

import java.io.File;

import org.codehaus.cargo.container.ContainerException;
import org.codehaus.cargo.container.deployable.WAR;
import org.codehaus.cargo.module.webapp.jboss.JBossWarArchive;

/**
 * Extension that supports custom JBoss descriptor files such as the <code>jboss-web.xml</code> one.
 * For example, this allows returning the right web context even if it has been defined in the
 * <code>jboss-web.xml</code> file.
 *
 */
public class JBossWAR extends WAR
{
    /**
     * The parsed JBoss descriptors in the WAR.
     */
    private JBossWarArchive warArchive;

    /**
     * @param war the location of the WAR being wrapped. This must point to either a WAR file or an
     * expanded WAR directory.
     */
    public JBossWAR(String war)
    {
        super(war);

        try
        {
            this.warArchive = new JBossWarArchive(new File(getFile()));
        }
        catch (Exception e)
        {
            throw new ContainerException("Failed to parse JBoss WAR file "
                + "in [" + getFile() + "]", e);
        }
    }

    /**
     * @return the context defined in <code>jboss-web.xml</code> if any (including, if present, the
     * virtual host name as a prefix). If there is no <code>jboss-web.xml</code> or if it doesn't
     * define any root context, then return {@link WAR#getContext()}.
     */
    @Override
    public synchronized String getContext()
    {
        String context = null;

        if (this.warArchive.getJBossWebXml() != null)
        {
            context = this.warArchive.getJBossWebXml().getContextRoot();
            if (context != null)
            {
                if ("".equals(context) || "/".equals(context))
                {
                    context = "ROOT";
                }

                String virtualHost = this.warArchive.getJBossWebXml().getVirtualHost();
                if (virtualHost != null)
                {
                    context = virtualHost + '-' + context;
                }
            }
        }

        if (context == null)
        {
            context = super.getContext();
        }

        return context;
    }

    /**
     * @return the virtual host element found in the <code>jboss-web.xml</code> file or null if not
     * defined
     */
    public String getVirtualHost()
    {
        String virtualHost = null;

        if (this.warArchive.getJBossWebXml() != null)
        {
            virtualHost = this.warArchive.getJBossWebXml().getVirtualHost();
        }

        return virtualHost;
    }

    /**
     * @return true if the WAR contains a <code>WEB-INF/jboss-web.xml</code> file
     */
    public boolean containsJBossWebFile()
    {
        return this.warArchive.getJBossWebXml() != null;
    }
}
