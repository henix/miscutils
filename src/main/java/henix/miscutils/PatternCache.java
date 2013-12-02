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
 * Matcher matcher = PatternCache.get("\\d+").matcher();
 */
@ThreadSafe
public final class PatternCache {

	private static ConcurrentHashMap<String, Pattern> cache = new ConcurrentHashMap<String, Pattern>();

	public static Pattern get(String regex) {
		// 不同步，用空间换时间
		if (!cache.containsKey(regex)) {
			cache.put(regex, Pattern.compile(regex));
		}
		return cache.get(regex);
	}
}
