package service;

import java.math.BigDecimal;

/**
 * Service layer for utility/parsing operations:
 * Provides safe parsing helpers that can be used across servlets.
 * These are instance methods (unlike static ServletUtils) to allow
 * easy mocking/testing in service-layer unit tests.
 */
public class UtilityService {

    public UtilityService() {
    }

    /**
     * Safely parses a String to int.
     * Returns defaultValue if the value is null, empty, or not a valid integer.
     */
    public int parseIntOrDefault(String value, int defaultValue) {
        if (value == null || value.trim().isEmpty()) {
            return defaultValue;
        }
        try {
            return Integer.parseInt(value.trim());
        } catch (NumberFormatException ex) {
            return defaultValue;
        }
    }

    /**
     * Safely parses a String to BigDecimal.
     * Returns defaultValue if the value is null, empty, or not a valid decimal.
     */
    public BigDecimal parseDecimalOrDefault(String value, BigDecimal defaultValue) {
        if (value == null || value.trim().isEmpty()) {
            return defaultValue;
        }
        try {
            return new BigDecimal(value.trim());
        } catch (NumberFormatException ex) {
            return defaultValue;
        }
    }

    /**
     * Trims the value and returns null if the result is empty.
     * Useful for normalizing optional form fields.
     */
    public String trimOrNull(String value) {
        if (value == null) {
            return null;
        }
        String t = value.trim();
        return t.isEmpty() ? null : t;
    }
}
