package com.arbade.maven.plugins.redis.tests;

import redis.clients.jedis.Jedis;
import com.arbade.maven.plugins.redis.RunRedisMojo;
import com.arbade.maven.plugins.redis.ShutdownRedisMojo;

public class RunForkedRedisMojoTest extends AbstractRedisMojoTest {

    public static final String FORKED_POM_FILE = "src/test/resources/unit/pom.xml";

    public void testRunForked() throws Exception {
        final RunRedisMojo runRedisMojo = lookupRedisMojo(FORKED_POM_FILE, "compileRun");
        assertNotNull(runRedisMojo);

        runRedisMojo.forked = true;

        runRedisMojo.execute();

        final Jedis jedis = new Jedis("localhost", runRedisMojo.port);
        waitUntilConnect(jedis);

        assertEquals("OK", jedis.set(TEST_KEY, TEST_VALUE));
        assertEquals(TEST_VALUE, jedis.get(TEST_KEY));

        try {
            jedis.quit();
        } catch(Exception ignored) {

        }

        final ShutdownRedisMojo shutdownRedisMojo = lookupRedisMojo(FORKED_POM_FILE, "compileShutdown");
        shutdownRedisMojo.execute();

        testConnectionDown(jedis);
    }
}
