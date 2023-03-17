package com.github.tddexample;

import net.bytebuddy.asm.Advice;
import org.junit.jupiter.api.Test;

public class ArgsTest {
    // -l -p 8080 -d /usr/logs -g this is a list -d 1 2 -3 5
    @Test
    public void should() {
        Options options = Args.prase(Options.class,"-l", "-p", "8080", "-d", "/usr/logs");
        options.logging();
        options.port();
        options.directory();

    }
    static record Options(@Option("l") boolean logging, @Option("p") int port, @Option("d") String directory) {}
}
