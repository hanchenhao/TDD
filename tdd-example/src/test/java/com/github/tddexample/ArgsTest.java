package com.github.tddexample;

import lombok.val;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class ArgsTest {
    // -l -p 8080 -d /usr/logs
    // happy path
    // 输入：-l， 输出：options.logging() = true
    @Test
    public void should_get_boolean_option_to_true_if_flag_present(){
        val option = Args.parse(BooleanOption.class, "-l");
        Assertions.assertTrue(option.logging());
    }
    @Test
    public void should_get_boolean_option_to_false_if_not_flag_present(){
        val option = Args.parse(BooleanOption.class);
        Assertions.assertFalse(option.logging());
    }
    static record BooleanOption(@Option("l")boolean logging){}
    @Test
    public void should_get_int_option_if_flag_present(){
        val option = Args.parse(IntOption.class, "-p", "8080");
        Assertions.assertEquals(8080,option.port());
    }
    @Test
    public void should_get_zero_option_if_input_empty_value(){
        val option = Args.parse(IntOption.class, "-p");
        Assertions.assertEquals(0,option.port());
    }
    static record IntOption(@Option("p")int port){}
    // TODO 输入：-d /usr/logs，输出： options.directory()=/usr/logs
    @Test
    public void should_get_string_option_if_flag_present(){
        val option = Args.parse(StringOption.class, "-d", "/usr/logs");
        Assertions.assertEquals("/usr/logs",option.directory());
    }

    @Test
    public void should_get_null_if_input_empty_value(){
        val option = Args.parse(StringOption.class, "-d");
        Assertions.assertEquals("",option.directory());
    }
    static record StringOption(@Option("d")String directory){}
    // TODO 输入：-l -p 8080 -d /usr/logs，输出：options（logging=true，port=8080，directory="/usr/logs"）
    @Test
    public void input_multiple_parameter_to_get_ture_option() {
        // 输入-l -p 8080 -d /usr/logs，将内容进行分割，并提取参数
        Options options = Args.parse(Options.class, "-l", "-p", "8080", "-d", "/usr/logs");
        Assertions.assertTrue(options.logging());
        Assertions.assertEquals(8080, options.port());
        Assertions.assertEquals("/usr/logs", options.directory());

    }

    @Test
    public void input_multiple_parameter_to_get_ture_option_by_out_of_order() {
        // 输入-l -p 8080 -d /usr/logs，将内容进行分割，并提取参数
        Options options = Args.parse(Options.class,  "-d", "/usr/logs","-l","-p", "8080");
        Assertions.assertTrue(options.logging());
        Assertions.assertEquals(8080, options.port());
        Assertions.assertEquals("/usr/logs", options.directory());

    }

    static record Options(@Option("l") boolean logging, @Option("p") int port, @Option("d") String directory) {
    }

    //sad path
    // TODO 输入：-l t f，输出：异常提示
    // TODO 输入：-p 8080 8081，异常提示
    // TODO 输入：-d /usr/logs /usr/log2，输出： 异常提示

    // -g this is a list -d 1 2 -3 5

    // 根据标志位，把内容进行分割


    @Test
    @Disabled
    public void example_2() {
        // 输入g this is a list -d 1 2 -3 5，将内容进行分割，并提取参数列表
        val listOptions = Args.parse(ListOptions.class, "-g", "this", "is", "a", "list", "-d", "1", "2", "3");
        Assertions.assertEquals(new String[]{"this", "is", "a", "list"}, listOptions.group());
        Assertions.assertEquals(new int[]{1, 2, 3}, listOptions.decimals());

    }


    static record ListOptions(@Option("g") String[] group, @Option("d") int[] decimals) {
    }
}
