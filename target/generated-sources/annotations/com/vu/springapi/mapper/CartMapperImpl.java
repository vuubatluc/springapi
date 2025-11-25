package com.vu.springapi.mapper;

import com.vu.springapi.dto.response.CartItemResponse;
import com.vu.springapi.dto.response.CartResponse;
import com.vu.springapi.model.Cart;
import com.vu.springapi.model.CartItem;
import com.vu.springapi.model.Product;
import com.vu.springapi.model.User;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-11-26T02:40:29+0700",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.9 (Microsoft)"
)
@Component
public class CartMapperImpl implements CartMapper {

    @Override
    public CartResponse toCartResponse(Cart cart) {
        if ( cart == null ) {
            return null;
        }

        CartResponse.CartResponseBuilder cartResponse = CartResponse.builder();

        cartResponse.userId( cartUserId( cart ) );
        cartResponse.id( cart.getId() );
        cartResponse.createdAt( cart.getCreatedAt() );
        cartResponse.updatedAt( cart.getUpdatedAt() );

        return cartResponse.build();
    }

    @Override
    public CartItemResponse toCartItemResponse(CartItem cartItem) {
        if ( cartItem == null ) {
            return null;
        }

        CartItemResponse.CartItemResponseBuilder cartItemResponse = CartItemResponse.builder();

        cartItemResponse.productId( cartItemProductId( cartItem ) );
        cartItemResponse.productName( cartItemProductName( cartItem ) );
        cartItemResponse.productImage( cartItemProductImageUrl( cartItem ) );
        cartItemResponse.productPrice( cartItemProductPrice( cartItem ) );
        cartItemResponse.id( cartItem.getId() );
        cartItemResponse.quantity( cartItem.getQuantity() );

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

    private Long cartUserId(Cart cart) {
        User user = cart.getUser();
        if ( user == null ) {
            return null;
        }
        return user.getId();
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

    private String cartItemProductImageUrl(CartItem cartItem) {
        Product product = cartItem.getProduct();
        if ( product == null ) {
            return null;
        }
        return product.getImageUrl();
    }

    private BigDecimal cartItemProductPrice(CartItem cartItem) {
        Product product = cartItem.getProduct();
        if ( product == null ) {
            return null;
        }
        return product.getPrice();
    }
}
