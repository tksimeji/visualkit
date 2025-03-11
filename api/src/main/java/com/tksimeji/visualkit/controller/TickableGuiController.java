package com.tksimeji.visualkit.controller;

public interface TickableGuiController extends GuiController {
    default void tick() {
    }
}
