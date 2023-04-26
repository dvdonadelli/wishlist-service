package com.dvdonadelli.wishlist.domain.service;

import com.dvdonadelli.wishlist.domain.model.Wishlist;
import org.springframework.stereotype.Service;

@Service
public interface WishlistService {
    Wishlist addItem(String userId, String productId);
}
