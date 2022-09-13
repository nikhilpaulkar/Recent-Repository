package com.app.config;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.springframework.util.StreamUtils;

public class MultiReadHttpServletRequest extends HttpServletRequestWrapper {

	private byte[] cachedBody;

	public MultiReadHttpServletRequest(HttpServletRequest request) throws IOException {

		super(request);

		if (((request.getContentType() == null) || (request.getContentType().toLowerCase().indexOf("multipart/form-data") <= -1))) {

			InputStream requestInputStream = request.getInputStream();
			this.cachedBody = StreamUtils.copyToByteArray(requestInputStream);

		}

	}

	@Override
	public ServletInputStream getInputStream() throws IOException {

		return new CachedBodyServletInputStream(this.cachedBody);

	}

	@Override
	public BufferedReader getReader() throws IOException {

		// Create a reader from cachedContent
		// and return it
		ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(this.cachedBody);
		return new BufferedReader(new InputStreamReader(byteArrayInputStream));

	}

}
