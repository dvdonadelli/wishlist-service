package com.dvdonadelli.wishlist.domain.service;

import com.dvdonadelli.wishlist.domain.model.Wishlist;
import com.dvdonadelli.wishlist.domain.repository.WishlistRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.time.LocalDateTime;
import java.util.List;

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
    void testAddItem_success() {
        // Given
        String userId = "Davi";
        String productId = "PRODUCT_1";
        LocalDateTime now = LocalDateTime.now();

        Wishlist existingList = new Wishlist(userId, List.of(), now, now);
        when(repository.findByUserId(userId)).thenReturn(existingList);

        // When
        Wishlist updatedWishlist = service.addItem(userId, productId);

        // Then
        assertNotNull(updatedWishlist);
        assertEquals(1, updatedWishlist.getItems().size());
        assertEquals(productId, updatedWishlist.getItems().get(0).getProductId());
        verify(repository).save(any(Wishlist.class));
    }

}