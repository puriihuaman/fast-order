package fast_order.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Data Transfer Object (DTO) representing the response for successful authentication operations.
 * *
 * Contains the JWT access token along with basic user information for immediate client use.
 *
 * @param accessToken JSON Web Token (JWT) for API access.
 * @param name        Full name of the authenticated user.
 * @param role        Primary role of the authenticated user.
 */
@Schema(
    name = "TokenResponse",
    description = """
                  DTO that represents the response after successful authentication.
                  It contains the JWT token and data of the authenticated user.
                  """
)
public record TokenResponseTO(
    @Schema(
        description = "JWT token generated to access protected resources.",
        example = "eyJhbGciOiJIUzI1NiJ9.xxx.xxx.xxx.xxx.xxx"
    )
    @JsonProperty("access_token") String accessToken,
    @Schema(description = "Full name of the user who authenticated.", example = "Jorge")
    @JsonProperty("name") String name,
    @Schema(description = "Authenticated user role.", example = "ADMIN")
    @JsonProperty("role") String role
) {}
