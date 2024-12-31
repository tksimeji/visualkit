package com.tksimeji.visualkit.util;

import com.tksimeji.visualkit.Killable;

import java.util.HashMap;
import java.util.Optional;

public class KillableHashMap<K, V extends Killable> extends HashMap<K, V> {
    @Override
    public V remove(Object key) {
        Optional.ofNullable(get(key)).ifPresent(Killable::kill);
        return super.remove(key);
    }

    @Override
    public boolean remove(Object key, Object value) {
        if (get(key) == value && value != null) {
            Optional.of((Killable) value).ifPresent(Killable::kill);
        }

        return super.remove(key, value);
    }

    @Override
    public void clear() {
        values().forEach(killable -> Optional.ofNullable(killable).ifPresent(Killable::kill));
        super.clear();
    }
}
