package am.agrotrade.common.dto.product.request;

import am.agrotrade.common.enums.CategoryProduct;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record CreateProductRequest(

        @NotBlank(message = "Product name is required")
        @Size(min = 3, max = 150, message = "Product name must be between 3 and 150 characters")
        String name,

        @NotBlank(message = "Product description is required")
        @Size(max = 2000, message = "Description cannot exceed 2000 characters")
        String description,

        @NotNull(message = "Price is required")
        @DecimalMin(value = "0.01", message = "Price must be greater than zero")
        @Digits(integer = 10, fraction = 2, message = "Price format invalid (max 10 digits and 2 decimals)")
        BigDecimal price,

        @NotNull(message = "Category is required")
        CategoryProduct category

) {
}
