package com.dvdonadelli.wishlist.infrastructure.service;

import com.dvdonadelli.wishlist.domain.model.Wishlist;
import com.dvdonadelli.wishlist.domain.model.WishlistItem;
import com.dvdonadelli.wishlist.domain.repository.WishlistRepository;
import com.dvdonadelli.wishlist.domain.service.WishlistService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Service
public class WishlistServiceImpl implements WishlistService {

    private final WishlistRepository repository;

    public WishlistServiceImpl(WishlistRepository repository) {
        this.repository = repository;
    }

    @Override
    public Wishlist addItem(String productId, String userId) {
        Wishlist current = repository.findByUserId(userId).orElseGet(() -> createNewWishlist(userId));

        if (current.getItems().size() >= 20) {
            throw new IllegalStateException("Wishlist already contains 20 items");
        }

        WishlistItem item = new WishlistItem(productId, LocalDateTime.now());
        current.getItems().add(item);
        current.setDateModified(LocalDateTime.now());

        return repository.save(current);
    }

    private Wishlist createNewWishlist(String userId) {
        LocalDateTime now = LocalDateTime.now();

        return new Wishlist(userId, new ArrayList<>(), now, now);
    }
}
