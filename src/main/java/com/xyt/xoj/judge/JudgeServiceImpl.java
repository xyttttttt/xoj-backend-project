package com.xyt.xoj.judge;


import cn.hutool.json.JSONUtil;
import com.xyt.xoj.common.ErrorCode;
import com.xyt.xoj.exception.BusinessException;
import com.xyt.xoj.judge.codessandbox.CodeSandBox;
import com.xyt.xoj.judge.codessandbox.CodeSandboxFactory;
import com.xyt.xoj.judge.codessandbox.CodeSandboxProxy;
import com.xyt.xoj.judge.codessandbox.model.ExecuteCodeRequest;
import com.xyt.xoj.judge.codessandbox.model.ExecuteCodeResponse;

import com.xyt.xoj.judge.strategy.JudgeContext;

import com.xyt.xoj.model.dto.question.JudgeCase;

import com.xyt.xoj.judge.codessandbox.model.JudgeInfo;
import com.xyt.xoj.model.entity.Question;
import com.xyt.xoj.model.entity.QuestionSubmit;

import com.xyt.xoj.model.enums.QuestionSubmitStatusEnum;

import com.xyt.xoj.service.QuestionService;
import com.xyt.xoj.service.QuestionSubmitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class JudgeServiceImpl implements JudgeService {

    @Resource
    private QuestionService questionService;

    @Autowired
    private QuestionSubmitService questionSubmitService;

    @Autowired
    private JudgeManager judgeManager;

    @Value("${codesandbox.type:example}")
    private String type;

    @Override
    public QuestionSubmit doJudge(long questionSubmitId) {
        //1.传入题目提交的id 获取题目、提交信息(包含代码、语言等)
        QuestionSubmit questionSubmit = questionSubmitService.getById(questionSubmitId);
        if (questionSubmit == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }

        Long questionId = questionSubmit.getQuestionId();
        Question question = questionService.getById(questionId);
        if (question == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }

        //判断题目状态防止重复执行
        //如果正在判题中 返回并抛出异常
        if (!questionSubmit.getStatus().equals(QuestionSubmitStatusEnum.WAITING.getValue())) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "题目正在判题中");
        }
        //更改题目状态   防止在等待判题的过程中，用户重复提交
        QuestionSubmit questionSubmitUpdate = new QuestionSubmit();
        questionSubmitUpdate.setId(questionSubmitId);
        questionSubmitUpdate.setStatus(QuestionSubmitStatusEnum.RUNNING.getValue());
        boolean update = questionSubmitService.updateById(questionSubmitUpdate);
        if (!update) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "服务判题异常");
        }
        //2.调用代码沙箱、获取执行结果
            //根据用户想要的判题模式 创建对应的沙箱
        CodeSandBox codeSandBox = CodeSandboxFactory.newInstance(type);
            //增强代理类 (在执行代码沙箱前后打印日志：请求信息和响应信息)
        codeSandBox = new CodeSandboxProxy(codeSandBox);

        String language = questionSubmit.getLanguage();
        String code = questionSubmit.getCode();
        //获取输入用例    判题用例
        String judgeCaseStr = question.getJudgeCase();
        //输入用例转数组
        List<JudgeCase> judgeCaseList = JSONUtil.toList(judgeCaseStr, JudgeCase.class);
        List<String> inputList = judgeCaseList.stream().map(JudgeCase::getInput).collect(Collectors.toList());

        //封装请求信息并执行代码沙箱   返回响应结果
        ExecuteCodeRequest executeCodeRequest = ExecuteCodeRequest.builder()
                .code(code)
                .language(language)
                .inputList(inputList)
                .build();
        ExecuteCodeResponse executeCodeResponse = codeSandBox.executeCode(executeCodeRequest);

        //在响应结果中获取用户代码输出信息
        List<String> outputList = executeCodeResponse.getOutputList();

        //3.根据代码沙箱的执行结果、设置题目的判题状态和信息
        JudgeContext judgeContext = new JudgeContext();
        judgeContext.setJudgeInfo(executeCodeResponse.getJudgeInfo());
        judgeContext.setInputList(inputList);
        judgeContext.setOutputList(outputList);
        judgeContext.setJudgeCaseList(judgeCaseList);
        judgeContext.setQuestion(question);
        judgeContext.setQuestionSubmit(questionSubmit);

        JudgeInfo judgeInfo = judgeManager.doJudge(judgeContext);

        //更新数据库
        questionSubmitUpdate = new QuestionSubmit();
        questionSubmitUpdate.setId(questionSubmitId);
        questionSubmitUpdate.setStatus(QuestionSubmitStatusEnum.SUCCESS.getValue());
        questionSubmitUpdate.setJudgeInfo(JSONUtil.toJsonStr(judgeInfo));
        update = questionSubmitService.updateById(questionSubmitUpdate);

        if (!update) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "服务判题异常");
        }
        QuestionSubmit questionSubmitResult = questionSubmitService.getById(questionId);
        return questionSubmitResult;
    }
}
