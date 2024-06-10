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
 * Listener that will be notified in the event that an asynchronous operation initiated on a ServletRequest to which the
 * listener had been added has completed, timed out, or resulted in an error.
 *
 * @since Servlet 3.0
 */
public interface AsyncListener extends EventListener {

    /**
     * Notifies this AsyncListener that an asynchronous operation has been completed.
     *
     * <p>
     * The {@link AsyncContext} corresponding to the asynchronous operation that has been completed may be obtained by
     * calling {@link AsyncEvent#getAsyncContext getAsyncContext} on the given <code>event</code>.
     *
     * <p>
     * In addition, if this AsyncListener had been registered via a call to
     * {@link AsyncContext#addListener(AsyncListener, ServletRequest, ServletResponse)}, the supplied ServletRequest and
     * ServletResponse objects may be retrieved by calling {@link AsyncEvent#getSuppliedRequest getSuppliedRequest} and
     * {@link AsyncEvent#getSuppliedResponse getSuppliedResponse}, respectively, on the given <code>event</code>.
     *
     * @param event the AsyncEvent indicating that an asynchronous operation has been completed
     *
     * @throws IOException if an I/O related error has occurred during the processing of the given AsyncEvent
     */
    void onComplete(AsyncEvent event) throws IOException;

    /**
     * Notifies this AsyncListener that an asynchronous operation has timed out.
     *
     * <p>
     * The {@link AsyncContext} corresponding to the asynchronous operation that has timed out may be obtained by
     * calling {@link AsyncEvent#getAsyncContext getAsyncContext} on the given <code>event</code>.
     *
     * <p>
     * In addition, if this AsyncListener had been registered via a call to
     * {@link AsyncContext#addListener(AsyncListener, ServletRequest, ServletResponse)}, the supplied ServletRequest and
     * ServletResponse objects may be retrieved by calling {@link AsyncEvent#getSuppliedRequest getSuppliedRequest} and
     * {@link AsyncEvent#getSuppliedResponse getSuppliedResponse}, respectively, on the given <code>event</code>.
     *
     * @param event the AsyncEvent indicating that an asynchronous operation has timed out
     *
     * @throws IOException if an I/O related error has occurred during the processing of the given AsyncEvent
     */
    void onTimeout(AsyncEvent event) throws IOException;

    /**
     * Notifies this AsyncListener that an asynchronous operation has failed to complete.
     *
     * <p>
     * The {@link AsyncContext} corresponding to the asynchronous operation that failed to complete may be obtained by
     * calling {@link AsyncEvent#getAsyncContext getAsyncContext} on the given <code>event</code>.
     *
     * <p>
     * In addition, if this AsyncListener had been registered via a call to
     * {@link AsyncContext#addListener(AsyncListener, ServletRequest, ServletResponse)}, the supplied ServletRequest and
     * ServletResponse objects may be retrieved by calling {@link AsyncEvent#getSuppliedRequest getSuppliedRequest} and
     * {@link AsyncEvent#getSuppliedResponse getSuppliedResponse}, respectively, on the given <code>event</code>.
     *
     * @param event the AsyncEvent indicating that an asynchronous operation has failed to complete
     *
     * @throws IOException if an I/O related error has occurred during the processing of the given AsyncEvent
     */
    void onError(AsyncEvent event) throws IOException;

    /**
     * Notifies this AsyncListener that a new asynchronous cycle is being initiated via a call to one of the
     * {@link ServletRequest#startAsync} methods.
     *
     * <p>
     * The {@link AsyncContext} corresponding to the asynchronous operation that is being reinitialized may be obtained
     * by calling {@link AsyncEvent#getAsyncContext getAsyncContext} on the given <code>event</code>.
     *
     * <p>
     * In addition, if this AsyncListener had been registered via a call to
     * {@link AsyncContext#addListener(AsyncListener, ServletRequest, ServletResponse)}, the supplied ServletRequest and
     * ServletResponse objects may be retrieved by calling {@link AsyncEvent#getSuppliedRequest getSuppliedRequest} and
     * {@link AsyncEvent#getSuppliedResponse getSuppliedResponse}, respectively, on the given <code>event</code>.
     *
     * <p>
     * This AsyncListener will not receive any events related to the new asynchronous cycle unless it registers itself
     * (via a call to {@link AsyncContext#addListener}) with the AsyncContext that is delivered as part of the given
     * AsyncEvent.
     *
     * @param event the AsyncEvent indicating that a new asynchronous cycle is being initiated
     *
     * @throws IOException if an I/O related error has occurred during the processing of the given AsyncEvent
     */
    void onStartAsync(AsyncEvent event) throws IOException;

    default jakarta.servlet.AsyncListener toJakartaAsyncListener() {
        return new jakarta.servlet.AsyncListener() {

            @Override
            public void onComplete(jakarta.servlet.AsyncEvent event) throws IOException {
                AsyncListener.this.onComplete(AsyncEvent.fromJakartaServletHttpAsyncEvent(event));
            }

            @Override
            public void onTimeout(jakarta.servlet.AsyncEvent event) throws IOException {
                AsyncListener.this.onTimeout(AsyncEvent.fromJakartaServletHttpAsyncEvent(event));
            }

            @Override
            public void onError(jakarta.servlet.AsyncEvent event) throws IOException {
                AsyncListener.this.onError(AsyncEvent.fromJakartaServletHttpAsyncEvent(event));
            }

            @Override
            public void onStartAsync(jakarta.servlet.AsyncEvent event) throws IOException {
                AsyncListener.this.onStartAsync(AsyncEvent.fromJakartaServletHttpAsyncEvent(event));
            }
        };
    }

    static AsyncListener fromJakartaAsyncListener(jakarta.servlet.AsyncListener from) {
        Objects.requireNonNull(from);
        return new AsyncListener() {

            @Override
            public void onComplete(AsyncEvent event) throws IOException {
                from.onComplete(AsyncEvent.toJakartaServletHttpAsyncEvent(event));
            }

            @Override
            public void onTimeout(AsyncEvent event) throws IOException {
                from.onTimeout(AsyncEvent.toJakartaServletHttpAsyncEvent(event));
            }

            @Override
            public void onError(AsyncEvent event) throws IOException {
                from.onError(AsyncEvent.toJakartaServletHttpAsyncEvent(event));
            }

            @Override
            public void onStartAsync(AsyncEvent event) throws IOException {
                from.onStartAsync(AsyncEvent.toJakartaServletHttpAsyncEvent(event));
            }

            @Override
            public jakarta.servlet.AsyncListener toJakartaAsyncListener() {
                return from;
            }
        };
    }
}
