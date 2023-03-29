package com.arbade.maven.plugins.redis;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.IOException;


@Mojo(name = "run", defaultPhase = LifecyclePhase.NONE)
public class RuntimeRedisRunMojo extends AbstractMojo {

    @Parameter(property = "redis.server.skip", defaultValue = "false")
    public boolean skip;

    @Parameter(property = "redis.server.forked", defaultValue = "false")
    public boolean forked;

    @Override
    public void execute() throws MojoExecutionException {
        if (skip) {
            getLog().info("Skipping Redis Server on Runtime...!");
            return;
        }

        doExecuteRuntime(forked);
        getLog().info("Redis-Server Started...");

    }

    private void doExecuteRuntime(boolean forked) {
        try {
            Runtime runtime = Runtime.getRuntime();
            runtime.exec("redis-server");
        } catch (IOException e) {
            getLog().warn(e);
        }
    }
}
