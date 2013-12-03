package henix.miscutils;

import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;

import net.jcip.annotations.ThreadSafe;

/**
 * Java 的 Pattern.compile 本身不带 cache
 *
 * 如果使用 static 变量，会增加很多不必要的名称
 *
 * Usage:
 * 
 * Matcher matcher = PatternCache.get("\\d+").matcher(str);
 */
@ThreadSafe
public final class PatternCache {

	private static final ConcurrentHashMap<String, Pattern> cache = new ConcurrentHashMap<String, Pattern>();

	public static Pattern get(String regex) {
		Pattern patt = cache.get(regex);
		if (patt == null) {
			patt = Pattern.compile(regex);
			cache.put(regex, patt);
		}
		return patt;
	}
}
