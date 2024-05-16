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

package javax.servlet;

import java.util.Set;

/**
 * Interface which allows a library/runtime to be notified of a web application's startup phase and perform any required
 * programmatic registration of servlets, filters, and listeners in response to it.
 *
 * <p>
 * Implementations of this interface may be annotated with {@link javax.servlet.annotation.HandlesTypes HandlesTypes},
 * in order to receive (at their {@link #onStartup} method) the Set of application classes that implement, extend, or
 * have been annotated with the class types specified by the annotation.
 * 
 * <p>
 * If an implementation of this interface does not use <code>HandlesTypes</code> annotation, or none of the application
 * classes match the ones specified by the annotation, the container must pass a <code>null</code> Set of classes to
 * {@link #onStartup}.
 *
 * <p>
 * When examining the classes of an application to see if they match any of the criteria specified by the
 * <code>HandlesTypes</code> annotation of a <code>ServletContainerInitializer</code>, the container may run into classloading
 * problems if any of the application's optional JAR files are missing. Because the container is not in a position to
 * decide whether these types of classloading failures will prevent the application from working correctly, it must
 * ignore them, while at the same time providing a configuration option that would log them.
 *
 * <p>
 * Implementations of this interface must be declared by a JAR file resource located inside the
 * <code>META-INF/services</code> directory and named for the fully qualified class name of this interface, and will be
 * discovered using the runtime's service provider lookup mechanism or a container specific mechanism that is
 * semantically equivalent to it. In either case, <code>ServletContainerInitializer</code> services from web fragment JAR
 * files excluded from an absolute ordering must be ignored, and the order in which these services are discovered must
 * follow the application's classloading delegation model.
 *
 * @see javax.servlet.annotation.HandlesTypes
 *
 * @since Servlet 3.0
 */
public interface ServletContainerInitializer {

    /**
     * Notifies this <code>ServletContainerInitializer</code> of the startup of the application represented by the given
     * <code>ServletContext</code>.
     *
     * <p>
     * If this <code>ServletContainerInitializer</code> is bundled in a JAR file inside the <code>WEB-INF/lib</code> directory
     * of an application, its <code>onStartup</code> method will be invoked only once during the startup of the bundling
     * application. If this <code>ServletContainerInitializer</code> is bundled inside a JAR file outside of any
     * <code>WEB-INF/lib</code> directory, but still discoverable as described above, its <code>onStartup</code> method will be
     * invoked every time an application is started.
     *
     * @param c   the Set of application classes that extend, implement, or have been annotated with the class types
     *            specified by the {@link javax.servlet.annotation.HandlesTypes HandlesTypes} annotation, or
     *            <code>null</code> if there are no matches, or this <code>ServletContainerInitializer</code> has not been
     *            annotated with <code>HandlesTypes</code>
     *
     * @param ctx the <code>ServletContext</code> of the web application that is being started and in which the classes
     *            contained in <code>c</code> were found
     *
     * @throws ServletException if an error has occurred
     */
    public void onStartup(Set<Class<?>> c, ServletContext ctx) throws ServletException;
}
