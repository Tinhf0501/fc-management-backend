package com.luke.fcmanagement.wrapper;

import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletInputStream;

import java.io.IOException;
import java.io.InputStream;

public class MyServletInputStream extends ServletInputStream {
    private final InputStream inputStream;

    public MyServletInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    @Override
    public int read() throws IOException {
        return inputStream.read();
    }

    @Override
    public int read(byte[] b, int off, int len) throws IOException {
        return inputStream.read(b, off, len);
    }

    @Override
    public boolean isFinished() {
        // Implement as needed
        return false;
    }

    @Override
    public boolean isReady() {
        // Implement as needed
        return false;
    }

    @Override
    public void setReadListener(ReadListener readListener) {
        // Implement as needed
    }
}
