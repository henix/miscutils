package henix.miscutils;

import org.apache.commons.pool.impl.GenericObjectPool;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolProperties;

import redis.clients.jedis.JedisPool;

public class ConfigUtils {

	public static JedisPool redis(int port) {
		final GenericObjectPool.Config poolConfig = new GenericObjectPool.Config();
		poolConfig.maxActive = 1024;
		poolConfig.maxWait = 1000; // wait timeout: 1s
		return new JedisPool(poolConfig, "localhost", port);
	}

	public static DataSource db(String host, String schema, String username, String password) {
		final PoolProperties p = new PoolProperties();

		p.setDriverClassName("com.mysql.jdbc.Driver");
		p.setUrl("jdbc:mysql://" + host + "/" + schema);
		p.setUsername(username);
		p.setPassword(password);
		p.setDefaultAutoCommit(true);
		// p.setDefaultReadOnly(true);

		p.setFairQueue(false);

		p.setInitialSize(2);
		p.setMaxActive(100);
		p.setMaxWait(10 * 1000);

		p.setValidationQuery("SELECT 1");
		p.setTestWhileIdle(true);

		p.setTimeBetweenEvictionRunsMillis(60 * 1000);

		p.setRemoveAbandoned(true);
		p.setRemoveAbandonedTimeout(60);
		p.setLogAbandoned(true);

		p.setJdbcInterceptors("org.apache.tomcat.jdbc.pool.interceptor.ConnectionState");

		return new DataSource(p);
	}
}
