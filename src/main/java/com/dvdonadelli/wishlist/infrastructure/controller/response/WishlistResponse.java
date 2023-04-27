package com.dvdonadelli.wishlist.infrastructure.controller.response;

import com.dvdonadelli.wishlist.domain.model.Wishlist;

import java.util.List;

public record WishlistResponse(
        String userId,
        List<WishlistItemResponse> items
) {

    public static WishlistResponse fromDomain(Wishlist wishlist) {
        List<WishlistItemResponse> itemResponses = wishlist.getItems().stream()
                .map(WishlistItemResponse::fromDomain)
                .toList();
        return new WishlistResponse(wishlist.getUserId(), itemResponses);
    }
}
