package br.com.restautomation.infra;

import javax.servlet.http.Cookie;

import spark.Request;

public class CookieHelper {

	public static String getSessionCookieValue(final Request request) {

		if (request.raw().getCookies() == null) {
			return null;
		}
		for (Cookie cookie : request.raw().getCookies()) {
			if (cookie.getName().equals("session")) {
				return cookie.getValue();
			}
		}
		return null;
	}

	public static Cookie getSessionCookie(final Request request) {

		if (request.raw().getCookies() == null) {
			return null;
		}
		for (Cookie cookie : request.raw().getCookies()) {
			if (cookie.getName().equals("session")) {
				return cookie;
			}
		}
		return null;
	}
}
