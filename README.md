# Visualkit

![Banner](./assets/042a7581-620a-4df7-bb12-e873befa8529.png)

Visualkit is a free, open-source GUI framework for [Paper](https://papermc.io/software/paper) server.

Maximum respect to [Bram Moolenaar](https://github.com/brammool), the developer of [Vim](https://www.vim.org/).

I also sympathized with his philanthropic side, so I decided to add the same text as the Vim startup screen to the Visualkit banner.

#### "Help poor children in Uganda!"

Donate to the future of Uganda's children!

## ![Placeholder](./assets/2deaff09-7d77-4493-8f71-39609f3b2704.png) Placeholders

<a id="2479b5e2-7a0e-4a02-8e73-9ab045086560"></a>

In GUIs created with Visualkit, you can use placeholders for text.

```java
VisualkitElement
        .of(Material.NAME_TAG)
        .title(Component.text("Hello, ${name}."));
```

The placeholders are replaced with field values from the GUI implementation class
at rendering time and automatically updated.

## ![Chest GUI](./assets/a6e12e13-5b3b-4e72-a847-d19267b4422d.png) Create a chest GUI

Create a user interface using a chest.

### 1. Create a class that extends `com.tksimeji.visualkit.ChestUI`

You need to implement a title() method that returns `net.kyori.adventure.text.Component`
and a size() method that defines the size of the chest.

```java
public class MyChestUI extends ChestUI {
    public MyChestUI(@NotNull Player player) {
        super(player);
    }

    @Override
    public @NotNull Component title() {
        return Component.text("Cookie clicker").decorate(TextDecoration.BOLD);
    }

    @Override
    public @NotNull Size size() {
        return Size.SIZE_9;
    }    
}
```

### 2. Add element

Let's add elements to the GUI.

The simplest way to declare an element is to define a field in the class.

Add `com.tksimeji.visualkit.api.InitialElement` to a field of type `com.tksimeji.visualkit.element.VisualkitElement`.

```java
private int count;

@InitialElement(13)
private final VisualkitElement sheepButton = VisualkitElement
        .of(Material.COOKIE)
        .title(Component.text("Click me!").color(NamedTextColor.GREEN).decorate(TextDecoration.BOLD))
        .lore(Component.text("Clicks: ${count}"));
```

The annotation parameter specifies the index in the GUI.

You can place it in multiple slots:

```java
@InitialElement({2, 3, 5, 7, 11, 13, 17, 19})
```

You can also use asm (Advanced Slot Mapping) for more advanced specifications.

```java
@InitialElement(asm = {@Asm(from = 0, to = 8), @Asm(from = 18, to = 26), @Asm({27, 28})}, value = {29, 30})
```

### 3. Add handler

Define a method to handle clicks on any slot.

Add `com.tksimeji.visualkit.api.Handler` to a method that take no arguments.
In addition to slots, you can add click and mouse conditions to the Handler annotation.

```java
@Handler(slot = 13, click = Click.SINGLE, mouse = {Mouse.LEFT, Mouse.RIGHT})
public void onSheepButtonClick() {
    count ++;
}
```

Of course, you can also use asm to specify the slot.

```java
@Handler(asm = {@Asm(from = 0, to = 8)}, slot = {9, 10})
```

### 4. Display the GUI

All you have to do is create an instance.
Basically, you need to pass in the player as an argument.

```java
new MyChestUI(player);
```

The GUI will be displayed to the player specified as an argument.

## ![Panel GUI](./assets/4a48ded0-5caf-4a18-bcb4-095fffb6f8bf.png) Create a Panel GUI

The Panel GUI is a user interface that utilizes the scoreboard sidebar.

### 1. Create a class that extends `com.tksimeji.visualkit.PanelUI`

You need to define a title method that returns a `net.kyori.adventure.text.Component`.

```java
public class MyPanelUI extends PanelUI {
    @Override
    public @NotNull Component title() {
        return Component.text("INFO").color(NamedTextColor.YELLOW).decorate(TextDecoration.BOLD);
    }
}
```

### 2. Write text on the panel

Here we'll use the constructor to write to it.

Let's display the player's information as an example.
[Placeholder](#2479b5e2-7a0e-4a02-8e73-9ab045086560) are used to embed values.

The values is updated using the `onTick` method.

```java
private String name;
private int health;
private int ping;

public MyPanelUI() {
    addLine(Component.text("Hello, ${name}!"));
    addLine();
    addLine(Component.text("Health:").appendSpace().append(Component.text("${health}♥").color(NamedTextColor.RED)));
    setLine(3, Component.text("Ping:").appendSpace().append(Component.text("${ping} ms").color(NamedTextColor.GREEN)));
}

// Some code omitted //

@Override
public void onTick() {
    Player player = getPlayers().getFirst();

    if (player == null) {
        return;
    }

    name = player.getName();
    health = (int) player.getHealth();
    ping = player.getPing();
}
```

Looking at the code, we can see that the PanelUI is different from the inventory GUI
in that it is designed to be displayed to multiple players.

If you want to display it for individual players, as in this case, you'll need to use a little ingenuity.

For example:

```java
public MyPanelUI(@NotNull Player player) {
    addPlayer(player);
}

@Override
public void addPlayer(@NotNull Player player) {
    if (1 < getPlayers().size()) {
        throw new IllegalArgumentException();
    }

    super.addPlayer(player);
}
```

### 3. Display the GUI

Just create an instance and add the players you want to display.

```java
new MyPanelUI().addPlayer(player);
```

It will be displayed:

![](./assets/9f1b15f3-90d9-4fba-aace-999084882d52.png)