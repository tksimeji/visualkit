package com.tksimeji.visualkit.util;

import org.jetbrains.annotations.Nullable;

import java.io.Closeable;
import java.io.IOException;
import java.util.HashMap;

public class CloseableHashMap<K, V extends Closeable> extends HashMap<K, V> {
    protected static void close(@Nullable Closeable closeable) {
        if (closeable == null) {
            return;
        }

        try {
            closeable.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public V remove(Object key) {
        close(get(key));
        return super.remove(key);
    }

    @Override
    public boolean remove(Object key, Object value) {
        if (get(key) == value) {
            close((Closeable) value);
        }

        return super.remove(key, value);
    }

    @Override
    public void clear() {
        values().forEach(CloseableHashMap::close);
        super.clear();
    }
}
