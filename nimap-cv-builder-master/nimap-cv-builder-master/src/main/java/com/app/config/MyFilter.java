package com.app.config;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

public class MyFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

		/* wrap the request in order to read the inputstream multiple times */
		MultiReadHttpServletRequest multiReadRequest = new MultiReadHttpServletRequest((HttpServletRequest) request);
		/*
		 * here I read the inputstream and do my thing with it; when I pass the wrapped request through the filter chain, the rest of the filters, and request handlers may read the cached inputstream
		 */
		// doMyThing(multiReadRequest.getInputStream());
		// //OR
		// anotherUsage(multiReadRequest.getReader());
		multiReadRequest.getReader();
		chain.doFilter(multiReadRequest, response);

	}

}
