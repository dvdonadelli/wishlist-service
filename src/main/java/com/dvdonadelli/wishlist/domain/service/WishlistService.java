package com.dvdonadelli.wishlist.domain.service;

import com.dvdonadelli.wishlist.domain.model.Wishlist;
import com.dvdonadelli.wishlist.infrastructure.repository.WishlistRepository;
import org.springframework.stereotype.Service;

@Service
public class WishlistService {

    private final WishlistRepository repository;

    public WishlistService(WishlistRepository repository) {
        this.repository = repository;
    }

    public Wishlist addItem(String userId, String productId) {
        Wishlist wishlist = repository.findByUserId(userId).orElseGet(() -> Wishlist.forUser(userId));
        int initialSize = wishlist.getItems().size();

        wishlist.addItem(productId);

        if (initialSize != wishlist.getItems().size()) return repository.save(wishlist);

        return wishlist;
    }
}
