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

import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * Interface through which a {@link Servlet} may be further configured.
 *
 * @since Servlet 3.0
 */
public interface ServletRegistration extends Registration {

    /**
     * Adds a servlet mapping with the given URL patterns for the Servlet represented by this ServletRegistration.
     *
     * <p>
     * If any of the specified URL patterns are already mapped to a different Servlet, no updates will be performed.
     *
     * <p>
     * If this method is called multiple times, each successive call adds to the effects of the former.
     *
     * <p>
     * The returned set is not backed by the {@code ServletRegistration} object, so changes in the returned set are not
     * reflected in the {@code ServletRegistration} object, and vice-versa.
     * </p>
     *
     * @param urlPatterns the URL patterns of the servlet mapping
     *
     * @return the (possibly empty) Set of URL patterns that are already mapped to a different Servlet
     *
     * @throws IllegalArgumentException if <code>urlPatterns</code> is null or empty
     * @throws IllegalStateException    if the ServletContext from which this ServletRegistration was obtained has
     *                                  already been initialized
     */
    Set<String> addMapping(String... urlPatterns);

    /**
     * Gets the currently available mappings of the Servlet represented by this <code>ServletRegistration</code>.
     *
     * <p>
     * If permitted, any changes to the returned <code>Collection</code> must not affect this
     * <code>ServletRegistration</code>.
     *
     * @return a (possibly empty) <code>Collection</code> of the currently available mappings of the Servlet represented
     *         by this <code>ServletRegistration</code>
     */
    Collection<String> getMappings();

    /**
     * Gets the name of the runAs role of the Servlet represented by this <code>ServletRegistration</code>.
     *
     * @return the name of the runAs role, or null if the Servlet is configured to run as its caller
     */
    String getRunAsRole();

    /**
     * Interface through which a {@link Servlet} registered via one of the <code>addServlet</code> methods on
     * {@link ServletContext} may be further configured.
     */
    interface Dynamic extends ServletRegistration, Registration.Dynamic {

        /**
         * Sets the <code>loadOnStartup</code> priority on the Servlet represented by this dynamic ServletRegistration.
         *
         * <p>
         * A <code>loadOnStartup</code> value of greater than or equal to zero indicates to the container the initialization
         * priority of the Servlet. In this case, the container must instantiate and initialize the Servlet during the
         * initialization phase of the ServletContext, that is, after it has invoked all of the ServletContextListener
         * objects configured for the ServletContext at their {@link ServletContextListener#contextInitialized} method.
         *
         * <p>
         * If <code>loadOnStartup</code> is a negative integer, the container is free to instantiate and initialize the
         * Servlet lazily.
         *
         * <p>
         * The default value for <code>loadOnStartup</code> is <code>-1</code>.
         *
         * <p>
         * A call to this method overrides any previous setting.
         *
         * @param loadOnStartup the initialization priority of the Servlet
         *
         * @throws IllegalStateException if the ServletContext from which this ServletRegistration was obtained has
         *                               already been initialized
         */
        void setLoadOnStartup(int loadOnStartup);

        /**
         * Sets the {@link ServletSecurityElement} to be applied to the mappings defined for this
         * <code>ServletRegistration</code>.
         *
         * <p>
         * This method applies to all mappings added to this <code>ServletRegistration</code> up until the point that
         * the <code>ServletContext</code> from which it was obtained has been initialized.
         *
         * <p>
         * If a URL pattern of this ServletRegistration is an exact target of a <code>security-constraint</code> that
         * was established via the portable deployment descriptor, then this method does not change the
         * <code>security-constraint</code> for that pattern, and the pattern will be included in the return value.
         *
         * <p>
         * If a URL pattern of this ServletRegistration is an exact target of a security constraint that was established
         * via the {@link javax.servlet.annotation.ServletSecurity} annotation or a previous call to this method, then
         * this method replaces the security constraint for that pattern.
         *
         * <p>
         * If a URL pattern of this ServletRegistration is neither the exact target of a security constraint that was
         * established via the {@link javax.servlet.annotation.ServletSecurity} annotation or a previous call to this
         * method, nor the exact target of a <code>security-constraint</code> in the portable deployment descriptor,
         * then this method establishes the security constraint for that pattern from the argument
         * <code>ServletSecurityElement</code>.
         *
         * <p>
         * The returned set is not backed by the {@code Dynamic} object, so changes in the returned set are not
         * reflected in the {@code Dynamic} object, and vice-versa.
         * </p>
         *
         * @param constraint the {@link ServletSecurityElement} to be applied to the patterns mapped to this
         *                   ServletRegistration
         *
         * @return the (possibly empty) Set of URL patterns that were already the exact target of a
         *         <code>security-constraint</code> that was established via the portable deployment descriptor. This
         *         method has no effect on the patterns included in the returned set
         *
         * @throws IllegalArgumentException if <code>constraint</code> is null
         *
         * @throws IllegalStateException    if the {@link ServletContext} from which this
         *                                  <code>ServletRegistration</code> was obtained has already been initialized
         */
        Set<String> setServletSecurity(ServletSecurityElement constraint);

        /**
         * Sets the {@link MultipartConfigElement} to be applied to the mappings defined for this
         * <code>ServletRegistration</code>. If this method is called multiple times, each successive call overrides the
         * effects of the former.
         *
         * @param multipartConfig the {@link MultipartConfigElement} to be applied to the patterns mapped to the
         *                        registration
         *
         * @throws IllegalArgumentException if <code>multipartConfig</code> is null
         *
         * @throws IllegalStateException    if the {@link ServletContext} from which this ServletRegistration was
         *                                  obtained has already been initialized
         */
        void setMultipartConfig(MultipartConfigElement multipartConfig);

        /**
         * Sets the name of the <code>runAs</code> role for this <code>ServletRegistration</code>.
         *
         * @param roleName the name of the <code>runAs</code> role
         *
         * @throws IllegalArgumentException if <code>roleName</code> is null
         *
         * @throws IllegalStateException    if the {@link ServletContext} from which this ServletRegistration was
         *                                  obtained has already been initialized
         */
        void setRunAsRole(String roleName);

        default jakarta.servlet.ServletRegistration.Dynamic toJakartaServletRegistrationDynamic() {
            return new jakarta.servlet.ServletRegistration.Dynamic() {
                @Override
                public String getName() {
                    return ServletRegistration.Dynamic.this.getName();
                }

                @Override
                public String getClassName() {
                    return ServletRegistration.Dynamic.this.getClassName();
                }

                @Override
                public boolean setInitParameter(String name, String value) {
                    return ServletRegistration.Dynamic.this.setInitParameter(name, value);
                }

                @Override
                public String getInitParameter(String name) {
                    return ServletRegistration.Dynamic.this.getInitParameter(name);
                }

                @Override
                public Set<String> setInitParameters(Map<String, String> initParameters) {
                    return ServletRegistration.Dynamic.this.setInitParameters(initParameters);
                }

                @Override
                public Map<String, String> getInitParameters() {
                    return ServletRegistration.Dynamic.this.getInitParameters();
                }

                @Override
                public Set<String> addMapping(String... urlPatterns) {
                    return ServletRegistration.Dynamic.this.addMapping(urlPatterns);
                }

                @Override
                public Collection<String> getMappings() {
                    return ServletRegistration.Dynamic.this.getMappings();
                }

                @Override
                public String getRunAsRole() {
                    return ServletRegistration.Dynamic.this.getRunAsRole();
                }

                @Override
                public void setAsyncSupported(boolean isAsyncSupported) {
                    ServletRegistration.Dynamic.this.setAsyncSupported(isAsyncSupported);
                }

                @Override
                public void setLoadOnStartup(int loadOnStartup) {
                    ServletRegistration.Dynamic.this.setLoadOnStartup(loadOnStartup);
                }

                @Override
                public Set<String> setServletSecurity(jakarta.servlet.ServletSecurityElement constraint) {
                    // TODO implement this
                    throw new UnsupportedOperationException();
                }

                @Override
                public void setMultipartConfig(jakarta.servlet.MultipartConfigElement multipartConfig) {
                    // TODO implement this
                    throw new UnsupportedOperationException();
                }

                @Override
                public void setRunAsRole(String roleName) {
                    ServletRegistration.Dynamic.this.setRunAsRole(roleName);
                }
            };
        }

        static ServletRegistration.Dynamic fromJakartaServletRegistrationDynamic(
                jakarta.servlet.ServletRegistration.Dynamic from) {
            Objects.requireNonNull(from);
            return new ServletRegistration.Dynamic() {
                @Override
                public void setLoadOnStartup(int loadOnStartup) {
                    from.setLoadOnStartup(loadOnStartup);
                }

                @Override
                public Set<String> setServletSecurity(ServletSecurityElement constraint) {
                    // TODO implement this
                    throw new UnsupportedOperationException();
                }

                @Override
                public void setMultipartConfig(MultipartConfigElement multipartConfig) {
                    // TODO implement this
                    throw new UnsupportedOperationException();
                }

                @Override
                public void setRunAsRole(String roleName) {
                    from.setRunAsRole(roleName);
                }

                @Override
                public Set<String> addMapping(String... urlPatterns) {
                    return from.addMapping(urlPatterns);
                }

                @Override
                public Collection<String> getMappings() {
                    return from.getMappings();
                }

                @Override
                public String getRunAsRole() {
                    return from.getRunAsRole();
                }

                @Override
                public void setAsyncSupported(boolean isAsyncSupported) {
                    from.setAsyncSupported(isAsyncSupported);
                }

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

                @Override
                public jakarta.servlet.Registration.Dynamic toJakartaRegistrationDynamic() {
                    return from;
                }

                @Override
                public jakarta.servlet.ServletRegistration toJakartaServletRegistration() {
                    return from;
                }

                @Override
                public jakarta.servlet.ServletRegistration.Dynamic toJakartaServletRegistrationDynamic() {
                    return from;
                }
            };
        }
    }

    default jakarta.servlet.ServletRegistration toJakartaServletRegistration() {
        return new jakarta.servlet.ServletRegistration() {
            @Override
            public String getName() {
                return ServletRegistration.this.getName();
            }

            @Override
            public String getClassName() {
                return ServletRegistration.this.getClassName();
            }

            @Override
            public boolean setInitParameter(String name, String value) {
                return ServletRegistration.this.setInitParameter(name, value);
            }

            @Override
            public String getInitParameter(String name) {
                return ServletRegistration.this.getInitParameter(name);
            }

            @Override
            public Set<String> setInitParameters(Map<String, String> initParameters) {
                return ServletRegistration.this.setInitParameters(initParameters);
            }

            @Override
            public Map<String, String> getInitParameters() {
                return ServletRegistration.this.getInitParameters();
            }

            @Override
            public Set<String> addMapping(String... urlPatterns) {
                return ServletRegistration.this.addMapping(urlPatterns);
            }

            @Override
            public Collection<String> getMappings() {
                return ServletRegistration.this.getMappings();
            }

            @Override
            public String getRunAsRole() {
                return ServletRegistration.this.getRunAsRole();
            }
        };
    }

    static ServletRegistration fromJakartaServletRegistration(jakarta.servlet.ServletRegistration from) {
        Objects.requireNonNull(from);
        return new ServletRegistration() {
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
            public Set<String> addMapping(String... urlPatterns) {
                return from.addMapping(urlPatterns);
            }

            @Override
            public Collection<String> getMappings() {
                return from.getMappings();
            }

            @Override
            public String getRunAsRole() {
                return from.getRunAsRole();
            }

            @Override
            public jakarta.servlet.Registration toJakartaRegistration() {
                return from;
            }

            @Override
            public jakarta.servlet.ServletRegistration toJakartaServletRegistration() {
                return from;
            }
        };
    }
}
