package com.tksimeji.kunectron;

import com.google.common.base.Preconditions;
import com.tksimeji.kunectron.adapter.Adapter;
import com.tksimeji.kunectron.adapter.V1_21_1;
import com.tksimeji.kunectron.adapter.V1_21_3;
import com.tksimeji.kunectron.listener.*;
import com.tksimeji.kunectron.controller.GuiController;
import com.tksimeji.kunectron.markupextension.MarkupExtensionParser;
import com.tksimeji.kunectron.type.*;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.logger.slf4j.ComponentLogger;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.annotation.Annotation;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public final class Kunectron extends JavaPlugin {
    private static Kunectron instance;

    private static final @NotNull Set<GuiType<?, ?>> GUI_TYPES = new HashSet<>();

    private static final @NotNull Set<GuiController> controllers = new HashSet<>();

    private static final @NotNull LinkedHashSet<Adapter> adapters = new LinkedHashSet<>();

    private static @NotNull MarkupExtensionParser markupExtensionParse = MarkupExtensionParser.create();

    @ApiStatus.Internal
    public static @NotNull Kunectron plugin() {
        return instance;
    }

    @ApiStatus.Internal
    public static @NotNull ComponentLogger logger() {
        return plugin().getComponentLogger();
    }

    @ApiStatus.Internal
    public static @Nullable Adapter adapter() {
        return getAdapter(Bukkit.getMinecraftVersion());
    }

    public static <T> @NotNull T create(final @NotNull T gui) {
        List<GuiType<?, ?>> types = GUI_TYPES.stream()
                .filter(type -> gui.getClass().isAnnotationPresent(type.getAnnotationClass()))
                .toList();

        Preconditions.checkArgument(!types.isEmpty(), "No valid annotations found in '" + gui.getClass().getName() + "'.");
        Preconditions.checkState(types.size() == 1, "Multiple valid annotations have been applied, and therefore, the gui type cannot be determined. Please specify the preferred annotation in Kunectron.create(Object, Class<? extends Annotation>).");

        return create(gui, types.getFirst().getAnnotationClass());
    }

    public static <A extends Annotation, T> @NotNull T create(final @NotNull T gui, final @NotNull Class<A> annotation) {
        Preconditions.checkArgument(gui.getClass().isAnnotationPresent(annotation), String.format("'%s' is not annotated with '%s'.", gui.getClass().getName(), annotation.getName()));

        GuiType<A, ?> type = getGuiType(annotation);
        Preconditions.checkArgument(type != null, "No gui type found to handle '" + annotation.getName() + "'.");

        GuiController controller = type.createController(gui, gui.getClass().getAnnotation(annotation));
        controllers.add(controller);
        controller.init();
        return gui;
    }

    public static @NotNull Set<Object> getGuis() {
        return controllers.stream().map(GuiController::getGui).collect(Collectors.toSet());
    }

    public static @NotNull Set<Object> getGuis(final @NotNull GuiType<?, ?> type) {
        return getGuiControllers(type).stream().map(GuiController::getGui).collect(Collectors.toSet());
    }

    public static @Nullable GuiController getGuiController(final @NotNull Object gui) {
        return controllers.stream().filter(controller -> controller.getGui() == gui).findFirst().orElse(null);
    }

    public static @NotNull GuiController getGuiControllerOrThrow(final @NotNull Object gui) {
        GuiController controller = getGuiController(gui);
        if (controller == null) {
            throw new NullPointerException();
        }
        return controller;
    }

    public static <C extends GuiController> @NotNull Set<C> getGuiControllers(final @NotNull GuiType<?, C> type) {
        return controllers.stream()
                .filter(controller -> type.getControllerClass().isAssignableFrom(controller.getClass()))
                .map(controller -> (C) controller)
                .collect(Collectors.toSet());
    }

    public static @NotNull Set<GuiController> getGuiControllers() {
        return new HashSet<>(controllers);
    }

    public static void removeGuiController(final @NotNull GuiController controller) {
        controllers.remove(controller);
    }

    public static <A extends Annotation> @Nullable GuiType<A, ?> getGuiType(final @NotNull Class<A> annotation) {
        return GUI_TYPES.stream()
                .filter(type -> type.getAnnotationClass() == annotation)
                .map(type -> (GuiType<A, ?>) type)
                .findFirst()
                .orElse(null);
    }

    public static @NotNull Set<GuiType<?, ?>> getGuiTypes() {
        return new HashSet<>(GUI_TYPES);
    }

    public static void registerGuiType(final @NotNull GuiType<?, ?> type, final @NotNull Plugin plugin) {
        Preconditions.checkState(getGuiType(type.getAnnotationClass()) == null, "A gui type that specifies '" + type.getAnnotationClass().getName() + "' as an annotation is already registered.");
        GUI_TYPES.add(type);
    }

    public static @Nullable Adapter getAdapter(final @NotNull String minecraftVersion) {
        return adapters.stream()
                .filter(adapter -> Arrays.asList(adapter.supports()).contains(minecraftVersion))
                .findFirst()
                .orElse(null);
    }

    public static @NotNull Set<Adapter> getAdapters() {
        return new HashSet<>(adapters);
    }

    public static void registerAdapter(final @NotNull Adapter adapter) {
        adapters.addFirst(adapter);
    }

    public static @NotNull MarkupExtensionParser getMarkupExtensionParser() {
        return markupExtensionParse;
    }

    public static void setMarkupExtensionParser(final @NotNull MarkupExtensionParser markupExtensionParser) {
        Kunectron.markupExtensionParse = markupExtensionParser;
    }

    @Override
    public void onEnable() {
        instance = this;

        getServer().getPluginManager().registerEvents(new AnvilGuiListener(), this);
        getServer().getPluginManager().registerEvents(new ContainerGuiListener(), this);
        getServer().getPluginManager().registerEvents(new ItemContainerGuiListener(), this);
        getServer().getPluginManager().registerEvents(new MerchantGuiListener(), this);
        getServer().getPluginManager().registerEvents(new ServerListener(), this);

        registerGuiType(AnvilGuiType.instance(), this);
        registerGuiType(ChestGuiType.instance(), this);
        registerGuiType(MerchantGuiType.instance(), this);
        registerGuiType(ScoreboardGuiType.instance(), this);

        registerAdapter(V1_21_1.INSTANCE);
        registerAdapter(V1_21_3.INSTANCE);

        LocalDate localDate = LocalDate.now();

        int month = localDate.getMonthValue();
        int day = localDate.getDayOfMonth();

        if (month == 1 && day <= 3) {
            logger().info(Component.text(" _ __     ").color(NamedTextColor.WHITE));
            logger().info(Component.text("| / / _ _ ").color(NamedTextColor.WHITE).append(Component.text("  ")).append(Component.text(String.format("🎍 Kunectron - %s 🎍", getPluginMeta().getVersion())).color(NamedTextColor.WHITE).decorate(TextDecoration.UNDERLINED)));
            logger().info(MiniMessage.miniMessage().deserialize("<white>|  <#ca0048>\\ |</#ca0048> | |").append(Component.text("  Happy New Year!").color(NamedTextColor.WHITE)));
            logger().info(Component.text("|_\\_\\`___|").color(NamedTextColor.WHITE).append(Component.text("  ").append(Component.text("Akeome♡").color(NamedTextColor.RED))));
            logger().info(Component.empty());
        } else if (month == 2 && day == 14) {
            logger().info(Component.text(" _ __     ").color(TextColor.color(255, 0, 2)));
            logger().info(Component.text("| / / _ _ ").color(TextColor.color(219, 19, 60)).append(Component.text("  ")).append(Component.text(String.format("🍫 Kunectron - %s ❤", getPluginMeta().getVersion())).color(NamedTextColor.WHITE).decorate(TextDecoration.UNDERLINED)));
            logger().info(Component.text("|  \\ | | |").color(TextColor.color(177, 34, 34)).append(Component.text("  Happy Valentine's Day!").color(NamedTextColor.RED)));
            logger().info(Component.text("|_\\_\\`___|").color(TextColor.color(139, 1, 0)).append(Component.text("  ").append(Component.text("(。-∀-)っ🍫 あげる〜").color(TextColor.color(172, 130, 91)))));
            logger().info(Component.empty());
        } else if (month == 12 && day == 25) {
            logger().info(Component.text(" _ __     ").color(TextColor.color(235, 201, 129)));
            logger().info(Component.text("| / / _ _ ").color(TextColor.color(235, 201, 129)).append(Component.text("  ")).append(Component.text(String.format("🎅 Kunectron - %s 🎄", getPluginMeta().getVersion())).color(NamedTextColor.DARK_GREEN).decorate(TextDecoration.UNDERLINED)));
            logger().info(Component.text("|  \\ | | |").color(TextColor.color(235, 201, 129)).append(MiniMessage.miniMessage().deserialize("  <red>M</red><green>e</green><red>r</red><green>r</green><red>y</red> <green>C</green><red>h</red><green>r</green><red>i</red><green>s</green><red>t</red><green>m</green><red>a</red><green>s</green><red>!</red>").color(NamedTextColor.RED)));
            logger().info(Component.text("|_\\_\\`___|").color(TextColor.color(235, 201, 129)).append(Component.text("  ").append(Component.text("🎁🔔🎁🔔🎁🔔"))));
            logger().info(Component.empty());
        } else {
            logger().info(Component.text(" _ __     ").color(TextColor.color(255, 86, 217)));
            logger().info(Component.text("| / / _ _ ").color(TextColor.color(255, 124, 255)).append(Component.text("  ")).append(Component.text("Kunectron - " + getPluginMeta().getVersion()).color(NamedTextColor.WHITE).decorate(TextDecoration.UNDERLINED)));
            logger().info(Component.text("|  \\ | | |").color(TextColor.color(225, 142, 255)).append(Component.text("  Help poor children in Uganda!").color(NamedTextColor.GRAY)));
            logger().info(Component.text("|_\\_\\`___|").color(TextColor.color(164, 86, 255)).append(Component.text("  ").append(Component.text("https://iccf-holland.org/").color(NamedTextColor.BLUE).clickEvent(ClickEvent.openUrl("https://iccf-holland.org/")))));
            logger().info(Component.empty());
        }

        if (adapter() == null) {
            logger().warn(Component.text("⚠").color(NamedTextColor.YELLOW)
                    .append(Component.text(":").color(NamedTextColor.GRAY))
                    .appendSpace()
                    .append(Component.text("No adapters were found for your server version. Some features that depend on the adapter will not work. Updating the plugin may solve the problem.").color(NamedTextColor.WHITE)));
        }
    }
}
