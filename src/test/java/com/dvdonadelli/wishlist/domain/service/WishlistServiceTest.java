package com.dvdonadelli.wishlist.domain.service;

import com.dvdonadelli.wishlist.domain.model.Wishlist;
import com.dvdonadelli.wishlist.domain.model.WishlistItem;
import com.dvdonadelli.wishlist.domain.repository.WishlistRepository;
import com.dvdonadelli.wishlist.infrastructure.service.WishlistServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;
import static org.mockito.Mockito.*;

@TestInstance(PER_CLASS)
class WishlistServiceTest {
    private static WishlistRepository repository;
    private static WishlistService service;

    @BeforeAll
    static void setUp() {
        repository = mock(WishlistRepository.class);
        service = new WishlistServiceImpl(repository);
    }

    @Test
    void testAddItemTo() {
        // Given
        String userId = "Davi";
        String productId = "PRODUCT_1";
        LocalDateTime now = LocalDateTime.now();

        Wishlist existingList = new Wishlist(userId, new ArrayList<>(), now, now);
        when(repository.findByUserId(userId)).thenReturn(Optional.of(existingList));

        Wishlist expectedList = new Wishlist(userId, List.of(new WishlistItem(productId, now)), now, now);
        when(repository.save(any(Wishlist.class))).thenReturn(expectedList);

        // When
        Wishlist updatedWishlist = service.addItem(userId, productId);

        // Then
        assertNotNull(updatedWishlist);
        assertEquals(1, updatedWishlist.getItems().size());
        assertEquals(productId, updatedWishlist.getItems().get(0).getProductId());
        verify(repository).save(any(Wishlist.class));
    }

    @Test
    void testAddItemTo_WhenListDoesNotExist() {
        // Given
        String userId = "Davi";
        String productId = "PRODUCT_1";
        LocalDateTime now = LocalDateTime.now();

        Wishlist expectedList = new Wishlist(userId, List.of(new WishlistItem(productId, now)), now, now);
        when(repository.save(any(Wishlist.class))).thenReturn(expectedList);

        // When
        Wishlist updatedWishlist = service.addItem(userId, productId);

        // Then
        assertNotNull(updatedWishlist);
        assertEquals(1, updatedWishlist.getItems().size());
        assertEquals(productId, updatedWishlist.getItems().get(0).getProductId());
        verify(repository).save(any(Wishlist.class));
    }

    @Test
    void testAddItemTo_NoDuplicates() {
        // Given
        String userId = "Davi";
        String productId = "PRODUCT_1";
        LocalDateTime now = LocalDateTime.now();

        Wishlist existingList = new Wishlist(userId, List.of(new WishlistItem(productId, now)), now, now);
        when(repository.findByUserId(userId)).thenReturn(Optional.of(existingList));

        // When
        Wishlist updatedWishlist = service.addItem(userId, productId);

        // Then
        assertNotNull(updatedWishlist);
        assertEquals(existingList.getItems().size(), updatedWishlist.getItems().size());
        verify(repository, never()).save(any(Wishlist.class));
    }

}