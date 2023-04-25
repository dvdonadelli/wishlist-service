package com.dvdonadelli.wishlist.infrastructure.repository;

import com.dvdonadelli.wishlist.domain.model.Wishlist;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WishlistRepository extends CrudRepository<Wishlist, String> {

    Optional<Wishlist> findByUserId(String userId);
}
