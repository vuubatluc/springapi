package com.vu.springapi.mapper;

import com.vu.springapi.dto.request.OrderCreateRequest;
import com.vu.springapi.dto.request.OrderUpdateRequest;
import com.vu.springapi.dto.response.OrderItemResponse;
import com.vu.springapi.dto.response.OrderResponse;
import com.vu.springapi.model.Address;
import com.vu.springapi.model.Order;
import com.vu.springapi.model.OrderItem;
import com.vu.springapi.model.Product;
import com.vu.springapi.model.User;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-11-26T02:40:30+0700",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.9 (Microsoft)"
)
@Component
public class OrderMapperImpl implements OrderMapper {

    @Autowired
    private AddressMapper addressMapper;

    @Override
    public Order toOrder(OrderCreateRequest request) {
        if ( request == null ) {
            return null;
        }

        Order.OrderBuilder order = Order.builder();

        order.shippingFee( request.getShippingFee() );
        order.note( request.getNote() );

        return order.build();
    }

    @Override
    public OrderResponse toOrderResponse(Order order) {
        if ( order == null ) {
            return null;
        }

        OrderResponse orderResponse = new OrderResponse();

        orderResponse.setUserId( orderUserId( order ) );
        orderResponse.setUserName( orderUserUsername( order ) );
        orderResponse.setAddressId( orderAddressId( order ) );
        orderResponse.setAddress( addressMapper.toAddressResponse( order.getAddress() ) );
        orderResponse.setId( order.getId() );
        orderResponse.setOrderNumber( order.getOrderNumber() );
        orderResponse.setStatus( order.getStatus() );
        orderResponse.setSubtotal( order.getSubtotal() );
        orderResponse.setShippingFee( order.getShippingFee() );
        orderResponse.setTotal( order.getTotal() );
        orderResponse.setPlacedAt( order.getPlacedAt() );
        orderResponse.setNote( order.getNote() );
        orderResponse.setItems( orderItemListToOrderItemResponseList( order.getItems() ) );

        return orderResponse;
    }

    @Override
    public OrderItemResponse toOrderItemResponse(OrderItem orderItem) {
        if ( orderItem == null ) {
            return null;
        }

        OrderItemResponse orderItemResponse = new OrderItemResponse();

        orderItemResponse.setProductId( orderItemProductId( orderItem ) );
        orderItemResponse.setId( orderItem.getId() );
        orderItemResponse.setProductName( orderItem.getProductName() );
        orderItemResponse.setUnitPrice( orderItem.getUnitPrice() );
        orderItemResponse.setQuantity( orderItem.getQuantity() );
        orderItemResponse.setTotalPrice( orderItem.getTotalPrice() );

        return orderItemResponse;
    }

    @Override
    public void updateOrder(Order order, OrderUpdateRequest request) {
        if ( request == null ) {
            return;
        }

        if ( request.getStatus() != null ) {
            order.setStatus( request.getStatus() );
        }
        if ( request.getNote() != null ) {
            order.setNote( request.getNote() );
        }
    }

    private Long orderUserId(Order order) {
        User user = order.getUser();
        if ( user == null ) {
            return null;
        }
        return user.getId();
    }

    private String orderUserUsername(Order order) {
        User user = order.getUser();
        if ( user == null ) {
            return null;
        }
        return user.getUsername();
    }

    private Long orderAddressId(Order order) {
        Address address = order.getAddress();
        if ( address == null ) {
            return null;
        }
        return address.getId();
    }

    protected List<OrderItemResponse> orderItemListToOrderItemResponseList(List<OrderItem> list) {
        if ( list == null ) {
            return null;
        }

        List<OrderItemResponse> list1 = new ArrayList<OrderItemResponse>( list.size() );
        for ( OrderItem orderItem : list ) {
            list1.add( toOrderItemResponse( orderItem ) );
        }

        return list1;
    }

    private Long orderItemProductId(OrderItem orderItem) {
        Product product = orderItem.getProduct();
        if ( product == null ) {
            return null;
        }
        return product.getId();
    }
}
