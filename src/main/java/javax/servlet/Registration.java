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

import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * Interface through which a {@link Servlet} or {@link Filter} may be further configured.
 *
 * <p>
 * A Registration object whose {@link #getClassName} method returns null is considered <i>preliminary</i>. Servlets and
 * Filters whose implementation class is container implementation specific may be declared without any
 * <code>servlet-class</code> or <code>filter-class</code> elements, respectively, and will be represented as preliminary
 * Registration objects. Preliminary registrations must be completed by calling one of the <code>addServlet</code> or
 * <code>addFilter</code> methods on {@link ServletContext}, and passing in the Servlet or Filter name (obtained via
 * {@link #getName}) along with the supporting Servlet or Filter implementation class name, Class object, or instance,
 * respectively. In most cases, preliminary registrations will be completed by an appropriate, container-provided
 * {@link ServletContainerInitializer}.
 *
 * @since Servlet 3.0
 */
public interface Registration {

    /**
     * Gets the name of the Servlet or Filter that is represented by this Registration.
     *
     * @return the name of the Servlet or Filter that is represented by this Registration
     */
    String getName();

    /**
     * Gets the fully qualified class name of the Servlet or Filter that is represented by this Registration.
     *
     * @return the fully qualified class name of the Servlet or Filter that is represented by this Registration, or null
     *         if this Registration is preliminary
     */
    String getClassName();

    /**
     * Sets the initialization parameter with the given name and value on the Servlet or Filter that is represented by
     * this Registration.
     *
     * @param name  the initialization parameter name
     * @param value the initialization parameter value
     *
     * @return true if the update was successful, i.e., an initialization parameter with the given name did not already
     *         exist for the Servlet or Filter represented by this Registration, and false otherwise
     *
     * @throws IllegalStateException    if the ServletContext from which this Registration was obtained has already been
     *                                  initialized
     * @throws IllegalArgumentException if the given name or value is <code>null</code>
     */
    boolean setInitParameter(String name, String value);

    /**
     * Gets the value of the initialization parameter with the given name that will be used to initialize the Servlet or
     * Filter represented by this Registration object.
     *
     * @param name the name of the initialization parameter whose value is requested
     *
     * @return the value of the initialization parameter with the given name, or <code>null</code> if no initialization
     *         parameter with the given name exists
     */
    String getInitParameter(String name);

    /**
     * Sets the given initialization parameters on the Servlet or Filter that is represented by this Registration.
     *
     * <p>
     * The given map of initialization parameters is processed <i>by-value</i>, i.e., for each initialization parameter
     * contained in the map, this method calls {@link #setInitParameter(String,String)}. If that method would return
     * false for any of the initialization parameters in the given map, no updates will be performed, and false will be
     * returned. Likewise, if the map contains an initialization parameter with a <code>null</code> name or value, no
     * updates will be performed, and an IllegalArgumentException will be thrown.
     *
     * <p>
     * The returned set is not backed by the {@code Registration} object, so changes in the returned set are not
     * reflected in the {@code Registration} object, and vice-versa.
     * </p>
     *
     * @param initParameters the initialization parameters
     *
     * @return the (possibly empty) Set of initialization parameter names that are in conflict
     *
     * @throws IllegalStateException    if the ServletContext from which this Registration was obtained has already been
     *                                  initialized
     * @throws IllegalArgumentException if the given map contains an initialization parameter with a <code>null</code> name
     *                                  or value
     */
    Set<String> setInitParameters(Map<String, String> initParameters);

    /**
     * Gets an immutable (and possibly empty) Map containing the currently available initialization parameters that will
     * be used to initialize the Servlet or Filter represented by this Registration object.
     *
     * @return Map containing the currently available initialization parameters that will be used to initialize the
     *         Servlet or Filter represented by this Registration object
     */
    Map<String, String> getInitParameters();

    /**
     * Interface through which a {@link Servlet} or {@link Filter} registered via one of the <code>addServlet</code> or
     * <code>addFilter</code> methods, respectively, on {@link ServletContext} may be further configured.
     */
    interface Dynamic extends Registration {

        /**
         * Configures the Servlet or Filter represented by this dynamic Registration as supporting asynchronous
         * operations or not.
         *
         * <p>
         * By default, servlet and filters do not support asynchronous operations.
         *
         * <p>
         * A call to this method overrides any previous setting.
         *
         * @param isAsyncSupported true if the Servlet or Filter represented by this dynamic Registration supports
         *                         asynchronous operations, false otherwise
         *
         * @throws IllegalStateException if the ServletContext from which this dynamic Registration was obtained has
         *                               already been initialized
         */
        void setAsyncSupported(boolean isAsyncSupported);

        default jakarta.servlet.Registration.Dynamic toJakartaRegistrationDynamic() {
            return new jakarta.servlet.Registration.Dynamic() {
                @Override
                public String getName() {
                    return Registration.Dynamic.this.getName();
                }

                @Override
                public String getClassName() {
                    return Registration.Dynamic.this.getClassName();
                }

                @Override
                public boolean setInitParameter(String name, String value) {
                    return Registration.Dynamic.this.setInitParameter(name, value);
                }

                @Override
                public String getInitParameter(String name) {
                    return Registration.Dynamic.this.getInitParameter(name);
                }

                @Override
                public Set<String> setInitParameters(Map<String, String> initParameters) {
                    return Registration.Dynamic.this.setInitParameters(initParameters);
                }

                @Override
                public Map<String, String> getInitParameters() {
                    return Registration.Dynamic.this.getInitParameters();
                }

                @Override
                public void setAsyncSupported(boolean isAsyncSupported) {
                    Registration.Dynamic.this.setAsyncSupported(isAsyncSupported);
                }
            };
        }

        static Registration.Dynamic fromJakartaRegistrationDynamic(jakarta.servlet.Registration.Dynamic from) {
            Objects.requireNonNull(from);
            return new Registration.Dynamic() {
                @Override
                public String getName() {
                    return from.getName();
                }

                @Override
                public String getClassName() {
                    return from.getClassName();
                }

                @Override
                public boolean setInitParameter(String name, String value) {
                    return from.setInitParameter(name, value);
                }

                @Override
                public String getInitParameter(String name) {
                    return from.getInitParameter(name);
                }

                @Override
                public Set<String> setInitParameters(Map<String, String> initParameters) {
                    return from.setInitParameters(initParameters);
                }

                @Override
                public Map<String, String> getInitParameters() {
                    return from.getInitParameters();
                }

                @Override
                public void setAsyncSupported(boolean isAsyncSupported) {
                    from.setAsyncSupported(isAsyncSupported);
                }

                @Override
                public jakarta.servlet.Registration toJakartaRegistration() {
                    return from;
                }

                @Override
                public jakarta.servlet.Registration.Dynamic toJakartaRegistrationDynamic() {
                    return from;
                }
            };
        }
    }

    default jakarta.servlet.Registration toJakartaRegistration() {
        return new jakarta.servlet.Registration() {
            @Override
            public String getName() {
                return Registration.this.getName();
            }

            @Override
            public String getClassName() {
                return Registration.this.getClassName();
            }

            @Override
            public boolean setInitParameter(String name, String value) {
                return Registration.this.setInitParameter(name, value);
            }

            @Override
            public String getInitParameter(String name) {
                return Registration.this.getInitParameter(name);
            }

            @Override
            public Set<String> setInitParameters(Map<String, String> initParameters) {
                return Registration.this.setInitParameters(initParameters);
            }

            @Override
            public Map<String, String> getInitParameters() {
                return Registration.this.getInitParameters();
            }
        };
    }

    static Registration fromJakartaRegistration(jakarta.servlet.Registration from) {
        Objects.requireNonNull(from);
        return new Registration() {
            @Override
            public String getName() {
                return from.getName();
            }

            @Override
            public String getClassName() {
                return from.getClassName();
            }

            @Override
            public boolean setInitParameter(String name, String value) {
                return from.setInitParameter(name, value);
            }

            @Override
            public String getInitParameter(String name) {
                return from.getInitParameter(name);
            }

            @Override
            public Set<String> setInitParameters(Map<String, String> initParameters) {
                return from.setInitParameters(initParameters);
            }

            @Override
            public Map<String, String> getInitParameters() {
                return from.getInitParameters();
            }

            @Override
            public jakarta.servlet.Registration toJakartaRegistration() {
                return from;
            }
        };
    }
}
