package com.xyt.xoj.judge.strategy;

import com.xyt.xoj.judge.codessandbox.model.JudgeInfo;

/**
 * 判题策略
 */
public interface JudgeStrategy {


    JudgeInfo doJudge(JudgeContext judgeContext);
}
