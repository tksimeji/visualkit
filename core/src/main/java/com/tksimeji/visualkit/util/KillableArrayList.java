package com.tksimeji.visualkit.util;

import com.tksimeji.visualkit.Killable;

import java.util.ArrayList;
import java.util.Optional;

@Deprecated(forRemoval = true)
public class KillableArrayList<E extends Killable> extends ArrayList<E> {
    @Override
    public E set(int index, E element) {
        Optional.ofNullable(get(index)).ifPresent(Killable::kill);
        return super.set(index, element);
    }

    @Override
    public E remove(int index) {
        Optional.ofNullable(get(index)).ifPresent(Killable::kill);
        return super.remove(index);
    }

    @Override
    public void clear() {
        forEach(killable -> Optional.ofNullable(killable).ifPresent(Killable::kill));
        super.clear();
    }
}
