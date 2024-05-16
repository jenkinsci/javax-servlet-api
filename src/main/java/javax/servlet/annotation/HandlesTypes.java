/*
 * Copyright (c) 2017, 2018 Oracle and/or its affiliates and others.
 * All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0, which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * This Source Code may also be made available under the following Secondary
 * Licenses when the conditions for such availability set forth in the
 * Eclipse Public License v. 2.0 are satisfied: GNU General Public License,
 * version 2 with the GNU Classpath Exception, which is available at
 * https://www.gnu.org/software/classpath/license.html.
 *
 * SPDX-License-Identifier: EPL-2.0 OR GPL-2.0 WITH Classpath-exception-2.0
 */

package javax.servlet.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation is used to declare the class types that a {@link javax.servlet.ServletContainerInitializer
 * ServletContainerInitializer} can handle.
 *
 * @see javax.servlet.ServletContainerInitializer
 *
 * @since Servlet 3.0
 */
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface HandlesTypes {

    /**
     * The classes in which a {@link javax.servlet.ServletContainerInitializer ServletContainerInitializer} has
     * expressed interest.
     *
     * <p>
     * If an implementation of <code>ServletContainerInitializer</code> specifies this annotation, the Servlet container
     * must pass the <code>Set</code> of application classes that extend, implement, or have been annotated with the class
     * types listed by this annotation to the {@link javax.servlet.ServletContainerInitializer#onStartup} method of the
     * ServletContainerInitializer (if no matching classes are found, <code>null</code> must be passed instead)
     * 
     * @return the classes in which {@link javax.servlet.ServletContainerInitializer ServletContainerInitializer} has
     *         expressed interest
     */
    Class<?>[] value();
}
