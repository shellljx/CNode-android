package com.licrafter.cnode.widget.richTextView;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.util.AttributeSet;

/**
 * author: shell
 * date 2017/3/2 上午10:30
 **/
public class RichTextView extends AppCompatTextView {

    public RichTextView(Context context) {
        super(context);
    }

    public RichTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RichTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setRichText(String text) {
        Spanned spanned = Html.fromHtml(text, new AsyncImageGetter(getContext(), this), null);
        SpannableStringBuilder htmlSpannable;
        if (spanned instanceof SpannableStringBuilder) {
            htmlSpannable = (SpannableStringBuilder) spanned;
        } else {
            htmlSpannable = new SpannableStringBuilder(spanned);
        }
        handleP(htmlSpannable);
        setText(htmlSpannable);
        setMovementMethod(LinkMovementMethod.getInstance());
    }

    private static void handleP(SpannableStringBuilder text) {
        int len = text.length();
        if (len >= 2 && text.charAt(len - 1) == '\n' && text.charAt(len - 2) == '\n') {
            text.replace(len - 2, len - 1, "");
        }

    }

}