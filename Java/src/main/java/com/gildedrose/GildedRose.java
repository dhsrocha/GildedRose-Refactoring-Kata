package com.gildedrose;

class GildedRose {

    static final String BRIE = "Aged Brie";
    static final String SULFURAS = "Sulfuras, Hand of Ragnaros";
    static final String BACKSTAGE = "Backstage passes to a TAFKAL80ETC concert";
    static final String CONJURED = "Conjured";

    static final int SELL_IN_EXPIRED = 0;
    static final int MIN_QUALITY = 0;
    static final int MAX_REGULAR_QUALITY = 50;

    Item[] items;

    public GildedRose(Item[] items) {
        this.items = items;
    }

    public void updateQuality() {
        for (Item item : items) {
            if (!item.name.equals(BRIE) && !item.name.equals(BACKSTAGE)) {
                if (item.quality > MIN_QUALITY && (!item.name.equals(SULFURAS))) {
                    item.quality--;
                }
            } else {
                if (item.quality < MAX_REGULAR_QUALITY) {
                    item.quality++;

                    if (item.name.equals(BACKSTAGE)) {
                        if (item.sellIn < 11 && (item.quality < MAX_REGULAR_QUALITY)) {
                            item.quality++;
                        }
                        if (item.sellIn < 6 && (item.quality < MAX_REGULAR_QUALITY)) {
                            item.quality++;
                        }
                    }
                }
            }
            if (!item.name.equals(SULFURAS)) {
                item.sellIn--;
            }
            if (item.name.startsWith(CONJURED) && (item.quality > MIN_QUALITY)) {
                item.quality--;
            }
            if (item.sellIn < SELL_IN_EXPIRED) {
                if (item.name.startsWith(CONJURED) && (item.quality > MIN_QUALITY)) {
                    item.quality--;
                }
                if (!item.name.equals(BRIE)) {
                    if (!item.name.equals(BACKSTAGE)) {
                        if (item.quality > MIN_QUALITY && (!item.name.equals(SULFURAS))) {
                            item.quality--;
                        }
                    } else {
                        item.quality = 0;
                    }
                } else {
                    if (item.quality < MAX_REGULAR_QUALITY) {
                        item.quality++;
                    }
                }
            }
        }
    }
}
