package com.dvdonadelli.wishlist.infrastructure.repository;

import com.dvdonadelli.wishlist.domain.model.Wishlist;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;


public interface WishlistRepository extends MongoRepository<Wishlist, String> {
    Optional<Wishlist> findByUserId(String userId);
}
