package com.arbade.maven.plugins.redis;


import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.exceptions.JedisConnectionException;

import java.io.IOException;

@Mojo(name = "shutdown", defaultPhase = LifecyclePhase.NONE)
public class RuntimeRedisShutdownMojo extends AbstractMojo {

    @Parameter(property = "redis.server.skip", defaultValue = "false")
    public boolean skip;

    @Parameter(property = "redis.server.forked", defaultValue = "false")
    public boolean forked;

    @Parameter(property = "redis.server.isConnected", defaultValue = "true")
    public boolean connected;


    @Override
    public void execute() throws MojoFailureException {

        if (skip) {
            getLog().info("Skipping Redis Server on Runtime...!");
            return;
        }

        try {
            doExecuteShutdownRuntime(forked);

        } catch (Exception e) {
            throw new MojoFailureException("Redis-Shutdwon failure");
        }


    }


    private void doExecuteShutdownRuntime(boolean forked) {
        JedisPool pool = new JedisPool("localhost");
        try {
            pool.getResource().ping();
            Runtime runtime = Runtime.getRuntime();
            runtime.exec("redis-cli shutdown");
            getLog().info("Redis-Server has been shutdown on Runtime...!");

        } catch (JedisConnectionException | IOException e) {
            if (this.connected) {
                connected = false;
                getLog().warn(e);

            }
            connected = true;


        }


    }


}
