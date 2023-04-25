package com.dvdonadelli.wishlist.domain.service;

import com.dvdonadelli.wishlist.domain.model.Wishlist;

public interface WishlistService {
    Wishlist addItem(String productId, String userId);
}
