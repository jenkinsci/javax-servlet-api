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
 * Class that may be used to configure various properties of cookies used for session tracking purposes.
 *
 * <p>
 * An instance of this class is acquired by a call to {@link ServletContext#getSessionCookieConfig}.
 *
 * @since Servlet 3.0
 */
public interface SessionCookieConfig {

    /**
     * Sets the name that will be assigned to any session tracking cookies created on behalf of the application
     * represented by the <code>ServletContext</code> from which this <code>SessionCookieConfig</code> was acquired.
     *
     * <p>
     * NOTE: Changing the name of session tracking cookies may break other tiers (for example, a load balancing
     * frontend) that assume the cookie name to be equal to the default <code>JSESSIONID</code>, and therefore should only
     * be done cautiously.
     *
     * @param name the cookie name to use
     *
     * @throws IllegalStateException if the <code>ServletContext</code> from which this <code>SessionCookieConfig</code> was
     *                               acquired has already been initialized
     */
    void setName(String name);

    /**
     * Gets the name that will be assigned to any session tracking cookies created on behalf of the application
     * represented by the <code>ServletContext</code> from which this <code>SessionCookieConfig</code> was acquired.
     *
     * <p>
     * By default, <code>JSESSIONID</code> will be used as the cookie name.
     *
     * @return the cookie name set via {@link #setName}, or <code>null</code> if {@link #setName} was never called
     *
     * @see javax.servlet.http.Cookie#getName()
     */
    String getName();

    /**
     * Sets the domain name that will be assigned to any session tracking cookies created on behalf of the application
     * represented by the <code>ServletContext</code> from which this <code>SessionCookieConfig</code> was acquired.
     *
     * @param domain the cookie domain to use
     *
     * @throws IllegalStateException if the <code>ServletContext</code> from which this <code>SessionCookieConfig</code> was
     *                               acquired has already been initialized
     *
     * @see javax.servlet.http.Cookie#setDomain(String)
     */
    void setDomain(String domain);

    /**
     * Gets the domain name that will be assigned to any session tracking cookies created on behalf of the application
     * represented by the <code>ServletContext</code> from which this <code>SessionCookieConfig</code> was acquired.
     *
     * @return the cookie domain set via {@link #setDomain}, or <code>null</code> if {@link #setDomain} was never called
     *
     * @see javax.servlet.http.Cookie#getDomain()
     */
    String getDomain();

    /**
     * Sets the path that will be assigned to any session tracking cookies created on behalf of the application
     * represented by the <code>ServletContext</code> from which this <code>SessionCookieConfig</code> was acquired.
     *
     * @param path the cookie path to use
     *
     * @throws IllegalStateException if the <code>ServletContext</code> from which this <code>SessionCookieConfig</code> was
     *                               acquired has already been initialized
     *
     * @see javax.servlet.http.Cookie#setPath(String)
     */
    void setPath(String path);

    /**
     * Gets the path that will be assigned to any session tracking cookies created on behalf of the application
     * represented by the <code>ServletContext</code> from which this <code>SessionCookieConfig</code> was acquired.
     *
     * <p>
     * By default, the context path of the <code>ServletContext</code> from which this <code>SessionCookieConfig</code> was
     * acquired will be used.
     *
     * @return the cookie path set via {@link #setPath}, or <code>null</code> if {@link #setPath} was never called
     *
     * @see javax.servlet.http.Cookie#getPath()
     */
    String getPath();

    /**
     * Sets the comment that will be assigned to any session tracking cookies created on behalf of the application
     * represented by the <code>ServletContext</code> from which this <code>SessionCookieConfig</code> was acquired.
     *
     * <p>
     * As a side effect of this call, the session tracking cookies will be marked with a <code>Version</code> attribute
     * equal to <code>1</code>.
     *
     * @param comment the cookie comment to use
     *
     * @throws IllegalStateException if the <code>ServletContext</code> from which this <code>SessionCookieConfig</code> was
     *                               acquired has already been initialized
     *
     * @see javax.servlet.http.Cookie#setComment(String)
     * @see javax.servlet.http.Cookie#getVersion
     */
    void setComment(String comment);

    /**
     * Gets the comment that will be assigned to any session tracking cookies created on behalf of the application
     * represented by the <code>ServletContext</code> from which this <code>SessionCookieConfig</code> was acquired.
     *
     * @return the cookie comment set via {@link #setComment}, or <code>null</code> if {@link #setComment} was never called
     *
     * @see javax.servlet.http.Cookie#getComment()
     */
    String getComment();

    /**
     * Marks or unmarks the session tracking cookies created on behalf of the application represented by the
     * <code>ServletContext</code> from which this <code>SessionCookieConfig</code> was acquired as <i>HttpOnly</i>.
     *
     * <p>
     * A cookie is marked as <code>HttpOnly</code> by adding the <code>HttpOnly</code> attribute to it. <i>HttpOnly</i> cookies
     * are not supposed to be exposed to client-side scripting code, and may therefore help mitigate certain kinds of
     * cross-site scripting attacks.
     *
     * @param httpOnly true if the session tracking cookies created on behalf of the application represented by the
     *                 <code>ServletContext</code> from which this <code>SessionCookieConfig</code> was acquired shall be marked
     *                 as <i>HttpOnly</i>, false otherwise
     *
     * @throws IllegalStateException if the <code>ServletContext</code> from which this <code>SessionCookieConfig</code> was
     *                               acquired has already been initialized
     *
     * @see javax.servlet.http.Cookie#setHttpOnly(boolean)
     */
    void setHttpOnly(boolean httpOnly);

    /**
     * Checks if the session tracking cookies created on behalf of the application represented by the
     * <code>ServletContext</code> from which this <code>SessionCookieConfig</code> was acquired will be marked as
     * <i>HttpOnly</i>.
     *
     * @return true if the session tracking cookies created on behalf of the application represented by the
     *         <code>ServletContext</code> from which this <code>SessionCookieConfig</code> was acquired will be marked as
     *         <i>HttpOnly</i>, false otherwise
     *
     * @see javax.servlet.http.Cookie#isHttpOnly()
     */
    boolean isHttpOnly();

    /**
     * Marks or unmarks the session tracking cookies created on behalf of the application represented by the
     * <code>ServletContext</code> from which this <code>SessionCookieConfig</code> was acquired as <i>secure</i>.
     *
     * <p>
     * One use case for marking a session tracking cookie as <code>secure</code>, even though the request that initiated the
     * session came over HTTP, is to support a topology where the web container is front-ended by an SSL offloading load
     * balancer. In this case, the traffic between the client and the load balancer will be over HTTPS, whereas the
     * traffic between the load balancer and the web container will be over HTTP.
     *
     * @param secure true if the session tracking cookies created on behalf of the application represented by the
     *               <code>ServletContext</code> from which this <code>SessionCookieConfig</code> was acquired shall be marked
     *               as <i>secure</i> even if the request that initiated the corresponding session is using plain HTTP
     *               instead of HTTPS, and false if they shall be marked as <i>secure</i> only if the request that
     *               initiated the corresponding session was also secure
     *
     * @throws IllegalStateException if the <code>ServletContext</code> from which this <code>SessionCookieConfig</code> was
     *                               acquired has already been initialized
     *
     * @see javax.servlet.http.Cookie#setSecure(boolean)
     * @see ServletRequest#isSecure()
     */
    void setSecure(boolean secure);

    /**
     * Checks if the session tracking cookies created on behalf of the application represented by the
     * <code>ServletContext</code> from which this <code>SessionCookieConfig</code> was acquired will be marked as <i>secure</i>
     * even if the request that initiated the corresponding session is using plain HTTP instead of HTTPS.
     *
     * @return true if the session tracking cookies created on behalf of the application represented by the
     *         <code>ServletContext</code> from which this <code>SessionCookieConfig</code> was acquired will be marked as
     *         <i>secure</i> even if the request that initiated the corresponding session is using plain HTTP instead of
     *         HTTPS, and false if they will be marked as <i>secure</i> only if the request that initiated the
     *         corresponding session was also secure
     *
     * @see javax.servlet.http.Cookie#getSecure()
     * @see ServletRequest#isSecure()
     */
    boolean isSecure();

    /**
     * Sets the lifetime (in seconds) for the session tracking cookies created on behalf of the application represented
     * by the <code>ServletContext</code> from which this <code>SessionCookieConfig</code> was acquired.
     *
     * @param maxAge the lifetime (in seconds) of the session tracking cookies created on behalf of the application
     *               represented by the <code>ServletContext</code> from which this <code>SessionCookieConfig</code> was
     *               acquired.
     *
     * @throws IllegalStateException if the <code>ServletContext</code> from which this <code>SessionCookieConfig</code> was
     *                               acquired has already been initialized
     *
     * @see javax.servlet.http.Cookie#setMaxAge
     */
    void setMaxAge(int maxAge);

    /**
     * Gets the lifetime (in seconds) of the session tracking cookies created on behalf of the application represented
     * by the <code>ServletContext</code> from which this <code>SessionCookieConfig</code> was acquired.
     *
     * <p>
     * By default, <code>-1</code> is returned.
     *
     * @return the lifetime (in seconds) of the session tracking cookies created on behalf of the application
     *         represented by the <code>ServletContext</code> from which this <code>SessionCookieConfig</code> was acquired, or
     *         <code>-1</code> (the default)
     *
     * @see javax.servlet.http.Cookie#getMaxAge
     */
    int getMaxAge();

    default jakarta.servlet.SessionCookieConfig toJakartaSessionCookieConfig() {
        return new jakarta.servlet.SessionCookieConfig() {
            @Override
            public void setName(String name) {
                SessionCookieConfig.this.setName(name);
            }

            @Override
            public String getName() {
                return SessionCookieConfig.this.getName();
            }

            @Override
            public void setDomain(String domain) {
                SessionCookieConfig.this.setDomain(domain);
            }

            @Override
            public String getDomain() {
                return SessionCookieConfig.this.getDomain();
            }

            @Override
            public void setPath(String path) {
                SessionCookieConfig.this.setPath(path);
            }

            @Override
            public String getPath() {
                return SessionCookieConfig.this.getPath();
            }

            @Override
            public void setComment(String comment) {
                SessionCookieConfig.this.setComment(comment);
            }

            @Override
            public String getComment() {
                return SessionCookieConfig.this.getComment();
            }

            @Override
            public void setHttpOnly(boolean httpOnly) {
                SessionCookieConfig.this.setHttpOnly(httpOnly);
            }

            @Override
            public boolean isHttpOnly() {
                return SessionCookieConfig.this.isHttpOnly();
            }

            @Override
            public void setSecure(boolean secure) {
                SessionCookieConfig.this.setSecure(secure);
            }

            @Override
            public boolean isSecure() {
                return SessionCookieConfig.this.isSecure();
            }

            @Override
            public void setMaxAge(int maxAge) {
                SessionCookieConfig.this.setMaxAge(maxAge);
            }

            @Override
            public int getMaxAge() {
                return SessionCookieConfig.this.getMaxAge();
            }
        };
    }

    static SessionCookieConfig fromJakartaSessionCookieConfig(jakarta.servlet.SessionCookieConfig from) {
        Objects.requireNonNull(from);
        return new SessionCookieConfig() {
            @Override
            public void setName(String name) {
                from.setName(name);
            }

            @Override
            public String getName() {
                return from.getName();
            }

            @Override
            public void setDomain(String domain) {
                from.setDomain(domain);
            }

            @Override
            public String getDomain() {
                return from.getDomain();
            }

            @Override
            public void setPath(String path) {
                from.setPath(path);
            }

            @Override
            public String getPath() {
                return from.getPath();
            }

            @Override
            public void setComment(String comment) {
                from.setComment(comment);
            }

            @Override
            public String getComment() {
                return from.getComment();
            }

            @Override
            public void setHttpOnly(boolean httpOnly) {
                from.setHttpOnly(httpOnly);
            }

            @Override
            public boolean isHttpOnly() {
                return from.isHttpOnly();
            }

            @Override
            public void setSecure(boolean secure) {
                from.setSecure(secure);
            }

            @Override
            public boolean isSecure() {
                return from.isSecure();
            }

            @Override
            public void setMaxAge(int maxAge) {
                from.setMaxAge(maxAge);
            }

            @Override
            public int getMaxAge() {
                return from.getMaxAge();
            }

            @Override
            public jakarta.servlet.SessionCookieConfig toJakartaSessionCookieConfig() {
                return from;
            }
        };
    }
}
