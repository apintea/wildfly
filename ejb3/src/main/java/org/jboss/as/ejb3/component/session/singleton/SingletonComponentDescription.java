/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2010, Red Hat, Inc., and individual contributors
 * as indicated by the @author tags. See the copyright.txt file in the
 * distribution for a full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */

package org.jboss.as.ejb3.component.session.singleton;

import org.jboss.as.ee.component.AbstractComponentConfiguration;
import org.jboss.as.ejb3.component.session.SessionBeanComponentDescription;

import javax.ejb.ConcurrencyManagementType;

/**
 * Component description for a singleton bean
 *
 * @author Jaikiran Pai
 */
public class SingletonComponentDescription extends SessionBeanComponentDescription {

    /**
     * Flag to indicate whether the singleton bean is a @Startup (a.k.a init-on-startup) bean
     */
    private boolean initOnStartup;

    /**
     * The {@link ConcurrencyManagementType} for this singleton bean
     */
    private ConcurrencyManagementType concurrencyManagementType;

    /**
     * Construct a new instance.
     *
     * @param componentName      the component name
     * @param componentClassName the component instance class name
     * @param moduleName         the module name
     * @param applicationName    the application name
     */
    public SingletonComponentDescription(final String componentName, final String componentClassName, final String moduleName, final String applicationName) {
        super(componentName, componentClassName, moduleName, applicationName);
    }

    @Override
    protected AbstractComponentConfiguration constructComponentConfiguration() {
        return new SingletonComponentConfiguration(this);
    }

    /**
     * Returns true if the singleton bean is marked for init-on-startup (a.k.a @Startup). Else
     * returns false
     *
     * @return
     */
    public boolean isInitOnStartup() {
        return this.initOnStartup;
    }

    /**
     * Marks the singleton bean for init-on-startup
     */
    public void initOnStartup() {
        this.initOnStartup = true;
    }

    /**
     * Returns the concurrency management type for this singleton bean.
     * <p/>
     * This method returns null if the concurrency management type hasn't explicitly been set on this
     * {@link SingletonComponentDescription}
     *
     * @return
     */
    public ConcurrencyManagementType getConcurrencyManagementType() {
        return this.concurrencyManagementType;
    }

    /**
     * Marks the singleton bean for bean managed concurrency.
     *
     * @throws IllegalStateException If the bean has already been marked for a different concurrency management type
     */
    public void beanManagedConcurrency() {
        if (this.concurrencyManagementType != null && this.concurrencyManagementType != ConcurrencyManagementType.BEAN) {
            throw new IllegalStateException(this.getEJBName() + " bean has been marked for " + this.concurrencyManagementType + " cannot change it now!");
        }
        this.concurrencyManagementType = ConcurrencyManagementType.BEAN;
    }

    /**
     * Marks this singleton bean for container managed concurrency.
     *
     * @throws IllegalStateException If the bean has already been marked for a different concurrency management type
     */
    public void containerManagedConcurrency() {
        if (this.concurrencyManagementType != null && this.concurrencyManagementType != ConcurrencyManagementType.CONTAINER) {
            throw new IllegalStateException(this.getEJBName() + " bean has been marked for " + this.concurrencyManagementType + " cannot change it now!");
        }
        this.concurrencyManagementType = ConcurrencyManagementType.CONTAINER;
    }
}
