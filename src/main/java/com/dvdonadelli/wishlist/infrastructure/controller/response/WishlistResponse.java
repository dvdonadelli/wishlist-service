package com.dvdonadelli.wishlist.infrastructure.controller.response;

import com.dvdonadelli.wishlist.domain.model.Wishlist;

import java.time.LocalDateTime;
import java.util.List;

public record WishlistResponse(String userId, List<WishlistItemResponse> items,
                               LocalDateTime dateCreated, LocalDateTime dateModified) {

    public static WishlistResponse fromDomain(Wishlist wishlist) {
        List<WishlistItemResponse> itemResponses = wishlist.getItems().stream()
                .map(WishlistItemResponse::fromDomain)
                .toList();
        return new WishlistResponse(wishlist.getUserId(),
                itemResponses,
                wishlist.getDateCreated(),
                wishlist.getDateModified());
    }
}
