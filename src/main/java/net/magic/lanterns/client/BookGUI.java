package net.magic.lanterns.client;

import io.github.cottonmc.cotton.gui.client.LightweightGuiDescription;
import io.github.cottonmc.cotton.gui.widget.*;
import io.github.cottonmc.cotton.gui.widget.data.Insets;
import net.minecraft.text.Text;
import net.minecraft.util.math.MathHelper;

import java.util.concurrent.atomic.AtomicInteger;

public class BookGUI extends LightweightGuiDescription {
    public BookGUI() {
        AtomicInteger page = new AtomicInteger(0);
        BookPage root = new BookPage();
        setRootPanel(root);
        root.setSize(180,180);
        root.setInsets(Insets.ROOT_PANEL);

        WLabel pageNumber = new WLabel(Text.of(String.valueOf(page.get())));

        WLabel pageTitle = new WLabel(Text.translatable("title.magic_lanterns.page" + page.get()));

        WText pageText = new WText(Text.translatable("text.magic_lanterns.page" + page.get()));

        // Navigation
        WButton nextPage = new WButton(Text.of("->"));
        nextPage.setOnClick(() -> {
            page.set(MathHelper.clamp(page.incrementAndGet(), 0, 11));
            pageText.setText(Text.translatable("text.magic_lanterns.page" + page.get()));
            pageNumber.setText(Text.translatable(String.valueOf(page)));
            pageTitle.setText(Text.translatable("title.magic_lanterns.page" + page.get()));
        });

        WButton prevPage = new WButton(Text.of("<-"));
        prevPage.setOnClick(() -> {
            page.set(MathHelper.clamp(page.decrementAndGet(), 0, 11));
            pageText.setText(Text.translatable("text.magic_lanterns.page" + page.get()));
            pageNumber.setText(Text.translatable(String.valueOf(page)));
            pageTitle.setText(Text.translatable("title.magic_lanterns.page" + page.get()));

        });


        root.add(prevPage, 0,0,20,20);
        root.add(nextPage, 160,0,20,20);
        root.add(pageNumber,90,5);
        root.add(pageTitle,20,30);
        root.add(pageText,20,45,160,135);


        root.validate(this);
    }
}