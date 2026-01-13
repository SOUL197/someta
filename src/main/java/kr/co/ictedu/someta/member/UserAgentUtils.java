package kr.co.ictedu.someta.member;

public class UserAgentUtils {
	public static String parseAgent(String userAgent) {
		String os = "Unknown OS";
		String browser = "Unknown Browser";
		
		if (userAgent.contains("Windows NT")) { // OS 판단
			os = "Windows";
			
		} else if (userAgent.contains("Mac OS X")) {
			os = "macOS";
		} else if (userAgent.contains("Android")) {
			os = "Android";
		} else if (userAgent.contains("iPhone")) {
			os = "iOS";
		}
		
		if (userAgent.contains("Edg/")) { // 브라우저 판단
			browser = "Edge";
			
		} else if (userAgent.contains("Chrome/") && !userAgent.contains("Edg/")) {
			browser = "Chrome";
		} else if (userAgent.contains("Firefox/")) {
			browser = "Firefox";
		} else if (userAgent.contains("Safari/") && !userAgent.contains("Chrome/")) {
			browser = "Safari";
		}
		
		return os + " - " + browser;
	}
}
