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
            item.sellIn--;
            final int factor = updateFactor(item);
            item.quality += factor;
            if (item.sellIn < SELL_IN_EXPIRED) {
                item.quality += factor;
            }
            if (item.name.equals(BACKSTAGE)) {
                if (item.sellIn < 11) {
                    item.quality++;
                }
                if (item.sellIn < 6) {
                    item.quality++;
                }
                if (item.sellIn <= SELL_IN_EXPIRED) {
                    item.quality = 0;
                }
            }
            correct(item);
            if (item.name.equals(SULFURAS)) {
                item.quality = LEGENDARY_QUALITY;
                item.sellIn++;
            }
        }
    }

    private static void correct(Item item) {
        if (item.quality <= MIN_QUALITY) {
            item.quality = MIN_QUALITY;
        }
        if (item.quality >= MAX_REGULAR_QUALITY) {
            item.quality = MAX_REGULAR_QUALITY;
        }
    }

    private static int updateFactor(final Item item) {
        final int pow = item.name.startsWith(CONJURED) ? 2 : 1;
        final int inversion = item.name.equals(BRIE) || item.name.equals(BACKSTAGE) ? 1 : -1;
        return pow * inversion;
    }
}
