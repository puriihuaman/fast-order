package fast_order.utils;

public class SwaggerResponseExample {
    public static final String EXAMPLE_GET_RESOURCE_WITH_TOKEN = """
                                                                 {
                                                                   "access_token": "eyJhbGciOiJIUzI1NiJ9.xxx.xxx.xxx.xxx.xxx",
                                                                   "email": "admin@admin.com",
                                                                   "role": "admin"
                                                                 }
    
                                                                 """;
    public static final String EXAMPLE_GET_ALL_RESOURCE = """
                                                      {
                                                        "data": [{}, {}],
                                                        "hasError": false,
                                                        "message": "Resources successfully recovered.",
                                                        "statusCode": 200,
                                                        "timestamp": "2025-04-13T15:42:00"
                                                      }
                                                      """;
    
    public static final String EXAMPLE_GET_RESOURCE = """
                                                      {
                                                        "data": {},
                                                        "hasError": false,
                                                        "message": "Resource successfully recovered.",
                                                        "statusCode": 200,
                                                        "timestamp": "2025-04-13T15:42:00"
                                                      }
                                                      """;

    public static final String EXAMPLE_DELETE_RESOURCE = """
                                                         {
                                                           "data": null,
                                                           "hasError": false,
                                                           "message": "Resource successfully deleted.",
                                                           "statusCode": 204,
                                                           "timestamp": "2025-04-13T15:42:00"
                                                         }
                                                         """;
    
    public static final String CREATED_EXAMPLE = """
                                                 {
                                                   "data": {},
                                                   "hasError": false,
                                                   "message": "Descriptive message.",
                                                   "statusCode": 201,
                                                   "timestamp": "2025-04-13T15:42:00"
                                                 }
                                                 """;
}
