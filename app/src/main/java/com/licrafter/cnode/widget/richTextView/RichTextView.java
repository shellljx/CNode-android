package com.licrafter.cnode.widget.richTextView;

import android.content.Context;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ImageSpan;
import android.text.style.URLSpan;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * author: shell
 * date 2017/3/2 上午10:30
 **/
public class RichTextView extends TextView {

    public RichTextView(Context context) {
        super(context);
    }

    public RichTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RichTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setRichText(String text){
        Spanned spanned = Html.fromHtml(text,new AsyncImageGetter(getContext(),this),null);
        SpannableStringBuilder htmlSpannable;
        if (spanned instanceof SpannableStringBuilder) {
            htmlSpannable = (SpannableStringBuilder) spanned;
        } else {
            htmlSpannable = new SpannableStringBuilder(spanned);
        }
        /**
         * 点击图片
         */
        ImageSpan[] spans = htmlSpannable.getSpans(0, htmlSpannable.length(), ImageSpan.class);
        final ArrayList<String> imageUrls = new ArrayList<String>();
        final ArrayList<String> imagePositions = new ArrayList<String>();
        for (ImageSpan span : spans) {
            final String imageUrl = span.getSource();
            final int start = htmlSpannable.getSpanStart(span);
            final int end = htmlSpannable.getSpanEnd(span);
            imagePositions.add(start + "/" + end);
            imageUrls.add(imageUrl);
        }

        for(ImageSpan span : spans){
            final int start = htmlSpannable.getSpanStart(span);
            final int end   = htmlSpannable.getSpanEnd(span);
            ClickableSpan clickableSpan = new ClickableSpan() {
                @Override
                public void onClick(View widget) {
                    //PhotoViewActivity.launch(getContext(), imagePositions.indexOf(start + "/" + end), imageUrls);
                    Toast.makeText(getContext(),"点击了",Toast.LENGTH_SHORT).show();
                }
            };

            htmlSpannable.setSpan(clickableSpan, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        /**
         * 点击@后面的人名崩溃的问题,并且点击@用户名跳转到用户详情页
         * http://androblip.huiges.nl/2010/11/28/handling-html-in-a-textview/
         */
        URLSpan[] urlSpans = htmlSpannable.getSpans(0,htmlSpannable.length(),URLSpan.class);
        for (URLSpan span : urlSpans){
            int start = htmlSpannable.getSpanStart(span);
            int end = htmlSpannable.getSpanEnd(span);
            int flag = htmlSpannable.getSpanFlags(span);
            if (!span.getURL().startsWith("http")&&span.getURL().contains("/member/")){
                ClickableSpan clickableSpan = new ClickableSpan() {
                    @Override
                    public void onClick(View widget) {
                        Toast.makeText(getContext(),"@",Toast.LENGTH_SHORT).show();
                    }
                };
                htmlSpannable.removeSpan(span);
                htmlSpannable.setSpan(clickableSpan,start,end,flag);
            }
        }

        super.setText(spanned);
        //添加超链接,不加链接点不了
        setMovementMethod(LinkMovementMethod.getInstance());
    }

}