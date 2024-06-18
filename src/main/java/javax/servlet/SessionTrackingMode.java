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

import java.util.Objects;

/**
 * Enumeration of session tracking modes.
 *
 * @since Servlet 3.0
 */
public enum SessionTrackingMode {
    COOKIE,
    URL,
    SSL;

    public static jakarta.servlet.SessionTrackingMode toJakartaSessionTrackingMode(SessionTrackingMode from) {
        Objects.requireNonNull(from);
        switch (from) {
            case COOKIE:
                return jakarta.servlet.SessionTrackingMode.COOKIE;
            case URL:
                return jakarta.servlet.SessionTrackingMode.URL;
            case SSL:
                return jakarta.servlet.SessionTrackingMode.SSL;
            default:
                throw new IllegalArgumentException("Unknown SessionTrackingMode: " + from);
        }
    }

    public static SessionTrackingMode fromJakartaSessionTrackingMode(jakarta.servlet.SessionTrackingMode from) {
        Objects.requireNonNull(from);
        switch (from) {
            case COOKIE:
                return SessionTrackingMode.COOKIE;
            case URL:
                return SessionTrackingMode.URL;
            case SSL:
                return SessionTrackingMode.SSL;
            default:
                throw new IllegalArgumentException("Unknown SessionTrackingMode: " + from);
        }
    }
}
