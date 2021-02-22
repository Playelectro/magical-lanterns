package net.magic.lanterns.client;

import io.github.cottonmc.cotton.gui.client.BackgroundPainter;
import io.github.cottonmc.cotton.gui.client.ScreenDrawing;
import io.github.cottonmc.cotton.gui.widget.WCardPanel;
import io.github.cottonmc.cotton.gui.widget.WPlainPanel;
import io.github.cottonmc.cotton.gui.widget.WWidget;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.magic.lanterns.MagicLanternsMod;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

import java.util.Iterator;

public class BookPage extends WPlainPanel {
    private static final Identifier TEXTURE = new Identifier(MagicLanternsMod.MODID, "textures/gui/page.png");

    @Environment(EnvType.CLIENT)
    @Override
    public void paint(MatrixStack matrices, int x, int y, int mouseX, int mouseY) {
        ScreenDrawing.texturedRect(x, y, width, height, TEXTURE, 0f, 0f, 1f, 1f, 0xFFFFFFFF);

        for (WWidget child : this.children) {
            child.paint(matrices, x + child.getX(), y + child.getY(), mouseX - child.getX(), mouseY - child.getY());
        }
        // the 0xFFFFFFFF is the color in ARGB format, which in this case is white
    }
}
