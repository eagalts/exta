package com.eagalts.exta.Extensions

import android.text.TextWatcher
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.StyleSpan
import android.widget.EditText
import android.graphics.Typeface

fun formatNotepadStyle(editText: EditText, textWatcher: TextWatcher) {
    val text = editText.text
    val spannableStringBuilder = SpannableStringBuilder(text)
    val lines = text.split("\n")

    var start = 0
    for (line in lines) {
        if (line.startsWith("#")) {
            val end = start + line.length
            spannableStringBuilder.setSpan(
                StyleSpan(Typeface.BOLD),
                start,
                end,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        } else {
            val spans = spannableStringBuilder.getSpans(start, start + line.length, StyleSpan::class.java)
            for (span in spans) {
                spannableStringBuilder.removeSpan(span)
            }
        }
        start += line.length + 1
    }

    // Удаляем TextWatcher перед изменением текста
    editText.removeTextChangedListener(textWatcher)

    // Обновляем только текст в spannableStringBuilder
    text.replace(0, text.length, spannableStringBuilder)

    // Повторно добавляем TextWatcher после изменения текста
    editText.addTextChangedListener(textWatcher)
}