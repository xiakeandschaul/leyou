package cn.leyou.service.impl;


import cn.leyou.enums.ExceptionEnum;
import cn.leyou.exception.LyException;
import cn.leyou.item.pojo.SpecGroup;
import cn.leyou.item.pojo.SpecParam;
import cn.leyou.item.pojo.dto.SpecGroupDTO;
import cn.leyou.item.pojo.dto.SpecParamDTO;
import cn.leyou.mapper.SpecGroupMapper;
import cn.leyou.mapper.SpecParamMapper;
import cn.leyou.service.SpecService;
import cn.leyou.utils.BeanHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;

/**
 * @author ：xiaKe
 * @date ：Created in 2020/3/25 16:55
 * @description：
 */
@Service
public class SpecGroupImpl implements SpecService {
    @Autowired
    private SpecGroupMapper specGroupMapper;

    @Autowired
    private SpecParamMapper specParamMapper;

    @Override
    public List<SpecGroupDTO> querySpecGroup(Long cid) {
        SpecGroup specGroup = new SpecGroup();
        specGroup.setCid(cid);
        List<SpecGroup> specGroups = specGroupMapper.select(specGroup);
        if (CollectionUtils.isEmpty(specGroups)) {
            throw new LyException(ExceptionEnum.DATA_NOT_FOUND);
        }
        return  BeanHelper.copyWithCollection(specGroups, SpecGroupDTO.class);
    }

    @Override
    public void add(SpecGroupDTO specGroupDTO) {
        SpecGroup specGroup = BeanHelper.copyProperties(specGroupDTO, SpecGroup.class);
        Date date = new Date();
        specGroup.setCreateTime(date);
        int count = specGroupMapper.insertSelective(specGroup);
        if (1!= count) {
            throw new LyException(ExceptionEnum.DATA_INSERT_ERROR);
        }
    }

    @Override
    public List<SpecParamDTO> querySpecParams(Long gid, Long cid, Boolean searching) {
        if (cid == null && gid == null) {
            throw new LyException(ExceptionEnum.INVALID_PARAM_ERROR);
        }
        SpecParam specParam = new SpecParam();
        specParam.setGroupId(gid);
        specParam.setCid(cid);
        specParam.setSearching(searching);
        List<SpecParam> specParams = specParamMapper.select(specParam);
        if (CollectionUtils.isEmpty(specParams)) {
            throw new LyException(ExceptionEnum.DATA_NOT_FOUND);
        }
        return BeanHelper.copyWithCollection(specParams, SpecParamDTO.class);
    }

    @Override
    public void addParams(SpecParamDTO specParamDTO) {
        SpecParam specParam = BeanHelper.copyProperties(specParamDTO, SpecParam.class);
        int count = specParamMapper.insertSelective(specParam);
        if (1 != count) {
            throw new LyException(ExceptionEnum.DATA_INSERT_ERROR);
        }
    }

    @Override
    public void updateParam(SpecParamDTO specParamDTO) {
        SpecParam specParam = BeanHelper.copyProperties(specParamDTO, SpecParam.class);
        int count = specParamMapper.updateByPrimaryKeySelective(specParam);
        if (1 != count) {
            throw new LyException(ExceptionEnum.DATA_UPLOAD_ERROR);
        }
    }

    @Override
    public void update(SpecGroupDTO specGroupDTO) {
        SpecGroup specGroup = BeanHelper.copyProperties(specGroupDTO, SpecGroup.class);
        int count = specGroupMapper.updateByPrimaryKeySelective(specGroup);
        if (1 != count) {
            throw new LyException(ExceptionEnum.DATA_UPLOAD_ERROR);
        }
    }

    @Override
    public void delete(Long gid) {
        int count = specGroupMapper.deleteByPrimaryKey(gid);
        if (1 != count) {
            throw new LyException(ExceptionEnum.DATA_DELETE_ERROR);
        }
    }

    @Override
    public void deleteParam(Long id) {
        int count = specParamMapper.deleteByPrimaryKey(id);
        if (1 != count) {
            throw new LyException(ExceptionEnum.DATA_DELETE_ERROR);
        }
    }
}
