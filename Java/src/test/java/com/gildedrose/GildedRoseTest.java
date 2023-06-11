package com.gildedrose;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.UUID;

@DisplayName("Unit Test: GildedRose.")
final class GildedRoseTest {

    private GildedRose subject;

    @ParameterizedTest(name = "providedSellIn=[{0}], providedQuality=[{1}], expectedQuality=[{2}]")
    @MethodSource("anyNameArgs")
    @DisplayName("GIVEN Any Name item WHEN update THEN expected quality.")
    void givenItemWithAnyName_whenUpdateQuality_thenHasExpectedSellIn_andExpectedQuality(
        int providedSellIn, int providedQuality, int expectedQuality) {
        // Given
        final String name = UUID.randomUUID().toString();
        subject = new GildedRose(new Item[]{new Item(name, providedSellIn, providedQuality)});
        // When
        subject.updateQuality();
        // Then
        Assertions.assertEquals(1, providedSellIn - subject.items[0].sellIn, "Sell in value mismatch.");
        Assertions.assertEquals(expectedQuality, subject.items[0].quality, "Quality value mismatch.");
    }

    @ParameterizedTest(name = "providedSellIn=[{0}], providedQuality=[{1}], expectedQuality=[{2}]")
    @MethodSource("agedBrieArgs")
    @DisplayName("GIVEN aged brie item WHEN update THEN expected quality.")
    void givenBrieItem_whenUpdateQuality_thenHasExpectedSellIn_andExpectedQuality(
        int providedSellIn, int providedQuality, int expectedQuality) {
        // Given
        subject = new GildedRose(new Item[]{new Item(GildedRose.BRIE, providedSellIn, providedQuality)});
        // When
        subject.updateQuality();
        // Then
        Assertions.assertEquals(1, providedSellIn - subject.items[0].sellIn, "Sell in value mismatch.");
        Assertions.assertEquals(expectedQuality, subject.items[0].quality, "Quality value mismatch.");
    }

    @ParameterizedTest(name = "providedSellIn=[{0}], providedQuality=[{1}], expectedQuality=[{2}]")
    @MethodSource("backstageArgs")
    @DisplayName("GIVEN Backstage Item WHEN update THEN expected quality.")
    void givenBackstageItem_whenUpdateQuality_thenHasExpectedName(
        int providedSellIn, int providedQuality, int expectedQuality) {
        // Given
        subject = new GildedRose(new Item[]{new Item(GildedRose.BACKSTAGE, providedSellIn, providedQuality)});
        // When
        subject.updateQuality();
        // Then
        Assertions.assertEquals(1, providedSellIn - subject.items[0].sellIn, "Sell in value mismatch.");
        Assertions.assertEquals(expectedQuality, subject.items[0].quality, "Quality value mismatch.");
    }

    @ParameterizedTest(name = "providedSellIn=[{0}], providedQuality=[{1}], expectedQuality=[{2}]")
    @MethodSource("conjuredArgs")
    @DisplayName("GIVEN conjured item WHEN update THEN expected quality.")
    void givenConjuredItem_whenUpdateQuality_thenHasExpectedName(
        int providedSellIn, int providedQuality, int expectedQuality) {
        // Given
        final String name = "Conjured Mana Cake";
        subject = new GildedRose(new Item[]{new Item(name, providedSellIn, providedQuality)});
        // When
        subject.updateQuality();
        // Then
        Assertions.assertEquals(1, providedSellIn - subject.items[0].sellIn, "Sell in value mismatch.");
        Assertions.assertEquals(expectedQuality, subject.items[0].quality, "Quality value mismatch.");
    }

    @Test
    @DisplayName("GIVEN legendary item WHEN update THEN expected quality.")
    void givenLegendaryItem_whenUpdateQuality_thenHasExpectedQuality() {
        // Given
        final int sellIn = 1;
        final int quality = 80;
        subject = new GildedRose(new Item[]{new Item(GildedRose.SULFURAS, sellIn, quality)});
        // When
        subject.updateQuality();
        // Then
        Assertions.assertEquals(sellIn, subject.items[0].sellIn, "Sell in value mismatch.");
        Assertions.assertEquals(quality, subject.items[0].quality, "Quality value mismatch.");
    }

    @AfterEach
    void tearDown() {
        // Then
        Assertions.assertTrue(subject.items[0].quality >= 0, "Quality is negative.");
    }

    private static Arguments[] anyNameArgs() {
        return new Arguments[]{ //
            // The Quality of an item is never negative
            Arguments.of(0, 0, 0), //
            // Once the sell by date has passed, Quality degrades twice as fast
            Arguments.of(0, 5, 3), //
        };
    }

    private static Arguments[] agedBrieArgs() {
        return new Arguments[]{ //
            // "Aged Brie" actually increases in Quality the older it gets
            Arguments.of(1, 50, 50), //
            Arguments.of(1, 1, 2), //
            Arguments.of(0, 0, 2), //
        };
    }

    private static Arguments[] backstageArgs() {
        return new Arguments[]{ //
            Arguments.of(0, 0, 0), //
            // "Backstage passes", like aged brie, increases in Quality as its SellIn value approaches;
            Arguments.of(12, 1, 2), //
            // Increases by 2 when there are 10 days or less
            Arguments.of(10, 1, 3), //
            Arguments.of(7, 5, 7), //
            // and by 3 when there are 5 days or less
            Arguments.of(5, 1, 4), //
            Arguments.of(5, 48, 50), //
            Arguments.of(5, 49, 50), //
            // Drops to 0 after the concert
            Arguments.of(0, 50, 0), //
        };
    }

    private static Arguments[] conjuredArgs() {
        return new Arguments[]{ //
            // "Conjured" items degrade in Quality twice as fast as normal items
            // Once the sell by date has passed, Quality degrades twice as fast
            Arguments.of(2, 5, 3), //
            Arguments.of(0, 5, 1), //
            Arguments.of(0, 0, 0), //
        };
    }
}
