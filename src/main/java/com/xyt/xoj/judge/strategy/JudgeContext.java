package com.xyt.xoj.judge.strategy;

import com.xyt.xoj.model.dto.question.JudgeCase;
import com.xyt.xoj.judge.codessandbox.model.JudgeInfo;
import com.xyt.xoj.model.entity.Question;
import com.xyt.xoj.model.entity.QuestionSubmit;
import lombok.Data;

import java.util.List;

/**
 * 上下文(用于定义策略中传递的参数)
 */
@Data
public class JudgeContext {

    private JudgeInfo judgeInfo;

    private List<String> inputList;

    private List<String> outputList;

    private List<JudgeCase> judgeCaseList;

    private Question question;

    private QuestionSubmit questionSubmit;
}
