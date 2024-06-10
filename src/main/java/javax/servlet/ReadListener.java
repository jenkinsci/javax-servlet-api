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

import java.io.IOException;
import java.util.EventListener;
import java.util.Objects;

/**
 * <p>
 * This class represents a call-back mechanism that will notify implementations as HTTP request data becomes available
 * to be read without blocking.
 * </p>
 *
 * @since Servlet 3.1
 */
public interface ReadListener extends EventListener {

    /**
     * When an instance of the <code>ReadListener</code> is registered with a {@link ServletInputStream}, this method
     * will be invoked by the container the first time when it is possible to read data. Subsequently the container will
     * invoke this method if and only if the {@link javax.servlet.ServletInputStream#isReady()} method has been called
     * and has returned a value of <code>false</code> <em>and</em> data has subsequently become available to read.
     *
     * @throws IOException if an I/O related error has occurred during processing
     */
    void onDataAvailable() throws IOException;

    /**
     * Invoked when all data for the current request has been read.
     *
     * @throws IOException if an I/O related error has occurred during processing
     */
    void onAllDataRead() throws IOException;

    /**
     * Invoked when an error occurs processing the request.
     *
     * @param t the throwable to indicate why the read operation failed
     */
    void onError(Throwable t);

    default jakarta.servlet.ReadListener toJakartaReadListener() {
        return new jakarta.servlet.ReadListener() {
            @Override
            public void onDataAvailable() throws IOException {
                ReadListener.this.onDataAvailable();
            }

            @Override
            public void onAllDataRead() throws IOException {
                ReadListener.this.onAllDataRead();
            }

            @Override
            public void onError(Throwable throwable) {
                ReadListener.this.onError(throwable);
            }
        };
    }

    static ReadListener fromJakartaReadListener(jakarta.servlet.ReadListener from) {
        Objects.requireNonNull(from);
        return new ReadListener() {
            @Override
            public void onDataAvailable() throws IOException {
                from.onDataAvailable();
            }

            @Override
            public void onAllDataRead() throws IOException {
                from.onAllDataRead();
            }

            @Override
            public void onError(Throwable t) {
                from.onError(t);
            }

            @Override
            public jakarta.servlet.ReadListener toJakartaReadListener() {
                return from;
            }
        };
    }
}
