package fast_order.annotation;

import fast_order.dto.ErrorResponseTO;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation that defines error responses in the API. Handled by Swagger.
 *
 * @Target indicates that the annotation can be applied to a method or type.
 * @Retention indicates that the annotation will be at runtime.
 * @interface defines a new annotation, the `SwaggerApiResponses` annotation is created.
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@ApiResponses(
    {
        @ApiResponse(
            responseCode = "400",
            description = "Incorrect request. Please check the data submitted.",
            content = @Content(
                mediaType = "application/json", schema = @Schema(
                    implementation = ErrorResponseTO.class
                )
            )
        ),
        @ApiResponse(
            responseCode = "401",
            description = "Not authenticated. The credentials provided are incorrect or have expired",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ErrorResponseTO.class),
                examples = @ExampleObject(
                    value = """
                            {
                              "hasError": "401",
                              "title": "Not authenticated",
                              "message": "The credentials provided are incorrect or have expired.",
                              "statusCode": "401",
                              "reasons": null,
                              "timestamp": "2025-04-13T15:42:00"
                            }
                            """
                )
            )
        ),
        @ApiResponse(
            responseCode = "403",
            description = "Insufficient permissions. You do not have the necessary permissions.",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ErrorResponseTO.class),
                examples = @ExampleObject(
                    value = """
                            {
                              "hasError": true,
                              "title": "Unauthorized access",
                              "message": "You do not have the necessary permissions for this operation.",
                              "statusCode": "403",
                              "reasons": null,
                              "timestamp": "2025-04-13T15:42:00"
                            }
                            """
                )
            )
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Resource not found",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ErrorResponseTO.class),
                examples = @ExampleObject(
                    value = """
                            {
                              "hasError": true,
                              "title": "Resource not found",
                              "message": "The requested resource does not exist or has been deleted.",
                              "statusCode": "404",
                              "reasons": null,
                              "timestamp": "2025-04-13T15:42:00"
                            }
                            """
                )
            )
        ),
        @ApiResponse(
            responseCode = "500",
            description = "Internal Server Error",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ErrorResponseTO.class),
                examples = @ExampleObject(
                    value = """
                            {
                              "hasError": true,
                              "title": "Internal Server Error",
                              "message": "An unexpected internal error occurred on the server. Please try again later.",
                              "statusCode": 500,
                              "reasons": null,
                              "timestamp": "2025-04-13T15:42:00"
                            }
                            """
                )
            )
        ),
    }
)
public @interface SwaggerApiResponses {}
