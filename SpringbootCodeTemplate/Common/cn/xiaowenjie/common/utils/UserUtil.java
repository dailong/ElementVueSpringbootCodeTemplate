package cn.xiaowenjie.common.utils;

import java.util.Locale;

import org.apache.log4j.MDC;

import cn.xiaowenjie.beans.User;
import cn.xiaowenjie.common.exceptions.UnloginException;

/**
 * 用户工具类
 * 
 * @author 肖文杰
 *
 */
public class UserUtil {

	private final static ThreadLocal<User> tlUser = new ThreadLocal<User>();

	private final static ThreadLocal<Locale> tlLocale = new ThreadLocal<Locale>() {
		protected Locale initialValue() {
			// 语言的默认值
			return Locale.CHINESE;
		};
	};

	public static final String KEY_LANG = "lang";

	public static final String KEY_USER = "user";

	public static void setUser(User user) {
		tlUser.set(user);

		// 把用户信息放到log4j
		MDC.put(KEY_USER, user.getUsername());
	}

	/**
	 * 如果没有登录，返回null
	 * 
	 * @return
	 */
	public static User getUserIfLogin() {
		return tlUser.get();
	}

	/**
	 * 如果没有登录会抛出异常
	 * 
	 * @return
	 */
	public static User getUser() {
		User user = tlUser.get();

		if (user == null) {
			throw new UnloginException();
		}

		return user;
	}
	
	public static long getUserId(){
		return getUser().getId();
	}

	public static void setLocale(String locale) {
		setLocale(new Locale(locale));
	}

	public static void setLocale(Locale locale) {
		tlLocale.set(locale);
	}

	public static Locale getLocale() {
		return tlLocale.get();
	}

	public static void clearAllUserInfo() {
		tlUser.remove();
		tlLocale.remove();

		MDC.remove(KEY_USER);
	}
}
