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

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Objects;

/**
 * <p>
 * This class represents a part or form item that was received within a <code>multipart/form-data</code> POST request.
 *
 * @since Servlet 3.0
 */
public interface Part {

    /**
     * Gets the content of this part as an <code>InputStream</code>
     *
     * @return The content of this part as an <code>InputStream</code>
     * @throws IOException If an error occurs in retrieving the content as an <code>InputStream</code>
     */
    InputStream getInputStream() throws IOException;

    /**
     * Gets the content type of this part.
     *
     * @return The content type of this part.
     */
    String getContentType();

    /**
     * Gets the name of this part
     *
     * @return The name of this part as a <code>String</code>
     */
    String getName();

    /**
     * Gets the file name specified by the client
     *
     * @return the submitted file name
     *
     * @since Servlet 3.1
     */
    String getSubmittedFileName();

    /**
     * Returns the size of this fille.
     *
     * @return a <code>long</code> specifying the size of this part, in bytes.
     */
    long getSize();

    /**
     * A convenience method to write this uploaded item to disk.
     *
     * <p>
     * This method is not guaranteed to succeed if called more than once for the same part. This allows a particular
     * implementation to use, for example, file renaming, where possible, rather than copying all of the underlying
     * data, thus gaining a significant performance benefit.
     *
     * @param fileName The location into which the uploaded part should be stored. The value may be a file name or a
     *                 path. The actual location of the file in the filesystem is relative to
     *                 {@link javax.servlet.MultipartConfigElement#getLocation()}. Absolute paths are used as provided
     *                 and are relative to <code>getLocation()</code>. Note: that this is a system dependent string and
     *                 URI notation may not be acceptable on all systems. For portability, this string should be
     *                 generated with the File or Path APIs.
     *
     * @throws IOException if an error occurs.
     */
    void write(String fileName) throws IOException;

    /**
     * Deletes the underlying storage for a file item, including deleting any associated temporary disk file.
     *
     * @throws IOException if an error occurs.
     */
    void delete() throws IOException;

    /**
     *
     * Returns the value of the specified mime header as a <code>String</code>. If the Part did not include a header of
     * the specified name, this method returns <code>null</code>. If there are multiple headers with the same name, this
     * method returns the first header in the part. The header name is case insensitive. You can use this method with
     * any request header.
     *
     * @param name a <code>String</code> specifying the header name
     *
     * @return a <code>String</code> containing the value of the requested header, or <code>null</code> if the part does
     *         not have a header of that name
     */
    String getHeader(String name);

    /**
     * Gets the values of the Part header with the given name.
     *
     * <p>
     * Any changes to the returned <code>Collection</code> must not affect this <code>Part</code>.
     *
     * <p>
     * Part header names are case insensitive.
     *
     * @param name the header name whose values to return
     *
     * @return a (possibly empty) <code>Collection</code> of the values of the header with the given name
     */
    Collection<String> getHeaders(String name);

    /**
     * Gets the header names of this Part.
     *
     * <p>
     * Some servlet containers do not allow servlets to access headers using this method, in which case this method
     * returns <code>null</code>
     *
     * <p>
     * Any changes to the returned <code>Collection</code> must not affect this <code>Part</code>.
     *
     * @return a (possibly empty) <code>Collection</code> of the header names of this Part
     */
    Collection<String> getHeaderNames();

    default jakarta.servlet.http.Part toJakartaPart() {
        return new jakarta.servlet.http.Part() {
            @Override
            public InputStream getInputStream() throws IOException {
                return Part.this.getInputStream();
            }

            @Override
            public String getContentType() {
                return Part.this.getContentType();
            }

            @Override
            public String getName() {
                return Part.this.getName();
            }

            @Override
            public String getSubmittedFileName() {
                return Part.this.getSubmittedFileName();
            }

            @Override
            public long getSize() {
                return Part.this.getSize();
            }

            @Override
            public void write(String fileName) throws IOException {
                Part.this.write(fileName);
            }

            @Override
            public void delete() throws IOException {
                Part.this.delete();
            }

            @Override
            public String getHeader(String name) {
                return Part.this.getHeader(name);
            }

            @Override
            public Collection<String> getHeaders(String name) {
                return Part.this.getHeaders(name);
            }

            @Override
            public Collection<String> getHeaderNames() {
                return Part.this.getHeaderNames();
            }
        };
    }

    static Part fromJakartaPart(jakarta.servlet.http.Part from) {
        Objects.requireNonNull(from);
        return new Part() {
            @Override
            public InputStream getInputStream() throws IOException {
                return from.getInputStream();
            }

            @Override
            public String getContentType() {
                return from.getContentType();
            }

            @Override
            public String getName() {
                return from.getName();
            }

            @Override
            public String getSubmittedFileName() {
                return from.getSubmittedFileName();
            }

            @Override
            public long getSize() {
                return from.getSize();
            }

            @Override
            public void write(String fileName) throws IOException {
                from.write(fileName);
            }

            @Override
            public void delete() throws IOException {
                from.delete();
            }

            @Override
            public String getHeader(String name) {
                return from.getHeader(name);
            }

            @Override
            public Collection<String> getHeaders(String name) {
                return from.getHeaders(name);
            }

            @Override
            public Collection<String> getHeaderNames() {
                return from.getHeaderNames();
            }

            @Override
            public jakarta.servlet.http.Part toJakartaPart() {
                return from;
            }
        };
    }
}
