package com.tksimeji.visualkit.controller;

public interface TickableController extends GuiController {
    default void tick() {
    }
}
