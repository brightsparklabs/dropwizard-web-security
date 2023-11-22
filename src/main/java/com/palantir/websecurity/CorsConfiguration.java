/*
 * (c) Copyright 2016 Palantir Technologies Inc. All rights reserved.
 */

package com.brightsparklabs.websecurity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.common.base.Optional;
import org.eclipse.jetty.servlets.CrossOriginFilter;
import org.immutables.value.Value;

/**
 * Configuration class used to set the properties for a {@link CrossOriginFilter}. If a value is not
 * set it will not be passed in as an initial parameter.
 */
@Value.Immutable
@ImmutableStyles
@JsonDeserialize(as = ImmutableCorsConfiguration.class)
@SuppressWarnings("checkstyle:designforextension")
public abstract class CorsConfiguration {

    private static final String DISABLED_ORIGINS = "";

    /** If set, will be used to set the initial property {@code allowCredentials}. */
    public abstract Optional<Boolean> allowCredentials();

    /** If set, will be used to set the initial property {@code allowedHeaders}. */
    public abstract Optional<String> allowedHeaders();

    /** If set, will be used to set the initial property {@code allowedMethods}. */
    public abstract Optional<String> allowedMethods();

    /** If set, will be used to set the initial property {@code allowedOrigins}. */
    public abstract Optional<String> allowedOrigins();

    /** If set, will be used to set the initial property {@code chainPreflight}. */
    public abstract Optional<Boolean> chainPreflight();

    /**
     * Determines if {@link CrossOriginFilter} is applied. Returns true if there is an {@link
     * #allowedOrigins()} value set to a non-empty string, false otherwise.
     */
    @Value.Derived
    public boolean enabled() {
        return !allowedOrigins().or(DISABLED_ORIGINS).isEmpty();
    }

    /** If set, will be used to set the initial property {@code exposedHeaders}. */
    public abstract Optional<String> exposedHeaders();

    /** If set, will be used to set the initial property {@code preflightMaxAge}. */
    public abstract Optional<Long> preflightMaxAge();

    /** Provides a configuration with default values. */
    public static final CorsConfiguration DEFAULT = CorsConfiguration.builder().build();

    /** Provides a configuration that is disabled. */
    public static final CorsConfiguration DISABLED =
            CorsConfiguration.builder().allowedOrigins(DISABLED_ORIGINS).build();

    // hides implementation details
    public static Builder builder() {
        return ImmutableCorsConfiguration.builder();
    }

    // hides implementation details
    public interface Builder {

        Builder allowCredentials(boolean allowCredentials);

        Builder allowedHeaders(String allowedHeaders);

        Builder allowedMethods(String allowedMethods);

        Builder allowedOrigins(String allowedOrigins);

        Builder chainPreflight(boolean chainPreflight);

        Builder exposedHeaders(String exposedHeaders);

        Builder preflightMaxAge(long preflightMaxAge);

        CorsConfiguration build();
    }
}
