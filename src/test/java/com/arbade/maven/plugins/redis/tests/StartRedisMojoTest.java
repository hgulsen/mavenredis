package com.arbade.maven.plugins.redis.tests;

import redis.clients.jedis.Jedis;
import com.arbade.maven.plugins.redis.ShutdownRedisMojo;
import com.arbade.maven.plugins.redis.StartRedisMojo;

public class StartRedisMojoTest extends AbstractRedisMojoTest {

    public static final String FORKED_POM_FILE = "src/test/resources/unit/pom.xml";

    public void testRunForked() throws Exception {
        final StartRedisMojo startRedisMojo = lookupRedisMojo(FORKED_POM_FILE, "compileStart");
        assertNotNull(startRedisMojo);

        startRedisMojo.execute();

        final Jedis jedis = new Jedis("localhost", startRedisMojo.port);
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
