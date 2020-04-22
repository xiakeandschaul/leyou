package cn.leyou.service;


import cn.leyou.item.pojo.dto.SpecGroupDTO;
import cn.leyou.item.pojo.dto.SpecParamDTO;

import java.util.List;

public interface SpecService {
    List<SpecGroupDTO> querySpecGroup(Long cid);

    void add(SpecGroupDTO specGroupDTO);

    List<SpecParamDTO> querySpecParams(Long gid, Long cid, Boolean searching);

    void addParams(SpecParamDTO specParamDTO);


    void deleteParam(Long id);

    void updateParam(SpecParamDTO specParamDTO);

    void update(SpecGroupDTO specGroupDTO);

    void delete(Long gid);
}
