package com.zerobase.order.service;

import com.zerobase.order.client.RedisClient;
import com.zerobase.order.domain.product.AddProductCartForm;
import com.zerobase.order.domain.redis.Cart;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class CartService {

    private RedisClient redisClient;

    public Cart getCart(Long customerId) {
        return redisClient.get(customerId, Cart.class);
    }

    public Cart addCart(Long customerId, AddProductCartForm form) {

        Cart cart = redisClient.get(customerId, Cart.class);
        if (cart == null) {
            cart = new Cart();
            cart.setCustomerId(customerId);
        }
        // 이전에 같은 상품이 있냐
        Optional<Cart.Product> productOptional = cart.getProducts().stream()
            .filter(product1 -> product1.getId().equals(form.getId()))
            .findFirst();

        if (productOptional.isPresent()) {
            Cart.Product redisProduct = productOptional.get();
            // requested
            List<Cart.ProductItem> items = form.getItems().stream().map(Cart.ProductItem::from).collect(
                Collectors.toList());
            // 검색속도 빠르게 하기 위해 map 사용
            Map<Long, Cart.ProductItem> redisItemMap = redisProduct.getItems().stream()
                .collect(Collectors.toMap(it -> it.getId(), it -> it));

            if (!redisProduct.getName().equals(form.getName())) {
                cart.addMessage(redisProduct.getName() + "의 정보가 변경되었습니다. 학인 부탁드립니다.");
            }
            for(Cart.ProductItem item : items) {
                Cart.ProductItem redisItem = redisItemMap.get(item.getId());

                if (redisItem == null) {
                    redisProduct.getItems().add(item);
                    // happy case
                } else {
                    if (!redisItem.getPrice().equals(item.getPrice())) {
                        cart.addMessage(redisProduct.getName() + item.getName() + "의 정보가 변경되었습니다. 학인 부탁드립니다.");
                    }
                    redisItem.setCount(redisItem.getCount() + item.getCount());
                }
            }
        } else {
            Cart.Product product = Cart.Product.from(form);
            cart.getProducts().add(product);
        }
        redisClient.put(customerId, cart);
        return cart;
    }

}
