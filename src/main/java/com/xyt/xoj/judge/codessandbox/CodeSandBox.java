package com.xyt.xoj.judge.codessandbox;

import com.xyt.xoj.judge.codessandbox.model.ExecuteCodeRequest;
import com.xyt.xoj.judge.codessandbox.model.ExecuteCodeResponse;

/**
 * 代码沙箱的接口定义
 */
public interface CodeSandBox {
    /**
     *
     * @param executeCodeRequest
     * @return
     */
    ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest);
}
