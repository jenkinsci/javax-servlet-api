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
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Objects;

/**
 *
 * Provides an input stream for reading binary data from a client request, including an efficient <code>readLine</code>
 * method for reading data one line at a time. With some protocols, such as HTTP POST and PUT, a
 * <code>ServletInputStream</code> object can be used to read data sent from the client.
 *
 * <p>
 * A <code>ServletInputStream</code> object is normally retrieved via the {@link ServletRequest#getInputStream} method.
 *
 *
 * <p>
 * This is an abstract class that a servlet container implements. Subclasses of this class must implement the
 * <code>java.io.InputStream.read()</code> method.
 *
 *
 * @author Various
 *
 * @see ServletRequest
 *
 */
public abstract class ServletInputStream extends InputStream {

    /**
     * Does nothing, because this is an abstract class.
     *
     */
    protected ServletInputStream() {}

    /**
     *
     * Reads the input stream, one line at a time. Starting at an offset, reads bytes into an array, until it reads a
     * certain number of bytes or reaches a newline character, which it reads into the array as well.
     *
     * <p>
     * This method returns -1 if it reaches the end of the input stream before reading the maximum number of bytes.
     *
     *
     *
     * @param b   an array of bytes into which data is read
     *
     * @param off an integer specifying the character at which this method begins reading
     *
     * @param len an integer specifying the maximum number of bytes to read
     *
     * @return an integer specifying the actual number of bytes read, or -1 if the end of the stream is reached
     *
     * @exception IOException if an input or output exception has occurred
     *
     */
    public int readLine(byte[] b, int off, int len) throws IOException {

        if (len <= 0) {
            return 0;
        }
        int count = 0, c;

        while ((c = read()) != -1) {
            b[off++] = (byte) c;
            count++;
            if (c == '\n' || count == len) {
                break;
            }
        }
        return count > 0 ? count : -1;
    }

    /**
     * Returns true when all the data from the stream has been read else it returns false.
     *
     * @return <code>true</code> when all data for this particular request has been read, otherwise returns
     *         <code>false</code>.
     *
     * @since Servlet 3.1
     */
    public abstract boolean isFinished();

    /**
     * Returns true if data can be read without blocking else returns false.
     *
     * @return <code>true</code> if data can be obtained without blocking, otherwise returns <code>false</code>.
     *
     * @since Servlet 3.1
     */
    public abstract boolean isReady();

    /**
     * Instructs the <code>ServletInputStream</code> to invoke the provided {@link ReadListener} when it is possible to
     * read
     *
     * @param readListener the {@link ReadListener} that should be notified when it's possible to read.
     *
     * @exception IllegalStateException if one of the following conditions is true
     *                                  <ul>
     *                                  <li>the associated request is neither upgraded nor the async started
     *                                  <li>setReadListener is called more than once within the scope of the same
     *                                  request.
     *                                  </ul>
     *
     * @throws NullPointerException if readListener is null
     *
     * @since Servlet 3.1
     *
     */
    public abstract void setReadListener(ReadListener readListener);

    public jakarta.servlet.ServletInputStream toJakartaServletInputStream() {
        return new jakarta.servlet.ServletInputStream() {
            @Override
            public int read() throws IOException {
                return ServletInputStream.this.read();
            }

            @Override
            public int readLine(byte[] b, int off, int len) throws IOException {
                return ServletInputStream.this.readLine(b, off, len);
            }

            @Override
            public int read(byte[] b) throws IOException {
                return ServletInputStream.this.read(b);
            }

            @Override
            public int read(byte[] b, int off, int len) throws IOException {
                return ServletInputStream.this.read(b, off, len);
            }

            @Override
            public byte[] readAllBytes() throws IOException {
                return ServletInputStream.this.readAllBytes();
            }

            @Override
            public byte[] readNBytes(int len) throws IOException {
                return ServletInputStream.this.readNBytes(len);
            }

            @Override
            public int readNBytes(byte[] b, int off, int len) throws IOException {
                return ServletInputStream.this.readNBytes(b, off, len);
            }

            @Override
            public long skip(long n) throws IOException {
                return ServletInputStream.this.skip(n);
            }

            @Override
            public int available() throws IOException {
                return ServletInputStream.this.available();
            }

            @Override
            public void close() throws IOException {
                ServletInputStream.this.close();
            }

            @Override
            public synchronized void mark(int readlimit) {
                ServletInputStream.this.mark(readlimit);
            }

            @Override
            public synchronized void reset() throws IOException {
                ServletInputStream.this.reset();
            }

            @Override
            public boolean markSupported() {
                return ServletInputStream.this.markSupported();
            }

            @Override
            public long transferTo(OutputStream out) throws IOException {
                return ServletInputStream.this.transferTo(out);
            }

            @Override
            public boolean isFinished() {
                return ServletInputStream.this.isFinished();
            }

            @Override
            public boolean isReady() {
                return ServletInputStream.this.isReady();
            }

            @Override
            public void setReadListener(jakarta.servlet.ReadListener readListener) {
                ServletInputStream.this.setReadListener(ReadListener.fromJakartaReadListener(readListener));
            }
        };
    }

    public static ServletInputStream fromJakartaServletInputStream(jakarta.servlet.ServletInputStream from) {
        Objects.requireNonNull(from);
        return new ServletInputStream() {
            @Override
            public int readLine(byte[] b, int off, int len) throws IOException {
                return from.readLine(b, off, len);
            }

            @Override
            public int read(byte[] b) throws IOException {
                return from.read(b);
            }

            @Override
            public int read(byte[] b, int off, int len) throws IOException {
                return from.read(b, off, len);
            }

            @Override
            public byte[] readAllBytes() throws IOException {
                return from.readAllBytes();
            }

            @Override
            public byte[] readNBytes(int len) throws IOException {
                return from.readNBytes(len);
            }

            @Override
            public int readNBytes(byte[] b, int off, int len) throws IOException {
                return from.readNBytes(b, off, len);
            }

            @Override
            public long skip(long n) throws IOException {
                return from.skip(n);
            }

            @Override
            public int available() throws IOException {
                return from.available();
            }

            @Override
            public void close() throws IOException {
                from.close();
            }

            @Override
            public synchronized void mark(int readlimit) {
                from.mark(readlimit);
            }

            @Override
            public synchronized void reset() throws IOException {
                from.reset();
            }

            @Override
            public boolean markSupported() {
                return from.markSupported();
            }

            @Override
            public long transferTo(OutputStream out) throws IOException {
                return from.transferTo(out);
            }

            @Override
            public boolean isFinished() {
                return from.isFinished();
            }

            @Override
            public boolean isReady() {
                return from.isReady();
            }

            @Override
            public void setReadListener(ReadListener readListener) {
                from.setReadListener(readListener.toJakartaReadListener());
            }

            @Override
            public int read() throws IOException {
                return from.read();
            }

            @Override
            public jakarta.servlet.ServletInputStream toJakartaServletInputStream() {
                return from;
            }
        };
    }
}
