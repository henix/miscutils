package henix.miscutils;

import org.apache.commons.io.Charsets;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.pool.impl.GenericObjectPool;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolProperties;

import redis.clients.jedis.JedisPool;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.List;

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

	public static ConfSpec confspec(String res, String target, Object[] params) {
		return new ConfSpec(res, target, params);
	}

	public static void genconf(Class<?> clazz, List<ConfSpec> confSpecs) throws IOException {
		for (final ConfSpec spec : confSpecs) {
			final InputStream in = clazz.getResourceAsStream(spec.res);
			if (in == null) {
				throw new FileNotFoundException(spec.res);
			}
			final MessageFormat format = new MessageFormat(IOUtils.toString(in, Charsets.UTF_8));
			IOUtils.closeQuietly(in);
			System.out.print("Writing to " + spec.target + " ... ");
			FileUtils.write(new File(spec.target), format.format(spec.params), Charsets.UTF_8);
			System.out.println("done");
		}
	}
}
