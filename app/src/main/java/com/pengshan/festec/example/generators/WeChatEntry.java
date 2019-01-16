package com.pengshan.festec.example.generators;

import com.pengshan.latte_annotations.annotations.EntryGenerator;
import com.pengshan.latte_core.wechat.templates.WXEntryTemplate;

@EntryGenerator(
        packageName = "com.pengshan.festec.example",
        entryTemplete = WXEntryTemplate.class
)
public interface WeChatEntry {

}
