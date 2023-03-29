Custom Redis Plugin For Maven
==================

Embedded Test Pure Java Redis Server Plugin for Maven POM


Usage
-----------------

add plugin to your pom:
```xml
<plugin>
    <groupId>com.arbade.maven.plugins</groupId>
    <artifactId>redis-maven-plugin</artifactId>
    <version>1.0.1</version>
</plugin>
```

run ```mvn redis:run```

When you see this message,you are ready to go: ```[INFO] Redis-Server Started...```


Integration tests example
-----------------


Configure plugin as follow steps:
```xml
                    <plugin>
                        <groupId>com.arbade.maven.plugins</groupId>
                        <artifactId>redis-maven-plugin</artifactId>
                        <version>1.0.1</version>
                        <executions>
                            <execution>
                                <id>start-redis</id>
                                <phase>pre-integration-test</phase>
                                <goals>
                                    <goal>compileRun</goal>
                                </goals>
                            </execution>
                            <execution>
                                <id>stop-redis</id>
                                <phase>post-integration-test</phase>
                                <goals>
                                    <goal>compileShutdown</goal>
                                </goals>
                            </execution>
                            <execution>
                                <id>run-redis</id>
                                <phase>run-redis</phase>
                                <goals>
                                    <goal>run</goal>
                                </goals>
                            </execution>
                            <execution>
                                <id>shutdown-redis</id>
                                <phase>shutdown-redis</phase>
                                <goals>
                                    <goal>shutdown</goal>
                                </goals>
                            </execution>
                        </executions>
                    </plugin>
```

Now you will be able to run your integration Redis-backed tests with ```mvn clean verify```

After then, you will be able to see this message you are ready to go about your integration test
``` 
-------------------------------------------------------
 T E S T S
-------------------------------------------------------
Running com.arbade.maven.plugins.redis.example.tests.ConnectionTest
PONG
Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.149 sec
Results :
Tests run: 1, Failures: 0, Errors: 0, Skipped: 0
[INFO] 
[INFO] --- redis-maven-plugin:1.0.1:shutdown (stop-redis) @ redis-maven-plugin-example ---
[INFO] Shutting down Redis server...
[INFO] Redis server shutdown completed
```
Basic example - Run on Localhost & Connect  to Redis
-----------------

Firstly,you will be able to add useful dependency for running up Redis Server on Java

```xml
       <dependencies>
         <dependency>
            <groupId>redis.clients</groupId>
            <artifactId>jedis</artifactId>
            <version>2.2.1</version>
            <scope>compile</scope>
            <optional>true</optional>
        </dependency>
        <dependency>
            <groupId>com.github.kstyrc</groupId>
            <artifactId>embedded-redis</artifactId>
            <version>0.6</version>
        </dependency>
      </dependencies>
```

Secondly,you have to use redis custom plugin for Runtime server running up...

run ```mvn redis:run```

Are you see this message you are ready to go: ```[INFO] Redis-Server Started...```



Thirdly,you can create Jedis Pool Config for Pool Connection...
When you create pool connection, you will be able to set key<-> value insert to Redis-Cli from Java
```java
public class Connection {
    public static void main(String[] args) {

        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        JedisPool pool = new JedisPool(jedisPoolConfig, "localhost", 6379);
        pool.getResource().set("arda", "demir");
        System.out.println(pool.getResource().get("arda"));
    }
}

```
Finally, you will be able to see value of setting up key's on console output.

```
/Library/Java/JavaVirtualMachines/jdk-11.0.7.jdk/Contents/Home/bin/java -javaagent:/Applications/IntelliJ IDEA.app/Contents/lib/idea_rt.jar=57549:/Applications/IntelliJ IDEA.app/Contents/bin -Dfile.encoding=UTF-8 -classpath /Users/arbade/Desktop/example/target/classes:/Users/arbade/.m2/repository/junit/junit/4.11/junit-4.11.jar:/Users/arbade/.m2/repository/org/hamcrest/hamcrest-core/1.3/hamcrest-core-1.3.jar:/Users/arbade/.m2/repository/redis/clients/jedis/2.2.1/jedis-2.2.1.jar:/Users/arbade/.m2/repository/commons-pool/commons-pool/1.6/commons-pool-1.6.jar:/Users/arbade/.m2/repository/com/github/kstyrc/embedded-redis/0.6/embedded-redis-0.6.jar:/Users/arbade/.m2/repository/com/google/guava/guava/18.0/guava-18.0.jar:/Users/arbade/.m2/repository/commons-io/commons-io/2.4/commons-io-2.4.jar:/Users/arbade/.m2/repository/org/springframework/spring-beans/5.2.6.RELEASE/spring-beans-5.2.6.RELEASE.jar:/Users/arbade/.m2/repository/org/springframework/spring-core/5.2.6.RELEASE/spring-core-5.2.6.RELEASE.jar:/Users/arbade/.m2/repository/org/springframework/spring-jcl/5.2.6.RELEASE/spring-jcl-5.2.6.RELEASE.jar Connection
demir

Process finished with exit code 0

```
When you check the redis-cli from terminal,you can be used to following steps(Installed Redis on MacOS) :
```
redis-server
```
```
> redis-cli
arbade@Ardas-MacBook-Pro ~ % redis-cli
127.0.0.1:6379> 
```
```
127.0.0.1:6379> get arda
"demir"
```
Basic example - Shutdown to Redis
-----------------
Firstly, you have to use redis custom plugin for Runtime server for shutting down...

Shutdown ```mvn redis:shutdown```

If Redis-Server is  not running on localhost, you will be able to see this message on logs
``` 
-------------------------------------------------------
 T E S T S
-------------------------------------------------------
[WARNING] 
redis.clients.jedis.exceptions.JedisConnectionException: Could not get a resource from the pool
    at redis.clients.util.Pool.getResource (Pool.java:40)
    at com.arbade.maven.plugins.redis.RuntimeRedisShutdownMojo.doExecuteShutdownRuntime (RuntimeRedisShutdownMojo.java:49)
    at com.arbade.maven.plugins.redis.RuntimeRedisShutdownMojo.execute (RuntimeRedisShutdownMojo.java:37)
    at org.apache.maven.plugin.DefaultBuildPluginManager.executeMojo (DefaultBuildPluginManager.java:137)
    at org.apache.maven.lifecycle.internal.MojoExecutor.execute (MojoExecutor.java:210)
    at org.apache.maven.lifecycle.internal.MojoExecutor.execute (MojoExecutor.java:156)
    at org.apache.maven.lifecycle.internal.MojoExecutor.execute (MojoExecutor.java:148)
    at org.apache.maven.lifecycle.internal.LifecycleModuleBuilder.buildProject (LifecycleModuleBuilder.java:117)
    at org.apache.maven.lifecycle.internal.LifecycleModuleBuilder.buildProject (LifecycleModuleBuilder.java:81)
    at org.apache.maven.lifecycle.internal.builder.singlethreaded.SingleThreadedBuilder.build (SingleThreadedBuilder.java:56)
    at org.apache.maven.lifecycle.internal.LifecycleStarter.execute (LifecycleStarter.java:128)
    at org.apache.maven.DefaultMaven.doExecute (DefaultMaven.java:305)
    at org.apache.maven.DefaultMaven.doExecute (DefaultMaven.java:192)
    at org.apache.maven.DefaultMaven.execute (DefaultMaven.java:105)
    at org.apache.maven.cli.MavenCli.execute (MavenCli.java:957)
    at org.apache.maven.cli.MavenCli.doMain (MavenCli.java:289)
    at org.apache.maven.cli.MavenCli.main (MavenCli.java:193)
    at jdk.internal.reflect.NativeMethodAccessorImpl.invoke0 (Native Method)
    at jdk.internal.reflect.NativeMethodAccessorImpl.invoke (NativeMethodAccessorImpl.java:62)
    at jdk.internal.reflect.DelegatingMethodAccessorImpl.invoke (DelegatingMethodAccessorImpl.java:43)
    at java.lang.reflect.Method.invoke (Method.java:566)
    at org.codehaus.plexus.classworlds.launcher.Launcher.launchEnhanced (Launcher.java:282)
    at org.codehaus.plexus.classworlds.launcher.Launcher.launch (Launcher.java:225)
    at org.codehaus.plexus.classworlds.launcher.Launcher.mainWithExitCode (Launcher.java:406)
    at org.codehaus.plexus.classworlds.launcher.Launcher.main (Launcher.java:347)
    at org.codehaus.classworlds.Launcher.main (Launcher.java:47)
Caused by: redis.clients.jedis.exceptions.JedisConnectionException: java.net.ConnectException: Connection refused (Connection refused)
    at redis.clients.jedis.Connection.connect (Connection.java:137)
    at redis.clients.jedis.BinaryClient.connect (BinaryClient.java:65)
    at redis.clients.jedis.BinaryJedis.connect (BinaryJedis.java:1706)
    at redis.clients.jedis.JedisFactory.makeObject (JedisFactory.java:28)
    at org.apache.commons.pool.impl.GenericObjectPool.borrowObject (GenericObjectPool.java:1188)
    at redis.clients.util.Pool.getResource (Pool.java:38)
    at com.arbade.maven.plugins.redis.RuntimeRedisShutdownMojo.doExecuteShutdownRuntime (RuntimeRedisShutdownMojo.java:49)
    at com.arbade.maven.plugins.redis.RuntimeRedisShutdownMojo.execute (RuntimeRedisShutdownMojo.java:37)
    at org.apache.maven.plugin.DefaultBuildPluginManager.executeMojo (DefaultBuildPluginManager.java:137)
    at org.apache.maven.lifecycle.internal.MojoExecutor.execute (MojoExecutor.java:210)
    at org.apache.maven.lifecycle.internal.MojoExecutor.execute (MojoExecutor.java:156)
    at org.apache.maven.lifecycle.internal.MojoExecutor.execute (MojoExecutor.java:148)
    at org.apache.maven.lifecycle.internal.LifecycleModuleBuilder.buildProject (LifecycleModuleBuilder.java:117)
    at org.apache.maven.lifecycle.internal.LifecycleModuleBuilder.buildProject (LifecycleModuleBuilder.java:81)
    at org.apache.maven.lifecycle.internal.builder.singlethreaded.SingleThreadedBuilder.build (SingleThreadedBuilder.java:56)
    at org.apache.maven.lifecycle.internal.LifecycleStarter.execute (LifecycleStarter.java:128)
    at org.apache.maven.DefaultMaven.doExecute (DefaultMaven.java:305)
    at org.apache.maven.DefaultMaven.doExecute (DefaultMaven.java:192)
    at org.apache.maven.DefaultMaven.execute (DefaultMaven.java:105)
    at org.apache.maven.cli.MavenCli.execute (MavenCli.java:957)
    at org.apache.maven.cli.MavenCli.doMain (MavenCli.java:289)
    at org.apache.maven.cli.MavenCli.main (MavenCli.java:193)
    at jdk.internal.reflect.NativeMethodAccessorImpl.invoke0 (Native Method)
    at jdk.internal.reflect.NativeMethodAccessorImpl.invoke (NativeMethodAccessorImpl.java:62)
    at jdk.internal.reflect.DelegatingMethodAccessorImpl.invoke (DelegatingMethodAccessorImpl.java:43)
    at java.lang.reflect.Method.invoke (Method.java:566)
    at org.codehaus.plexus.classworlds.launcher.Launcher.launchEnhanced (Launcher.java:282)
    at org.codehaus.plexus.classworlds.launcher.Launcher.launch (Launcher.java:225)
    at org.codehaus.plexus.classworlds.launcher.Launcher.mainWithExitCode (Launcher.java:406)
    at org.codehaus.plexus.classworlds.launcher.Launcher.main (Launcher.java:347)
    at org.codehaus.classworlds.Launcher.main (Launcher.java:47)
Caused by: java.net.ConnectException: Connection refused (Connection refused)
    at java.net.PlainSocketImpl.socketConnect (Native Method)
    at java.net.AbstractPlainSocketImpl.doConnect (AbstractPlainSocketImpl.java:399)
    at java.net.AbstractPlainSocketImpl.connectToAddress (AbstractPlainSocketImpl.java:242)
    at java.net.AbstractPlainSocketImpl.connect (AbstractPlainSocketImpl.java:224)
    at java.net.SocksSocketImpl.connect (SocksSocketImpl.java:403)
    at java.net.Socket.connect (Socket.java:608)
    at redis.clients.jedis.Connection.connect (Connection.java:132)
    at redis.clients.jedis.BinaryClient.connect (BinaryClient.java:65)
    at redis.clients.jedis.BinaryJedis.connect (BinaryJedis.java:1706)
    at redis.clients.jedis.JedisFactory.makeObject (JedisFactory.java:28)
    at org.apache.commons.pool.impl.GenericObjectPool.borrowObject (GenericObjectPool.java:1188)
    at redis.clients.util.Pool.getResource (Pool.java:38)
    at com.arbade.maven.plugins.redis.RuntimeRedisShutdownMojo.doExecuteShutdownRuntime (RuntimeRedisShutdownMojo.java:49)
    at com.arbade.maven.plugins.redis.RuntimeRedisShutdownMojo.execute (RuntimeRedisShutdownMojo.java:37)
    at org.apache.maven.plugin.DefaultBuildPluginManager.executeMojo (DefaultBuildPluginManager.java:137)
    at org.apache.maven.lifecycle.internal.MojoExecutor.execute (MojoExecutor.java:210)
    at org.apache.maven.lifecycle.internal.MojoExecutor.execute (MojoExecutor.java:156)
    at org.apache.maven.lifecycle.internal.MojoExecutor.execute (MojoExecutor.java:148)
    at org.apache.maven.lifecycle.internal.LifecycleModuleBuilder.buildProject (LifecycleModuleBuilder.java:117)
    at org.apache.maven.lifecycle.internal.LifecycleModuleBuilder.buildProject (LifecycleModuleBuilder.java:81)
    at org.apache.maven.lifecycle.internal.builder.singlethreaded.SingleThreadedBuilder.build (SingleThreadedBuilder.java:56)
    at org.apache.maven.lifecycle.internal.LifecycleStarter.execute (LifecycleStarter.java:128)
    at org.apache.maven.DefaultMaven.doExecute (DefaultMaven.java:305)
    at org.apache.maven.DefaultMaven.doExecute (DefaultMaven.java:192)
    at org.apache.maven.DefaultMaven.execute (DefaultMaven.java:105)
    at org.apache.maven.cli.MavenCli.execute (MavenCli.java:957)
    at org.apache.maven.cli.MavenCli.doMain (MavenCli.java:289)
    at org.apache.maven.cli.MavenCli.main (MavenCli.java:193)
    at jdk.internal.reflect.NativeMethodAccessorImpl.invoke0 (Native Method)
    at jdk.internal.reflect.NativeMethodAccessorImpl.invoke (NativeMethodAccessorImpl.java:62)
    at jdk.internal.reflect.DelegatingMethodAccessorImpl.invoke (DelegatingMethodAccessorImpl.java:43)
    at java.lang.reflect.Method.invoke (Method.java:566)
    at org.codehaus.plexus.classworlds.launcher.Launcher.launchEnhanced (Launcher.java:282)
    at org.codehaus.plexus.classworlds.launcher.Launcher.launch (Launcher.java:225)
    at org.codehaus.plexus.classworlds.launcher.Launcher.mainWithExitCode (Launcher.java:406)
    at org.codehaus.plexus.classworlds.launcher.Launcher.main (Launcher.java:347)
    at org.codehaus.classworlds.Launcher.main (Launcher.java:47)

[INFO] ------------------------------------------------------------------------
[INFO] BUILD FAILURE
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  0.699 s
[INFO] Finished at: 2020-05-31T19:24:17+03:00
[INFO] ------------------------------------------------------------------------
```
In the other hand, If Redis-Server is running on localhost, you will be able to see success message on logs

```
[INFO] Scanning for projects...
[INFO] 
[INFO] --< com.arbade.maven.plugins.redis.example:redis-maven-plugin-example >--
[INFO] Building redis-maven-plugin-example 1.0-SNAPSHOT
[INFO] --------------------------------[ jar ]---------------------------------
[INFO] 
[INFO] --- redis-maven-plugin:1.0.1:shutdown (default-cli) @ redis-maven-plugin-example ---
[INFO] Redis-Server has been shutdown on Runtime...!
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  0.769 s
[INFO] Finished at: 2020-05-31T19:26:58+03:00
[INFO] ------------------------------------------------------------------------

```

