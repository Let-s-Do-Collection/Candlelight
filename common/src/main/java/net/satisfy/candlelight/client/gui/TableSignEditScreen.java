package net.satisfy.candlelight.client.gui;

import dev.architectury.networking.NetworkManager;
import net.minecraft.util.StringUtil;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.satisfy.candlelight.core.item.TableSignItem;
import net.satisfy.candlelight.core.networking.packet.SetTableSignTextPacket;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.List;

public class TableSignEditScreen extends Screen {
    private static final int TEXTURE_SIZE = 192;
    private static final int TEXT_X = 36;
    private static final int TEXT_Y = 32;
    private static final int TEXT_WIDTH = 114;
    private static final int LINE_HEIGHT = 10;
    private static final int TEXT_COLOR = 0x000000;

    private final InteractionHand hand;
    private final List<StringBuilder> lines = new ArrayList<>();
    private int cursorLine;
    private int cursorColumn;
    private int frameTick;

    public TableSignEditScreen(InteractionHand hand, String initialText) {
        super(Component.translatable("gui.candlelight.table_sign.edit"));
        this.hand = hand;
        for (String line : TableSignItem.getLines(initialText)) {
            if (lines.size() < TableSignItem.MAX_LINES) {
                lines.add(new StringBuilder(line));
            }
        }
        if (lines.isEmpty()) {
            lines.add(new StringBuilder());
        }
        cursorLine = lines.size() - 1;
        cursorColumn = lines.get(cursorLine).length();
    }

    @Override
    protected void init() {
        addRenderableWidget(Button.builder(Component.translatable("gui.done"), button -> onClose())
                .bounds(width / 2 - 50, TEXTURE_SIZE + 4, 100, 20)
                .build());
    }

    @Override
    public void tick() {
        frameTick++;
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        super.render(graphics, mouseX, mouseY, partialTick);
        int left = (width - TEXTURE_SIZE) / 2;
        for (int i = 0; i < lines.size(); i++) {
            graphics.drawString(font, lines.get(i).toString(), left + TEXT_X, TEXT_Y + i * LINE_HEIGHT, TEXT_COLOR, false);
        }
        if (frameTick / 6 % 2 == 0) {
            String beforeCursor = lines.get(cursorLine).substring(0, cursorColumn);
            int cursorX = left + TEXT_X + font.width(beforeCursor);
            int cursorY = TEXT_Y + cursorLine * LINE_HEIGHT;
            if (cursorColumn < lines.get(cursorLine).length()) {
                graphics.fill(cursorX, cursorY - 1, cursorX + 1, cursorY + font.lineHeight, 0xFF000000);
            } else {
                graphics.drawString(font, "_", cursorX, cursorY, TEXT_COLOR, false);
            }
        }
    }

    @Override
    public void renderBackground(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        super.renderBackground(graphics, mouseX, mouseY, partialTick);
        graphics.blit(NoteGui.NOTE_TEXTURE, (width - TEXTURE_SIZE) / 2, 2, 0, 0, TEXTURE_SIZE, TEXTURE_SIZE);
    }

    @Override
    public boolean charTyped(char character, int modifiers) {
        if (!StringUtil.isAllowedChatCharacter(character)) {
            return false;
        }
        StringBuilder line = lines.get(cursorLine);
        String candidate = new StringBuilder(line).insert(cursorColumn, character).toString();
        if (candidate.length() <= TableSignItem.MAX_LINE_LENGTH && font.width(candidate) <= TEXT_WIDTH) {
            line.insert(cursorColumn, character);
            cursorColumn++;
        }
        return true;
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        StringBuilder line = lines.get(cursorLine);
        switch (keyCode) {
            case GLFW.GLFW_KEY_ESCAPE -> {
                onClose();
                return true;
            }
            case GLFW.GLFW_KEY_ENTER, GLFW.GLFW_KEY_KP_ENTER -> {
                if (lines.size() < TableSignItem.MAX_LINES) {
                    String rest = line.substring(cursorColumn);
                    line.setLength(cursorColumn);
                    lines.add(cursorLine + 1, new StringBuilder(rest));
                    cursorLine++;
                    cursorColumn = 0;
                }
                return true;
            }
            case GLFW.GLFW_KEY_BACKSPACE -> {
                if (cursorColumn > 0) {
                    line.deleteCharAt(cursorColumn - 1);
                    cursorColumn--;
                } else if (cursorLine > 0) {
                    StringBuilder previous = lines.get(cursorLine - 1);
                    String merged = previous.toString() + line;
                    if (merged.length() <= TableSignItem.MAX_LINE_LENGTH && font.width(merged) <= TEXT_WIDTH) {
                        cursorColumn = previous.length();
                        previous.append(line);
                        lines.remove(cursorLine);
                        cursorLine--;
                    }
                }
                return true;
            }
            case GLFW.GLFW_KEY_DELETE -> {
                if (cursorColumn < line.length()) {
                    line.deleteCharAt(cursorColumn);
                } else if (cursorLine < lines.size() - 1) {
                    String merged = line.toString() + lines.get(cursorLine + 1);
                    if (merged.length() <= TableSignItem.MAX_LINE_LENGTH && font.width(merged) <= TEXT_WIDTH) {
                        line.append(lines.remove(cursorLine + 1));
                    }
                }
                return true;
            }
            case GLFW.GLFW_KEY_LEFT -> {
                if (cursorColumn > 0) {
                    cursorColumn--;
                } else if (cursorLine > 0) {
                    cursorLine--;
                    cursorColumn = lines.get(cursorLine).length();
                }
                return true;
            }
            case GLFW.GLFW_KEY_RIGHT -> {
                if (cursorColumn < line.length()) {
                    cursorColumn++;
                } else if (cursorLine < lines.size() - 1) {
                    cursorLine++;
                    cursorColumn = 0;
                }
                return true;
            }
            case GLFW.GLFW_KEY_UP -> {
                if (cursorLine > 0) {
                    cursorLine--;
                    cursorColumn = Math.min(cursorColumn, lines.get(cursorLine).length());
                }
                return true;
            }
            case GLFW.GLFW_KEY_DOWN -> {
                if (cursorLine < lines.size() - 1) {
                    cursorLine++;
                    cursorColumn = Math.min(cursorColumn, lines.get(cursorLine).length());
                }
                return true;
            }
            case GLFW.GLFW_KEY_HOME -> {
                cursorColumn = 0;
                return true;
            }
            case GLFW.GLFW_KEY_END -> {
                cursorColumn = line.length();
                return true;
            }
            default -> {
                return super.keyPressed(keyCode, scanCode, modifiers);
            }
        }
    }

    @Override
    public void onClose() {
        List<String> text = lines.stream().map(StringBuilder::toString).toList();
        NetworkManager.sendToServer(new SetTableSignTextPacket(hand, String.join("\n", text)));
        super.onClose();
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
