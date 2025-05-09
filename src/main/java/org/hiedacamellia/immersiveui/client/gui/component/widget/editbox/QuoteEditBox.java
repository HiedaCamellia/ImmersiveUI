package org.hiedacamellia.immersiveui.client.gui.component.widget.editbox;

import net.minecraft.Util;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.Nullable;

public class QuoteEditBox extends EditBox {

    public Component getQuoted() {
        return quoted;
    }

    protected Component quoted = Component.empty();

    public void setQuoted(Component quoted) {
        this.quoted = quoted;
    }

    protected boolean hasQuote() {
        return !quoted.equals(Component.empty());
    }

    @Override
    public int getHeight() {
        return super.getHeight() + (!hasQuote()? 0 : font.lineHeight+1);
    }

    @Override
    public int getY() {
        return super.getY() - (!hasQuote()? 0 : font.lineHeight+1);
    }

    public QuoteEditBox(Font font, int width, int height, Component message) {
        super(font, width, height, message);
    }

    public QuoteEditBox(Font font, int x, int y, int width, int height, Component message) {
        super(font, x, y, width, height, message);
    }

    public QuoteEditBox(Font font, int x, int y, int width, int height, @Nullable EditBox editBox, Component message) {
        super(font, x, y, width, height, editBox, message);
    }


    public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        if (this.isVisible()) {
            if (this.isBordered()) {
                ResourceLocation resourcelocation = SPRITES.get(this.isActive(), this.isFocused());
                guiGraphics.blitSprite(resourcelocation, this.getX(), this.getY(), this.getWidth(), this.getHeight());
            }

            int l1 = this.isEditable ? this.textColor : this.textColorUneditable;
            int i = this.cursorPos - this.displayPos;
            String s = this.font.plainSubstrByWidth(this.value.substring(this.displayPos), this.getInnerWidth());
            boolean flag = i >= 0 && i <= s.length();
            boolean flag1 = this.isFocused() && (Util.getMillis() - this.focusedTime) / 300L % 2L == 0L && flag;
            int j = this.bordered ? this.getX() + 4 : this.getX();
            int k = this.bordered ? super.getY() + (this.height - 8) / 2 : super.getY();
            int l = j;
            int i1 = Mth.clamp(this.highlightPos - this.displayPos, 0, s.length());
            if (!s.isEmpty()) {
                String s1 = flag ? s.substring(0, i) : s;
                l = guiGraphics.drawString(this.font, (FormattedCharSequence)this.formatter.apply(s1, this.displayPos), j, k, l1, this.textShadow);
            }

            boolean flag2 = this.cursorPos < this.value.length() || this.value.length() >= this.getMaxLength();
            int j1 = l;
            if (!flag) {
                j1 = i > 0 ? j + this.width : j;
            } else if (flag2) {
                j1 = l - 1;
                --l;
            }

            if(hasQuote()){
                guiGraphics.drawString(this.font,quoted, j+4, k - font.lineHeight-3, 0xFFC4C4C4, false);
                guiGraphics.hLine(RenderType.gui(), j+4, j+4+this.font.width(quoted), k - 3, 0xFFC4C4C4);
            }


            if (!s.isEmpty() && flag && i < s.length()) {
                guiGraphics.drawString(this.font, (FormattedCharSequence)this.formatter.apply(s.substring(i), this.cursorPos), l, k, l1, this.textShadow);
            }

            if (this.hint != null && s.isEmpty() && !this.isFocused()) {
                guiGraphics.drawString(this.font, this.hint, l, k, l1, this.textShadow);
            }

            if (!flag2 && this.suggestion != null) {
                guiGraphics.drawString(this.font, this.suggestion, j1 - 1, k, -8355712, this.textShadow);
            }

            if (flag1) {
                if (flag2) {
                    guiGraphics.fill(RenderType.guiOverlay(), j1, k - 1, j1 + 1, k + 1 + 9, -3092272);
                } else {
                    guiGraphics.drawString(this.font, "_", j1, k, l1, this.textShadow);
                }
            }

            if (i1 != i) {
                int k1 = j + this.font.width(s.substring(0, i1));
                this.renderHighlight(guiGraphics, j1, k - 1, k1 - 1, k + 1 + 9);
            }
        }

    }
}
