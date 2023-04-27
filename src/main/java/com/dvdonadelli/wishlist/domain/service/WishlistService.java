package com.dvdonadelli.wishlist.domain.service;

import com.dvdonadelli.wishlist.domain.exceptions.WishlistNotFoundException;
import com.dvdonadelli.wishlist.domain.model.Wishlist;
import com.dvdonadelli.wishlist.domain.model.WishlistItem;
import com.dvdonadelli.wishlist.infrastructure.repository.WishlistRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

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

    public Wishlist getWishlist(String userId) {
        Optional<Wishlist> optional = repository.findByUserId(userId);

        if (optional.isPresent()) {
            return optional.get();
        } else {
            throw new WishlistNotFoundException("Wishlist not found for user: " + userId);
        }
    }

    public WishlistItem isProductInWishlist(String userId, String productId) {
        Optional<Wishlist> optionalWishlist = repository.findByUserId(userId);

        if (optionalWishlist.isPresent()) {
            Wishlist wishlist = optionalWishlist.get();
            return wishlist.findItemByProductId(productId);
        } else {
            throw new WishlistNotFoundException("Wishlist not found for user: " + userId);
        }
    }

    public void removeItemFromWishlist(String userId, String productId) {
        Optional<Wishlist> optional = repository.findByUserId(userId);

        if (optional.isPresent()) {
            Wishlist wishlist = optional.get();
            wishlist.removeItemByProductId(productId);
            repository.save(wishlist);
        } else {
            throw new WishlistNotFoundException("Wishlist not found for user: " + userId);
        }
    }
}
