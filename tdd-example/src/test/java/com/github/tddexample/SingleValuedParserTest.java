package com.github.tddexample;

import lombok.val;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.List;

class SingleValuedParserTest {

    @Test
    public void should_get_int_option_if_flag_present() {
        val option = Args.parse(IntOption.class, "-p", "8080");
        Assertions.assertEquals(8080, option.port());
    }

    @Test
    public void should_get_zero_option_if_input_empty_value() {
        val option = Args.parse(IntOption.class, "-p");
        Assertions.assertEquals(0, option.port());
    }

    record IntOption(@Option("p") int port) {
    }

    //  输入：-p 8080 8081，异常提示
    @Test
    public void should_not_allow_extra_arguments_for_bool_option() {
        Assertions.assertThrows(TooManyArgumentsException.class, () -> new SingleValuedParser(0, Integer::valueOf).parse(Arrays.asList("8080", "8801"), option()));
    }

    @Test
    public void should_set_a_default_value_for_bool_option() {
        Assertions.assertEquals(0, new SingleValuedParser(0, Integer::valueOf)
                .parse(List.of(), option()));
    }

    static Option option() {
        return new Option() {
            @Override
            public Class<? extends Annotation> annotationType() {
                return Option.class;
            }

            @Override
            public String value() {
                return "p";
            }
        };
    }
}