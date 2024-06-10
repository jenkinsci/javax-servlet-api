/*
 * Copyright (c) 1997-2018 Oracle and/or its affiliates and others.
 * All rights reserved.
 * Copyright 2004 The Apache Software Foundation
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package javax.servlet;

import java.io.IOException;
import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * A FilterChain is an object provided by the servlet container to the developer giving a view into the invocation chain
 * of a filtered request for a resource. Filters use the FilterChain to invoke the next filter in the chain, or if the
 * calling filter is the last filter in the chain, to invoke the resource at the end of the chain.
 *
 * @see Filter
 * @since Servlet 2.3
 */
public interface FilterChain {

    /**
     * Causes the next filter in the chain to be invoked, or if the calling filter is the last filter in the chain,
     * causes the resource at the end of the chain to be invoked.
     *
     * @param request  the request to pass along the chain.
     * @param response the response to pass along the chain.
     * @throws IOException      if an I/O related error has occurred during the processing
     * @throws ServletException if an exception has occurred that interferes with the filterChain's normal operation
     */
    void doFilter(ServletRequest request, ServletResponse response) throws IOException, ServletException;

    default jakarta.servlet.FilterChain toJakartaFilterChain() {
        return new jakarta.servlet.FilterChain() {
            @Override
            public void doFilter(jakarta.servlet.ServletRequest request, jakarta.servlet.ServletResponse response)
                    throws IOException, jakarta.servlet.ServletException {
                try {
                    if (request instanceof jakarta.servlet.http.HttpServletRequest
                            && response instanceof jakarta.servlet.http.HttpServletResponse) {
                        jakarta.servlet.http.HttpServletRequest httpRequest =
                                (jakarta.servlet.http.HttpServletRequest) request;
                        jakarta.servlet.http.HttpServletResponse httpResponse =
                                (jakarta.servlet.http.HttpServletResponse) response;
                        FilterChain.this.doFilter(
                                HttpServletRequest.fromJakartaHttpServletRequest(httpRequest),
                                HttpServletResponse.fromJakartaHttpServletResponse(httpResponse));
                    } else {
                        FilterChain.this.doFilter(
                                ServletRequest.fromJakartaServletRequest(request),
                                ServletResponse.fromJakartaServletResponse(response));
                    }
                } catch (ServletException e) {
                    throw e.toJakartaServletException();
                }
            }
        };
    }

    static FilterChain fromJakartaFilterChain(jakarta.servlet.FilterChain from) {
        Objects.requireNonNull(from);
        return new FilterChain() {
            @Override
            public void doFilter(ServletRequest request, ServletResponse response)
                    throws IOException, ServletException {
                try {
                    if (request instanceof HttpServletRequest && response instanceof HttpServletResponse) {
                        HttpServletRequest httpRequest = (HttpServletRequest) request;
                        HttpServletResponse httpResponse = (HttpServletResponse) response;
                        from.doFilter(
                                httpRequest.toJakartaHttpServletRequest(), httpResponse.toJakartaHttpServletResponse());
                    } else {
                        from.doFilter(request.toJakartaServletRequest(), response.toJakartaServletResponse());
                    }
                } catch (jakarta.servlet.ServletException e) {
                    throw ServletException.fromJakartaServletException(e);
                }
            }

            @Override
            public jakarta.servlet.FilterChain toJakartaFilterChain() {
                return from;
            }
        };
    }
}
