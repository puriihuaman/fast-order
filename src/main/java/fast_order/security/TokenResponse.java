package fast_order.security;

import com.fasterxml.jackson.annotation.JsonProperty;

public record TokenResponse(
    @JsonProperty("access_token") String accessToken,
    @JsonProperty("name") String name,
    @JsonProperty("role") String role
) {}
