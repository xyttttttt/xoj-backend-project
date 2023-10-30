package com.xyt.xoj.judge.codessandbox.impl;

import com.xyt.xoj.judge.codessandbox.CodeSandBox;
import com.xyt.xoj.judge.codessandbox.model.ExecuteCodeRequest;
import com.xyt.xoj.judge.codessandbox.model.ExecuteCodeResponse;

/**
 * 第三方代码沙箱
 */
public class ThirdPartyCodeSandBoxImpl implements CodeSandBox {


    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        System.out.println("第三方代码沙箱");
        return null;
    }
}
