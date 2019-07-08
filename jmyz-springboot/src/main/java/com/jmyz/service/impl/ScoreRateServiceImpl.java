package com.jmyz.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jmyz.entity.ScoreRate;
import com.jmyz.mapper.RateMapper;
import com.jmyz.service.ScoreRateService;
import com.jmyz.utils.ResponseData;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Log4j2
@Service
public class ScoreRateServiceImpl extends ServiceImpl<RateMapper, ScoreRate> implements ScoreRateService {

    @Resource
    private RateMapper rateMapper;

    @Override
    public ResponseData operaRate(ScoreRate scoreRate) {
        int result;
        if(StringUtils.isBlank(scoreRate.getRateId())){
            scoreRate.setRateId("1");
            scoreRate.setDataStatus(1);
            result = rateMapper.insert(scoreRate);
        }else{
            result = rateMapper.updateById(scoreRate);
        }
        return new ResponseData<>(result==1?0:100,"操作结束");
    }
}
