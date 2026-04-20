package am.agrotrade.web.endpoint;

import am.agrotrade.common.dto.order.request.CreateOrderRequest;
import am.agrotrade.common.dto.order.request.UpdateOrderStatusRequest;
import am.agrotrade.common.dto.order.response.OrderResponse;
import am.agrotrade.web.infrastructure.annotation.CurrentUserId;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Order V1", description = "Order management endpoints.")
@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/agro-trade-service/api/v1/order")
public interface OrderV1API {

    @Operation(summary = "List orders", description = "Returns a paginated list of all orders.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Orders retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content)
    })
    @GetMapping
    OrderResponse getAll(@ParameterObject Pageable pageable);

    @Operation(summary = "List my orders", description = "Returns orders related to the authenticated manager.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User orders retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content)
    })
    @GetMapping("/my")
    OrderResponse getMyOrders(
            @Parameter(hidden = true) @CurrentUserId long managerId,
            @ParameterObject Pageable pageable);

    @Operation(summary = "Get order by id", description = "Returns details for a single order.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Order found"),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "404", description = "Order not found", content = @Content)
    })
    @GetMapping("/{id}")
    OrderResponse getById(@Parameter(description = "Order identifier", example = "1") @PathVariable long id);

    @Operation(summary = "Create order", description = "Creates a new order on behalf of the authenticated manager.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Order created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request body", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content)
    })
    @PostMapping
    OrderResponse createOrder(
            @Parameter(hidden = true) @CurrentUserId long managerId,
            @Valid @RequestBody CreateOrderRequest request
    );

    @Operation(summary = "Update order status", description = "Updates the status of an existing order.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Order updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request body", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "404", description = "Order not found", content = @Content)
    })
    @PutMapping("/{id}")
    OrderResponse updateStatus(
            @Parameter(description = "Order identifier", example = "1") @PathVariable long id,
            @Valid @RequestBody UpdateOrderStatusRequest request);

    @Operation(summary = "Delete order", description = "Deletes an order available to the authenticated manager.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Order deleted successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "404", description = "Order not found", content = @Content)
    })
    @DeleteMapping("/{id}")
    void delete(
            @Parameter(hidden = true) @CurrentUserId long managerId,
            @Parameter(description = "Order identifier", example = "1") @PathVariable long id
    );
}
