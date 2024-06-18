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
import java.util.EnumSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Interface through which a {@link Filter} may be further configured.
 *
 * @since Servlet 3.0
 */
public interface FilterRegistration extends Registration {

    /**
     * Adds a filter mapping with the given servlet names and dispatcher types for the Filter represented by this
     * FilterRegistration.
     *
     * <p>
     * Filter mappings are matched in the order in which they were added.
     *
     * <p>
     * Depending on the value of the <code>isMatchAfter</code> parameter, the given filter mapping will be considered after
     * or before any <i>declared</i> filter mappings of the ServletContext from which this FilterRegistration was
     * obtained.
     *
     * <p>
     * If this method is called multiple times, each successive call adds to the effects of the former.
     *
     * @param dispatcherTypes the dispatcher types of the filter mapping, or null if the default
     *                        <code>DispatcherType.REQUEST</code> is to be used
     * @param isMatchAfter    true if the given filter mapping should be matched after any declared filter mappings, and
     *                        false if it is supposed to be matched before any declared filter mappings of the
     *                        ServletContext from which this FilterRegistration was obtained
     * @param servletNames    the servlet names of the filter mapping
     *
     * @throws IllegalArgumentException if <code>servletNames</code> is null or empty
     * @throws IllegalStateException    if the ServletContext from which this FilterRegistration was obtained has
     *                                  already been initialized
     */
    void addMappingForServletNames(
            EnumSet<DispatcherType> dispatcherTypes, boolean isMatchAfter, String... servletNames);

    /**
     * Gets the currently available servlet name mappings of the Filter represented by this
     * <code>FilterRegistration</code>.
     *
     * <p>
     * If permitted, any changes to the returned <code>Collection</code> must not affect this
     * <code>FilterRegistration</code>.
     *
     * @return a (possibly empty) <code>Collection</code> of the currently available servlet name mappings of the Filter
     *         represented by this <code>FilterRegistration</code>
     */
    Collection<String> getServletNameMappings();

    /**
     * Adds a filter mapping with the given url patterns and dispatcher types for the Filter represented by this
     * FilterRegistration.
     *
     * <p>
     * Filter mappings are matched in the order in which they were added.
     *
     * <p>
     * Depending on the value of the <code>isMatchAfter</code> parameter, the given filter mapping will be considered after
     * or before any <i>declared</i> filter mappings of the ServletContext from which this FilterRegistration was
     * obtained.
     *
     * <p>
     * If this method is called multiple times, each successive call adds to the effects of the former.
     *
     * @param dispatcherTypes the dispatcher types of the filter mapping, or null if the default
     *                        <code>DispatcherType.REQUEST</code> is to be used
     * @param isMatchAfter    true if the given filter mapping should be matched after any declared filter mappings, and
     *                        false if it is supposed to be matched before any declared filter mappings of the
     *                        ServletContext from which this FilterRegistration was obtained
     * @param urlPatterns     the url patterns of the filter mapping
     *
     * @throws IllegalArgumentException if <code>urlPatterns</code> is null or empty
     * @throws IllegalStateException    if the ServletContext from which this FilterRegistration was obtained has
     *                                  already been initialized
     */
    void addMappingForUrlPatterns(EnumSet<DispatcherType> dispatcherTypes, boolean isMatchAfter, String... urlPatterns);

    /**
     * Gets the currently available URL pattern mappings of the Filter represented by this
     * <code>FilterRegistration</code>.
     *
     * <p>
     * If permitted, any changes to the returned <code>Collection</code> must not affect this
     * <code>FilterRegistration</code>.
     *
     * @return a (possibly empty) <code>Collection</code> of the currently available URL pattern mappings of the Filter
     *         represented by this <code>FilterRegistration</code>
     */
    Collection<String> getUrlPatternMappings();

    /**
     * Interface through which a {@link Filter} registered via one of the <code>addFilter</code> methods on
     * {@link ServletContext} may be further configured.
     */
    interface Dynamic extends FilterRegistration, Registration.Dynamic {
        default jakarta.servlet.FilterRegistration.Dynamic toJakartaFilterRegistrationDynamic() {
            return new jakarta.servlet.FilterRegistration.Dynamic() {
                @Override
                public String getName() {
                    return FilterRegistration.Dynamic.this.getName();
                }

                @Override
                public String getClassName() {
                    return FilterRegistration.Dynamic.this.getClassName();
                }

                @Override
                public boolean setInitParameter(String name, String value) {
                    return FilterRegistration.Dynamic.this.setInitParameter(name, value);
                }

                @Override
                public String getInitParameter(String name) {
                    return FilterRegistration.Dynamic.this.getInitParameter(name);
                }

                @Override
                public Set<String> setInitParameters(Map<String, String> initParameters) {
                    return FilterRegistration.Dynamic.this.setInitParameters(initParameters);
                }

                @Override
                public Map<String, String> getInitParameters() {
                    return FilterRegistration.Dynamic.this.getInitParameters();
                }

                @Override
                public void setAsyncSupported(boolean isAsyncSupported) {
                    FilterRegistration.Dynamic.this.setAsyncSupported(isAsyncSupported);
                }

                @Override
                public void addMappingForServletNames(
                        EnumSet<jakarta.servlet.DispatcherType> dispatcherTypes,
                        boolean isMatchAfter,
                        String... servletNames) {
                    FilterRegistration.Dynamic.this.addMappingForServletNames(
                            EnumSet.copyOf(dispatcherTypes.stream()
                                    .map(DispatcherType::fromJakartaDispatcherType)
                                    .collect(Collectors.toSet())),
                            isMatchAfter,
                            servletNames);
                }

                @Override
                public Collection<String> getServletNameMappings() {
                    return FilterRegistration.Dynamic.this.getServletNameMappings();
                }

                @Override
                public void addMappingForUrlPatterns(
                        EnumSet<jakarta.servlet.DispatcherType> dispatcherTypes,
                        boolean isMatchAfter,
                        String... urlPatterns) {
                    FilterRegistration.Dynamic.this.addMappingForUrlPatterns(
                            EnumSet.copyOf(dispatcherTypes.stream()
                                    .map(DispatcherType::fromJakartaDispatcherType)
                                    .collect(Collectors.toSet())),
                            isMatchAfter,
                            urlPatterns);
                }

                @Override
                public Collection<String> getUrlPatternMappings() {
                    return FilterRegistration.Dynamic.this.getUrlPatternMappings();
                }
            };
        }

        static FilterRegistration.Dynamic fromJakartaFilterRegistrationDynamic(
                jakarta.servlet.FilterRegistration.Dynamic from) {
            Objects.requireNonNull(from);
            return new FilterRegistration.Dynamic() {
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
                public void addMappingForServletNames(
                        EnumSet<DispatcherType> dispatcherTypes, boolean isMatchAfter, String... servletNames) {
                    from.addMappingForServletNames(
                            EnumSet.copyOf(dispatcherTypes.stream()
                                    .map(DispatcherType::toJakartaDispatcherType)
                                    .collect(Collectors.toSet())),
                            isMatchAfter,
                            servletNames);
                }

                @Override
                public Collection<String> getServletNameMappings() {
                    return from.getServletNameMappings();
                }

                @Override
                public void addMappingForUrlPatterns(
                        EnumSet<DispatcherType> dispatcherTypes, boolean isMatchAfter, String... urlPatterns) {
                    from.addMappingForUrlPatterns(
                            EnumSet.copyOf(dispatcherTypes.stream()
                                    .map(DispatcherType::toJakartaDispatcherType)
                                    .collect(Collectors.toSet())),
                            isMatchAfter,
                            urlPatterns);
                }

                @Override
                public Collection<String> getUrlPatternMappings() {
                    return from.getUrlPatternMappings();
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
                public jakarta.servlet.FilterRegistration toJakartaFilterRegistration() {
                    return from;
                }

                @Override
                public jakarta.servlet.FilterRegistration.Dynamic toJakartaFilterRegistrationDynamic() {
                    return from;
                }
            };
        }
    }

    default jakarta.servlet.FilterRegistration toJakartaFilterRegistration() {
        return new jakarta.servlet.FilterRegistration() {
            @Override
            public String getName() {
                return FilterRegistration.this.getName();
            }

            @Override
            public String getClassName() {
                return FilterRegistration.this.getClassName();
            }

            @Override
            public boolean setInitParameter(String name, String value) {
                return FilterRegistration.this.setInitParameter(name, value);
            }

            @Override
            public String getInitParameter(String name) {
                return FilterRegistration.this.getInitParameter(name);
            }

            @Override
            public Set<String> setInitParameters(Map<String, String> initParameters) {
                return FilterRegistration.this.setInitParameters(initParameters);
            }

            @Override
            public Map<String, String> getInitParameters() {
                return FilterRegistration.this.getInitParameters();
            }

            @Override
            public void addMappingForServletNames(
                    EnumSet<jakarta.servlet.DispatcherType> dispatcherTypes,
                    boolean isMatchAfter,
                    String... servletNames) {
                FilterRegistration.this.addMappingForServletNames(
                        EnumSet.copyOf(dispatcherTypes.stream()
                                .map(DispatcherType::fromJakartaDispatcherType)
                                .collect(Collectors.toSet())),
                        isMatchAfter,
                        servletNames);
            }

            @Override
            public Collection<String> getServletNameMappings() {
                return FilterRegistration.this.getServletNameMappings();
            }

            @Override
            public void addMappingForUrlPatterns(
                    EnumSet<jakarta.servlet.DispatcherType> dispatcherTypes,
                    boolean isMatchAfter,
                    String... urlPatterns) {
                FilterRegistration.this.addMappingForUrlPatterns(
                        EnumSet.copyOf(dispatcherTypes.stream()
                                .map(DispatcherType::fromJakartaDispatcherType)
                                .collect(Collectors.toSet())),
                        isMatchAfter,
                        urlPatterns);
            }

            @Override
            public Collection<String> getUrlPatternMappings() {
                return FilterRegistration.this.getUrlPatternMappings();
            }
        };
    }

    static FilterRegistration fromJakartaFilterRegistration(jakarta.servlet.FilterRegistration from) {
        Objects.requireNonNull(from);
        return new FilterRegistration() {
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
            public void addMappingForServletNames(
                    EnumSet<DispatcherType> dispatcherTypes, boolean isMatchAfter, String... servletNames) {
                from.addMappingForServletNames(
                        EnumSet.copyOf(dispatcherTypes.stream()
                                .map(DispatcherType::toJakartaDispatcherType)
                                .collect(Collectors.toSet())),
                        isMatchAfter,
                        servletNames);
            }

            @Override
            public Collection<String> getServletNameMappings() {
                return from.getServletNameMappings();
            }

            @Override
            public void addMappingForUrlPatterns(
                    EnumSet<DispatcherType> dispatcherTypes, boolean isMatchAfter, String... urlPatterns) {
                from.addMappingForUrlPatterns(
                        EnumSet.copyOf(dispatcherTypes.stream()
                                .map(DispatcherType::toJakartaDispatcherType)
                                .collect(Collectors.toSet())),
                        isMatchAfter,
                        urlPatterns);
            }

            @Override
            public Collection<String> getUrlPatternMappings() {
                return from.getUrlPatternMappings();
            }

            @Override
            public jakarta.servlet.Registration toJakartaRegistration() {
                return from;
            }

            @Override
            public jakarta.servlet.FilterRegistration toJakartaFilterRegistration() {
                return from;
            }
        };
    }
}
