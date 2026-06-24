package am.agrotrade.common.dto.news.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateNewsRequest(

        @NotBlank(message = "Title is required")
        @Size(min = 3, max = 255, message = "Title must be between 3 and 255 characters")
        String title,

        @NotBlank(message = "Context is required")
        @Size(min = 10, max = 5000, message = "Context must be between 10 and 5000 characters")
        String context
) {
}
