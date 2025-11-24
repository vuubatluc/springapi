package com.vu.springapi.service;

import com.vu.springapi.dto.request.AddToCartRequest;
import com.vu.springapi.dto.request.UpdateCartItemRequest;
import com.vu.springapi.dto.response.CartResponse;
import com.vu.springapi.mapper.CartMapper;
import com.vu.springapi.model.*;
import com.vu.springapi.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final CartMapper cartMapper;

    /**
     * Lấy giỏ hàng theo user ID
     */
    @Transactional(readOnly = true)
    public Cart getCartByUserId(Long userId) {
        return cartRepository.findByUserId(userId).orElse(null);
    }

    /**
     * Thêm sản phẩm vào giỏ hàng
     */
    public Cart addItemToCart(Long userId, Long productId, Integer quantity) {
        if (quantity <= 0) {
            throw new RuntimeException("Số lượng phải lớn hơn 0");
        }

        Cart cart = getCartByUserId(userId);

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm"));

        if (product.getStock() < quantity) {
            throw new RuntimeException("Tồn kho không đủ");
        }

        Optional<CartItem> existingItem = cartItemRepository.findByCartAndProduct(cart, product);

        if (existingItem.isPresent()) {
            CartItem item = existingItem.get();
            int newQuantity = item.getQuantity() + quantity;
            
            if (product.getStock() < newQuantity) {
                throw new RuntimeException("Tồn kho không đủ cho số lượng được cập nhật");
            }
            
            item.setQuantity(newQuantity);
            cartItemRepository.save(item);
        } else {
            CartItem newItem = new CartItem();
            newItem.setCart(cart);
            newItem.setProduct(product);
            newItem.setQuantity(quantity);
            newItem.setAddedAt(LocalDateTime.now());
            cartItemRepository.save(newItem);
        }

        cart.setUpdatedAt(LocalDateTime.now());
        return cartRepository.save(cart);
    }

    /**
     * Xóa sản phẩm khỏi giỏ hàng (THEO ITEM ID)
     */
    public CartResponse removeItemFromCart(Long userId, Long itemId) {
        CartItem cartItem = cartItemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm trong giỏ hàng"));

        if (!cartItem.getCart().getUser().getId().equals(userId)) {
            throw new RuntimeException("Không có quyền xóa sản phẩm này");
        }

        Cart cart = cartItem.getCart();
        cartItemRepository.delete(cartItem);
        
        cart.setUpdatedAt(LocalDateTime.now());
        Cart savedCart = cartRepository.save(cart);
        
        CartResponse response = cartMapper.toCartResponse(savedCart);
        calculateCartTotals(response, savedCart);
        log.info("Xóa sản phẩm {} khỏi giỏ hàng", itemId);
        return response;
    }

    /**
     * Xóa toàn bộ giỏ hàng
     */
    public void clearCart(Long userId) {
        Cart cart = getCartByUserId(userId);

        cartItemRepository.deleteByCart(cart);
        
        cart.setUpdatedAt(LocalDateTime.now());
        cartRepository.save(cart);
        log.info("Xóa toàn bộ giỏ hàng của user: {}", userId);
    }

    /**
     * Lấy giỏ hàng theo user ID (trả về DTO)
     */
    @Transactional(readOnly = true)
    public CartResponse getCartResponseByUserId(Long userId) {
        Cart cart = getCartByUserId(userId);
        CartResponse response = cartMapper.toCartResponse(cart);
        calculateCartTotals(response, cart);
        return response;
    }

    /**
     * Thêm sản phẩm vào giỏ hàng (dùng DTO)
     */
    public CartResponse addItemToCart(Long userId, AddToCartRequest request) {
        Cart cart = addItemToCart(userId, request.getProductId(), request.getQuantity());
        CartResponse response = cartMapper.toCartResponse(cart);
        calculateCartTotals(response, cart);
        log.info("Thêm sản phẩm vào giỏ hàng của user: {}", userId);
        return response;
    }

    /**
     * Cập nhật số lượng sản phẩm trong giỏ hàng (THEO ITEM ID)
     */
    public CartResponse updateItemQuantity(Long userId, Long itemId, UpdateCartItemRequest request) {
        if (request.getQuantity() <= 0) {
            throw new RuntimeException("Số lượng phải lớn hơn 0");
        }

        CartItem cartItem = cartItemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm trong giỏ hàng"));

        if (!cartItem.getCart().getUser().getId().equals(userId)) {
            throw new RuntimeException("Không có quyền cập nhật sản phẩm này");
        }

        Cart cart = cartItem.getCart();
        Product product = cartItem.getProduct();

        if (product.getStock() < request.getQuantity()) {
            throw new RuntimeException("Tồn kho không đủ cho số lượng được yêu cầu");
        }

        cartItem.setQuantity(request.getQuantity());
        cartItemRepository.save(cartItem);

        cart.setUpdatedAt(LocalDateTime.now());
        Cart savedCart = cartRepository.save(cart);

        CartResponse response = cartMapper.toCartResponse(savedCart);
        calculateCartTotals(response, savedCart);
        log.info("Cập nhật số lượng sản phẩm {} của user: {}", itemId, userId);
        return response;
    }

    /**
     * Tính toán tổng số lượng và tổng tiền
     */
    private void calculateCartTotals(CartResponse response, Cart cart) {
        List<CartItem> cartItems = cartItemRepository.findByCart(cart);
        
        if (cartItems != null && !cartItems.isEmpty()) {
            int totalItems = 0;
            java.math.BigDecimal totalPrice = java.math.BigDecimal.ZERO;
            
            for (CartItem item : cartItems) {
                totalItems += item.getQuantity();
                
                java.math.BigDecimal itemPrice = item.getProduct().getPrice();
                java.math.BigDecimal itemTotal = itemPrice.multiply(java.math.BigDecimal.valueOf(item.getQuantity()));
                totalPrice = totalPrice.add(itemTotal);
            }
            
            response.setTotalItems(totalItems);
            response.setTotalPrice(totalPrice);
        } else {
            response.setTotalItems(0);
            response.setTotalPrice(java.math.BigDecimal.ZERO);
        }
    }
}