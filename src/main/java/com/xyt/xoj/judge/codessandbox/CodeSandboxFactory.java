package com.xyt.xoj.judge.codessandbox;

import com.xyt.xoj.judge.codessandbox.impl.ExampleCodeSandBoxImpl;
import com.xyt.xoj.judge.codessandbox.impl.RemoteCodeSandBoxImpl;
import com.xyt.xoj.judge.codessandbox.impl.ThirdPartyCodeSandBoxImpl;

/**
 * 代码沙箱工厂：根据字符串参数创建指定代码沙箱实例
 */
public class CodeSandboxFactory {

    public static CodeSandBox newInstance(String type) {
        switch (type) {
            case "example":
                return new ExampleCodeSandBoxImpl();
            case "remote":
                return new RemoteCodeSandBoxImpl();
            case "thirdParty":
                return new ThirdPartyCodeSandBoxImpl();
            default:
                return new ExampleCodeSandBoxImpl();
        }
    }
}
