package am.agrotrade.common.dto.product.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record UpdateProductRequest(

        @NotBlank(message = "Product name cannot be empty")
        @Size(min = 3, max = 150, message = "Product name must be between 3 and 150 characters")
        String name,

        @NotBlank(message = "Description cannot be empty")
        @Size(max = 2000, message = "Description cannot exceed 2000 characters")
        String description,

        @NotNull(message = "Price is required")
        @DecimalMin(value = "0.01", message = "Price must be at least 0.01")
        @Digits(integer = 10, fraction = 2, message = "Invalid price format")
        BigDecimal price

) {
}
