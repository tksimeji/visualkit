package com.tksimeji.kunectron.controller;

public interface TickableGuiController extends GuiController {
    default void tick() {
    }
}
