package com.jmyz.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jmyz.entity.Accounts;
import com.jmyz.entity.ScoreRate;
import com.jmyz.entity.Scores;
import com.jmyz.mapper.RateMapper;
import com.jmyz.mapper.ScoresMapper;
import com.jmyz.service.ScoresService;
import com.jmyz.utils.ResponseData;
import lombok.extern.log4j.Log4j2;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.stream.Collectors;

@Log4j2
@Service
public class ScoresServiceImpl extends ServiceImpl<ScoresMapper, Scores> implements ScoresService {

    @Resource
    private ScoresMapper scoresMapper;

    @Resource
    private RateMapper rateMapper;

    @Override
    public ResponseData queryScores() {
        Accounts accounts = (Accounts) SecurityUtils.getSubject().getPrincipal();
        SimpleDateFormat format =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        QueryWrapper<Scores> scoresQueryWrapper = new QueryWrapper<>();
        scoresQueryWrapper.lambda().eq(Scores::getAccountId,accounts.getAccountId()).
                eq(Scores::getDataStatus,1);

        final Double[] scoreAmounts = new Double[1];

        scoresMapper.selectList(scoresQueryWrapper).stream().filter(scores -> {
            if(scores.getVerifyTime()==0){
                return true;
            }
            try {
                return format.parse(scores.getRecTime()).getTime()+scores.getVerifyTime()*1000 >= new Date().getTime();
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return false;
        }).collect(Collectors.toList()).forEach(scores ->
                scoreAmounts[0] = new BigDecimal(scoreAmounts[0]).add(new BigDecimal(scores.getScoresAmounts())).doubleValue());

        ScoreRate scoreRate = rateMapper.selectById(1);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("scoreAmounts",scoreAmounts[0]);
        jsonObject.put("scoreToMoney",scoreAmounts[0]*(scoreRate ==null?1: scoreRate.getScoreRmbRate()));

        return new ResponseData<>(0,jsonObject);
    }
}
