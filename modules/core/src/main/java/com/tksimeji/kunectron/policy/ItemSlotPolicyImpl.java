package com.tksimeji.kunectron.policy;

final class ItemSlotPolicyImpl implements ItemSlotPolicy {
    private final boolean variation;

    public ItemSlotPolicyImpl(final boolean variation) {
        this.variation = variation;
    }

    @Override
    public boolean isFixation() {
        return !variation;
    }

    @Override
    public boolean isVariation() {
        return variation;
    }
}
