package com.dvdonadelli.wishlist.infrastructure.controller.request;

public record AddItemRequest(
        String userId,
        String productId
) {
}
