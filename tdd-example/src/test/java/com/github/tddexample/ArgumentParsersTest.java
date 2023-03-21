package com.github.tddexample;

import lombok.val;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.List;

class ArgumentParsersTest {

    @Nested
    class BooleanOptionParserTest {
        @Test
        public void should_get_boolean_option_to_true_if_flag_present() {
            val option = Args.parse(BooleanOption.class, "-l");
            Assertions.assertTrue(option.logging());
        }

        record BooleanOption(@Option("l") boolean logging) {
        }

        // 输入：-l t f，输出：异常提示
        @Test
        public void should_not_allow_extra_arguments_for_bool_option() {
            Assertions.assertThrows(TooManyArgumentsException.class,
                    () -> ArgumentParsers.bool()
                            .parse(Arrays.asList("-l", "t", "f"), option()));
        }

        // 未输入：-l 默认值false
        @Test
        public void should_set_a_default_value_for_bool_option() {
            Assertions.assertFalse((Boolean) ArgumentParsers.bool()
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
                    return "l";
                }
            };
        }
    }

    @Nested
    class StringOptionParserTest {
        @Test
        public void should_get_string_option_if_flag_present() {
            val option = Args.parse(StringOption.class, "-d", "/usr/logs");
            Assertions.assertEquals("/usr/logs", option.directory());
        }

        @Test
        public void should_get_null_if_input_empty_value() {
            val option = Args.parse(StringOption.class, "-d");
            Assertions.assertEquals("", option.directory());
        }

        record StringOption(@Option("d") String directory) {
        }

        // 输入：-d /usr/logs /usr/log2，输出： 异常提示
        @Test
        public void should_not_allow_extra_arguments_for_string_option() {
            Assertions.assertThrows(TooManyArgumentsException.class, () ->
                    ArgumentParsers.unary("", String::valueOf)
                            .parse(Arrays.asList("/user/logs", "/user/log2"), option()));
        }

        @Test
        public void should_set_a_default_value_for_string_option() {
            Assertions.assertEquals("", ArgumentParsers.unary("", String::valueOf)
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
                    return "d";
                }
            };
        }

    }

    @Nested
    class IntOptionParserTest {

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
            Assertions.assertThrows(TooManyArgumentsException.class,
                    () -> ArgumentParsers.unary(0, Integer::valueOf)
                            .parse(Arrays.asList("8080", "8801"), option()));
        }

        @Test
        public void should_set_a_default_value_for_bool_option() {
            Assertions.assertEquals(0, ArgumentParsers.unary(0, Integer::valueOf)
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

}