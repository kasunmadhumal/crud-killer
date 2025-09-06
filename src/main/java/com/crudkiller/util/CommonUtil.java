package com.crudkiller.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.util.StringUtils;

import java.beans.PropertyDescriptor;
import java.util.*;
import java.util.function.*;
import java.util.stream.Stream;

@Slf4j
public final class CommonUtil {

    private CommonUtil() {
        // Utility class
    }

    // String utilities with Java 21 improvements
    public static boolean isNotEmpty(String value) {
        return !isEmpty(value);
    }

    public static boolean isEmpty(String string) {
        return string == null || string.isEmpty();
    }

    public static String nvl(String value) {
        return nvl(value, "");
    }

    public static String nvl(String value, String defaultValue) {
        return nvl(value, true, defaultValue);
    }

    public static String nvl(String value, boolean checkEmpty, String defaultValue) {
        return nvl(value, checkEmpty, UnaryOperator.identity(), defaultValue);
    }

    public static String nvl(String value, UnaryOperator<String> formatter, String defaultValue) {
        return nvl(value, true, formatter, defaultValue);
    }

    public static String nvl(String value, boolean checkEmpty, UnaryOperator<String> formatter, String defaultValue) {
        boolean isEmpty = value == null || (checkEmpty && StringUtils.isEmpty(value));
        return !isEmpty ? formatter.apply(value) : defaultValue;
    }

    public static boolean isTrimmedEmpty(String value) {
        return value == null || value.isBlank(); // Java 11+ method
    }

    // Collection utilities with modern Stream API
    public static <T> boolean isNotEmpty(Collection<T> collection) {
        return !isEmpty(collection);
    }

    public static <T> boolean isEmpty(Collection<T> collection) {
        return collection == null || collection.isEmpty();
    }

    public static <T> Collection<T> nvlList(Collection<T> list) {
        return nvlList(list, List.of()); // Java 9+ immutable list
    }

    public static <C extends Collection<T>, T> C nvlList(C list, C defaultList) {
        return isNotEmpty(list) ? list : defaultList;
    }

    public static <T> Stream<T> toStream(Collection<T> collection) {
        return nvlList(collection).stream();
    }

    // Object utilities
    public static <T> boolean isEmpty(T value) {
        return value == null;
    }

    public static <T> T nvl(T value, T defaultValue) {
        return value != null ? value : defaultValue;
    }

    public static <T, R> R nvl(T source, Function<T, R> mapper, R defaultValue) {
        return Optional.ofNullable(source).map(mapper).orElse(defaultValue);
    }

    public static <T> boolean nvlEqual(T value1, T value2) {
        return Objects.equals(value1, value2);
    }

    public static <T> void copyNonNullProperties(T source, T target, Set<String> fieldsToUpdate) {
        BeanWrapper srcWrapper = new BeanWrapperImpl(source);
        BeanWrapper trgWrapper = new BeanWrapperImpl(target);

        for (PropertyDescriptor propertyDescriptor : srcWrapper.getPropertyDescriptors()) {
            String propertyName = propertyDescriptor.getName();
            if (fieldsToUpdate.contains(propertyName)) {
                Object value = srcWrapper.getPropertyValue(propertyName);
                if (value != null) {
                    trgWrapper.setPropertyValue(propertyName, value);
                }
            }
        }
    }

    // Java 21 Pattern Matching and Switch Expressions
    public static <T> String getTypeName(T object) {
        return switch (object) {
            case null -> "null";
            case String s -> "String: " + s;
            case Integer i -> "Integer: " + i;
            case List<?> list -> "List with " + list.size() + " elements";
            case Map<?, ?> map -> "Map with " + map.size() + " entries";
            default -> object.getClass().getSimpleName();
        };
    }

    // Record-based utility methods for Java 14+
    public record ValidationResult(boolean isValid, String errorMessage) {
        public static ValidationResult valid() {
            return new ValidationResult(true, null);
        }

        public static ValidationResult invalid(String message) {
            return new ValidationResult(false, message);
        }
    }
}