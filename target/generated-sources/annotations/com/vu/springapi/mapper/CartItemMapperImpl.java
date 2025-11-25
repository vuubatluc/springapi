package com.vu.springapi.mapper;

import com.vu.springapi.dto.response.CartItemResponse;
import com.vu.springapi.model.CartItem;
import com.vu.springapi.model.Product;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-11-26T02:50:57+0700",
    comments = "version: 1.6.3, compiler: javac, environment: Java 17.0.12 (Oracle Corporation)"
)
@Component
public class CartItemMapperImpl implements CartItemMapper {

    @Override
    public CartItemResponse toCartItemResponse(CartItem cartItem) {
        if ( cartItem == null ) {
            return null;
        }

        CartItemResponse.CartItemResponseBuilder cartItemResponse = CartItemResponse.builder();

        cartItemResponse.id( cartItem.getId() );
        cartItemResponse.productId( cartItemProductId( cartItem ) );
        cartItemResponse.productName( cartItemProductName( cartItem ) );
        cartItemResponse.productPrice( cartItemProductPrice( cartItem ) );
        cartItemResponse.productImage( cartItemProductImageUrl( cartItem ) );
        cartItemResponse.quantity( cartItem.getQuantity() );

        cartItemResponse.totalPrice( cartItem.getProduct().getPrice().multiply(java.math.BigDecimal.valueOf(cartItem.getQuantity())) );

        return cartItemResponse.build();
    }

    @Override
    public List<CartItemResponse> toCartItemResponseList(List<CartItem> cartItems) {
        if ( cartItems == null ) {
            return null;
        }

        List<CartItemResponse> list = new ArrayList<CartItemResponse>( cartItems.size() );
        for ( CartItem cartItem : cartItems ) {
            list.add( toCartItemResponse( cartItem ) );
        }

        return list;
    }

    private Long cartItemProductId(CartItem cartItem) {
        Product product = cartItem.getProduct();
        if ( product == null ) {
            return null;
        }
        return product.getId();
    }

    private String cartItemProductName(CartItem cartItem) {
        Product product = cartItem.getProduct();
        if ( product == null ) {
            return null;
        }
        return product.getName();
    }

    private BigDecimal cartItemProductPrice(CartItem cartItem) {
        Product product = cartItem.getProduct();
        if ( product == null ) {
            return null;
        }
        return product.getPrice();
    }

    private String cartItemProductImageUrl(CartItem cartItem) {
        Product product = cartItem.getProduct();
        if ( product == null ) {
            return null;
        }
        return product.getImageUrl();
    }
}
