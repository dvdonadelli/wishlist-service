package com.dvdonadelli.wishlist.domain.service;

import com.dvdonadelli.wishlist.domain.exceptions.WishlistNotFoundException;
import com.dvdonadelli.wishlist.domain.model.Wishlist;
import com.dvdonadelli.wishlist.domain.model.WishlistItem;
import com.dvdonadelli.wishlist.infrastructure.repository.WishlistRepository;
import org.springframework.stereotype.Service;

@Service
public class WishlistService {

    private final WishlistRepository repository;

    public WishlistService(WishlistRepository repository) {
        this.repository = repository;
    }

    public Wishlist addItem(String userId, String productId) {
        final var wishlist = repository.findByUserId(userId).orElseGet(() -> Wishlist.forUser(userId));
        final var initialSize = wishlist.getItems().size();

        wishlist.addItem(productId);

        if (initialSize != wishlist.getItems().size()) return repository.save(wishlist);

        return wishlist;
    }

    public Wishlist getWishlist(String userId) {
        return repository.findByUserId(userId)
                .orElseThrow(() -> new WishlistNotFoundException(userId));
    }

    public WishlistItem isProductInWishlist(String userId, String productId) {
        final var wishlist = repository.findByUserId(userId)
                .orElseThrow(() -> new WishlistNotFoundException(userId));

        return wishlist.findItemByProductId(productId);
    }

    public void removeItemFromWishlist(String userId, String productId) {
        final var wishlist = repository.findByUserId(userId)
                .orElseThrow(() -> new WishlistNotFoundException(userId));

        wishlist.removeItemByProductId(productId);
        repository.save(wishlist);
    }
}
