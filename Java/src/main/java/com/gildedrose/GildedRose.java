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
        for (int i = 0; i < items.length; i++) {
            if (!items[i].name.equals(BRIE)
                    && !items[i].name.equals(BACKSTAGE)) {
                if (items[i].quality > MIN_QUALITY) {
                    if (!items[i].name.equals(SULFURAS)) {
                        items[i].quality = items[i].quality - 1;
                    }
                }
            } else {
                if (items[i].quality < MAX_REGULAR_QUALITY) {
                    items[i].quality = items[i].quality + 1;

                    if (items[i].name.equals(BACKSTAGE)) {
                        if (items[i].sellIn < 11) {
                            if (items[i].quality < MAX_REGULAR_QUALITY) {
                                items[i].quality = items[i].quality + 1;
                            }
                        }

                        if (items[i].sellIn < 6) {
                            if (items[i].quality < MAX_REGULAR_QUALITY) {
                                items[i].quality = items[i].quality + 1;
                            }
                        }
                    }
                }
            }

            if (!items[i].name.equals(SULFURAS)) {
                items[i].sellIn = items[i].sellIn - 1;
            }

            if (items[i].name.startsWith(CONJURED)) {
                if (items[i].quality > MIN_QUALITY) {
                    items[i].quality = items[i].quality - 1;
                }
            }

            if (items[i].sellIn < SELL_IN_EXPIRED) {
                if (items[i].name.startsWith(CONJURED)) {
                    if (items[i].quality > MIN_QUALITY) {
                        items[i].quality = items[i].quality - 1;
                    }
                }
                if (!items[i].name.equals(BRIE)) {
                    if (!items[i].name.equals(BACKSTAGE)) {
                        if (items[i].quality > MIN_QUALITY) {
                            if (!items[i].name.equals(SULFURAS)) {
                                items[i].quality = items[i].quality - 1;
                            }
                        }
                    } else {
                        items[i].quality = items[i].quality - items[i].quality;
                    }
                } else {
                    if (items[i].quality < MAX_REGULAR_QUALITY) {
                        items[i].quality = items[i].quality + 1;
                    }
                }
            }
        }
    }
}
