package am.agrotrade.core.service;

import am.agrotrade.common.dto.document.OrderDocumentGenerateRequest;
import am.agrotrade.common.dto.order.OrderDetailsDto;
import am.agrotrade.common.dto.order.request.CreateOrderRequest;
import am.agrotrade.common.dto.order.request.UpdateOrderStatusRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Manages order creation, status changes, and order queries.
 */
public interface OrderService {

    /**
     * Creates an order for the specified buyer.
     *
     * @param buyerId buyer identifier
     * @param request order payload
     * @return created order data
     */
    OrderDetailsDto save(long buyerId, CreateOrderRequest request);

    /**
     * Updates the status of an order.
     *
     * @param managerId manager identifier
     * @param request status update payload
     * @return updated order data
     */
    OrderDetailsDto updateStatus(long managerId, UpdateOrderStatusRequest request);

    /**
     * Links an existing chat to the specified order.
     *
     * @param orderId order identifier
     * @param chatId chat identifier
     */
    void attachChatToOrder(long orderId, long chatId);

    /**
     * Deletes an order available to the specified manager.
     *
     * @param managerId manager identifier
     * @param orderId order identifier
     */
    void delete(long managerId, long orderId);

    /**
     * Returns all orders.
     *
     * @param pageable paging parameters
     * @return order list
     */
    List<OrderDetailsDto> findAll(Pageable pageable);

    /**
     * Returns an order by identifier.
     *
     * @param orderId order identifier
     * @return order data
     */
    OrderDetailsDto findById(long orderId);

    /**
     * Returns orders assigned to the specified manager.
     *
     * @param managerId manager identifier
     * @param pageable paging parameters
     * @return manager order list
     */
    List<OrderDetailsDto> findByManagerId(long managerId, Pageable pageable);

    /**
     * Collects the data required to generate the order document for the order
     * linked to the given chat.
     *
     * @param chatId chat identifier
     * @return order document generation request
     */
    OrderDocumentGenerateRequest buildOrderDocumentRequest(long chatId);
}
