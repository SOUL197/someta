package kr.co.ictedu.someta.member;

import java.net.URI;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.net.ssl.SSLContext;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import kr.co.ictedu.someta.pwl.MessageUtils;
import kr.co.ictedu.someta.vo.LoginLogVO;
import kr.co.ictedu.someta.vo.MemberVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/login")
public class LoginController {

	@Autowired
	private LoginService loginService;
	
	// {"id":"tess01","pwd":"11"} -> 이런 형태로 넘어오면, vo에 저장할 것
	@PostMapping("/dologin")
	public String doLogin(HttpSession session, HttpServletRequest request,
			@RequestHeader("User-Agent") String userAgent,
			@RequestBody MemberVO vo) {
		
		Map<String, Object> result = loginService.loginCheck(vo);
		System.out.println("result" + result);
		
		if (result != null && result.get("CNT") != null) {
			int cnt = ((Number) result.get("CNT")).intValue();
			
			if (cnt == 1) {
				System.out.println("세션 처리 완료!");
				vo.setNickname(result.get("NICKNAME").toString());
				vo.setNum(Integer.parseInt(result.get("NUM").toString()));
				// 로그인 처리를 완료하기 위해서 세션 Scope에 키(key)와 값(value)으로 저장
				// vo에는 nickname,
				session.setAttribute("loginMember", vo);
				return "success";
			}
		}
		return "fail";
	}
	
	@GetMapping("/dologout")
	public String doLogout(HttpSession session, HttpServletRequest request,
			@RequestHeader("User-Agent") String userAgent) {
		System.out.println("로그아웃 처리 완료!");
		session.invalidate();
		return "logout";
	}
	
	@GetMapping("/session")
	public MemberVO session(HttpSession session) {
		MemberVO loginMember = (MemberVO) session.getAttribute("loginMember");
		
		// 로그인의 상태를 확인할 때 setPassword는 json으로 노출 안 되게 null
		if (loginMember != null) {
			loginMember.setPwd(null);
		}
		
		return loginMember; // username, id
	}
	
	@GetMapping("/loginlog")
	public List<LoginLogVO> loginlog(HttpSession session){
		MemberVO loginMember = (MemberVO) session.getAttribute("loginMember");
		return loginService.loginlog(loginMember.getNum());
	}
	
	// ------------------------------------------------ Passwordless ------------------------------------------------
	@Autowired
	MessageUtils messageUtils;

	@Value("${passwordless.corpId}")
	private String corpId;

	@Value("${passwordless.serverId}")
	private String serverId;

	@Value("${passwordless.serverKey}")
	private String serverKey;

	@Value("${passwordless.simpleAutopasswordUrl}")
	private String simpleAutopasswordUrl;

	@Value("${passwordless.restCheckUrl}")
	private String restCheckUrl;

	@Value("${passwordless.pushConnectorUrl}")
	private String pushConnectorUrl;

	@Value("${passwordless.recommend}")
	private String recommend;

	// Passwordless URL
	private String isApUrl = "/ap/rest/auth/isAp"; // Check Passwordless Registration Status
	private String joinApUrl = "/ap/rest/auth/joinAp"; // Passwordless Registration REST API
	private String withdrawalApUrl = "/ap/rest/auth/withdrawalAp"; // Passwordless Deregistration REST API
	private String getTokenForOneTimeUrl = "/ap/rest/auth/getTokenForOneTime"; // Passwordless One-Time Token Request
																				// REST API
	private String getSpUrl = "/ap/rest/auth/getSp"; // Passwordless Authentication Request REST API
	private String resultUrl = "/ap/rest/auth/result"; // Passwordless Authentication Result Request REST API
	private String cancelUrl = "/ap/rest/auth/cancel"; // Passwordless Authentication Request Cancellation REST API

		
		// Login
		@PostMapping(value="passwordlessManageCheck", produces="application/json;charset=utf8")
		public Map<String, Object> passwordlessManageCheck(
			@RequestParam(value = "id", required = false) String id,
			@RequestParam(value = "pwd", required = false) String pwd,
			HttpServletRequest request) {
			System.out.println("패스워드리스");
			if(id == null)	id = "";
			if(pwd == null)	pwd = "";

			log.info("passwordlessManageCheck : id [" + id + "] pwd ["  + pwd + "]");

			Map<String, Object> mapResult = new HashMap<String, Object>();

			if(!id.equals("") && !pwd.equals("")) {
				
				MemberVO vo = new MemberVO();
				vo.setId(id);
				vo.setPwd(pwd);
				MemberVO newVo = loginService.checkPassword(vo);
				
				if(newVo != null) {
					String tmpToken = java.util.UUID.randomUUID().toString();
					String tmpTime = Long.toString(System.currentTimeMillis());
					
					log.info("passwordlessManageCheck : token [" + tmpToken + "] time [" + tmpTime + "]");
					
					HttpSession session = request.getSession(true);
					session.setAttribute("PasswordlessToken", tmpToken);
					session.setAttribute("PasswordlessTime", tmpTime);
					
					mapResult.put("PasswordlessToken", tmpToken);
					mapResult.put("result", "OK");
				}
				else {
					mapResult.put("result", messageUtils.getMessage("text.passwordless.invalid"));	// Invalid id or password.
				}
			}
			else {
				mapResult.put("result", messageUtils.getMessage("text.passwordless.empty"));	// ID or Password is empty.
			}
			
			return mapResult;
		}
		
		@RequestMapping(value="/passwordlessCallApi")
		public ModelMap passwordlessCallApi(
				@RequestParam(value = "url", required = false) String url,
				@RequestParam(value = "params", required = false) String params,
				HttpServletRequest request, HttpServletResponse response){
			System.out.println("로그인 컨트롤러");
			ModelMap modelMap = new ModelMap();
			String result = "";

			boolean existMember = false;
			
			if(url == null)		url = "";
			if(params == null)	params = "";
			
			Map<String, String> mapParams = getParamsKeyValue(params);
			
			String userId = "";
			String userToken = "";
			
			HttpSession session = request.getSession();
			String sessionUserToken = (String) session.getAttribute("PasswordlessToken");
			String sessionTime = (String) session.getAttribute("PasswordlessTime");
			
			if(sessionUserToken == null)	sessionUserToken = "";
			if(sessionTime == null)			sessionTime = "";
			
			long nowTime = System.currentTimeMillis();
			long tokenTime = 0L;
			int gapTime = 0;
			try {
				tokenTime = Long.parseLong(sessionTime);
				gapTime = (int) (nowTime - tokenTime);
			}
			catch(Exception e) {
				gapTime = 99999999;
			}
			
			userId = mapParams.get("userId");
			userToken = mapParams.get("token");
			
			boolean matchToken = false;
			if(!sessionUserToken.equals("") && sessionUserToken.equals(userToken))
				matchToken = true;
			
			log.info("passwordlessCallApi : [" + url + "] userId=" + userId + ", Token Match [" + matchToken + "] userToken[" + userToken + "], sessionUserToken [" + sessionUserToken + "], gapTime [" + gapTime + "]");
			
			if(userId == null)		userId = "";
			if(userToken == null)	userToken = "";
			
			// Identity verification for QR request and cancellation
			if (url.equals("joinApUrl") || url.equals("withdrawalApUrl")) {
			    // If the user is not logged in for Passwordless settings
			    if (!matchToken) {
			        modelMap.put("result", messageUtils.getMessage("text.passwordless.abnormal")); // This is not a normal user.
			        return modelMap;
			    }
			    // If more than 5 minutes have passed after logging in for Passwordless settings, handle as Timeout
			    else if (gapTime > 5 * 60 * 1000) {
			        modelMap.put("result", messageUtils.getMessage("text.passwordless.expired")); // Passwordless management token expired.
			        return modelMap;
			    }
			}

			
			if(!url.equals("resultUrl")) {
				log.info("passwordlessCallApi : url [" + url + "] params [" + params + "] userId [" + userId + "]");
			}
			
			MemberVO vo = new MemberVO();
			vo.setId(userId);
			MemberVO newVo = loginService.getUserInfo(vo);
			
			if(newVo == null) {
				String tmp_result = messageUtils.getMessage("text.passwordless.idnotexist");	// ID [" + id + "] does not exist.
				modelMap.put("result", tmp_result.replace("@@@", userId));
				return modelMap;
			}
			else {
				String random = java.util.UUID.randomUUID().toString();
		    	String sessionId = System.currentTimeMillis() + "_sessionId";
		    	String apiUrl = "";
		    	String ip = request.getRemoteAddr();
		    	
		    	if(ip.equals("0:0:0:0:0:0:0:1"))
		    		ip = "127.0.0.1";
		    	
		    	if(url.equals("isApUrl"))				{ apiUrl = isApUrl; }
				if(url.equals("joinApUrl"))				{ apiUrl = joinApUrl; }
				if(url.equals("withdrawalApUrl"))		{ apiUrl = withdrawalApUrl; }
				if(url.equals("getTokenForOneTimeUrl"))	{ apiUrl = getTokenForOneTimeUrl; }
				if(url.equals("getSpUrl"))				{ apiUrl = getSpUrl; params += "&clientIp=" + ip + "&sessionId=" + sessionId + "&random=" + random + "&password="; }
				if(url.equals("resultUrl"))				{ apiUrl = resultUrl;}
				if(url.equals("cancelUrl"))				{ apiUrl = cancelUrl;}
				
				if(!url.equals("resultUrl")) {
					log.info("passwordlessCallApi : url [" + url + "], param [" + params + "], apiUrl [" + apiUrl + "]");
				}
				
				if(!apiUrl.equals("")) {
					try {
						if(!url.equals("getSpUrl") && !url.equals("resultUrl")) {
							log.info("passwordlessCallApi : url [" + (restCheckUrl + apiUrl + "?" + params) + "]");
						}

						result = callApi("POST", restCheckUrl + apiUrl, params);
					} catch(Exception e) {
						e.printStackTrace();
					}
				}
				
				if(!url.equals("getSpUrl") && !url.equals("resultUrl")) {
					log.info("passwordlessCallApi : result [" + result + "]");
				}

				// One-Time Token Request
				if(url.equals("getTokenForOneTimeUrl")) {
					String token = "";
					String oneTimeToken = "";
					
					JSONParser parser = new JSONParser();
					try {
						JSONObject jsonResponse = (JSONObject)parser.parse(result);
					    JSONObject jsonData = (JSONObject) jsonResponse.get("data");
					    token = (String) (jsonData).get("token");
					    oneTimeToken = getDecryptAES(token, serverKey.getBytes());
					} catch(ParseException pe) {
						pe.printStackTrace();
					}
					log.info("passwordlessCallApi : token [" + token + "] --> oneTimeToken [" + oneTimeToken + "]");
					
					modelMap.put("oneTimeToken", oneTimeToken);
				}
				
				// Passwordless Authentication Request REST API
				if(url.equals("getSpUrl")) {
					modelMap.put("sessionId", sessionId);
				}
				
				// Passwordless QR Registration Waiting WebSocket URL
				if(url.equals("joinApUrl")) {
					modelMap.put("pushConnectorUrl", pushConnectorUrl);
				}
				
				// Passwordless QR Registration Waiting WebSocket URL
				if(url.equals("isApUrl")) {
					log.info("passwordlessCallApi : mapParams=" + mapParams.toString());
					try {
						String isQRReg = mapParams.get("QRReg");
						if(isQRReg.equals("T")) {
							JSONParser parser = new JSONParser();
							JSONObject jsonResponse = (JSONObject)parser.parse(result);
						    JSONObject jsonData = (JSONObject) jsonResponse.get("data");
						    boolean exist = (boolean) (jsonData).get("exist");
						    
						    if(exist) {
						    	  // Change password after QR registration is complete
						    	log.info("passwordlessCallApi : QR Registration Complete --> Change Password");
						    	String newPw = Long.toString(System.currentTimeMillis()) + ":" + userId;
								vo.setId(userId);
								vo.setPwd(newPw);
								loginService.changepw(vo);
						    }
						}
					}
					catch(NullPointerException npe) {
						//
					} catch(ParseException pe) {
						//
					}
				}
				
				// Passwordless Approval Waiting
				if(url.equals("resultUrl")) {
					JSONParser parser = new JSONParser();
					try {
						JSONObject jsonResponse = (JSONObject)parser.parse(result);
					    JSONObject jsonData = (JSONObject) jsonResponse.get("data");
					    
					    if(jsonData != null) {
						    String auth = (String) (jsonData).get("auth");
			
						    if(auth != null && auth.equals("Y")) {
						    	// Change password upon successful login
						    	log.info("passwordlessCallApi : Login Success --> Change Password");
								String newPw = Long.toString(System.currentTimeMillis()) + ":" + userId;
								vo = new MemberVO();
								vo.setId(userId);
								vo.setPwd(newPw);
								vo.setNickname(newVo.getNickname().toString());
								vo.setNum(newVo.getNum());
								loginService.changepw(vo);
								
								session.setAttribute("loginMember", vo);
							}
					    }
					} catch(ParseException pe) {
						pe.printStackTrace();
					}
				}

				modelMap.put("result", "OK");
			}
			
			JSONParser parser = new JSONParser();
			JSONObject jsonResult;
			try {
				jsonResult = (JSONObject) parser.parse(result);
				modelMap.put("data", jsonResult.get("data"));
				modelMap.put("result", jsonResult.get("result"));
				modelMap.put("code", jsonResult.get("code"));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}


			return modelMap;
		}
		
		public String callApi(String type, String requestURL, String params) {

	 		String retVal = "";
	 		Map<String, String> mapParams = getParamsKeyValue(params);

	 		try {
				URIBuilder b = new URIBuilder(requestURL);
				
				Set<String> set = mapParams.keySet();
				Iterator<String> keyset = set.iterator();
				while(keyset.hasNext()) {
					String key = keyset.next();
					String value = mapParams.get(key);
					b.addParameter(key, value);
				}
				URI uri = b.build();
				
				// 신뢰할 수 없는 인증서 허용
		        SSLContext sslContext = SSLContextBuilder
		            .create()
		            .loadTrustMaterial(new TrustSelfSignedStrategy())
		            .build();
			
			    CloseableHttpClient httpClient = HttpClientBuilder.create().build();
			    
			    org.apache.http.HttpResponse response;
			    
			    if(type.toUpperCase().equals("POST")) {
			        HttpPost httpPost = new HttpPost(uri);
			        httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded");
			    	response = httpClient.execute(httpPost);
			    }
			    else {
			    	HttpGet httpGet = new HttpGet(uri);
			    	httpGet.addHeader("Content-Type", "application/x-www-form-urlencoded");
			    	response = httpClient.execute(httpGet);
			    }
			    
			    HttpEntity entity = response.getEntity();
			    retVal = EntityUtils.toString(entity);
			} catch(Exception e) {
				System.out.println("Rest API call error !!!");
				System.out.println(e.toString());
			}
			
			return retVal;
	 	}
		
		public Map<String, String> getParamsKeyValue(String params) {
	 		String[] arrParams = params.split("&");
	         Map<String, String> map = new HashMap<String, String>();
	         for (String param : arrParams)
	         {
	         	String name = "";
	         	String value = "";
	         	
	         	String[] tmpArr = param.split("=");
	             name = tmpArr[0];
	             
	             if(tmpArr.length == 2)
	             	value = tmpArr[1];
	             
	             map.put(name, value);
	         }

	         return map;
	 	}
		
		private static String getDecryptAES(String encrypted, byte[] key) {
	 		String strRet = null;
	 		
	 		byte[]  strIV = key;
	 		if ( key == null || strIV == null ) return null;
	 		try {
	 			SecretKey secureKey = new SecretKeySpec(key, "AES");
	 			Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
	 			c.init(Cipher.DECRYPT_MODE, secureKey, new IvParameterSpec(strIV));
	 			byte[] byteStr = java.util.Base64.getDecoder().decode(encrypted);//Base64Util.getDecData(encrypted);
	 			strRet = new String(c.doFinal(byteStr), "utf-8");
	 		} catch (Exception e) {
	 			e.printStackTrace();
	 		}
	 		return strRet;	
	 	}
}
