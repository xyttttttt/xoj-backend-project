package com.xyt.xoj.model.vo;

import cn.hutool.json.JSONUtil;
import com.xyt.xoj.model.dto.question.JudgeConfig;
import com.xyt.xoj.model.entity.Question;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.util.List;

@Data
public class QuestionAdminVo {
    /**
     * id
     */
    private Long id;

    /**
     * 标题
     */
    private String title;

    /**
     * 内容
     */
    private String content;

    /**
     * 标签列表（json 数组）
     */
    private List<String> tags;

    /**
     * 判题配置(json 对象)
     */
    private JudgeConfig judgeConfig;

    /**
     * 判题用例(json 数组)
     */
    private List<String> judgeCase;
    /**
     * 题目提交数
     */
    private Integer submitNum;


    /**
     * 题目答案
     */
    private String answer;

    /**
     * 题目通过数
     */
    private Integer acceptedNum;

    /**
     * 点赞数
     */
    private Integer thumbNum;

    /**
     * 收藏数
     */
    private Integer favourNum;

    /**
     * 创建用户 id
     */
    private Long userId;



    private static final long serialVersionUID = 1L;

    /**
     * 包装类转对象
     *
     * @param questionAdminVo
     * @return
     */
    public static Question voToObj(QuestionAdminVo questionAdminVo) {
        if (questionAdminVo == null) {
            return null;
        }
        Question question = new Question();
        BeanUtils.copyProperties(questionAdminVo, question);
        List<String> tagList = questionAdminVo.getTags();
        if (tagList != null) {
            question.setTags(JSONUtil.toJsonStr(tagList));
        }
        JudgeConfig judgeConfig = questionAdminVo.getJudgeConfig();

        if (judgeConfig != null){
            question.setJudgeConfig(JSONUtil.toJsonStr(judgeConfig));
        }
        return question;
    }

    /**
     * 对象转包装类
     *
     * @param question
     * @return
     */
    public static QuestionAdminVo objToVo(Question question) {
        if (question == null) {
            return null;
        }
        QuestionAdminVo questionAdminVo = new QuestionAdminVo();
        BeanUtils.copyProperties(question, questionAdminVo);
        List<String> tagList = JSONUtil.toList(question.getTags(), String.class);
        questionAdminVo.setTags(tagList);
        String judgeConfig = question.getJudgeConfig();
        List<String> caseList = JSONUtil.toList(question.getJudgeCase(),String.class);
        questionAdminVo.setJudgeCase(caseList);
        questionAdminVo.setJudgeConfig(JSONUtil.toBean(judgeConfig, JudgeConfig.class));  //字符串转bean
        return questionAdminVo;
    }
}
