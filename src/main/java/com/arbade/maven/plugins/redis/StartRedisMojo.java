package com.arbade.maven.plugins.redis;

import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;

@Mojo(name = "compileStart", defaultPhase = LifecyclePhase.NONE)
public class StartRedisMojo extends RunRedisMojo {

    @Override
    public void doExecute(final boolean forked) {
        super.doExecute(true);
    }
}
