package com.gildedrose;

class GildedRose {

    static final String BRIE = "Aged Brie";
    static final String SULFURAS = "Sulfuras, Hand of Ragnaros";
    static final String BACKSTAGE = "Backstage passes to a TAFKAL80ETC concert";
    static final String CONJURED = "Conjured";

    static final int SELL_IN_EXPIRED = 0;
    static final int MIN_QUALITY = 0;
    static final int MAX_REGULAR_QUALITY = 50;
    static final int LEGENDARY_QUALITY = 80;

    Item[] items;

    public GildedRose(Item[] items) {
        this.items = items;
    }

    public void updateQuality() {
        for (final Item item : items) {
            categorize(item).update(item);
        }
    }

    private ItemStrategy categorize(Item item) {
        if (item.name.equals(BRIE)) {
            return new Cheese();
        }
        if (item.name.equals(BACKSTAGE)) {
            return new BackstagePass();
        }
        if (item.name.equals(SULFURAS)) {
            return new Legendary();
        }
        if (item.name.startsWith(CONJURED)) {
            return new Conjured();
        }
        return new ItemStrategy();
    }

    private static class ItemStrategy {
        void incrementQuality(Item item) {
            if (item.quality < MAX_REGULAR_QUALITY) {
                item.quality++;
            }
        }

        void decrementQuality(Item item) {
            if (item.quality > MIN_QUALITY) {
                item.quality--;
            }
        }

        void updateSellIn(Item item) {
            item.sellIn--;
        }

        void updateQuality(Item item) {
            decrementQuality(item);
        }

        void updateExpired(Item item) {
            decrementQuality(item);
        }

        private void update(Item item) {
            updateQuality(item);
            updateSellIn(item);
            if (item.sellIn < SELL_IN_EXPIRED) {
                updateExpired(item);
            }
        }
    }

    private static class Legendary extends ItemStrategy {
        @Override
        void updateSellIn(Item item) {
            // No effect
        }

        @Override
        void updateExpired(Item item) {
            item.quality = LEGENDARY_QUALITY;
        }

        @Override
        void updateQuality(Item item) {
            item.quality = LEGENDARY_QUALITY;
        }
    }

    private static class Cheese extends ItemStrategy {

        @Override
        void updateExpired(Item item) {
            incrementQuality(item);
        }

        @Override
        void updateQuality(Item item) {
            incrementQuality(item);
        }
    }

    private static class BackstagePass extends ItemStrategy {

        @Override
        void updateExpired(Item item) {
            item.quality = MIN_QUALITY;
        }

        @Override
        void updateQuality(Item item) {
            incrementQuality(item);
            if (item.sellIn <= 10) {
                incrementQuality(item);
            }
            if (item.sellIn <= 5) {
                incrementQuality(item);
            }
        }
    }

    private static class Conjured extends ItemStrategy {
        @Override
        void updateExpired(Item item) {
            updateQuality(item);
        }

        @Override
        void updateQuality(Item item) {
            decrementQuality(item);
            decrementQuality(item);
        }
    }
}
