package com.eternalpixel.eternalpixeldungeon.windows;

import com.eternalpixel.eternalpixeldungeon.Chrome;
import com.eternalpixel.eternalpixeldungeon.Wishing;
import com.eternalpixel.eternalpixeldungeon.messages.Messages;
import com.eternalpixel.eternalpixeldungeon.scenes.PixelScene;
import com.eternalpixel.eternalpixeldungeon.ui.RedButton;
import com.eternalpixel.eternalpixeldungeon.ui.RenderedTextBlock;
import com.eternalpixel.eternalpixeldungeon.ui.Window;
import com.watabou.noosa.TextInput;
import com.watabou.utils.DeviceCompat;

public class WndWishing extends Window {

    private static final int WIDTH = 120;
    private static final int W_LAND_MULTI = 200; //in the specific case of multiline in landscape
    private static final int MARGIN = 2;
    private static final int BUTTON_HEIGHT = 16;

    public WndWishing() {
        super();

        final String title = Messages.get(this,"title");
        final String initialValue = null;
        final int maxLength = 48;
        final boolean multiLine = false;
        final String posTxt = Messages.get(this,"confirm");

        //need to offset to give space for the soft keyboard
        if (!DeviceCompat.isDesktop()) {
            if (PixelScene.landscape()) {
                offset(-45);
            } else {
                offset(multiLine ? -60 : -45);
            }
        }

        final int width;
        if (PixelScene.landscape() && multiLine) {
            width = W_LAND_MULTI; //more editing space for landscape users
        } else {
            width = WIDTH;
        }

        final RenderedTextBlock txtTitle = PixelScene.renderTextBlock(title, 9);
        txtTitle.maxWidth(width);
        txtTitle.hardlight(Window.TITLE_COLOR);
        txtTitle.setPos((width - txtTitle.width()) / 2, 2);
        add(txtTitle);

        int textSize = (int)PixelScene.uiCamera.zoom * (multiLine ? 6 : 9);
        final TextInput textBox = new TextInput(Chrome.get(Chrome.Type.TOAST_WHITE), multiLine, textSize){
            @Override
            public void enterPressed() {
                //triggers positive action on enter pressed, only with non-multiline though.
                onSelect(true, getText());
                hide();
            }
        };
        if (initialValue != null) textBox.setText(initialValue);
        textBox.setMaxLength(maxLength);

        float pos = txtTitle.bottom() + 2 * MARGIN;

        //sets different height depending on whether this is a single or multi line input.
        final float inputHeight;
        if (multiLine) {
            inputHeight = 64; //~8 lines of text
        } else {
            inputHeight = 16;
        }
        add(textBox);
        textBox.setRect(MARGIN, pos, width-2*MARGIN, inputHeight);

        pos += inputHeight + MARGIN;

        final RedButton positiveBtn = new RedButton(posTxt) {
            @Override
            protected void onClick() {
                if (textBox.getText() != "") {
                    Wishing.doWish(textBox.getText());
                }
                hide();
            }
        };

        positiveBtn.setRect(MARGIN, pos, width - MARGIN * 2, BUTTON_HEIGHT);
        add(positiveBtn);


        pos += BUTTON_HEIGHT + MARGIN;

        //need to resize first before laying out the text box, as it depends on the window's camera
        resize(width, (int) pos);

        textBox.setRect(MARGIN, textBox.top(), width-2*MARGIN, inputHeight);

    }

    public void onSelect(boolean positive, String text){ }

    @Override
    public void onBackPressed() {
        //Do nothing, prevents accidentally losing writing
    }
}
