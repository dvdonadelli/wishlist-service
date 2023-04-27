package com.dvdonadelli.wishlist.domain.service;

import com.dvdonadelli.wishlist.domain.model.Wishlist;
import com.dvdonadelli.wishlist.infrastructure.repository.WishlistRepository;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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

    public Optional<Wishlist> getWishlist(String userId) {
        return repository.findByUserId(userId);
    }

    public Boolean isProductInWishlist(String userId, String productId) throws NotFoundException {
        Wishlist wishlist = repository.findByUserId(userId)
                .orElseThrow(NotFoundException::new);

        return wishlist.getItems().stream()
                .anyMatch(item -> item.getProductId().equals(productId));
    }

    public void removeItemFromWishlist(String userId, String productId) throws NotFoundException {
        Wishlist wishlist = repository.findByUserId(userId)
                .orElseThrow(NotFoundException::new);

        wishlist.getItems().removeIf(item -> item.getProductId().equals(productId));
        wishlist.setDateModified(LocalDateTime.now());

        repository.save(wishlist);
    }
}
