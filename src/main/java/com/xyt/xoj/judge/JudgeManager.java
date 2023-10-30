package com.xyt.xoj.judge;

import com.xyt.xoj.judge.strategy.DefaultJudgeStrategy;
import com.xyt.xoj.judge.strategy.JavaLanguageJudgeStrategy;
import com.xyt.xoj.judge.strategy.JudgeContext;
import com.xyt.xoj.judge.strategy.JudgeStrategy;
import com.xyt.xoj.judge.codessandbox.model.JudgeInfo;
import com.xyt.xoj.model.entity.QuestionSubmit;
import org.springframework.stereotype.Service;

/**
 * 判题管理 简化调用
 *
 */
@Service
public class JudgeManager {

    /**
     * 判题服务
     * @param judgeContext
     * @return
     */
    JudgeInfo doJudge(JudgeContext judgeContext){
        QuestionSubmit questionSubmit = judgeContext.getQuestionSubmit();
        String language = questionSubmit.getLanguage();
        JudgeStrategy judgeStrategy = new DefaultJudgeStrategy();
        if ("java".equals(language)){
            judgeStrategy = new JavaLanguageJudgeStrategy();
        }
        return judgeStrategy.doJudge(judgeContext);
    }
}
