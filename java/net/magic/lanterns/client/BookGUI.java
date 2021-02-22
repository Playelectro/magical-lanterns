package net.magic.lanterns.client;

import io.github.cottonmc.cotton.gui.client.LightweightGuiDescription;
import io.github.cottonmc.cotton.gui.widget.*;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.text.LiteralText;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.math.MathHelper;

import java.util.concurrent.atomic.AtomicInteger;

public class BookGUI extends LightweightGuiDescription {
    public BookGUI(){
        AtomicInteger page = new AtomicInteger(0);
        BookPage root = new BookPage();
        root.setSize(180,180);
        setRootPanel(root);

        WLabel pageNumber = new WLabel(String.valueOf(page.get()));

        WLabel pageTitle = new WLabel(I18n.translate("title.magic_lanterns.page"+page.get()));

        WText pageText = new WText(new TranslatableText("text.magic_lanterns.page"+page.get()));

        //Navigation
        WButton nextPage= new WButton(new LiteralText("->"));
        nextPage.setOnClick(()->{
            page.set(MathHelper.clamp(page.incrementAndGet(),0,11));
            pageText.setText(new TranslatableText("text.magic_lanterns.page"+page.get()));
            pageNumber.setText(new TranslatableText(String.valueOf(page)));
            pageTitle.setText(new TranslatableText("title.magic_lanterns.page"+page.get()));
        });

        WButton prevPage= new WButton(new LiteralText("<-"));
        prevPage.setOnClick(()->{
            page.set(MathHelper.clamp(page.decrementAndGet(),0,11));
            pageText.setText(new TranslatableText("text.magic_lanterns.page"+page.get()));
            pageNumber.setText(new TranslatableText(String.valueOf(page)));
            pageTitle.setText(new TranslatableText("title.magic_lanterns.page"+page.get()));

        });


        root.add(prevPage, 0,0,20,20);
        root.add(nextPage, 160,0,20,20);
        root.add(pageNumber,90,5);
        root.add(pageTitle,20,30);
        root.add(pageText,20,45,160,135);


        root.validate(this);
    }
}