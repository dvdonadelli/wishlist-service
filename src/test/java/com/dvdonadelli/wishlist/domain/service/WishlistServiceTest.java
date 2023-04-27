package com.dvdonadelli.wishlist.domain.service;

import com.dvdonadelli.wishlist.domain.exceptions.WishlistNotFoundException;
import com.dvdonadelli.wishlist.domain.model.Wishlist;
import com.dvdonadelli.wishlist.domain.model.WishlistItem;
import com.dvdonadelli.wishlist.infrastructure.repository.WishlistRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.ArgumentCaptor;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_METHOD)
class WishlistServiceTest {
    private static WishlistRepository repository;
    private static WishlistService service;

    @BeforeEach
    public void setUp() {
        repository = mock(WishlistRepository.class);
        service = new WishlistService(repository);
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddItemTo() {
        // Given
        String userId = "Davi";
        String productId = "PRODUCT_1";

        Wishlist existingList = Wishlist.forUser(userId);
        when(repository.findByUserId(userId)).thenReturn(Optional.of(existingList));

        Wishlist expectedList = Wishlist.forUserWithProduct(userId, productId);
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

        Wishlist expectedList = Wishlist.forUserWithProduct(userId, productId);
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

        Wishlist existingList = Wishlist.forUser(userId);
        existingList.addItem(productId);
        when(repository.findByUserId(userId)).thenReturn(Optional.of(existingList));

        // When
        Wishlist updatedWishlist = service.addItem(userId, productId);

        // Then
        assertNotNull(updatedWishlist);
        assertEquals(existingList.getItems().size(), updatedWishlist.getItems().size());
        verify(repository, never()).save(any(Wishlist.class));
    }

    @Test
    public void testRemoveItemFromWishlist_ItemExists_SuccessfullyRemoved() {
        // Given
        String userId = "user123";
        String productId = "product123";

        Wishlist wishlist = Wishlist.forUserWithProduct(userId, productId);
        when(repository.findByUserId(userId)).thenReturn(Optional.of(wishlist));

        // When
        service.removeItemFromWishlist(userId, productId);

        // Then
        ArgumentCaptor<Wishlist> wishlistCaptor = ArgumentCaptor.forClass(Wishlist.class);
        verify(repository, times(1)).findByUserId(userId);
        verify(repository, times(1)).save(wishlistCaptor.capture());

        Wishlist capturedWishlist = wishlistCaptor.getValue();
        Assertions.assertEquals(0, capturedWishlist.getItems().size());
    }

    @Test
    public void testRemoveItemFromWishlist_WishlistNotFound_ExceptionThrown() {
        // Given
        String userId = "user123";
        String productId = "product123";

        when(repository.findByUserId(userId)).thenReturn(Optional.empty());

        // When
        Assertions.assertThrows(WishlistNotFoundException.class,
                () -> service.removeItemFromWishlist(userId, productId));

        // Then
        verify(repository, times(1)).findByUserId(userId);
        verify(repository, never()).save(any());
    }

    @Test
    public void testIsProductInWishlist_ItemExists_ReturnsWishlistItem() {
        // Given
        String userId = "user123";
        String productId = "product123";
        WishlistItem wishlistItem = WishlistItem.of(productId);
        Wishlist wishlist = Wishlist.forUserWithProduct(userId, productId);

        when(repository.findByUserId(userId)).thenReturn(Optional.of(wishlist));

        // When
        WishlistItem result = service.isProductInWishlist(userId, productId);

        // Then
        verify(repository, times(1)).findByUserId(userId);
        Assertions.assertEquals(wishlistItem, result);
    }

    @Test
    public void testIsProductInWishlist_WishlistNotFound_ExceptionThrown() {
        // Given
        String userId = "user123";
        String productId = "product123";

        when(repository.findByUserId(userId)).thenReturn(Optional.empty());

        // When
        Assertions.assertThrows(WishlistNotFoundException.class,
                () -> service.isProductInWishlist(userId, productId));

        // Then
        verify(repository, times(1)).findByUserId(userId);
    }

    @Test
    public void testGetWishlist_WishlistExists_ReturnsWishlist() {
        // Given
        String userId = "user123";
        Wishlist wishlist = Wishlist.forUser(userId);

        when(repository.findByUserId(userId)).thenReturn(Optional.of(wishlist));

        // When
        Wishlist result = service.getWishlist(userId);

        // Then
        verify(repository, times(1)).findByUserId(userId);
        Assertions.assertEquals(wishlist, result);
    }

    @Test
    public void testGetWishlist_WishlistNotFound_ExceptionThrown() {
        // Given
        String userId = "user123";

        when(repository.findByUserId(userId)).thenReturn(Optional.empty());

        // When
        Assertions.assertThrows(WishlistNotFoundException.class,
                () -> service.getWishlist(userId));

        // Then
        verify(repository, times(1)).findByUserId(userId);
    }
}