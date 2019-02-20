package com.os.framework.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by IntelliJ IDEA. User: gaoxy Date: 11-5-3 Time: 下午1:53 To change
 * this template use File | Settings | File Templates.
 */
public class ManagerFilter implements Filter {

	private FilterConfig filterConfig;

	public void setFilterConfig(final FilterConfig filterConfig) {
		this.filterConfig = filterConfig;
	}

	public FilterConfig getFilterConfig() {
		return filterConfig;
	}

	public void init(FilterConfig config) throws ServletException {
		this.filterConfig = config;
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpSession session = req.getSession();
		String url = req.getRequestURI();
		
		//管理端
		if (url.contains("manager")  ){
			if (session.getAttribute("userInfo") != null) {

				chain.doFilter(request, response);
			} else {
				if (url.contains("login.do") || url.contains("logout.do") || url.contains("login.jsp") || url.contains("validateCode.do") || url.contains("platformLogin.do") || url.contains("web/")) {
					chain.doFilter(request, response);
				} else {
					request.getRequestDispatcher("/logout.do").forward(request, response);
				}
			}
		}
	}

	public void destroy() {
		this.filterConfig = null;
	}
}
