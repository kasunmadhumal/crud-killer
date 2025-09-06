package com.crudkiller.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "crud-killer")
public class CrudKillerProperties {

    /**
     * Enable CRUD Killer framework
     */
    private boolean enabled = true;

    /**
     * Validation configuration
     */
    private Validation validation = new Validation();

    /**
     * Logging configuration
     */
    private Logging logging = new Logging();

    @Data
    public static class Validation {
        /**
         * Enable automatic validation for entities
         */
        private boolean enabled = true;
    }

    @Data
    public static class Logging {
        /**
         * Enable debug logging for CRUD operations
         */
        private boolean enabled = false;
    }
}