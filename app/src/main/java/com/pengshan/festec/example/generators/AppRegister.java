package com.pengshan.festec.example.generators;


import com.pengshan.latte_annotations.annotations.AppRegisterGenerator;
import com.pengshan.latte_annotations.annotations.EntryGenerator;
import com.pengshan.latte_core.wechat.templates.AppRegisterTemplate;

@AppRegisterGenerator(
        packageName = "com.pengshan.festec.example",
        registerTemplete = AppRegisterTemplate.class
)
public interface AppRegister {

}
