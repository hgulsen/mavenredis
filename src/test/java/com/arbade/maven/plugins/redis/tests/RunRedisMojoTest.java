package com.arbade.maven.plugins.redis.tests;

import com.arbade.maven.plugins.redis.RunRedisMojo;
import com.arbade.maven.plugins.redis.ShutdownRedisMojo;
import org.apache.maven.plugin.MojoExecutionException;
import redis.clients.jedis.Jedis;

public class RunRedisMojoTest extends AbstractRedisMojoTest {

    public static final String SIMPLE_POM_FILE = "src/test/resources/unit/pom.xml";

    public void testRun() throws Exception {
        final RunRedisMojo runRedisMojo = lookupRedisMojo(SIMPLE_POM_FILE, "compileRun");
        assertNotNull(runRedisMojo);

        Thread redisThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    runRedisMojo.execute();
                } catch (MojoExecutionException e) {
                    e.printStackTrace();
                }
            }
        });

        redisThread.start();

        final Jedis jedis = new Jedis("localhost", runRedisMojo.port);
        waitUntilConnect(jedis);

        assertEquals("OK", jedis.set(TEST_KEY, TEST_VALUE));
        assertEquals(TEST_VALUE, jedis.get(TEST_KEY));

        try {
            jedis.quit();
        } catch(Exception ignored) {

        }

        final ShutdownRedisMojo shutdownRedisMojo = lookupRedisMojo(SIMPLE_POM_FILE, "compileShutdown");
        shutdownRedisMojo.execute();

        testConnectionDown(jedis);
    }
}
