package com.dvdonadelli.wishlist.infrastructure.service;

import com.dvdonadelli.wishlist.domain.model.Wishlist;
import com.dvdonadelli.wishlist.domain.service.WishlistService;
import com.dvdonadelli.wishlist.infrastructure.repository.WishlistRepository;

public class WishlistServiceImpl implements WishlistService {

    private final WishlistRepository repository;

    public WishlistServiceImpl(WishlistRepository repository) {
        this.repository = repository;
    }

    @Override
    public Wishlist addItem(String userId, String productId) {
        Wishlist wishlist = repository.findByUserId(userId).orElseGet(() -> Wishlist.forUser(userId));
        int initialSize = wishlist.getItems().size();

        wishlist.addItem(productId);

        if (initialSize != wishlist.getItems().size()) return repository.save(wishlist);

        return wishlist;
    }
}
