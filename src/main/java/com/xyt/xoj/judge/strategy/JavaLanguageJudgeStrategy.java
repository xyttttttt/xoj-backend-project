package com.xyt.xoj.judge.strategy;

import cn.hutool.json.JSONUtil;
import com.xyt.xoj.model.dto.question.JudgeCase;
import com.xyt.xoj.model.dto.question.JudgeConfig;
import com.xyt.xoj.judge.codessandbox.model.JudgeInfo;
import com.xyt.xoj.model.entity.Question;
import com.xyt.xoj.model.enums.JudgeInfoMessageEnum;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * java判题策略
 */
public class JavaLanguageJudgeStrategy implements JudgeStrategy {


    @Override
    public JudgeInfo doJudge(JudgeContext judgeContext) {

        JudgeInfo judgeInfo = judgeContext.getJudgeInfo();
        List<String> inputList = judgeContext.getInputList();
        List<String> outputList = judgeContext.getOutputList();
        List<JudgeCase> judgeCaseList = judgeContext.getJudgeCaseList();
        Question question = judgeContext.getQuestion();

        outputList = outputList.stream()
                .filter(str -> str.lastIndexOf("\n") != -1)
                .map(str -> str.substring(0, str.lastIndexOf("\n")))
                .collect(Collectors.toList());

        Long memory = Optional.ofNullable(judgeInfo.getMemory()).orElse(0L);
        Long time = Optional.ofNullable(judgeInfo.getTime()).orElse(0L);

        JudgeInfo judgeInfoResponse = new JudgeInfo();

        judgeInfoResponse.setMemory(memory);
        judgeInfoResponse.setTime(time);

        //3.根据代码沙箱的执行结果、设置题目的判题状态和信息
        JudgeInfoMessageEnum judgeInfoMessageEnum = JudgeInfoMessageEnum.ACCEPTED;
        //判断执行结果的输出用例 和 输入用例个数是否相同
        if (outputList.size() != inputList.size()) {
            judgeInfoMessageEnum = JudgeInfoMessageEnum.WRONG_ANSWER;
            judgeInfoResponse.setMessage(judgeInfoMessageEnum.getValue());
            return judgeInfoResponse;
        }
        //判断每一项 执行的输出用例和和判题预期用例是否相同
        for (int i = 0; i < judgeCaseList.size(); i++) {
            JudgeCase judgeCase = judgeCaseList.get(i);
            if (!judgeCase.getOutput().equals(outputList.get(i))) {
                judgeInfoMessageEnum = JudgeInfoMessageEnum.WRONG_ANSWER;
                judgeInfoResponse.setMessage(judgeInfoMessageEnum.getValue());
                return judgeInfoResponse;
            }
        }
        //判断题目限制

        String judgeConfigStr = question.getJudgeConfig();
        JudgeConfig judgeConfig = JSONUtil.toBean(judgeConfigStr, JudgeConfig.class);
        //java程序本身额外需要10s
        Long timeLimit = judgeConfig.getTimeLimit();
        Long memoryLimit = judgeConfig.getMemoryLimit();
        if (memory > memoryLimit) {
            judgeInfoMessageEnum = JudgeInfoMessageEnum.MEMORY_LIMIT_EXCEEDED;
            judgeInfoResponse.setMessage(judgeInfoMessageEnum.getValue());
            return judgeInfoResponse;
        }
        long JAVA_PROGRAM_TIME_COST = 10000L;
        if ((time-JAVA_PROGRAM_TIME_COST) > timeLimit) {
            judgeInfoMessageEnum = JudgeInfoMessageEnum.TIME_LIMIT_EXCEEDED;
            judgeInfoResponse.setMessage(judgeInfoMessageEnum.getValue());
            return judgeInfoResponse;
        }

        judgeInfoResponse.setMessage(judgeInfoMessageEnum.getValue());
        return judgeInfoResponse;
    }
}
