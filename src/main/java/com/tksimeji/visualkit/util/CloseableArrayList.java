package com.tksimeji.visualkit.util;

import org.jetbrains.annotations.Nullable;

import java.io.Closeable;
import java.io.IOException;
import java.util.ArrayList;

public class CloseableArrayList<E extends Closeable> extends ArrayList<E> {
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
    public E set(int index, E element) {
        close(get(index));
        return super.set(index, element);
    }

    @Override
    public E remove(int index) {
        close(get(index));
        return super.remove(index);
    }

    @Override
    public void clear() {
        forEach(CloseableArrayList::close);
        super.clear();
    }
}
