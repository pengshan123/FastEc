package com.pengshan.festec.example.generators;

import com.pengshan.latte_annotations.annotations.PayEntryGenerator;
import com.pengshan.latte_core.wechat.templates.WXPayEntryTemplate;

@PayEntryGenerator(
        packageName = "com.pengshan.festec.example",
        payEntryTemplete = WXPayEntryTemplate.class
)
public interface WeChatPayEntry {

}
