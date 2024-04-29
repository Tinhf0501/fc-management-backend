package com.luke.fcmanagement.module.logging.model;

import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

@Getter
@Slf4j
public class RequestWrapper extends HttpServletRequestWrapper {

    private final String body;

    public RequestWrapper(HttpServletRequest request) throws IOException {
        super(request);
        final ServletInputStream inputStream = request.getInputStream();
        try ( BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream)) ) {
            body = bufferedReader.lines()
                    .collect(Collectors.joining());
        }
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(body.getBytes());

        return new ServletInputStream() {
            @Override
            public boolean isFinished() {
                return false;
            }

            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setReadListener(ReadListener readListener) {
            }

            public int read () throws IOException {
                return byteArrayInputStream.read();
            }
        };
    }
}
