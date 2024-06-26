/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates and others.
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

package javax.servlet.http;

import java.util.Objects;

/**
 * <p>
 * Enumeration of Servlet mapping types.
 * </p>
 *
 * @since 4.0
 */
public enum MappingMatch {
    /**
     * <p>
     * This is used when the mapping was achieved with an exact match to the application's context root.
     * </p>
     */
    CONTEXT_ROOT,
    /**
     * <p>
     * This is used when the mapping was achieved with an exact match to the default servlet of the application, the
     * '{@code /}' character.
     * </p>
     */
    DEFAULT,
    /**
     * <p>
     * This is used when the mapping was achieved with an exact match to the incoming request.
     * </p>
     */
    EXACT,
    /**
     * <p>
     * This is used when the mapping was achieved using an extension, such as "{@code *.xhtml}".
     * </p>
     */
    EXTENSION,
    /**
     * <p>
     * This is used when the mapping was achieved using a path, such as "{@code /faces/*}".
     * </p>
     */
    PATH;

    public static jakarta.servlet.http.MappingMatch toJakartaMappingMatch(MappingMatch from) {
        Objects.requireNonNull(from);
        switch (from) {
            case CONTEXT_ROOT:
                return jakarta.servlet.http.MappingMatch.CONTEXT_ROOT;
            case DEFAULT:
                return jakarta.servlet.http.MappingMatch.DEFAULT;
            case EXACT:
                return jakarta.servlet.http.MappingMatch.EXACT;
            case EXTENSION:
                return jakarta.servlet.http.MappingMatch.EXTENSION;
            case PATH:
                return jakarta.servlet.http.MappingMatch.PATH;
            default:
                throw new IllegalArgumentException("Unknown MappingMatch: " + from);
        }
    }

    public static MappingMatch fromJakartaMappingMatch(jakarta.servlet.http.MappingMatch from) {
        Objects.requireNonNull(from);
        switch (from) {
            case CONTEXT_ROOT:
                return MappingMatch.CONTEXT_ROOT;
            case DEFAULT:
                return MappingMatch.DEFAULT;
            case EXACT:
                return MappingMatch.EXACT;
            case EXTENSION:
                return MappingMatch.EXTENSION;
            case PATH:
                return MappingMatch.PATH;
            default:
                throw new IllegalArgumentException("Unknown MappingMatch: " + from);
        }
    }
}
