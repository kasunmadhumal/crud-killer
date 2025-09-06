package com.crudkiller.type.comparison;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;

import java.util.*;
import java.util.function.Function;

public class ComparisonProvider<T> {
    private final Optional<T> existing;
    private final T updated;
    @Getter
    private final Map<String, String> updatedFields = new HashMap<>();

    public ComparisonProvider(Optional<T> existing, T updated) {
        this.existing = existing;
        this.updated = updated;
    }

    public static <T> ComparisonProvider<T> by(T existing, T updated) {
        return by(Optional.ofNullable(existing), updated);
    }

    public static <T> ComparisonProvider<T> by(Optional<T> existing, T updated) {
        return new ComparisonProvider<>(existing, updated);
    }

    public ComparisonProvider<T> compare(String fieldName, Function<T, ?> elementFunction) {
        return compare(fieldName, elementFunction, false);
    }

    public ComparisonProvider<T> compareJson(String fieldName, Function<T, ?> elementFunction) {
        return compare(fieldName, elementFunction, true);
    }

    public ComparisonProvider<T> compare(String fieldName, Function<T, ?> elementFunction, boolean isJson) {
        Object oldValue = existing.map(elementFunction).orElse(null);
        Object newValue = elementFunction.apply(updated);
        if (isJson) {
            compareJsonAndAdd(fieldName, oldValue, newValue);
        } else {
            compareAndAdd(fieldName, oldValue, newValue);
        }
        return this;
    }

    private void compareAndAdd(String fieldName, Object oldValue, Object newValue) {
        if ((oldValue == null && newValue != null) || (oldValue != null && !oldValue.equals(newValue))) {
            updatedFields.put(fieldName, newValue != null ? newValue.toString() : "null");
        }
    }

    private void compareJsonAndAdd(String fieldName, Object oldValue, Object newValue) {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode oldNode = mapper.valueToTree(oldValue);
        JsonNode newNode = mapper.valueToTree(newValue);
        compareJsonNodes(oldNode, newNode, fieldName);
    }

    private void compareJsonNodes(JsonNode oldNode, JsonNode newNode, String path) {
        if (oldNode == null && newNode == null) return;

        if (oldNode == null) {
            updatedFields.put(path, newNode.toString());
            return;
        }

        if (newNode == null) {
            updatedFields.put(path, "null");
            return;
        }

        if (!oldNode.equals(newNode)) {
            if (oldNode.isObject() && newNode.isObject()) {
                Set<String> allFields = new HashSet<>();
                oldNode.fieldNames().forEachRemaining(allFields::add);
                newNode.fieldNames().forEachRemaining(allFields::add);

                for (String fieldName : allFields) {
                    JsonNode oldField = oldNode.get(fieldName);
                    JsonNode newField = newNode.get(fieldName);
                    compareJsonNodes(oldField, newField, path + "." + fieldName);
                }
            } else if (oldNode.isArray() && newNode.isArray()) {
                int maxSize = Math.max(oldNode.size(), newNode.size());
                for (int i = 0; i < maxSize; i++) {
                    JsonNode oldElement = i < oldNode.size() ? oldNode.get(i) : null;
                    JsonNode newElement = i < newNode.size() ? newNode.get(i) : null;
                    compareJsonNodes(oldElement, newElement, path + "[" + i + "]");
                }
            } else {
                updatedFields.put(path, newNode.toString());
            }
        }
    }
}