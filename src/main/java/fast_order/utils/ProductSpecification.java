package fast_order.utils;

import fast_order.commons.enums.APIError;
import fast_order.entity.ProductEntity;
import fast_order.exception.APIRequestException;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ProductSpecification {
    private static final String NAME = "name";
    private static final String STOCK = "stock";
    private static final String PRICE = "price";
    private static final String DESCRIPTION = "description";
    
    private static final Set<String> ALLOWED_KEY = Set.of(NAME, STOCK, PRICE, DESCRIPTION);
    
    public static Specification<ProductEntity> filterProducts(Map<String, String> keywords) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            keywords.remove("page");
            keywords.remove("size");
            
            if (!ALLOWED_KEY.containsAll(keywords.keySet())) {
                APIError.BAD_REQUEST.setTitle("Invalid parameter");
                APIError.BAD_REQUEST.setMessage("Invalid filter parameter. Allowed parameters: %s.".formatted(
                    ALLOWED_KEY));
                throw new APIRequestException(APIError.BAD_REQUEST);
            }
            
            try {
                for (Map.Entry<String, String> entry : keywords.entrySet()) {
                    String key = entry.getKey();
                    String value = entry.getValue();
                    
                    if (value == null || value.trim().isEmpty()) continue;
                    
                    switch (key) {
                        case NAME:
                            String searchName = "%" + value.trim().toLowerCase() + "%";
                            predicates.add(cb.like(cb.lower(root.get(NAME)), searchName));
                            break;
                        case STOCK:
                            Integer stock = Integer.parseInt(value);
                            predicates.add(cb.lessThanOrEqualTo(root.get(STOCK), stock));
                            break;
                        case PRICE:
                            Double price = Double.parseDouble(value);
                            predicates.add(cb.lessThanOrEqualTo(root.get(PRICE), price));
                            break;
                        case DESCRIPTION:
                            String searchDescription = "%" + value.trim().toLowerCase() + "%";
                            predicates.add(cb.like(
                                cb.lower(root.get(DESCRIPTION)),
                                searchDescription
                            ));
                    }
                }
            } catch (NumberFormatException ex) {
                APIError.BAD_REQUEST.setTitle("Invalid value");
                APIError.BAD_REQUEST.setMessage(
                    "Invalid value for parameter: %s. Expected a number.".formatted(keywords.keySet()));
                throw new APIRequestException(APIError.BAD_REQUEST);
            }
            
            return cb.and(predicates.toArray(new Predicate[0]));
        };
        
    }
}
